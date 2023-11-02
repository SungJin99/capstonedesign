package com.mokpo.capstonedesign.ui.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.internal.ApiKey;
import com.mokpo.capstonedesign.IngredientAddActivity;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.IngredientAddRequest;
import com.mokpo.capstonedesign.retrofit2.IngredientAddResponse;
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientUpdateActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    private TextView titleEditText;
    private EditText contentEditText;
    private EditText commentEditText;
    private TextView dateTextView;
    private Button regButton;
    private Button updateButton;
    private Button deleteButton;
    private int id;
    private String title;
    private String content;
    private String Date;
    private int user;
    private RecyclerView commentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", -1);

        if (postId == -1) {
            // 게시글 ID가 전달되지 않았을 때의 처리
            finish();
            return;
        }
        loadPostDetail(postId);

        commentRecyclerView = findViewById(R.id.comment_recyclerview);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        titleEditText = findViewById(R.id.title_tv);
        contentEditText = findViewById(R.id.content_tv);
        dateTextView = findViewById(R.id.date_tv);
        commentEditText = findViewById(R.id.comment_et);
        regButton = findViewById(R.id.reg_button);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        Date = getIntent().getStringExtra("date");
        user = getIntent().getIntExtra("user_id", 0);

        titleEditText.setText(title);
        contentEditText.setText(content);
        dateTextView.setText(Date);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailActivity.this, PostUpdateActivity.class);
                intent.putExtra("postId", postId);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivity(intent);
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
                                deletePost(postId);
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent  = commentEditText.getText().toString();
                sendComment(postId, commentContent);
            }
        });
    }
    private void loadPostDetail(int postId) {

        ApiService apiService = ApiClient.getApiService();

        Call<Post> call = apiService.getPost(postId);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    List<Comment> comments = post.getComments();
                    System.out.println(comments.toString());
                    CommentAdapter commentAdapter = new CommentAdapter(comments, PostDetailActivity.this);
                    commentRecyclerView.setAdapter(commentAdapter);
                    // 받아온 게시글 정보를 화면에 표시합니다.
//                    TextView titleView = findViewById(R.id.title_tv);
//                    TextView contentView = findViewById(R.id.content_tv);
//                    TextView dateView = findViewById(R.id.date_tv);
//                    titleView.setText(post.getTitle());
//                    contentView.setText(post.getContent());
//                    dateView.setText(post.getDate());
                    displayPost(post);
                    displayComments(comments);
                    Intent intent = getIntent();
                    intent.putExtra("title", post.getTitle());
                    intent.putExtra("content", post.getContent());
                } else {
                    Toast.makeText(PostDetailActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(PostDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deletePost(int postId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");
        ApiService apiService = ApiClient.getApiService();

        Call<Void> call = apiService.deletePost("Bearer " + accessToken, postId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                   // Post post = response.body();
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(PostDetailActivity.this, "게시글이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    //adapter.notifyDataSetChanged();
                    finish();
                } else {
                    Toast.makeText(PostDetailActivity.this, "게시글 삭제 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PostDetailActivity.this, "에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayPost(Post post) {
        TextView titleView = findViewById(R.id.title_tv);
        TextView contentView = findViewById(R.id.content_tv);
        TextView dateView = findViewById(R.id.date_tv);
        titleView.setText(post.getTitle());
        contentView.setText(post.getContent());
        dateView.setText(post.getDate());
    }

    private void displayComments(List<Comment> comments) {
        CommentAdapter adapter = new CommentAdapter(comments, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(layoutManager);
        commentRecyclerView.setAdapter(adapter);
    }
    private void sendComment(int postId, String content) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();

        Call<Void> call = apiService.createComment("Bearer " + accessToken, postId, content);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(PostDetailActivity.this, "댓글이 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    commentEditText.setText("");
                    loadPostDetail(postId);
                } else {
                    Log.e("MainActivity", "Error posting data: " + response.code());
                    Toast.makeText(PostDetailActivity.this, "댓글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(PostDetailActivity.this, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", -1);
        // 게시글 상세 정보 불러오기
        loadPostDetail(postId);
    }

}
