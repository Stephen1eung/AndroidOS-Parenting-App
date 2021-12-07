package ca.cmpt276.parentapp.UI.TakeBreath;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.parentapp.R;

public class TakeBreathActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final State BreathInState = new In();
    private final State BreathOutState = new Out();
    String buttonText;
    TextView HelpText;
    Button breathBtn;
    TextView numOfBreath;
    ObjectAnimator scaleUpX, scaleDownX;
    ObjectAnimator scaleUpY, scaleDownY;
    AnimatorSet scaleUp, scaleDown;
    MediaPlayer sound;
    private final State startState = new StartState();
    private final State finishState = new FinishState();
    private State CurrState = startState;
    private int NumOfBreaths = 3, HoldState = 0;
    public static Intent makeIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }

    @Override
    protected void onStart() {
        loadBreaths();
        super.onStart();
    }

    @Override
    protected void onStop() {
        saveBreaths(NumOfBreaths);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        saveBreaths(NumOfBreaths);
        super.onDestroy();
    }

    private void loadBreaths() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TakeBreathActivity.this);
        NumOfBreaths = sp.getInt("SavedBreaths", 3);
    }

    private void saveBreaths(int n) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TakeBreathActivity.this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("SavedBreaths", n);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
        setTitle("Take Breath");
        initDropDown();
        startBreathBtn();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void startBreathBtn() {
        breathBtn = findViewById(R.id.BreathBtn);
        buttonText = breathBtn.getText().toString();
        breathBtn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (HoldState == 0) {
                        if (buttonText.equals("In")){
                            animationPlayUp();
                        }
                        else if (buttonText.equals("Out")){
                            animationPlayDown();
                        }
                        HoldState = 1;
                        CurrState.handleClickOnButton();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (HoldState == 1) {
                        HoldState = 2;
                        CurrState.handleClickOnButton();
                    }
                case MotionEvent.ACTION_UP:
                    if (HoldState == 1 || HoldState == 2) {
                        HoldState = 0;
                        CurrState.handleClickOnButton();
                        if (buttonText.equals("In")){
                            scaleUp.pause();
                        }
                        else{
                            scaleDown.pause();
                        }
                        sound.stop();
                    }
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void animationPlayDown() {
        scaleDownX = ObjectAnimator.ofFloat(breathBtn,"scaleX",1f).setDuration(2000);
        scaleDownY = ObjectAnimator.ofFloat(breathBtn,"scaleY",1f).setDuration(2000);
        scaleDown = new AnimatorSet();

        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();
    }

    private void animationPlayUp() {
        scaleUpX = ObjectAnimator.ofFloat(breathBtn,"scaleX",2.5f).setDuration(2000);
        scaleUpY = ObjectAnimator.ofFloat(breathBtn,"scaleY",2.5f).setDuration(2000);
        scaleUp = new AnimatorSet();

        scaleUp.play(scaleUpX).with(scaleUpY);
        scaleUp.start();
    }

    private void initDropDown() {
        loadBreaths();
        Spinner spinner = findViewById(R.id.dropDownBreath);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(TakeBreathActivity.this, R.array.breathNums,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(NumOfBreaths);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        numOfBreath = findViewById(R.id.numOfBreath);
        String text = adapterView.getItemAtPosition(i).toString();
        NumOfBreaths = adapterView.getSelectedItemPosition();
        numOfBreath.setText(String.format("%s %s", getString(R.string.NumOfBreathTextView), text));
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // ***********************************************************
    // Taken from Dr. Fraser's state problem demo
    // ***********************************************************

    private void setState(State newState) {
        CurrState.handleEnter();
        CurrState.handleExit();
        CurrState = newState;
    }

    private abstract class State {
        void handleEnter() {
        }

        void handleClickOnButton() {
        }

        void handleClickOffButton() {
        }

        void handleExit() {
        }
    }

    private class StartState extends State {
        @Override
        void handleEnter() {
            if (NumOfBreaths > 0) {
                numOfBreath.setText(String.format("%s %s", getString(R.string.NumOfBreathTextView), NumOfBreaths));
            }
        }

        @Override
        void handleClickOnButton() {
            setState(BreathInState);
            BreathInState.handleClickOnButton();
        }

        @Override
        void handleExit() {
            HelpText = findViewById(R.id.HelpText);
            breathBtn = findViewById(R.id.BreathBtn);
            if (NumOfBreaths > 0) {
                numOfBreath.setText(String.format("%s %s", getString(R.string.NumOfBreathTextView), NumOfBreaths));
                breathBtn.setText("In");

                HelpText.setText("Hold Button to breath In");
            } else {
                HelpText.setText("Good Job!");
                numOfBreath.setText("DONE!!!");
                HelpText.setText("");
            }
        }
    }

    private class FinishState extends State {
        @Override
        void handleEnter() {
            super.handleEnter();
        }

        @Override
        void handleClickOnButton() {
            super.handleClickOnButton();
            setState(finishState);
        }
    }

    private class In extends State {
        Handler handler = new Handler();
        Runnable runnable = () -> setState(BreathOutState);

        @Override
        void handleEnter() {
            breathBtn.setText("In");
            HelpText.setText("Breathe In");
            buttonText = breathBtn.getText().toString();
        }

        @Override
        void handleClickOnButton() {
            super.handleClickOnButton();

            sound = MediaPlayer.create(TakeBreathActivity.this, R.raw.calm);
            handler.removeCallbacks(runnable);
            sound.start();

            handler.postDelayed(runnable, 3000);
            Toast.makeText(TakeBreathActivity.this, "3 seconds have passed, you may release the button ", Toast.LENGTH_SHORT);
            //breathBtn.animate().scaleX(2.5f).scaleY(2.5f).setDuration(10000);
            animationPlayUp();
            sound.stop();

            // for sound new Handler(Looper.getMainLooper()).postDelayed(() -> )
            setState(finishState);

        }

        @Override
        void handleClickOffButton() {
            handler.removeCallbacks(runnable);
        }


    }

    private class Out extends State {
        Handler handler = new Handler();
        Runnable runnable = () -> setState(BreathInState);

        @Override
        void handleEnter() {
            breathBtn.setText("Out");
            HelpText.setText("Breathe Out");
            buttonText = breathBtn.getText().toString();
        }

        @Override
        void handleClickOnButton() {
            super.handleClickOnButton();
            sound = MediaPlayer.create(TakeBreathActivity.this, R.raw.calm);
            handler.removeCallbacks(runnable);
            sound.start();

            handler.postDelayed(runnable, 3000);
            Toast.makeText(TakeBreathActivity.this, "3 seconds have passed, you may release the button ", Toast.LENGTH_SHORT);
            //breathBtn.animate().scaleX(1f).scaleY(1f).setDuration(10000);
            animationPlayDown();

            sound.stop();


            // for sound new Handler(Looper.getMainLooper()).postDelayed(() -> )
            setState(finishState);
        }

        @Override
        void handleClickOffButton() {
            handler.removeCallbacks(runnable);
        }


    }
}