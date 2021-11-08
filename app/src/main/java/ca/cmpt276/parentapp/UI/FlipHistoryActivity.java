package ca.cmpt276.parentapp.UI;

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
    private CoinHistoryManager manger;
    
    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_history);
        setTitle("Flip History");

        manger = CoinHistoryManager.getInstance();
        
        listAllItems();
    }

    private void listAllItems() {
        ArrayAdapter<CoinHistory> adapter = new FlipHistoryActivity.adapter();
        ListView list = findViewById(R.id.HistoryList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class adapter extends ArrayAdapter<CoinHistory> {
        public adapter() {
            super(FlipHistoryActivity.this, R.layout.activity_flip_history, manger.getCoinHistoryArrayList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_flip_history, parent, false);
            }

            CoinHistory currKid = manger.getCoinHistoryArrayList().get(position);
            TextView txt = itemView.findViewById(R.id.ChildsName);
            txt.setText(currKid.getPlayerChoice()+"");

            return itemView;
        }
    }
    
}