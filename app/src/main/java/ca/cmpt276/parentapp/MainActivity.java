package ca.cmpt276.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ca.cmpt276.parentapp.UI.Help.HelpActivity;
import ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity;
import ca.cmpt276.parentapp.UI.FlipCoin.FlipCoinActivity;
import ca.cmpt276.parentapp.UI.TimeoutTimer.TimeoutTimerActivity;
import ca.cmpt276.parentapp.UI.WhoseTurn.WhoseTurnActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlipCoinBtn();
        TimeoutTimerBtn();
        ConfigChildBtn();
        HelpBtn();
        WhoseTurnBtn();
    }

    private void FlipCoinBtn() {
        Button button = findViewById(R.id.FlipBtn);
        button.setOnClickListener(view -> {
            Intent intent = FlipCoinActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void TimeoutTimerBtn() {
        Button button = findViewById(R.id.TimeoutBtn);
        button.setOnClickListener(view -> {
            Intent intent = TimeoutTimerActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void ConfigChildBtn() {
        Button button = findViewById(R.id.ConfigChildBtn);
        button.setOnClickListener(view -> {
            Intent intent = ConfigureChildActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void HelpBtn() {
        Button button = findViewById(R.id.HelpBtn);
        button.setOnClickListener(view -> {
            Intent intent = HelpActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void WhoseTurnBtn() {
        Button button = findViewById(R.id.WhoseTurnBtn);
        button.setOnClickListener(view -> {
            Intent intent = WhoseTurnActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }
}