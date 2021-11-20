package ca.cmpt276.parentapp.UI.WhoseTurn;


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
import ca.cmpt276.parentapp.UI.ConfigChild.EditChildActivity;
import ca.cmpt276.parentapp.model.Tasks.TaskManager;

public class EditTask extends AppCompatActivity {
    private static final String INDEX_NAME = "ca.cmpt276.project.UI.WhoseTurn - index";
    private EditText taskDesc;
    private TaskManager taskManager;
    private int taskIndex;

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, EditTask.class);
        intent.putExtra(INDEX_NAME, index);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_child_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initItems() {
        taskDesc = findViewById(R.id.TaskDecEditView);
        taskManager = TaskManager.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_task_layout);


        initItems();
        getIndexFromIntent();
        fillInFields();
        saveEdited();
    }

    private void saveEdited() {
        Button editTaskBtn = findViewById(R.id.addTaskToList);
        editTaskBtn.setOnClickListener(view -> {
            if (!taskDesc.getText().toString().equals("")) {
                taskManager.getTaskArrayList().get(taskIndex).setTaskDesc(taskDesc.getText().toString());
                finish();
            } else {
                Toast.makeText(EditTask.this, "Task Cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillInFields() {
        taskDesc.setText(taskManager.getTaskArrayList().get(taskIndex).getTaskDesc());
    }

    private void getIndexFromIntent() {
        Intent intent = getIntent();
        taskIndex = intent.getIntExtra(INDEX_NAME, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.deleteBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditTask.this);
            builder.setIcon(R.drawable.warning)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to DELETE this TASK?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        taskManager.removeTask(taskIndex);
                        Toast.makeText(EditTask.this, "TASK DELETED", Toast.LENGTH_SHORT).show();
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
