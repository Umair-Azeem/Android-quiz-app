package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    TextView tvQuestion;
    RadioGroup rgOptions;
    Button btnPrevious, btnNext;
    int currentQuestionIndex = 0;
    int score = 0;
    List<Question> questions;
    String userName; // Store the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        // Get user name from intent
        userName = getIntent().getStringExtra("USER_NAME");

        // Load questions
        questions = loadQuestions();

        // Display the first question
        displayQuestion(currentQuestionIndex);

        btnNext.setOnClickListener(v -> {
            if (rgOptions.getCheckedRadioButtonId() != -1) {
                checkAnswer();
            }

            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                displayQuestion(currentQuestionIndex);
            } else {
                // Navigate to ResultActivity with the user's name
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("USER_NAME", userName); // Pass user name
                intent.putExtra("SCORE", score);
                intent.putExtra("TOTAL_QUESTIONS", questions.size());
                startActivity(intent);
                finish();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion(currentQuestionIndex);
            }
        });
    }

    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is 2 + 2?", Arrays.asList("3", "4", "5", "6"), 1));
        questions.add(new Question("What is the capital of France?", Arrays.asList("London", "Paris", "Berlin", "Madrid"), 1));
        questions.add(new Question("Who wrote 'Romeo and Juliet'?", Arrays.asList("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"), 1));
        questions.add(new Question("What is the chemical symbol for water?", Arrays.asList("H2O", "CO2", "O2", "NaCl"), 0));
        questions.add(new Question("Which country is known as the Land of the Rising Sun?", Arrays.asList("China", "Japan", "South Korea", "Thailand"), 1));
        return questions;
    }

    private void displayQuestion(int index) {
        Question question = questions.get(index);
        tvQuestion.setText("Question " + (index + 1) + ": " + question.getQuestion());

        // Update options
        for (int i = 0; i < rgOptions.getChildCount(); i++) {
            ((RadioButton) rgOptions.getChildAt(i)).setText(question.getOptions().get(i));
        }

        // Clear previous selection
        rgOptions.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        int selectedIndex = rgOptions.indexOfChild(findViewById(selectedId));

        if (selectedIndex == questions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
        }
    }
}
