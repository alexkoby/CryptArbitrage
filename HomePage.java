package com.example.alexander.cryptarbitrage2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */

public class HomePage extends Activity implements View.OnClickListener {
    static ArrayList<Exchange> listOfExchanges;
    static Exchange bitfinex;
    static Exchange bittrex;
    static Exchange binance;
    static Exchange hitBTC;
    static Exchange bitZ;
    static Exchange poloniex;

    static DownloadTask taskBitfinex;
    static DownloadTask taskBittrex;
    static DownloadTask taskBinance;
    static DownloadTask taskHitBTC;
    static DownloadTask taskBitZ;
    static DownloadTask taskPoloniex;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //If this is the first time visiting the homepage
        if(!MainActivity.isCreatedHomepage) {
            listOfExchanges = new ArrayList<>();
            bitfinex = new Exchange("Bitfinex", "ask", "bid", false);
            bittrex = new Exchange("Bittrex", "Ask", "Bid", true);
            binance = new Exchange("Binance", "price", "price", false);
            hitBTC = new Exchange("HitBTC", "ask", "bid", false);
            bitZ = new Exchange("Bit-Z","sell","buy",false);
            poloniex = new Exchange("Poloniex","lowestAsk", "highestBid",false);

            taskBitfinex = new DownloadTask(null,"https://api.bitfinex.com/v1/pubticker/", bitfinex );
            taskBittrex = new DownloadTask("MarketName",
                    "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
            taskBinance = new DownloadTask("symbol", "https://www.binance.com/api/v1/ticker/allPrices",
                    binance);
            taskHitBTC = new DownloadTask("symbol","https://api.hitbtc.com/api/2/public/ticker", hitBTC);
            taskBitZ = new DownloadTask("","https://www.bit-z.com/api_v1/tickerall", bitZ);
            taskPoloniex = new DownloadTask("","https://poloniex.com/public?command=returnTicker",poloniex);

        }


        //set up click listeners for buttons on home page
        View viewCurrentOpprotunities = findViewById(R.id.view_current_opprotunities);
        viewCurrentOpprotunities.setOnClickListener(this);

        View modifyExchanges = findViewById(R.id.modify_exchanges);
        modifyExchanges.setOnClickListener(this);

        View modifyCryptocurrencies = findViewById(R.id.modify_cryptocurrencies);
        modifyCryptocurrencies.setOnClickListener(this);


        MainActivity.isCreatedHomepage = true;
    }
    @Override
    public void onStart(){
        super.onStart();
        recreateTasks();
System.out.println(MainActivity.isCreatedCryptocurrencies + " Crypto " + MainActivity.isCreatedExchanges + "Exchanges");
        if(MainActivity.isCreatedExchanges  && MainActivity.isCreatedCryptocurrencies) {
            //getAsksAndBids(taskBitfinex, HomePage.bitfinex, "https://api.bitfinex.com/v1/pubticker/");
            //getAsksAndBids(taskBittrex, HomePage.bittrex, "https://bittrex.com/api/v1.1/public/getmarketsummaries");
            //getAsksAndBids(taskBinance, HomePage.binance, "https://www.binance.com/api/v1/ticker/allPrices");
            //getAsksAndBids(taskHitBTC, HomePage.hitBTC, "https://api.hitbtc.com/api/2/public/ticker");
            //getAsksAndBids(taskBitZ,HomePage.bitZ,"https://www.bit-z.com/api_v1/tickerall");
            getAsksAndBids(taskPoloniex, HomePage.poloniex, "https://poloniex.com/public?command=returnTicker");
        }
    }


    //Creates an Array of URLs and calls downloadtask.execute()
    public void getAsksAndBids(DownloadTask task, Exchange e, String API){

        String [] APIs = new String [e.getCoins().size()*3];

        System.out.println(e.getCoins().size());
        for(int i = 0; i < e.getCoins().size(); i+=1){
            switch (e.getName()){
                case "Bitfinex":
                    APIs[3*i] = API.concat(e.getCoins().get(i).getAbbreviation().concat("USD"));
                    APIs[3*i + 1] = API.concat(e.getCoins().get(i).getAbbreviation().concat("BTC"));
                    APIs[3*i+2] = API.concat(e.getCoins().get(i).getAbbreviation().concat("ETH"));
                    break;
                case "Bittrex":
                    APIs[3*i] = "USDT-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 1] = "BTC-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 2] = "ETH-".concat(e.getCoins().get(i).getAbbreviation());
                    break;
                case "Binance":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USDT");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    break;
                case "HitBTC":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    break;
                case "Bit-Z":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("_usdt");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("_btc");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("_eth");
                    break;
                case "Poloniex":
                    APIs[3*i] = "USDT_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 1] = "BTC_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 2] = "ETH_".concat(e.getCoins().get(i).getAbbreviation());
                    break;
            }

        }
        task.execute(APIs);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.view_current_opprotunities:
                Intent j = new Intent(this, ViewCryptoOpprotunities.class);
                startActivity(j);

                break;
            case R.id.modify_exchanges:
                Intent i = new Intent(this,Exchanges.class);
                startActivity(i);
                break;

            case R.id.modify_cryptocurrencies:
                Intent k = new Intent(this, Cryptocurrencies.class);
                startActivity(k);
                break;
        }
    }

    public void recreateTasks(){
        //taskBitfinex = new DownloadTask("Bitfinex");
        taskBittrex = new DownloadTask("MarketName",
                "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
        taskBinance = new DownloadTask("symbol","https://www.binance.com/api/v1/ticker/allPrices", binance);
    }
}
