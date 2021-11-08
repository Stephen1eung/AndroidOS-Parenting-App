package ca.cmpt276.parentapp;

import static ca.cmpt276.parentapp.UI.ConfigureMyChildrenActivity.loadSavedKids;
import static ca.cmpt276.parentapp.UI.FlipCoinActivity.loadSavedHistory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.parentapp.UI.ConfigureMyChildrenActivity;
import ca.cmpt276.parentapp.UI.FlipCoinActivity;
import ca.cmpt276.parentapp.UI.TimeoutTimerActivity;
import ca.cmpt276.parentapp.model.ChildManager;
import ca.cmpt276.parentapp.model.CoinHistoryManager;

public class MainActivity extends AppCompatActivity {

    ChildManager childManager;
    CoinHistoryManager coinHistoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.MainActivityTitle);

        childManager = ChildManager.getInstance();
        childManager.setChild(loadSavedKids(MainActivity.this));


        coinHistoryManager = CoinHistoryManager.getInstance();
        coinHistoryManager.setCoinHistoryArrayList(loadSavedHistory(MainActivity.this));

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