package ca.cmpt276.project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.ChildManager;

public class AddChildActivity extends AppCompatActivity {
    private ChildManager manager;
    private EditText name;


    public static Intent makeIntent(Context context) {
        return new Intent(context, AddChildActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setTitle(R.string.addPage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}