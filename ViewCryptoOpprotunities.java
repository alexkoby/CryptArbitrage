package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.util.Log;
import java.util.PriorityQueue;
/**
 * Created by Alexander on 1/7/2018.
 */

public class ViewCryptoOpprotunities extends Activity{
    ArbitrageFinder arbitrageFinder;
    PriorityQueue<Opportunity> bestOpportunitiesWithinExchanges;
    PriorityQueue<Opportunity> bestOpportunitiesAcrossExchanges;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_opprotunities);

        arbitrageFinder = new ArbitrageFinder(HomePage.minGainsWanted);
        if(HomePage.typeOfArbitrageString.equals("Inter-Exchange and Cross Exchange Arbitrage")) {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();
        }
        else if (HomePage.typeOfArbitrageString.equals("Inter-Exchange Arbitrage Only")) {
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();
        }
        else {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();
        }
        while(bestOpportunitiesAcrossExchanges.size() > 0){
            System.out.println(bestOpportunitiesAcrossExchanges.peek().getPercentGain() + "% at " +
                    bestOpportunitiesAcrossExchanges.peek().getLowPriceCoinExchange().getExchange() + " to" +
            bestOpportunitiesAcrossExchanges.peek().getHighPriceCoinExchange().getExchange() + " Coin is : " +
            bestOpportunitiesAcrossExchanges.poll().getHighPriceCoinExchange().getName());
        }
        while(bestOpportunitiesWithinExchanges.size() > 0){
            System.out.println(bestOpportunitiesWithinExchanges.peek().getPercentGain() + "% at " +
                    bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getName()+ " Coin is : " +
                    bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getExchange() + " Type" +
            bestOpportunitiesWithinExchanges.poll().getType());
        }
    }





}
