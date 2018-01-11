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

        addAsksAndBidsToCoins(HomePage.taskBitfinex, MainActivity.bitfinex);
        addAsksAndBidsToCoins(HomePage.taskBittrex, MainActivity.bitfinex);


        for(int i = 0; i < 16; i ++) {
            String test = MainActivity.bitfinex.getCoins().get(i).getName() + " ask price USD: " +
                    MainActivity.bitfinex.getCoins().get(i).getAskPriceUSD() +
                    "  ask price: BTC" + MainActivity.bitfinex.getCoins().get(i).getBidPriceBTC()
                    + " ask price: ETH" + MainActivity.bitfinex.getCoins().get(i).getAskPriceETH();
            String test2 = MainActivity.bittrex.getCoins().get(i).getName() + " ask price USD: " +
                    MainActivity.bittrex.getCoins().get(i).getAskPriceUSD() +
                    "  ask price: BTC" + MainActivity.bittrex.getCoins().get(i).getBidPriceBTC()
                    + " ask price: ETH" + MainActivity.bittrex.getCoins().get(i).getAskPriceETH();
            Log.d("Bitfinex", test);
            Log.d("Bittrex", test);
        }

    }

    //For every coin in exchange, add ask and bid price to the coin's data field
    public void addAsksAndBidsToCoins(DownloadTask dt1, Exchange e){
        for(Coin c1: e.getCoins()){
            int index = e.getCoins().indexOf(c1);
            c1.setBidPriceUSD(dt1.getExchangeCoinsBidUSD().get(index));
            c1.setAskPriceUSD(dt1.getExchangeCoinsAskUSD().get(index));
            c1.setBidPriceBTC(dt1.getExchangeCoinsBIDBTC().get(index));
            c1.setAskPriceBTC(dt1.getExchangeCoinsAskBTC().get(index));
            c1.setBidPriceETH(dt1.getExchangeCoinsBIDETH().get(index));
            c1.setAskPriceETH(dt1.getExchangeCoinsAskETH().get(index));
        }
    }




}
