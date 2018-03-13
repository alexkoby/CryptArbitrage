package my.awesome.project.cryptarbitrage30;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.PriorityQueue;
/**
 * Created by Alexander on 1/7/2018.
 */

public class ViewCryptoOpprotunities extends Activity implements View.OnClickListener{
    ArbitrageFinder arbitrageFinder;
    PriorityQueue<Opportunity> bestOpportunitiesWithinExchanges;
    PriorityQueue<Opportunity> bestOpportunitiesAcrossExchanges;

    Opportunity [] topOpportunitiesArray;

    static boolean hasData = true;
    static boolean selectedRefreshViewOpportunities = false;

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
    Button refreshDataButton;

    TextView timePicker;

    AlertDialog Opportunity1AlertDialog;
    AlertDialog Opportunity2AlertDialog;
    AlertDialog Opportunity3AlertDialog;
    AlertDialog Opportunity4AlertDialog;
    AlertDialog Opportunity5AlertDialog;
    AlertDialog alertDialog;
    int counter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_opprotunities);


        setUpMessages();
        Opportunity1AlertDialog = new AlertDialog.Builder(this).create();
        Opportunity2AlertDialog = new AlertDialog.Builder(this).create();
        Opportunity3AlertDialog = new AlertDialog.Builder(this).create();
        Opportunity4AlertDialog = new AlertDialog.Builder(this).create();
        Opportunity5AlertDialog = new AlertDialog.Builder(this).create();
        alertDialog = new AlertDialog.Builder(this).create();
        arbitrageFinder = new ArbitrageFinder(HomePage.minGainsWanted);


        timePicker = findViewById(R.id.lastTimeRefreshID);
        if(HomePage.lastTimeRefreshedMinute < 10){
            timePicker.setText("Last Refresh: ".concat(Integer.toString(HomePage.lastTimeRefreshedHour).
                    concat(":0").concat(Integer.toString(HomePage.lastTimeRefreshedMinute))));
        }
        else {
            timePicker.setText("Last Refresh: ".concat(Integer.toString(HomePage.lastTimeRefreshedHour).
                    concat(":").concat(Integer.toString(HomePage.lastTimeRefreshedMinute))));
        }

        int x = recalculateNumbers();

        /*if(topOpportunitiesArray != null) {
            for (int i = 0; i < topOpportunitiesArray.length; i++) {
                System.out.println(topOpportunitiesArray[i].getPercentGain());
            }
        }*/

        counter = 0;

        connectJavaToXML();
        if(x == 1){
            getDataToScreen();
        }
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
        refreshDataButton = findViewById(R.id.refreshDataButtonView);
        refreshDataButton.setOnClickListener(this);
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
        opportunity1Price.setText(doubleToStringFiveSigDigs(topOpportunitiesArray[counter].getPercentGain()));
        if (topOpportunitiesArray[counter].getType() > 6) {
            opportunity1Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                    getName().concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName()));
        }
        else {
            opportunity1Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName());
        }
        counter++;


        if (counter < topOpportunitiesArray.length) {
            opportunity2Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity2Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity2Price.setText(doubleToStringFiveSigDigs(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity2Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().
                        getExchange().getName().concat(" Sell ").concat(topOpportunitiesArray[counter].
                        getHighPriceCoinExchange().getExchange().getName()));
            }
            else {
                opportunity2Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName());
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
            opportunity3Price.setText(doubleToStringFiveSigDigs(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity3Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        getName().concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName()));
            }
            else {
                opportunity3Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName());
            }
            counter++;
        }
        else{
            setEverythingAfterBlank(2);
            counter += 3;
            return;
        }

        if (counter < topOpportunitiesArray.length) {
            opportunity4Cryptocurrency.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getName());
            opportunity4Type.setText(Integer.toString(topOpportunitiesArray[counter].getType()));
            opportunity4Price.setText(doubleToStringFiveSigDigs(topOpportunitiesArray[counter].getPercentGain()));
            String s = getDialogInfo(topOpportunitiesArray[counter]);
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity4Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        getName().concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName()));
            }
            else {
                opportunity4Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName());
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
            opportunity5Price.setText(doubleToStringFiveSigDigs(topOpportunitiesArray[counter].getPercentGain()));
            if (topOpportunitiesArray[counter].getType() > 6) {
                opportunity5Exchange.setText("Buy " + topOpportunitiesArray[counter].getLowPriceCoinExchange().getExchange().
                        getName().concat(" Sell ").concat(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName()));
            }
            else {
                opportunity5Exchange.setText(topOpportunitiesArray[counter].getHighPriceCoinExchange().getExchange().getName());
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
                else{
                    Toast.makeText(this,"No More Arbitrage Opportunities",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.prev5Button:
                if(counter >= 10){
                    counter -= 10;
                    getDataToScreen();
                }
                else {
                    Toast.makeText(this, "No More Arbitrage Opportunities", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.refreshDataButtonView:
                //get clock data here
                selectedRefreshViewOpportunities = true;
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
                break;

            case R.id.opportunity1Type:
                if(opportunity1Cryptocurrency.getText().toString().equals("")){
                    break;
                }
                alertDialog.setTitle("Details");
                alertDialog.setMessage(getDialogInfo(topOpportunitiesArray[counter - 5]));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            case R.id.opportunity2Type:
                if(opportunity2Cryptocurrency.getText().toString().equals("")){
                    break;
                }
                alertDialog.setTitle("Details");
                alertDialog.setMessage(getDialogInfo(topOpportunitiesArray[counter - 4]));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

            case R.id.opportunity3Type:
                if(opportunity3Cryptocurrency.getText().toString().equals("")){
                    break;
                }
                alertDialog.setTitle("Details");
                alertDialog.setMessage(getDialogInfo(topOpportunitiesArray[counter - 3]));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

            case R.id.opportunity4Type:
                if(opportunity4Cryptocurrency.getText().toString().equals("")){
                    break;
                }
                alertDialog.setTitle("Details");
                alertDialog.setMessage(getDialogInfo(topOpportunitiesArray[counter - 2]));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

            case R.id.opportunity5Type:
                if(opportunity5Cryptocurrency.getText().toString().equals("")){
                    break;
                }
                alertDialog.setTitle("Details");
                alertDialog.setMessage(getDialogInfo(topOpportunitiesArray[counter - 1]));
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

    private String doubleToStringFiveSigDigs(double d1){
        if(d1 >= 1000){
            return String.format("%.1f", d1);
        }
        else if (d1 >= 100){
            return String.format("%.2f", d1);
        }
        else if(d1 >= 10){
            return String.format("%.3f", d1);
        }
        else if (d1 >= 1){
            return String.format("%.4f", d1);
        }
        else if (d1 >= .1){
            return String.format("%.5f", d1);
        }
        else if(d1 >= .01){
            return String.format("%.6f", d1);
        }
        else if (d1 >= .001){
            return String.format("%.7f", d1);
        }
        else if (d1 >= .0001){
            return String.format("%.8f", d1);
        }
        else if(d1 >= .00001){
            return String.format("%.9f", d1);
        }
        else if (d1 >= .000001){
            return String.format("%.10f", d1);
        }
        else if (d1 >= .00000001){
            return String.format("%.11f", d1);
        }

        return null;
    }

    public int recalculateNumbers(){
        if(HomePage.typeOfArbitrageString.equals("Intra-Exchange and Cross Exchange Arbitrage")) {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();
        }
        else if (HomePage.typeOfArbitrageString.equals("Intra-Exchange Arbitrage Only")) {
            bestOpportunitiesWithinExchanges = arbitrageFinder.getBestOpportunitiesWithinExchange();
            bestOpportunitiesAcrossExchanges = new PriorityQueue<>();
        }
        else {
            bestOpportunitiesAcrossExchanges = arbitrageFinder.getBestOpportunitiesAcrossExchange();
            bestOpportunitiesWithinExchanges = new PriorityQueue<>();
        }

        if(bestOpportunitiesAcrossExchanges.size() == 0 && bestOpportunitiesWithinExchanges.size() == 0){
            ViewCryptoOpprotunities.hasData = false;
            Intent k = new Intent(this, HomePage.class);
            startActivity(k);
            return 0;
        }
        if(bestOpportunitiesWithinExchanges.size() == 0){
            topOpportunitiesArray = new Opportunity[bestOpportunitiesAcrossExchanges.size()];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
            }
        }
        else if (bestOpportunitiesAcrossExchanges.size() == 0){
            topOpportunitiesArray = new Opportunity[bestOpportunitiesWithinExchanges.size()];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
            }
        }
        else {
            topOpportunitiesArray = new Opportunity[bestOpportunitiesAcrossExchanges.size()
                    + bestOpportunitiesWithinExchanges.size()];

            for(int i = 0; i < topOpportunitiesArray.length; i++){
                if(bestOpportunitiesAcrossExchanges.size() == 0){
                    topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
                }
                else if (bestOpportunitiesWithinExchanges.size() == 0){
                    topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
                }
                else if(bestOpportunitiesAcrossExchanges.peek().getPercentGain() >
                        bestOpportunitiesWithinExchanges.peek().getPercentGain()) {
                    topOpportunitiesArray[i] = bestOpportunitiesAcrossExchanges.poll();
                }
                else{
                    topOpportunitiesArray[i] = bestOpportunitiesWithinExchanges.poll();
                }
            }
        }
        /*for(int i = 0; i < topOpportunitiesArray.length; i++){
            if(topOpportunitiesArray[i].getType() < 7 &&
                    topOpportunitiesArray[i].getHighPriceCoinExchange().getExchange().getName().equals("Binance")) {
                arbitrageFinder.quantityToBuy(topOpportunitiesArray[i]);
            }
        }*/
        return 1;
    }

    public void setUpMessages(){

    }

    public String getDialogInfo(Opportunity opportunity){
        StringBuilder stringBuilder = new StringBuilder();
        switch (opportunity.getType()){
            case 1:
                stringBuilder.append("Step1:\nBuy ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD\n\nStep 2:\nCovert ");
                }
                else{
                    stringBuilder.append(" USDT\n\nStep 2:\nCovert ");
                }
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" to Bitcoin at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceBTC()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" Bitcoin\n\nStep 3:\nSell your Bitcoin for USD at: $");
                }
                else{
                    stringBuilder.append(" Bitcoin\n\nStep 3:\nSell your Bitcoin for USDT at: ");
                }
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getBidPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD");
                }
                else{
                    stringBuilder.append(" USDT");
                }
                stringBuilder.append("\n\nNote you may start at any point in this cycle");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 2:
                stringBuilder.append("Step1:\nBuy Bitcoin at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD \n\nStep 2:\nCovert Bitcoin to ");
                }
                else{
                    stringBuilder.append(" USDT \n\nStep 2:\nCovert Bitcoin to ");
                }
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append( "at ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceBTC()));
                stringBuilder.append(" Bitcoin\n\nStep 3:\nSell your");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" for USD at: $");
                }
                else{
                    stringBuilder.append(" for USDT at: ");
                }
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD");
                }
                else{
                    stringBuilder.append(" USDT");
                }
                stringBuilder.append("\nNote: you may start at any point in this cycle");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 3:
                stringBuilder.append("Step1:\nBuy ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD\n\nStep 2:\nCovert ");
                }
                else{
                    stringBuilder.append(" USDT\n\nStep 2:\nCovert ");
                }
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" to Ethereum at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceETH()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" Ethereum\n\nStep 3:\nSell your Ethereum for USD at: $");
                }
                else{
                    stringBuilder.append(" Ethereum\n\nStep 3:\nSell your Ethereum for USDT at: ");
                }
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getBidPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD\nNote: you may start at any point in this cycle");
                }
                else{
                    stringBuilder.append(" USDT\nNote: you may start at any point in this cycle");
                }
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 4:
                stringBuilder.append("Step1:\nBuy Ethereum at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD\n\nStep 2:\nCovert Ethereum to ");
                }
                else{
                    stringBuilder.append(" USDT\n\nStep 2:\nCovert Ethereum to ");
                }
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append("at ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceETH()));
                stringBuilder.append(" Ethereum\n\nStep 3:\nSell your");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" For USD at: $");
                }
                else{
                    stringBuilder.append(" For USDT at: ");
                }
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceUSD()));
                if(opportunity.getHighPriceCoinExchange().getExchange().getIsUSD()){
                    stringBuilder.append(" USD\nNote: you may start at any point in this cycle");
                }
                else{
                    stringBuilder.append(" USDT\nNote: you may start at any point in this cycle");
                }
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 5:
                stringBuilder.append("Step1:\nConvert Your Ethereum to: ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append( "at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceETH()));
                stringBuilder.append("Ethereum\n\nStep 2:\nConvert ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" to Bitcoin at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceBTC()));
                stringBuilder.append(" Bitcoin\n\nStep 3:\nConvert Your Bitcoin to Ethereum at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceBTC()));
                stringBuilder.append(" Bitcoin\nNote: you may start at any point in this cycle");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 6:
                stringBuilder.append("Step1:\nConvert Your Bitcoin to: ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append( "at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getAskPriceBTC()));
                stringBuilder.append(" Bitcoin\n\nStep 2:\nConvert ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" to Ethereum at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceETH()));
                stringBuilder.append(" Ethereum\n\nStep 3:\nConvert Your Ethereum to Bitcoin at: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getBidPriceBTC()));
                stringBuilder.append(" Bitcoin\nNote: you may start at any point in this cycle");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()));
                stringBuilder.append("%\n\nAlways check the quantity of the asks/bids at each step" +
                        " in the cycle before you begin to ensure it is adequate");
                return stringBuilder.toString();

            case 7:
                stringBuilder.append("Step 1\nBuy ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: $");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceUSD()));
                stringBuilder.append(" USD");
                stringBuilder.append("\n\nStep 2:\n");
                stringBuilder.append("Sell ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: $");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceUSD()));
                stringBuilder.append(" USD");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()) + "%");
                stringBuilder.append("\n\nVolume on " + opportunity.getLowPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getLowPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else {
                    stringBuilder.append("$" + round(opportunity.getLowPriceCoinExchange().getVolumeUSD()));
                }
                stringBuilder.append("\nVolume on " + opportunity.getHighPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getHighPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else{
                    stringBuilder.append("$" + round(opportunity.getHighPriceCoinExchange().getVolumeUSD()));
                }
                stringBuilder.append("\n\nAlways check to ensure the wallets on both exchanges work and allow deposits/withdraws");
                return stringBuilder.toString();

            case 8:
                stringBuilder.append("Step 1\nBuy ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceBTC()));
                stringBuilder.append(" Bitcoin\n\nStep 2:\n");
                stringBuilder.append("Sell ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceBTC()));
                stringBuilder.append(" Bitcoin");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()) + "%");
                stringBuilder.append("\n\nVolume on " + opportunity.getLowPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getLowPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else{
                    stringBuilder.append("$" +round(opportunity.getLowPriceCoinExchange().getVolumeBTC()));
                }
                stringBuilder.append("\nVolume on " + opportunity.getHighPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getHighPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else {
                    stringBuilder.append("$" +round(opportunity.getHighPriceCoinExchange().getVolumeBTC()));
                }
                stringBuilder.append("\n\nAlways check to ensure the wallets on both exchanges work and allow deposits/withdraws");
                return stringBuilder.toString();

            case 9:
                stringBuilder.append("Step 1\nBuy ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceETH()));
                stringBuilder.append(" Ethereum\n\nStep 2:\n");
                stringBuilder.append("Sell ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceETH()));
                stringBuilder.append(" Ethereum");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()) + "%");
                stringBuilder.append("\n\nVolume on " + opportunity.getLowPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getLowPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else {
                    stringBuilder.append("$" +round(opportunity.getLowPriceCoinExchange().getVolumeETH()));
                }
                stringBuilder.append("\nVolume on " + opportunity.getHighPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getHighPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else{
                    stringBuilder.append("$" + round(opportunity.getHighPriceCoinExchange().getVolumeETH()));
                }
                stringBuilder.append("\n\nAlways check to ensure the wallets on both exchanges work and allow deposits/withdraws");
                return stringBuilder.toString();

            case 10:
                stringBuilder.append("Step 1\nBuy ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getLowPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getLowPriceCoinExchange().getAskPriceUSD()));
                stringBuilder.append(" USDT");
                stringBuilder.append("\n\nStep 2:\n");
                stringBuilder.append("Sell ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getName());
                stringBuilder.append(" on ");
                stringBuilder.append(opportunity.getHighPriceCoinExchange().getExchange().getName());
                stringBuilder.append(" for: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getHighPriceCoinExchange().getBidPriceUSD()));
                stringBuilder.append(" USDT");
                stringBuilder.append("\n\nPercent Profit: ");
                stringBuilder.append(doubleToStringFiveSigDigs(opportunity.getPercentGain()) + "%");
                stringBuilder.append("\n\nVolume on " + opportunity.getLowPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getLowPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else {
                    stringBuilder.append("$" + round(opportunity.getLowPriceCoinExchange().getVolumeUSD()));
                }
                stringBuilder.append("\nVolume on " + opportunity.getHighPriceCoinExchange().getExchange().getName() + ": ");
                if(opportunity.getHighPriceCoinExchange().getExchange().getName().equals("Bitfinex")){
                    stringBuilder.append("unknown");
                }
                else{
                    stringBuilder.append("$" + round(opportunity.getHighPriceCoinExchange().getVolumeUSD()));
                }
                stringBuilder.append("\n\nAlways check to ensure the wallets on both exchanges work and allow deposits/withdraws");
                return stringBuilder.toString();
        }
        return null;
    }


    private int round(Double d1){
        if(d1 != null){
            double d2 = d1;
            return (int)d2;

        }
        else{
            return -1;
        }
    }
}