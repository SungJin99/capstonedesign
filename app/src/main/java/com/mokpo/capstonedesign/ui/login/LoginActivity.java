package com.mokpo.capstonedesign.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mokpo.capstonedesign.IngredientManagementActivity;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.SignUpActivity;
import com.mokpo.capstonedesign.databinding.ActivityLoginBinding;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.LoginRequest;
import com.mokpo.capstonedesign.retrofit2.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        final EditText useridEditText = binding.userid;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        signUpButton = findViewById(R.id.signup);
        final ProgressBar loadingProgressBar = binding.loading;


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String userid = useridEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                boolean enableButton = !userid.isEmpty() && !password.isEmpty();
                loginButton.setEnabled(enableButton);
            }
        };
        useridEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = useridEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateInputs(userid, password)) {
                    loginUser(userid, password);
                } else {
                    Toast.makeText(LoginActivity.this, "모든 입력란을 채워주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                // 회원가입 버튼 클릭 시 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loginUser(String userid, String password) {
        ApiService apiService = ApiClient.getApiService();
        Call<LoginResponse> call = apiService.login(new LoginRequest(userid, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    String refreshToken = response.body().getRefreshToken();
                    saveJwtToken(getApplicationContext(), accessToken);
                    saveRefreshToken(getApplicationContext(), refreshToken);
                    System.out.println(accessToken);
                    System.out.println(refreshToken);
                    Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, IngredientManagementActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "로그인 실패! 아이디나 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "로그인 실패! 아이디나 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateInputs(String email, String password) {
        return !(email.isEmpty() || password.isEmpty());
    }
    private void saveJwtToken(Context context, String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
    }
    private void saveRefreshToken(Context context, String refreshToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    private String getJwtToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("jwt_token", null);
    }

}
