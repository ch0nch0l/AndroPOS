package me.chonchol.andropos.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import me.chonchol.andropos.layout.fragment.SaleConfirmFragment;
import me.chonchol.andropos.layout.fragment.SaleCustomerFragment;
import me.chonchol.andropos.layout.fragment.SaleProductFragment;

/**
 * Created by mehedi.chonchol on 14-Oct-18.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position){
            case 0:
                final SaleCustomerFragment stepOne = new SaleCustomerFragment();
                Bundle bundleOne = new Bundle();
                bundleOne.putInt(CURRENT_STEP_POSITION_KEY, position);
                stepOne.setArguments(bundleOne);
                return stepOne;
            case 1:
                final SaleProductFragment stepTwo = new SaleProductFragment();
                Bundle bundleTwo = new Bundle();
                bundleTwo.putInt(CURRENT_STEP_POSITION_KEY, position);
                stepTwo.setArguments(bundleTwo);
                return stepTwo;
            case 2:
                final SaleConfirmFragment stepThree = new SaleConfirmFragment();
                Bundle bundleThree = new Bundle();
                bundleThree.putInt(CURRENT_STEP_POSITION_KEY, position);
                stepThree.setArguments(bundleThree);
                return stepThree;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        switch (position){
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("Customer") //can be a CharSequence instead
                        .create();
            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("Product") //can be a CharSequence instead
                        .create();
            case 2:
                return new StepViewModel.Builder(context)
                        .setTitle("Confirm") //can be a CharSequence instead
                        .create();
        }
        return null;
    }
}
