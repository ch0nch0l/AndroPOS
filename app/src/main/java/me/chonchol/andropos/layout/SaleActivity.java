package me.chonchol.andropos.layout;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.StepperAdapter;
import me.chonchol.andropos.enums.OrderStatus;
import me.chonchol.andropos.interfaces.IDataManager;
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

public class SaleActivity extends AppCompatActivity implements StepperLayout.StepperListener, IDataManager {

    private StepperLayout stepperLayout;
    private AVLoadingIndicatorView progress;

    private String data;
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final String DATA = "data";

    private Customer customer;
    private List<CartProduct> productList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        if (savedInstanceState != null) {
            savedInstanceState.getInt("position");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        StepperAdapter adapter = new StepperAdapter(getSupportFragmentManager(), getApplicationContext());

        stepperLayout = findViewById(R.id.stepperLayout);
        stepperLayout.setAdapter(adapter);
        stepperLayout.setListener(this);

    }

    @Override
    public void onCompleted(View completeButton) {

        progress.show();
        performSale();
        //Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    private void performSale() {

        apiService = ApiClient.getClient().create(ApiService.class);

        //Insert into Customer
        apiService.createCustomer(getCustomerData()).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    createQuotation(response.body());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

    }

    //Insert into Quotation
    private void createQuotation(Customer customer) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Quotation quotation = new Quotation();
        quotation.setCustomer(customer);
        quotation.setOrderDate(Calendar.getInstance().getTime());
        quotation.setOrderStatus(OrderStatus.PENDING.getValue());

        apiService.createQuotation(quotation).enqueue(new Callback<Quotation>() {
            @Override
            public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                if (response.isSuccessful()) {
                    createQuotationList(response.body());
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
        for (int i = 0; i < getCartProductList().size(); i++) {
            Product product = getCartProductList().get(i).getProduct();
            Integer quantity = getCartProductList().get(i).getQuantity();

            quotationList.setProduct(product);
            quotationList.setQuantity(quantity);
            quotationList.setQuotation(quotation);
            quotationList.setTotalPrice(product.getPrice() * quantity);

            apiService.createQuotationList(quotationList).enqueue(new Callback<QuotationList>() {
                @Override
                public void onResponse(Call<QuotationList> call, Response<QuotationList> response) {
                    if (response.isSuccessful()) {
                        createSale(quotation);

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
                }
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {

            }
        });
    }

    //Update Stock
    private void updateStock() {
        for (CartProduct cartProduct : getCartProductList()) {
            Stock stock = new Stock();
            stock.setProduct(cartProduct.getProduct());
            stock.setQuantity(cartProduct.getQuantity());

            apiService.updateStock(stock.getProduct().getProductId()).enqueue(new Callback<Stock>() {
                @Override
                public void onResponse(Call<Stock> call, Response<Stock> response) {
                    if (response.isSuccessful()) {
                        progress.hide();
                        Toasty.success(getApplicationContext(), "Sale successful", Toast.LENGTH_SHORT, true).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Stock> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        switch (newStepPosition) {
            case 0:
                Toasty.info(getApplicationContext(), "Enter Customer Information.!", Toast.LENGTH_SHORT, true).show();
            case 1:
                Toasty.info(getApplicationContext(), "Add products to be sold.!", Toast.LENGTH_SHORT, true).show();
            case 2:
                Toasty.info(getApplicationContext(), "Confirm Sale", Toast.LENGTH_SHORT, true).show();
            default:
                break;
        }
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.getCurrentStepPosition());
//        outState.putString(DATA, this.data);
//        outState.putSerializable("CUSTOMER", this.customer);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void customerData(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Customer getCustomerData() {
        return customer;
    }

    @Override
    public void cartProducts(List<CartProduct> productList) {
        this.productList = productList;
    }

    @Override
    public List<CartProduct> getCartProductList() {
        return productList;
    }
}
