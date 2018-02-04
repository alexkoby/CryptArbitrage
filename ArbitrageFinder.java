package com.example.alexander.cryptarbitrage2;
import android.graphics.Path;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/11/2018.
 */

public class ArbitrageFinder {
    private final int NUMBER_OPPORTUNITIES = 15;
    private PriorityQueue<Opportunity> bestOpportunitiesWithinExchanges;
    private PriorityQueue<Opportunity> bestOpportunitiesAcrossExchanges;
    private double goalReturn;

    public ArbitrageFinder(double goalReturn) {
        this.goalReturn = goalReturn;
        bestOpportunitiesWithinExchanges = new PriorityQueue(NUMBER_OPPORTUNITIES, new Comparator<Opportunity>() {
            @Override
            public int compare(Opportunity o1, Opportunity o2) {
                if(o1.getPercentGain() > o2.getPercentGain()){
                    return 1;
                }
                else if (o1.getPercentGain() < o2.getPercentGain()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        bestOpportunitiesAcrossExchanges = new PriorityQueue(NUMBER_OPPORTUNITIES, new Comparator<Opportunity>() {
            @Override
            public int compare(Opportunity o1, Opportunity o2) {
                if(o1.getPercentGain() > o2.getPercentGain()){
                    return 1;
                }
                else if (o1.getPercentGain() < o2.getPercentGain()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
    }
    public PriorityQueue getBestOpportunitiesWithinExchange() {
        calculateBestOpportunitiesWithinExchange();
        return this.bestOpportunitiesWithinExchanges;
    }
    public PriorityQueue getBestOpportunitiesAcrossExchange(){
        calculateBestOpportunitiesCrossExchange();
        return this.bestOpportunitiesAcrossExchanges;
    }

    private void calculateBestOpportunitiesCrossExchange(){
        Opportunity opportunity;
        if(HomePage.listOfCurrencies.size() == 0){
            //tell user to select more currencies
            return;
        }
        if(HomePage.listOfExchanges.size() <= 1){
            //tell user to select more exchanges - need at least two to tango
            return;
        }
        for(String coin: HomePage.listOfCurrencies){
            opportunity = typeSeven(coin);
            if(opportunity != null){
                bestOpportunitiesAcrossExchanges.add(opportunity);
            }
        }
    }

    private void calculateBestOpportunitiesWithinExchange() {
        Opportunity opportunity;
        Coin bitcoin;
        Coin ethereum;
        if(HomePage.listOfExchanges.size() == 0){
            //Tell user to select more Exchanges
            return;
        }
        for (Exchange exchange : HomePage.listOfExchanges) {
            bitcoin = exchange.getCoins().get(0);
            ethereum = exchange.getCoins().get(1);
            //don't want to perform function
            if(exchange.getCoins().size() == 0){
                continue;
            }
            for (Coin coin : exchange.getCoins()) {
                opportunity = typeOne(coin, bitcoin );
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeTwo(coin, bitcoin);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeThree(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeFour(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeFive(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeSix(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
            }
        }
    }
    /**
     * Checks if way type one offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeOne(Coin coin, Coin bitcoin) {
        double type1Rate;

        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceBTC() > 0 && bitcoin.getBidPriceUSD() > 0) {
            type1Rate = (1/coin.getAskPriceUSD()) * coin.getBidPriceBTC() * bitcoin.getBidPriceUSD();
            if (type1Rate - 1 > goalReturn / 100) {
                return new Opportunity(type1Rate, 1, coin, bitcoin);
            }
        }
        return null;
    }
    /**
     * Checks if way type two offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeTwo(Coin coin, Coin bitcoin) {
        double type2Rate;
        if (bitcoin.getAskPriceUSD() > 0 && coin.getAskPriceBTC() > 0 && coin.getBidPriceUSD() > 0) {
            type2Rate = (1/bitcoin.getAskPriceUSD()) * (1/coin.getAskPriceBTC()) * coin.getBidPriceUSD();
            if (type2Rate - 1 > goalReturn / 100) {
                return new Opportunity(type2Rate, 2, coin, bitcoin);
            }
        }
        return null;
    }
    /**
     * Checks if way type three offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeThree(Coin coin, Coin ethereum) {
        double type3Rate;
        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceBTC() > 0 && ethereum.getBidPriceUSD() > 0) {
            type3Rate = (1/coin.getAskPriceUSD()) * coin.getBidPriceETH() * ethereum.getBidPriceUSD();
            if (type3Rate - 1 > goalReturn / 100) {
                return new Opportunity(type3Rate, 3, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeFour(Coin coin, Coin ethereum) {
        double type4Rate;
        if (ethereum.getAskPriceUSD() > 0 && coin.getAskPriceETH() > 0 && coin.getBidPriceUSD() > 0) {
            type4Rate = (1/ethereum.getAskPriceUSD()) * (1/coin.getAskPriceETH()) * coin.getBidPriceUSD();
            if (type4Rate - 1> goalReturn / 100) {
                return new Opportunity(type4Rate, 4, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange you're testing
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeFive(Coin coin, Coin ethereum) {
        double type5Rate;
        if(coin.getAskPriceETH() > 0 && coin.getBidPriceBTC() > 0 && ethereum.getAskPriceBTC() > 0){
            type5Rate = (1/coin.getAskPriceETH()) * coin.getBidPriceBTC() * ethereum.getAskPriceBTC();
            if(type5Rate - 1> goalReturn / 100) {
                return new Opportunity(type5Rate, 5, coin, ethereum);
            }

        }
        return null;
    }
    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange you're testing
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeSix(Coin coin, Coin ethereum) {
        double type6Rate;
        if(coin.getAskPriceBTC() > 0 && coin.getBidPriceETH() > 0 && ethereum.getBidPriceBTC() > 0) {
            type6Rate = (1/coin.getAskPriceBTC()) * coin.getBidPriceETH() * ethereum.getBidPriceBTC();
            if(type6Rate -1 > goalReturn / 100) {
                return new Opportunity(type6Rate, 6, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type seven offers above a 'goalReturn' arbitrage opportunitiy
     * @param coinName is the coin you're testing
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeSeven(String coinName){
        double minAskUSD = 1000000;
        Coin coinMinAskUSD = null;
        double maxBidUSD = 0;
        Coin coinMaxBidUSD = null;

        for(Exchange exchange: HomePage.listOfExchanges){
            for(Coin coin: exchange.getCoins()){
                if(coin.getName().equals(coinName)){
                    if(coin.getBidPriceUSD() > maxBidUSD){
                        maxBidUSD = coin.getBidPriceUSD();
                        coinMaxBidUSD = coin;
                    }
                    if(coin.getAskPriceUSD() > 0 && coin.getAskPriceUSD() < minAskUSD){
                        minAskUSD = coin.getAskPriceUSD();
                        coinMinAskUSD = coin;
                    }
                    break;
                }
            }
        }
        if(coinMaxBidUSD == null || coinMinAskUSD == null){
            return null;
        }
        if(maxBidUSD/minAskUSD - 1 > goalReturn/100){
            return new Opportunity(maxBidUSD/minAskUSD,7,coinMaxBidUSD,coinMinAskUSD);
        }
        return null;
    }
}