package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */

public class Exchanges extends Activity implements View.OnClickListener{

    static ArrayList<ToggleButton> allExchangesButton;

    SharedPreferences exchangeInfo; //for storing the exchange info

    Button selectAllExchangesButton;
    Button submitExchangeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchanges_page);

        //if first time opening this page, create allExchangesButton - arrayList of all buttons
         //The data has likely been changed before, try to load it
        //if(!MainActivity.isCreatedExchanges){
        setUpExchangeButtons();





        //getExchangeInfo();


        //set up click listeners
        selectAllExchangesButton = findViewById(R.id.select_all_exchanges_button);
        selectAllExchangesButton.setOnClickListener(this);

        submitExchangeButton = findViewById(R.id.submit_exchange_button);
        submitExchangeButton.setOnClickListener(this);

        if(MainActivity.isCreatedExchanges) {
            getExchangeInfo1();
            System.out.println("Papa Johns");
        }

        MainActivity.isCreatedExchanges = true;

    }

    public void onClick(View v){
        switch (v.getId()){
            //Makes the Select All Exchanges button work
            case R.id.select_all_exchanges_button:
                //if the select all button is off, turn every button on
                if(selectAllExchangesButton.getText().equals("Off")) {
                    for (ToggleButton button : allExchangesButton) {
                        button.setChecked(true);
                    }
                    selectAllExchangesButton.setText("On");
                }
                //if the select all button is on, turn every button off
                else {
                    for(ToggleButton button: allExchangesButton){
                        button.setChecked(false);
                    }
                    selectAllExchangesButton.setText("Off");
                }
                break;

            //Submit Button
            case R.id.submit_exchange_button:
                //Gets rid of all Exchanges from listOfExchanges and adds in valid exchanges
                    //based on the buttons that are 'On'
                HomePage.listOfExchanges.clear();
                System.out.println(allExchangesButton.size() + " SIZE");
                for (ToggleButton button: allExchangesButton){
                    if (button.isChecked()){
                        addExchangeToList(button);
                    }
                }
                saveExchangeInfo1(allExchangesButton);

                startActivity(new Intent(this, HomePage.class));
                break;
        }
    }





    /**
     * Adds exchange of the button to the list of selected Exchanges
     * @param button
     */
    public void addExchangeToList (ToggleButton button){
        switch (button.getId()){
            case R.id.exchange1_button:
                HomePage.listOfExchanges.add(HomePage.binance);
                break;
            case R.id.exchange3_button:
                HomePage.listOfExchanges.add(HomePage.bittrex);
                break;

            case R.id.exchange4_button:
                HomePage.listOfExchanges.add(HomePage.bitfinex);
                break;

            case R.id.exchange5_button:
                HomePage.listOfExchanges.add(HomePage.OKEX);
                break;

            case R.id.exchange7_button:
                HomePage.listOfExchanges.add(HomePage.poloniex);
                break;

            case R.id.exchange8_button:
                HomePage.listOfExchanges.add(HomePage.hitBTC);
                break;

            case R.id.exchange9_button:
                HomePage.listOfExchanges.add(HomePage.GDAX);
                break;

            case R.id.exchange11_button:
                HomePage.listOfExchanges.add(HomePage.bitZ);
                break;
        }
    }

    /*//Saves the users Exchange Preferences
    public void saveExchangesInfo(){
        exchangeInfo =  getSharedPreferences("exchangeInfo", Context.MODE_PRIVATE);

        //Gives us object to write to file
        SharedPreferences.Editor editor = exchangeInfo.edit();
        //Adds all buttons to file in form of: 'exchangeButton#', string Value
        for(int i = 0; i < MainActivity.allExchangesButton.size(); i++) {
            editor.putBoolean("exchangeButton" + Integer.toString(i+1),
                    MainActivity.allExchangesButton.get(i).isChecked());
        }
        //submit
        editor.commit();
    }

    public void getExchangeInfo(){
        for(int i = 0; i < MainActivity.allExchangesButton.size(); i++){
            MainActivity.allExchangesButton.get(i).setChecked(exchangeInfo.
                    getBoolean("exchange" + Integer.toString(i+1),false));
        }
    } */

    public void saveExchangeInfo1(ArrayList<ToggleButton> listOfButtons){
        String fileName = "ExchangeInformation";
        String message;
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            for(ToggleButton b1:allExchangesButton){
                System.out.println("Saved File");
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
     * Retrieves all values for each ToggleButton as to
     * whether an Exchange has been selected or not
     */
    public void getExchangeInfo1(){
        int numExchanges = allExchangesButton.size();
        int counter = 0;
        try {
            FileInputStream fileInputStream = openFileInput("ExchangeInformation");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //while more lines
            System.out.println("Size of allExchangesButton is: " + numExchanges);
            StringBuilder message = new StringBuilder();
            int data = 1;
            while(counter < numExchanges && data != -1){
                data = bufferedReader.read();
                message.append((char) data);

                if(message.length()==4 && message.charAt(0) == 't'){
                    allExchangesButton.get(counter++).setChecked(true);
                    // why doesn't the above text automatically change once it's checked
                    message.delete(0,4);
                    System.out.println("Went" + allExchangesButton.get(counter-1).isChecked() + counter);

                }
                else if (message.length() == 5 && message.charAt(0) == 'f'){
                    allExchangesButton.get(counter++).setChecked(false);
                    message.delete(0, 5);
                    System.out.println("went false" + counter);
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

    /**
     * Creates objects for all the toggle
     * adds all buttons except for toggleButton into "exchange_butons" arrayList
     */
    public void setUpExchangeButtons(){
        allExchangesButton = new ArrayList<>();
        ToggleButton exchangeButton1 = findViewById(R.id.exchange1_button);
        allExchangesButton.add(exchangeButton1);
        ToggleButton exchangeButton3 = findViewById(R.id.exchange3_button);
        allExchangesButton.add(exchangeButton3);
        ToggleButton exchangeButton4 = findViewById(R.id.exchange4_button);
        allExchangesButton.add(exchangeButton4);
        ToggleButton exchangeButton5 = findViewById(R.id.exchange5_button);
        allExchangesButton.add(exchangeButton5);
        ToggleButton exchangeButton7 = findViewById(R.id.exchange7_button);
        allExchangesButton.add(exchangeButton7);
        ToggleButton exchangeButton8 = findViewById(R.id.exchange8_button);
        allExchangesButton.add(exchangeButton8);
        ToggleButton exchangeButton9 = findViewById(R.id.exchange9_button);
        allExchangesButton.add(exchangeButton9);
        ToggleButton exchangeButton10 = findViewById(R.id.exchange10_button);
        allExchangesButton.add(exchangeButton10);
        ToggleButton exchangeButton11 = findViewById(R.id.exchange11_button);
        allExchangesButton.add(exchangeButton11);
    }

    private void setButtonOn(ToggleButton button, boolean value){
        button.setChecked(value);
    }
}
