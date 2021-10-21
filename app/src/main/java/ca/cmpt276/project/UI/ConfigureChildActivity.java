package ca.cmpt276.project.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ca.cmpt276.project.MainMenu;
import ca.cmpt276.project.R;
import ca.cmpt276.project.model.Child;
import ca.cmpt276.project.model.ChildManager;

public class ConfigureChildActivity extends AppCompatActivity {
    private ChildManager manager;
    private ArrayList<Child> kids;

    private ImageView DownArrow;
    private TextView whenEmpty, info;

    public static Intent makeIntent(Context context) {
        return new Intent(context, ConfigureChildActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);
        setTitle(R.string.configPage);

        whenEmpty = findViewById(R.id.textWhnEmpty);
        info = findViewById(R.id.infoTxt);
        DownArrow = findViewById(R.id.arrow);

        manager = ChildManager.getInstance();
        kids = manager.getKids();

        itemClick();
        addKidBtn();
        listAllKids();
    }

    private void itemClick() {
        ListView listView = findViewById(R.id.kidsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = EditChildActivity.makeIntent(ConfigureChildActivity.this, i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        kids = manager.getKids();
        if (manager.getKids().isEmpty()) {
            whenEmpty.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            DownArrow.setVisibility(View.VISIBLE);
        }
        else {
            whenEmpty.setVisibility(View.INVISIBLE);
            info.setVisibility(View.INVISIBLE);
            DownArrow.setVisibility(View.INVISIBLE);
        }
        listAllKids();
        super.onStart();
    }

    private void addKidBtn() {
        FloatingActionButton btn = findViewById(R.id.addKid);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddChildActivity.makeIntent(ConfigureChildActivity.this);
                startActivity(intent);
            }
        });
    }

    private void listAllKids() {
        ArrayAdapter<Child> adapter = new adapter();
        ListView list = findViewById(R.id.kidsList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<Child> {
        public adapter() {
            super(ConfigureChildActivity.this, R.layout.child_list_layout, manager.getKids());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.child_list_layout, parent, false);
            }

            Child currKid = manager.getKids().get(position);

            TextView txt = itemView.findViewById(R.id.kidsName);
            txt.setText(currKid.getName());

            return itemView;
        }
    }
}