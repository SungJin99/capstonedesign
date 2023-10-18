package com.mokpo.capstonedesign;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.IngredientAddRequest;
import com.mokpo.capstonedesign.retrofit2.IngredientAddItem;
import com.mokpo.capstonedesign.retrofit2.IngredientAddResponse;
import com.mokpo.capstonedesign.retrofit2.IngredientResponse;
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientAdapter;
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientManagementFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientAddActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int BARCODE_SCAN_REQUEST_CODE = 200;

    public static EditText nameEditText;
    private EditText expiryEditText;
    private Button cameraButton;
    private Button increaseButton;
    private Button decreaseButton;
    private TextView quantityTextView;
    private EditText memoEditText;
    private Button registerButton;

    private int mYear, mMonth, mDay;
    private TextView barcodeTextView;
    private int quantity = 0;

    private String name;
    private String memo;
    private String expiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_add);

        //barcodeTextView = findViewById(R.id.barcodeEditText);
        nameEditText = findViewById(R.id.nameEditText);
        EditText expiryEditText = findViewById(R.id.expiryEditText);
        cameraButton = findViewById(R.id.cameraButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        quantityTextView = findViewById(R.id.quantityTextView);
        memoEditText = findViewById(R.id.memoEditText);
        registerButton = findViewById(R.id.registerButton);
        expiryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DatePickerDialog를 띄우기 위한 캘린더 객체 생성
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // DatePickerDialog 생성 및 설정
                DatePickerDialog datePickerDialog = new DatePickerDialog(IngredientAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        expiryEditText.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(IngredientAddActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IngredientAddActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    openCamera();
                }
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quantityTextView.setText(String.valueOf(quantity));
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    quantityTextView.setText(String.valueOf(quantity));
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String expiry = expiryEditText.getText().toString();
                int quantity = Integer.parseInt(quantityTextView.getText().toString());
                String memo = memoEditText.getText().toString();

                IngredientAddRequest request = createFoodList(name, memo, quantity, expiry);
                sendFoodList(request);
            }
        });
    }
    public IngredientAddRequest createFoodList(String name, String memo, int quantity, String expiry) {
        IngredientAddRequest request = new IngredientAddRequest();
        List<IngredientAddRequest.FoodItem> foodlist = new ArrayList<>();
        foodlist.add(new IngredientAddRequest.FoodItem(name, memo, quantity, expiry));
        request.setFoodlist(foodlist);
        return request;
    }

    private void sendFoodList(IngredientAddRequest addRequest) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();

        Call<IngredientAddResponse> call = apiService.getInsertfood("Bearer " + accessToken, addRequest);
        call.enqueue(new Callback<IngredientAddResponse>() {
            @Override
            public void onResponse(Call<IngredientAddResponse> call, retrofit2.Response<IngredientAddResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(IngredientAddActivity.this, "식재료가 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(IngredientAddActivity.this, "식재료 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IngredientAddResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(IngredientAddActivity.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(IngredientAddActivity.this, CameraActivity.class);
        startActivityForResult(intent, BARCODE_SCAN_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BARCODE_SCAN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String scanResult = data.getStringExtra("SCAN_RESULT");
                // 스캔 결과 처리
                // 예를 들어, TextView에 스캔 결과 출력
                barcodeTextView.setText("스캔 결과: " + scanResult);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // 스캔 취소 처리
            } else {
                // 기타 처리
            }
        }
    }

}