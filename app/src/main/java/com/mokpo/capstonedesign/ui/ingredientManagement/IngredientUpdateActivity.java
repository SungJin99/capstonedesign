package com.mokpo.capstonedesign.ui.ingredientManagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.retrofit2.DeleteIngredients;
import com.mokpo.capstonedesign.retrofit2.IngredientDeleteResponse;
import com.mokpo.capstonedesign.retrofit2.IngredientResponse;
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientManagementFragment;
import com.mokpo.capstonedesign.CameraActivity;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.IngredientAddRequest;
import com.mokpo.capstonedesign.retrofit2.IngredientAddResponse;
import com.mokpo.capstonedesign.retrofit2.IngredientUpdateRequest;
import com.mokpo.capstonedesign.retrofit2.IngredientUpdateResponse;
import com.mokpo.capstonedesign.ui.recipeRecommend.RecipeAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientUpdateActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int BARCODE_SCAN_REQUEST_CODE = 200;
    private IngredientAdapter adapter;
    public static EditText nameEditText;
    private EditText expiryEditText;
    private Button cameraButton;
    private Button increaseButton;
    private Button decreaseButton;

    private TextView idEditText;
    private TextView quantityTextView;
    private EditText memoEditText;
    private Button updateButton;

    private Button deleteButton;

    private int mYear, mMonth, mDay;
    private TextView barcodeTextView;
    private int quantity = 0;

    private int id;
    private String name;
    private String memo;
    private String expiry;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 정보 불러오는 작업 테스트
        setContentView(R.layout.activity_ingredient_update);

        idEditText = findViewById(R.id.idEditText);
        //barcodeTextView = findViewById(R.id.barcodeEditText);
        nameEditText = findViewById(R.id.nameEditText);
        EditText expiryEditText = findViewById(R.id.expiryEditText);
        cameraButton = findViewById(R.id.cameraButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        quantityTextView = findViewById(R.id.quantityTextView);
        memoEditText = findViewById(R.id.memoEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        expiry = getIntent().getStringExtra("expirationdate");
        quantity = getIntent().getIntExtra("quantity", 0);
        memo = getIntent().getStringExtra("memo");

        idEditText.setText(String.valueOf(id));
        nameEditText.setText(name);
        expiryEditText.setText(expiry);
        memoEditText.setText(memo);
        quantityTextView.setText(String.valueOf(quantity));
        idEditText.setEnabled(false);
        expiryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DatePickerDialog를 띄우기 위한 캘린더 객체 생성
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // DatePickerDialog 생성 및 설정
                DatePickerDialog datePickerDialog = new DatePickerDialog(IngredientUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                if (ContextCompat.checkSelfPermission(IngredientUpdateActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IngredientUpdateActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("수정 확인")
                        .setMessage("정말 수정하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // '네' 버튼을 클릭했을 때 실행할 코드 작성.
                                int id = Integer.parseInt(idEditText.getText().toString());
                                String name = nameEditText.getText().toString();
                                String expiry = expiryEditText.getText().toString();
                                int quantity = Integer.parseInt(quantityTextView.getText().toString());
                                String memo = memoEditText.getText().toString();

                                IngredientUpdateRequest request = new IngredientUpdateRequest(id, name, expiry, quantity, memo);
                                sendUpdateFoodList(request);
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("삭제 확인")
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // '네' 버튼을 클릭했을 때 실행할 코드 작성.
                                String id = idEditText.getText().toString();
                                sendDeleteFood(new String[]{id});

                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });
    }

    private void sendUpdateFoodList(IngredientUpdateRequest updateRequest) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();

        Call<IngredientUpdateResponse> call = apiService.getUpdateFood("Bearer " + accessToken,
                updateRequest.getId(),
                updateRequest.getName(),
                updateRequest.getMemo(),
                updateRequest.getCount(),
                updateRequest.getDate());

        call.enqueue(new Callback<IngredientUpdateResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<IngredientUpdateResponse> call, retrofit2.Response<IngredientUpdateResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(IngredientUpdateActivity.this, "식재료가 성공적으로 수정되었습니다..", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(IngredientUpdateActivity.this, "식재료 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IngredientUpdateResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(IngredientUpdateActivity.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDeleteFood(String[] ingredientIds) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();
        DeleteIngredients deleteIngredients = new DeleteIngredients(ingredientIds);
        Call<IngredientDeleteResponse> call = apiService.DeleteFood("Bearer " + accessToken, deleteIngredients);

        call.enqueue(new Callback<IngredientDeleteResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<IngredientDeleteResponse> call, retrofit2.Response<IngredientDeleteResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(IngredientUpdateActivity.this, "식재료가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(IngredientUpdateActivity.this, "식재료 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IngredientDeleteResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(IngredientUpdateActivity.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void openCamera() {
        Intent intent = new Intent(IngredientUpdateActivity.this, CameraActivity.class);
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