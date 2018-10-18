package me.chonchol.andropos.layout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText inputCatName;
    private CheckBox chkIsCatActive;
    private Category category;
    private AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new Category();
                String catName = inputCatName.getText().toString();

                if (catName.isEmpty()){
                    Toasty.warning(getApplicationContext(), "Category Name can't be empty!", Toast.LENGTH_SHORT, true).show();
                } else {

                    category.setCatName(inputCatName.getText().toString());
                    category.setActive(chkIsCatActive.isChecked());

                    saveCategory(category);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void initializeView(){
        inputCatName = findViewById(R.id.inputCatName);
        chkIsCatActive = findViewById(R.id.catIsActive);
        progress = findViewById(R.id.progress);
    }

    private void saveCategory(Category category) {

        progress.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.saveCategory(category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()){
                    progress.hide();
                    Toasty.success(getApplicationContext(), "Category added successfully!", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progress.hide();
                Toasty.error(getApplicationContext(), "Category addition failed!!!", Toast.LENGTH_SHORT, true).show();
            }
        });

    }

}
