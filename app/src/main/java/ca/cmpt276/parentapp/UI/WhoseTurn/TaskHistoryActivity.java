package ca.cmpt276.parentapp.UI.WhoseTurn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

        listAllTasks();
    }

    private void listAllTasks() {
        ArrayAdapter<TaskHistory> adapter = new adapter();
        ListView list = findViewById(R.id.TaskHistoryListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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