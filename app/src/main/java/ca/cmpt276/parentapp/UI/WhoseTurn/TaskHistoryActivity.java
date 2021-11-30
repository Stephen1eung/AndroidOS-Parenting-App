package ca.cmpt276.parentapp.UI.WhoseTurn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity;
import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Tasks.Task;
import ca.cmpt276.parentapp.model.Tasks.TaskHistory;
import ca.cmpt276.parentapp.model.Tasks.TaskHistoryManager;

public class TaskHistoryActivity extends AppCompatActivity {
    private final TaskHistoryManager taskHistoryManager = TaskHistoryManager.getInstance();

    public static Intent makeIntent(Context context) {
        return new Intent(context, TaskHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);
        setTitle("Task History");

        listAllTasks();
    }

    private void listAllTasks() {
        ArrayAdapter<TaskHistory> adapter = new adapter();
        ListView list = findViewById(R.id.TaskHistoryListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        taskHistoryManager.setTaskHistoryArrayList(loadSavedTaskHistory(TaskHistoryActivity.this));
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        saveHistory(TaskHistoryActivity.this);
        super.onDestroy();
    }

    public static ArrayList<TaskHistory> loadSavedTaskHistory(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("SaveTaskHistory", "");
        Type type = new TypeToken<ArrayList<TaskHistory>>() {

        }.getType();
        return gson.fromJson(json, type);
    }

    public static void saveHistory(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(TaskHistoryManager.getInstance().getTaskHistoryArrayList());
        editor.putString("SaveTaskHistory", json);
        editor.apply();
    }

    public class adapter extends ArrayAdapter<TaskHistory> {
        public adapter() {
                super(TaskHistoryActivity.this, R.layout.task_history_list_view, taskHistoryManager.getTaskHistoryArrayList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.task_history_list_view, parent, false);

            ImageView childImage = itemView.findViewById(R.id.TaskHistoryImageView);
            TaskHistory CurrTask = taskHistoryManager.getTaskHistoryArrayList().get(position);
            Child currKid = CurrTask.currChild();

            TextView txt = itemView.findViewById(R.id.ChildNameTaskHistory);
            txt.setText(CurrTask.toString());

            if (currKid == null) {
                return itemView;
            } else {
                if (currKid.getImg() != null && currKid.getImg() != "") {
                    try {
                        File f = new File(currKid.getImg(), currKid.getImgName());
                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                        childImage.setImageBitmap(b);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    childImage.setImageResource(R.drawable.childimg);
                }

                return itemView;
            }
        }
    }

}