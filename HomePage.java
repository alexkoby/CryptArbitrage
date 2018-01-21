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

    static DownloadTask taskBitfinex;
    static DownloadTask taskBittrex;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //If this is the first time visiting the homepage
        if(!MainActivity.isCreatedHomepage) {
            listOfExchanges = new ArrayList<>();
            bitfinex = new Exchange("Bitfinex");
            bittrex = new Exchange("Bittrex");

            //taskBitfinex = new DownloadTask("Bitfinex");
            taskBittrex = new DownloadTask("Bittrex",
                    "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);

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
            System.out.println("Went here" + MainActivity.isCreatedExchanges);
            getAsksAndBids(taskBittrex, HomePage.bittrex, "https://bittrex.com/api/v1.1/public/getmarketsummaries");
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
                    APIs[3*i+1] = API.concat(e.getCoins().get(i).getAbbreviation().concat("BTC"));
                    APIs[3*i+2] = API.concat(e.getCoins().get(i).getAbbreviation().concat("ETH"));
                    break;
                case "Bittrex":
                    APIs[3*i] = "USDT-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i+1] = "BTC-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i+2] = "ETH-".concat(e.getCoins().get(i).getAbbreviation());
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
        taskBittrex = new DownloadTask("Bittrex",
                "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
    }


}
