package ca.cmpt276.project.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.project.R;

public class TimeoutActivity extends AppCompatActivity {
    private TextView countDown;
    private EditText userInput;
    private boolean timerRunning;
    private final long START_TIME = 60000;
    private long TIME_LEFT, END_TIME;
    private CountDownTimer countDownTimer;
    private Button startAndPauseBtn, resetBtn, setBtn;

    public static Intent makeIntent(Context context) {
        return new Intent(context, TimeoutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);
        setTitle(R.string.timeOutTitle);


        initAllItems();
        setupTimer();
    }

    private void initAllItems() {
        setBtn = findViewById(R.id.setBtn);
        resetBtn = findViewById(R.id.resetBtn);
        userInput = findViewById(R.id.userInput);
        countDown = findViewById(R.id.countDown);
        startAndPauseBtn = findViewById(R.id.startAndPauseBtn);
    }

    private void setupTimer() {
        startAndPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
}