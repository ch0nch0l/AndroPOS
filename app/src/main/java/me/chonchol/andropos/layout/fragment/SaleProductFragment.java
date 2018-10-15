package me.chonchol.andropos.layout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import me.chonchol.andropos.R;
import me.chonchol.andropos.interfaces.IDataManager;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mehedi.chonchol on 14-Oct-18.
 */

public class SaleProductFragment extends Fragment implements BlockingStep {

    private Button btnSearchProduct, btnScanProduct;

    private IDataManager dataManager;
    private ApiService apiService;
    private ArrayList<Product> products;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataManager = (IDataManager) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sale_product_fragment, container, false);
        initializeView(view);
        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //you can do anythings you want
                Customer customer = dataManager.getCustomerData();
                Toasty.success(getActivity(), customer.getCustomerName(), Toast.LENGTH_SHORT, true).show();
                callback.goToNextStep();
            }
        }, 0L);// delay open another fragment,
    }
    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }
    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
    @Override
    public VerificationError verifyStep() {
        return null;
    }
    @Override
    public void onSelected() {
    }
    @Override
    public void onError(@NonNull VerificationError error) {
    }

    public void initializeView(View view){
        btnSearchProduct = view.findViewById(R.id.btnSearchProduct);
        btnScanProduct = view.findViewById(R.id.btnScanProduct);

        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat<>(getActivity(), "Search",
                        "Enter Product Name", null, getProductList(),
                        new SearchResultListener<Product>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Product product, int i) {
                        Toasty.success(getActivity(), product.getTitle(), Toast.LENGTH_SHORT, true).show();
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    private ArrayList<Product> getProductList(){

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Stock>> getAvailableStockList = apiService.getAvailableStockList();

        getAvailableStockList.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                response.body();
                List<Stock> stockList = new ArrayList<>();
                for (Stock stock: response.body()){
                    products = new ArrayList<>();
                    products.add(stock.getProduct());
                    stockList.add(stock);
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });

        return products;
    }
}
