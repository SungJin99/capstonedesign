package com.mokpo.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mokpo.capstonedesign.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText userIdEditText, usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private ExecutorService executorService;
    private Button checkIdButton;
    private Boolean isDuplicated;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userIdEditText = findViewById(R.id.userid_edittext);
        usernameEditText = findViewById(R.id.username_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        signUpButton = findViewById(R.id.sign_up_button);
        checkIdButton = findViewById(R.id.check_duplicate_button);

        executorService = Executors.newFixedThreadPool(1);
        checkIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdEditText.getText().toString();

                if (userId.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    executorService.execute(() -> {
                        try {
                            JSONObject postData = new JSONObject();
                            postData.put("userid", userId);

                            String result = checkDuplicate(postData.toString());
                            JSONObject jsonResponse = new JSONObject(result);
                            runOnUiThread(() -> {
                                try {
                                    String status = jsonResponse.getString("status");
                                    if (status.equals("success")) {
                                        isDuplicated = false;
                                        Toast.makeText(SignUpActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        isDuplicated = true;
                                        Toast.makeText(SignUpActivity.this, "중복된 아이디입니다. 다른 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (final Exception e) {
                                    Log.e("SignUpActivity", "Error: " + e.getMessage());
                                    Toast.makeText(SignUpActivity.this, "아이디 인증 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Log.e("SignUpActivity", "Error: " + e.getMessage());
                            Toast.makeText(SignUpActivity.this, "아이디 인증 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // 회원가입 정보 유효성 검사
                if (userId.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                } else if (!isValidUserId(userId)) {
                    Toast.makeText(SignUpActivity.this, "아이디에 특수 문자는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else if (isDuplicated == null) {
                    Toast.makeText(SignUpActivity.this, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (isDuplicated == true) {
                    Toast.makeText(SignUpActivity.this, "아이디가 중복되었습니다. 다른 아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 쓰레드풀 실행
                    executorService.execute(() -> {
                        try {
                            JSONObject postData = new JSONObject();
                            postData.put("userid", userId);
                            postData.put("username", username);
                            postData.put("email", email);
                            postData.put("password", password);

                            String result = signUp(postData.toString());
                            JSONObject jsonResponse = new JSONObject(result);

                            runOnUiThread(() -> {
                                try {
                                    String status = jsonResponse.getString("status");

                                    if (status.equals("success")) {
                                        Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "회원가입 실패: " + result, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(SignUpActivity.this, "회원가입 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (final Exception e) {
                            Log.e("SignUpActivity", "Error: " + e.getMessage());
                            runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "회원가입 실패" + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                }

            }
        });
    }
    private String checkDuplicate(String postData) {
        String result = "";
        String apiUrl = "http://20.214.138.61:8000/api/checkid/"; // Django 아이디 중복 확인 API URL을 입력하세요.

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                br.close();

                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Log.d("SignUpActivity", "이 ID는 사용 가능 합니다.");
                    } else {
                        Log.d("SignUpActivity", "이 ID는 중복 입니다.");
                    }
                } catch (JSONException e) {
                    Log.e("SignUpActivity", "JSONException 발생", e);
                }
            } else {
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        result += errorLine;
                    }
                    errorReader.close();
                } else {
                    result = "Error: " + responseCode;
                }
            }

            conn.disconnect();
        } catch (IOException e) {
            Log.e("SignUpActivity", "IOException 발생", e);
            result = "IOException 발생: " + e.getMessage();
        } catch (Exception e) {
            Log.e("SignUpActivity", "예외 발생", e);
            result = "예외 발생: " + e.getMessage();
        }

        return result;
    }
    private boolean isValidEmail(String email) {
// 이메일 유효성 검사에 사용될 정규표현식
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        // 주어진 이메일 주소가 정규표현식에 일치하는지 확인
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidUserId(String userId) {
// 아이디 유효성 검사에 사용될 정규표현식
        String userIdRegex = "^[a-zA-Z0-9]+$";// 주어진 아이디가 정규표현식에 일치하는지 확인
        return Pattern.matches(userIdRegex, userId);
    }

    private String signUp(String postData) {
        String result = "";
        String apiUrl = "http://20.214.138.61:8000/api/join/"; // Django 회원가입 API URL을 입력하세요.
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                br.close();

                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String status = jsonResponse.getString("status");

                    if (status.equals("success")) {
                        Log.d("SignUpActivity", "회원 가입 성공");
                    } else {
                        Log.d("SignUpActivity", "회원 가입 실패");
                    }
                } catch (JSONException e) {
                    Log.e("SignUpActivity", "JSONException 발생", e);
                }

            } else {
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        result += errorLine;
                    }
                    errorReader.close();
                } else {
                    result = "Error: " + responseCode;
                }
            }

            conn.disconnect();
        } catch (IOException e) {
            Log.e("SignUpActivity", "IOException 발생", e);
            result = "IOException 발생: " + e.getMessage();
        } catch (Exception e) {
            Log.e("SignUpActivity", "예외 발생", e);
            result = "예외 발생: " + e.getMessage();
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}