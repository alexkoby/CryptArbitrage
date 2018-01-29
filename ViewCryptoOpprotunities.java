package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.util.Log;

/**
 * Created by Alexander on 1/7/2018.
 */

public class ViewCryptoOpprotunities extends Activity{



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_opprotunities);

        //addAsksAndBidsToCoins(HomePage.taskBitfinex, HomePage.bitfinex);
        //addAsksAndBidsToCoins(HomePage.taskBitStamp, HomePage.bitStamp);
        //addAsksAndBidsToCoins(HomePage.taskOKEX, HomePage.OKEX);
        addAsksAndBidsToCoins(HomePage.taskGDAX, HomePage.GDAX);
        //System.out.println("Homepage: " + MainActivity.isCreatedHomepage + "CryptoPage " +
        //        MainActivity.isCreatedCryptocurrencies + " Exchanges: " + MainActivity.isCreatedExchanges);

        System.out.print("Homepage size is: " + HomePage.taskBittrex.getExchangeCoinsAskUSD().size());
        System.out.println("AKA: " + HomePage.bittrex.getCoins().size());
    }


    /**
     * Transfers all of the data from the download task to the exchange
     * @param dt1 is the download task
     * @param e is the exchange
     */
    public void addAsksAndBidsToCoins(DownloadTask dt1, Exchange e){
        System.out.println(e.getName());
        for(Coin c1: e.getCoins()){
            int index = e.getCoins().indexOf(c1);
            c1.setBidPriceUSD(dt1.getExchangeCoinsBidUSD().get(index));
            c1.setAskPriceUSD(dt1.getExchangeCoinsAskUSD().get(index));
            c1.setBidPriceBTC(dt1.getExchangeCoinsBIDBTC().get(index));
            c1.setAskPriceBTC(dt1.getExchangeCoinsAskBTC().get(index));
            c1.setBidPriceETH(dt1.getExchangeCoinsBIDETH().get(index));
            c1.setAskPriceETH(dt1.getExchangeCoinsAskETH().get(index));

            System.out.println(c1.getName() + " " + c1.getAskPriceUSD() + " " + c1.getAskPriceBTC() + " " + c1.getAskPriceETH());

        }

    }




}
