package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/8/2018.
 */

//still need to add kraken eos

public class Cryptocurrencies extends Activity implements View.OnClickListener{
    ArrayList<ToggleButton> allCurrenciesButtons;
    Button submitCurrencyButton;
    Button selectAllCurrenciesButton;
    boolean hasAddedBitcoinAndEthereum = false;

    ArrayAdapter<String>listAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencies_page);


        //set up click listeners
        selectAllCurrenciesButton = findViewById(R.id.select_all_cryptocurrencies_button);
        selectAllCurrenciesButton.setOnClickListener(this);

        submitCurrencyButton = findViewById(R.id.submit_currency_button);
        submitCurrencyButton.setOnClickListener(this);

        //Create and add all other Buttons to ArrayList
        setUpButtons();

        if(MainActivity.isCreatedCryptocurrencies){
            getSavedCoins();
        }

        MainActivity.isCreatedCryptocurrencies = true;


    }

    public void onClick(View v) {
        switch (v.getId()) {
            //Makes Select All button work
            case R.id.select_all_cryptocurrencies_button:
                if (selectAllCurrenciesButton.getText().equals("On")) {
                    for (ToggleButton button : allCurrenciesButtons) {
                        button.setChecked(false);
                    }
                    selectAllCurrenciesButton.setText("Off");
                }
                else {
                    for (ToggleButton button : allCurrenciesButtons) {
                        button.setChecked(true);
                    }
                    selectAllCurrenciesButton.setText("On");
                }
                break;
            //Submit button - clears everything off each exchange and inserts coins
            //to respective exchanges
            case R.id.submit_currency_button:
                clearExchanges();
                HomePage.listOfCurrencies.clear();
                for(ToggleButton button : allCurrenciesButtons){
                    if(button.isChecked()) {
                        //adds bitcoin and ethereum if at least one coin is checked
                        if(!hasAddedBitcoinAndEthereum){
                            addBitcoinAndEthereumToExchanges();
                            hasAddedBitcoinAndEthereum = true;
                        }
                        addCurrencyToExchanges(button);
                    }
                }
                hasAddedBitcoinAndEthereum = false;

                saveSelectedCoinsInfo(allCurrenciesButtons);
                System.out.println("size is: " + HomePage.bitfinex.getCoins().size());
                startActivity(new Intent(this, HomePage.class));

        }
    }

    public void setUpButtons(){
        allCurrenciesButtons = new ArrayList<>();
        ToggleButton currencyButton1 = findViewById(R.id.bitcoinButton);
        allCurrenciesButtons.add(currencyButton1);
        ToggleButton currencyButton2 = findViewById(R.id.ethereumButton);
        allCurrenciesButtons.add(currencyButton2);
        ToggleButton currencyButton3 = findViewById(R.id.rippleButton);
        allCurrenciesButtons.add(currencyButton3);
        ToggleButton currencyButton4 = findViewById(R.id.bitcoinCashButton);
        allCurrenciesButtons.add(currencyButton4);
        ToggleButton currencyButton5 = findViewById(R.id.cardanoButton);
        allCurrenciesButtons.add(currencyButton5);
        ToggleButton currencyButton6 = findViewById(R.id.nemButton);
        allCurrenciesButtons.add(currencyButton6);
        ToggleButton currencyButton7 = findViewById(R.id.litecoinButton);
        allCurrenciesButtons.add(currencyButton7);
        ToggleButton currencyButton8 = findViewById(R.id.stellarButton);
        allCurrenciesButtons.add(currencyButton8);
        ToggleButton currencyButton9 = findViewById(R.id.iotaButton);
        allCurrenciesButtons.add(currencyButton9);
        ToggleButton currencyButton10 = findViewById(R.id.dashButton);
        allCurrenciesButtons.add(currencyButton10);
        ToggleButton currencyButton11 = findViewById(R.id.neoButton);
        allCurrenciesButtons.add(currencyButton11);
        ToggleButton currencyButton12 = findViewById(R.id.moneroButton);
        allCurrenciesButtons.add(currencyButton12);
        ToggleButton currencyButton13 = findViewById(R.id.qtumButton);
        allCurrenciesButtons.add(currencyButton13);
        ToggleButton currencyButton14 = findViewById(R.id.liskButton);
        allCurrenciesButtons.add(currencyButton14);
        ToggleButton currencyButton15 = findViewById(R.id.ethereumClassicButton);
        allCurrenciesButtons.add(currencyButton15);
        ToggleButton currencyButton16 = findViewById(R.id.raiblocksButton);
        allCurrenciesButtons.add(currencyButton16);
        ToggleButton currencyButton17 = findViewById(R.id.vergeButton);
        allCurrenciesButtons.add(currencyButton17);
        ToggleButton currencyButton18 = findViewById(R.id.siacoinButton);
        allCurrenciesButtons.add(currencyButton18);
        ToggleButton currencyButton19 = findViewById(R.id.stratisButton);
        allCurrenciesButtons.add(currencyButton19);
        ToggleButton currencyButton20 = findViewById(R.id.zcashButton);
        allCurrenciesButtons.add(currencyButton20);
        ToggleButton currencyDogecoin = findViewById(R.id.DogeCoinButton);
        allCurrenciesButtons.add(currencyDogecoin);
        ToggleButton currencyButton21 = findViewById(R.id.steemButton);
        allCurrenciesButtons.add(currencyButton21);
        ToggleButton currencyButton22 = findViewById(R.id.wavesButton);
        allCurrenciesButtons.add(currencyButton22);
        ToggleButton currencyButton23 = findViewById(R.id.vechainButton);
        allCurrenciesButtons.add(currencyButton23);
        ToggleButton currencyButton24 = findViewById(R.id.digibyteButton);
        allCurrenciesButtons.add(currencyButton24);
        ToggleButton currencyButton25 = findViewById(R.id.komodoButton);
        allCurrenciesButtons.add(currencyButton25);
        ToggleButton currencyButton26 = findViewById(R.id.hshareButton);
        allCurrenciesButtons.add(currencyButton26);
        ToggleButton currencyButton27 = findViewById(R.id.arkButton);
        allCurrenciesButtons.add(currencyButton27);
        ToggleButton currencyButton28 = findViewById(R.id.decredButton);
        allCurrenciesButtons.add(currencyButton28);
        ToggleButton currencyButton29 = findViewById(R.id.factomButton);
        allCurrenciesButtons.add(currencyButton29);
        ToggleButton currencyButton30 = findViewById(R.id.neblioButton);
        allCurrenciesButtons.add(currencyButton30);
        ToggleButton currencyButton31 = findViewById(R.id.digitalNoteButton);
        allCurrenciesButtons.add(currencyButton31);
        ToggleButton currencyButton32 = findViewById(R.id.nxtButton);
        allCurrenciesButtons.add(currencyButton32);
        ToggleButton currencyButton33 = findViewById(R.id.syscoinButton);
        allCurrenciesButtons.add(currencyButton33);
        ToggleButton currencyButton34 = findViewById(R.id.zcoinButton);
        allCurrenciesButtons.add(currencyButton34);
        ToggleButton currencyButton36 = findViewById(R.id.gameCreditsButton);
        allCurrenciesButtons.add(currencyButton36);
        ToggleButton currencyButton37 = findViewById(R.id.gxSharesButton);
        allCurrenciesButtons.add(currencyButton37);
        ToggleButton currencyButton38 = findViewById(R.id.vertcoinButton);
        allCurrenciesButtons.add(currencyButton38);
        ToggleButton eosButton = findViewById(R.id.eosButton);
        allCurrenciesButtons.add(eosButton);
        ToggleButton tronButton = findViewById(R.id.tronButton);
        allCurrenciesButtons.add(tronButton);
        ToggleButton bitcoinGoldButton = findViewById(R.id.bitcoinGoldButton);
        allCurrenciesButtons.add(bitcoinGoldButton);
        ToggleButton iconButton = findViewById(R.id.iconButton);
        allCurrenciesButtons.add(iconButton);
        ToggleButton omiseGoButton = findViewById(R.id.omiseGoButton);
        allCurrenciesButtons.add(omiseGoButton);
        ToggleButton ucashButton = findViewById(R.id.ucashButton);
        allCurrenciesButtons.add(ucashButton);
    }

    /**
     * Adds bitcoin and ethereum to every exchange by default
     */
    public void addBitcoinAndEthereumToExchanges(){
        Coin bitcoinBitfinex = new Coin("Bitcoin","BTC",
                "Bitfinex","100");
        HomePage.bitfinex.addCoin(bitcoinBitfinex);
        Coin bitcoinBittrex = new Coin("Bitcoin","BTC",
                "Bittrex","100");
        HomePage.bittrex.addCoin(bitcoinBittrex);
        Coin bitcoinBinance = new Coin("Bitcoin", "BTC", "Binance","100");
        HomePage.binance.addCoin(bitcoinBinance);
        Coin bitcoinHitBTC = new Coin("Bitcoin", "BTC",
                "HitBTC","100");
        HomePage.hitBTC.addCoin(bitcoinHitBTC);
        Coin bitcoinBitZ = new Coin("Bitcoin", "btc",
                "Bit-Z","100");
        HomePage.bitZ.addCoin(bitcoinBitZ);
        Coin bitcoinPoloniex = new Coin("Bitcoin","BTC",
                "Poloniex","100");
        HomePage.poloniex.addCoin(bitcoinPoloniex);
        Coin bitcoinBitStamp = new Coin ("Bitcoin","BTC",
                "BitStamp","100");
        HomePage.bitStamp.addCoin(bitcoinBitStamp);
        Coin bitcoinOKEX = new Coin("Bitcoin", "BTC",
                "OKEX","100");
        HomePage.OKEX.addCoin(bitcoinOKEX);
        Coin bitcoinGDAX = new Coin("Bitcoin","BTC",
                "GDAX","100");
        HomePage.GDAX.addCoin(bitcoinGDAX);
        Coin bitcoinKraken = new Coin("Bitcoin","XXBT",
                "Kraken", "100");
        HomePage.kraken.addCoin(bitcoinKraken);
        HomePage.listOfCurrencies.add("Bitcoin");

        Coin ethereumBitfinex = new Coin("Ethereum","ETH",
                "Bitfinex","110");
        HomePage.bitfinex.addCoin(ethereumBitfinex);
        Coin ethereumBittrex = new Coin("Ethereum","ETH",
                "Bittrex","110");
        HomePage.bittrex.addCoin(ethereumBittrex);
        Coin ethereumBinance = new Coin("Ethereum", "ETH",
                "Binance","110");
        HomePage.binance.addCoin(ethereumBinance);
        Coin ethereumHitBTC = new Coin("Ethereum", "ETH",
                "HitBTC","110");
        HomePage.hitBTC.addCoin(ethereumHitBTC);
        Coin ethereumBitZ = new Coin("Ethereum", "eth",
                "Bit-Z","110");
        HomePage.bitZ.addCoin(ethereumBitZ);
        Coin ethereumPoloniex = new Coin("Ethereum","ETH",
                "Poloniex","110");
        HomePage.poloniex.addCoin(ethereumPoloniex);
        Coin ethereumBitStamp = new Coin ("Ethereum","ETH",
                "BitStamp","110");
        HomePage.bitStamp.addCoin(ethereumBitStamp);
        Coin ethereumOKEX = new Coin("Ethereum", "ETH",
                "OKEX","110");
        HomePage.OKEX.addCoin(ethereumOKEX);
        Coin ethereumGDAX = new Coin("Ethereum","ETH",
                "GDAX", "110");
        HomePage.GDAX.addCoin(ethereumGDAX);
        Coin ethereumKraken = new Coin("Ethereum","XETH",
                "Kraken","110");
        HomePage.kraken.addCoin(ethereumKraken);
        HomePage.listOfCurrencies.add("Ethereum");
    }

    /**
     * Checks to see if button is selected, if it is, it adds it to all exchanges
     *     that have that currency
     * @param button is the ToggleButton you're checking
     */
    public void addCurrencyToExchanges(ToggleButton button){
        switch (button.getId()){
            case R.id.rippleButton:
                Coin rippleBitfinex = new Coin("Ripple","XRP",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(rippleBitfinex);
                Coin rippleBittrex = new Coin("Ripple","XRP",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(rippleBittrex);
                Coin rippleBinance = new Coin("Ripple", "XRP",
                        "Binance","011");
                HomePage.binance.addCoin(rippleBinance);
                Coin rippleHitBTC = new Coin("Ripple", "XRP",
                        "HitBTC","011");
                HomePage.hitBTC.addCoin(rippleHitBTC);
                Coin ripplePoloniex = new Coin("Ripple","XRP",
                        "Poloniex","110");
                HomePage.poloniex.addCoin(ripplePoloniex);
                Coin rippleBitStamp = new Coin ("Ripple","XRP",
                        "BitStamp","110");
                HomePage.bitStamp.addCoin(rippleBitStamp);
                Coin rippleOKEX = new Coin("Ripple", "XRP",
                        "OKEX","111");
                HomePage.OKEX.addCoin(rippleOKEX);
                Coin rippleKraken = new Coin("Ripple","XXRP",
                        "Kraken","110");
                HomePage.kraken.addCoin(rippleKraken);
                HomePage.listOfCurrencies.add("Ripple");
                break;

            case R.id.bitcoinCashButton:
                Coin bitcoinCashBitfinex = new Coin("Bitcoin Cash","BCH",
                        "Bitfinex","111");
                HomePage.bitfinex.addCoin(bitcoinCashBitfinex);
                Coin bitcoinCashBittrex = new Coin("Bitcoin Cash","BCC",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(bitcoinCashBittrex);
                Coin bitcoinCashBinance = new Coin("Bitcoin Cash", "BCC",
                        "Binance","111");
                HomePage.binance.addCoin(bitcoinCashBinance);
                Coin bitcoinCashHitBTC = new Coin("Bitcoin Cash", "BCH",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(bitcoinCashHitBTC);
                Coin bitcoinCashPoloniex = new Coin("Bitcoin Cash","BCH",
                        "Poloniex","111");
                HomePage.poloniex.addCoin(bitcoinCashPoloniex);
                Coin bitcoinCashBitStamp = new Coin ("Bitcoin Cash","BCH",
                        "BitStamp","110");
                HomePage.bitStamp.addCoin(bitcoinCashBitStamp);
                Coin bitcoinCashOKEX = new Coin("Bitcoin Cash", "BCH",
                        "OKEX","111");
                HomePage.OKEX.addCoin(bitcoinCashOKEX);
                Coin bitcoinCashGDAX = new Coin("Bitcoin Cash","BCH",
                        "GDAX","110");
                HomePage.GDAX.addCoin(bitcoinCashGDAX);
                Coin bitcoinCashKraken = new Coin("Bitcoin Cash","BCH",
                        "Kraken","110");
                HomePage.kraken.addCoin(bitcoinCashKraken);
                HomePage.listOfCurrencies.add("Bitcoin Cash");
                break;

            case R.id.cardanoButton:
                Coin cardanoBittrex = new Coin("Cardano","ADA",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(cardanoBittrex);
                Coin cardanoBinance = new Coin("Cardano", "ADA",
                        "Binance","011");
                HomePage.binance.addCoin(cardanoBinance);
                HomePage.listOfCurrencies.add("Cardano");
                break;

            case R.id.nemButton:
                Coin nemBittrex = new Coin("NEM","XEM",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(nemBittrex);
                Coin nemHitBTC = new Coin("NEM", "XEM",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(nemHitBTC);
                Coin nemPoloniex = new Coin("NEM","XEM",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(nemPoloniex);
                Coin nemOKEX = new Coin("NEM", "XEM",
                        "OKEX","111");
                HomePage.OKEX.addCoin(nemOKEX);
                HomePage.listOfCurrencies.add("NEM");
                break;

            case R.id.litecoinButton:
                Coin litecoinBitfinex = new Coin("Litecoin","LTC",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(litecoinBitfinex);
                Coin litecoinBittrex = new Coin("Litecoin","LTC",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(litecoinBittrex);
                Coin litecoinBinance = new Coin("Litecoin", "LTC",
                        "Binance","111");
                HomePage.binance.addCoin(litecoinBinance);
                Coin litecoinHitBTC = new Coin("Litecoin", "LTC",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(litecoinHitBTC);
                Coin litecoinBitZ = new Coin("Litecoin", "ltc",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(litecoinBitZ);
                Coin litecoinPoloniex = new Coin("Litecoin", "LTC",
                        "Poloniex","110");
                HomePage.poloniex.addCoin(litecoinPoloniex);
                Coin litecoinBitStamp = new Coin ("Litecoin","LTC",
                        "BitStamp","110");
                HomePage.bitStamp.addCoin(litecoinBitStamp);
                Coin litecoinOKEX = new Coin("Litecoin", "LTC",
                        "OKEX","111");
                HomePage.OKEX.addCoin(litecoinOKEX);
                Coin litecoinGDAX = new Coin("Litecoin","LTC",
                        "GDAX","100");
                HomePage.GDAX.addCoin(litecoinGDAX);
                Coin litecoinKraken = new Coin("Litecoin","XLTC",
                        "Kraken","110");
                HomePage.kraken.addCoin(litecoinKraken);
                HomePage.listOfCurrencies.add("Litecoin");
                break;

            case R.id.stellarButton:
                Coin stellarBittrex = new Coin("Stellar","XLM",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(stellarBittrex);
                Coin stellarBinance = new Coin("Stellar", "XLM",
                        "Binance","011");
                HomePage.binance.addCoin(stellarBinance);
                Coin stellarOKEX = new Coin("Stellar", "XLM",
                        "OKEX","111");
                HomePage.OKEX.addCoin(stellarOKEX);
                Coin stellarKraken = new Coin("Stellar","XXLM",
                        "Kraken","010");
                HomePage.kraken.addCoin(stellarKraken);
                HomePage.listOfCurrencies.add("Stellar");
                break;

            case R.id.iotaButton:
                Coin iotaBitfinex = new Coin("Iota","IOT",
                        "Bitfinex","111");
                HomePage.bitfinex.addCoin(iotaBitfinex);
                Coin iotaBinance = new Coin("Iota", "IOTA",
                        "Binance","011");
                HomePage.binance.addCoin(iotaBinance);
                Coin iotaOKEX = new Coin("Iota", "IOTA",
                        "OKEX","111");
                HomePage.OKEX.addCoin(iotaOKEX);
                HomePage.listOfCurrencies.add("Iota");
                break;

            case R.id.dashButton:
                Coin dashBitfinex = new Coin("Dash","DSH",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(dashBitfinex);
                Coin dashBittrex = new Coin("Dash","DASH",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(dashBittrex);
                Coin dashBinance = new Coin("Dash", "DASH", "Binance","011");
                HomePage.binance.addCoin(dashBinance);
                Coin dashHitBTC = new Coin("Dash", "DASH",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(dashHitBTC);
                Coin dashBitZ = new Coin("Dash", "dash",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(dashBitZ);
                Coin dashPoloniex = new Coin("Dash", "DASH",
                        "Poloniex","110");
                HomePage.poloniex.addCoin(dashPoloniex);
                Coin dashOKEX = new Coin("Dash", "DASH",
                        "OKEX","111");
                HomePage.OKEX.addCoin(dashOKEX);
                Coin dashKraken = new Coin("Dash","DASH",
                        "Kraken","110");
                HomePage.kraken.addCoin(dashKraken);
                HomePage.listOfCurrencies.add("Dash");
                break;

            case R.id.neoButton:
                Coin neoBitfinex = new Coin("NEO","NEO",
                        "Bitfinex","111");
                HomePage.bitfinex.addCoin(neoBitfinex);
                Coin neoBittrex = new Coin("NEO","NEO",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(neoBittrex);
                Coin neoBinance = new Coin("NEO", "NEO", "Binance","111");
                HomePage.binance.addCoin(neoBinance);
                Coin neoHitBTC = new Coin("NEO", "NEO",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(neoHitBTC);
                Coin neoOKEX = new Coin("NEO", "NEO",
                        "OKEX","111");
                HomePage.OKEX.addCoin(neoOKEX);
                HomePage.listOfCurrencies.add("NEO");
                break;

            case R.id.moneroButton:
                Coin moneroBitfinex = new Coin("Monero","XMR",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(moneroBitfinex);
                Coin moneroBittrex = new Coin("Monero","XMR",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(moneroBittrex);
                Coin moneroBinance = new Coin("Monero", "XMR",
                        "Binance","011");
                HomePage.binance.addCoin(moneroBinance);
                Coin moneroHitBTC = new Coin("Monero", "XMR",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(moneroHitBTC);
                Coin moneroPoloniex = new Coin("Monero", "XMR",
                        "Poloniex","110");
                HomePage.poloniex.addCoin(moneroPoloniex);
                Coin moneroOKEX = new Coin("Monero", "XMR",
                        "OKEX","111");
                HomePage.OKEX.addCoin(moneroOKEX);
                Coin moneroKraken = new Coin("Monero","XXMR",
                        "Kraken","110");
                HomePage.kraken.addCoin(moneroKraken);
                HomePage.listOfCurrencies.add("Monero");
                break;

            case R.id.qtumButton:
                Coin qtumBitfinex = new Coin("QTUM","QTM",
                        "Bitfinex","111");
                HomePage.bitfinex.addCoin(qtumBitfinex);
                Coin qtumBittrex = new Coin("QTUM","QTUM",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(qtumBittrex);
                Coin qtumBinance = new Coin("QTUM", "QTUM",
                        "Binance","011");
                HomePage.binance.addCoin(qtumBinance);
                Coin qtumHitBTC = new Coin("QTUM", "QTUM",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(qtumHitBTC);
                Coin qtumBitZ = new Coin("QTUM", "qtum",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(qtumBitZ);
                Coin qtumOKEX = new Coin("QTUM", "QTUM",
                        "OKEX","111");
                HomePage.OKEX.addCoin(qtumOKEX);
                HomePage.listOfCurrencies.add("QTUM");
                break;

            case R.id.liskButton:
                Coin liskBittrex = new Coin("Lisk","LSK",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(liskBittrex);
                Coin liskBinance = new Coin("Lisk", "LSK",
                        "Binance","011");
                HomePage.binance.addCoin(liskBinance);
                Coin liskHitBTC = new Coin("Lisk", "LSK",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(liskHitBTC);
                Coin liskBitZ = new Coin("Lisk", "lsk",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(liskBitZ);
                Coin liskPoloniex = new Coin("Lisk", "LSK",
                        "Poloniex","011");
                HomePage.poloniex.addCoin(liskPoloniex);
                HomePage.listOfCurrencies.add("Lisk");
                break;

            case R.id.ethereumClassicButton:
                Coin ethereumClassicBitfinex = new Coin("Ethereum Classic","ETC",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(ethereumClassicBitfinex);
                Coin ethereumClassicBittrex = new Coin("Ethereum Classic ","ETC",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(ethereumClassicBittrex);
                Coin ethereumClassicBinance = new Coin("Ethereum Classic", "ETC",
                        "Binance","011");
                HomePage.binance.addCoin(ethereumClassicBinance);
                Coin ethereumClassicHitBTC = new Coin("Ethereum Classic", "ETC",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(ethereumClassicHitBTC);
                Coin ethereumClassicBitZ = new Coin("Ethereum Classic", "etc",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(ethereumClassicBitZ);
                Coin ethereumClassicPoloniex = new Coin("Ethereum Classic", "ETC",
                        "Poloniex","111");
                HomePage.poloniex.addCoin(ethereumClassicPoloniex);
                Coin ethereumClassicOKEX = new Coin("Ethereum Classic", "ETC",
                        "OKEX","111");
                HomePage.OKEX.addCoin(ethereumClassicOKEX);
                Coin ethereumClassicKraken = new Coin("Ethereum Classic","XETC",
                        "Kraken","111");
                HomePage.kraken.addCoin(ethereumClassicKraken);
                HomePage.listOfCurrencies.add("Ethereum Classic");
                break;

            case R.id.raiblocksButton:
                Coin raiBlocksBitZ = new Coin("RaiBlocks", "xrb",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(raiBlocksBitZ);
                HomePage.listOfCurrencies.add("RaiBlocks");
                break;

            case R.id.vergeButton:
                Coin vergeBittrex = new Coin("Verge","XVG",
                        "Bittrex","110");
                HomePage.bittrex.addCoin(vergeBittrex);
                Coin vergeBinance = new Coin("Verge", "XVG",
                        "Binance","011");
                HomePage.binance.addCoin(vergeBinance);
                Coin vergeHitBTC = new Coin("Verge", "XVG",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(vergeHitBTC);
                HomePage.listOfCurrencies.add("Verge");
                break;

            case R.id.siacoinButton:
                Coin siacoinBittrex = new Coin("Siacoin","SC",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(siacoinBittrex);
                Coin siacoinHitBTC = new Coin("Siacoin", "SC",
                        "HitBTC","010");
                HomePage.hitBTC.addCoin(siacoinHitBTC);
                Coin siacoinPoloniex = new Coin("Siacoin", "SC",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(siacoinPoloniex);
                HomePage.listOfCurrencies.add("Siacoin");
                break;

            case R.id.stratisButton:
                Coin stratisBittrex = new Coin("Stratis","STRAT",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(stratisBittrex);
                Coin stratisBinance = new Coin("Stratis", "STRAT",
                        "Binance","011");
                HomePage.binance.addCoin(stratisBinance);
                Coin stratisHitBTC = new Coin("Stratis", "STRAT",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(stratisHitBTC);
                Coin stratisPoloniex = new Coin("Stratis", "STRAT",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(stratisPoloniex);
                HomePage.listOfCurrencies.add("Stratis");
                break;

            case R.id.zcashButton:
                Coin zcashBitfinex = new Coin("ZCash","ZEC",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(zcashBitfinex);
                Coin zcashBittrex = new Coin("ZCash","ZEC",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(zcashBittrex);
                Coin zcashBinance = new Coin("ZCash", "ZEC",
                        "Binance","011");
                HomePage.binance.addCoin(zcashBinance);
                Coin zcashHitBTC = new Coin("ZCash", "ZEC",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(zcashHitBTC);
                Coin zcashBitZ = new Coin("ZCash", "zec",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(zcashBitZ);
                Coin zcashPoloniex = new Coin("ZCash", "ZEC",
                        "Poloniex","111");
                HomePage.poloniex.addCoin(zcashPoloniex);
                Coin zcashOKEX = new Coin("ZCash", "ZEC",
                        "OKEX","111");
                HomePage.OKEX.addCoin(zcashOKEX);
                Coin zcashKraken = new Coin("ZCash","XZEC",
                        "Kraken","110");
                HomePage.kraken.addCoin(zcashKraken);
                HomePage.listOfCurrencies.add("ZCash");
                break;

            case R.id.dogecoinButton:
                Coin dogecoinBittrex = new Coin("Dogecoin","DOGE",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(dogecoinBittrex);
                Coin dogecoinHitBTC = new Coin("Dogecoin", "DOGE",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(dogecoinHitBTC);
                Coin dogecoinBitZ = new Coin("Dogecoin", "doge",
                        "Bit-Z", "011");
                HomePage.bitZ.addCoin(dogecoinBitZ);
                Coin dogecoinPoloniex = new Coin("Dogecoin", "DOGE",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(dogecoinPoloniex);
                HomePage.listOfCurrencies.add("Dogecoin");
                break;

            case R.id.steemButton: //Steem
                Coin steemBittrex = new Coin("Steem","STEEM",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(steemBittrex);
                Coin steemHitBTC = new Coin("Steem", "STEEM",
                        "HitBTC","010");
                HomePage.hitBTC.addCoin(steemHitBTC);
                Coin steemPoloniex = new Coin("Steem", "STEEM",
                        "Poloniex","011");
                HomePage.poloniex.addCoin(steemPoloniex);
                HomePage.listOfCurrencies.add("Steem");
                break;

            case R.id.wavesButton: //Waves
                Coin wavesBittrex = new Coin("Waves","WAVES",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(wavesBittrex);
                Coin wavesBinance = new Coin("Waves", "WAVES",
                        "Binance","011");
                HomePage.binance.addCoin(wavesBinance);
                HomePage.listOfCurrencies.add("Waves");
                break;

            case R.id.vechainButton: //VeChain
                Coin vechainBinance = new Coin("VeChain", "VEN",
                        "Binance","011");
                HomePage.binance.addCoin(vechainBinance);
                Coin vechainHitBTC = new Coin("VeChain", "VEN",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(vechainHitBTC);
                HomePage.listOfCurrencies.add("VeChain");
                break;

            case R.id.digibyteButton: //Digibyte
                Coin digibyteBittrex = new Coin("Digibyte","DGB",
                        "Bittrex","011");
                HomePage.bittrex.addCoin(digibyteBittrex);
                Coin digibyteHitBTC = new Coin("Digibyte", "DGB",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(digibyteHitBTC);
                Coin digibtyeBitZ = new Coin("Digibyte", "dgb",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(digibtyeBitZ);
                Coin digibytePoloniex = new Coin("Digibyte", "DGB",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(digibytePoloniex);
                Coin digibyteOKEX = new Coin("Digibtye", "DGB",
                        "OKEX","111");
                HomePage.OKEX.addCoin(digibyteOKEX);
                HomePage.listOfCurrencies.add("Digibyte");
                break;

            case R.id.komodoButton: //Komodo
                Coin komodoBittrex = new Coin("Komodo","KMD",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(komodoBittrex);
                Coin komodoBinance = new Coin("Komodo", "KMD",
                        "Binance", "011");
                HomePage.binance.addCoin(komodoBinance);
                Coin komodoHitBTC = new Coin("Komodo", "KMD",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(komodoHitBTC);
                HomePage.listOfCurrencies.add("Komodo");
                break;

            case R.id.hshareButton: //HShare
                Coin hshareBinance = new Coin("HShare", "HSR",
                        "Binance","011");
                HomePage.binance.addCoin(hshareBinance);
                Coin hshareHitBTC = new Coin("HShare", "HSR",
                        "HitBTC","010");
                HomePage.hitBTC.addCoin(hshareHitBTC);
                Coin hshareBitZ = new Coin("HShare", "hsr",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(hshareBitZ);
                Coin hshareOKEX = new Coin("HShare", "HSR",
                        "OKEX","111");
                HomePage.OKEX.addCoin(hshareOKEX);
                HomePage.listOfCurrencies.add("HShare");
                break;

            case R.id.arkButton: //Ark
                Coin arkBittrex = new Coin("Ark","ARK",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(arkBittrex);
                Coin arkBinance = new Coin("Ark", "ARK", "Binance","011");
                HomePage.binance.addCoin(arkBinance);
                Coin arkBitZ = new Coin("Ark", "ark",
                        "Bit-Z","010");
                HomePage.bitZ.addCoin(arkBitZ);
                Coin arkOKEX = new Coin("Ark", "ARK",
                        "OKEX","111");
                HomePage.OKEX.addCoin(arkOKEX);
                HomePage.listOfCurrencies.add("Ark");
                break;

            case R.id.decredButton:
                Coin decredBittrex = new Coin("Decred","DCR","Bittrex","010");
                HomePage.bittrex.addCoin(decredBittrex);
                Coin decredPoloniex = new Coin("Decred","DCR","Poloniex","010");
                HomePage.poloniex.addCoin(decredPoloniex);
                HomePage.listOfCurrencies.add("Decred");
                break;

            case R.id.factomButton:
                Coin factomBittrex = new Coin("Factom","FCT","Bittrex","011");
                HomePage.bittrex.addCoin(factomBittrex);
                Coin factomPoloniex = new Coin("Factom","FCT","Poloniex","010");
                HomePage.poloniex.addCoin(factomPoloniex);
                Coin factomBitZ = new Coin("Factom","fct","Bit-Z","010");
                HomePage.bitZ.addCoin(factomBitZ);
                HomePage.listOfCurrencies.add("Factom");
                break;

            case R.id.neblioButton:
                Coin neblioBinance = new Coin("Neblio","NEBL","Binance","011");
                HomePage.binance.addCoin(neblioBinance);
                HomePage.listOfCurrencies.add("Neblio");
                break;

            case R.id.digitalNoteButton:
                Coin digitalNoteBittrex = new Coin("Digital Note","XDN",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(digitalNoteBittrex);
                Coin digitalNoteHitBTC = new Coin("Digital Note","XDN",
                        "HitBTC","111");
                HomePage.hitBTC.addCoin(digitalNoteHitBTC);
                HomePage.listOfCurrencies.add("Digital Note");
                break;

            case R.id.nxtButton:
                Coin nxtHitBTC = new Coin("NXT","NXT","HitBTC","111");
                HomePage.hitBTC.addCoin(nxtHitBTC);
                Coin nxtBittrex = new Coin("NXT","NXT",
                        "Bittrex","110");
                HomePage.listOfCurrencies.add("NXT");
                Coin nxtPoloniex = new Coin("NXT","NXT",
                        "Poloniex","110");
                HomePage.poloniex.addCoin(nxtPoloniex);
                HomePage.listOfCurrencies.add("NXT");
                break;

            case R.id.syscoinButton:
                Coin syscoinBittrex = new Coin("Syscoin","SYS",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(syscoinBittrex);
                Coin syscoinPoloniex = new Coin("Syscoin","SYS",
                        "Bittrex","010");
                HomePage.poloniex.addCoin(syscoinPoloniex);
                HomePage.listOfCurrencies.add("Syscoin");
                break;

            case R.id.zcoinButton:
                Coin zcoinBittrex = new Coin("ZCoin","XZC","Bittrex","010");
                HomePage.bittrex.addCoin(zcoinBittrex);
                /* wait for Binance to be back up
                Coin zcoinBinance = new Coin("ZCoin","XZC","Binance","010");
                HomePage.binance.addCoin(zcoinBinance);
                */
                HomePage.listOfCurrencies.add("ZCoin");
                break;

            case R.id.gameCreditsButton:
                Coin gameCreditsBitZ = new Coin("Game Credits","game","Bit-Z","010");
                HomePage.bitZ.addCoin(gameCreditsBitZ);
                Coin gameCreditsBittrex = new Coin("Game Credits","GAME",
                        "Bittrex","010");
                HomePage.bittrex.addCoin(gameCreditsBittrex);
                Coin gameCreditsPoloniex = new Coin("Game Credits","GAME",
                        "Poloniex","010");
                HomePage.poloniex.addCoin(gameCreditsPoloniex);
                Coin gameCreditsHitBTC = new Coin("Game Credits","GAME",
                    "Bittrex","010");
                HomePage.hitBTC.addCoin(gameCreditsHitBTC);
                HomePage.listOfCurrencies.add("Game Credits");
                break;

            case R.id.gxSharesButton:
                /* wait until binance works again
                Coin gxSharesBinance = new Coin("GXShares","GXS",
                        "Binance","011");
                HomePage.binance.addCoin(gxSharesBinance);
                */
                Coin gxSharesBitZ = new Coin("GXShares","gxs","Bit-Z","011");
                HomePage.bitZ.addCoin(gxSharesBitZ);
                HomePage.listOfCurrencies.add("GXShares");
                break;

            case R.id.vertcoinButton:
                Coin vertcoinBittrex = new Coin("Vertcoin","VTC","Bittrex","010");
                HomePage.bittrex.addCoin(vertcoinBittrex);
                Coin vertcoinPoloniex = new Coin("Vertcoin","VTC","Poloniex","010");
                HomePage.poloniex.addCoin(vertcoinPoloniex);
                HomePage.listOfCurrencies.add("Vertcoin");
                break;

            case R.id.eosButton:
                Coin eosBitfinex = new Coin("EOS","EOS","Bitfinex","111");
                HomePage.bitfinex.addCoin(eosBitfinex);
                //Coin eosBinance = new Coin("EOS","EOS","Binance","011");
                //HomePage.binance.addCoin(eosBinance);
                //DEAL WITH KRAKEN HERE
                Coin eosBitZ = new Coin("EOS","eos","Bit-Z","010");
                HomePage.bitZ.addCoin(eosBitZ);
                Coin eosHitBTC = new Coin("EOS","EOS","HitBTC","111");
                HomePage.hitBTC.addCoin(eosHitBTC);
                HomePage.listOfCurrencies.add("EOS");
                break;

            case R.id.tronButton:
                Coin tronBitfinex = new Coin("Tron","TRX","Bitfinex","111");
                HomePage.bitfinex.addCoin(tronBitfinex);
                //Coin tronBinance = new Coin("Tron","TRX","Binance","011");
                //HomePage.binance.addCoin(tronBinance);
                Coin tronBitZ = new Coin("Tron","trx","BitZ","011");
                HomePage.bitZ.addCoin(tronBitZ);
                Coin tronHitBTC = new Coin("Tron","TRX","HitBTC","111");
                HomePage.hitBTC.addCoin(tronHitBTC);
                HomePage.listOfCurrencies.add("Tron");
                break;

            case R.id.bitcoinGoldButton:
                //Coin bitcoinGoldBinance = new Coin("Bitcoin Gold","BTG",
                //        "Binance","000");
                //HomePage.binance.addCoin(bitcoinGoldBinance);
                Coin bitcoinGoldBittrex = new Coin("Bitcoin Gold","BTG",
                        "Bittrex","111");
                HomePage.bittrex.addCoin(bitcoinGoldBittrex);
                Coin bitcoinGoldBitfinex = new Coin("Bitcoin Gold","BTG",
                        "Bitfinex","110");
                HomePage.bitfinex.addCoin(bitcoinGoldBitfinex);
                Coin bitcoinGoldHitBTC = new Coin("Bitcoin Gold","BTG",
                        "HitBTC","111");
                HomePage.listOfCurrencies.add("Bitcoin Gold");
                break;

            case R.id.iconButton:
                Coin iconBinance = new Coin("Icon","ICX","Binance","011");
                HomePage.binance.addCoin(iconBinance);
                Coin iconHitBTC = new Coin("Icon","ICX","Binance","111");
                HomePage.hitBTC.addCoin(iconHitBTC);
                HomePage.listOfCurrencies.add("Icon");
                break;

            case R.id.omiseGoButton:
                //Coin omiseGoBinance = new Coin("OmiseGO","OMG","Binance","000");
                //HomePage.binance.addCoin(omiseGoBinance);
                Coin omiseGoBitfinex = new Coin("OmiseGO","OMG","Binance","111");
                HomePage.bitfinex.addCoin(omiseGoBitfinex);
                Coin omiseGoBittrex = new Coin("OmiseGO","OMG","Binance","111");
                HomePage.bittrex.addCoin(omiseGoBittrex);
                Coin omiseGoPoloniex = new Coin("OmiseGO","OMG","Binance","011");
                HomePage.poloniex.addCoin(omiseGoPoloniex);
                HomePage.listOfCurrencies.add("OmiseGO");
                break;

            case R.id.ucashButton:
                //NO MAJOR EXCHANGES YET



            default:
                break;
        }
    }

    /**
     * Clears all exchanges -- makes them have no coins
     */
    public void clearExchanges(){
        for(Exchange exchange: HomePage.listOfExchanges){
            if(exchange != null) {
                exchange.getCoins().clear();
            }
        }
    }

    /**
     * Saves files listing whether each coin is toggled to "On" or "Off"
     * @param listOfButtons of the list of all buttons viewable
     */
    public void saveSelectedCoinsInfo(ArrayList<ToggleButton> listOfButtons){
        String fileName = "CurrenciesInformation";
        String message;
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            for(ToggleButton b1:allCurrenciesButtons){
                message = Boolean.toString(b1.isChecked());
                fileOutputStream.write(message.getBytes());
            }
            fileOutputStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves file and determines which coins should be toggled to "On" and
     *     which coins should be toggled to "Off"
     */
    public void getSavedCoins(){
        int numExchanges = allCurrenciesButtons.size();
        int counter = 0;
        try {
            FileInputStream fileInputStream = openFileInput("CurrenciesInformation");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //while more lines
            System.out.println("Size of allExchangesButton is: " + numExchanges);
            StringBuilder message = new StringBuilder();
            int data = 1;
            while(counter < numExchanges && data != -1){
                data = bufferedReader.read();
                message.append((char) data);
                //if word is true, set exchange value to true
                if(message.length()==4 && message.charAt(0) == 't'){
                    allCurrenciesButtons.get(counter).setChecked(true); //why is above statement necessary/ why doesn't
                    //the text automatically change once it's checked
                    message.delete(0, 4);
                    System.out.println("Went" + allCurrenciesButtons.get(counter).isChecked() + counter);
                    counter++;

                }
                else if (message.length() == 5 && message.charAt(0) == 'f'){
                    //if word is false, set exchange value to false
                    allCurrenciesButtons.get(counter).setChecked(false);
                    message.delete(0, 5);
                    System.out.println("went false" + counter);
                    counter++;
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}