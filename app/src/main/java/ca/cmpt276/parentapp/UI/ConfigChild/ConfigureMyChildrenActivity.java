package ca.cmpt276.parentapp.UI.ConfigChild;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child;
import ca.cmpt276.parentapp.model.ChildManager;

public class ConfigureMyChildrenActivity extends AppCompatActivity {
    private static ChildManager manager;
    private ImageView DownArrow;
    private TextView whenEmpty, info;

    public static final class UriAdapter extends TypeAdapter<Uri> {
        @Override
        public void write(JsonWriter out, Uri uri) throws IOException {
            out.value(uri.toString());
        }

        @Override
        public Uri read(JsonReader in) throws IOException {
            return Uri.parse(in.nextString());
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ConfigureMyChildrenActivity.class);
    }

    public static ArrayList<Child> loadSavedKids(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        // https://stackoverflow.com/questions/22533432/create-object-from-gson-string-doesnt-work
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String json = sharedPrefs.getString("SavedKids", "");
        Type type = new TypeToken<ArrayList<Child>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void saveKids(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String json = gson.toJson(manager.getChildArrayList());
        editor.putString("SavedKids", json);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_my_children);
        setTitle(R.string.ConfigChildTitle);

        whenEmpty = findViewById(R.id.textWhnEmpty);
        info = findViewById(R.id.infoTxt);
        DownArrow = findViewById(R.id.arrow);

        manager = ChildManager.getInstance();
        manager.setChild(loadSavedKids(ConfigureMyChildrenActivity.this));

        itemClick();
        addKidBtn();
        listAllKids();
    }

    @Override
    protected void onStart() {
        manager = ChildManager.getInstance();
        manager.setChild(loadSavedKids(ConfigureMyChildrenActivity.this));
        if (manager.getChildArrayList().isEmpty()) {
            whenEmpty.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            DownArrow.setVisibility(View.VISIBLE);
        } else {
            whenEmpty.setVisibility(View.INVISIBLE);
            info.setVisibility(View.INVISIBLE);
            DownArrow.setVisibility(View.INVISIBLE);
        }
        listAllKids();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        saveKids(ConfigureMyChildrenActivity.this);
        super.onDestroy();
    }

    private void itemClick() {
        ListView listView = findViewById(R.id.ChildListView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = EditChildActivity.makeIntent(ConfigureMyChildrenActivity.this, i);
            startActivity(intent);
        });
    }


    private void addKidBtn() {
        FloatingActionButton btn = findViewById(R.id.AddChildBtn);
        btn.setOnClickListener(view -> {
            Intent intent = AddChildActivity.makeIntent(ConfigureMyChildrenActivity.this);
            startActivity(intent);
        });
    }

    private void listAllKids() {
        ArrayAdapter<Child> adapter = new adapter();
        ListView list = findViewById(R.id.ChildListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<Child> {
        public adapter() {
            super(ConfigureMyChildrenActivity.this, R.layout.child_list_layout, manager.getChildArrayList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.child_list_layout, parent, false);
            }

            Child currKid = manager.getChildArrayList().get(position);
            TextView txt = itemView.findViewById(R.id.CoinTextView);
            txt.setText(currKid.getName());

            ImageView childImg = itemView.findViewById(R.id.CoinImageView);
            childImg.setImageResource(R.drawable.child);

            return itemView;
        }
    }

}