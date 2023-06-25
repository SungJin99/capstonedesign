package com.mokpo.capstonedesign;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DjangoApiHandler {

    private static final String TAG = "DjangoApiHandler";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface ApiResponseCallback {
        void onResponseReceived(String response);
        void onError(String errorMessage);
    }

    public static void sendPostRequest(String urlString, String barnum, ApiResponseCallback callback) {
        Future<?> future = executor.submit(() -> {
            String response = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject postData = new JSONObject();
                postData.put("barnum", barnum);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    bufferedReader.close();
                    response = responseBuilder.toString();
                } else {
                    Log.e(TAG, "HTTP 오류 코드: " + responseCode);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return response;
        });

        executor.execute(() -> {
            try {
                String response = (String) future.get();
                if (response != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.d(TAG, "응답: " + jsonResponse.toString());
                        // 결과를 전달하기 위해 onResponseReceived 콜백 메서드 호출
                        callback.onResponseReceived(jsonResponse.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "API 요청 실패");
                    // 에러를 전달하기 위해 onError 콜백 메서드 호출
                    callback.onError("API 요청 실패");
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 에러를 전달하기 위해 onError 콜백 메서드 호출
                callback.onError("API 요청 실패");
            }
        });
    }
}
