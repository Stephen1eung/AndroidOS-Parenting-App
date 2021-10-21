package ca.cmpt276.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.cmpt276.project.UI.ConfigureChildActivity;
import ca.cmpt276.project.model.Child;
import ca.cmpt276.project.model.ChildManager;

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