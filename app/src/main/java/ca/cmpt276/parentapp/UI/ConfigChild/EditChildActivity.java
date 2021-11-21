package ca.cmpt276.parentapp.UI.ConfigChild;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity.saveKids;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child.ChildManager;

public class EditChildActivity extends AppCompatActivity {
    private static final String INDEX_NAME = "ca.cmpt276.project.UI - index";
    private EditText name;
    private ChildManager childManager;
    private int kidIndex;

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, EditChildActivity.class);
        intent.putExtra(INDEX_NAME, index);
        return intent;
    }

    private void initItems() {
        name = findViewById(R.id.ChildNameEditText);
        childManager = ChildManager.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_child_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_child_layout);


        initItems();
        getIndexFromIntent();
        fillInFields();
        saveEdited();
    }

    private void saveEdited() {
        Button editChildBtn = findViewById(R.id.addChildToListBtn);
        editChildBtn.setOnClickListener(view -> {
            if (!name.getText().toString().equals("")) {
                childManager.getChildArrayList().get(kidIndex).setName(name.getText().toString());
                saveKids(EditChildActivity.this);
                finish();
            } else {
                Toast.makeText(EditChildActivity.this, "Name Cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIndexFromIntent() {
        Intent intent = getIntent();
        kidIndex = intent.getIntExtra(INDEX_NAME, 0);
    }

    private void fillInFields() {
        name.setText(childManager.getChildArrayList().get(kidIndex).getName());
        Button editChildBtn = findViewById(R.id.addChildToListBtn);
        editChildBtn.setText(R.string.edit_child_btn);
        ImageView childImg = findViewById(R.id.ChildImageImageView);
        Bitmap bitmap = childManager.getChildArrayList().get(kidIndex).getImg();
        childImg.setImageBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.helpBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);
            builder.setIcon(R.drawable.warning)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to DELETE this kid?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        childManager.removeChild(kidIndex);
                        saveKids(EditChildActivity.this);
                        Toast.makeText(EditChildActivity.this, "KID DELETED", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.warning)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this setting without saving?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

}
