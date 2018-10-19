package me.chonchol.andropos.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.CartAdapter;
import me.chonchol.andropos.enums.OrderStatus;
import me.chonchol.andropos.model.CartProduct;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;
import me.chonchol.andropos.model.Quotation;
import me.chonchol.andropos.model.QuotationList;
import me.chonchol.andropos.model.Sale;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSaleActivity extends AppCompatActivity {

    private Customer customer = new Customer();
    private String customerName, customerPhone, customerAddress;
    private ApiService apiService;
    private Integer counter = 1;
    private List<Stock> stockList = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private List<CartProduct> cartProductList = new ArrayList<>();
    private CartAdapter adapter;

    private ImageView imgOne, imgTwo, imgThree;
    private View lineOne, lineTwo;
    private LinearLayout saleCustomerLayout, saleProductLayout, saleConfirmLayout, saleProductListLayout;
    private EditText inputCustomerName, inputCustomerPhone, inputCustomerAddress;
    private Button btnSearchProduct, btnScanProduct;
    private TextView txtCustomerName, txtCustomerPhone, txtCustomerAddress;
    private RecyclerView cartListView;
    private FloatingActionButton btnNext, btnPrev;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeView();
        saleCustomerView();

    }


    private void initializeView() {
        progressDialog = new ProgressDialog(AddSaleActivity.this);
        imgOne = findViewById(R.id.imgOne);
        imgTwo = findViewById(R.id.imgTwo);
        imgThree = findViewById(R.id.imgThree);

        lineOne = findViewById(R.id.lineOne);
        lineTwo = findViewById(R.id.lineTwo);

        saleCustomerLayout = findViewById(R.id.saleCustomerLayout);
        saleProductLayout = findViewById(R.id.saleProductLayout);
        saleConfirmLayout = findViewById(R.id.saleConfirmLayout);
        saleProductListLayout = findViewById(R.id.saleProductListLayout);

        inputCustomerName = findViewById(R.id.inputCustomerName);
        inputCustomerPhone = findViewById(R.id.inputCustomerPhone);
        inputCustomerAddress = findViewById(R.id.inputCustomerAddress);

        btnSearchProduct = findViewById(R.id.btnSearchProduct);
        btnScanProduct = findViewById(R.id.btnScanProduct);

        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtCustomerPhone = findViewById(R.id.txtCustomerPhone);
        txtCustomerAddress = findViewById(R.id.txtCustomerAddress);

        cartListView = findViewById(R.id.cartListView);

        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        adapter = new CartAdapter(getApplicationContext(), cartProductList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartListView.setLayoutManager(layoutManager);
        cartListView.setAdapter(adapter);

        //CLICK LISTENER
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gather Customer Information
                if (counter == 1) {
                    customerName = inputCustomerName.getText().toString();
                    customerPhone = inputCustomerPhone.getText().toString();
                    customerAddress = inputCustomerAddress.getText().toString();

                    if (customerName.isEmpty() || customerPhone.isEmpty() || customerAddress.isEmpty()) {
                        Toasty.warning(getApplicationContext(), "Field can't be empty.!", Toast.LENGTH_SHORT, true).show();
                    } else if (customerPhone.length() < 11) {
                        Toasty.warning(getApplicationContext(), "Enter valid phone number.!", Toast.LENGTH_SHORT, true).show();
                    } else {
                        inputCustomerName.setText(customerName);
                        inputCustomerPhone.setText(customerPhone);
                        inputCustomerAddress.setText(customerAddress);

                        customer.setCustomerName(customerName);
                        customer.setPhoneNo(customerPhone);
                        customer.setAddress(customerAddress);
                        counter = 2;
                        saleProductView();
                    }

                    //Gather Product Information
                } else if (counter == 2) {
                    txtCustomerName.setText(customerName);
                    txtCustomerPhone.setText(customerPhone);
                    txtCustomerAddress.setText(customerAddress);
                    counter = 3;
                    saleConfirmView();
                } else if (counter == 3) {
                    performSale();
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                }

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter == 3) {
                    saleProductView();

                    lineTwo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    imgThree.setBackgroundResource(R.drawable.ic_three);
                    btnPrev.setImageResource(R.drawable.ic_prev);
                    btnNext.setImageResource(R.drawable.ic_next);

                    counter = 2;
                } else if (counter == 2) {
                    saleCustomerView();

                    lineOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    imgTwo.setBackgroundResource(R.drawable.ic_two);
                    btnPrev.setImageResource(R.drawable.ic_prev);
                    btnNext.setImageResource(R.drawable.ic_next);

                    counter = 1;
                }
            }
        });

        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> searchProductList = new ArrayList<>();
                searchProductList = getProductList();
                new SimpleSearchDialogCompat<>(AddSaleActivity.this, "Search",
                        "Enter Product Name", null, searchProductList,
                        new SearchResultListener<Product>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog, final Product product, int i) {
                                Integer quantity = 1;
                                Boolean isNewItem = false;

                                if (cartProductList.isEmpty()) {
                                    cartProductList.add(new CartProduct(product, quantity));
                                    adapter.notifyItemChanged(0);
                                } else {
                                    //TODO:Update Quantity if exists into list else add new
                                    for (int j = 0; j < cartProductList.size(); j++) {
                                        CartProduct cartProduct = cartProductList.get(j);
                                        if (cartProduct.getProduct().getProductId().equals(product.getProductId())) {
                                            cartProductList.get(j).setQuantity(cartProductList.get(j).getQuantity() + 1);
                                            adapter.notifyItemChanged(j);
                                            isNewItem = false;
                                            break;
                                        } else {
                                            isNewItem = true;
                                        }
                                    }
                                }

                                if (isNewItem) {
                                    cartProductList.add(new CartProduct(product, quantity));
                                    adapter.notifyItemInserted(cartProductList.size() + 1);
                                }

                                adapter.notifyDataSetChanged();
                                Toasty.success(getApplicationContext(), product.getTitle(), Toast.LENGTH_SHORT, true).show();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void saleCustomerView() {
        saleCustomerLayout.setVisibility(View.VISIBLE);
        saleProductLayout.setVisibility(View.GONE);
        saleConfirmLayout.setVisibility(View.GONE);
        saleProductListLayout.setVisibility(View.GONE);
        btnPrev.setVisibility(View.GONE);

        imgOne.setBackgroundResource(R.drawable.ic_one_red);
        btnNext.setImageResource(R.drawable.ic_next);
    }

    private void saleProductView() {
        saleCustomerLayout.setVisibility(View.GONE);
        saleProductLayout.setVisibility(View.VISIBLE);
        saleConfirmLayout.setVisibility(View.GONE);
        saleProductListLayout.setVisibility(View.VISIBLE);
        btnPrev.setVisibility(View.VISIBLE);

        lineOne.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imgTwo.setBackgroundResource(R.drawable.ic_two_red);
        btnNext.setImageResource(R.drawable.ic_next);
    }

    private void saleConfirmView() {

        saleCustomerLayout.setVisibility(View.GONE);
        saleProductLayout.setVisibility(View.GONE);
        saleConfirmLayout.setVisibility(View.VISIBLE);
        saleProductListLayout.setVisibility(View.VISIBLE);
        btnPrev.setVisibility(View.VISIBLE);

        lineTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imgThree.setBackgroundResource(R.drawable.ic_three_red);

        btnNext.setImageResource(R.drawable.ic_done);
    }

    private ArrayList<Product> getProductList() {

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Stock>> getAvailableStockList = apiService.getAvailableStockList(0);

        getAvailableStockList.enqueue(new Callback<List<Stock>>() {

            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                products.clear();
                for (Stock stock : response.body()) {
                    stockList.add(stock);
                    products.add(stock.getProduct());
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Can't fetch any product!!!", Toast.LENGTH_SHORT, true).show();
            }
        });

        return products;
    }

    private void performSale() {

        apiService = ApiClient.getClient().create(ApiService.class);

        //Insert into Customer
        apiService.createCustomer(customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                response.body();
                if (response.isSuccessful()) {
                    createQuotation(response.body());
                    //Toasty.success(getApplicationContext(), "Customer Added.!", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.hide();
            }
        });

    }

    //Insert into Quotation
    private void createQuotation(Customer customer) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Quotation quotation = new Quotation();
        quotation.setCustomer(customer);
        quotation.setOrderDate(format.format(Calendar.getInstance().getTime()));
        quotation.setOrderStatus(OrderStatus.PENDING.getValue());

        apiService.createQuotation(quotation).enqueue(new Callback<Quotation>() {
            @Override
            public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                if (response.isSuccessful()) {
                    createQuotationList(response.body());
                    createSale(response.body());
                    //Toasty.success(getApplicationContext(), "Quotation Added.!", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Quotation> call, Throwable t) {

            }
        });
    }

    // Insert into QuotationList
    private void createQuotationList(Quotation quotation) {
        QuotationList quotationList = new QuotationList();

        for (int i = 0; i < cartProductList.size(); i++) {
            Integer quantity = cartProductList.get(i).getQuantity();

            quotationList.setProduct(cartProductList.get(i).getProduct());
            quotationList.setQuantity(quantity);
            quotationList.setQuotation(quotation);
            quotationList.setTotalPrice(cartProductList.get(i).getProduct().getPrice() * quantity);

            apiService.createQuotationList(quotationList).enqueue(new Callback<QuotationList>() {
                @Override
                public void onResponse(Call<QuotationList> call, Response<QuotationList> response) {
                    if (response.isSuccessful()) {
                        //Toasty.success(getApplicationContext(), "Quotation List Created.!", Toast.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void onFailure(Call<QuotationList> call, Throwable t) {

                }
            });
        }
    }

    //Insert into Sale
    private void createSale(Quotation quotation) {
        Sale sale = new Sale();
        sale.setQuotation(quotation);

        apiService.createSale(sale).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if (response.isSuccessful()) {
                    updateStock();
                    progressDialog.hide();
                    Toasty.success(getApplicationContext(), "Sale Successful.!", Toast.LENGTH_SHORT, true).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {

            }
        });
    }

    //Update Stock
    private void updateStock() {
        for (CartProduct cartProduct : cartProductList) {
            Stock stock = new Stock();
            stock.setProduct(cartProduct.getProduct());
            stock.setQuantity(cartProduct.getQuantity());

            apiService.updateStockByProductId(stock.getProduct().getProductId(), stock.getQuantity()).enqueue(new Callback<Stock>() {
                @Override
                public void onResponse(Call<Stock> call, Response<Stock> response) {
                    if (response.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<Stock> call, Throwable t) {

                }
            });
        }
    }

}
