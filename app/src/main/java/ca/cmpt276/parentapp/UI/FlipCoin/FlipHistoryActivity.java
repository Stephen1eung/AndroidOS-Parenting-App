package ca.cmpt276.parentapp.UI.FlipCoin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ca.cmpt276.parentapp.R;
import ca.cmpt276.parentapp.model.CoinHistory;
import ca.cmpt276.parentapp.model.CoinHistoryManager;

public class FlipHistoryActivity extends AppCompatActivity {
    CoinHistoryManager coinHistoryManager;

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_history);
        setTitle("Flip History");

        coinHistoryManager = CoinHistoryManager.getInstance();

        ListAllItem();
    }

    private void ListAllItem() {
        ArrayAdapter<CoinHistory> adapter = new FlipHistoryActivity.adapter();
        ListView list = findViewById(R.id.FlipHistoryListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<CoinHistory> {
        public adapter() {
            super(FlipHistoryActivity.this, R.layout.history_layout, coinHistoryManager.getCoinHistory());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_layout, parent, false);
            }

            CoinHistory currKid = coinHistoryManager.getCoinHistory().get(position);

            TextView txt = itemView.findViewById(R.id.CoinTextView);
            txt.setText(currKid.getPlayersName() + "\n" + currKid.getPlayerChoice());

            ImageView imageView = itemView.findViewById(R.id.CoinImageView);
            if (currKid.isWinOrNot()) {
                imageView.setImageResource(R.drawable.win);
            } else {
                imageView.setImageResource(R.drawable.lose);
            }

            return itemView;
        }
    }
}