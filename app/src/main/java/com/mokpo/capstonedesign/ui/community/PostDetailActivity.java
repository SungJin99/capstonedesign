package com.mokpo.capstonedesign.ui.community;

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

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;

import retrofit2.Call;
import retrofit2.Callback;

public class PostDetailActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;

    private EditText commentEditText;
    private TextView dateTextView;
    private Button regButton;

    private int id;
    private String title;
    private String content;
    private String Date;
    private int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
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
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
