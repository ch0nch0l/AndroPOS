package me.chonchol.andropos.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.enums.UserRole;
import me.chonchol.andropos.helper.EncryptorHelper;
import me.chonchol.andropos.model.User;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    private EditText inputUserName, inputUserPassword, inputConfirmPassword;
    private CheckBox chkIsActive;
    private FloatingActionButton btnAddUser;

    private User user = new User();
    private Boolean userExists = false;

    private ApiService apiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            initializeView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNewUser(User user) {
        userExists = checkIfUserExists(user);
        if (!userExists){
            saveNewUser(user);
        }
    }

    private void saveNewUser(User user) {
        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);

        apiService.createNewUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                    progressDialog.dismiss();
                    Toasty.success(getApplicationContext(), "New user created.! Contact with developer to active the user.!", Toast.LENGTH_SHORT, true).show();
                    finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.error(getApplicationContext(), "New user creation failed.! Contact with developer.!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private Boolean checkIfUserExists(User user) {

        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);

        apiService.getUserByUserName(user.getUserName()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getUserName() == user.getUserName())
                        userExists = true;
                    Toasty.error(getApplicationContext(), "User already exists with user name: "+ user.getUserName() + " .!", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return userExists;
    }


    private void initializeView() throws Exception {

        progressDialog = new ProgressDialog(AddUserActivity.this);
        inputUserName = findViewById(R.id.inputUserName);
        inputUserPassword = findViewById(R.id.inputUserPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        chkIsActive = findViewById(R.id.chkIsActive);
        btnAddUser = findViewById(R.id.btnAddUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(inputUserName.getText().toString());
                user.setActive(!chkIsActive.isChecked());
                user.setUserRole(UserRole.SALES_MAN.getValue());
                user.setActive(chkIsActive.isChecked());

                String inputPass = inputConfirmPassword.getText().toString();

                if (inputUserPassword.getText().length() < 6){
                    Toasty.warning(getApplicationContext(), "Password must be at least 6 characters.!", Toast.LENGTH_SHORT, true).show();
                } else if (!inputUserPassword.getText().toString().equals(inputPass)){
                    Toasty.warning(getApplicationContext(), "Confirm password not matched.!", Toast.LENGTH_SHORT, true).show();
                } else {

                    String dbPass = null;
                    try {
                        dbPass = EncryptorHelper.encrypt(inputPass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.setPassword(dbPass);

                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    createNewUser(user);
                }
            }
        });
    }

}
