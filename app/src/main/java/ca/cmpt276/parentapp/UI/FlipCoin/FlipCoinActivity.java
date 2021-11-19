package ca.cmpt276.parentapp.UI.FlipCoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.UI.TimeoutTimer.TimeoutTimerActivity;

public class FlipCoinActivity extends AppCompatActivity {
    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
    }
}