package com.mokpo.capstonedesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.mokpo.capstonedesign.DjangoApiHandler;
import com.mokpo.capstonedesign.IngredientAddActivity;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.ui.community.PostDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private BarcodeView barcodeView;

    public static String result_NM;
    public static String result_DAYCNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        barcodeView = findViewById(R.id.scanner_view);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startScanning();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
        private void onScanComplete(String barcodeResult) {
            String url = "http://20.249.62.24:8000/api/food/barnumretrun/";
            String barnum = barcodeResult;
            Intent resultIntent = new Intent(CameraActivity.this, IngredientAddActivity.class);
            DjangoApiHandler.sendPostRequest(url, barnum, new DjangoApiHandler.ApiResponseCallback() {
                @Override
                public void onResponseReceived(String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                // JSON 응답에서 필요한 값을 추출합니다.
                                JSONObject c005Object = jsonResponse.getJSONObject("C005");
                                JSONArray rowArray = c005Object.getJSONArray("row");
                                JSONObject rowObject = rowArray.getJSONObject(0);
                                String prdlstNm = rowObject.getString("PRDLST_NM");
                                String pogDayCnt = rowObject.getString("POG_DAYCNT");

                                // 추출한 값을 텍스트뷰에 표시합니다.
                                result_NM = prdlstNm;
                                result_DAYCNT = pogDayCnt;
                                System.out.println(result_NM);
                                System.out.println(result_DAYCNT);
                                IngredientAddActivity.mfdEditText.setText(pogDayCnt);
                                IngredientAddActivity.nameEditText.setText(result_NM);


                                finish();
                                setResult(Activity.RESULT_OK, resultIntent);

                               // resultIntent.putExtra("SCAN_RESULT", barnum);
                               // finish();
                                // 화면 이동
                                //startActivity(resultIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (errorMessage.contains("404")) {
                                // 에러 처리
                                onPause();
                            }
                            Toast.makeText(CameraActivity.this, "식재료 정보가 없는 바코드입니다. 추가하시려면 직접 등록해주세요.", Toast.LENGTH_SHORT).show();
                            Log.e("CameraActivity", "API request failed: " + errorMessage);
                            finish();
                        }
                    });

                }
            });
        }



    private void startScanning() {
        barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                String barcodeValue = result.getText();

                onScanComplete(barcodeValue);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // 메서드 구현
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                Toast.makeText(this, "카메라 권한을 허용해야 스캐너를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
