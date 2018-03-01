package My.Awesome.Project.cryptarbitrage30;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//import com.anjlab.android.iab.v3.BillingProcessor;
//import com.anjlab.android.iab.v3.SkuDetails;
//import com.anjlab.android.iab.v3.TransactionDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomePage extends Activity implements View.OnClickListener{

    //BillingProcessor bp;
    static boolean hasSubscription = false;
    static double minimumVolumeUSD = 50000.0;

    static boolean isCreatedHomepage = false;
    static boolean isCreatedExchanges = false;
    static boolean isCreatedCryptocurrencies = false;

    static ArrayList<String> listOfCurrencies;
    static ArrayList<Exchange> listOfExchanges;
    static ArrayList<Exchange> allPossibleExchanges;
    static Exchange bitfinex;
    static Exchange bittrex;
    static Exchange binance;
    static Exchange hitBTC;
    static Exchange bitZ;
    static Exchange poloniex;
    static Exchange bitStamp;
    static Exchange OKEX;
    static Exchange GDAX;
    static Exchange kraken;
    static Exchange huobi;


    static DownloadTask taskBitfinex;
    static DownloadTask taskBittrex;
    static DownloadTask taskBinance;
    static DownloadTask taskHitBTC;
    static DownloadTask taskBitZ;
    static DownloadTask taskPoloniex;
    static DownloadTask taskBitStamp;
    static DownloadTask taskOKEX;
    static DownloadTask taskGDAX;
    static DownloadTask taskKraken;
    static DownloadTask taskHuobi;

    static int lastTimeRefreshedMinute;
    static int lastTimeRefreshedHour;

    static String lastExchange;

    AlertDialog alertDialog;

    static boolean isInProcessOfRefreshing = false;

    static double minGainsWanted = 1.5;
    EditText minGainEditText;
    EditText minVolumeEditText;
    Button typeOfArbitrage;
    Button refreshButtonHomePage;
    static String typeOfArbitrageString = "Intra-Exchange and Cross Exchange Arbitrage";

    final String publicAPI = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArNSgdFawfG05qVr5dmy5VGnrR/D7A636WN7l28Gpy8X9hIM8FlKpRDWfDNjV5x2q/7Nlzpla462DLlYRFIxCHm/LoQMd6vm37k10FqhskFxkvcMshKE7fEfVVrOnnod3JE8UDhwMd0a3UYGqAWNWx8m02K2Y6vzKfIYpu0NLpaPMD1GgVw6ZtoiNCIR+ilL/Kvv8WZutM3yUBhrTr47dOjvu/bwYQ01RT1QbGMjujIE3KWphzmfUCWRhZIGsTypgskVFoH2px5gSB2ynQVvjFAN2Jx18Hj+AhinVl1pSBdOl0eMJG0TXyPtbseRAhPdv1H+YcTPOed5g2j7qxKwxswIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);



        //If this is the first time visiting the homepage
        if(!isCreatedHomepage) {
            //bp = new BillingProcessor(this, publicAPI, this);
            //System.out.println("Home page not created");
            listOfExchanges = new ArrayList<>();
            listOfCurrencies = new ArrayList<>();
            allPossibleExchanges = new ArrayList<>();
            bitfinex = new Exchange("Bitfinex", "ask", "bid",false,
                    false, true,null, null, null); // doesn't return volume
            allPossibleExchanges.add(bitfinex);
            bittrex = new Exchange("Bittrex", "Ask", "Bid",true,
                    false, false, "BaseVolume", null, null); //multiply by price of bitcoin or eth to get price usd
            allPossibleExchanges.add(bittrex);
            binance = new Exchange("Binance", "askPrice", "bidPrice",false,
                    false,false,"volume", "bidQty","askQty"); // doesn't return volume
            allPossibleExchanges.add(binance);
            hitBTC = new Exchange("HitBTC", "ask", "bid",false,
                    false, true,"volume", null, null); // volume * trading price * usd price of eth or btc
            allPossibleExchanges.add(hitBTC);
            bitZ = new Exchange("Bit-Z","sell","buy",false,
                    false, false, "vol", null, null);//volume * trading price * usd price of eth or btc
            allPossibleExchanges.add(bitZ);
            poloniex = new Exchange("Poloniex","lowestAsk","highestBid",false,
                    false, false, "baseVolume", null, null);//volume * price of usd(1) or brice of bitcoin
            allPossibleExchanges.add(poloniex);
            bitStamp = new Exchange("BitStamp","bid","ask",false,
                    false, true, "volume", null, null); //volume * price * price usd(1) or btc
            allPossibleExchanges.add(bitStamp);
            OKEX = new Exchange("OKEX","sell","buy",false,
                    false, false, "vol", null, null);// volume * trading price * price of usd(1) or price btc or eth
            allPossibleExchanges.add(OKEX);
            GDAX = new Exchange("GDAX","ask","bid",false,
                    false, true, "volume", null, null);// same as bitstamp
            allPossibleExchanges.add(GDAX);
            kraken = new Exchange("Kraken","a","b",false,
                    false, true, "v", null, null);//same at bitstamp
            allPossibleExchanges.add(kraken);
            huobi = new Exchange("Huobi","ask","bid",false,
                    false, false, "amount", null, null);
            allPossibleExchanges.add(huobi);

            isCreatedHomepage = true;

            initialzeTasks();
        }


        //set up click listeners for buttons on home page
        View viewCurrentOpprotunities = findViewById(R.id.view_current_opprotunities);
        viewCurrentOpprotunities.setOnClickListener(this);

        View modifyExchanges = findViewById(R.id.modify_exchanges);
        modifyExchanges.setOnClickListener(this);

        View modifyCryptocurrencies = findViewById(R.id.modify_cryptocurrencies);
        modifyCryptocurrencies.setOnClickListener(this);

        View enterMinGainsButton = findViewById(R.id.enterMinGainsButton);
        enterMinGainsButton.setOnClickListener(this);

        View enterMinVolumeButton = findViewById(R.id.enterVolumeButton);
        enterMinVolumeButton.setOnClickListener(this);

        refreshButtonHomePage = findViewById(R.id.refreshDataButtonHomePage);
        refreshButtonHomePage.setOnClickListener(this);


        minGainEditText= findViewById(R.id.minGainEditText);
        minGainEditText.setText(Double.toString(minGainsWanted));
        minVolumeEditText = findViewById(R.id.minVolumeEditText);
        minVolumeEditText.setText(Double.toString(minimumVolumeUSD));

        typeOfArbitrage = findViewById(R.id.typeOfArbitrage);
        typeOfArbitrage.setText(typeOfArbitrageString);
        typeOfArbitrage.setOnClickListener(this);

        //alert dialog in case user tries to click viewCurrentOpprotunities before it's ready
        alertDialog = new AlertDialog.Builder(this).create();
    }
    @Override
    public void onStart(){
        super.onStart();

        if(!ViewCryptoOpprotunities.hasData){
            alertDialog.setTitle("No Arbitrage Opportunities Were Found");
            alertDialog.setMessage("Please add more exchanges," +
                    " more cryptocurrencies, set a lower minimum gains or a lower minimum volume to find an Arbitrage Opprotunity for you");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            ViewCryptoOpprotunities.hasData = true;
        }
        if(ViewCryptoOpprotunities.selectedRefreshViewOpportunities){
            HomePage.makeAPIRequests();
            Toast.makeText(this, "Refreshing your data", Toast.LENGTH_LONG).show();
            ViewCryptoOpprotunities.selectedRefreshViewOpportunities = false;
        }
    }


    //Creates an Array of URLs and calls downloadtask.execute()
    private static void getAsksAndBids(Exchange e){
        //System.out.println("Exchange is: " + e.getName());
        String [] APIs = new String [e.getCoins().size()*3];
        DownloadTask task = null;

        //System.out.println(e.getCoins().size());
        switch (e.getName()){
            case "Bitfinex":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                }
                task = HomePage.taskBitfinex;
                break;
            case "Bittrex":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = "USDT-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3 * i + 1] = "BTC-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3 * i + 2] = "ETH-".concat(e.getCoins().get(i).getAbbreviation());
                }
                task = HomePage.taskBittrex;
                break;
            case "Binance":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("USDT");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                }
                task = HomePage.taskBinance;
                break;
            case "HitBTC":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                }
                task = HomePage.taskHitBTC;
                break;
            case "Bit-Z":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("_usdt");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("_btc");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("_eth");
                }
                task = HomePage.taskBitZ;
                break;
            case "Poloniex":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = "USDT_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3 * i + 1] = "BTC_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3 * i + 2] = "ETH_".concat(e.getCoins().get(i).getAbbreviation());
                }
                task = HomePage.taskPoloniex;
                break;
            case "BitStamp":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                }
                task = HomePage.taskBitStamp;
                break;
            case "OKEX":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("_usdt");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("_btc");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("_eth");
                }
                task = HomePage.taskOKEX;
                break;
            case "GDAX":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("-usd/ticker");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("-btc/ticker");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("-eth/ticker");
                }
                task = HomePage.taskGDAX;
                break;
            case "Kraken":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("ZUSD");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("XXBT");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("XETH");
                }
                task = HomePage.taskKraken;
                break;

            case "Huobi":
                for(int i = 0; i < e.getCoins().size(); i+=1) {
                    APIs[3 * i] = e.getCoins().get(i).getAbbreviation().concat("usdt");
                    APIs[3 * i + 1] = e.getCoins().get(i).getAbbreviation().concat("btc");
                    APIs[3 * i + 2] = e.getCoins().get(i).getAbbreviation().concat("eth");
                }
                task = HomePage.taskHuobi;
                break;
        }
        if(task == null) {
            //System.out.println("TRIED TO USE NULL DOWNLOAD TASK");
            return;
        }
        else {
            //System.out.println("Made it to reimplement " + e.getName());
            task = reImplementTask(task);
            if(task == null){
                //System.out.println("TASK IS NUUUUUUUUUUUUUUUUUUUUL");
            }
            task.execute(APIs);
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.view_current_opprotunities:
                Toast.makeText(this, "Have paid: " +HomePage.hasSubscription, Toast.LENGTH_SHORT).show();
                if(listOfExchanges.size() == 0 && listOfCurrencies.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Exchanges And One Or " +
                            "More Cryptocurrencies Before Viewing Current Opprotunities");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                else if(listOfExchanges.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Exchanges Before Viewing Current Opprotunities");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                else if(listOfCurrencies.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Currencies Before Viewing Current Opprotunities");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                if(HomePage.isDataCurrentlyRefreshing()){
                    Toast.makeText(getApplicationContext(),"Please Wait for data to finish loading" +
                            "\n\n Currently gathering data from ".concat(currentlyOnWhatExchange()),Toast.LENGTH_LONG).show();
                    alertDialog.setTitle("Please Wait");
                    alertDialog.setMessage("Please Wait While We Refresh All The Data To Find Your Best Arbitrage Opportunities" +
                            "\n\nCurrently gathering data from ".concat(currentlyOnWhatExchange()));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                if(!HomePage.isAllDataFinishedRefreshing() || !doesEveryCoinHaveData()){
                    //System.out.println("Made it inside the loop");
                    Toast.makeText(getApplicationContext(),"Please Select The Refresh Data Button To Get Data!",Toast.LENGTH_LONG).show();
                    alertDialog.setTitle("Please Wait");
                    alertDialog.setMessage("Please Select The Refresh Data Button To Get Data!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                //if(!hasSubscription){
                //    Toast.makeText(this, "Please Subscribe To View Top Arbitrage Opportunities", Toast.LENGTH_SHORT).show();
                //    bp.subscribe(this,"monthly_sub");
                //    break;
                //}
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

            case R.id.enterMinGainsButton:
                String s = minGainEditText.getText().toString();
                minGainsWanted = Double.parseDouble(s);
                minGainEditText.setText(Double.toString(minGainsWanted));
                break;

            case R.id.enterVolumeButton:
                String str = minVolumeEditText.getText().toString();
                minimumVolumeUSD = Double.parseDouble(str);
                minVolumeEditText.setText(Double.toString(minimumVolumeUSD));
                System.out.println("Volume min is: " + minimumVolumeUSD);
                break;

            case R.id.typeOfArbitrage:
                if(typeOfArbitrage.getText().toString().equals("Intra-Exchange and Cross Exchange Arbitrage")){
                    typeOfArbitrageString = "Intra-Exchange Arbitrage Only";
                    typeOfArbitrage.setText(typeOfArbitrageString);
                }
                else if (typeOfArbitrage.getText().toString().equals("Intra-Exchange Arbitrage Only")){
                    typeOfArbitrageString = "Cross-Exchange Arbitrage Only";
                    typeOfArbitrage.setText(typeOfArbitrageString);
                }
                else {
                    typeOfArbitrageString = "Intra-Exchange and Cross Exchange Arbitrage";
                    typeOfArbitrage.setText(typeOfArbitrageString);
                }
                break;

            case R.id.refreshDataButtonHomePage:
                if(listOfExchanges.size() == 0 && listOfCurrencies.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Exchanges And One Or " +
                            "More Cryptocurrencies Before Refreshing Data");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                else if(listOfExchanges.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Exchanges Before Refreshing Data");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                else if(listOfCurrencies.size() == 0){
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select One Or More Currencies Before Refreshing Data");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                if(!isDataCurrentlyRefreshing()){
                    HomePage.makeAPIRequests();
                    Toast.makeText(this, "Refreshing Your Data", Toast.LENGTH_LONG).show();
                    break;
                }
                else{
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Wait For Data To Finish Refreshing Before Refreshing Again" +
                            "\n\nCurrently gathering data from ".concat(currentlyOnWhatExchange()));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }

        }
    }

    public void initialzeTasks(){
        taskBitfinex = new DownloadTask(null,"https://api.bitfinex.com/v1/pubticker/", bitfinex );
        taskBittrex = new DownloadTask("MarketName",
                "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
        taskBinance = new DownloadTask("symbol", "https://www.binance.com/api/v1/ticker/24hr",
                binance);
        taskHitBTC = new DownloadTask("symbol","https://api.hitbtc.com/api/2/public/ticker", hitBTC);
        taskBitZ = new DownloadTask("data","https://www.bit-z.com/api_v1/tickerall", bitZ);
        taskPoloniex = new DownloadTask("","https://poloniex.com/public?command=returnTicker",poloniex);
        taskBitStamp = new DownloadTask(null, "https://www.bitstamp.net/api/v2/ticker/",bitStamp);
        taskOKEX = new DownloadTask("ticker","https://www.okex.com/api/v1/ticker.do?symbol=", OKEX);
        taskGDAX = new DownloadTask(null,"https://api.gdax.com/products/", GDAX);
        taskKraken = new DownloadTask("result","https://api.kraken.com/0/public/Ticker?pair=XBTUSD,ETHXBT," +
                "ETHUSD,XRPXBT,XRPUSD,LTCXBT,LTCUSD,BCHXBT,BCHUSD,XLMXBT,DASHXBT,DASHUSD,XMRXBT,XMRUSD,ETCXBT,ETCUSD," +
                "ETCETH,ZECXBT,ZECUSD, EOSXBT, EOSETH, EOSUSD, REPETH, REPXBT, GNOUSD, GNOETH, GNOXBT, ICNXBT, ICNETH" +
                ", MLNXBT, MLNETH",kraken);
        taskHuobi = new DownloadTask("tick","https://api.huobi.pro/market/detail/merged?symbol=",huobi);
    }

    //@Override
    //after OnPause and OnStart, basically every time
//    public void onResume(){
    //       super.onResume();
    //   }

    public static DownloadTask reImplementTask(DownloadTask downloadTask){
        switch (downloadTask.getExchange().getName()){
            case "Bitfinex":
                taskBitfinex = new DownloadTask(null,"https://api.bitfinex.com/v1/pubticker/", bitfinex );
                return taskBitfinex;
            case "Bittrex":
                taskBittrex = new DownloadTask("MarketName",
                        "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
                return taskBittrex;
            case "Binance":
                taskBinance = new DownloadTask("symbol", "https://www.binance.com/api/v1/ticker/24hr",
                        binance);
                return taskBinance;
            case "HitBTC":
                taskHitBTC = new DownloadTask("symbol","https://api.hitbtc.com/api/2/public/ticker", hitBTC);
                return taskHitBTC;
            case "Bit-Z":
                taskBitZ = new DownloadTask("","https://www.bit-z.com/api_v1/tickerall", bitZ);
                return taskBitZ;
            case "Poloniex":
                taskPoloniex = new DownloadTask("","https://poloniex.com/public?command=returnTicker",poloniex);
                return taskPoloniex;
            case "BitStamp":
                taskBitStamp = new DownloadTask(null, "https://www.bitstamp.net/api/v2/ticker/",bitStamp);
                return taskBitStamp;
            case "OKEX":
                taskOKEX = new DownloadTask("ticker","https://www.okex.com/api/v1/ticker.do?symbol=", OKEX);
                return taskOKEX;
            case "GDAX":
                taskGDAX = new DownloadTask(null,"https://api.gdax.com/products/", GDAX);
                return taskGDAX;
            case "Kraken":
                taskKraken = new DownloadTask("result","https://api.kraken.com/0/public/Ticker?pair=XBTUSD,ETHXBT," +
                        "ETHUSD,XRPXBT,XRPUSD,LTCXBT,LTCUSD,BCHXBT,BCHUSD,XLMXBT,DASHXBT,DASHUSD,XMRXBT,XMRUSD,ETCXBT,ETCUSD," +
                        "ETCETH,ZECXBT,ZECUSD, EOSXBT, EOSETH, EOSUSD, REPETH, REPXBT, GNOUSD, GNOETH, GNOXBT, ICNXBT, ICNETH" +
                        ", MLNXBT, MLNETH",kraken);
                return taskKraken;
            case "Huobi":
                taskHuobi = new DownloadTask("tick","https://api.huobi.pro/market/detail/merged?symbol=",huobi);
                return taskHuobi;
        }
        return null;
    }

    public static void makeAPIRequests(){

        HomePage.lastTimeRefreshedMinute = Calendar.getInstance().get(Calendar.MINUTE);
        HomePage.lastTimeRefreshedHour = Calendar.getInstance().get(Calendar.HOUR);

        clearCoinData();
        HomePage.isInProcessOfRefreshing = true;
        lastExchange = HomePage.listOfExchanges.get(HomePage.listOfExchanges.size() - 1).getName();

        if(listOfExchanges.size() > 0 && listOfCurrencies.size() > 0) {
            for(Exchange exchange: listOfExchanges){
                exchange.setDataIsFinishedRefreshing(false);
                getAsksAndBids(exchange);
                //System.out.println(exchange.getName());
            }
        }
    }
    public static void clearCoinData(){
        for(Exchange exchange: allPossibleExchanges) {
            for (Coin coin : exchange.getCoins()) {
                clearCoinData(coin);
            }
        }
    }

    private static void clearCoinData(Coin coin){
        coin.setAskPriceUSD(-1.0);
        coin.setBidPriceUSD(-1.0);
        coin.setAskPriceBTC(-1.0);
        coin.setBidPriceBTC(-1.0);
        coin.setAskPriceETH(-1.0);
        coin.setBidPriceETH(-1.0);
    }

    static boolean isAllDataFinishedRefreshing(){
        for (Exchange exchange: HomePage.listOfExchanges){
            if (exchange.isDataFinishedRefreshing() == false){
                return false;
            }
        }
        return true;
    }

    private boolean doesEveryCoinHaveData(){
        for(Exchange exchange: HomePage.listOfExchanges){
            for(Coin coin: exchange.getCoins()){
                if(coin.getBidPriceUSD() == null)
                {
                    System.out.println(coin.getName());
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isDataCurrentlyRefreshing(){
        return HomePage.isInProcessOfRefreshing;
    }

    private String currentlyOnWhatExchange(){
        Exchange e;
        for(Exchange exchange: listOfExchanges){
            if (!exchange.isDataFinishedRefreshing()){
                return exchange.getName();
            }
        }
        return null;
    }

    /*@Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "Thank You For Purchasing A Subscription", Toast.LENGTH_LONG).show();
        HomePage.hasSubscription = true;
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }
//Either onBillingInitialized or onPurchaseHistoryRestored
    @Override
    public void onBillingInitialized() {
        if(!bp.loadOwnedPurchasesFromGoogle()) {
            Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_LONG);
            HomePage.hasSubscription = false;
        }
//this is getting executed below
        else{
            if(bp.isSubscribed("monthly_sub")){
                Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
                HomePage.hasSubscription = true;
            }
            else{
                Toast.makeText(this,"You do not have a subscription", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if(resultCode == 0){
            Toast.makeText(this,"Buy A Subscription To See The Top Arbitrage Opportunities", Toast.LENGTH_LONG).show();
        }
        if(resultCode == -1){
            HomePage.hasSubscription = true;
        }
        if (resultCode == 2){
            Toast.makeText(this, "Network Connection Down", Toast.LENGTH_LONG).show();
        }
        if(resultCode == 7){
            Toast.makeText(this,"You Are Already Subscribed",Toast.LENGTH_SHORT).show();;
        }
        Toast.makeText(this,"OnActivityResultMethod Result " + resultCode, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
    */

}