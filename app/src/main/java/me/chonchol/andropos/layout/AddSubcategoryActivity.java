package me.chonchol.andropos.layout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Subcategory;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class AddSubcategoryActivity extends AppCompatActivity {

    private EditText inputSabcatName;
    private CheckBox subcatIsActive;
    private FloatingActionButton btnAddSubcategory;

    private Subcategory subcategory;
    private Category category;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subcategory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        btnAddSubcategory = (FloatingActionButton) findViewById(R.id.btnAddSubcategory);
        btnAddSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subcatName = inputSabcatName.getText().toString();
                if (!subcatName.isEmpty()){
                    subcategory.setSubcatName(subcatName);
                    subcategory.setActive(subcatIsActive.isChecked());
                    saveSubcategory(subcategory);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toasty.warning(getApplicationContext(), "Subcategory Name can't be empty!", Toast.LENGTH_SHORT, true).show();
                }

            }
        });
    }

    private void initializeView(){
        inputSabcatName = findViewById(R.id.inputSubcatName);
        subcatIsActive = findViewById(R.id.subcatIsActive);

        subcategory = new Subcategory();

        apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCategoryById(getIntent().getIntExtra("CAT_ID", 0)).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                category = response.body();
                subcategory.setCategory(category);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    private void saveSubcategory(Subcategory subcategory) {
        apiService.saveSubcategory(subcategory).enqueue(new Callback<Subcategory>() {
            @Override
            public void onResponse(Call<Subcategory> call, Response<Subcategory> response) {
                if (response.isSuccessful()){
                    Toasty.success(getApplicationContext(), "Subcategory added successfully!", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Subcategory> call, Throwable t) {

            }
        });
    }
}
