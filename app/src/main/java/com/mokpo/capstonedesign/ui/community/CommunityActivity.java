package com.mokpo.capstonedesign.ui.community;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.mokpo.capstonedesign.R;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_add);

        Button regButton = findViewById(R.id.reg_button);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code for handling the button click here
                // For example, you can retrieve the entered title and content from the EditText views
                EditText titleEditText = findViewById(R.id.title_et);
                String title = titleEditText.getText().toString();

                EditText contentEditText = findViewById(R.id.content_et);
                String content = contentEditText.getText().toString();

                // Perform any necessary actions with the entered title and content
                // For now, let's just display them in logs
                Log.d("NotificationsAddActivity", "Title: " + title);
                Log.d("NotificationsAddActivity", "Content: " + content);

                // You can also navigate back to the previous fragment or finish the activity here
                finish();
            }
        });
///

        // Add your code for handling the activity here
        // For example, you can initialize views and set up any necessary event listeners
    }
}