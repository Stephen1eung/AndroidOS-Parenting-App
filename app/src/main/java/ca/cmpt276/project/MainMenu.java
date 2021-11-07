package ca.cmpt276.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.project.UI.ConfigureChildActivity;
import ca.cmpt276.project.UI.TimeoutActivity;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle(R.string.homeTitle);

        timeOutBtn();
        configChildBtn();
    }

    private void timeOutBtn() {
        Button btn = findViewById(R.id.timeOut);
        btn.setOnClickListener(view -> {
            Intent intent = TimeoutActivity.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }

    private void configChildBtn() {
        Button btn = findViewById(R.id.ChildConfig);
        btn.setOnClickListener(view -> {
            Intent intent = ConfigureChildActivity.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }
}