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
import java.util.PriorityQueue;

import android.util.Log;

/**
 * Created by Alexander on 1/9/2018.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {
    int APIRequestsMade = -1;
    String apiBase;
    String findSymbol;

    Exchange exchange;


    public DownloadTask(String findSymbol, String apiBase, Exchange exchange) {
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
        int counter = -1; //specify which arrayList to add results to - explicit for first 3
        //rounds, then rotate

        if (exchange.getName().equals("Bittrex") || exchange.getName().equals("Binance")
    || exchange.getName().equals("HitBTC") || exchange.getName().equals("Bit-Z") ||
                exchange.getName().equals("Poloniex") || exchange.getName().equals("Kraken")) {
            fullAPIWay(q1);
            return "Worked";
        }

        String result = "";

        while (q1.size() != 0) {
            counter++;

            if(isCoinPairNull(counter)){
                System.out.println("REMOVED");
                q1.remove();
                continue;
            }
            APIRequestsMade++;

            StringBuilder stringBuilder = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection;


            try {
                String currentPair = q1.peek();
                url = new URL(this.apiBase.concat(currentPair));
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
                if(this.findSymbol!= null && this.findSymbol.length() > 0){
                    jsonObject = jsonObject.getJSONObject(this.findSymbol);
                }
                if (counter % 3 == 0) {
                    //search for 'bid' if bitfinex, else search for 'Bid'
                    exchange.getCoins().get(counter/3).setBidPriceUSD
                            (Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                    exchange.getCoins().get(counter/3).setAskPriceUSD
                            (Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                }
                else if (counter % 3 == 1) {
                    exchange.getCoins().get(counter/3).setBidPriceBTC
                            (Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                    exchange.getCoins().get(counter/3).setAskPriceBTC
                            (Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                }
                else if (counter % 3 == 2) { //dont really need the if, but makes it more clear
                    exchange.getCoins().get(counter/3).setBidPriceETH
                            (Double.parseDouble(jsonObject.getString(exchange.getBidSymbol())));
                    exchange.getCoins().get(counter/3).setAskPriceETH
                            (Double.parseDouble(jsonObject.getString(exchange.getAskSymbol())));
                }
            }
            catch (Exception e) {
                if (e instanceof FileNotFoundException || e instanceof org.json.JSONException) {
                    if (!isCoinPairNull(counter) && (!exchange.getName().equals("GDAX"))
                            && (!exchange.getName().equals("BitStamp"))) {
                        e.printStackTrace();
                        System.out.print("NO MORE API REQUESTS ALLOWED " + exchange.getName());
                        System.out.println("The maximum amt is: " + APIRequestsMade);
                        return "NO MORE API REQUESTS ALLOWED";
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
        System.out.println(exchange.getName());
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
                System.out.print("Name: " + coin.getName() + " Price Ask USD: "
                        + coin.getAskPriceUSD() + " Price Bid USD" + coin.getBidPriceUSD());
                System.out.println(" Price Ask BTC: " + coin.getAskPriceBTC() + "Price BID BTC" + coin.getBidPriceBTC() +
                        " Price Ask ETH: " + coin.getAskPriceETH() + " Price Bid USD" + coin.getBidPriceETH());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Exchange getExchange(){
        return this.exchange;
    }

    private void fullAPIWay(LinkedList<String> queue) {

        int counter = -1;
        Coin currentCoin;

        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection;

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
            else if(exchange.getName().equals("Kraken")){
                jsonObject = new JSONObject(result.toString());
                jsonObject = jsonObject.getJSONObject("result");
                krakenWay(queue,jsonObject);
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
                counter ++; //makes counter1 0 initially 0 - don't want it at end bc might overlook it
                if(isCoinPairNull(counter)){
                    queue.remove();
                    continue;
                }
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

    private void bitZWay(LinkedList<String> queue, JSONObject jsonObject){
        JSONObject temp;
        int counter = -1;
        Coin currentCoin;
        while(!queue.isEmpty()){
            counter++;
            if(isCoinPairNull(counter)){
                queue.remove();
                continue;
            }
            currentCoin = exchange.getCoins().get(counter/3);
            try{
                temp = jsonObject.getJSONObject(queue.peek());


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
                queue.remove();

            }
            catch (Exception e){
                e.printStackTrace();
                if (counter % 3 == 1) {
                    currentCoin.setBidPriceBTC(-1.0);
                    currentCoin.setAskPriceBTC(-1.0);
                }
                //eth pair
                else if (counter % 3 == 2) {
                    currentCoin.setBidPriceETH(-1.0);
                    currentCoin.setAskPriceETH(-1.0);
                }
                //USDT pair
                else if (counter % 3 == 0) {
                    currentCoin.setBidPriceUSD(-1.0);
                    currentCoin.setAskPriceUSD(-1.0);
                }
                queue.remove();
            }
        }
    }
        //Kraken pairs are so weird
    public void krakenWay(LinkedList<String> q1, JSONObject jsonObject) {
        System.out.println("Kraken has " + exchange.getCoins().size() + " coins in it");
        Coin currentCoin;
        JSONObject currentCoinJSONForm;
        String currentAskString;
        String currentBidString;

        int counter = -1;
        while(!q1.isEmpty()){
            counter++;
            currentCoin = exchange.getCoins().get(counter/3);
            if(counter % 3 == 0){
                System.out.println("Coin is: " + exchange.getCoins().get(counter/3).getName());
            }
            if(isCoinPairNull(counter)){
                q1.remove();
                continue;
            }
            String coinPairBase = q1.peek();

            //BitcoinCash and Dash have different way to look at API for some reason
            if(currentCoin.getName().equals("Bitcoin Cash") || currentCoin.getName().equals("Dash")){
                coinPairBase = coinPairBase.substring(0, coinPairBase.length() - 4).
                        concat(coinPairBase.substring(coinPairBase.length() - 3));
            }

            try {
                currentCoinJSONForm = jsonObject.getJSONObject(coinPairBase);
                currentAskString = currentCoinJSONForm.getString(exchange.getAskSymbol());
                System.out.println(currentAskString);
                currentBidString = currentCoinJSONForm.getString(exchange.getBidSymbol());
                System.out.println(currentBidString);

                if (counter % 3 == 1) {
                    currentCoin.setBidPriceBTC(getPriceDataFromStringInArrayFormKraken(currentBidString));
                    currentCoin.setAskPriceBTC(getPriceDataFromStringInArrayFormKraken(currentAskString));
                }
                //eth pair
                else if (counter % 3 == 2) {
                    currentCoin.setBidPriceETH(getPriceDataFromStringInArrayFormKraken(currentBidString));
                    currentCoin.setAskPriceETH(getPriceDataFromStringInArrayFormKraken(currentAskString));
                }
                //USDT pair
                else if (counter % 3 == 0) {
                    currentCoin.setBidPriceUSD(getPriceDataFromStringInArrayFormKraken(currentBidString));
                    currentCoin.setAskPriceUSD(getPriceDataFromStringInArrayFormKraken(currentAskString));
                }
                q1.remove();
            }
            catch (Exception e){
                e.printStackTrace();
                q1.remove();
            }
        }
    }

    private boolean isCoinPairNull(int counter){
        if(counter % 3 == 0 && exchange.getCoins().
                get(counter/3).getUSDBTCETHPairs().charAt(0)=='0'){
            return true;
        }
        else if (counter % 3 == 1 && exchange.getCoins().
                get(counter/3).getUSDBTCETHPairs().charAt(1) == '0'){
            return true;
        }
        else if (counter % 3 == 2 && exchange.getCoins().get(counter/3)
                .getUSDBTCETHPairs().charAt(2) == '0'){
            return true;
        }
        return false;
    }

    /**
     * Takes a string that looks like an array of data and finds the price
     * @param s is the string that looks like an array
     * @return the first price - or null if it doesn't exist
     */
    private Double getPriceDataFromStringInArrayFormKraken(String s){
        int spotOfFirstQuote = 1;
        int spotOfSecondQuote = 0;

        for(int i = 2; i < s.length(); i++){
            if(s.charAt(i) == '"'){
                spotOfSecondQuote = i;
                break;
            }
        }
        if(spotOfSecondQuote == 0){
            return null;
        }
        else {
            return Double.parseDouble(s.substring(spotOfFirstQuote + 1, spotOfSecondQuote));
        }
    }
}