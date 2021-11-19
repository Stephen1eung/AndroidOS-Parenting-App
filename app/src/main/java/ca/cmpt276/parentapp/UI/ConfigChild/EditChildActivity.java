package ca.cmpt276.parentapp.UI.ConfigChild;

import static ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity.saveKids;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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
    }

    private void getIndexFromIntent() {
        Intent intent = getIntent();
        kidIndex = intent.getIntExtra(INDEX_NAME, 0);
    }

    private void fillInFields() {
        name.setText(childManager.getChildArrayList().get(kidIndex).getName());
        Button editChildBtn = findViewById(R.id.addChildToListBtn);
        editChildBtn.setText(R.string.edit_child_btn);
//        childImg = findViewById(R.id.ChildImagePreview);
//        childImg.setImageResource(R.drawable.child);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.deleteBtn) {
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
