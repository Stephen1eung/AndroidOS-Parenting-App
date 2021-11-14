package ca.cmpt276.parentapp.UI.ConfigChild;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureMyChildrenActivity.saveKids;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.ChildManager;

public class EditChildActivity extends AppCompatActivity {
    private static final String INDEX_NAME = "ca.cmpt276.project.UI - index";
    private EditText name;
    private ChildManager manager;
    private int kidIndex;
    private ImageView childImg;
    private Uri childImgPath;

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, EditChildActivity.class);
        intent.putExtra(INDEX_NAME, index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setTitle(R.string.EditChildTitle);

        name = findViewById(R.id.nameOfChild);
        manager = ChildManager.getInstance();

        getIndexFromIntent();
        fillInFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_child_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getIndexFromIntent() {
        Intent intent = getIntent();
        kidIndex = intent.getIntExtra(INDEX_NAME, 0);
    }

    private void fillInFields() {
        name.setText(manager.getChildArrayList().get(kidIndex).getName());
        childImg = findViewById(R.id.ChildImagePreview);
        childImg.setImageResource(R.drawable.child);
//        String idToStr = "android.resource://" + Objects.requireNonNull(R.class.getPackage()).getName() + "/" + R.drawable.child;
//        Uri path = Uri.parse(idToStr);
//        if (manager.getChildArrayList().get(kidIndex).getImgPath() != idToStr) {
//            File imgFile = new File(manager.getChildArrayList().get(kidIndex).getImgPath());
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            childImg.setImageBitmap(myBitmap);
//        } else {
//            childImg.setImageURI(path);
//        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                manager.getChildArrayList().get(kidIndex).setName(name.getText().toString());
                Toast.makeText(EditChildActivity.this, "CHILD EDITED", Toast.LENGTH_SHORT).show();
                saveKids(EditChildActivity.this);
                finish();
                return true;
            case R.id.deleteBtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);
                builder.setIcon(R.drawable.warning)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to DELETE this kid?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            manager.removeChild(kidIndex);
                            saveKids(EditChildActivity.this);
                            Toast.makeText(EditChildActivity.this, "KID DELETED", Toast.LENGTH_SHORT).show();
                            finish();
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
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}
