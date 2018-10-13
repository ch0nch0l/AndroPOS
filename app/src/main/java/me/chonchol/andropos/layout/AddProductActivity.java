package me.chonchol.andropos.layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Product;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.model.Subcategory;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission_group.CAMERA;

public class AddProductActivity extends AppCompatActivity {

    private EditText productName, productPrice, productQuantity;
    private Button btnScanBarCode;
    private ImageView imgProductBarCode;
    private CheckBox chkScanBarcode;
    private BetterSpinner dropdownCatList, dropdownSubcatList;
    private FloatingActionButton btnAddProduct;

    private IntentIntegrator qrScan;
    private ApiService apiService;

    private List<String> catNameList = new ArrayList<>();
    private List<Object> categoryList = new ArrayList<>();
    private List<String> subcatNameList = new ArrayList<>();
    private List<Object> subcategoryList = new ArrayList<>();

    private ArrayAdapter<String> catAdapter, subcatAdapter;
    private Product product;
    private String scannedCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        qrScan = new IntentIntegrator(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "No QRCode/BarCode Found, Please Try again.!", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    scannedCode = obj.getString("name")+obj.getString("address");

                    Toast.makeText(this, obj.getString("name") + obj.getString("address"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    scannedCode = result.getContents();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void initializeView() {
        productName = findViewById(R.id.txtProductName);
        productPrice = findViewById(R.id.txtProductPrice);
        productQuantity = findViewById(R.id.txtProductQuantity);
        btnScanBarCode = findViewById(R.id.btnScanBarCode);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        imgProductBarCode = findViewById(R.id.imgProductBarCode);
        chkScanBarcode = findViewById(R.id.chkScanBarcode);

        dropdownCatList = findViewById(R.id.dropdownCatList);
        dropdownSubcatList = findViewById(R.id.dropdownSubcatList);

        product = new Product();
        getCatNameList();

        catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, catNameList);
        dropdownCatList.setAdapter(catAdapter);

        dropdownCatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = categoryList.get(position);
                Category category = (Category) object;
                getSubcategoryListByCatId(category.getCatId());
                subcatAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, subcatNameList);
                dropdownSubcatList.setEnabled(true);
                dropdownSubcatList.isClickable();
                dropdownSubcatList.setAdapter(subcatAdapter);

                dropdownSubcatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        product.setSubcategory((Subcategory) subcategoryList.get(position));
                    }
                });

            }
        });

        chkScanBarcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnScanBarCode.setEnabled(true);
                    int color = Color.parseColor("#424242");
                    btnScanBarCode.setBackgroundColor(color);
                } else {
                    btnScanBarCode.setEnabled(false);
                    int color = Color.parseColor("#bebebe");
                    btnScanBarCode.setBackgroundColor(color);
                }
            }
        });

        btnScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setProductName(productName.getText().toString());
                product.setPrice(Double.valueOf(productPrice.getText().toString()));
                product.setActive(true);
                if (scannedCode != null){
                    product.setCode(scannedCode);
                } else {
                    product.setCode(productName.getText().toString()+ dropdownSubcatList.getText().toString());
                }

                saveProduct(product);


//                String text = productName.getText().toString()+ dropdownSubcatList.getText().toString(); // Whatever you need to encode in the QR code
//                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//                try {
//                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
//                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                    imgProductBarCode.setImageBitmap(bitmap);
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private void saveProduct(Product product) {
        apiService = ApiClient.getClient().create(ApiService.class);
        apiService.saveProduct(product).enqueue(new Callback<Product>(){
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                response.body();
                Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_LONG).show();

                if (response.isSuccessful()){
                    Product product = response.body();
                    Stock stock = new Stock();
                    stock.setProduct(product);
                    stock.setQuantity(Integer.valueOf(productQuantity.getText().toString()));

                    saveStock(stock);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Product add failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveStock(Stock stock) {
        apiService = ApiClient.getClient().create(ApiService.class);
        apiService.saveStock(stock).enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Stock added", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {

            }
        });
    }

    private void getSubcategoryListByCatId(Integer catId) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Subcategory>> getSubcategoryListByCatId = apiService.getSubcategoryListByCatId(catId);
        getSubcategoryListByCatId.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                subcatNameList.clear();
                for (Subcategory subcategory: response.body()){
                    subcatNameList.add(subcategory.getSubcatName());
                    subcategoryList.add(subcategory);
                }
            }

            @Override
            public void onFailure(Call<List<Subcategory>> call, Throwable t) {

            }
        });
    }

    private List<String> getCatNameList() {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Category>> getAllCategories = apiService.getAllCategories();
        getAllCategories.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                for (Category category : response.body()) {
                    catNameList.add(category.getCatName());
                    categoryList.add(category);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        return catNameList;
    }

}
