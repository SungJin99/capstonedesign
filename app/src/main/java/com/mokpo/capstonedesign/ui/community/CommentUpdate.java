package com.mokpo.capstonedesign.ui.community;

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

import retrofit2.Call;
import retrofit2.Callback;

public class CommentUpdate extends AppCompatActivity{
    private Button updateButton;
    private EditText commentEditText;
    private int board;
    private int id;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_comment_update);
        updateButton = findViewById(R.id.comment_update_button);
        commentEditText = findViewById(R.id.cmt_content_et);

        id = getIntent().getIntExtra("id", 0);
        content = getIntent().getStringExtra("content");
        board = getIntent().getIntExtra("postId", 0);

        commentEditText.setText(content);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = commentEditText.getText().toString();
                updateComment(board, id, content);
            }
        });

    }
    private void updateComment(int postId, int commentId, String content) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();

        Call<Void> call = apiService.updateComment("Bearer " + accessToken, postId, commentId, content);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(CommentUpdate.this, "댓글이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(CommentUpdate.this, "댓글 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(CommentUpdate.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
