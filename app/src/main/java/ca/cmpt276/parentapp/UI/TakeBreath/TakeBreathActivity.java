package ca.cmpt276.parentapp.UI.TakeBreath;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.UI.TimeoutTimer.TimeoutTimerActivity;

public class TakeBreathActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int NumOfBreaths = 3, HoldState = 0;

    public static Intent makeIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
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
        Button btn = findViewById(R.id.BreathBtn);
        btn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (HoldState == 0) {
                        HoldState = 1;
                        btn.setText("In");
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (HoldState == 1) {
                        HoldState = 2;

                    }
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void initDropDown() {
        Spinner spinner = findViewById(R.id.dropDownBreath);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(TakeBreathActivity.this, R.array.breathNums,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView numOfBreath = findViewById(R.id.NumOfBreathes);
        String text = adapterView.getItemAtPosition(i).toString();
        NumOfBreaths = Integer.parseInt(text);
        numOfBreath.setText(String.format("%s %s", getString(R.string.NumOfBreathTextView), text));
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}