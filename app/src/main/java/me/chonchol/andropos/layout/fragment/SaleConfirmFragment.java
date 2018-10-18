package me.chonchol.andropos.layout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.CartAdapter;
import me.chonchol.andropos.interfaces.IDataManager;
import me.chonchol.andropos.model.CartProduct;
import me.chonchol.andropos.model.Customer;

/**
 * Created by mehedi.chonchol on 14-Oct-18.
 */

public class SaleConfirmFragment extends Fragment implements BlockingStep {

    private TextView txtCustomerName, txtCustomerPhone, txtCustomerAddress;
    private BetterSpinner dropdownPaymentType;
    private RecyclerView cartListView;
    private IDataManager dataManager;

    private Customer customer = new Customer();
    private List<CartProduct> cartProductList = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataManager = (IDataManager) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sale_confirm_fragment, container, false);
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
        callback.complete();
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
        txtCustomerName = view.findViewById(R.id.txtCustomerName);
        txtCustomerPhone = view.findViewById(R.id.txtCustomerPhone);
        txtCustomerAddress = view.findViewById(R.id.txtCustomerAddress);
        dropdownPaymentType = view.findViewById(R.id.dropdownPaymentType);
        cartListView = view.findViewById(R.id.cartListView);

        customer = dataManager.getCustomerData();
        cartProductList = dataManager.getCartProductList();

        txtCustomerName.setText(customer.getCustomerName().toString());
        txtCustomerPhone.setText(customer.getPhoneNo().toString());
        txtCustomerAddress.setText(customer.getAddress().toString());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        cartListView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(getContext(), cartProductList);
        cartListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
