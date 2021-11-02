package ca.cmpt276.project.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ca.cmpt276.project.R;

// https://www.youtube.com/watch?v=MDuGwI6P-X8&list=PLrnPJCHvNZuB8wxqXCwKw2_NkyEmFwcSd&index=1&ab_channel=CodinginFlow
// a lot of the code was from that youtube video above, I simply added shared pref and user input (the video was missing those)
public class TimeoutActivity extends AppCompatActivity {
    private long START_TIME = 60000;
    RadioGroup group;
    private int defaultOption;
    private TextView countDown;
    private EditText userInput;
    private boolean timerRunning;
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
        populateTimeOptions();
        setupTimer();
    }

    private void initAllItems() {
        group = findViewById(R.id.timeGroup);
        setBtn = findViewById(R.id.setBtn);
        resetBtn = findViewById(R.id.resetBtn);
        userInput = findViewById(R.id.userInput);
        countDown = findViewById(R.id.countDown);
        startAndPauseBtn = findViewById(R.id.startAndPauseBtn);
    }

    private void setTimer(long time) {
        START_TIME = time;
        resetTimer();
    }

    private void setupTimer() {
        startAndPauseBtn.setOnClickListener(view -> {
            if (timerRunning) pauseTimer();
            else startTimer();
        });

        resetBtn.setOnClickListener(view -> resetTimer());

        setBtn.setOnClickListener(view -> {
            if (!userInput.getText().toString().isEmpty()) {
                if (Long.parseLong(userInput.getText().toString()) == 0) {
                    Toast.makeText(TimeoutActivity.this, "Enter Positive Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                group.clearCheck();
                long time = Long.parseLong(userInput.getText().toString()) * 60000;
                setTimer(time);
                userInput.setText("");
            }
        });
    }

    private void startTimer() {
        END_TIME = System.currentTimeMillis() + TIME_LEFT;
        countDownTimer = new CountDownTimer(TIME_LEFT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TIME_LEFT = millisUntilFinished;
                updateCounter();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateBtnStates();
            }
        }.start();
        timerRunning = true;
        updateBtnStates();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateBtnStates();
    }

    private void resetTimer() {
        TIME_LEFT = START_TIME;
        updateCounter();
        updateBtnStates();
    }

    private void updateCounter() {
        int hours = (int) (TIME_LEFT / 1000) / 3600;
        int minutes = (int) ((TIME_LEFT / 1000) % 3600) / 60;
        int seconds = (int) (TIME_LEFT / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) timeLeftFormatted = String.format(Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, seconds);
        else timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        countDown.setText(timeLeftFormatted);
    }

    private void updateBtnStates() {
        if (timerRunning) {
            userInput.setVisibility(View.INVISIBLE);
            setBtn.setVisibility(View.INVISIBLE);
            resetBtn.setVisibility(View.INVISIBLE);
            startAndPauseBtn.setText("Pause");
        } else {
            userInput.setVisibility(View.VISIBLE);
            setBtn.setVisibility(View.VISIBLE);
            startAndPauseBtn.setText("Start");

            if (TIME_LEFT < 1000) {
                startAndPauseBtn.setVisibility(View.INVISIBLE);
            } else {
                startAndPauseBtn.setVisibility(View.VISIBLE);
            }

            if (TIME_LEFT < START_TIME) {
                resetBtn.setVisibility(View.VISIBLE);
            } else {
                resetBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void populateTimeOptions() {

        int[] boardRow = getResources().getIntArray(R.array.timeOptions);

        for (int row : boardRow) {
            RadioButton btn = new RadioButton(this);
            btn.setText(getString(R.string.timeOptionString, row));

            if (defaultOption == row) btn.setChecked(true);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long time = row * 60000;
                    setTimer(time);
                    defaultOption = row;
                }
            });
            group.addView(btn);
        }
    }

    @Override
    protected void onStop() {
        SharedPreferences sp = getSharedPreferences("timerOutPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putLong("START_TIME", START_TIME);
        editor.putLong("TIME_LEFT", TIME_LEFT);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("END_TIME", END_TIME);
        editor.putInt("defaultOption", defaultOption);
        editor.apply();

        if (countDownTimer != null) countDownTimer.cancel();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("timerOutPref", MODE_PRIVATE);
        defaultOption = prefs.getInt("defaultOption", 1);
        START_TIME = prefs.getLong("START_TIME", 60000);
        TIME_LEFT = prefs.getLong("TIME_LEFT", START_TIME);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCounter();
        updateBtnStates();

        if (timerRunning) {
            END_TIME = prefs.getLong("END_TIME", 0);
            TIME_LEFT = END_TIME - System.currentTimeMillis();

            if (TIME_LEFT < 0) {
                TIME_LEFT = 0;
                timerRunning = false;
                updateCounter();
                updateBtnStates();
            } else startTimer();
        }
    }
}
