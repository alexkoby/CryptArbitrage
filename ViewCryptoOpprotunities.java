package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Created by Alexander on 1/7/2018.
 */

public class ViewCryptoOpprotunities extends Activity implements View.OnClickListener{
    ArbitrageFinder arbitrageFinder;
    PriorityQueue<Opportunity> bestOpportunitiesWithinExchanges;
    PriorityQueue<Opportunity> bestOpportunitiesAcrossExchanges;

    Opportunity [] topOpportunitiesArray;

    TextView opportunity1Price;
    TextView opportunity2Price;
    TextView opportunity3Price;
    TextView opportunity4Price;
    TextView opportunity5Price;

    TextView opportunity1Cryptocurrency;
    TextView opportunity2Cryptocurrency;
    TextView opportunity3Cryptocurrency;
    TextView opportunity4Cryptocurrency;
    TextView opportunity5Cryptocurrency;

    TextView opportunity1Exchange;
    TextView opportunity2Exchange;
    TextView opportunity3Exchange;
    TextView opportunity4Exchange;
    TextView opportunity5Exchange;

    Button opportunity1Type;
    Button opportunity2Type;
    Button opportunity3Type;
    Button opportunity4Type;
    Button opportunity5Type;

    Button prev5Button;
    Button next5Button;


    int counter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_opprotunities);



        arbitrageFinder = new ArbitrageFinder(HomePage.minGainsWanted);
        if(HomePage.typeOfArbitrageString.equals("Inter-Exchange and Cross Exchange Arbitrage")) {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();

/*            while(bestOpportunitiesWithinExchanges.size() > 0){
                System.out.println(bestOpportunitiesWithinExchanges.peek().getPercentGain() + "% at " +
                        bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getName()+ " Coin is : " +
                        bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getExchange() + " Type" +
                        bestOpportunitiesWithinExchanges.poll().getType());
            }
            while(bestOpportunitiesAcrossExchanges.size() > 0){
                System.out.println(bestOpportunitiesAcrossExchanges.peek().getPercentGain() + "% at " +
                        bestOpportunitiesAcrossExchanges.peek().getLowPriceCoinExchange().getExchange() + " to" +
                        bestOpportunitiesAcrossExchanges.peek().getHighPriceCoinExchange().getExchange() + " Coin is : " +
                        bestOpportunitiesAcrossExchanges.poll().getHighPriceCoinExchange().getName());
            }
*/
        }
        else if (HomePage.typeOfArbitrageString.equals("Inter-Exchange Arbitrage Only")) {
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();

/*            while(bestOpportunitiesWithinExchanges.size() > 0){
                System.out.println(bestOpportunitiesWithinExchanges.peek().getPercentGain() + "% at " +
                        bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getName()+ " Coin is : " +
                        bestOpportunitiesWithinExchanges.peek().getHighPriceCoinExchange().getExchange() + " Type" +
                        bestOpportunitiesWithinExchanges.poll().getType());
            }
*/        }
        else {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();

/*            while(bestOpportunitiesAcrossExchanges.size() > 0){
                System.out.println(bestOpportunitiesAcrossExchanges.peek().getPercentGain() + "% at " +
                        bestOpportunitiesAcrossExchanges.peek().getLowPriceCoinExchange().getExchange() + " to" +
                        bestOpportunitiesAcrossExchanges.peek().getHighPriceCoinExchange().getExchange() + " Coin is : " +
                        bestOpportunitiesAcrossExchanges.poll().getHighPriceCoinExchange().getName());
            }
*/        }

        if(bestOpportunitiesWithinExchanges == null){
            topOpportunitiesArray = new Opportunity[min(bestOpportunitiesAcrossExchanges.size(),30)];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                System.out.println("First Loop");
                topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
            }
        }
        else if (bestOpportunitiesAcrossExchanges == null){
            topOpportunitiesArray = new Opportunity[min(bestOpportunitiesWithinExchanges.size(),30)];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                System.out.println("Second loop");
                topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
            }
        }
        else {
            topOpportunitiesArray = new Opportunity[min(bestOpportunitiesAcrossExchanges.size()
                    + bestOpportunitiesWithinExchanges.size(), 30)];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                System.out.println("Third loop");
                if(bestOpportunitiesAcrossExchanges.size() == 0){
                    System.out.println("Added 1");
                    topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
                }
                else if (bestOpportunitiesWithinExchanges.size() == 0){
                    topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
                    System.out.println("Added 2");
                }
                else if(bestOpportunitiesAcrossExchanges.peek().getPercentGain() >
                        bestOpportunitiesWithinExchanges.peek().getPercentGain()) {
                    topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
                    System.out.println("Added 3");
                }
                else{
                    topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
                    System.out.println("Added 4");
                }
            }
        }

        for(int i = 0; i < topOpportunitiesArray.length; i++){
            System.out.println(topOpportunitiesArray[i].getPercentGain());
        }

        counter = 0;

        connectJavaToXML();
        getDataToScreen();



    }

    private void connectJavaToXML(){
        opportunity1Price = findViewById(R.id.opportunity1Price);
        opportunity2Price = findViewById(R.id.opportunity2Price);
        opportunity3Price = findViewById(R.id.opportunity3Price);
        opportunity4Price = findViewById(R.id.opportunity4Price);
        opportunity5Price = findViewById(R.id.opportunity5Price);

        opportunity1Cryptocurrency = findViewById(R.id.opportunity1Cryptocurrency);
        opportunity2Cryptocurrency = findViewById(R.id.opportunity2Cryptocurrency);
        opportunity3Cryptocurrency = findViewById(R.id.opportunity3Cryptocurrency);
        opportunity4Cryptocurrency = findViewById(R.id.opportunity4Cryptocurrency);
        opportunity5Cryptocurrency = findViewById(R.id.opportunity5Cryptocurrency);

        opportunity1Exchange = findViewById(R.id.opportunity1Exchange);
        opportunity2Exchange = findViewById(R.id.opportunity2Exchange);
        opportunity3Exchange = findViewById(R.id.opportunity3Exchange);
        opportunity4Exchange = findViewById(R.id.opportunity4Exchange);
        opportunity5Exchange = findViewById(R.id.opportunity5Exchange);

        opportunity1Type = findViewById(R.id.opportunity1Type);
        opportunity1Type.setOnClickListener(this);
        opportunity2Type = findViewById(R.id.opportunity2Type);
        opportunity2Type.setOnClickListener(this);
        opportunity3Type = findViewById(R.id.opportunity3Type);
        opportunity3Type.setOnClickListener(this);
        opportunity4Type = findViewById(R.id.opportunity4Type);
        opportunity4Type.setOnClickListener(this);
        opportunity5Type = findViewById(R.id.opportunity5Type);
        opportunity5Type.setOnClickListener(this);

        prev5Button = findViewById(R.id.prev5Button);
        prev5Button.setOnClickListener(this);
        next5Button = findViewById(R.id.next5Button);
        next5Button.setOnClickListener(this);
    }


    private void setEverythingAfterBlank(int level) {
        switch (level) {
            case 1:
                opportunity2Cryptocurrency.setText("");
                opportunity2Exchange.setText("");
                opportunity2Price.setText("");
                opportunity2Type.setText("");

            case 2:
                opportunity3Cryptocurrency.setText("");
                opportunity3Exchange.setText("");
                opportunity3Price.setText("");
                opportunity3Type.setText("");

            case 3:
                opportunity4Cryptocurrency.setText("");
                opportunity4Exchange.setText("");
                opportunity4Price.setText("");
                opportunity4Type.setText("");

            case 4:
                opportunity5Cryptocurrency.setText("");
                opportunity5Exchange.setText("");
                opportunity5Price.setText("");
                opportunity5Type.setText("");
        }
    }


    private void getDataToScreen() {

            opportunity1Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity1Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity1Price.setText(Double.toString(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity1Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange()));
            }
            else {
                opportunity1Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            }
            counter++;


        if (counter < topOpportunitiesArray.length) {
            opportunity2Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity2Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity2Price.setText(Double.toString(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity2Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().
                        getExchange().concat(" Sell ").concat(topOpportunitiesArray[counter].
                        getHighPriceCoinExchange().getExchange()));
            }
            else {
                opportunity2Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            }
            counter++;
        }
        else{
            setEverythingAfterBlank(1);
            counter += 4;
            return;
        }

        if (counter < topOpportunitiesArray.length) {
            opportunity3Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity3Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity3Price.setText(Double.toString(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity3Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange()));
            }
            else {
                opportunity3Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            }
            counter++;
        }
        else{
            setEverythingAfterBlank(2);
            counter += 3;
            return;
        }

        if (counter < topOpportunitiesArray.length) {
            opportunity4Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            opportunity4Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity4Price.setText(Double.toString(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity4Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange()));
            }
            else {
                opportunity4Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            }
            counter++;
        }
        else{
            setEverythingAfterBlank(3);
            counter+=2;
            return;
        }

        if (counter < topOpportunitiesArray.length) {
            opportunity5Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity5Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity5Price.setText(Double.toString(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity5Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange()));
            }
            else {
                opportunity5Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange());
            }
            counter++;
        }
        else{
            setEverythingAfterBlank(4);
            counter++;
        }
    }

    public int min(int i1, int i2){
        if(i1 > i2){
            return i2;
        }
        else{
            return i1;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next5Button:
                if(topOpportunitiesArray.length > counter + 1){
                    getDataToScreen();
                }
                break;
            case R.id.prev5Button:
                if(counter >= 10){
                    counter -= 10;
                    getDataToScreen();
                }
                else{
                    //tell user no previous 5
                }
                break;
        }
    }

    private String doubleToStringThreeDecimals(){
        return null;
    }
}