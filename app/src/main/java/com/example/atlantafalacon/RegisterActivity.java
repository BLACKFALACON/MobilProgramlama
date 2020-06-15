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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    private void register() {
        edtEmail = findViewById(R.id.ac_register_ed_email);
        edtPassword = findViewById(R.id.ac_register_ed_password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserService users = retrofit.create(UserService.class);
        String email = edtEmail.getText().toString();
        String sifre = edtPassword.getText().toString();
        Call<UserRegisterResponse> call = users.registerUser(email,sifre);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"Başarıyla kayıt oldunuz.",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String resp = null;
                    String errorMessage = "";
                    try {
                        resp = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(resp);
                        errorMessage = jsonObject.getString("error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Bir şeyler yanlış gitti.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        btnRegister = findViewById(R.id.ac_register_btn_kayit);
    }

    public void girisYap(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}
