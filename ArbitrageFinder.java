package com.example.alexander.cryptarbitrage2;

import com.example.alexander.cryptarbitrage2.Coin;
import com.example.alexander.cryptarbitrage2.Exchange;
import com.example.alexander.cryptarbitrage2.HomePage;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/11/2018.
 */

public class ArbitrageFinder {
    private ArrayList<Exchange> allExchanges;
    private final int NUMBER_OPPROTUNITIES = 10;
    private MaxHeap bestOpprotunitiesWithinExchanges;


    public ArbitrageFinder() {
        bestOpprotunitiesWithinExchanges = new MaxHeap(NUMBER_OPPROTUNITIES);
    }

    public void addExchange(Exchange e) {
        allExchanges.add(e);
    }

    public ArrayList<Exchange> getAllExchanges() {
        return this.allExchanges;
    }

    public MaxHeap getBestOpprotunitiesWithinExchange(ArrayList<Exchange> listOfExchanges) {
        calculateBestOpprotunitiesWithinExchange();
        return bestOpprotunitiesWithinExchanges;
    }

    private void calculateBestOpprotunitiesWithinExchange() {
        for (Exchange exchange : HomePage.listOfExchanges) {
            for (Coin coin : exchange.getCoins()) { //120$/1Btc * 1LTC/10$ * xBTC/LTC  -- sell btc, buy ltc, exchange ltc for bitcoin
                //if coin can be converted to btc and converted to usd
                if (coin.getBidPriceBTC() > 0 && coin.getBidPriceUSD() > 0) {
                    //sell price of bitcoin / buy price usd of coin * convert coin to btc (starded with 1 btc, now hopefully 1.03)
                    //buy price of bitcoin / convert btc to coin * sell price of other coin - (
                    // gotta divide by buy price of btc for percentage gain)
                    double betterDeal = Math.max((exchange.getCoins().get(0).getBidPriceUSD() /
                            coin.getAskPriceUSD() * coin.getBidPriceBTC()), exchange.getCoins().get(0).getAskPriceUSD() *
                            coin.getAskPriceBTC() * coin.getBidPriceUSD() / exchange.getCoins().get(0).getAskPriceUSD());
                    //if this can yield a 1% gain
                    if (betterDeal > 1.01) {
                        bestOpprotunitiesWithinExchanges.insert(betterDeal);
                    }

                }
            }
        }

    }
    /**
     * Checks if way type one offers above a 1.5% arbitrage opprotunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opprotunity if a 1.5% opprotunity or higher exists, null if it doesn't
     */
    public Opprotunity typeOne(Coin coin, Coin bitcoin) {
        double type1Rate;

        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceBTC() > 0 && bitcoin.getBidPriceUSD() > 0) {
            type1Rate = coin.getAskPriceUSD() * coin.getBidPriceBTC() * bitcoin.getBidPriceUSD();
            if (type1Rate > 1.5) {
                Opprotunity newOpprotunity = new Opprotunity(type1Rate, 1, coin, coin);
            }
        }
        return null;
    }
    /**
     * Checks if way type two offers above a 1.5% arbitrage opprotunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opprotunity if a 1.5% opprotunity or higher exists, null if it doesn't
     */
    public Opprotunity typeTwo(Coin coin, Coin bitcoin) {
        double type2Rate;
        if (bitcoin.getAskPriceUSD() > 0 && coin.getAskPriceBTC() > 0 && coin.getBidPriceUSD() > 0) {
            type2Rate = bitcoin.getAskPriceUSD() * coin.getAskPriceBTC() * coin.getBidPriceUSD();
            if (type2Rate > 1.5) {
                Opprotunity newOpprotunitiy = new Opprotunity(type2Rate, 2, coin, coin);
                return newOpprotunitiy;
            }
        }
        return null;
    }
    /**
     * Checks if way type three offers above a 1.5% arbitrage opprotunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opprotunity if a 1.5% opprotunity or higher exists, null if it doesn't
     */
    public Opprotunity typeThree(Coin coin, Coin ethereum) {
        double type3Rate;
        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceBTC() > 0 && ethereum.getBidPriceUSD() > 0) {
            type3Rate = coin.getAskPriceUSD() * coin.getBidPriceBTC() * ethereum.getBidPriceUSD();
            if (type3Rate > 1.5) {
                Opprotunity newOpprotunity = new Opprotunity(type3Rate, 3, coin, coin);
            }
        }
        return null;
    }

    /**
     * Checks if way type four offers above a 1.5% arbitrage opprotunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opprotunity if a 1.5% opprotunity or higher exists, null if it doesn't
     */
    public Opprotunity typeFour(Coin coin, Coin ethereum) {
        double type4Rate;
        if (ethereum.getAskPriceUSD() > 0 && coin.getAskPriceBTC() > 0 && coin.getBidPriceUSD() > 0) {
            type4Rate = ethereum.getAskPriceUSD() * coin.getAskPriceBTC() * coin.getBidPriceUSD();
            if (type4Rate > 1.5) {
                Opprotunity newOpprotunitiy = new Opprotunity(type4Rate, 4, coin, coin);
                return newOpprotunitiy;
            }
        }
        return null;
    }
}