package my.awesome.project.cryptarbitrage30;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */
import java.util.Calendar;

public class HomePage extends Activity implements View.OnClickListener{

    static double minimumVolumeUSD = 20000.0;

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

    static double minGainsWanted = 1.0;
    EditText minGainEditText;
    EditText minVolumeEditText;
    Button typeOfArbitrage;
    Button refreshButtonHomePage;
    static String typeOfArbitrageString = "Intra-Exchange and Cross Exchange Arbitrage";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        retrieveMinGainsInfo();
        retrieveMinVolumeInfo();



        //If this is the first time visiting the homepage
        if(!isCreatedHomepage) {
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
        //always starts exchanges activity, which starts cryptocurrencies activity, which restarts homepage activity to ensure all data
        //is gotten from saved files and up to date for user
        if(!HomePage.isCreatedExchanges){
            startActivity(new Intent(this, Exchanges.class));
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

        View helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(this);

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
            Toast.makeText(this, "Refreshing Your Data", Toast.LENGTH_LONG).show();
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
                return;
                //System.out.println("TASK IS NUUUUUUUUUUUUUUUUUUUUL");
            }
            task.execute(APIs);
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.helpButton:
                alertDialog.setTitle("Best Practices");
                alertDialog.setMessage("Select Exchanges\nClick the \"Select Exchanges\" button from the Home page." +
                        " It is recommended to hit select all, so you will be able to see all of the most lucrative arbitrage opportunities " +
                        "out there, even if you don't currently have an account on a particular exchange.\n\n" +
                        "Select Cryptocurrencies\nClick the \"Select Cryptocurrencies\" button. On the free version, you will only be" +
                        " able to select " + Cryptocurrencies.MAX_NUMBER_ALLOWED + " coins. With premium version, you empowered to select " +
                        "unlimited coins, which is highly recommended. Selecting all coins ensures you never miss an opportunity for " +
                        "arbitrage, " +
                        "as the most lucrative opportunities often come from less popular coins.\n\n" +
                        "Refresh Data\nClick the \"" +
                        refreshButtonHomePage.getText().toString() +
                        "\" button. This is where the app will be gathering the most accurate and up-to-date information regarding each " +
                        "coin's price, volume, ect. You will need to click this every time you want the app" +
                        " to update the coin's prices and every time you modify the selected cryptocurrencies. This process can sometimes " +
                        "take longer than you'd like, see \"Refresh Data Too Slow?\" below for details.\n\n" +
                        "Minimum Percent Gains\nMinumum percent gains is the lowest percentage profits you want the app to " +
                        "look for. For example, if you only want to " +
                        "see opportunities for 5.2% gains, change the value to \"5.2\" and select the enter button next to it." +
                        "\n\nMinimum 24 Hour Volume\n" +
                        "This value is used to filter out any trading pairs" +
                        " with less than this amount of volume traded within the past 24 hours. 24 hour trading volume can be used " +
                        "to get an idea as to how much " +
                        "of a currency you can buy or sell before the price begins to move." +
                        "\n\nCross Exchange Arbitrage\nIf you want to see only cross exchange arbitrage or only intra-exchange arbitrage, hit the button that says" +
                        " \"Intra-Exchange And Cross Exchange Arbitrage.\" Cross Exchange Arbitrage is buying a coin on one exchange, then sending it to another " +
                        "exchange to sell on. Intra-Exchange Arbitrage is when everything occurs on only one exchange. For example, " +
                        "you might buy Bitcoin for USD, convert Bitcoin to Dash, then sell Dash for USD, and end up with more USD than when you started. " +
                        "Looking at both Intra-Exchange and Cross Exchange Arbitrage " +
                        "is the recommended option.\nNote: Trading fees are NOT considered in our calculations. " +
                        "\n\nView Arbitrage Opportunities\nSelect the \"View Current Opportunities\" button when the data is finished refreshing. " +
                        " This will show you the best opportunities for arbitrage, sorted by profitablility. The details button will allow you to see " +
                        "what price to buy at, what price to sell at, and which exchanges to use. Always remember to ensure the wallets for " +
                        "your cryptocurrencies are working on both exchanges you hope to engage in arbitrage on, especially for " +
                        "opportunities over 10%. Also check the exchange's withdrawl fee for the currency, as this is not factored in our calculations." +
                        "\n\n\"Refresh Data\" Too Slow?\nCryptArbitrage prides itself on always using the most accurate and " +
                        "most fresh data, even more-so than CoinMarketCap. To ensure we maintain these high standards, which" +
                        " ensure our users make the most well informed decision, it can sometimes take ~2 minutes to refresh the data." +
                        " Huobi and OKEX are the main culprits causing this longer than ideal refresh time. If you wish to keep your refresh data to under 30 seconds" +
                        ", uncheck Huobi and OKEX. However, it is recomended to keep these two exchanges selected if you want the best arbitrage opportunities. " +
                        "A good technique is to select all currencies to figure out which coins present the best arbitrage opportunities. Then, unselect " +
                        "everything except for the coins with arbitrage opportunities that you seek to pursue, and then refresh the data again. This method will ensure " +
                        "that when making your trade, the information will be up to date to the minute." +
                        "\n\nDoes CryptArbitrage Consider Withdral Fees?\n" +
                        "CryptArbitrage currently does not consider withdrawl fees in our calculations. These fees vary from exchange to exchange, time to time, " +
                        "and coin to coin." +
                        " Always consider their impact on your percent gains before trading. The larger the amount of money you're withdrawing, the less the fee " +
                        "will impact you, as the fees are flat fees.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            case R.id.view_current_opprotunities:
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
                //System.out.println("Volume min is: " + minimumVolumeUSD);
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
            if (!exchange.isDataFinishedRefreshing()){
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
                    //System.out.println(coin.getName());
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
        for(Exchange exchange: listOfExchanges){
            if (!exchange.isDataFinishedRefreshing()){
                if(exchange.getName() == null){
                    return "Unknown";
                }
                else{
                   return exchange.getName();
                }
            }
        }
        return null;
    }




    public void saveMinGainsInfo(){
        String fileName = "minGainsInfo";
        String message;
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            message = Double.toString(HomePage.minGainsWanted) + ' ';
            fileOutputStream.write(Double.toString(HomePage.minGainsWanted).getBytes());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void retrieveMinGainsInfo(){
        try {
            FileInputStream fileInputStream = openFileInput("minGainsInfo");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //while more lines
            StringBuilder message = new StringBuilder();
            int data = 1;
            while(data != -1){
                data = bufferedReader.read();
                if((char)data == ' '){
                    break;
                }
                if(((char)data >= '0' && (char)data <= '9')|| (char)data == '.'){
                    message.append((char) data);
                }
            }
            if(message.toString().length() > 1){
                HomePage.minGainsWanted = Double.parseDouble(message.toString());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void saveMinVolumeInfo(){
        String fileName = "minVolumeInfo";
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write((Double.toString(HomePage.minimumVolumeUSD) + ' ').getBytes());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void retrieveMinVolumeInfo(){
        try {
            FileInputStream fileInputStream = openFileInput("minVolumeInfo");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //while more lines
            StringBuilder message = new StringBuilder();
            int data = 1;
            while(data != -1){
                data = bufferedReader.read();
                if((char)data == ' '){
                    break;
                }
                if(((char)data >= '0' && (char)data <= '9')|| (char)data == '.'){
                    message.append((char) data);
                }
            }
            if(message.toString().length() > 1){
                HomePage.minimumVolumeUSD = Double.parseDouble(message.toString());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @Override
    protected void onDestroy(){
        saveMinGainsInfo();
        saveMinVolumeInfo();

        super.onDestroy();
    }





}