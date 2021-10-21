package ca.cmpt276.project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.Child;
import ca.cmpt276.project.model.ChildManager;

public class ConfigureChildActivity extends AppCompatActivity {
    private ChildManager manager;
    private ArrayList<Child> kids;

    private ImageView DownArrow;
    private TextView whenEmpty, info;

    public static Intent makeIntent(Context context) {
        return new Intent(context, ConfigureChildActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);
        setTitle(R.string.configPage);

        whenEmpty = findViewById(R.id.textWhnEmpty);
        info = findViewById(R.id.infoTxt);
        DownArrow = findViewById(R.id.arrow);

        manager = ChildManager.getInstance();
        kids = manager.getKids();

        addKidBtn();
    }

    @Override
    protected void onStart() {
        kids = manager.getKids();
        if (kids.size() <= 0) {
            whenEmpty.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            DownArrow.setVisibility(View.VISIBLE);
        }
        else {
            whenEmpty.setVisibility(View.INVISIBLE);
            info.setVisibility(View.INVISIBLE);
            DownArrow.setVisibility(View.INVISIBLE);
        }
        super.onStart();
    }

    private void addKidBtn() {
        FloatingActionButton btn = findViewById(R.id.addKid);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddChildActivity.makeIntent(ConfigureChildActivity.this);
                startActivity(intent);
                setTitle(R.string.configPage);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        });
    }
}