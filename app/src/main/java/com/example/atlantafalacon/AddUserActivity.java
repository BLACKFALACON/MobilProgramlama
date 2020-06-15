package com.example.atlantafalacon;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddUserActivity extends AppCompatActivity {

    private EditText edtFirstName;
    private EditText edtLastName;
    private Button btnOlustur;
    private Button btnIptal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initializeViews();

        btnOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);
            }
        });

    }

    private void addUser(View v) {
        String name = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserService users = retrofit.create(UserService.class);

        Call<UserCreateResponse> call = users.addUser(name,lastName);
        call.enqueue(new Callback<UserCreateResponse>() {
            @Override
            public void onResponse(Call<UserCreateResponse> call, Response<UserCreateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddUserActivity.this,"Kullanıcı başarıyla eklendi.",Toast.LENGTH_SHORT).show();
                    finish();
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
                    Toast.makeText(AddUserActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCreateResponse> call, Throwable t) {
                Toast.makeText(AddUserActivity.this,"Bir şeyler yanlış gitti.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        edtFirstName = findViewById(R.id.ac_add_user_firstName);
        edtLastName = findViewById(R.id.ac_add_user_lastName);
        btnOlustur = findViewById(R.id.ac_add_user_btnOlustur);
        btnIptal = findViewById(R.id.ac_add_user_btnIptal);
    }
}
