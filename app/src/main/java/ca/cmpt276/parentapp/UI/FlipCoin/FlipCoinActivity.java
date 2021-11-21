package ca.cmpt276.parentapp.UI.FlipCoin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Child.ChildManager;
import ca.cmpt276.parentapp.model.Coin.Coin;
import ca.cmpt276.parentapp.model.Coin.CoinManager;

public class FlipCoinActivity extends AppCompatActivity {
    private ChildManager childManager;
    private CoinManager coinManager;
    private ImageView coinImage;
    private static int childIndex;
    private static int lastChildIndex;
    private int PlayerChoice;
    private ListView list;

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    private void initItems() {
        childManager = ChildManager.getInstance();
        coinManager = CoinManager.getInstance();
        coinManager.setCoinHistoryArrayList(loadSavedFlips(FlipCoinActivity.this));
        coinImage = findViewById(R.id.coinImage);
    }

    @Override
    protected void onStart() {
        initItems();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        saveFlip(FlipCoinActivity.this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);

        initItems();
        PlayerPickBtn();
        pickKid();
        flipHistoryBtn();
        FlipBtn();
    }

    private void flipHistoryBtn() {
        Button FlipBtn = findViewById(R.id.FlipHistory);
        FlipBtn.setOnClickListener(view -> {
            Intent intent = FlipHistory.makeIntent(FlipCoinActivity.this);
            startActivity(intent);
        });
    }

    private void pickKid() {
        Spinner dropdown = findViewById(R.id.pickchild);
        ArrayList<Child> ChildArray = childManager.getChildArrayList();
        ArrayList<String> items = new ArrayList<>();
        if (ChildArray.size() == 0) {
            items.add("NO CHILDREN");
        }
        else {
            items.add("DEFAULT");
            for (Child i : ChildArray) {
                items.add(i.getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_dropdown, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() == "NO CHILDREN" || adapterView.getItemAtPosition(i).toString() == "DEFAULT") {
                    childIndex = -1;
                }
                else {
                    childIndex = childManager.findChildIndex(adapterView.getItemAtPosition(i).toString());
                    lastChildIndex = childIndex;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                childIndex = -1;
            }
        });
    }

    private void FlipBtn() {
        Button FlipBtn = findViewById(R.id.Flip);
        FlipBtn.setOnClickListener(view -> {
            int pick = new Random().nextInt(2);

            if (pick == 1) coinImage.setImageResource(R.drawable.head);
            else coinImage.setImageResource(R.drawable.tail);
            RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF,
                    0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(1000);
            MediaPlayer mp = MediaPlayer.create(FlipCoinActivity.this, R.raw.flip_sound);
            mp.start();
            coinImage.startAnimation(rotate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            String formattedDateTime = dateTime.format(formatter);

            Coin newCoin = new Coin(formattedDateTime, childIndex, PlayerChoice, PlayerChoice == pick);
            Toast.makeText(FlipCoinActivity.this, newCoin + "", Toast.LENGTH_SHORT).show();
            coinManager.addCoinHistory(newCoin);
            saveFlip(FlipCoinActivity.this);
        });
    }

    private void PlayerPickBtn() {
        Button head = findViewById(R.id.Head);
        head.setOnClickListener(view -> {
            PlayerChoice = 0;
            Toast.makeText(FlipCoinActivity.this, "Player Choice: Head", Toast.LENGTH_SHORT).show();
        });

        Button tail = findViewById(R.id.Tail);
        tail.setOnClickListener(view -> {
            PlayerChoice = 1;
            Toast.makeText(FlipCoinActivity.this, "Player Choice: Tail", Toast.LENGTH_SHORT).show();
        });
    }

//    private void listAllKids() {
//        ArrayAdapter<Child> adapter = new adapter();
//        list = findViewById(R.id.QueueList);
//        list.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

//    public class adapter extends ArrayAdapter<Child> {
//        public adapter() {
//            super(FlipCoinActivity.this, R.layout.child_list_layout, queueManager.getQueueList());
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            View itemView = convertView;
//            if (itemView == null)
//                itemView = getLayoutInflater().inflate(R.layout.child_list_layout, parent, false);
//
//            Child currKid = queueManager.getQueueList().get(position);
//            TextView txt = itemView.findViewById(R.id.childName);
//            txt.setText(currKid.getName());
//
//            ImageView childImage = itemView.findViewById(R.id.ChildImageList);
//            childImage.setImageResource(R.drawable.childimg);
//
//            return itemView;
//        }
//    }


    public static ArrayList<Coin> loadSavedFlips(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("SavedFlips", "");
        Type type = new TypeToken<ArrayList<Coin>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void saveFlip(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(CoinManager.getInstance().getCoinHistory());
        editor.putString("SavedFlips", json);
        editor.apply();
    }
}