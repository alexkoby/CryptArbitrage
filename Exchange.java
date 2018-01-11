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

    public Exchange(String name){
        coins = new ArrayList<>();
        asks = new ArrayList<>();
        bids = new ArrayList<>();
        this.name = name;
        amtCoins = 0;
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
}
