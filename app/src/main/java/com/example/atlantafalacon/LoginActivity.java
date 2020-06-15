package com.example.atlantafalacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atlantafalacon.utils.ApiUrl;
import com.example.atlantafalacon.utils.UserService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edEmail,edSifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        edEmail = findViewById(R.id.ac_login_edt_email);
        edSifre = findViewById(R.id.ac_login_edt_password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserService users = retrofit.create(UserService.class);
        String email = edEmail.getText().toString();
        String sifre = edSifre.getText().toString();
        Call<UserLoginResponse> call = users.loginUser(email,sifre);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        String resp = null;
                        String errorMessage = "";
                        try {
                            resp = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(resp);
                            errorMessage = jsonObject.getString("error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Başarıyla giriş yaptınız.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),UsersListActivity.class));

                    }
                }
                else {
                    String resp = null;
                    String errorMessage = "";
                    try {
                        resp = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(resp);
                        errorMessage = jsonObject.getString("error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Bir şeyler yanlış gitti.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void kayitOl(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void initializeViews() {
        btnLogin = findViewById(R.id.ac_login_btn_giris);
    }
}
