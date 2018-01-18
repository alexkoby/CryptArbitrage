package com.example.alexander.cryptarbitrage2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    static boolean isCreatedHomepage = false;
    static boolean isCreatedExchanges = false;
    static boolean isCreatedCryptocurrencies = false;

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

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Penis");
        System.out.println("Penis");

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
