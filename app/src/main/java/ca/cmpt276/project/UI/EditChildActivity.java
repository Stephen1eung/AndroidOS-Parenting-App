package ca.cmpt276.project.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.ChildManager;

public class EditChildActivity extends AppCompatActivity {
    private EditText name;
    private ChildManager manager;
    private int kidIndex;
    private static final String INDEX_NAME = "ca.cmpt276.project.UI - index";

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, EditChildActivity.class);
        intent.putExtra(INDEX_NAME, index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setTitle(R.string.editTitle);

        name = findViewById(R.id.nameOfKid);
        manager = ChildManager.getInstance();

        getIndexFromIntent();
        fillInFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getIndexFromIntent() {
        Intent intent = getIntent();
        kidIndex = intent.getIntExtra(INDEX_NAME, 0);
    }

    private void fillInFields() {
        name.setText(manager.getKids().get(kidIndex).getName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                manager.getKids().get(kidIndex).setName(name.getText().toString());
                Toast.makeText(EditChildActivity.this, "CHILD EDITED", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.deleteBtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);
                builder.setIcon(R.drawable.warning)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to DELETE this kid?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.removeKid(kidIndex);
                                Toast.makeText(EditChildActivity.this, "KID DELETED", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.warning)
                .setTitle("Closing Activity")
                .setMessage("Seems like you have made some changes to the data, are you sure you want to CANCEL?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
