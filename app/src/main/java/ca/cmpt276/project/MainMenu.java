package ca.cmpt276.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.project.UI.ConfigureChildActivity;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle(R.string.homeTitle);
        configChildBtn();
    }

    private void configChildBtn() {
        Button btn = findViewById(R.id.ChildConfig);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ConfigureChildActivity.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });
    }
}