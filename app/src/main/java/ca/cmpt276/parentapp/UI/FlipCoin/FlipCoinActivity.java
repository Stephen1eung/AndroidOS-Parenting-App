package ca.cmpt276.parentapp.UI.FlipCoin;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.Child;
import ca.cmpt276.parentapp.model.ChildManager;
import ca.cmpt276.parentapp.model.CoinHistory;
import ca.cmpt276.parentapp.model.CoinHistoryManager;

public class FlipCoinActivity extends AppCompatActivity {
    private String PlayersName;
    private int PlayerChoice = -1; // 0 = head, 1 = tail

    private CoinHistoryManager coinHistoryManager;
    private ChildManager childManager;

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        setTitle(R.string.FlipCoinTitle);

        childManager = ChildManager.getInstance();
        coinHistoryManager = CoinHistoryManager.getInstance();

        PlayersName = "";

        FlipBtn();
        PlayerPickBtn();
        FlipHistoryBtn();
        PopulateChildrenOptions();
    }

    private void FlipHistoryBtn() {
        Button HistoryBtn = findViewById(R.id.FlipHistoryBtn);
        HistoryBtn.setOnClickListener(view -> {
            Intent intent = FlipHistoryActivity.makeIntent(FlipCoinActivity.this);
            startActivity(intent);
        });
    }

    private void FlipBtn() {
        Button FlipBtn = findViewById(R.id.FLIP);

        FlipBtn.setOnClickListener(view -> {
            if (PlayersName.equals("") || PlayerChoice == -1) {
                Toast.makeText(FlipCoinActivity.this, "Pick a kid or side", Toast.LENGTH_SHORT).show();
            } else {
                ImageView coinImage = findViewById(R.id.CoinFlipImage);

                int pick = new Random().nextInt(2);
                if (pick == 1) coinImage.setImageResource(R.drawable.head);
                else coinImage.setImageResource(R.drawable.tail);

                RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF,
                        0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                coinImage.startAnimation(rotate);

                CoinHistory newCoinHistory = new CoinHistory(LocalDateTime.now(), PlayersName, PlayerChoice,
                        PlayerChoice == pick);
                Toast.makeText(FlipCoinActivity.this, newCoinHistory.toString()+ "", Toast.LENGTH_SHORT).show();
                coinHistoryManager.addCoinHistory(newCoinHistory);
            }
        });
    }

    private void PlayerPickBtn() {
        TextView childPick = findViewById(R.id.pickAChild);
        Button head = findViewById(R.id.HEAD);
        head.setOnClickListener(view -> {
            PlayerChoice = 0;
            childPick.setText("Child's Pick: Head");
        });

        Button tail = findViewById(R.id.TAIL);
        tail.setOnClickListener(view -> {
            PlayerChoice = 1;
            childPick.setText("Child's Pick: Tail");
        });
    }

    private void PopulateChildrenOptions() {
        RadioGroup group = findViewById(R.id.ChildernRadioGroup);
        ArrayList<Child> ChildArray = childManager.getChildArrayList();

        if (ChildArray.isEmpty()) {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setText("No Children");
            group.addView(textView);
        } else {
            for (Child i : ChildArray) {
                RadioButton btn = new RadioButton(this);
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}},
                        new int[]{Color.WHITE, Color.WHITE}
                );

                btn.setTextColor(Color.WHITE);
                btn.setButtonTintList(colorStateList);
                btn.setText(i.getName());

                btn.setOnClickListener(view -> PlayersName = i.getName());

                group.addView(btn);
            }
        }
    }
}