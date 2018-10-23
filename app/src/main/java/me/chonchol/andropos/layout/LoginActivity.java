package me.chonchol.andropos.layout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.helper.EncryptorHelper;
import me.chonchol.andropos.model.User;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.LoginSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUserName, inputUserPassword;
    private TextView txtRegister, txtForgotPassword;
    private FloatingActionButton btnLogin;
    private LinearLayout layoutLogin;
    private String userName, userPassword, dyctyptedPass;

    private User user = new User();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();

        if (LoginSharedPreference.getLoggedInStatus(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            layoutLogin.setVisibility(View.VISIBLE);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = inputUserName.getText().toString();
                userPassword = inputUserPassword.getText().toString();

                userLogin(userName, userPassword);
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);

            }
        });

    }

    private void userLogin(String userName, String userPassword) {
        apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getUserByUserName(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();

                    if (user.getActive()){
                        try {
                            dyctyptedPass = EncryptorHelper.decrypt(user.getPassword());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (dyctyptedPass.equals(userPassword)){
                            LoginSharedPreference.setLoggedIn(getApplicationContext(), true);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), "Credentials are not Valid.", Toast.LENGTH_SHORT, true).show();
                        }
                    } else {

                        Toasty.info(getApplicationContext(), "User is not active. Contact with developer.!", Toast.LENGTH_LONG, true).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Request Failed!", Toast.LENGTH_SHORT, true).show();
            }
        });

    }


    private void initializeView(){
        inputUserName = findViewById(R.id.inputUserName);
        inputUserPassword = findViewById(R.id.inputUserPassword);
        btnLogin = findViewById(R.id.btnLogin);
        layoutLogin = findViewById(R.id.layoutLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
    }
}
