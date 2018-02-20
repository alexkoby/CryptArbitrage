package com.example.alexander.cryptarbitrage2;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/9/2018.
 */

public class Exchange {
    private ArrayList<Coin> coins;
    private ArrayList<Double> asks;
    private ArrayList<Double> bids;
    private String name;
    private int amtCoins;
    private String askSymbol;
    private String bidSymbol;
    private boolean exchangeAPISorted;
    private boolean dataIsFinishedRefreshing;
    private boolean isUSD;
    public Exchange(String name, String askSymbol, String bidSymbol, boolean exchangeAPISorted, boolean dataIsFinishedRefreshing,
                    boolean isdUSD){
        coins = new ArrayList<>();
        asks = new ArrayList<>();
        bids = new ArrayList<>();
        this.name = name;
        amtCoins = 0;
        this.dataIsFinishedRefreshing = dataIsFinishedRefreshing;

        this.askSymbol = askSymbol;
        this.bidSymbol = bidSymbol;

        this.exchangeAPISorted = exchangeAPISorted;

        this.isUSD = isdUSD;
    }


    public void addCoin(Coin c){
        coins.add(c);
        amtCoins++;
    }

    public void removeCoin(Coin c){
        if(amtCoins<=0){
            return;
        }
        coins.remove(c);
        amtCoins--;
    }
    public boolean getIsUSD(){
        return this.isUSD;
    }
    public String getName(){
        return this.name;
    }

    public ArrayList<Coin> getCoins(){
        return this.coins;
    }
    public void addAsk(Double asks){
        this.asks.add(asks);
    }
    public ArrayList<Double> getAsks(){
        return this.asks;
    }

    public void addBid(Double bids){
        this.bids.add(bids);
    }
    public ArrayList<Double> getBids(){
        return this.bids;
    }

    public String getAskSymbol(){
        return this.askSymbol;
    }
    public String getBidSymbol(){
        return this.bidSymbol;
    }
    public boolean isExchangeAPISorted(){
        return this.exchangeAPISorted;
    }

    public boolean isDataFinishedRefreshing(){
        return this.dataIsFinishedRefreshing;
    }
    public void setDataIsFinishedRefreshing(boolean value){
        this.dataIsFinishedRefreshing = value;
    }
}
