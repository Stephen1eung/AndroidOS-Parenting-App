package ca.cmpt276.parentapp.UI.WhoseTurn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.UI.ConfigChild.ConfigureChildActivity;
import ca.cmpt276.parentapp.UI.ConfigChild.EditChildActivity;
import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Tasks.Task;
import ca.cmpt276.parentapp.model.Tasks.TaskManager;

public class WhoseTurnActivity extends AppCompatActivity {
    private static TaskManager taskManager = TaskManager.getInstance();
    private TextView NoTaskTextView;

    public static Intent makeIntent(Context context) {
        return new Intent(context, WhoseTurnActivity.class);
    }

    private void initItems() {
        taskManager.setTaskArrayList(loadSavedTasks(WhoseTurnActivity.this));
        NoTaskTextView = findViewById(R.id.NoTaskTextView);
    }

    @Override
    protected void onDestroy() {
        saveTasks(WhoseTurnActivity.this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        initItems();
        if (taskManager.getTaskArrayList().isEmpty())
            NoTaskTextView.setVisibility(View.VISIBLE);
        else
            NoTaskTextView.setVisibility(View.INVISIBLE);
        listAllTasks();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);

        initItems();
        listAllTasks();
        itemClick();
        addTaskBtn();
    }

    public static ArrayList<Task> loadSavedTasks(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("SavedTasks", "");
        Type type = new TypeToken<ArrayList<Task>>() {

        }.getType();
        return gson.fromJson(json, type);
    }

    public static void saveTasks(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskManager.getTaskArrayList());
        editor.putString("SavedTasks", json);
        editor.apply();
    }

    private void listAllTasks() {
        ArrayAdapter<Task> adapter = new adapter();
        ListView list = findViewById(R.id.TaskListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<Task> {
        public adapter() {
            super(WhoseTurnActivity.this, R.layout.task_list_layout, taskManager.getTaskArrayList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.task_list_layout, parent, false);

            Task CurrTask = taskManager.getTaskArrayList().get(position);
            TextView txt = itemView.findViewById(R.id.TaskDes);
            txt.setText(CurrTask.toString());

            ImageView childImage = itemView.findViewById(R.id.ChildimageViewTaskList);
            childImage.setImageResource(R.drawable.childimg);

            return itemView;
        }
    }

    private void itemClick() {
        ListView listView = findViewById(R.id.TaskListView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = EditTask.makeIntent(WhoseTurnActivity.this, i);
            startActivity(intent);
        });
    }

    private void addTaskBtn() {
        Button button = findViewById(R.id.AddTaskBtn);
        button.setOnClickListener(view -> {
            Intent intent = AddTask.makeIntent(WhoseTurnActivity.this);
            startActivity(intent);
        });
    }
}