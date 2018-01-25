package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
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

public class Cryptocurrencies extends Activity implements View.OnClickListener {
    ArrayList<ToggleButton> allCurrenciesButtons;
    Button submitCurrencyButton;
    Button selectAllCurrenciesButton;


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
                for(ToggleButton button : allCurrenciesButtons){
                    if(button.isChecked()) {
                        addCurrencyToExchanges(button);
                    }
                }
                saveSelectedCoinsInfo(allCurrenciesButtons);
                System.out.println("size is: " + HomePage.bitfinex.getCoins().size());
                startActivity(new Intent(this, HomePage.class));

        }
    }

    public void setUpButtons(){
        allCurrenciesButtons = new ArrayList<>();
        ToggleButton currencyButton1 = findViewById(R.id.currency1_button);
        allCurrenciesButtons.add(currencyButton1);
        ToggleButton currencyButton2 = findViewById(R.id.currency2_button);
        allCurrenciesButtons.add(currencyButton2);
        ToggleButton currencyButton3 = findViewById(R.id.currency3_button);
        allCurrenciesButtons.add(currencyButton3);
        ToggleButton currencyButton4 = findViewById(R.id.currency4_button);
        allCurrenciesButtons.add(currencyButton4);
        ToggleButton currencyButton5 = findViewById(R.id.currency5_button);
        allCurrenciesButtons.add(currencyButton5);
        ToggleButton currencyButton6 = findViewById(R.id.currency6_button);
        allCurrenciesButtons.add(currencyButton6);
        ToggleButton currencyButton7 = findViewById(R.id.currency7_button);
        allCurrenciesButtons.add(currencyButton7);
        ToggleButton currencyButton8 = findViewById(R.id.currency8_button);
        allCurrenciesButtons.add(currencyButton8);
        ToggleButton currencyButton9 = findViewById(R.id.currency9_button);
        allCurrenciesButtons.add(currencyButton9);
        ToggleButton currencyButton10 = findViewById(R.id.currency10_button);
        allCurrenciesButtons.add(currencyButton10);
        ToggleButton currencyButton11 = findViewById(R.id.currency11_button);
        allCurrenciesButtons.add(currencyButton11);
        ToggleButton currencyButton12 = findViewById(R.id.currency12_button);
        allCurrenciesButtons.add(currencyButton12);
        ToggleButton currencyButton13 = findViewById(R.id.currency13_button);
        allCurrenciesButtons.add(currencyButton13);
        ToggleButton currencyButton14 = findViewById(R.id.currency14_button);
        allCurrenciesButtons.add(currencyButton14);
        ToggleButton currencyButton15 = findViewById(R.id.currency15_button);
        allCurrenciesButtons.add(currencyButton15);
        ToggleButton currencyButton16 = findViewById(R.id.currency16_button);
        allCurrenciesButtons.add(currencyButton16);
        ToggleButton currencyButton17 = findViewById(R.id.currency17_button);
        allCurrenciesButtons.add(currencyButton17);
        ToggleButton currencyButton18 = findViewById(R.id.currency18_button);
        allCurrenciesButtons.add(currencyButton18);
        ToggleButton currencyButton19 = findViewById(R.id.currency19_button);
        allCurrenciesButtons.add(currencyButton19);
        ToggleButton currencyButton20 = findViewById(R.id.currency20_button);
        allCurrenciesButtons.add(currencyButton20);
        ToggleButton currencyButton21 = findViewById(R.id.currency21_button);
        allCurrenciesButtons.add(currencyButton21);
        ToggleButton currencyButton22 = findViewById(R.id.currency22_button);
        allCurrenciesButtons.add(currencyButton22);
        ToggleButton currencyButton23 = findViewById(R.id.currency23_button);
        allCurrenciesButtons.add(currencyButton23);
        ToggleButton currencyButton24 = findViewById(R.id.currency24_button);
        allCurrenciesButtons.add(currencyButton24);
        ToggleButton currencyButton25 = findViewById(R.id.currency25_button);
        allCurrenciesButtons.add(currencyButton25);
        ToggleButton currencyButton26 = findViewById(R.id.currency26_button);
        allCurrenciesButtons.add(currencyButton26);
        ToggleButton currencyButton27 = findViewById(R.id.currency27_button);
        allCurrenciesButtons.add(currencyButton27);
        ToggleButton currencyButton28 = findViewById(R.id.currency28_button);
        allCurrenciesButtons.add(currencyButton28);
        ToggleButton currencyButton29 = findViewById(R.id.currency29_button);
        allCurrenciesButtons.add(currencyButton29);
        ToggleButton currencyButton30 = findViewById(R.id.currency30_button);
        allCurrenciesButtons.add(currencyButton30);
        ToggleButton currencyButton31 = findViewById(R.id.currency31_button);
        allCurrenciesButtons.add(currencyButton31);
        ToggleButton currencyButton32 = findViewById(R.id.currency32_button);
        allCurrenciesButtons.add(currencyButton32);
        ToggleButton currencyButton33 = findViewById(R.id.currency33_button);
        allCurrenciesButtons.add(currencyButton33);
        ToggleButton currencyButton34 = findViewById(R.id.currency34_button);
        allCurrenciesButtons.add(currencyButton34);
        ToggleButton currencyButton35 = findViewById(R.id.currency35_button);
        allCurrenciesButtons.add(currencyButton35);
        ToggleButton currencyButton36 = findViewById(R.id.currency36_button);
        allCurrenciesButtons.add(currencyButton36);
        ToggleButton currencyButton37 = findViewById(R.id.currency37_button);
        allCurrenciesButtons.add(currencyButton37);
        ToggleButton currencyButton38 = findViewById(R.id.currency38_button);
        allCurrenciesButtons.add(currencyButton38);
    }

    /**
     * Checks to see if button is selected, if it is, it adds it to all exchanges
     *     that have that currency
     * @param button is the ToggleButton you're checking
     */
    public void addCurrencyToExchanges(ToggleButton button){
        switch (button.getId()){
            case R.id.currency1_button:
                System.out.println("BitcoinBitcoin");
                Coin bitcoinBitfinex = new Coin("Bitcoin","BTC", "Bitfinex");
                HomePage.bitfinex.addCoin(bitcoinBitfinex);
                Coin bitcoinBittrex = new Coin("Bitcoin","BTC", "Bittrex");
                HomePage.bittrex.addCoin(bitcoinBittrex);
                Coin bitcoinBinance = new Coin("Bitcoin", "BTC", "Binance");
                HomePage.binance.addCoin(bitcoinBinance);
                Coin bitcoinHitBTC = new Coin("Bitcoin", "BTC", "HitBTC");
                HomePage.hitBTC.addCoin(bitcoinHitBTC);
                Coin bitcoinBitZ = new Coin("Bitcoin", "btc", "Bit-Z");
                HomePage.bitZ.addCoin(bitcoinBitZ);
                Coin bitcoinPoloniex = new Coin("Bitcoin","BTC","Poloniex");
                HomePage.poloniex.addCoin(bitcoinPoloniex);
                break;

            case R.id.currency2_button:
                Coin ethereumBitfinex = new Coin("Ethereum","ETH", "Bitfinex");
                HomePage.bitfinex.addCoin(ethereumBitfinex);
                Coin ethereumBittrex = new Coin("Ethereum","ETH", "Bittrex");
                HomePage.bittrex.addCoin(ethereumBittrex);
                Coin ethereumBinance = new Coin("Ethereum", "ETH", "Binance");
                HomePage.binance.addCoin(ethereumBinance);
                Coin ethereumHitBTC = new Coin("Ethereum", "ETH", "HitBTC");
                HomePage.hitBTC.addCoin(ethereumHitBTC);
                Coin ethereumBitZ = new Coin("Ethereum", "eth", "Bit-Z");
                HomePage.bitZ.addCoin(ethereumBitZ);
                Coin ethereumPoloniex = new Coin("Ethereum","ETH","Poloniex");
                HomePage.poloniex.addCoin(ethereumPoloniex);
                break;

            case R.id.currency3_button:
                Coin rippleBitfinex = new Coin("Ripple","XRP", "Bitfinex");
                HomePage.bitfinex.addCoin(rippleBitfinex);
                Coin rippleBittrex = new Coin("Ripple","XRP", "Bittrex");
                HomePage.bittrex.addCoin(rippleBittrex);
                Coin rippleBinance = new Coin("Ripple", "XRP", "Binance");
                HomePage.binance.addCoin(rippleBinance);
                Coin rippleHitBTC = new Coin("Ripple", "XRP", "HitBTC");
                HomePage.hitBTC.addCoin(rippleHitBTC);
                Coin ripplePoloniex = new Coin("Ripple","XRP","Poloniex");
                HomePage.poloniex.addCoin(ripplePoloniex);
                break;

            case R.id.currency4_button:
                Coin bitcoinCashBitfinex = new Coin("Bitcoin Cash","BCC", "Bitfinex");
                HomePage.bitfinex.addCoin(bitcoinCashBitfinex);
                Coin bitcoinCashBittrex = new Coin("Bitcoin Cash","BCC", "Bittrex");
                HomePage.bittrex.addCoin(bitcoinCashBittrex);
                Coin bitcoinCashBinance = new Coin("Bitcoin Cash", "BCC", "Binance");
                HomePage.binance.addCoin(bitcoinCashBinance);
                Coin bitcoinCashHitBTC = new Coin("Bitcoin Cash", "BCC", "HitBTC");
                HomePage.hitBTC.addCoin(bitcoinCashHitBTC);
                Coin bitcoinCashPoloniex = new Coin("Bitcoin","BCH","Poloniex");
                HomePage.poloniex.addCoin(bitcoinCashPoloniex);
                break;

            case R.id.currency5_button:
                Coin cardanoBittrex = new Coin("Cardano","ADA", "Bittrex");
                HomePage.bittrex.addCoin(cardanoBittrex);
                Coin cardanoBinance = new Coin("Cardano", "ADA", "Binance");
                HomePage.binance.addCoin(cardanoBinance);
                break;

            case R.id.currency6_button:
                Coin nemBittrex = new Coin("NEM","XEM", "Bittrex");
                HomePage.bittrex.addCoin(nemBittrex);
                Coin nemHitBTC = new Coin("NEM", "XEM", "HitBTC");
                HomePage.hitBTC.addCoin(nemHitBTC);
                Coin nemPoloniex = new Coin("NEM","XEM","Poloniex");
                HomePage.poloniex.addCoin(nemPoloniex);
                break;

            case R.id.currency7_button:
                Coin litecoinBitfinex = new Coin("Litecoin","LTC", "Bitfinex");
                HomePage.bitfinex.addCoin(litecoinBitfinex);
                Coin litecoinBittrex = new Coin("Litecoin","LTC", "Bittrex");
                HomePage.bittrex.addCoin(litecoinBittrex);
                Coin litecoinBinance = new Coin("Litecoin", "LTC", "Binance");
                HomePage.binance.addCoin(litecoinBinance);
                Coin litecoinHitBTC = new Coin("Litecoin", "LTC", "HitBTC");
                HomePage.hitBTC.addCoin(litecoinHitBTC);
                Coin litecoinBitZ = new Coin("Litecoin", "ltc", "Bit-Z");
                HomePage.bitZ.addCoin(litecoinBitZ);
                Coin litecoinPoloniex = new Coin("Litecoin", "LTC", "Poloniex");
                HomePage.poloniex.addCoin(litecoinPoloniex);
                break;

            case R.id.currency8_button:
                Coin stellarBittrex = new Coin("Stellar","XLM", "Bittrex");
                HomePage.bittrex.addCoin(stellarBittrex);
                Coin stellarBinance = new Coin("Stellar", "XLM", "Binance");
                HomePage.binance.addCoin(stellarBinance);
                break;

            case R.id.currency9_button:
                Coin iotaBitfinex = new Coin("IOTA","IOT", "Bitfinex");
                HomePage.bitfinex.addCoin(iotaBitfinex);
                Coin iotaBinance = new Coin("Iota", "IOTA", "Binance");
                HomePage.binance.addCoin(iotaBinance);
                break;

            case R.id.currency10_button:
                Coin dashBitfinex = new Coin("Dash","DSH", "Bitfinex");
                HomePage.bitfinex.addCoin(dashBitfinex);
                Coin dashBittrex = new Coin("Dash","DASH", "Bittrex");
                HomePage.bittrex.addCoin(dashBittrex);
                Coin dashBinance = new Coin("Dash", "DASH", "Binance");
                HomePage.binance.addCoin(dashBinance);
                Coin dashHitBTC = new Coin("Dash", "DASH", "HitBTC");
                HomePage.hitBTC.addCoin(dashHitBTC);
                Coin dashBitZ = new Coin("Dash", "dash", "Bit-Z");
                HomePage.bitZ.addCoin(dashBitZ);
                Coin dashPoloniex = new Coin("Dash", "DASH", "Poloniex");
                HomePage.poloniex.addCoin(dashPoloniex);

                break;

            case R.id.currency11_button:
                Coin neoBitfinex = new Coin("NEO","NEO", "Bitfinex");
                HomePage.bitfinex.addCoin(neoBitfinex);
                Coin neoBittrex = new Coin("NEO","NEO", "Bittrex");
                HomePage.bittrex.addCoin(neoBittrex);
                Coin neoBinance = new Coin("NEO", "NEO", "Binance");
                HomePage.binance.addCoin(neoBinance);
                Coin neoHitBTC = new Coin("NEO", "NEO", "HitBTC");
                HomePage.hitBTC.addCoin(neoHitBTC);
                break;

            case R.id.currency12_button:
                Coin moneroBitfinex = new Coin("Monero","XMR", "Bitfinex");
                HomePage.bitfinex.addCoin(moneroBitfinex);
                Coin moneroBittrex = new Coin("Monero","XMR", "Bittrex");
                HomePage.bittrex.addCoin(moneroBittrex);
                Coin moneroBinance = new Coin("Monero", "XMR", "Binance");
                HomePage.binance.addCoin(moneroBinance);
                Coin moneroHitBTC = new Coin("Monero", "XMR", "HitBTC");
                HomePage.hitBTC.addCoin(moneroHitBTC);
                Coin moneroPoloniex = new Coin("Monero", "XMR", "Poloniex");
                HomePage.poloniex.addCoin(moneroPoloniex);
                break;

            case R.id.currency13_button:
                Coin qtumBitfinex = new Coin("QTUM","QTM", "Bitfinex");
                HomePage.bitfinex.addCoin(qtumBitfinex);
                Coin qtumBittrex = new Coin("QTUM","QTUM", "Bittrex");
                HomePage.bittrex.addCoin(qtumBittrex);
                Coin qtumBinance = new Coin("QTUM", "QTUM", "Binance");
                HomePage.binance.addCoin(qtumBinance);
                Coin qtumHitBTC = new Coin("QTUM", "QTUM", "HitBTC");
                HomePage.hitBTC.addCoin(qtumHitBTC);
                Coin qtumBitZ = new Coin("QTUM", "qtum", "Bit-Z");
                HomePage.bitZ.addCoin(qtumBitZ);
                break;

            case R.id.currency14_button:
                Coin liskBittrex = new Coin("Lisk","LSK", "Bittrex");
                HomePage.bittrex.addCoin(liskBittrex);
                Coin liskBinance = new Coin("Lisk", "LSK", "Binance");
                HomePage.binance.addCoin(liskBinance);
                Coin liskHitBTC = new Coin("Lisk", "LSK", "HitBTC");
                HomePage.hitBTC.addCoin(liskHitBTC);
                Coin liskBitZ = new Coin("Lisk", "lsk", "Bit-Z");
                HomePage.bitZ.addCoin(liskBitZ);
                Coin liskPoloniex = new Coin("Lisk", "LSK", "Poloniex");
                HomePage.poloniex.addCoin(liskPoloniex);
                break;

            case R.id.currency15_button:
                Coin ethereumClassicBitfinex = new Coin("Ethereum Classic","ETC", "Bitfinex");
                HomePage.bitfinex.addCoin(ethereumClassicBitfinex);
                Coin ethereumClassicBittrex = new Coin("Ethereum Classic ","ETC", "Bittrex");
                HomePage.bittrex.addCoin(ethereumClassicBittrex);
                Coin ethereumClassicBinance = new Coin("Ethereum Classic", "ETC", "Binance");
                HomePage.binance.addCoin(ethereumClassicBinance);
                Coin ethereumClassicHitBTC = new Coin("Ethereum Classic", "ETC", "HitBTC");
                HomePage.hitBTC.addCoin(ethereumClassicHitBTC);
                Coin ethereumClassicBitZ = new Coin("Ethereum Classic", "etc", "Bit-Z");
                HomePage.bitZ.addCoin(ethereumClassicBitZ);
                Coin ethereumClassicPoloniex = new Coin("Ethereum Classic", "ETC", "Poloniex");
                HomePage.poloniex.addCoin(ethereumClassicPoloniex);
                break;

            case R.id.currency16_button:
                Coin raiBlocksBitZ = new Coin("RaiBlocks", "xrb", "Bit-Z");
                HomePage.bitZ.addCoin(raiBlocksBitZ);

                //Raiblocks
                break;

            case R.id.currency17_button:
                Coin vergeBittrex = new Coin("Verge","XVG", "Bittrex");
                HomePage.bittrex.addCoin(vergeBittrex);
                Coin vergeBinance = new Coin("Verge", "XVG", "Binance");
                HomePage.binance.addCoin(vergeBinance);
                Coin vergeHitBTC = new Coin("Verge", "XVG", "HitBTC");
                HomePage.hitBTC.addCoin(vergeHitBTC);
                break;

            case R.id.currency18_button:
                Coin siacoinBittrex = new Coin("Siacoin","SC", "Bittrex");
                HomePage.bittrex.addCoin(siacoinBittrex);
                Coin siacoinHitBTC = new Coin("Siacoin", "SC", "HitBTC");
                HomePage.hitBTC.addCoin(siacoinHitBTC);
                Coin siacoinPoloniex = new Coin("Siacoin", "SC", "Poloniex");
                HomePage.poloniex.addCoin(siacoinPoloniex);
                break;

            case R.id.currency19_button:
                Coin stratisBittrex = new Coin("Stratis","STRAT", "Bittrex");
                HomePage.bittrex.addCoin(stratisBittrex);
                Coin stratisBinance = new Coin("Stratis", "STRAT", "Binance");
                HomePage.binance.addCoin(stratisBinance);
                Coin stratisHitBTC = new Coin("Stratis", "STRAT", "HitBTC");
                HomePage.hitBTC.addCoin(stratisHitBTC);
                Coin stratisPoloniex = new Coin("Stratis", "STRAT", "Poloniex");
                HomePage.poloniex.addCoin(stratisPoloniex);
                break;

            case R.id.currency20_button:
                Coin zcashBitfinex = new Coin("Z-Cash","ZEC", "Bitfinex");
                HomePage.bitfinex.addCoin(zcashBitfinex);
                Coin zcashBittrex = new Coin("Z-Cash","ZEC", "Bittrex");
                HomePage.bittrex.addCoin(zcashBittrex);
                Coin zcashBinance = new Coin("Z-Cash", "ZEC", "Binance");
                HomePage.binance.addCoin(zcashBinance);
                Coin zcashHitBTC = new Coin("Z-Cash", "ZEC", "HitBTC");
                HomePage.hitBTC.addCoin(zcashHitBTC);
                Coin zcashBitZ = new Coin("Z-Cash", "zec", "Bit-Z");
                HomePage.bitZ.addCoin(zcashBitZ);
                Coin zcashPoloniex = new Coin("Z-Cash", "ZEC", "Poloniex");
                HomePage.poloniex.addCoin(zcashPoloniex);
                break;

            case R.id.currency21_button: //DogeCoin
                Coin dogecoinBittrex = new Coin("Dogecoin","DOGE", "Bittrex");
                HomePage.bittrex.addCoin(dogecoinBittrex);
                Coin dogecoinHitBTC = new Coin("Dogecoin", "DOGE", "HitBTC");
                HomePage.hitBTC.addCoin(dogecoinHitBTC);
                Coin dogecoinBitZ = new Coin("Dogecoin", "doge", "Bit-Z");
                HomePage.bitZ.addCoin(dogecoinBitZ);
                Coin dogecoinPoloniex = new Coin("Dogecoin", "DOGE", "Poloniex");
                HomePage.poloniex.addCoin(dogecoinPoloniex);
                break;

            case R.id.currency22_button: //Steem
                Coin steemBittrex = new Coin("Steem","STEEM", "Bittrex");
                HomePage.bittrex.addCoin(steemBittrex);
                Coin steemHitBTC = new Coin("Steem", "STEEM", "HitBTC");
                HomePage.hitBTC.addCoin(steemHitBTC);
                Coin steemPoloniex = new Coin("Steem", "STEEM", "Poloniex");
                HomePage.poloniex.addCoin(steemPoloniex);
                break;

            case R.id.currency23_button: //Waves
                Coin wavesBittrex = new Coin("Waves","WAVES", "Bittrex");
                HomePage.bittrex.addCoin(wavesBittrex);
                Coin wavesBinance = new Coin("Waves", "WAVES", "Binance");
                HomePage.binance.addCoin(wavesBinance);
                break;

            case R.id.currency24_button: //VeChain
                Coin vechainBinance = new Coin("VeChain", "VEN", "Binance");
                HomePage.binance.addCoin(vechainBinance);
                Coin vechainHitBTC = new Coin("VeChain", "VEN", "HitBTC");
                HomePage.hitBTC.addCoin(vechainHitBTC);
                break;

            case R.id.currency25_button: //Digibyte
                Coin digibyteBittrex = new Coin("Digibyte","DGB", "Bittrex");
                HomePage.bittrex.addCoin(digibyteBittrex);
                Coin digibyteHitBTC = new Coin("Digibyte", "DGB", "HitBTC");
                HomePage.hitBTC.addCoin(digibyteHitBTC);
                Coin digibtyeBitZ = new Coin("Digibyte", "dgb", "Bit-Z");
                HomePage.bitZ.addCoin(digibtyeBitZ);
                Coin digibytePoloniex = new Coin("Digibyte", "DGB", "Poloniex");
                HomePage.poloniex.addCoin(digibytePoloniex);
                break;

            case R.id.currency26_button: //Komodo
                Coin komodoBittrex = new Coin("Komodo","KMD", "Bittrex");
                HomePage.bittrex.addCoin(komodoBittrex);
                Coin komodoBinance = new Coin("Komodo", "KMD", "Binance");
                HomePage.binance.addCoin(komodoBinance);
                Coin komodoHitBTC = new Coin("Komodo", "KMD", "HitBTC");
                HomePage.hitBTC.addCoin(komodoHitBTC);
                break;

            case R.id.currency27_button: //HShare
                Coin hshareBinance = new Coin("HShare", "HSR", "Binance");
                HomePage.binance.addCoin(hshareBinance);
                Coin hshareHitBTC = new Coin("HShare", "HSR", "HitBTC");
                HomePage.hitBTC.addCoin(hshareHitBTC);
                Coin hshareBitZ = new Coin("HShare", "hsr", "Bit-Z");
                HomePage.bitZ.addCoin(hshareBitZ);
                break;

            case R.id.currency28_button: //Ark
                Coin arkBittrex = new Coin("Ark","ARK", "Bittrex");
                HomePage.bittrex.addCoin(arkBittrex);
                Coin arkBinance = new Coin("Ark", "ARK", "Binance");
                HomePage.binance.addCoin(arkBinance);
                Coin arkBitZ = new Coin("Ark", "ARK", "Bit-Z");
                HomePage.bitZ.addCoin(arkBitZ);
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
     * @param listOfButtons
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