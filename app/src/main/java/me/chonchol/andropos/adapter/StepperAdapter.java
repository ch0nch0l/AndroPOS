package me.chonchol.andropos.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import me.chonchol.andropos.layout.fragment.StepperCustomerFragment;
import me.chonchol.andropos.layout.fragment.StepperProductFragment;

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
                final StepperCustomerFragment stepOne = new StepperCustomerFragment();
                Bundle bundleOne = new Bundle();
                bundleOne.putInt(CURRENT_STEP_POSITION_KEY, position);
                stepOne.setArguments(bundleOne);
                return stepOne;
            case 1:
                final StepperProductFragment stepTwo = new StepperProductFragment();
                Bundle bundleTwo = new Bundle();
                bundleTwo.putInt(CURRENT_STEP_POSITION_KEY, position);
                stepTwo.setArguments(bundleTwo);
                return stepTwo;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        switch (position){
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 1") //can be a CharSequence instead
                        .create();
            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 2") //can be a CharSequence instead
                        .create();
        }
        return null;
    }
}
