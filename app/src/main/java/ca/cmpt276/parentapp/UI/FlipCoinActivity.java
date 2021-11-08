package ca.cmpt276.parentapp.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child;
import ca.cmpt276.parentapp.model.ChildManager;
import ca.cmpt276.parentapp.model.CoinHistory;
import ca.cmpt276.parentapp.model.CoinHistoryManager;

public class FlipCoinActivity extends AppCompatActivity {
    ChildManager childManager = ChildManager.getInstance();
    private static CoinHistoryManager coinHistoryManager;
    private String NameOfPlayer;
    private int PlayerChoice;
    private boolean WinOrLose;

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        setTitle(R.string.FlipCoinTitle);

        PlayerChoice = -1;
        NameOfPlayer = "";

        FlipBtn();
        historyBtn();
        playerChoiceBtn();
        populateChildOptions();
    }

    public static ArrayList<CoinHistory> loadSavedHistory(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("SavedHistory", "");
        Type type = new TypeToken<ArrayList<CoinHistory>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void saveHistory(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(coinHistoryManager.getCoinHistoryArrayList());
        editor.putString("SavedHistory", json);
        editor.apply();
    }

    private void FlipBtn() {
        Button btn = findViewById(R.id.FlipBtn);
        btn.setOnClickListener(view -> {
            if (PlayerChoice == -1 || (NameOfPlayer.equals("") && !childManager.getChildArrayList().isEmpty())) {
                Toast.makeText(FlipCoinActivity.this, "Choice a side or Child", Toast.LENGTH_SHORT).show();
            } else {
                int pick = new Random().nextInt(2);
                WinOrLose = pick == PlayerChoice;
                ImageView coinView = findViewById(R.id.CoinImageView);

                RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                coinView.startAnimation(rotate);

                if (pick == 1) coinView.setImageResource(R.drawable.tail);
                else coinView.setImageResource(R.drawable.head);

                coinHistoryManager.addCoinHistory(new CoinHistory(LocalDateTime.now(), NameOfPlayer, PlayerChoice, WinOrLose));
                saveHistory(FlipCoinActivity.this);
            }
        });
    }

    private void historyBtn() {
        Button btn = findViewById(R.id.HistoryBtn);
        btn.setOnClickListener(view -> {
            Intent intent = FlipHistoryActivity.makeIntent(FlipCoinActivity.this);
            startActivity(intent);
        });
    }

    private void populateChildOptions() {
        RadioGroup group = findViewById(R.id.ChildOptions);
        if (childManager.getChildArrayList().isEmpty()) {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setText("NO CHILDREN");
            group.addView(textView);
        } else {
            for (Child i : childManager.getChildArrayList()) {
                RadioButton btn = new RadioButton(this);

                ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}},
                        new int[]{Color.WHITE, Color.WHITE}
                );

                btn.setTextColor(Color.WHITE);
                btn.setButtonTintList(colorStateList);

                btn.setOnClickListener(view -> NameOfPlayer = i.getName());

                btn.setText(i.toString());
                group.addView(btn);
            }
        }
    }

    private void playerChoiceBtn() {
        Button Head = findViewById(R.id.Head);
        TextView playerChoice = findViewById(R.id.ChildsChoiceTextView);
        Head.setOnClickListener(view -> {
            playerChoice.setText("Child's Choice: Head");
            PlayerChoice = 0;
        });

        Button Tail = findViewById(R.id.Tail);
        Tail.setOnClickListener(view -> {
            playerChoice.setText("Child's Choice: Tail");
            PlayerChoice = 1;
        });
    }
}