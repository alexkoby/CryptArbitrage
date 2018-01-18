package com.example.alexander.cryptarbitrage2;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.util.Log;

/**
 * Created by Alexander on 1/9/2018.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {


    //Why can't this go in constructor or doInBackground? -- always says its null
    ArrayList<Double> exchangeCoinsBidUSD = new ArrayList<>();
    ArrayList<Double> exchangeCoinsAskUSD = new ArrayList<>();
    ArrayList<Double> exchangeCoinsBidBTC = new ArrayList<>();
    ArrayList<Double> exchangeCoinsAskBTC = new ArrayList<>();
    ArrayList<Double> exchangeCoinsBidETH = new ArrayList<>();
    ArrayList<Double> exchangeCoinsAskETH = new ArrayList<>();

    String exchangeName;


    public DownloadTask(String exchangeName){
        //gives ask and bid for BTCBTC 1:1, BTCETH = -999, ETHETH 1:1
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
        //Create priority Queue first with USD endings, then BTC, then ETH
        //skips BTCBTC, BTCETH, ETHETH - DNE

        LinkedList<String> q1 = new LinkedList<>();

        for(int i = 0; i < urls.length; i++){
            q1.add(urls[i]);
            System.out.println(q1.size() + " size");
        }
        String result = "";

        int counter = 0; //specify which arrayList to add results to - explicit for first 3
        //rounds, then rotate

        while(q1.size()!=0){
            counter++;
            result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            //skips BTCBTC, BTCETH, ETHETH as they DNE
            if(counter == 2 || counter == 3 || counter == 6){
                q1.remove();
                continue;
            }

            try {
                url = new URL(q1.peek());
                q1.remove();

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                JSONObject jsonObject = new JSONObject(result);


                /**Check if page can be reached
                 JSONObject jsonObjectSuccessBittrex = new JSONObject(jsonObject.getString("success"));
                 JSONObject jsonObjectSuccessBitfinex = new JSONObject(jsonObject.getString("message"));
                 System.out.println(counter + " " + jsonObjectSuccessBitfinex);
                 if((exchangeName.equals("Bittrex") && jsonObjectSuccessBittrex.getBoolean("success")==false) ||
                 (exchangeName.equals("Bitfinex") && jsonObjectSuccessBitfinex.getString
                 ("bid")==null)){

                 if(counter%3 == 1){
                 exchangeCoinsBidUSD.add(-999.0);
                 exchangeCoinsAskUSD.add(-999.0);
                 }
                 else if (counter%3 == 2){
                 exchangeCoinsBidBTC.add(-999.0);
                 exchangeCoinsAskBTC.add(-999.0);
                 }
                 else{
                 exchangeCoinsBidETH.add(-999.0);
                 exchangeCoinsAskETH.add(-999.0);
                 }
                 continue;
                 }
                 */
                //if its bittrex, need to check in result section of JSON
                if(exchangeName.equals("Bittrex")) {

                    jsonObject = new JSONObject(jsonObject.getString("result"));
                }

                if(counter%3 == 1 ) {
                    //search for 'bid' if bitfinex, else search for 'Bid'
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                    else if (exchangeName.equals("Bittrex")){
                        System.out.println("Number1");
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("Bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("Ask")));
                    }
                }
                else if (counter%3 == 2){
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidBTC.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskBTC.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                    else if (exchangeName.equals("Bittrex")){
                        System.out.println("Number2");
                        exchangeCoinsBidBTC.add(Double.parseDouble(jsonObject.getString("Bid")));
                        exchangeCoinsAskBTC.add(Double.parseDouble(jsonObject.getString("Ask")));
                    }
                }
                else if (counter%3 == 0){ //dont really need the if, but makes it more clear
                    if(exchangeName.equals("Bitfinex")) {
                        exchangeCoinsBidETH.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskETH.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                    else if (exchangeName.equals("Bittrex")){
                        System.out.println("Number3");
                        exchangeCoinsBidETH.add(Double.parseDouble(jsonObject.getString("Bid")));
                        exchangeCoinsAskETH.add(Double.parseDouble(jsonObject.getString("Ask")));
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Hey guys: " + (e instanceof FileNotFoundException));
                if(e instanceof FileNotFoundException || e instanceof org.json.JSONException){
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
