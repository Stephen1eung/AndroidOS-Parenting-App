package ca.cmpt276.parentapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public class CoinHistoryManager implements Iterable<CoinHistory> {
    private static final CoinHistoryManager instance = new CoinHistoryManager();
    private ArrayList<CoinHistory> coinHistoryArrayList = new ArrayList<>();

    private CoinHistoryManager() {

    }

    public static CoinHistoryManager getInstance() {
        return instance;
    }

    public ArrayList<CoinHistory> getCoinHistory() {
        return coinHistoryArrayList;
    }

    public void setCoinHistoryArrayList(ArrayList<CoinHistory> coinHistoryArrayList) {
        this.coinHistoryArrayList = coinHistoryArrayList;
    }

    public void addCoinHistory(CoinHistory coinHistory) {
        coinHistoryArrayList.add(coinHistory);
    }

    public void removeCoinHistory(int index) {
        coinHistoryArrayList.remove(index);
    }

    @NonNull
    @Override
    public Iterator<CoinHistory> iterator() {
        return coinHistoryArrayList.iterator();
    }
}
