package com.example.alexander.cryptarbitrage2;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    String apiBase;
    String findSymbol;

    Exchange exchange;


    public DownloadTask(String findSymbol, String apiBase, Exchange exchange) {
        //gives ask and bid for BTCBTC 1:1, BTCETH = -999, ETHETH 1:1
        exchangeCoinsAskBTC.add(1.0);
        exchangeCoinsBidBTC.add(1.0);
        exchangeCoinsAskETH.add(-999.0);
        exchangeCoinsBidETH.add(-999.0);
        exchangeCoinsAskETH.add(1.0);
        exchangeCoinsBidETH.add(1.0);

        this.findSymbol = findSymbol;

        this.apiBase = apiBase;

        this.exchange = exchange;

    }


    @Override
    protected String doInBackground(String[] urls) {
        //Create priority Queue first with USD endings, then BTC, then ETH
        //skips BTCBTC, BTCETH, ETHETH - DNE

        LinkedList<String> q1 = new LinkedList<>();

        for (int i = 0; i < urls.length; i++) {
            q1.add(urls[i]);
        }
        int counter = 0; //specify which arrayList to add results to - explicit for first 3
        //rounds, then rotate

        if (exchange.getName().equals("Bittrex") || exchange.getName().equals("Binance")
    || exchange.getName().equals("HitBTC") || exchange.getName().equals("Bit-Z") ||
                exchange.getName().equals("Poloniex")) {
            fullAPIWay(q1);
            return "Worked";
        }

        String result = "";

        while (q1.size() != 0) {
            counter++;
            StringBuilder stringBuilder = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;

            //skips BTCBTC, BTCETH, ETHETH as they DNE
            if (counter == 2 || counter == 3 || counter == 6) {
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
                    stringBuilder.append(current);
                    data = reader.read();
                }
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                if (counter % 3 == 1) {
                    //search for 'bid' if bitfinex, else search for 'Bid'
                    if (exchange.getName().equals("Bitfinex")) {
                        exchangeCoinsBidUSD.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskUSD.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                }
                else if (counter % 3 == 2) {
                    if (exchange.getName().equals("Bitfinex")) {
                        exchangeCoinsBidBTC.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskBTC.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                }
                else if (counter % 3 == 0) { //dont really need the if, but makes it more clear
                    if (exchange.getName().equals("Bitfinex")) {
                        exchangeCoinsBidETH.add(Double.parseDouble(jsonObject.getString("bid")));
                        exchangeCoinsAskETH.add(Double.parseDouble(jsonObject.getString("ask")));
                    }
                }
            } catch (Exception e) {
                System.out.println("Hey guys: " + (e instanceof FileNotFoundException));
                if (e instanceof FileNotFoundException || e instanceof org.json.JSONException) {
                    if (counter % 3 == 1) {
                        exchangeCoinsAskUSD.add(-999.0);
                        exchangeCoinsBidUSD.add(-999.0);
                    } else if (counter % 3 == 2) {
                        exchangeCoinsAskBTC.add(-999.0);
                        exchangeCoinsBidBTC.add(-999.0);
                    } else if (counter % 3 == 0) { //could just be else, but this is more clear
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try { //sets all null values in each coin to 0 if it DNE - previously null
            for(Coin coin : exchange.getCoins()){
                if (coin.getAskPriceUSD() == null){
                    coin.setAskPriceUSD(-1.0);
                }
                if (coin.getBidPriceUSD() == null){
                    coin.setBidPriceUSD(-1.0);
                }
                if (coin.getAskPriceBTC() == null){
                    coin.setAskPriceBTC(-1.0);
                }
                if (coin.getBidPriceBTC() == null){
                    coin.setBidPriceBTC(-1.0);
                }
                if (coin.getAskPriceETH() == null){
                    coin.setAskPriceETH(-1.0);
                }
                if (coin.getBidPriceETH() == null){
                    coin.setBidPriceETH(-1.0);
                }
            }
            for(Coin coin: exchange.getCoins()){
                System.out.print("Name: " + coin.getName() + " Price USD: " + coin.getAskPriceUSD());
                System.out.println(" Price BTC: " + coin.getAskPriceBTC() + " Price ETH: " + coin.getAskPriceETH());
            }

            Log.d("PLEEEEEEESE", Integer.toString(exchangeCoinsBidUSD.size()));


        } catch (Exception e) {
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


    private void fullAPIWay(LinkedList<String> queue) {

        int counter = -1;
        Coin currentCoin;

        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(this.apiBase);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result.append(current);
                data = reader.read();
            }

            JSONObject jsonObject;
            JSONArray allPairs;
            if(exchange.getName().equals("Bittrex")) {
                jsonObject = new JSONObject(result.toString());
                allPairs = jsonObject.getJSONArray("result");
            }
            else if (exchange.getName().equals("Bit-Z")){
                jsonObject = new JSONObject(result.toString());
                jsonObject = jsonObject.getJSONObject("data");
                bitZWay(queue, jsonObject);
                return;
            }
            else if(exchange.getName().equals("Poloniex")){
                jsonObject = new JSONObject(result.toString());
                bitZWay(queue, jsonObject);
                return;
            }
            else {
                allPairs = new JSONArray(result.toString());
            }
            if(!exchange.isExchangeAPISorted()) {
                sortJSONArray(allPairs, findSymbol);
            }

            System.out.println("JSON SIZE" + allPairs.length() + " Queue length" + queue.size());
            System.out.println("Exchange name is: " + exchange.getName());

            while(!queue.isEmpty()){
                counter ++; //makes counter 0 initially 0 - don't want it at end bc might overlook it
                currentCoin = exchange.getCoins().get(counter/3);
                //want it to switch every third time - coinUSD, coinBTC, coinETH

                jsonObject = binarySearch(queue.peek(), allPairs, this.findSymbol);
                //System.out.println("The object is " + jsonObject.getString(this.findSymbol) +
                //        " Price is " + jsonObject.getString(exchange.getAskSymbol()));

                if(jsonObject == null){
                    System.out.println("Null " + queue.peek() );//debugger
                }
                else {
                    //a btc pair
                    if (counter % 3 == 1) {
                        currentCoin.setBidPriceBTC(Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                        currentCoin.setAskPriceBTC(Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                    }
                    //eth pair
                    else if (counter % 3 == 2) {
                        currentCoin.setBidPriceETH(Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                        currentCoin.setAskPriceETH(Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                    }
                    //USDT pair
                    else if (counter % 3 == 0) {
                        currentCoin.setBidPriceUSD(Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                        currentCoin.setAskPriceUSD(Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                    }
                }
                queue.remove();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sorts JSONArray
     * @param array is the JSONArray to be sorted
     * @param key is the Field you want them sorted by
     */
    private void sortJSONArray(JSONArray array, final String key){

        if(array.length() < 2){
            return; //not needed, but clarifies
        }
        else {
            List<JSONObject> jsonList = new ArrayList<>();
            try {
                for (int i = 0; i < array.length(); i++) {
                    jsonList.add(array.getJSONObject(i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Collections.sort(jsonList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject json1, JSONObject json2) {
                    try {
                        String val1 = json1.getString(key);
                        String val2 = json2.getString(key);
                        return val1.compareTo(val2);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

            });
            for(int i = 0; i < array.length(); i++){
                try{
                    array.put(i, jsonList.get(i));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Binary search returns the JSON Object with the name = key
     * @param key is the pair you're looking for
     * @param array is the JSONArray you're looking in
     * @param name is the what we search to look at the keys
     * @return the JSON Object with the matching key, or null if DNE
     */
    private JSONObject binarySearch(String key, JSONArray array, String name){
        int low = 0;
        int high = array.length()-1;
        int middle;

        while(high >= low){
            middle = (high + low) / 2;
            try {
                if(key.equals(array.getJSONObject(middle).getString(name))){
                    return array.getJSONObject(middle);
                } //key comes after where we're at
                else if (key.compareTo(array.getJSONObject(middle).getString(name)) > 0){
                    low = middle + 1;
                }
                else if (key.compareTo(array.getJSONObject(middle).getString(name)) < 0){
                    high = middle - 1;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public void bitZWay(LinkedList<String> queue, JSONObject jsonObject){
        JSONObject temp;
        int counter = -1;
        Coin currentCoin;
        while(!queue.isEmpty()){
            counter++;
            currentCoin = exchange.getCoins().get(counter/3);
            try{
                temp = jsonObject.getJSONObject(queue.peek());

                queue.remove();
                if (counter % 3 == 1) {
                    currentCoin.setBidPriceBTC(Double.parseDouble(temp.getString(exchange.getBidSymbol())));
                    currentCoin.setAskPriceBTC(Double.parseDouble(temp.getString(exchange.getAskSymbol())));
                }
                //eth pair
                else if (counter % 3 == 2) {
                    currentCoin.setBidPriceETH(Double.parseDouble(temp.getString(exchange.getBidSymbol())));
                    currentCoin.setAskPriceETH(Double.parseDouble(temp.getString(exchange.getAskSymbol())));
                }
                //USDT pair
                else if (counter % 3 == 0) {
                    currentCoin.setBidPriceUSD(Double.parseDouble(temp.getString(exchange.getBidSymbol())));
                    currentCoin.setAskPriceUSD(Double.parseDouble(temp.getString(exchange.getAskSymbol())));
                }

            }
            catch (Exception e){
                e.printStackTrace();
                queue.remove();
            }
        }
    }

}