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
import ca.cmpt276.parentapp.model.Coin.Coin;
import ca.cmpt276.parentapp.model.Coin.CoinManager;

public class FlipHistory extends AppCompatActivity {
    private TextView NoHistoryTextView;
    private CoinManager coinManager;
    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipHistory.class);
    }

    private void initItems() {
        coinManager = CoinManager.getInstance();
        NoHistoryTextView = findViewById(R.id.NoHistoryTextView);
    }

    @Override
    protected void onStart() {
        initItems();
        if (coinManager.getCoinHistory().isEmpty()) {
            NoHistoryTextView.setVisibility(View.VISIBLE);
        } else {
            NoHistoryTextView.setVisibility(View.INVISIBLE);
        }
        listCoins();
        super.onStart();
    }

    private void listCoins() {
        ArrayAdapter<Coin> adapter = new adapter();
        ListView list = findViewById(R.id.FlipHistoryListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<Coin> {
        public adapter() {
            super(FlipHistory.this, R.layout.flip_history_list_view, coinManager.getCoinHistory());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.flip_history_list_view, parent, false);

            Coin currCoin = coinManager.getCoinHistory().get(position);
            TextView txt = itemView.findViewById(R.id.CoinTextView);
            txt.setText(currCoin.toString());

            ImageView winOrNotImage = itemView.findViewById(R.id.CoinImageView);

            if (currCoin.isWinOrNot()) {
                winOrNotImage.setImageResource(R.drawable.win);
            } else {
                winOrNotImage.setImageResource(R.drawable.lose);
            }

            return itemView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_history);

        initItems();
        listCoins();
    }


}