package ca.cmpt276.project.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.util.Objects;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.Child;
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.nameOfKid);
        manager = ChildManager.getInstance();
        // textWatcher();
    }

    private void textWatcher() {
        name = findViewById(R.id.nameOfKid);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                if (!name.getText().toString().equals("")) {
                    String n = name.getText().toString();
                    Child newChild = new Child(n);
                    manager.addKid(newChild);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);
                    builder.setIcon(R.drawable.warning)
                            .setTitle("Closing Activity")
                            .setMessage("Are you sure you want to QUIT WITHOUT FINISHING this setting?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AddChildActivity.this, "DID NOT SAVE (EMPTY FIELDS)", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }
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
                .setMessage("Are you sure you want to close this setting without saving?")
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