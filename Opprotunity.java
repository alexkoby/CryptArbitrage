package com.example.alexander.cryptarbitrage2;

/**
 * Created by Alexander on 2/1/2018.
 */

public class Opprotunity {
    private double percentGain;
    private int type;
    /*type 1 = usd * (coin/USD) * (btc/coin) * (usd/btc), type 2 = usd * (btc/usd) * (coin/btc) * (usd/coin)
      type 3 = usd * (coin/USD) * (eth/coin) * (usd/eth), type 4 = usd * (eth/usd) * (coin/eth) * (usd/coin)
      type 5 = eth * (coin/eth) * (btc/coin) * (eth/btc), type 6 = btc * (coin/btc) * (eth/coin) * (btc/eth)
      type 6 = buy at Exchange1, sell at Exchange two
    */
    private Coin firstExchange;
    private Coin secondExchange;
    public Opprotunity(double percentGain, int type, Coin firstExchange, Coin secondExchange){
        this.percentGain = percentGain;
        this.type = type;
        this.firstExchange = firstExchange;
        this.secondExchange = secondExchange;
    }

    public double getPercentGain() {
        return percentGain;
    }

    public void setPercentGain(double percentGain) {
        this.percentGain = percentGain;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Coin getFirstExchange() {
        return firstExchange;
    }

    public void setFirstExchange(Coin firstExchange) {
        this.firstExchange = firstExchange;
    }

    public Coin getSecondExchange() {
        return secondExchange;
    }

    public void setSecondExchange(Coin secondExchange) {
        this.secondExchange = secondExchange;
    }
}
