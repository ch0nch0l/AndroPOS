package me.chonchol.andropos.layout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.helper.EncryptorHelper;
import me.chonchol.andropos.helper.UrlHelper;
import me.chonchol.andropos.helper.ViewDialog;
import me.chonchol.andropos.model.Client;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.rest.ValidApiClient;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import me.chonchol.andropos.sharedpref.LoginSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientLoginActivity extends AppCompatActivity {

    private EditText inputClientUrl, inputClientPort, inputClientDBName, inputClientPin;
    private FloatingActionButton btnLogin;
    private LinearLayout layoutClientLogin;

    private String clientUrl, clientPort, clientDBName, clientPin, encryptedPin;
    private ApiService apiService;
    private boolean isValidClient, isValidPin;

    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        initializeView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientUrl = inputClientUrl.getText().toString();
                clientPort = inputClientPort.getText().toString();
                clientDBName = inputClientDBName.getText().toString();
                clientPin = inputClientPin.getText().toString();

                viewDialog = new ViewDialog(ClientLoginActivity.this);
                viewDialog.show();

                if (isValidClient()) {
                    UrlHelper urlHelper = new UrlHelper(clientUrl, clientPort);
                    ClientSharedPreference.setLoggedIn(getApplicationContext(), true);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void initializeView() {
        inputClientUrl = findViewById(R.id.inputClientUrl);
        inputClientPort = findViewById(R.id.inputClientPort);
        inputClientDBName = findViewById(R.id.inputClientDBName);
        inputClientPin = findViewById(R.id.inputClientPin);
        btnLogin = findViewById(R.id.btnLogin);
        layoutClientLogin = findViewById(R.id.layoutClientLogin);

        if (ClientSharedPreference.getLoggedInStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            layoutClientLogin.setVisibility(View.VISIBLE);
        }
    }

    private boolean isValidClient() {
        isValidClient = false;
        apiService = ValidApiClient.getClient().create(ApiService.class);
        apiService.getClientByName(clientDBName).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    isValidClient = true;
                    try {
                        encryptedPin = EncryptorHelper.encrypt(clientPin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response.body().getClientPin() == encryptedPin) {
                        viewDialog.hide();
                        Toasty.success(getApplicationContext(), "Welcome to AndroPOS.!", Toast.LENGTH_SHORT, true).show();

                    } else {
                        Toasty.error(getApplicationContext(), "User PIN is not valid.!", Toast.LENGTH_LONG, true).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                viewDialog.hide();
                Toasty.error(getApplicationContext(), "You are not a registered user.!", Toast.LENGTH_LONG, true).show();
            }
        });
        return isValidClient;
    }
}
