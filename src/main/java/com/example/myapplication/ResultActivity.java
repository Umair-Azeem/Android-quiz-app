package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView tvResult, tvUserName;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvUserName = findViewById(R.id.tvUserName);
        tvResult = findViewById(R.id.tvResult);
        btnShare = findViewById(R.id.btnShare);

        // Retrieve user name
        String userName = getIntent().getStringExtra("USER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        // Set text with username
        if (userName != null) {
            tvUserName.setText("User: " + userName);
        } else {
            tvUserName.setText("User: Unknown");
        }

        tvResult.setText("Your Score: " + score + "/" + totalQuestions);

        btnShare.setOnClickListener(v -> {
            String shareText = userName + " scored " + score + "/" + totalQuestions + " in the Quiz App!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }
}
