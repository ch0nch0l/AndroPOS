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

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.StepperAdapter;
import me.chonchol.andropos.interfaces.IDataManager;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;

public class SaleActivity extends AppCompatActivity implements StepperLayout.StepperListener, IDataManager {

    private StepperLayout stepperLayout;

    private String data;
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final String DATA = "data";

    private Customer customer;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        if (savedInstanceState != null) {
            savedInstanceState.getInt("position");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
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
    public void cartProducts(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public List<Product> getCartProductList() {
        return productList;
    }
}
