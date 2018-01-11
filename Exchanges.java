package com.example.alexander.cryptarbitrage2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ToggleButton;
import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */

public class Exchanges extends Activity implements View.OnClickListener{

    static ArrayList<ToggleButton> allExchangesButton;
    Button selectAllExchangesButton;
    Button submitExchangeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchanges_page);

        //Create and add all other Buttons to ArrayList
        allExchangesButton = new ArrayList<>(); //not sure whether to use ArrayList or Array, might add more later
        setUpButtons();

        //set up click listeners
        selectAllExchangesButton = findViewById(R.id.select_all_exchanges_button);
        selectAllExchangesButton.setOnClickListener(this);

        submitExchangeButton = findViewById(R.id.submit_exchange_button);
        selectAllExchangesButton.setOnClickListener(this);







    }

    public void onClick(View v){
        switch (v.getId()){
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
            case R.id.submit_exchange_button:
                Intent i = new Intent(this,HomePage.class );
                startActivity(i);
        }
    }

    /**
     * Creates objects for all the toggle
     * adds all buttons except for toggleButton into "exchange_butons" arrayList
     */
    public void setUpButtons(){
        ToggleButton exchangeButton1 = findViewById(R.id.exchange1_button);
        allExchangesButton.add(exchangeButton1);
        ToggleButton exchangeButton2 = findViewById(R.id.exchange2_button);
        allExchangesButton.add(exchangeButton2);
        ToggleButton exchangeButton3 = findViewById(R.id.exchange3_button);
        allExchangesButton.add(exchangeButton3);
        ToggleButton exchangeButton4 = findViewById(R.id.exchange4_button);
        allExchangesButton.add(exchangeButton4);
        ToggleButton exchangeButton5 = findViewById(R.id.exchange5_button);
        allExchangesButton.add(exchangeButton5);
        ToggleButton exchangeButton6 = findViewById(R.id.exchange6_button);
        allExchangesButton.add(exchangeButton6);
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
        ToggleButton exchangeButton12 = findViewById(R.id.exchange12_button);
        allExchangesButton.add(exchangeButton12);
        ToggleButton exchangeButton13 = findViewById(R.id.exchange13_button);
        allExchangesButton.add(exchangeButton13);
        ToggleButton exchangeButton14 = findViewById(R.id.exchange14_button);
        allExchangesButton.add(exchangeButton14);
        ToggleButton exchangeButton15 = findViewById(R.id.exchange15_button);
        allExchangesButton.add(exchangeButton15);
        ToggleButton exchangeButton16 = findViewById(R.id.exchange16_button);
        allExchangesButton.add(exchangeButton16);
        ToggleButton exchangeButton17 = findViewById(R.id.exchange17_button);
        allExchangesButton.add(exchangeButton17);
        ToggleButton exchangeButton18 = findViewById(R.id.exchange18_button);
        allExchangesButton.add(exchangeButton18);
    }


}
