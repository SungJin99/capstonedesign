package com.mokpo.capstonedesign.ui.community;

//import static com.mokpo.capstonedesign.retrofit2.ApiClient.retrofit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.PostRequest;
import com.mokpo.capstonedesign.retrofit2.PostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAddActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;

    private EditText commentEditText;

    private Button regButton;

    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_add);
        titleEditText = findViewById(R.id.title_et);
        contentEditText = findViewById(R.id.content_et);
        regButton = findViewById(R.id.reg_button);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();


                PostRequest request = new PostRequest(title, content);
                createPost(request);
            }
        });
    }
    private void createPost(PostRequest request) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");


        ApiService apiService = ApiClient.getApiService();

        Call<PostResponse> call = apiService.createPost("Bearer " + accessToken, request);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(PostAddActivity.this, "게시글이 성공적으로 등록되었습니다..", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(PostAddActivity.this, "게시글 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(PostAddActivity.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
