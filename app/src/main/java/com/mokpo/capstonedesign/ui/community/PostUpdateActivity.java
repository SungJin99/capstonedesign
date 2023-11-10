package com.mokpo.capstonedesign.ui.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.common.api.internal.ApiKey;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostUpdateActivity extends AppCompatActivity {
    private EditText titleEdit;
    private EditText contentEdit;
    private Button postUpdateButton;
    private String title;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_update);

        titleEdit = findViewById(R.id.title_et);
        contentEdit = findViewById(R.id.content_et);
        postUpdateButton = findViewById(R.id.post_update_button);

        Intent intent = getIntent();
        title = getIntent().getStringExtra("title");
        content= getIntent().getStringExtra("content");
        titleEdit.setText(title);
        contentEdit.setText(content);

        int postId = intent.getIntExtra("postId", -1);
        if (postId == -1) {
            // 게시글 ID가 전달되지 않았을 때의 처리
            finish();
            return;
        }
//        loadPostDetail(postId);
        postUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString();
                String content = contentEdit.getText().toString();
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(PostUpdateActivity.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(title.isEmpty()){
                    Toast.makeText(PostUpdateActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(content.isEmpty()){
                    Toast.makeText(PostUpdateActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    updatePost(postId, title, content);
                }
            }
        });


    }
    private void updatePost(int postId, String title, String content){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");
        ApiService apiService = ApiClient.getApiService();

        Call<Post> call = apiService.updatePost("Bearer " + accessToken, postId, title, content);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    EditText titleEdit = findViewById(R.id.title_et);
                    TextView contentEdit = findViewById(R.id.content_et);
                    titleEdit.setText(post.getTitle());
                    contentEdit.setText(post.getContent());
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(PostUpdateActivity.this, "게시글이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PostUpdateActivity.this, "게시글 수정 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(PostUpdateActivity.this, "에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void loadPostDetail(int postId) {
//
//        ApiService apiService = ApiClient.getApiService();
//
//        Call<Post> call = apiService.getPost(postId);
//
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if (response.isSuccessful()) {
//                    Post post = response.body();
//
//                    // 받아온 게시글 정보를 화면에 표시합니다.
//                    EditText titleView = findViewById(R.id.title_et);
//                    EditText contentView = findViewById(R.id.content_et);
//                    titleView.setText(post.getTitle());
//                    contentView.setText(post.getContent());
//
//
//                } else {
//                    Toast.makeText(PostUpdateActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                Toast.makeText(PostUpdateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}