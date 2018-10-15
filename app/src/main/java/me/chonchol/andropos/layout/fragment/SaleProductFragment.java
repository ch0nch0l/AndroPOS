package me.chonchol.andropos.layout.fragment;

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

import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Product;

/**
 * Created by mehedi.chonchol on 14-Oct-18.
 */

public class SaleProductFragment extends Fragment implements BlockingStep {

    private Button btnSearchProduct, btnScanProduct;

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

    private void initializeView(View view){
        btnSearchProduct = view.findViewById(R.id.btnSearchProduct);
        btnScanProduct = view.findViewById(R.id.btnScanProduct);

        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat<>(getActivity(), "Search",
                        "Enter Product Name", null, createSampleData(),
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

    private ArrayList<Product> createSampleData(){
        ArrayList<Product> items = new ArrayList<>();
        items.add(new Product("First item"));
        items.add(new Product("Second item"));
        items.add(new Product("Third item"));
        items.add(new Product("The ultimate item"));
        items.add(new Product("Last item"));
        items.add(new Product("Lorem ipsum"));
        items.add(new Product("Dolor sit"));
        items.add(new Product("Some random word"));
        items.add(new Product("guess who's back"));
        return items;
    }
}
