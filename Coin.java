package com.example.alexander.cryptarbitrage2;


/**
 * Created by Alexander on 1/9/2018.
 */

public class Coin {

    String name;
    String abbreviation;
    String exchange;
    Double askPriceUSD;
    Double bidPriceUSD;
    Double askPriceBTC;
    Double bidPriceBTC;
    Double askPriceETH;
    Double bidPriceETH;


    /**
     * Constructor
     * @param name is the name of the coin
     */
    public Coin(String name, String abbreviation, String exchange){
        this.name = name;
        this.abbreviation = abbreviation;
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double getAskPriceUSD() {
        return askPriceUSD;
    }

    public void setAskPriceUSD(Double askPriceUSD) {
        this.askPriceUSD = askPriceUSD;
    }

    public Double getBidPriceUSD() {
        return bidPriceUSD;
    }

    public void setBidPriceUSD(Double bidPriceUSD) {
        this.bidPriceUSD = bidPriceUSD;
    }

    public Double getAskPriceBTC() {
        return askPriceBTC;
    }

    public void setAskPriceBTC(Double askPriceBTC) {
        this.askPriceBTC = askPriceBTC;
    }

    public Double getBidPriceBTC() {
        return bidPriceBTC;
    }

    public void setBidPriceBTC(Double bidPriceBTC) {
        this.bidPriceBTC = bidPriceBTC;
    }

    public Double getAskPriceETH() {
        return askPriceETH;
    }

    public void setAskPriceETH(Double askPriceETH) {
        this.askPriceETH = askPriceETH;
    }

    public Double getBidPriceETH() {
        return bidPriceETH;
    }

    public void setBidPriceETH(Double bidPriceETH) {
        this.bidPriceETH = bidPriceETH;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }








}
