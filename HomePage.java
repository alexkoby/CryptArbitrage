package com.example.alexander.cryptarbitrage2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Alexander on 1/7/2018.
 */

public class HomePage extends Activity implements View.OnClickListener {
    static DownloadTask taskBitfinex;
    static DownloadTask taskBittrex;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //set up click listeners for buttons on home page
        View viewCurrentOpprotunities = findViewById(R.id.view_current_opprotunities);
        viewCurrentOpprotunities.setOnClickListener(this);

        View modifyExchanges = findViewById(R.id.modify_exchanges);
        modifyExchanges.setOnClickListener(this);

        View modifyCryptocurrencies = findViewById(R.id.modify_cryptocurrencies);
        modifyCryptocurrencies.setOnClickListener(this);

        taskBitfinex = new DownloadTask("Bitfinex");
        taskBittrex = new DownloadTask("Bittrex");

        getAsksAndBids(taskBitfinex, MainActivity.bitfinex, "https://api.bitfinex.com/v1/pubticker/");
//        getAsksAndBids(taskBittrex, MainActivity.bittrex, "https://bittrex.com/api/v1.1/public/getticker?market=" );

    }


    //Creates an Array of URLs and calls downloadtask.execute()
    public void getAsksAndBids(DownloadTask task, Exchange e, String API){

        String [] APIs = new String [e.getCoins().size()*3];

        for(int i = 0; i < e.getCoins().size(); i+=1){
            switch (e.getName()){
                case "Bitfinex":
                    APIs[3*i] = API.concat(e.getCoins().get(i).getAbbreviation().concat("USD"));
                    APIs[3*i+1] = API.concat(e.getCoins().get(i).getAbbreviation().concat("BTC"));
                    APIs[3*i+2] = API.concat(e.getCoins().get(i).getAbbreviation().concat("ETH"));
                    break;
                case "Bittrex":
                    APIs[3*i] = API.concat("USDT-".concat(e.getCoins().get(i).getAbbreviation()));

                    APIs[3*i+1] = API.concat("BTC-".concat(e.getCoins().get(i).getAbbreviation()));

                    APIs[3*i+2] = API.concat("ETH-".concat(e.getCoins().get(i).getAbbreviation()));
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

                break;
        }
    }
}
