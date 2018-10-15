package me.chonchol.andropos.layout.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import me.chonchol.andropos.R;
import me.chonchol.andropos.interfaces.IDataManager;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;

/**
 * Created by mehedi.chonchol on 14-Oct-18.
 */

public class SaleCustomerFragment extends Fragment implements BlockingStep {

    private EditText txtCustomerName, txtCustomerPhone, txtCustomerAddress;
    private Customer customer;

    private IDataManager dataManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataManager = (IDataManager) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sale_customer_fragment, container, false);
        initializeView(view);
        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //you can do anythings you want

                customer = new Customer();
                customer.setCustomerName(txtCustomerName.getText().toString());
                customer.setPhoneNo(txtCustomerPhone.getText().toString());
                customer.setAddress(txtCustomerAddress.getText().toString());

                dataManager.customerData(customer);

                callback.goToNextStep();
            }
        }, 0L);// delay open another fragment,
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

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

    private void initializeView(View view) {
        txtCustomerName = view.findViewById(R.id.txtCustomerName);
        txtCustomerPhone = view.findViewById(R.id.txtCustomerPhone);
        txtCustomerAddress = view.findViewById(R.id.txtCustomerAddress);

    }
}
