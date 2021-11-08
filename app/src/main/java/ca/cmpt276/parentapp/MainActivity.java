package ca.cmpt276.parentapp;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureMyChildrenActivity.loadSavedKids;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.parentapp.UI.ConfigChild.ConfigureMyChildrenActivity;
import ca.cmpt276.parentapp.UI.FlipCoin.FlipCoinActivity;
import ca.cmpt276.parentapp.UI.TimeoutTimer.TimeoutTimerActivity;
import ca.cmpt276.parentapp.model.ChildManager;

public class MainActivity extends AppCompatActivity {
    ChildManager childManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.MainActivityTitle);

        childManager = ChildManager.getInstance();
        childManager.setChild(loadSavedKids(MainActivity.this));

        FlipCoinBtn();
        TimeoutTimerBtn();
        ConfigMyChildBtn();
    }

    private void FlipCoinBtn() {
        Button btn = findViewById(R.id.FlipCoinBtn);
        btn.setOnClickListener(view -> {
            Intent intent = FlipCoinActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void TimeoutTimerBtn() {
        Button btn = findViewById(R.id.TimeoutTimerBtn);
        btn.setOnClickListener(view -> {
            Intent intent = TimeoutTimerActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void ConfigMyChildBtn() {
        Button btn = findViewById(R.id.ConfigChildernBtn);
        btn.setOnClickListener(view -> {
            Intent intent = ConfigureMyChildrenActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }
}