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
                break;

            case R.id.currency2_button:
                Coin ethereumBitfinex = new Coin("Ethereum","ETH", "Bitfinex");
                HomePage.bitfinex.addCoin(ethereumBitfinex);
                Coin ethereumBittrex = new Coin("Ethereum","ETH", "Bittrex");
                HomePage.bittrex.addCoin(ethereumBittrex);
                break;

            case R.id.currency3_button:
                Coin rippleBitfinex = new Coin("Ripple","XRP", "Bitfinex");
                HomePage.bitfinex.addCoin(rippleBitfinex);
                Coin rippleBittrex = new Coin("Ripple","XRP", "Bittrex");
                HomePage.bittrex.addCoin(rippleBittrex);
                break;

            case R.id.currency4_button:
                Coin bitcoinCashBitfinex = new Coin("Bitcoin Cash","BCC", "Bitfinex");
                HomePage.bitfinex.addCoin(bitcoinCashBitfinex);
                Coin bitcoinCashBittrex = new Coin("BitcoinCash","BCC", "Bittrex");
                HomePage.bittrex.addCoin(bitcoinCashBittrex);
                break;

            case R.id.currency5_button:
                Coin cardanoBittrex = new Coin("Cardano","ADA", "Bittrex");
                HomePage.bittrex.addCoin(cardanoBittrex);
                break;

            case R.id.currency6_button:
                Coin nemBittrex = new Coin("NEM","NEM", "Bittrex");
                HomePage.bittrex.addCoin(nemBittrex);
                break;

            case R.id.currency7_button:
                Coin litecoinBitfinex = new Coin("Litecoin","LTC", "Bitfinex");
                HomePage.bitfinex.addCoin(litecoinBitfinex);
                Coin litecoinBittrex = new Coin("Litecoin","LTC", "Bittrex");
                HomePage.bittrex.addCoin(litecoinBittrex);
                break;

            case R.id.currency8_button:
                Coin stellarBittrex = new Coin("Stellar","XLM", "Bittrex");
                HomePage.bittrex.addCoin(stellarBittrex);
                break;

            case R.id.currency9_button:
                Coin iotaBitfinex = new Coin("IOTA","IOT", "Bitfinex");
                HomePage.bitfinex.addCoin(iotaBitfinex);
                break;

            case R.id.currency10_button:
                Coin dashBitfinex = new Coin("Dash","DSH", "Bitfinex");
                HomePage.bitfinex.addCoin(dashBitfinex);
                Coin dashBittrex = new Coin("Dash","DASH", "Bittrex");
                HomePage.bittrex.addCoin(dashBittrex);
                break;

            case R.id.currency11_button:
                Coin neoBitfinex = new Coin("NEO","NEO", "Bitfinex");
                HomePage.bitfinex.addCoin(neoBitfinex);
                Coin neoBittrex = new Coin("NEO","NEO", "Bittrex");
                HomePage.bittrex.addCoin(neoBittrex);

                break;

            case R.id.currency12_button:
                Coin moneroBitfinex = new Coin("Monero","ETH", "Bitfinex");
                HomePage.bitfinex.addCoin(moneroBitfinex);
                Coin moneroBittrex = new Coin("Monero","ETH", "Bittrex");
                HomePage.bittrex.addCoin(moneroBittrex);
                break;

            case R.id.currency13_button:
                Coin qtumBitfinex = new Coin("QTUM","ETH", "Bitfinex");
                HomePage.bitfinex.addCoin(qtumBitfinex);
                Coin qtumBittrex = new Coin("QTUM","ETH", "Bittrex");
                HomePage.bittrex.addCoin(qtumBittrex);
                break;

            case R.id.currency14_button:
                Coin liskBittrex = new Coin("Lisk","ETH", "Bittrex");
                HomePage.bittrex.addCoin(liskBittrex);
                break;

            case R.id.currency15_button:
                Coin ethereumClassicBitfinex = new Coin("Ethereum Classic","ETC", "Bitfinex");
                HomePage.bitfinex.addCoin(ethereumClassicBitfinex);
                Coin ethereumClassicBittrex = new Coin("Ethereum Classic ","ETC", "Bittrex");
                HomePage.bittrex.addCoin(ethereumClassicBittrex);
                break;

            case R.id.currency16_button:
                //Raiblocks
                break;

            case R.id.currency17_button:
                Coin vergeBittrex = new Coin("Verge","ETH", "Bittrex");
                HomePage.bittrex.addCoin(vergeBittrex);
                break;

            case R.id.currency18_button:
                Coin siacoinBittrex = new Coin("Siacoin","ETH", "Bittrex");
                HomePage.bittrex.addCoin(siacoinBittrex);
                break;

            case R.id.currency19_button:
                Coin stratisBittrex = new Coin("Stratis","ETH", "Bittrex");
                HomePage.bittrex.addCoin(stratisBittrex);
                break;

            case R.id.currency20_button:
                Coin zcashBitfinex = new Coin("Z-Cash","ZEC", "Bitfinex");
                HomePage.bitfinex.addCoin(zcashBitfinex);
                Coin zcashBittrex = new Coin("Z-Cash","ETH", "Bittrex");
                HomePage.bittrex.addCoin(zcashBittrex);
                break;

            case R.id.currency21_button: //DogeCoin
                break;

            case R.id.currency22_button: //Steem
                break;

            case R.id.currency23_button: //Waves
                break;

            case R.id.currency24_button: //VeChain
                break;

            case R.id.currency25_button: //Digibyte
                break;

            case R.id.currency26_button: //Komodo
                break;

            case R.id.currency27_button: //HShare
                break;

            case R.id.currency28_button: //Ark
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