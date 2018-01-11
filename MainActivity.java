package com.example.alexander.cryptarbitrage2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Exchanges
    static Exchange bitfinex;
    static Exchange binance;
    static Exchange bittrex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up click listeners for all the buttons
        View forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        forgotPasswordButton.setOnClickListener(this);

        View logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(this);

        View signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        //create exchanges and all coins associated with each exchange
        createExchanges();
            }


    /**
     * Creates all the Exchanges and associated coins
     * Always make sure bitcoin is first and ethereuum second
     */
    public void createExchanges(){
        bitfinex = new Exchange("Bitfinex");
        //Choose top 8 coins
        Coin bitcoinBitfinex = new Coin("Bitcoin","BTC", "Bitfinex");
        bitfinex.addCoin(bitcoinBitfinex);
        Coin ethereumBitfinex = new Coin("Ethereum","ETH", "Bitfinex");
        bitfinex.addCoin(ethereumBitfinex);
        Coin rippleBitfinex = new Coin("Ripple","XRP", "Bitfinex");
        bitfinex.addCoin(rippleBitfinex);
        Coin litecoinBitfinex = new Coin("Litecoin","LTC", "Bitfinex");
        bitfinex.addCoin(litecoinBitfinex);
        Coin iotaBitfinex = new Coin("Iota","IOT", "Bitfinex");
        bitfinex.addCoin(iotaBitfinex);
        Coin eosBitfinex = new Coin("EOS", "EOS","Bitfinex");
        bitfinex.addCoin(eosBitfinex);
        Coin ethereumClassicBitfinex = new Coin("Ethereum Classic", "ETC","Bitfinex");
        bitfinex.addCoin(ethereumClassicBitfinex);
        Coin neoBitfinex = new Coin("NEO", "NEO","Bitfinex");
        bitfinex.addCoin(neoBitfinex);
        Coin omiseGoBitfinex = new Coin("OmiseGo", "OMG","Bitfinex");
        bitfinex.addCoin(omiseGoBitfinex);
        Coin zCashBitfinex = new Coin("ZCash", "ZEC","Bitfinex");
        bitfinex.addCoin(zCashBitfinex);
        Coin moneroBitfinex = new Coin("Monero", "XMR","Bitfinex");
        bitfinex.addCoin(moneroBitfinex);
        Coin statusBitfinex = new Coin("Status","SNT", "Bitfinex");
        bitfinex.addCoin(statusBitfinex);
        Coin dashBitfinex = new Coin("Dash", "DSH","Bitfinex");
        bitfinex.addCoin(dashBitfinex);
        Coin qtumBitfinex = new Coin("Qtum", "QTM","Bitfinex");
        bitfinex.addCoin(qtumBitfinex);
        Coin etpBitfinex = new Coin("ETP", "ETP","Bitfinex");
        bitfinex.addCoin(etpBitfinex);
        bitfinex.getCoins().trimToSize();

        bittrex = new Exchange("Bittrex");
        Coin bitcoinBittrex = new Coin("Bitcoin", "BTC","Bittrex");
        bittrex.addCoin(bitcoinBittrex);
        Coin ethereumBittrex = new Coin("Ethereum", "ETH","Bittrex");
        bittrex.addCoin(ethereumBittrex);
        Coin rippleBittrex = new Coin("Ripple", "XRP","Bittrex");
        bittrex.addCoin(rippleBittrex);
        Coin bitcoinCashBittrex = new Coin("Bitcoin Cash", "BCC","Bittrex");
        bittrex.addCoin(bitcoinCashBittrex);
        //still need Cardano abbreviation
        Coin cardanoBittrex = new Coin("Cardano", "","Bittrex");
        //bittrex.addCoin(cardanoBittrex);
        //Still need nem abbreviation
        Coin nemBittrex = new Coin("NEM", "","Bittrex");
       // bittrex.addCoin(nemBittrex);
        Coin litecoinBittrex = new Coin("Litecoin", "LTC","Bittrex");
        bittrex.addCoin(litecoinBittrex);
        //Still need stellar
        Coin stellarBittrex = new Coin("Stellar", "","Bittrex");
        //bittrex.addCoin(stellarBittrex);
        Coin dashBittrex = new Coin("Dash", "DASH","Bittrex");
        bittrex.addCoin(dashBittrex);
        Coin neoBittrex = new Coin("NEO", "NEO","Bittrex");
        bittrex.addCoin(neoBittrex);
        Coin moneroBittrex = new Coin("Monero", "XMR","Bittrex");
        bittrex.addCoin(moneroBittrex);
        Coin qtumBittrex = new Coin("Qtum", "QTUM","Bittrex");
        bittrex.addCoin(qtumBittrex);
        Coin liskBittrex = new Coin("Lisk", "LSK","Bittrex");
        bittrex.addCoin(liskBittrex);
        Coin ethereumClassicBittrex = new Coin("Ethereum Classic", "ETC","Bittrex");
        bittrex.addCoin(ethereumClassicBittrex);
        Coin vergeBittrex = new Coin("Verge", "XVG","Bittrex");
        bittrex.addCoin(vergeBittrex);
        Coin siacoinBittrex = new Coin("Siacoin", "SC","Bittrex");
        bittrex.addCoin(siacoinBittrex);
        Coin stratisBittrex = new Coin("Stratis", "STRAT","Bittrex");
        bittrex.addCoin(stratisBittrex);
        Coin zcashBittrex = new Coin("Z-Cash", "ZEC","Bittrex");
        bittrex.addCoin(zcashBittrex);
        Coin dogecoinBittrex = new Coin("Dogecoin", "DOGE","Bittrex");
        bittrex.addCoin(dogecoinBittrex);
        Coin steemBittrex = new Coin("Steem", "STEEM","Bittrex");
        bittrex.addCoin(steemBittrex);
        Coin wavesBittrex = new Coin("Waves", "WAVES","Bittrex");
        bittrex.addCoin(wavesBittrex);
        Coin digibyteBittrex = new Coin("Digibyte", "DGB","Bittrex");
        bittrex.addCoin(digibyteBittrex);
        Coin komodoBittrex = new Coin("Komodo", "KMD","Bittrex");
        bittrex.addCoin(komodoBittrex);
        Coin arkBittrex = new Coin("Ark", "ARK","Bittrex");
        bittrex.addCoin(arkBittrex);
        Coin decredBittrex = new Coin("Decred", "DCR","Bittrex");
        bittrex.addCoin(decredBittrex);
        Coin factomBittrex = new Coin("Factom", "FCT","Bittrex");
        bittrex.addCoin(factomBittrex);
        Coin digitalNoteBittrex = new Coin("Digital Note", "XDN","Bittrex");
        bittrex.addCoin(digitalNoteBittrex);
        Coin nxtBittrex = new Coin("NXT", "NXT","Bittrex");
        bittrex.addCoin(nxtBittrex);
        Coin syscoinBittrex = new Coin("Syscoin", "SYS","Bittrex");
        bittrex.addCoin(syscoinBittrex);
        Coin zcoinBittrex = new Coin("Zcoin", "XZC","Bittrex");
        bittrex.addCoin(zcoinBittrex);
        Coin gameCreditsBittrex = new Coin("Game Credits", "GAME","Bittrex");
        bittrex.addCoin(gameCreditsBittrex);
        Coin vertcoinBittrex = new Coin("Vertcoin", "VTC","Bittrex");
        bittrex.addCoin(vertcoinBittrex);





    }

    /**
     *
     * @param v is the button being clicked
     */
    public void onClick(View v){
        switch (v.getId()){
            case R.id.forgotPasswordButton:
                //fill in what happens when forgotPassword Button is clicked
                break;
            case R.id.logInButton:
                //fill in what happens when logIn Button is clicked
                Intent i = new Intent(this, HomePage.class);
                startActivity(i);
                break;
            case R.id.signUpButton:
                //fill in what happens when signUpButton is clicked
                break;

        }
    }
}
