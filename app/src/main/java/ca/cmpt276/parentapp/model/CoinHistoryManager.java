package ca.cmpt276.parentapp.model;

import java.util.ArrayList;
import java.util.Iterator;

public class CoinHistoryManager implements Iterable<CoinHistory> {
    private static final CoinHistoryManager instance = new CoinHistoryManager();
    private ArrayList<CoinHistory> coinHistoryArrayList = new ArrayList<>();

    private CoinHistoryManager() {
        if (coinHistoryArrayList == null) coinHistoryArrayList = new ArrayList<>();
    }

    public static CoinHistoryManager getInstance() {
        return instance;
    }

    public ArrayList<CoinHistory> getCoinHistoryArrayList() {
        if (coinHistoryArrayList == null) {
            coinHistoryArrayList = new ArrayList<>();
        }
        return this.coinHistoryArrayList;
    }

    public void setCoinHistoryArrayList(ArrayList<CoinHistory> coinHistoryArrayList) {
        this.coinHistoryArrayList = coinHistoryArrayList;
    }

    public void addCoinHistory(CoinHistory coinHistory) {
        if (coinHistoryArrayList == null) coinHistoryArrayList = new ArrayList<>();
        coinHistoryArrayList.add(coinHistory);
    }

    public void removeCoinHistory(int index) {
        coinHistoryArrayList.remove(index);
    }

    public CoinHistory getItem(int index) {
        return coinHistoryArrayList.get(index);
    }

    @Override
    public Iterator<CoinHistory> iterator() {
        return coinHistoryArrayList.iterator();
    }
}
