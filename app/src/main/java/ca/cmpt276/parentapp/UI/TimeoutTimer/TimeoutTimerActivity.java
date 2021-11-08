package ca.cmpt276.parentapp.UI.TimeoutTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ca.cmpt276.parentapp.R;

public class TimeoutTimerActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, TimeoutTimerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);
        setTitle(R.string.TimeoutTimerTitle);
    }
}