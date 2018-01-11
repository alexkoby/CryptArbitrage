package com.example.alexander.cryptarbitrage2;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONObject;

import java.io.Console;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.util.Log;

/**
 * Created by Alexander on 1/9/2018.
 *
 * This class is used to parse all the URLs and retrieve the bids and asks for each coin
 * Stores these values in an arrayList - not storing in coins themselves because ran into problem where
 *     it takes a while to retrieve all values and don't want values to end up null due to time constraints. Thus, 
 *     in another activity, all the values are inserted into respective coin. I now realize this was not a smart
 *     design idea and it is possible to insert the values directly into the coins themselves, will make alterations when
 *     the rest of the implementation works properly.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {


//Why can't this go in constructor or doInBackground? -- always says its null
    ArrayList<Double> exchangeCoinsBidUSD = new ArrayList<>(5);
    ArrayList<Double> exchangeCoinsAskUSD = new ArrayList<>(5);
    ArrayList<Double> exchangeCoinsBidBTC = new ArrayList<>(5);
    ArrayList<Double> exchangeCoinsAskBTC = new ArrayList<>(5);
    ArrayList<Double> exchangeCoinsBidETH = new ArrayList<>(5);
    ArrayList<Double> exchangeCoinsAskETH = new ArrayList<>(5);

    String exchangeName;


    public DownloadTask(String exchangeName){
        this.exchangeName = exchangeName;

        exchangeCoinsAskBTC.add(1.0);
        exchangeCoinsBidBTC.add(1.0);
        exchangeCoinsAskETH.add(-999.0);
        exchangeCoinsBidETH.add(-999.0);
        exchangeCoinsAskETH.add(1.0);
        exchangeCoinsBidETH.add(1.0);


    }


    @Override
    protected String doInBackground(String [] urls) {
       

        PriorityQueue<String> q1 = new PriorityQueue<>();

        //add all strings of URLs into queue
        for(int i = 0; i < urls.length; i++){
            q1.add(urls[i]);
        }
        String result = "";
        //for debugging purposes
        System.out.println(getStatus());

        //used to figure out if dealing with a coin:USD, coin:BTC, or coin:ETH
        int counter = 0; 

        //while queue has items still in it
        while(q1.size()!=0){
            counter++; //yes counter starts at one
            result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(q1.peek());
                q1.remove();
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                
                //while still characters to be read, add to string
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                JSONObject jsonObject = new JSONObject(result);

                //Check if page can be reached, this doesn't help because Exception is already thrown above
                //Was trying to see if what showed up on my computer could be used to check if page could be reached
                //Some URLs didn't work - network errors - too many times using API/minute
                JSONObject jsonObjectSuccessBittrex = new JSONObject(jsonObject.getString("success"));
                JSONObject jsonObjectSuccessBitfinex = new JSONObject(jsonObject.getString("message"));
                System.out.println(counter + " " + jsonObjectSuccessBitfinex);
                if((exchangeName.equals("Bittrex") && jsonObjectSuccessBittrex.getBoolean("success")==false) ||
                        (exchangeName.equals("Bitfinex") && jsonObjectSuccessBitfinex.getString
                                ("bid")==null)){
                    //Dealing with a coin:USD ratio
                    if(counter%3 == 1){
                        exchangeCoinsBidUSD.add(-999.0);
                        exchangeCoinsAskUSD.add(-999.0);
                    }
                    //coin:BTC ratio
                    else if (counter%3 == 2){
                        exchangeCoinsBidBTC.add(-999.0);
                        exchangeCoinsAskBTC.add(-999.0);
                    }
                    //coin:Eth ratio
                    else{
                        exchangeCoinsBidETH.add(-999.0);
                        exchangeCoinsAskETH.add(-999.0);
                    }
                    continue;
                }

                    //if its bittrex, need to check in result section of JSON
                if(exchangeName.equals("Bittrex")) {

                    jsonObject = new JSONObject(jsonObject.getString("result"));
                }

                //if coin:USD
                if(counter%3 == 1 ) {
                    
                    //search for 'bid' if bitfinex, else search for 'Bid'
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                    else if (exchangeName.equals("Bittrex")){
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("Bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("Ask")));
                    }
                }
                //if coin:USD and not BTCBTC - DNE
                else if (counter%3 == 2 && counter!=2){
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidBTC.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskBTC.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                    else if (exchangeName.equals("Bittrex")){
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("Bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("Ask")));
                    }
            }
                //if coin:Eth and neither BTC:ETH nor ETH:ETH
                else if (counter%3 == 0 && counter > 6 ){ //dont really need the if, but makes it more clear
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidETH.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskETH.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                else if (exchangeName.equals("Bitrex")){
                    exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("Bid")));
                    exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("Ask")));
                }
            }
        }
            
        catch (Exception e) {
            //if FileNotFoundException, plug in -999.0 in place of true values
            if(e instanceof FileNotFoundException){
                if(counter%3 == 1){
                    exchangeCoinsAskUSD.add(-999.0);
                    exchangeCoinsBidUSD.add(-999.0);
                }
                else if(counter%3 == 2){
                    exchangeCoinsAskBTC.add(-999.0);
                    exchangeCoinsBidBTC.add(-999.0);
                }
                else if (counter%3 == 0) { //could just be else, but this is more clear
                    exchangeCoinsAskETH.add(-999.0);
                    exchangeCoinsBidETH.add(-999.0);
                }
            }
            e.printStackTrace();
        }
    }
        return result;
    }
    //Dont really need anymore, keeping J.I.C.
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        try{


            Log.d("PLEEEEEEESE", Integer.toString(exchangeCoinsBidUSD.size()));




        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//probably don't need anymore
    public ArrayList<Double> getExchangeCoinsBidUSD() {
        return exchangeCoinsBidUSD;
    }
//probably don't need anymore
    public ArrayList<Double> getExchangeCoinsAskUSD() {
        return exchangeCoinsAskUSD;
    }
    public ArrayList<Double> getExchangeCoinsBIDBTC() {
        return exchangeCoinsBidBTC;
    }
    public ArrayList<Double> getExchangeCoinsAskBTC() {
        return exchangeCoinsAskBTC;
    }
    public ArrayList<Double> getExchangeCoinsBIDETH() {
        return exchangeCoinsBidETH;
    }
    public ArrayList<Double> getExchangeCoinsAskETH() {
        return exchangeCoinsAskETH;
    }
}
