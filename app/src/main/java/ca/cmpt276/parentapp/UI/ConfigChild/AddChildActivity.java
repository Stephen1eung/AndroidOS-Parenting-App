package ca.cmpt276.parentapp.UI.ConfigChild;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureMyChildrenActivity.saveKids;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child;
import ca.cmpt276.parentapp.model.ChildManager;

public class AddChildActivity extends AppCompatActivity {
    public static int KEY_CODE = 1;
    private ChildManager manager;
    private EditText name;
    private ImageView childImg;
    private Uri childImgPath;

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddChildActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setTitle(R.string.addchild);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.nameOfChild);
        childImg = findViewById(R.id.ChildImagePreview);
        childImg.setImageResource(R.drawable.child);
        manager = ChildManager.getInstance();

        AddImageBtn();
    }

    private void AddImageBtn() {
        ActivityResultLauncher<String> getContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        childImg.setImageURI(result);
                        childImgPath = result;
                    }
                }
        );


        Button button = findViewById(R.id.AddChildImg);
        button.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(AddChildActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContent.launch("image/*");
            } else {
                requestStoragePermission();
            }
        });
    }

    // https://www.youtube.com/watch?v=SMrB97JuIoM&ab_channel=CodinginFlow
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(AddChildActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, KEY_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, KEY_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == KEY_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_child_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                if (!name.getText().toString().equals("")) {
                    String n = name.getText().toString();
                    Child newChild = new Child(n);
                    manager.addChild(newChild);
                    if (childImgPath != null) {
                        newChild.setImgPath(childImgPath.toString());
                    } else {
                        String path = "android.resource://" + Objects.requireNonNull(R.class.getPackage()).getName() + "/" + R.drawable.child;
                        newChild.setImgPath(path);
                    }
                    saveKids(AddChildActivity.this);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);
                    builder.setIcon(R.drawable.warning)
                            .setTitle("Closing Activity")
                            .setMessage("Are you sure you want to QUIT WITHOUT FINISHING this setting?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Toast.makeText(AddChildActivity.this, "DID NOT SAVE (EMPTY FIELDS)", Toast.LENGTH_SHORT).show();
                                finish();
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
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}