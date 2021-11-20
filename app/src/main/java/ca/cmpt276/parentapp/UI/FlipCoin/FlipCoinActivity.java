package ca.cmpt276.parentapp.UI.FlipCoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.UI.TimeoutTimer.TimeoutTimerActivity;
import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Child.ChildManager;
import ca.cmpt276.parentapp.model.Coin.CoinManager;

public class FlipCoinActivity extends AppCompatActivity {
    private ChildManager childManager;
    private CoinManager coinManager;
    private String PlayersName;

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);

        childManager = ChildManager.getInstance();
        coinManager = CoinManager.getInstance();

        pickKid();
    }

    private void pickKid() {
        Spinner dropdown = findViewById(R.id.pickchild);
        ArrayList<Child> ChildArray = childManager.getChildArrayList();
        ArrayList<String> items = new ArrayList<String>();
        for (Child i : ChildArray) {
            items.add(i.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PlayersName = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PlayersName = "No One Selected";
            }
        });

    }
}