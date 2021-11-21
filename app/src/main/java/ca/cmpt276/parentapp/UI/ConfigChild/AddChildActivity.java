package ca.cmpt276.parentapp.UI.ConfigChild;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity.saveKids;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Child.ChildManager;
import ca.cmpt276.parentapp.model.Child.QueueManager;

public class AddChildActivity extends AppCompatActivity {
    private EditText name;
    private String childImage;
    private String imgName;

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddChildActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_child_layout);

        addItemBtn();
        addImgBtn();
    }

    private void addImgBtn() {
        ActivityResultLauncher<String> getContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        ImageView childImg = findViewById(R.id.ChildImageImageView);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                            childImage = saveToInternalStorage(bitmap);
                            childImg.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Button button = findViewById(R.id.addImgBtn);
        button.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(AddChildActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContent.launch("image/*");
            } else {
                requestStoragePermission();
            }
        });

    }
    // https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String randomChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String fileName = "";
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            new StringBuilder().append(fileName).append(randomChar.charAt(random.nextInt(randomChar.length()))).toString();
        }
        File myPath = new File(directory, fileName+".jpg");
        imgName = fileName+".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    // https://www.youtube.com/watch?v=SMrB97JuIoM&ab_channel=CodinginFlow
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(AddChildActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addItemBtn() {
        ChildManager childManager = ChildManager.getInstance();
        QueueManager queueManager = QueueManager.getInstance();
        name = findViewById(R.id.ChildNameEditText);
        Button button = findViewById(R.id.addChildToListBtn);
        button.setOnClickListener(view -> {
            if (!name.getText().toString().equals("")) {
                String childName = name.getText().toString();
                childManager.addChild(new Child(childName, childImage, imgName));
                // queueManager.addChild(new Child(childName, childImage));
                saveKids(AddChildActivity.this);
                ConfigureChildActivity.saveQueue(AddChildActivity.this);
                finish();
            } else {
                Toast.makeText(AddChildActivity.this, "Please Add your Child's Name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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