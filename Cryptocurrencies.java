package my.awesome.project.cryptarbitrage30;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

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

public class Cryptocurrencies extends Activity implements View.OnClickListener, BillingProcessor.IBillingHandler{

    ArrayList<ToggleButton> allCurrenciesButtons;

    static boolean hasSubscription = false;
    BillingProcessor bp;

    Button selectAllCurrenciesButton;
    boolean hasAddedBitcoinAndEthereum = false;

    static int numberClicked = 0;
    static final int MAX_NUMBER_ALLOWED = 5;

    final String publicAPI = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArNSgdFawfG05qVr5dmy5VGnrR/D7A636WN7l28Gpy8X9hIM8FlKpRDWfDNjV5x2q/7Nlzpla462DLlYRFIxCHm/LoQMd6vm37k10FqhskFxkvcMshKE7fEfVVrOnnod3JE8UDhwMd0a3UYGqAWNWx8m02K2Y6vzKfIYpu0NLpaPMD1GgVw6ZtoiNCIR+ilL/Kvv8WZutM3yUBhrTr47dOjvu/bwYQ01RT1QbGMjujIE3KWphzmfUCWRhZIGsTypgskVFoH2px5gSB2ynQVvjFAN2Jx18Hj+AhinVl1pSBdOl0eMJG0TXyPtbseRAhPdv1H+YcTPOed5g2j7qxKwxswIDAQAB";

    ArrayAdapter<String>listAdapter;

    boolean [] startingValuesForButtons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencies_page);


        //set up click listeners
        selectAllCurrenciesButton = findViewById(R.id.select_all_cryptocurrencies_button);
        selectAllCurrenciesButton.setOnClickListener(this);

        if(!hasSubscription){
            bp = new BillingProcessor(this, publicAPI, this);
        }

        //Create and add all other Buttons to ArrayList
        setUpButtons();


        //Used to ensure all coin data is up to date, cycle from homepage to exchanges page to cryptocurrencies page back to home page

    }
    @Override
    protected void onStart(){
        getSavedCoins();

        if(!HomePage.isCreatedCryptocurrencies){
                startActivity(new Intent(this, HomePage.class));
        }

        super.onStart();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //Makes Select All button work
            case R.id.select_all_cryptocurrencies_button:
                if(!Cryptocurrencies.hasSubscription){
                    Toast.makeText(this, "Buy A Subscription to Select More Than " + MAX_NUMBER_ALLOWED +
                            " Cryptocurrencies", Toast.LENGTH_LONG).show();
                    bp.subscribe(this, "monthly_sub");
                    break;
                }
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

            default:
                selectAllCurrenciesButton.setText("On");
                for(ToggleButton toggleButton: allCurrenciesButtons){
                    if(!toggleButton.isChecked()){
                        selectAllCurrenciesButton.setText("Off");
                        break;
                    }
                }
                if(!hasSubscription) {
                    int numberOfButtonsOn = 0;
                    for (ToggleButton toggleButton : allCurrenciesButtons) {
                        if (toggleButton.isChecked()) {
                            numberOfButtonsOn++;
                        }
                        if (numberOfButtonsOn > 5) {
                            if(toggleButton.isChecked()){
                                toggleButton.setChecked(false);
                                toggleButton.setText("OFF");
                            }
                            Toast.makeText(this, "Buy A Subscription to Select More Than 5 Cryptocurrencies" + hasSubscription, Toast.LENGTH_LONG).show();
                            bp.subscribe(this, "monthly_sub");
                            break;
                        }
                    }
                }
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
        ToggleButton currencyDogecoin = findViewById(R.id.dogecoinButton);
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
        ToggleButton populousButton = findViewById(R.id.populousButton);
        allCurrenciesButtons.add(populousButton);
        ToggleButton bytecoinButton = findViewById(R.id.bytecoinButton);
        allCurrenciesButtons.add(bytecoinButton);
        ToggleButton statusButton = findViewById(R.id.statusButton);
        allCurrenciesButtons.add(statusButton);
        ToggleButton bitsharesButton = findViewById(R.id.bitsharesButton);
        allCurrenciesButtons.add(bitsharesButton);
        ToggleButton augurButton = findViewById(R.id.augurButton);
        allCurrenciesButtons.add(augurButton);
        ToggleButton veritaseumButton = findViewById(R.id.veritaseumButton);
        allCurrenciesButtons.add(veritaseumButton);
        ToggleButton waltonButton = findViewById(R.id.waltonButton);
        allCurrenciesButtons.add(waltonButton);
        ToggleButton zeroxButton = findViewById(R.id.zeroxButton);
        allCurrenciesButtons.add(zeroxButton);
        ToggleButton ardorButton = findViewById(R.id.ardorButton);
        allCurrenciesButtons.add(ardorButton);
        ToggleButton revainButton = findViewById(R.id.revainButton);
        allCurrenciesButtons.add(revainButton);
        ToggleButton digixdaoButton = findViewById(R.id.digixdaoButton);
        allCurrenciesButtons.add(digixdaoButton);
        ToggleButton gasButton = findViewById(R.id.gasButton);
        allCurrenciesButtons.add(gasButton);
        ToggleButton kyberButton = findViewById(R.id.kyberCurrency);
        allCurrenciesButtons.add(kyberButton);
        ToggleButton batButton = findViewById(R.id.batButton);
        allCurrenciesButtons.add(batButton);
        ToggleButton loopringButton = findViewById(R.id.loopringButton);
        allCurrenciesButtons.add(loopringButton);
        ToggleButton pivxButton = findViewById(R.id.pivxButton);
        allCurrenciesButtons.add(pivxButton);
        ToggleButton ethosButton = findViewById(R.id.ethosButton);
        allCurrenciesButtons.add(ethosButton);
        ToggleButton golemButton = findViewById(R.id.golemButton);
        allCurrenciesButtons.add(golemButton);
        ToggleButton aelfButton = findViewById(R.id.aelfButton);
        allCurrenciesButtons.add(aelfButton);
        ToggleButton nebulasButton = findViewById(R.id.nebulasButton);
        allCurrenciesButtons.add(nebulasButton);
        ToggleButton pillarButton = findViewById(R.id.pillarButton);
        allCurrenciesButtons.add(pillarButton);
        ToggleButton powerledgerButton = findViewById(R.id.powerledgerButton);
        allCurrenciesButtons.add(powerledgerButton);
        ToggleButton cindicatorButton = findViewById(R.id.cindicatorButton);
        allCurrenciesButtons.add(cindicatorButton);
        ToggleButton iosTokenButton = findViewById(R.id.iosTokenButton);
        allCurrenciesButtons.add(iosTokenButton);
        ToggleButton funfairButton = findViewById(R.id.funfairButton);
        allCurrenciesButtons.add(funfairButton);
        ToggleButton enigmaButton = findViewById(R.id.enigmaButton);
        allCurrenciesButtons.add(enigmaButton);
        ToggleButton saltButton = findViewById(R.id.saltButton);
        allCurrenciesButtons.add(saltButton);
        ToggleButton civicButton = findViewById(R.id.civicButton);
        allCurrenciesButtons.add(civicButton);
        ToggleButton waxButton = findViewById(R.id.waxButton);
        allCurrenciesButtons.add(waxButton);
        ToggleButton storjButton = findViewById(R.id.storjButton);
        allCurrenciesButtons.add(storjButton);
        ToggleButton decentralandButton = findViewById(R.id.decentralandButton);
        allCurrenciesButtons.add(decentralandButton);
        ToggleButton navButton = findViewById(R.id.navButton);
        allCurrenciesButtons.add(navButton);
        ToggleButton timenewbankButton = findViewById(R.id.timenewbankButton);
        allCurrenciesButtons.add(timenewbankButton);
        ToggleButton achainButton = findViewById(R.id.achainButton);
        allCurrenciesButtons.add(achainButton);
        ToggleButton gnosisButton = findViewById(R.id.gnosisButton);
        allCurrenciesButtons.add(gnosisButton);
        ToggleButton iconomiButton = findViewById(R.id.iconomiButton);
        allCurrenciesButtons.add(iconomiButton);
        ToggleButton bancorButton = findViewById(R.id.bancorButton);
        allCurrenciesButtons.add(bancorButton);
        ToggleButton madesafecoinButton = findViewById(R.id.madesafecoinButton);
        allCurrenciesButtons.add(madesafecoinButton);
        ToggleButton chainlinkButton = findViewById(R.id.chainlinkButton);
        allCurrenciesButtons.add(chainlinkButton);
        ToggleButton emercoinButton = findViewById(R.id.emercoinButton);
        allCurrenciesButtons.add(emercoinButton);
        ToggleButton ethlendButton = findViewById(R.id.ethlendButton);
        allCurrenciesButtons.add(ethlendButton);
        ToggleButton iexecrlcButton = findViewById(R.id.iexecrlcButton);
        allCurrenciesButtons.add(iexecrlcButton);
        ToggleButton monacoButton = findViewById(R.id.monacoButton);
        allCurrenciesButtons.add(monacoButton);
        ToggleButton blockvButton = findViewById(R.id.blockvButton);
        allCurrenciesButtons.add(blockvButton);
        ToggleButton bluzelleButton = findViewById(R.id.bluzelleButton);
        allCurrenciesButtons.add(bluzelleButton);
        ToggleButton ripiocreditnetworkButton = findViewById(R.id.ripiocreditnetworkButton);
        allCurrenciesButtons.add(ripiocreditnetworkButton);
        ToggleButton iotchainButton = findViewById(R.id.iotchainButton);
        allCurrenciesButtons.add(iotchainButton);
        ToggleButton airswapButton = findViewById(R.id.airswapButton);
        allCurrenciesButtons.add(airswapButton);
        ToggleButton counterpartyButton = findViewById(R.id.counterpartyButton);
        allCurrenciesButtons.add(counterpartyButton);
        ToggleButton einsteiniumButton = findViewById(R.id.einsteiniumButton);
        allCurrenciesButtons.add(einsteiniumButton);
        ToggleButton peercoinButton = findViewById(R.id.peercoinButton);
        allCurrenciesButtons.add(peercoinButton);
        ToggleButton vibeButton = findViewById(R.id.vibeButton);
        allCurrenciesButtons.add(vibeButton);
        ToggleButton cybermilesButton = findViewById(R.id.cybermilesButton);
        allCurrenciesButtons.add(cybermilesButton);
        ToggleButton adexButton = findViewById(R.id.adexButton);
        allCurrenciesButtons.add(adexButton);
        ToggleButton singulardtvButton = findViewById(R.id.singulardtvButton);
        allCurrenciesButtons.add(singulardtvButton);
        ToggleButton metalButton = findViewById(R.id.metalButton);
        allCurrenciesButtons.add(metalButton);
        ToggleButton simpletokenButton = findViewById(R.id.simpletokenButton);
        allCurrenciesButtons.add(simpletokenButton);
        ToggleButton eidooButton = findViewById(R.id.eidooButton);
        allCurrenciesButtons.add(eidooButton);
        ToggleButton thetatokenButton = findViewById(R.id.thetatokenButton);
        allCurrenciesButtons.add(thetatokenButton);
        ToggleButton metaverseetpButton = findViewById(R.id.metaverseetpButton);
        allCurrenciesButtons.add(metaverseetpButton);
        ToggleButton aeternityButton = findViewById(R.id.aeternityButton);
        allCurrenciesButtons.add(aeternityButton);
        ToggleButton zilliqaButton = findViewById(R.id.zilliqaButton);
        allCurrenciesButtons.add(zilliqaButton);
        ToggleButton bytomButton = findViewById(R.id.bytomButton);
        allCurrenciesButtons.add(bytomButton);
        ToggleButton dentacoinButton = findViewById(R.id.dentacoinButton);
        allCurrenciesButtons.add(dentacoinButton);
        ToggleButton qashButton = findViewById(R.id.qashButton);
        allCurrenciesButtons.add(qashButton);
        ToggleButton bitcoreButton = findViewById(R.id.bitcoreButton);
        allCurrenciesButtons.add(bitcoreButton);
        ToggleButton aionButton = findViewById(R.id.aionButton);
        allCurrenciesButtons.add(aionButton);
        ToggleButton requestnetworkButton = findViewById(R.id.requestnetworkButton);
        allCurrenciesButtons.add(requestnetworkButton);
        ToggleButton tenxButton = findViewById(R.id.tenxButton);
        allCurrenciesButtons.add(tenxButton);
        ToggleButton quantstampButton = findViewById(R.id.quantstampButton);
        allCurrenciesButtons.add(quantstampButton);
        ToggleButton appcoinButton = findViewById(R.id.appcoinsButton);
        allCurrenciesButtons.add(appcoinButton);
        ToggleButton santimentButton = findViewById(R.id.santimentButton);
        allCurrenciesButtons.add(santimentButton);
        ToggleButton poetButton = findViewById(R.id.po_etButton);
        allCurrenciesButtons.add(poetButton);
        ToggleButton substratumButton = findViewById(R.id.substratumButton);
        allCurrenciesButtons.add(substratumButton);
        ToggleButton raidenButton = findViewById(R.id.raidenButton);
        allCurrenciesButtons.add(raidenButton);
        ToggleButton zencashButton = findViewById(R.id.zencashButton);
        allCurrenciesButtons.add(zencashButton);
        ToggleButton redpulseButton = findViewById(R.id.redpulseButton);
        allCurrenciesButtons.add(redpulseButton);
        ToggleButton enjincoinButton = findViewById(R.id.enjincoinButton);
        allCurrenciesButtons.add(enjincoinButton);
        ToggleButton aragonButton = findViewById(R.id.aragonButton);
        allCurrenciesButtons.add(aragonButton);
        ToggleButton deepbrainchainButton = findViewById(R.id.deepbrainchainButton);
        allCurrenciesButtons.add(deepbrainchainButton);
        ToggleButton sirinlabsButton = findViewById(R.id.sirinlabsButton);
        allCurrenciesButtons.add(sirinlabsButton);
        ToggleButton medisharesButton = findViewById(R.id.medisharesButton);
        allCurrenciesButtons.add(medisharesButton);
        ToggleButton giftoButton = findViewById(R.id.giftoButton);
        allCurrenciesButtons.add(giftoButton);
        ToggleButton wabiButton = findViewById(R.id.wabiButton);
        allCurrenciesButtons.add(wabiButton);
        ToggleButton crypto20Button = findViewById(R.id.crypto20Button);
        allCurrenciesButtons.add(crypto20Button);
        ToggleButton ambrosusButton = findViewById(R.id.ambrosusButton);
        allCurrenciesButtons.add(ambrosusButton);
        ToggleButton insecosystemButton = findViewById(R.id.insecosystemButton);
        allCurrenciesButtons.add(insecosystemButton);
        ToggleButton streamrdatacoinButton = findViewById(R.id.streamrdataButton);
        allCurrenciesButtons.add(streamrdatacoinButton);
        ToggleButton sonmButton = findViewById(R.id.sonmButton);
        allCurrenciesButtons.add(sonmButton);
        ToggleButton viacoinButton = findViewById(R.id.viacoinButton);
        allCurrenciesButtons.add(viacoinButton);
        ToggleButton genesisvisionButton = findViewById(R.id.genesisvisionButton);
        allCurrenciesButtons.add(genesisvisionButton);
        ToggleButton melonButton = findViewById(R.id.melonButton);
        allCurrenciesButtons.add(melonButton);
        ToggleButton spankchainButton = findViewById(R.id.spankchainButton);
        allCurrenciesButtons.add(spankchainButton);
        ToggleButton breadButton = findViewById(R.id.breadButton);
        allCurrenciesButtons.add(breadButton);
        ToggleButton nulsButton = findViewById(R.id.nulsButton);
        allCurrenciesButtons.add(nulsButton);
        ToggleButton utrustButton = findViewById(R.id.utrustButton);
        allCurrenciesButtons.add(utrustButton);
        ToggleButton triggersButton = findViewById(R.id.triggersButton);
        allCurrenciesButtons.add(triggersButton);
        ToggleButton etherpartyButton = findViewById(R.id.etherpartyButton);
        allCurrenciesButtons.add(etherpartyButton);
        ToggleButton modumButton = findViewById(R.id.modumButton);
        allCurrenciesButtons.add(modumButton);
        ToggleButton wingsButton = findViewById(R.id.wingsButton);
        allCurrenciesButtons.add(wingsButton);
        ToggleButton tierionButton = findViewById(R.id.tierionButton);
        allCurrenciesButtons.add(tierionButton);
        ToggleButton wepowerButton = findViewById(R.id.wepowerButton);
        allCurrenciesButtons.add(wepowerButton);
        ToggleButton burstButton = findViewById(R.id.burstButton);
        allCurrenciesButtons.add(burstButton);
        ToggleButton allsportsButton = findViewById(R.id.allsportsButton);
        allCurrenciesButtons.add(allsportsButton);
        ToggleButton district0xButton = findViewById(R.id.district0xButton);
        allCurrenciesButtons.add(district0xButton);
        ToggleButton internetnodetokenButton = findViewById(R.id.internetnodetokenButton);
        allCurrenciesButtons.add(internetnodetokenButton);
        ToggleButton coindashButton = findViewById(R.id.coindashButton);
        allCurrenciesButtons.add(coindashButton);
        ToggleButton cossButton = findViewById(R.id.cossButton);
        allCurrenciesButtons.add(cossButton);
        ToggleButton lbryButton = findViewById(R.id.lbrycreditsButton);
        allCurrenciesButtons.add(lbryButton);
        ToggleButton unikoingoldButton = findViewById(R.id.unikoingoldButton);
        allCurrenciesButtons.add(unikoingoldButton);
        ToggleButton steemdollarsButton = findViewById(R.id.steemdollarsButton);
        allCurrenciesButtons.add(steemdollarsButton);
        ToggleButton nagaButton = findViewById(R.id.nagaButton);
        allCurrenciesButtons.add(nagaButton);
        ToggleButton delphyButton = findViewById(R.id.delphyButton);
        allCurrenciesButtons.add(delphyButton);
        ToggleButton aeonButton = findViewById(R.id.aeonButton);
        allCurrenciesButtons.add(aeonButton);
        ToggleButton indahashButton = findViewById(R.id.indahashButton);
        allCurrenciesButtons.add(indahashButton);
        ToggleButton lunyrButton = findViewById(R.id.lunyrButton);
        allCurrenciesButtons.add(lunyrButton);
        ToggleButton firstbloodButton = findViewById(R.id.firstbloodButton);
        allCurrenciesButtons.add(firstbloodButton);
        ToggleButton viberateButton = findViewById(R.id.viberateButton);
        allCurrenciesButtons.add(viberateButton);
        ToggleButton datumButton = findViewById(R.id.datumButton);
        allCurrenciesButtons.add(datumButton);
        ToggleButton odysseyButton = findViewById(R.id.odysseyButton);
        allCurrenciesButtons.add(odysseyButton);
        ToggleButton inkButton = findViewById(R.id.inkButton);
        allCurrenciesButtons.add(inkButton);
        ToggleButton potcoinButton = findViewById(R.id.potcoinButton);
        allCurrenciesButtons.add(potcoinButton);
        ToggleButton swftcoinButton = findViewById(R.id.swftcoinButton);
        allCurrenciesButtons.add(swftcoinButton);
        ToggleButton humaniqButton = findViewById(R.id.humaniqButton);
        allCurrenciesButtons.add(humaniqButton);
        ToggleButton monethaButton = findViewById(R.id.monethaButton);
        allCurrenciesButtons.add(monethaButton);
        ToggleButton agrelloButton = findViewById(R.id.agrelloButton);
        allCurrenciesButtons.add(agrelloButton);
        ToggleButton yoyowButton = findViewById(R.id.yoyowButton);
        allCurrenciesButtons.add(yoyowButton);
        ToggleButton worldcoreButton = findViewById(R.id.worldcoreButton);
        allCurrenciesButtons.add(worldcoreButton);
        ToggleButton selfkeyButton = findViewById(R.id.selfkeyButton);
        allCurrenciesButtons.add(selfkeyButton);
        ToggleButton blocktixButton = findViewById(R.id.blocktixButton);
        allCurrenciesButtons.add(blocktixButton);
        ToggleButton everexButton = findViewById(R.id.everexButton);
        allCurrenciesButtons.add(everexButton);
        ToggleButton suncontractButton = findViewById(R.id.suncontractButton);
        allCurrenciesButtons.add(suncontractButton);
        ToggleButton tradetokenButton = findViewById(R.id.tradetokenButton);
        allCurrenciesButtons.add(tradetokenButton);
        ToggleButton latokenButton = findViewById(R.id.latokenButton);
        allCurrenciesButtons.add(latokenButton);
        ToggleButton blackcoinButton = findViewById(R.id.blackcoinButton);
        allCurrenciesButtons.add(blackcoinButton);
        ToggleButton ixledgerButton = findViewById(R.id.ixledgerButton);
        allCurrenciesButtons.add(ixledgerButton);
        ToggleButton olympuslabsButton = findViewById(R.id.olympuslabsButton);
        allCurrenciesButtons.add(olympuslabsButton);
        ToggleButton hydroprotocolButton = findViewById(R.id.hydroprotocolButton);
        allCurrenciesButtons.add(hydroprotocolButton);
        ToggleButton moedaloyaltypointsButton = findViewById(R.id.moedaloyaltypointsButton);
        allCurrenciesButtons.add(moedaloyaltypointsButton);
        ToggleButton oaxButton = findViewById(R.id.oaxButton);
        allCurrenciesButtons.add(oaxButton);
        ToggleButton stoxButton = findViewById(R.id.stoxButton);
        allCurrenciesButtons.add(stoxButton);
        ToggleButton qunqunButton = findViewById(R.id.qunqunButton);
        allCurrenciesButtons.add(qunqunButton);
        ToggleButton blockmasonButton = findViewById(R.id.blockmasoncreditButton);
        allCurrenciesButtons.add(blockmasonButton);
        ToggleButton stkButton = findViewById(R.id.stkButton);
        allCurrenciesButtons.add(stkButton);
        ToggleButton aeronButton = findViewById(R.id.aeronButton);
        allCurrenciesButtons.add(aeronButton);
        ToggleButton synereoButton = findViewById(R.id.syneroButton);
        allCurrenciesButtons.add(synereoButton);
        ToggleButton radiumButton = findViewById(R.id.radiumButton);
        allCurrenciesButtons.add(radiumButton);
        ToggleButton vericoinButton = findViewById(R.id.vericoinButton);
        allCurrenciesButtons.add(vericoinButton);
        ToggleButton propyButton = findViewById(R.id.propyButton);
        allCurrenciesButtons.add(propyButton);
        ToggleButton matchpoolButton = findViewById(R.id.matchpoolButton);
        allCurrenciesButtons.add(matchpoolButton);

        for(ToggleButton toggleButton: allCurrenciesButtons){
            toggleButton.setOnClickListener(this);
        }




    }

    /**
     * Adds bitcoin and ethereum to every exchange by default
     */
    public void addBitcoinAndEthereumToExchanges(){
        Coin bitcoinBitfinex = new Coin("Bitcoin","BTC",
                HomePage.bitfinex,"100");
        HomePage.bitfinex.addCoin(bitcoinBitfinex);
        Coin bitcoinBittrex = new Coin("Bitcoin","BTC",
                HomePage.bittrex,"100");
        HomePage.bittrex.addCoin(bitcoinBittrex);
        Coin bitcoinBinance = new Coin("Bitcoin", "BTC", HomePage.binance,"100");
        HomePage.binance.addCoin(bitcoinBinance);
        Coin bitcoinHitBTC = new Coin("Bitcoin", "BTC",
                HomePage.hitBTC,"100");
        HomePage.hitBTC.addCoin(bitcoinHitBTC);
        Coin bitcoinBitZ = new Coin("Bitcoin", "btc",
                HomePage.bitZ,"100");
        HomePage.bitZ.addCoin(bitcoinBitZ);
        Coin bitcoinPoloniex = new Coin("Bitcoin","BTC",
                HomePage.poloniex,"100");
        HomePage.poloniex.addCoin(bitcoinPoloniex);
        Coin bitcoinBitStamp = new Coin ("Bitcoin","BTC",
                HomePage.bitStamp,"100");
        HomePage.bitStamp.addCoin(bitcoinBitStamp);
        Coin bitcoinOKEX = new Coin("Bitcoin", "BTC",
                HomePage.OKEX,"100");
        HomePage.OKEX.addCoin(bitcoinOKEX);
        Coin bitcoinGDAX = new Coin("Bitcoin","BTC",
                HomePage.GDAX,"100");
        HomePage.GDAX.addCoin(bitcoinGDAX);
        Coin bitcoinKraken = new Coin("Bitcoin","XXBT",
                HomePage.kraken, "100");
        HomePage.kraken.addCoin(bitcoinKraken);
        Coin bitcoinHuobi = new Coin("Bitcoin","btc",
                HomePage.huobi,"100");
        HomePage.huobi.addCoin(bitcoinHuobi);
        HomePage.listOfCurrencies.add("Bitcoin");

        Coin ethereumBitfinex = new Coin("Ethereum","ETH",
                HomePage.bitfinex,"110");
        HomePage.bitfinex.addCoin(ethereumBitfinex);
        Coin ethereumBittrex = new Coin("Ethereum","ETH",
                HomePage.bittrex,"110");
        HomePage.bittrex.addCoin(ethereumBittrex);
        Coin ethereumBinance = new Coin("Ethereum", "ETH",
                HomePage.binance,"110");
        HomePage.binance.addCoin(ethereumBinance);
        Coin ethereumHitBTC = new Coin("Ethereum", "ETH",
                HomePage.hitBTC,"110");
        HomePage.hitBTC.addCoin(ethereumHitBTC);
        Coin ethereumBitZ = new Coin("Ethereum", "eth",
                HomePage.bitZ,"110");
        HomePage.bitZ.addCoin(ethereumBitZ);
        Coin ethereumPoloniex = new Coin("Ethereum","ETH",
                HomePage.poloniex,"110");
        HomePage.poloniex.addCoin(ethereumPoloniex);
        Coin ethereumBitStamp = new Coin ("Ethereum","ETH",
                HomePage.bitStamp,"110");
        HomePage.bitStamp.addCoin(ethereumBitStamp);
        Coin ethereumOKEX = new Coin("Ethereum", "ETH",
                HomePage.OKEX,"110");
        HomePage.OKEX.addCoin(ethereumOKEX);
        Coin ethereumGDAX = new Coin("Ethereum","ETH",
                HomePage.GDAX, "110");
        HomePage.GDAX.addCoin(ethereumGDAX);
        Coin ethereumKraken = new Coin("Ethereum","XETH",
                HomePage.kraken,"110");
        HomePage.kraken.addCoin(ethereumKraken);
        Coin ethereumHuobi = new Coin("Ethereum","eth",
                HomePage.huobi,"110");
        HomePage.huobi.addCoin(ethereumHuobi);
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
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(rippleBitfinex);
                Coin rippleBittrex = new Coin("Ripple","XRP",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(rippleBittrex);
                Coin rippleBinance = new Coin("Ripple", "XRP",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(rippleBinance);
                Coin rippleHitBTC = new Coin("Ripple", "XRP",
                        HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(rippleHitBTC);
                Coin ripplePoloniex = new Coin("Ripple","XRP",
                        HomePage.poloniex,"110");
                HomePage.poloniex.addCoin(ripplePoloniex);
                Coin rippleBitStamp = new Coin ("Ripple","XRP",
                        HomePage.bitStamp,"110");
                HomePage.bitStamp.addCoin(rippleBitStamp);
                Coin rippleOKEX = new Coin("Ripple", "XRP",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(rippleOKEX);
                Coin rippleKraken = new Coin("Ripple","XXRP",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(rippleKraken);
                Coin rippleHuobi = new Coin("Ripple","xrp",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(rippleHuobi);
                HomePage.listOfCurrencies.add("Ripple");
                break;

            case R.id.bitcoinCashButton:
                Coin bitcoinCashBitfinex = new Coin("Bitcoin Cash","BCH",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(bitcoinCashBitfinex);
                Coin bitcoinCashBittrex = new Coin("Bitcoin Cash","BCC",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(bitcoinCashBittrex);
                Coin bitcoinCashBinance = new Coin("Bitcoin Cash", "BCC",
                        HomePage.binance,"111");
                HomePage.binance.addCoin(bitcoinCashBinance);
                Coin bitcoinCashHitBTC = new Coin("Bitcoin Cash", "BCH",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(bitcoinCashHitBTC);
                Coin bitcoinCashPoloniex = new Coin("Bitcoin Cash","BCH",
                        HomePage.poloniex,"111");
                HomePage.poloniex.addCoin(bitcoinCashPoloniex);
                Coin bitcoinCashBitStamp = new Coin ("Bitcoin Cash","BCH",
                        HomePage.bitStamp,"110");
                HomePage.bitStamp.addCoin(bitcoinCashBitStamp);
                Coin bitcoinCashOKEX = new Coin("Bitcoin Cash", "BCH",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(bitcoinCashOKEX);
                Coin bitcoinCashGDAX = new Coin("Bitcoin Cash","BCH",
                        HomePage.GDAX,"110");
                HomePage.GDAX.addCoin(bitcoinCashGDAX);
                Coin bitcoinCashKraken = new Coin("Bitcoin Cash","BCH",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(bitcoinCashKraken);
                Coin bitcoinCashHuobi = new Coin("Bitcoin Cash","bch",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(bitcoinCashHuobi);
                HomePage.listOfCurrencies.add("Bitcoin Cash");
                break;

            case R.id.cardanoButton:
                Coin cardanoBittrex = new Coin("Cardano","ADA",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(cardanoBittrex);
                Coin cardanoBinance = new Coin("Cardano", "ADA",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(cardanoBinance);
//19
                HomePage.listOfCurrencies.add("Cardano");
                break;

            case R.id.nemButton:
                Coin nemBittrex = new Coin("NEM","XEM",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(nemBittrex);
                Coin nemHitBTC = new Coin("NEM", "XEM",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(nemHitBTC);
                Coin nemPoloniex = new Coin("NEM","XEM",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(nemPoloniex);
                Coin nemOKEX = new Coin("NEM", "XEM",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(nemOKEX);
                Coin nemHuobi = new Coin("NEM","xem",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(nemHuobi);
                HomePage.listOfCurrencies.add("NEM");
                break;

            case R.id.litecoinButton:
                Coin litecoinBitfinex = new Coin("Litecoin","LTC",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(litecoinBitfinex);
                Coin litecoinBittrex = new Coin("Litecoin","LTC",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(litecoinBittrex);
                Coin litecoinBinance = new Coin("Litecoin", "LTC",
                        HomePage.binance,"111");
                HomePage.binance.addCoin(litecoinBinance);
                Coin litecoinHitBTC = new Coin("Litecoin", "LTC",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(litecoinHitBTC);
                Coin litecoinBitZ = new Coin("Litecoin", "ltc",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(litecoinBitZ);
                Coin litecoinPoloniex = new Coin("Litecoin", "LTC",
                        HomePage.poloniex,"110");
                HomePage.poloniex.addCoin(litecoinPoloniex);
                Coin litecoinBitStamp = new Coin ("Litecoin","LTC",
                        HomePage.bitStamp,"110");
                HomePage.bitStamp.addCoin(litecoinBitStamp);
                Coin litecoinOKEX = new Coin("Litecoin", "LTC",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(litecoinOKEX);
                Coin litecoinGDAX = new Coin("Litecoin","LTC",
                        HomePage.GDAX,"100");
                HomePage.GDAX.addCoin(litecoinGDAX);
                Coin litecoinKraken = new Coin("Litecoin","XLTC",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(litecoinKraken);
                Coin litecoinHuobi = new Coin("Litecoin","ltc",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(litecoinHuobi);
                HomePage.listOfCurrencies.add("Litecoin");
                break;

            case R.id.stellarButton:
                Coin stellarBittrex = new Coin("Stellar","XLM",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(stellarBittrex);
                Coin stellarBinance = new Coin("Stellar", "XLM",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(stellarBinance);
                Coin stellarOKEX = new Coin("Stellar", "XLM",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(stellarOKEX);
                Coin stellarKraken = new Coin("Stellar","XXLM",
                        HomePage.kraken,"010");
                HomePage.kraken.addCoin(stellarKraken);

                HomePage.listOfCurrencies.add("Stellar");
                break;

            case R.id.iotaButton:
                Coin iotaBitfinex = new Coin("Iota","IOT",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(iotaBitfinex);
                Coin iotaBinance = new Coin("Iota", "IOTA",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(iotaBinance);
                Coin iotaOKEX = new Coin("Iota", "IOTA",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(iotaOKEX);
                HomePage.listOfCurrencies.add("Iota");
                break;

            case R.id.dashButton:
                Coin dashBitfinex = new Coin("Dash","DSH",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(dashBitfinex);
                Coin dashBittrex = new Coin("Dash","DASH",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(dashBittrex);
                Coin dashBinance = new Coin("Dash", "DASH",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(dashBinance);
                Coin dashHitBTC = new Coin("Dash", "DASH",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(dashHitBTC);
                Coin dashBitZ = new Coin("Dash", "dash",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(dashBitZ);
                Coin dashPoloniex = new Coin("Dash", "DASH",
                        HomePage.poloniex,"110");
                HomePage.poloniex.addCoin(dashPoloniex);
                Coin dashOKEX = new Coin("Dash", "DASH",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(dashOKEX);
                Coin dashKraken = new Coin("Dash","DASH",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(dashKraken);
                Coin dashHuobi = new Coin("Dash","dash",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(dashHuobi);
                HomePage.listOfCurrencies.add("Dash");
                break;

            case R.id.neoButton:
                Coin neoBitfinex = new Coin("NEO","NEO",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(neoBitfinex);
                Coin neoBittrex = new Coin("NEO","NEO",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(neoBittrex);
                Coin neoBinance = new Coin("NEO", "NEO",
                        HomePage.binance,"111");
                HomePage.binance.addCoin(neoBinance);
                Coin neoHitBTC = new Coin("NEO", "NEO",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(neoHitBTC);
                Coin neoOKEX = new Coin("NEO", "NEO",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(neoOKEX);
                Coin neoHuobi = new Coin("NEO","neo",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(neoHuobi);
                HomePage.listOfCurrencies.add("NEO");
                break;

            case R.id.moneroButton:
                Coin moneroBitfinex = new Coin("Monero","XMR",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(moneroBitfinex);
                Coin moneroBittrex = new Coin("Monero","XMR",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(moneroBittrex);
                Coin moneroBinance = new Coin("Monero", "XMR",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(moneroBinance);
                Coin moneroHitBTC = new Coin("Monero", "XMR",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(moneroHitBTC);
                Coin moneroPoloniex = new Coin("Monero", "XMR",
                        HomePage.poloniex,"110");
                HomePage.poloniex.addCoin(moneroPoloniex);
                Coin moneroOKEX = new Coin("Monero", "XMR",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(moneroOKEX);
                Coin moneroKraken = new Coin("Monero","XXMR",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(moneroKraken);
                HomePage.listOfCurrencies.add("Monero");
                break;

            case R.id.qtumButton:
                Coin qtumBitfinex = new Coin("QTUM","QTM",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(qtumBitfinex);
                Coin qtumBittrex = new Coin("QTUM","QTUM",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(qtumBittrex);
                Coin qtumBinance = new Coin("QTUM", "QTUM",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(qtumBinance);
                Coin qtumHitBTC = new Coin("QTUM", "QTUM",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(qtumHitBTC);
                Coin qtumBitZ = new Coin("QTUM", "qtum",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(qtumBitZ);
                Coin qtumOKEX = new Coin("QTUM", "QTUM",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(qtumOKEX);
                Coin qtumHuobi = new Coin("QTUM","qtum",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(qtumHuobi);
                HomePage.listOfCurrencies.add("QTUM");
                break;

            case R.id.liskButton:
                Coin liskBittrex = new Coin("Lisk","LSK",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(liskBittrex);
                Coin liskBinance = new Coin("Lisk", "LSK",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(liskBinance);
                Coin liskHitBTC = new Coin("Lisk", "LSK",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(liskHitBTC);
                Coin liskBitZ = new Coin("Lisk", "lsk",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(liskBitZ);
                Coin liskPoloniex = new Coin("Lisk", "LSK",
                        HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(liskPoloniex);
                Coin liskHuobi = new Coin("Lisk","lsk",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(liskHuobi);
                HomePage.listOfCurrencies.add("Lisk");
                break;

            case R.id.ethereumClassicButton:
                Coin ethereumClassicBitfinex = new Coin("Ethereum Classic","ETC",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(ethereumClassicBitfinex);
                Coin ethereumClassicBittrex = new Coin("Ethereum Classic","ETC",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(ethereumClassicBittrex);
                Coin ethereumClassicBinance = new Coin("Ethereum Classic", "ETC",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(ethereumClassicBinance);
                Coin ethereumClassicHitBTC = new Coin("Ethereum Classic", "ETC",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(ethereumClassicHitBTC);
                Coin ethereumClassicBitZ = new Coin("Ethereum Classic", "etc",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(ethereumClassicBitZ);
                Coin ethereumClassicPoloniex = new Coin("Ethereum Classic", "ETC",
                        HomePage.poloniex,"111");
                HomePage.poloniex.addCoin(ethereumClassicPoloniex);
                Coin ethereumClassicOKEX = new Coin("Ethereum Classic", "ETC",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(ethereumClassicOKEX);
                Coin ethereumClassicKraken = new Coin("Ethereum Classic","XETC",
                        HomePage.kraken,"111");
                HomePage.kraken.addCoin(ethereumClassicKraken);
                Coin ethereumClassicHuobi = new Coin("Ethereum Classic","etc",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(ethereumClassicHuobi);
                HomePage.listOfCurrencies.add("Ethereum Classic");
                break;

            case R.id.raiblocksButton:
                Coin raiBlocksBitZ = new Coin("Nano", "xrb",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(raiBlocksBitZ);
                Coin raiBlocksBinance = new Coin("Nano","NANO",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(raiBlocksBinance);
                HomePage.listOfCurrencies.add("Nano");
                break;

            case R.id.vergeButton:
                Coin vergeBittrex = new Coin("Verge","XVG",
                        HomePage.bittrex,"110");
                HomePage.bittrex.addCoin(vergeBittrex);
                Coin vergeBinance = new Coin("Verge", "XVG",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(vergeBinance);
                Coin vergeHitBTC = new Coin("Verge", "XVG",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(vergeHitBTC);
                HomePage.listOfCurrencies.add("Verge");
                break;

            case R.id.siacoinButton:
                Coin siacoinBittrex = new Coin("Siacoin","SC",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(siacoinBittrex);
                Coin siacoinHitBTC = new Coin("Siacoin", "SC",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(siacoinHitBTC);
                Coin siacoinPoloniex = new Coin("Siacoin", "SC",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(siacoinPoloniex);
                HomePage.listOfCurrencies.add("Siacoin");
                break;

            case R.id.stratisButton:
                Coin stratisBittrex = new Coin("Stratis","STRAT",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(stratisBittrex);
                Coin stratisBinance = new Coin("Stratis", "STRAT",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(stratisBinance);
                Coin stratisHitBTC = new Coin("Stratis", "STRAT",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(stratisHitBTC);
                Coin stratisPoloniex = new Coin("Stratis", "STRAT",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(stratisPoloniex);
                HomePage.listOfCurrencies.add("Stratis");
                break;

            case R.id.zcashButton:
                Coin zcashBitfinex = new Coin("ZCash","ZEC",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(zcashBitfinex);
                Coin zcashBittrex = new Coin("ZCash","ZEC",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(zcashBittrex);
                Coin zcashBinance = new Coin("ZCash", "ZEC",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(zcashBinance);
                Coin zcashHitBTC = new Coin("ZCash", "ZEC",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(zcashHitBTC);
                Coin zcashBitZ = new Coin("ZCash", "zec",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(zcashBitZ);
                Coin zcashPoloniex = new Coin("ZCash", "ZEC",
                        HomePage.poloniex,"111");
                HomePage.poloniex.addCoin(zcashPoloniex);
                Coin zcashOKEX = new Coin("ZCash", "ZEC",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(zcashOKEX);
                Coin zcashKraken = new Coin("ZCash","XZEC",
                        HomePage.kraken,"110");
                HomePage.kraken.addCoin(zcashKraken);
                Coin zecHuobi = new Coin("ZCash","zec",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(zecHuobi);
                HomePage.listOfCurrencies.add("ZCash");
                break;

            case R.id.dogecoinButton:
                Coin dogecoinBittrex = new Coin("Dogecoin","DOGE",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(dogecoinBittrex);
                Coin dogecoinHitBTC = new Coin("Dogecoin", "DOGE",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(dogecoinHitBTC);
                Coin dogecoinBitZ = new Coin("Dogecoin", "doge",
                        HomePage.bitZ, "011");
                HomePage.bitZ.addCoin(dogecoinBitZ);
                Coin dogecoinPoloniex = new Coin("Dogecoin", "DOGE",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(dogecoinPoloniex);
                HomePage.listOfCurrencies.add("Dogecoin");
                break;

            case R.id.steemButton: //Steem
                Coin steemBittrex = new Coin("Steem","STEEM",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(steemBittrex);
                Coin steemHitBTC = new Coin("Steem", "STEEM",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(steemHitBTC);
                Coin steemPoloniex = new Coin("Steem", "STEEM",
                        HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(steemPoloniex);
                HomePage.listOfCurrencies.add("Steem");
                break;

            case R.id.wavesButton: //Waves
                Coin wavesBittrex = new Coin("Waves","WAVES",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(wavesBittrex);
                Coin wavesBinance = new Coin("Waves", "WAVES",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(wavesBinance);
                HomePage.listOfCurrencies.add("Waves");
                break;

            case R.id.vechainButton: //VeChain
                Coin vechainBinance = new Coin("VeChain", "VEN",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(vechainBinance);
                Coin vechainHitBTC = new Coin("VeChain", "VEN",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(vechainHitBTC);
                Coin vechainHuobi = new Coin("VeChain","ven",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(vechainHuobi);
                HomePage.listOfCurrencies.add("VeChain");
                break;

            case R.id.digibyteButton: //Digibyte
                Coin digibyteBittrex = new Coin("Digibyte","DGB",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(digibyteBittrex);
                Coin digibyteHitBTC = new Coin("Digibyte", "DGB",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(digibyteHitBTC);
                Coin digibtyeBitZ = new Coin("Digibyte", "dgb",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(digibtyeBitZ);
                Coin digibytePoloniex = new Coin("Digibyte", "DGB",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(digibytePoloniex);
                Coin digibyteOKEX = new Coin("Digibyte", "DGB",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(digibyteOKEX);
                HomePage.listOfCurrencies.add("Digibyte");
                break;

            case R.id.komodoButton: //Komodo
                Coin komodoBittrex = new Coin("Komodo","KMD",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(komodoBittrex);
                Coin komodoBinance = new Coin("Komodo", "KMD",
                        HomePage.binance, "011");
                HomePage.binance.addCoin(komodoBinance);
                Coin komodoHitBTC = new Coin("Komodo", "KMD",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(komodoHitBTC);
                HomePage.listOfCurrencies.add("Komodo");
                break;

            case R.id.hshareButton: //HShare
                Coin hshareBinance = new Coin("HShare", "HSR",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(hshareBinance);
                Coin hshareHitBTC = new Coin("HShare", "HSR",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(hshareHitBTC);
                Coin hshareBitZ = new Coin("HShare", "hsr",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(hshareBitZ);
                Coin hshareOKEX = new Coin("HShare", "HSR",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(hshareOKEX);
                Coin hshareHuobi = new Coin("HShare","hsr",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(hshareHuobi);
                HomePage.listOfCurrencies.add("HShare");
                break;

            case R.id.arkButton: //Ark
                Coin arkBittrex = new Coin("Ark","ARK",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(arkBittrex);
                Coin arkBinance = new Coin("Ark", "ARK",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(arkBinance);
                Coin arkBitZ = new Coin("Ark", "ark",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(arkBitZ);
                Coin arkOKEX = new Coin("Ark", "ARK",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(arkOKEX);
                HomePage.listOfCurrencies.add("Ark");
                break;

            case R.id.decredButton:
                Coin decredBittrex = new Coin("Decred","DCR",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(decredBittrex);
                Coin decredPoloniex = new Coin("Decred","DCR",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(decredPoloniex);
                HomePage.listOfCurrencies.add("Decred");
                break;

            case R.id.factomButton:
                Coin factomBittrex = new Coin("Factom","FCT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(factomBittrex);
                Coin factomPoloniex = new Coin("Factom","FCT",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(factomPoloniex);
                Coin factomBitZ = new Coin("Factom","fct",HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(factomBitZ);
                HomePage.listOfCurrencies.add("Factom");
                break;

            case R.id.neblioButton:
                Coin neblioBinance = new Coin("Neblio","NEBL",HomePage.binance,
                        "011");
                HomePage.binance.addCoin(neblioBinance);
                //HomePage.listOfCurrencies.add("Neblio");
                break;

            case R.id.digitalNoteButton:
                Coin digitalNoteBittrex = new Coin("Digital Note","XDN",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(digitalNoteBittrex);
                Coin digitalNoteHitBTC = new Coin("Digital Note","XDN",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(digitalNoteHitBTC);
                HomePage.listOfCurrencies.add("Digital Note");
                break;

            case R.id.nxtButton:
                Coin nxtHitBTC = new Coin("NXT","NXT",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(nxtHitBTC);
                Coin nxtBittrex = new Coin("NXT","NXT",
                        HomePage.bittrex,"110");
                HomePage.bittrex.addCoin(nxtBittrex);
                Coin nxtPoloniex = new Coin("NXT","NXT",
                        HomePage.poloniex,"110");
                HomePage.poloniex.addCoin(nxtPoloniex);
                HomePage.listOfCurrencies.add("NXT");
                break;

            case R.id.syscoinButton:
                Coin syscoinBittrex = new Coin("Syscoin","SYS",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(syscoinBittrex);
                Coin syscoinPoloniex = new Coin("Syscoin","SYS",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(syscoinPoloniex);
                HomePage.listOfCurrencies.add("Syscoin");
                break;

            case R.id.zcoinButton:
                Coin zcoinBittrex = new Coin("ZCoin","XZC",HomePage.bittrex,
                        "010");
                HomePage.bittrex.addCoin(zcoinBittrex);
                Coin zcoinBinance = new Coin("ZCoin","XZC",HomePage.binance,
                        "011");
                HomePage.binance.addCoin(zcoinBinance);
                HomePage.listOfCurrencies.add("ZCoin");
                break;

            case R.id.gameCreditsButton:
                Coin gameCreditsBitZ = new Coin("Game Credits","game",HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(gameCreditsBitZ);
                Coin gameCreditsBittrex = new Coin("Game Credits","GAME",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(gameCreditsBittrex);
                Coin gameCreditsPoloniex = new Coin("Game Credits","GAME",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(gameCreditsPoloniex);
                Coin gameCreditsHitBTC = new Coin("Game Credits","GAME",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(gameCreditsHitBTC);
                HomePage.listOfCurrencies.add("Game Credits");
                break;

            case R.id.gxSharesButton:
                 Coin gxSharesBinance = new Coin("GXShares","GXS",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(gxSharesBinance);
                Coin gxSharesBitZ = new Coin("GXShares","gxs",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(gxSharesBitZ);
                HomePage.listOfCurrencies.add("GXShares");
                break;

            case R.id.vertcoinButton:
                Coin vertcoinBittrex = new Coin("Vertcoin","VTC",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(vertcoinBittrex);
                Coin vertcoinPoloniex = new Coin("Vertcoin","VTC",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(vertcoinPoloniex);
                HomePage.listOfCurrencies.add("Vertcoin");
                break;

            case R.id.eosButton:
                Coin eosBitfinex = new Coin("EOS","EOS",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(eosBitfinex);
                Coin eosBinance = new Coin("EOS","EOS",HomePage.binance,"011");
                HomePage.binance.addCoin(eosBinance);
                Coin eosKraken = new Coin("EOS","EOS",HomePage.kraken,"011");
                HomePage.kraken.addCoin(eosKraken);
                Coin eosBitZ = new Coin("EOS","eos",HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(eosBitZ);
                Coin eosHitBTC = new Coin("EOS","EOS",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(eosHitBTC);
                Coin eosHuobi = new Coin("EOS","eos",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(eosHuobi);
                HomePage.listOfCurrencies.add("EOS");
                break;

            case R.id.tronButton:
                Coin tronBitfinex = new Coin("Tron","TRX",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(tronBitfinex);
                Coin tronBinance = new Coin("Tron","TRX",HomePage.binance,"011");
                HomePage.binance.addCoin(tronBinance);
                Coin tronBitZ = new Coin("Tron","trx",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(tronBitZ);
                Coin tronHitBTC = new Coin("Tron","TRX",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(tronHitBTC);
                Coin tronHuobi = new Coin("Tron","trx",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(tronHuobi);
                HomePage.listOfCurrencies.add("Tron");
                break;

            case R.id.bitcoinGoldButton:
                Coin bitcoinGoldBinance = new Coin("Bitcoin Gold","BTG",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(bitcoinGoldBinance);
                Coin bitcoinGoldBittrex = new Coin("Bitcoin Gold","BTG",
                        HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(bitcoinGoldBittrex);
                Coin bitcoinGoldBitfinex = new Coin("Bitcoin Gold","BTG",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(bitcoinGoldBitfinex);
                Coin bitcoinGoldHitBTC = new Coin("Bitcoin Gold","BTG",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(bitcoinGoldHitBTC);
                Coin bitcoinGoldHuobi = new Coin("Bitcoin Gold","btg",
                        HomePage.huobi,"010");
                HomePage.huobi.addCoin(bitcoinGoldHuobi);
                HomePage.listOfCurrencies.add("Bitcoin Gold");
                break;

            case R.id.iconButton:
                Coin iconBinance = new Coin("Icon","ICX",HomePage.binance,"011");
                HomePage.binance.addCoin(iconBinance);
                Coin iconHitBTC = new Coin("Icon","ICX",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(iconHitBTC);
                Coin iconHuobi = new Coin("Icon","icx",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(iconHuobi);
                HomePage.listOfCurrencies.add("Icon");
                break;

            case R.id.omiseGoButton:
                Coin omiseGoBinance = new Coin("OmiseGO","OMG",HomePage.binance,"011");
                HomePage.binance.addCoin(omiseGoBinance);
                Coin omiseGoBitfinex = new Coin("OmiseGO","OMG",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(omiseGoBitfinex);
                Coin omiseGoBittrex = new Coin("OmiseGO","OMG",HomePage.bittrex,"111");
                HomePage.bittrex.addCoin(omiseGoBittrex);
                Coin omiseGoPoloniex = new Coin("OmiseGO","OMG",HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(omiseGoPoloniex);
                Coin omiseGoHuobi = new Coin("OmiseGO","omg",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(omiseGoHuobi);
                HomePage.listOfCurrencies.add("OmiseGO");
                break;


            case R.id.populousButton:
                Coin populousBinance = new Coin("Populous","PPT",HomePage.binance,"011");
                HomePage.binance.addCoin(populousBinance);
                Coin populousHitBTC = new Coin("Populous","PPT",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(populousHitBTC);
                Coin populousOKEX = new Coin("Populous","ppt",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(populousOKEX);
                HomePage.listOfCurrencies.add("Populous");
                break;

            case R.id.bytecoinButton:
                Coin bytecoinPoloniex = new Coin("Bytecoin","BCN",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(bytecoinPoloniex);
                Coin bytecoinHitBTC = new Coin("Bytecoin","BCN",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(bytecoinHitBTC);
                HomePage.listOfCurrencies.add("Bytecoin");
                break;

            case R.id.statusButton:
                Coin statusBittrex = new Coin("Status","SNT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(statusBittrex);
                Coin statusBinance = new Coin("Status","SNT",HomePage.binance,"011");
                HomePage.binance.addCoin(statusBinance);
                Coin statusBitfinex = new Coin("Status","SNT",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(statusBitfinex);
                Coin statusOKEX = new Coin("Status","SNT",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(statusOKEX);
                Coin statusHuobi = new Coin("Status","snt",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(statusHuobi);
                HomePage.listOfCurrencies.add("Status");
                break;

            case R.id.bitsharesButton:
                Coin bitsharesBinance = new Coin("BitShares","BTS",HomePage.binance,"011");
                HomePage.binance.addCoin(bitsharesBinance);
                Coin bitsharesPoloniex = new Coin("BitShares","BTS",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(bitsharesPoloniex);
                HomePage.listOfCurrencies.add("BitShares");
                break;

            case R.id.augurButton:
                Coin augurBittrex = new Coin("Augur","REP",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(augurBittrex);
                Coin augurPoloniex = new Coin("Augur","REP",HomePage.poloniex,"111");
                HomePage.poloniex.addCoin(augurPoloniex);
                Coin augurBitfinex = new Coin("Augur","REP",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(augurBitfinex);
                Coin augurKraken = new Coin("Augur","XREP",HomePage.kraken,"011");
                HomePage.kraken.addCoin(augurKraken);
                Coin augurHitBTC = new Coin("Augur","REP",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(augurHitBTC);
                HomePage.listOfCurrencies.add("Augur");
                break;

            case R.id.veritaseumButton:
                Coin veritaseumHitBTC = new Coin("Veritaseum","VERI",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(veritaseumHitBTC);
                //HomePage.listOfCurrencies.add("Veritaseum");
                break;

            case R.id.waltonButton:
                Coin waltonBinance = new Coin("Waltonchain","WTC",HomePage.binance,"011");
                HomePage.binance.addCoin(waltonBinance);
                Coin waltonOKEX = new Coin("Waltonchain","WTC",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(waltonOKEX);
                Coin waltonHitBTC = new Coin("Waltonchain","WTC",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(waltonHitBTC);
                HomePage.listOfCurrencies.add("Waltonchain");
                break;

            case R.id.zeroxButton:
                Coin zeroxBinance = new Coin("0x","ZRX",HomePage.binance,"011");
                HomePage.binance.addCoin(zeroxBinance);
                Coin zeroxBitfinex = new Coin("0x","zrx",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(zeroxBitfinex);
                Coin zeroxPoloniex = new Coin("0x","ZRX",HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(zeroxPoloniex);
                Coin zeroxHitBTC = new Coin("0x","ZRX",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(zeroxHitBTC);
                Coin zeroxOKEX = new Coin("0x","ZRX",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(zeroxOKEX);
                Coin zeroxHuobi = new Coin("0x","zrx",
                        HomePage.huobi,"010");
                HomePage.huobi.addCoin(zeroxHuobi);
                HomePage.listOfCurrencies.add("0x");
                break;

            case R.id.ardorButton:
                Coin ardorHitBTC = new Coin("Ardor","ARDR",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(ardorHitBTC);
                Coin ardorBittrex = new Coin("Ardor","ARDR",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(ardorBittrex);
                Coin ardorPoloniex = new Coin("Ardor","ARDR",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(ardorPoloniex);
                HomePage.listOfCurrencies.add("Ardor");
                break;

            case R.id.revainButton:
                Coin revainOKEX = new Coin("Revain","R",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(revainOKEX);
                //HomePage.listOfCurrencies.add("Revain");
                break;

            case R.id.digixdaoButton:
                Coin digixdaoBinance = new Coin("DigixDAO", "DGD",HomePage.binance,"011");
                HomePage.binance.addCoin(digixdaoBinance);
                Coin digixdaoOKEX = new Coin("DigixDAO","dgd",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(digixdaoOKEX);
                Coin digixdaoHitBTC = new Coin("DigixDAO","DGD",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(digixdaoHitBTC);
                Coin digixdaoHuobi = new Coin("DigixDAO","dgd",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(digixdaoHuobi);
                HomePage.listOfCurrencies.add("DigixDAO");
                break;

            case R.id.gasButton:
                Coin gasBinance = new Coin("Gas","GAS",HomePage.binance,"010");
                HomePage.binance.addCoin(gasBinance);
                Coin gasPoloniex = new Coin("Gas","GAS",HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(gasPoloniex);
                Coin gasOKEX = new Coin("Gas","GAS",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(gasOKEX);
                Coin gasHuobi = new Coin("Gas","gas",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(gasHuobi);
                HomePage.listOfCurrencies.add("Gas");
                break;

            case R.id.kyberCurrency:
                Coin kyberBinance = new Coin("Kyber","KNC",HomePage.binance,"011");
                HomePage.binance.addCoin(kyberBinance);
                Coin kyberOKEX = new Coin("Kyber","KNC",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(kyberOKEX);
                Coin kyberHuobi = new Coin("Kyber","knc",
                        HomePage.huobi,"010");
                HomePage.huobi.addCoin(kyberHuobi);
                HomePage.listOfCurrencies.add("Kyber");
                break;

            case R.id.batButton:
                Coin batBinance = new Coin("BAT","BAT",HomePage.binance,"011");
                HomePage.binance.addCoin(batBinance);
                Coin batBitfinex = new Coin("BAT","BAT",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(batBitfinex);
                Coin batBittrex = new Coin("BAT","BAT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(batBittrex);
                Coin batHuobi = new Coin("BAT","bat",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(batHuobi);
                HomePage.listOfCurrencies.add("BAT");
                break;

            case R.id.loopringButton:
                Coin loopringBinance = new Coin("Loopring","LRC",HomePage.binance,"011");
                HomePage.binance.addCoin(loopringBinance);
                Coin loopringOKEX = new Coin("Loopring","LRC",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(loopringOKEX);
                HomePage.listOfCurrencies.add("Loopring");
                break;

            case R.id.pivxButton:
                Coin pivxBinance = new Coin("PIVX","PIVX",HomePage.binance,"011");
                HomePage.binance.addCoin(pivxBinance);
                Coin pivxBittrex = new Coin("PIVX","PIVX",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(pivxBittrex);
                HomePage.listOfCurrencies.add("PIVX");
                break;

            case R.id.ethosButton:
                Coin ethosBinance = new Coin("Ethos","BQX",HomePage.binance,"011");
                HomePage.binance.addCoin(ethosBinance);
                //HomePage.listOfCurrencies.add("Ethos");
                break;

            case R.id.golemButton:
                Coin golemBittrex = new Coin("Golem","GNT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(golemBittrex);
                Coin golemBitfinex = new Coin("Golem","GNT",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(golemBitfinex);
                Coin golemPoloniex = new Coin("Golem","GNT",HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(golemPoloniex);
                Coin golemOKEX = new Coin("Golem","GNT",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(golemOKEX);
                Coin golemHuobi = new Coin("Golem","gnt",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(golemHuobi);
                HomePage.listOfCurrencies.add("Golem");
                break;

            case R.id.aelfButton:
                Coin aelfBinance = new Coin("aelf","ELF",HomePage.binance,"011");
                HomePage.binance.addCoin(aelfBinance);
                Coin aelfOKEX = new Coin("aelf","ELF",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(aelfOKEX);
                Coin aelfHuobi = new Coin("aelf","elf",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(aelfHuobi);
                HomePage.listOfCurrencies.add("aelf");
                break;

            case R.id.nebulasButton:
                Coin nebulasOKEX = new Coin("Nebulas","NAS",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(nebulasOKEX);
                Coin nebulasHuobi = new Coin("Nebulas","nas",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(nebulasHuobi);
                HomePage.listOfCurrencies.add("Nebulas");
                break;

            case R.id.pillarButton:
                Coin pillarHitBTC = new Coin("Pillar","PLR",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(pillarHitBTC);
                //HomePage.listOfCurrencies.add("Pillar");
                break;

            case R.id.powerledgerButton:
                Coin powerledgerBinance = new Coin("Power Ledger","POWR",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(powerledgerBinance);
                Coin powerledgerBittrex = new Coin("Power Ledger","POWR",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(powerledgerBittrex);
                HomePage.listOfCurrencies.add("Power Ledger");
                break;

            case R.id.cindicatorButton:
                Coin cindicatorBinance = new Coin("Cindicator","CND",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(cindicatorBinance);
                Coin cindicatorHitBTC = new Coin("Cindicator","CND",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(cindicatorHitBTC);
                HomePage.listOfCurrencies.add("Cindicator");
                break;

            case R.id.iosTokenButton:
                Coin iostokenBinance = new Coin("IOStoken","IOST",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(iostokenBinance);
                Coin iostokenOKEX = new Coin("IOStoken","IOST",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(iostokenOKEX);
                Coin iostokenHuobi = new Coin("IOStoken","iost",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(iostokenHuobi);
                HomePage.listOfCurrencies.add("IOStoken");
                break;

            case R.id.funfairButton:
                Coin funfairBinance = new Coin("FunFair","FUN",HomePage.binance,"011");
                HomePage.binance.addCoin(funfairBinance);
                Coin funfairBitfinex = new Coin("FunFair","FUN",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(funfairBitfinex);
                Coin funfairOKEX = new Coin("FunFair","FUN",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(funfairOKEX);
                HomePage.listOfCurrencies.add("FunFair");
                break;

            case R.id.enigmaButton:
                Coin enigmaBinance = new Coin("Enigma","ENG",HomePage.binance,"011");
                HomePage.binance.addCoin(enigmaBinance);
                Coin enigmaBittrex = new Coin("Enigma","ENG",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(enigmaBittrex);
                HomePage.listOfCurrencies.add("Enigma");
                break;

            case R.id.saltButton:
                Coin saltBinance = new Coin("Salt","SALT",HomePage.binance,"011");
                HomePage.binance.addCoin(saltBinance);
                Coin saltBittrex = new Coin("Salt","SALT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(saltBittrex);
                Coin saltHuobi = new Coin("Salt","salt",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(saltHuobi);
                HomePage.listOfCurrencies.add("Salt");
                break;

            case R.id.civicButton:
                Coin civicBittrex = new Coin("Civic","CVC",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(civicBittrex);
                Coin civicPoloniex = new Coin("Civic","CVC",HomePage.poloniex,"011");
                HomePage.poloniex.addCoin(civicPoloniex);
                Coin civicOKEX = new Coin("Civic","cvc",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(civicOKEX);
                Coin civicHuobi = new Coin("Civic","cvc",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(civicHuobi);
                HomePage.listOfCurrencies.add("Civic");
                break;

            case R.id.waxButton:
                Coin waxHitBTC = new Coin("WAX","WAX",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(waxHitBTC);
                Coin waxHuobi = new Coin("WAX","wax",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(waxHuobi);
                HomePage.listOfCurrencies.add("WAX");
                break;

            case R.id.storjButton:
                Coin storjBittrex = new Coin("Storj","STORJ",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(storjBittrex);
                Coin storjBinance = new Coin("Storj","STORJ",HomePage.binance,"011");
                HomePage.binance.addCoin(storjBinance);
                Coin storjPoloniex = new Coin("Storj","STORJ",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(storjPoloniex);
                Coin storjHuobi = new Coin("Storj","storj",
                        HomePage.huobi,"110");
                HomePage.huobi.addCoin(storjHuobi);
                HomePage.listOfCurrencies.add("Storj");
                break;

            case R.id.decentralandButton:
                Coin decentralandBittrex = new Coin("Decentraland","MANA",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(decentralandBittrex);
                Coin decentralandBinance = new Coin("Decentraland","MANA",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(decentralandBinance);
                Coin decentralandBitfinex = new Coin("Decentraland","MANA",
                        HomePage.bitfinex,"010");
                HomePage.bitfinex.addCoin(decentralandBitfinex);
                Coin decentralandOKEX = new Coin("Decentraland","MANA",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(decentralandOKEX);
                Coin decentralandHuobi = new Coin("Decentraland","mana",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(decentralandHuobi);
                HomePage.listOfCurrencies.add("Decentraland");
                break;

            case R.id.navButton:
                Coin navBittrex = new Coin("NAV","NAV",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(navBittrex);
                Coin navBinance = new Coin("NAV","NAV",HomePage.binance,"011");
                HomePage.binance.addCoin(navBinance);
                Coin navPoloniex = new Coin("NAV","NAV",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(navPoloniex);
                HomePage.listOfCurrencies.add("NAV");
                break;

            case R.id.timenewbankButton:
                Coin timenewbankBinance = new Coin("Time New Bank","TNB",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(timenewbankBinance);
                Coin timenewbankBitfinex = new Coin("Time New Bank","TNB",
                        HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(timenewbankBitfinex);
                Coin timenewbankOKEX = new Coin("Time New Bank","TNB",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(timenewbankOKEX);
                Coin timenewbankHuobi = new Coin("Time New Bank","tnb",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(timenewbankHuobi);
                HomePage.listOfCurrencies.add("Time New Bank");
                break;

            case R.id.achainButton:
                Coin achainOKEX = new Coin("Achain","ACT",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(achainOKEX);
                Coin achainHuobi = new Coin("Achain","act",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(achainHuobi);
                HomePage.listOfCurrencies.add("Achain");
                break;

            case R.id.gnosisButton:
                Coin gnosisBittrex = new Coin("Gnosis","GNO",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(gnosisBittrex);
                Coin gnosisKraken = new Coin("Gnosis","GNO",HomePage.kraken,"011");
                HomePage.kraken.addCoin(gnosisKraken);
                Coin gnosisPoloniex = new Coin("Gnosis","GNO",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(gnosisPoloniex);
                HomePage.listOfCurrencies.add("Gnosis");
                break;

            case R.id.iconomiButton:
                Coin iconomiBinance = new Coin("Iconomi","ICN",HomePage.binance,"011");
                HomePage.binance.addCoin(iconomiBinance);
                Coin iconomiKraken = new Coin("Iconomi","XICN",HomePage.kraken,"011");
                HomePage.kraken.addCoin(iconomiKraken);
                HomePage.listOfCurrencies.add("Iconomi");
                break;

            case R.id.bancorButton:
                Coin bancorBittrex = new Coin("Bancor","BNT",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(bancorBittrex);
                Coin bancorBinance = new Coin("Bancor","BNT",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(bancorBinance);
                Coin bancorHitBTC = new Coin("Bancor","BNT",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(bancorHitBTC);
                Coin bancorOKEX = new Coin("Bancor","BNT",
                        HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(bancorOKEX);
                HomePage.listOfCurrencies.add("Bancor");
                break;

            case R.id.madesafecoinButton:
                //Coin madesafecoinBittrex = new Coin("MaidSafeCoin",
                //        "MAID",HomePage.bittrex,"010");
                //HomePage.bittrex.addCoin(madesafecoinBittrex);
                Coin madesafecoinPoloniex = new Coin("MaidSafeCoin","MAID",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(madesafecoinPoloniex);
                Coin madesafecoinHitBTC = new Coin("MaidSafeCoin","MAID",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(madesafecoinHitBTC);
                HomePage.listOfCurrencies.add("MaidSafeCoin");
                break;

            case R.id.chainlinkButton:
                Coin chainlinkBinance = new Coin("ChainLink","LINK",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(chainlinkBinance);
                Coin chainlinkOKEX = new Coin("ChainLink","link",
                        HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(chainlinkOKEX);
                Coin chainlinkHuobi = new Coin("ChainLink","link",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(chainlinkHuobi);
                HomePage.listOfCurrencies.add("ChainLink");
                break;

            case R.id.emercoinButton:
                Coin emercoinBittrex = new Coin("Emercoin","EMC",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(emercoinBittrex);
                Coin emercoinHitBTC = new Coin("Emercoin","EMC",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(emercoinHitBTC);
                HomePage.listOfCurrencies.add("Emercoin");
                break;

            case R.id.ethlendButton:
                Coin ethlendBinance = new Coin("ETHLend","LEND",HomePage.binance,"011");
                HomePage.binance.addCoin(ethlendBinance);
                Coin ethlendOKEX = new Coin("ETHLend","LEND",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(ethlendOKEX);
                Coin ethlendHitBTC = new Coin("ETHLend","LEND",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(ethlendHitBTC);
                HomePage.listOfCurrencies.add("ETHLend");
                break;

            case R.id.iexecrlcButton:
                Coin iexecrlcBittrex = new Coin("iExec RLC","RLC",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(iexecrlcBittrex);
                Coin iexecrlcBinance = new Coin("iExec RLC","RLC",HomePage.binance,"011");
                HomePage.binance.addCoin(iexecrlcBinance);
                Coin iexecrlcBitfinex = new Coin("iExec RLC","RLC",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(iexecrlcBitfinex);
                HomePage.listOfCurrencies.add("iExec RLC");
                break;

            case R.id.monacoButton:
                Coin monacoBittrex = new Coin("Monaco","MCO",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(monacoBittrex);
                Coin monacoBinance = new Coin("Monaco","MCO",HomePage.binance,"011");
                HomePage.binance.addCoin(monacoBinance);
                Coin monacoOKEX = new Coin("Monaco","MCO",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(monacoOKEX);
                Coin monacoBitZ = new Coin("Monaco","mco",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(monacoBitZ);
                Coin monacoHuobi = new Coin("Monaco","mco",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(monacoHuobi);
                HomePage.listOfCurrencies.add("Monaco");
                break;

            case R.id.blockvButton:
                Coin blockvOKEX = new Coin("BLOCKv","VEE",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(blockvOKEX);
                //HomePage.listOfCurrencies.add("BLOCKv");
                break;

            case R.id.bluzelleButton:
                Coin bluzelleBinance = new Coin("Bluzelle","BLZ",HomePage.binance,"011");
                HomePage.binance.addCoin(bluzelleBinance);
                //HomePage.listOfCurrencies.add("Bluzelle");
                break;

            case R.id.ripiocreditnetworkButton:
                Coin ripioCreditNetworkBittrex = new Coin("Ripio Credit Network",
                        "RCN",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(ripioCreditNetworkBittrex);
                Coin ripioCreditNetworkBinance = new Coin("Ripio Credit Network",
                        "RCN",HomePage.binance,"011");
                HomePage.binance.addCoin(ripioCreditNetworkBinance);
                Coin ripioCreditNetworkBitfinex = new Coin("Ripio Credit Network",
                        "rcn",HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(ripioCreditNetworkBitfinex);
                Coin ripioCreditNetworkOKEX = new Coin("Ripio Credit Network",
                        "rcn",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(ripioCreditNetworkOKEX);
                Coin ripioCreditNetworkHuobi = new Coin("Ripio Credit Network","rcn",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(ripioCreditNetworkHuobi);
                HomePage.listOfCurrencies.add("Ripio Credit Network");
                break;

            case R.id.iotchainButton:
                Coin iotchainOKEX = new Coin("IoT Chain","ITC",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(iotchainOKEX);
                Coin iotchainHuobi = new Coin("IoT Chain","itc",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(iotchainHuobi);
                HomePage.listOfCurrencies.add("IoT Chain");
                break;

            case R.id.airswapButton:
                Coin airswapBinance = new Coin("AirSwap","AST",HomePage.binance,"011");
                HomePage.binance.addCoin(airswapBinance);
                Coin airswapHuobi = new Coin("AirSwap","ast",
                        HomePage.huobi,"010");
                HomePage.huobi.addCoin(airswapHuobi);
                HomePage.listOfCurrencies.add("AirSwap");
                break;

            case R.id.counterpartyButton:
                Coin counterpartyBittrex = new Coin("Counterparty","XCP",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(counterpartyBittrex);
                Coin counterpartyPoloniex = new Coin("Counterparty","XCP",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(counterpartyPoloniex);
                HomePage.listOfCurrencies.add("Counterparty");
                break;

            case R.id.einsteiniumButton:
                Coin einsteiniumBittrex = new Coin("Einsteinium","EMC2",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(einsteiniumBittrex);
                Coin einsteiniumPoloniex = new Coin("Einsteinium","EMC2",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(einsteiniumPoloniex);
                HomePage.listOfCurrencies.add("Einsteinium");
                break;

            case R.id.peercoinButton:
                Coin peercoinBittrex = new Coin("Peercoin","PPC",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(peercoinBittrex);
                Coin peercoinBitZ = new Coin("Peercoin","ppc",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(peercoinBitZ);
                Coin peercoinPoloniex = new Coin("Peercoin","PPC",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(peercoinPoloniex);
                HomePage.listOfCurrencies.add("Peercoin");
                break;

            case R.id.vibeButton:
                Coin vibeBinance = new Coin("VIBE","VIBE",HomePage.binance,"011");
                HomePage.binance.addCoin(vibeBinance);
                Coin vibeHitBTC = new Coin("VIBE","VIBE",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(vibeHitBTC);
                HomePage.listOfCurrencies.add("VIBE");
                break;

            case R.id.cybermilesButton:
                Coin cybermilesBinance = new Coin("CyberMiles","CMT",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(cybermilesBinance);
                Coin cybermilesOKEX = new Coin("CyberMiles","CMT",HomePage.OKEX,
                        "110");
                HomePage.OKEX.addCoin(cybermilesOKEX);
                Coin cybermilesHuobi = new Coin("CyberMiles","cmt",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(cybermilesHuobi);
                HomePage.listOfCurrencies.add("CyberMiles");
                break;

            case R.id.adexButton:
                Coin adexBittrex = new Coin("AdEx","ADX",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(adexBittrex);
                Coin adexbinance = new Coin("AdEx","ADX",HomePage.binance,"011");
                HomePage.binance.addCoin(adexbinance);
                Coin adexHitBTC = new Coin("AdEx","",HomePage.hitBTC,"001");
                HomePage.hitBTC.addCoin(adexHitBTC);
                HomePage.listOfCurrencies.add("AdEx");
                break;

            case R.id.singulardtvButton:
                Coin singulardtvBinance = new Coin("SingularDTV","SNGLS",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(singulardtvBinance);
                Coin singulardtvBitfinex = new Coin("SingularDTV","SNG",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(singulardtvBitfinex);
                Coin singulardtvOKEX = new Coin("SingularDTV","sngls",
                        HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(singulardtvOKEX);
                HomePage.listOfCurrencies.add("SingularDTV");
                break;

            case R.id.metalButton:
                Coin metalBinance = new Coin("Metal","MTL",HomePage.binance,"011");
                HomePage.binance.addCoin(metalBinance);
                Coin metalOKEX = new Coin("Metal","mtl",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(metalOKEX);
                Coin metalHuobi = new Coin("Metal","mtl",
                        HomePage.huobi,"010");
                HomePage.huobi.addCoin(metalHuobi);
                HomePage.listOfCurrencies.add("Metal");
                break;

            case R.id.simpletokenButton:
                Coin simpletokenBinance = new Coin("Simple Token","OST",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(simpletokenBinance);
                Coin simpletokenOKEX = new Coin("Simple Token","OST",
                        HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(simpletokenOKEX);
                Coin simpletokenHuobi = new Coin("Simple Token","ost",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(simpletokenHuobi);
                HomePage.listOfCurrencies.add("Simple Token");
                break;

            case R.id.eidooButton:
                Coin eidooBinance = new Coin("Eidoo","EDO",HomePage.binance,"011");
                HomePage.binance.addCoin(eidooBinance);
                Coin eidooBitfinex = new Coin("Eidoo","EDO",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(eidooBitfinex);
                Coin eidooHitBTC = new Coin("Eidoo","EDO",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(eidooHitBTC);
                Coin eidooOKEX = new Coin("Eidoo","EDO",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(eidooOKEX);
                HomePage.listOfCurrencies.add("Eidoo");
                break;

            case R.id.thetatokenButton:
                Coin thetatokenOKEX = new Coin("Theta Token","THETA",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(thetatokenOKEX);
                Coin thetatokenHuobi = new Coin("Theta Token","theta",
                        HomePage.huobi,"111");
                HomePage.huobi.addCoin(thetatokenHuobi);
                HomePage.listOfCurrencies.add("Theta Token");
                break;

            case R.id.metaverseetpButton:
                Coin metaverseBitfinex = new Coin("Metaverse","ETP",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(metaverseBitfinex);
                Coin metaverseBitZ = new Coin("Metaverse","etp",
                        HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(metaverseBitZ);
                Coin metaverseHitBTC = new Coin("Metaverse","ETP",
                        HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(metaverseHitBTC);
                HomePage.listOfCurrencies.add("Metaverse");
                break;

            case R.id.aeternityButton:
                Coin aeternityBinance = new Coin("Aeternity","AE",HomePage.binance,"011");
                HomePage.binance.addCoin(aeternityBinance);
                Coin aeternityHitBTC = new Coin("Aeternity","AE",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(aeternityHitBTC);
                HomePage.listOfCurrencies.add("Aeternity");
                break;

            case R.id.zilliqaButton:
                Coin zilliqaHuobi = new Coin("Zilliqa","zil",HomePage.huobi,"111");
                HomePage.huobi.addCoin(zilliqaHuobi);
                //HomePage.listOfCurrencies.add("Zilliqa");
                break;

            case R.id.bytomButton:
                Coin bytomOKEX = new Coin("Bytom","BTM",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(bytomOKEX);
                Coin bytomHuobi = new Coin("Bytom","btm",HomePage.huobi,"011");
                HomePage.huobi.addCoin(bytomHuobi);
                HomePage.listOfCurrencies.add("Bytom");
                break;

            case R.id.dentacoinButton:
                Coin dentacoinHitBTC = new Coin("Dentacoin","DCN",HomePage.hitBTC,"101");
                HomePage.hitBTC.addCoin(dentacoinHitBTC);
                //HomePage.listOfCurrencies.add("Dentacoin");
                break;

            case R.id.qashButton:
                Coin qashHuobi = new Coin("QASH","qash",HomePage.huobi,"011");
                HomePage.huobi.addCoin(qashHuobi);
                Coin qashBitfinex = new Coin("QASH","QSH",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(qashBitfinex);
                HomePage.listOfCurrencies.add("QASH");
                break;

            case R.id.bitcoreButton:
                Coin bitcoreBitZ = new Coin("Bitcore","btx",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(bitcoreBitZ);
                Coin bitcoreHitBTC = new Coin("Bitcore","BTX",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(bitcoreHitBTC);
                HomePage.listOfCurrencies.add("Bitcore");
                break;

            case R.id.aionButton:
                Coin aionBinance = new Coin("Aion","AION",HomePage.binance,"011");
                HomePage.binance.addCoin(aionBinance);
                break;

            case R.id.requestnetworkButton:
                Coin requestnetworkBinance = new Coin("Request Network","REQ",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(requestnetworkBinance);
                Coin requestnetworkHuobi = new Coin("Request Network","req",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(requestnetworkHuobi);
                HomePage.listOfCurrencies.add("Request Network");
                break;

            case R.id.tenxButton:
                Coin tenxBittrex = new Coin("TenX","PAY",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(tenxBittrex);
                Coin tenxHuobi = new Coin("TenX","pay",HomePage.huobi,"011");
                HomePage.huobi.addCoin(tenxHuobi);
                HomePage.listOfCurrencies.add("TenX");
                break;

            case R.id.quantstampButton:
                Coin quantstampBinance = new Coin("Quantstamp","QSP",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(quantstampBinance);
                Coin quantstampHuobi = new Coin("Quantstamp","qsp",HomePage.huobi,"011");
                HomePage.huobi.addCoin(quantstampHuobi);
                HomePage.listOfCurrencies.add("Quantstamp");
                break;

            case R.id.appcoinsButton:
                Coin appcoinsBinance = new Coin("AppCoins","APPC",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(appcoinsBinance);
                Coin appcoinsHuobi = new Coin("AppCoins","appc",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(appcoinsHuobi);
                HomePage.listOfCurrencies.add("AppCoins");
                break;

            case R.id.santimentButton:
                Coin santimentBitfinex = new Coin("Santiment","SAN",HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(santimentBitfinex);
                Coin santimentOKEX = new Coin("Santiment","SAN",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(santimentOKEX);
                HomePage.listOfCurrencies.add("Santiment");
                break;

            case R.id.po_etButton:
                Coin poetBinance = new Coin("Po.et","POE",HomePage.binance,"011");
                HomePage.binance.addCoin(poetBinance);
                Coin poetOKEX = new Coin("Po.et","POE",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(poetOKEX);
                Coin poetHitBTC = new Coin("Po.et","POE",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(poetHitBTC);
                HomePage.listOfCurrencies.add("Po.et");
                break;

            case R.id.substratumButton:
                Coin substratumBinance = new Coin("Substratum","SUB",HomePage.binance,"011");
                HomePage.binance.addCoin(substratumBinance);
                Coin substratumHitBTC = new Coin("Substratum","SUB",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(substratumHitBTC);
                HomePage.listOfCurrencies.add("Substratum");
                break;

            case R.id.raidenButton:
                Coin raidenBinance = new Coin("Raiden","RDN",HomePage.binance,"011");
                HomePage.binance.addCoin(raidenBinance);
                Coin raidenHuobi = new Coin("Raiden","rdn",HomePage.huobi,"011");
                HomePage.huobi.addCoin(raidenHuobi);
                HomePage.listOfCurrencies.add("Raiden");
                break;

            case R.id.zencashButton:
                Coin zencashBittrex = new Coin("ZenCash","ZEN",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(zencashBittrex);
                Coin zencashOKEX = new Coin("ZenCash","ZEN",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(zencashOKEX);
                HomePage.listOfCurrencies.add("ZenCash");
                break;

            case R.id.redpulseButton:
                Coin redpulseBinance = new Coin("Red Pulse","RPX",HomePage.binance,"011");
                HomePage.binance.addCoin(redpulseBinance);
                Coin redpulseHuobi = new Coin("Red Pulse","rpx",HomePage.huobi,"010");
                HomePage.huobi.addCoin(redpulseHuobi);
                HomePage.listOfCurrencies.add("Red Pulse");
                break;

            case R.id.enjincoinButton:
                Coin enjincoinBinance = new Coin("Enjin Coin","ENJ",HomePage.binance,"011");
                HomePage.binance.addCoin(enjincoinBinance);
                break;

            case R.id.aragonButton:
                Coin aragonBittrex = new Coin("Aragon","ANT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(aragonBittrex);
                break;

            case R.id.deepbrainchainButton:
                Coin deepbrainchainHuobi = new Coin("DeepBrain Chain","dbc",HomePage.huobi,"011");
                HomePage.huobi.addCoin(deepbrainchainHuobi);
                break;

            case R.id.sirinlabsButton:
                Coin sirinlabsBittrex = new Coin("SIRIN LABS","SRN",
                        HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(sirinlabsBittrex);
                Coin sirinlabsHuobi = new Coin("SIRIN LABS","srn",
                        HomePage.huobi,"011");
                HomePage.huobi.addCoin(sirinlabsHuobi);
                HomePage.listOfCurrencies.add("SIRIN LABS");
                break;

            case R.id.medisharesButton:
                Coin medisharesHuobi = new Coin("MediShares","mds",HomePage.huobi,"011");
                HomePage.huobi.addCoin(medisharesHuobi);
                break;

            case R.id.giftoButton:
                Coin giftoBinance = new Coin("Gifto","GTO",HomePage.binance,"011");
                HomePage.binance.addCoin(giftoBinance);
                Coin giftoOKEX = new Coin("Gifto","GTO",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(giftoOKEX);
                HomePage.listOfCurrencies.add("Gifto");
                break;

            case R.id.wabiButton:
                Coin wabiBinance = new Coin("Wabi","WABI",HomePage.binance,"011");
                HomePage.binance.addCoin(wabiBinance);
                break;

            case R.id.crypto20Button:
                Coin crypto20HitBTC = new Coin("CRYPTO20","C20",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(crypto20HitBTC);
                break;

            case R.id.ambrosusButton:
                Coin ambrosusBinance = new Coin("Ambrosus","AMB",HomePage.binance,"011");
                HomePage.binance.addCoin(ambrosusBinance);
                break;

            case R.id.insecosystemButton:
                Coin insecosystemBinance = new Coin("INS Ecosystem","INS",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(insecosystemBinance);
                Coin insecosystemOKEX = new Coin("INS Ecosystem","INS",HomePage.OKEX,
                        "111");
                HomePage.OKEX.addCoin(insecosystemOKEX);
                HomePage.listOfCurrencies.add("INS Ecosystem");
                break;

            case R.id.streamrdataButton:
                Coin streamrdataBitfinex = new Coin("Streamr DATAcoin","DATA",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(streamrdataBitfinex);
                Coin streamrdataHitBTC = new Coin("Streamr DATAcoin","DATA",
                        HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(streamrdataHitBTC);
                HomePage.listOfCurrencies.add("Streamr DATAcoin");
                break;

            case R.id.sonmButton:
                Coin sonmBinance = new Coin("SONM","SNM",HomePage.binance,"011");
                HomePage.binance.addCoin(sonmBinance);
                break;

            case R.id.viacoinButton:
                Coin viacoinBinance = new Coin("Viacoin","VIA",HomePage.binance,"011");
                HomePage.binance.addCoin(viacoinBinance);
                Coin viacoinBittrex = new Coin("Viacoin","VIA",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(viacoinBittrex);
                Coin viacoinPoloniex = new Coin("Viacoin","VIA",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(viacoinPoloniex);
                HomePage.listOfCurrencies.add("Viacoin");
                break;

            case R.id.genesisvisionButton:
                Coin genesisvisionBinance = new Coin("Genesis Vision","GVT",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(genesisvisionBinance);
                break;

            case R.id.melonButton:
                Coin melonBittrex = new Coin("Melon","MLN",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(melonBittrex);
                Coin melonKraken = new Coin("Melon","XMLN",HomePage.kraken,"011");
                HomePage.kraken.addCoin(melonKraken);
                HomePage.listOfCurrencies.add("Melon");
                break;

            case R.id.spankchainButton:
                Coin spankchainBitfinex = new Coin("SpankChain","SPK",
                        HomePage.bitfinex,"111");
                HomePage.bitfinex.addCoin(spankchainBitfinex);
                break;

            case R.id.breadButton:
                Coin breadBinance = new Coin("Bread","BRD",HomePage.binance,"011");
                HomePage.binance.addCoin(breadBinance);
                Coin breadOKEX = new Coin("Bread","BRD",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(breadOKEX);
                HomePage.listOfCurrencies.add("Bread");
                break;

            case R.id.nulsButton:
                Coin nulsBinance = new Coin("Nuls","NULS",HomePage.binance,"011");
                HomePage.binance.addCoin(nulsBinance);
                Coin nulsBitZ = new Coin("Nuls","nuls",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(nulsBitZ);
                HomePage.listOfCurrencies.add("Nuls");
                break;

            case R.id.utrustButton:
                Coin utrustHuobi = new Coin("UTRUST","utk",HomePage.huobi,"011");
                HomePage.huobi.addCoin(utrustHuobi);
                Coin utrustHitBTC = new Coin("UTRUST","UTK",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(utrustHitBTC);
                Coin utrustOKEX = new Coin("UTRUST","UTK",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(utrustOKEX);
                HomePage.listOfCurrencies.add("UTRUST");
                break;

            case R.id.triggersButton:
                Coin triggersBinance = new Coin("Triggers","TRIG",HomePage.binance,"011");
                HomePage.binance.addCoin(triggersBinance);
                break;

            case R.id.etherpartyButton:
                Coin etherpartyBinance = new Coin("Etherparty","FUEL",HomePage.binance,"011");
                HomePage.binance.addCoin(etherpartyBinance);
                break;

            case R.id.modumButton:
                Coin modumBinance = new Coin("Modum","MOD",HomePage.binance,"011");
                HomePage.binance.addCoin(modumBinance);
                break;

            case R.id.wingsButton:
                Coin wingsBinance = new Coin("Wings","WINGS",HomePage.binance,"011");
                HomePage.binance.addCoin(wingsBinance);
                Coin wingsBittrex = new Coin("Wings","WINGS",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(wingsBittrex);
                HomePage.listOfCurrencies.add("Wings");
                break;

            case R.id.tierionButton:
                Coin tierionBinance = new Coin("Tierion","TNT",HomePage.binance,"011");
                HomePage.binance.addCoin(tierionBinance);
                Coin tierionHitBTC = new Coin("Tierion","TNT",HomePage.hitBTC,"101");
                HomePage.hitBTC.addCoin(tierionHitBTC);
                Coin tierionHuobi = new Coin("Tierion","tnt",HomePage.huobi,"011");
                HomePage.huobi.addCoin(tierionHuobi);
                HomePage.listOfCurrencies.add("Tierion");
                break;

            case R.id.wepowerButton:
                Coin wepowerHuobi = new Coin("WePower","wpr",HomePage.huobi,"011");
                HomePage.huobi.addCoin(wepowerHuobi);
                break;

            case R.id.burstButton:
                Coin burstBittrex = new Coin("Burst","BURST",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(burstBittrex);
                Coin burstPoloniex = new Coin("Burst","BURST",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(burstPoloniex);
                HomePage.listOfCurrencies.add("Burst");
                break;

            case R.id.allsportsButton:
                Coin allsportsHuobi = new Coin("All Sports","soc",HomePage.huobi,"011");
                HomePage.huobi.addCoin(allsportsHuobi);
                Coin allsportsOKEX = new Coin("All Sports","SOC",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(allsportsOKEX);
                HomePage.listOfCurrencies.add("All Sports");
                break;

            case R.id.district0xButton:
                Coin district0xBinance = new Coin("district0x","DNT",HomePage.binance,"011");
                HomePage.binance.addCoin(district0xBinance);
                Coin district0xBittrex = new Coin("district0x","DNT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(district0xBittrex);
                Coin district0xOKEX = new Coin("district0x","DNT",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(district0xOKEX);
                HomePage.listOfCurrencies.add("district0x");
                break;

            case R.id.internetnodetokenButton:
                Coin internetnodetokenOKEX = new Coin("Internet Node Token","INT",
                        HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(internetnodetokenOKEX);
                break;

            case R.id.coindashButton:
                Coin coindashBinance = new Coin("CoinDash","CDT",HomePage.binance,"011");
                HomePage.binance.addCoin(coindashBinance);
                break;

            case R.id.cossButton:
                Coin cossHitBTC = new Coin("COSS","COSS",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(cossHitBTC);
                break;

            case R.id.lbrycreditsButton:
                Coin lbrycreditsBittrex = new Coin("LBRY Credits","LBC",
                        HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(lbrycreditsBittrex);
                Coin lbrycreditsPoloniex = new Coin("LBRY Credits","LBC",
                        HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(lbrycreditsPoloniex);
                HomePage.listOfCurrencies.add("LBRY Credits");
                break;

            case R.id.unikoingoldButton:
                Coin unikoingoBittrex = new Coin("Unikoin Gold","UKG",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(unikoingoBittrex);
                Coin unikoingoOKEX = new Coin("Unikoin Gold","UKG",HomePage.OKEX,"110");
                HomePage.OKEX.addCoin(unikoingoOKEX);
                HomePage.listOfCurrencies.add("Unikoin Gold");
                break;

            case R.id.steemdollarsButton:
                Coin steemdollarsBittrex = new Coin("Steem Dollars","SBD",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(steemdollarsBittrex);
                Coin steemdollarsPoloniex = new Coin("Steem Dollars","SBD",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(steemdollarsPoloniex);
                HomePage.listOfCurrencies.add("Steem Dollars");
                break;

            case R.id.nagaButton:
                Coin nagaHitBTC = new Coin("NAGA","NGC",HomePage.hitBTC,"111");
                HomePage.hitBTC.addCoin(nagaHitBTC);
                Coin nagaOKEX = new Coin("NAGA","NGC",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(nagaOKEX);
                HomePage.listOfCurrencies.add("NAGA");
                break;

            case R.id.delphyButton:
                Coin delphyOKEX = new Coin("Delphy","DPY",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(delphyOKEX);
                break;

            case R.id.aeonButton:
                Coin aeonBittrex = new Coin("Aeon","AEON",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(aeonBittrex);
                Coin aeonHitBTC = new Coin("Aeon","AEON",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(aeonHitBTC);
                HomePage.listOfCurrencies.add("Aeon");
                break;

            case R.id.indahashButton:
                Coin indahashHitBTC = new Coin("indaHash","IDH",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(indahashHitBTC);
                break;

            case R.id.lunyrButton:
                Coin lunyrBittrex = new Coin("Lunyr","LUN",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(lunyrBittrex);
                Coin lunyrBinance = new Coin("Lunyr","LUN",HomePage.binance,"011");
                HomePage.binance.addCoin(lunyrBinance);
                Coin lunyrHuobi = new Coin("Lunyr","lun",HomePage.huobi,"011");
                HomePage.huobi.addCoin(lunyrHuobi);
                HomePage.listOfCurrencies.add("Lunyr");
                break;

            case R.id.firstbloodButton:
                //Coin firstbloodBittrex = new Coin("FirstBlood","1ST",
                //        HomePage.bittrex,"010");
                //HomePage.bittrex.addCoin(firstbloodBittrex);
                Coin firstbloodOKEX = new Coin("FirstBlood","1ST",
                        HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(firstbloodOKEX);
                //HomePage.listOfCurrencies.add("FirstBlood");
                break;

            case R.id.viberateButton:
                Coin viberateBinance = new Coin("Viberate","VIB",HomePage.binance,"011");
                HomePage.binance.addCoin(viberateBinance);
                Coin viberateBittrex = new Coin("Viberate","VIB",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(viberateBittrex);
                Coin viberateHitBTC = new Coin("Viberate","VIB",HomePage.hitBTC,"100");
                HomePage.hitBTC.addCoin(viberateHitBTC);
                Coin viberateOKEX = new Coin("Viberate","VIB",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(viberateOKEX);
                HomePage.listOfCurrencies.add("Viberate");
                break;

            case R.id.datumButton:
                Coin datumHuobi = new Coin("Datum","dat",HomePage.huobi,"011");
                HomePage.huobi.addCoin(datumHuobi);
                Coin datumOKEX = new Coin("Datum","DAT",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(datumOKEX);
                HomePage.listOfCurrencies.add("Datum");
                break;

            case R.id.odysseyButton:
                Coin odysseyBitZ = new Coin("Odyssey","ocn",HomePage.bitZ,"001");
                HomePage.bitZ.addCoin(odysseyBitZ);
                Coin odysseyHuobi = new Coin("Odyssey","ocn",HomePage.huobi,"011");
                HomePage.huobi.addCoin(odysseyHuobi);
                HomePage.listOfCurrencies.add("Odyssey");
                break;

            case R.id.inkButton:
                Coin inkBitZ = new Coin("Ink","ink",HomePage.bitZ,"011");
                HomePage.bitZ.addCoin(inkBitZ);
                break;

            case R.id.potcoinButton:
                Coin potcoinBittrex = new Coin("PotCoin","POT",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(potcoinBittrex);
                Coin potcoinPoloniex = new Coin("PotCoin","POT",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(potcoinPoloniex);
                HomePage.listOfCurrencies.add("PotCoin");
                break;

            case R.id.swftcoinButton:
                Coin swftcoinOKEX = new Coin("SwftCoin","SWFTC",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(swftcoinOKEX);
                Coin swftcoinHuobi = new Coin("SwftCoin","swftc",HomePage.huobi,"011");
                HomePage.huobi.addCoin(swftcoinHuobi);
                HomePage.listOfCurrencies.add("SwftCoin");
                break;

            case R.id.humaniqButton:
                Coin humaniqBittrex = new Coin("Humaniq","HMQ",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(humaniqBittrex);
                break;

            case R.id.monethaButton:
                Coin monethaBinance = new Coin("Monetha","MTH",HomePage.binance,"011");
                HomePage.binance.addCoin(monethaBinance);
                break;

            case R.id.agrelloButton:
                Coin agrelloBinance = new Coin("Agrello","DLT",HomePage.binance,"011");
                HomePage.binance.addCoin(agrelloBinance);
                break;

            case R.id.yoyowButton:
                Coin yoyowBitfinex = new Coin("YOYOW","YYW",HomePage.bitfinex,"110");
                HomePage.bitfinex.addCoin(yoyowBitfinex);
                Coin yoyowBinance = new Coin("YOYOW","YOYO",HomePage.binance,"011");
                HomePage.binance.addCoin(yoyowBinance);
                Coin yoyoOKEX = new Coin("YOYOW","YOYO",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(yoyoOKEX);
                HomePage.listOfCurrencies.add("YOYOW");
                break;

            case R.id.worldcoreButton:
                Coin worldcoreOKEX = new Coin("Worldcore","WRC",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(worldcoreOKEX);
                Coin worldcoreHitBTC = new Coin("Worldcore","WRC",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(worldcoreHitBTC);
                HomePage.listOfCurrencies.add("Worldcore");
                break;

            case R.id.selfkeyButton:
                Coin selfkeyOKEX = new Coin("Selfkey","KEY",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(selfkeyOKEX);
                break;

            case R.id.blocktixButton:
                Coin blocktixBittrex = new Coin("Blocktix","TIX",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(blocktixBittrex);
                break;

            case R.id.everexButton:
                Coin everexHuobi = new Coin("Everex","evx",HomePage.huobi,"011");
                HomePage.huobi.addCoin(everexHuobi);
                Coin everexBinance = new Coin("Everex","EVX",HomePage.binance,"011");
                HomePage.binance.addCoin(everexBinance);
                HomePage.listOfCurrencies.add("Everex");
                break;

            case R.id.suncontractButton:
                Coin suncontractOKEX = new Coin("SunContract","SNC",HomePage.OKEX,"010");
                HomePage.OKEX.addCoin(suncontractOKEX);
                Coin suncontractHitBTC = new Coin("SunContract","SNC",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(suncontractHitBTC);
                Coin suncontractHuobi = new Coin("SunContract","snc",HomePage.huobi,"011");
                HomePage.huobi.addCoin(suncontractHuobi);
                HomePage.listOfCurrencies.add("SunContract");
                break;

            case R.id.tradetokenButton:
                Coin tradetokenOKEX = new Coin("Trade Token","TIO",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(tradetokenOKEX);
                break;

            case R.id.latokenButton:
                Coin latokenHitBTC = new Coin("LATOKEN","LA",HomePage.hitBTC,"001");
                HomePage.hitBTC.addCoin(latokenHitBTC);
                Coin latokenOKEX = new Coin("LATOKEN","LA",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(latokenOKEX);
                HomePage.listOfCurrencies.add("LATOKEN");
                break;

            case R.id.blackcoinButton:
                Coin blackcoinBittrex = new Coin("BlackCoin","BLK",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(blackcoinBittrex);
                Coin blackcoinPoloniex = new Coin("BlackCoin","BLK",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(blackcoinPoloniex);
                HomePage.listOfCurrencies.add("BlackCoin");
                break;

            case R.id.ixledgerButton:
                Coin ixledgerHitBTC = new Coin("iXledger","IXT",HomePage.hitBTC,"011");
                HomePage.hitBTC.addCoin(ixledgerHitBTC);
                break;

            case R.id.olympuslabsButton:
                Coin olympuslabsOKEX = new Coin("Olympus Labs","MOT",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(olympuslabsOKEX);
                break;

            case R.id.hydroprotocolButton:
                Coin hydroprotocolOKEX = new Coin("Hydro Protocol","HOT",HomePage.OKEX,"111");
                HomePage.OKEX.addCoin(hydroprotocolOKEX);
                break;

            case R.id.moedaloyaltypointsButton:
                Coin moedaloyaltypointsBinance = new Coin("Moeda Loyalty Points","MDA",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(moedaloyaltypointsBinance);
                break;

            case R.id.oaxButton:
                Coin oaxBinance = new Coin("OAX","OAX",HomePage.binance, "011");
                HomePage.binance.addCoin(oaxBinance);
                break;

            case R.id.stoxButton:
                Coin stoxHitBTC = new Coin("Stox","STX",HomePage.hitBTC, "111");
                HomePage.hitBTC.addCoin(stoxHitBTC);
                break;

            case R.id.qunqunButton:
                Coin qunqunHuobi = new Coin("Qunqun","qun",HomePage.huobi,"011");
                HomePage.huobi.addCoin(qunqunHuobi);
                Coin qunqunOKEX = new Coin("Qunqun","QUN",HomePage.OKEX,"011");
                HomePage.OKEX.addCoin(qunqunOKEX);
                HomePage.listOfCurrencies.add("Qunqun");
                break;

            case R.id.blockmasoncreditButton:
                Coin blockmasonBinance = new Coin("Block Mason","BCPT",
                        HomePage.binance,"011");
                HomePage.binance.addCoin(blockmasonBinance);
                Coin blockmasonBittrex = new Coin("Block Mason","BCPT",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(blockmasonBittrex);
                HomePage.listOfCurrencies.add("Block Mason");
                break;

            case R.id.stkButton:
                Coin stkHuobi = new Coin("STK","stk",HomePage.huobi,"011");
                HomePage.huobi.addCoin(stkHuobi);
                break;

            case R.id.aeronButton:
                Coin aeronBinance = new Coin("Aeron","ARN",HomePage.binance,"011");
                HomePage.binance.addCoin(aeronBinance);
                Coin aeronBitZ = new Coin("Aeron","arn",HomePage.bitZ,"010");
                HomePage.bitZ.addCoin(aeronBitZ);
                Coin aeronHitBTC = new Coin("Aeron","ARN",HomePage.hitBTC,"010");
                HomePage.hitBTC.addCoin(aeronHitBTC);
                HomePage.listOfCurrencies.add("Aeron");
                break;

            case R.id.syneroButton:
                Coin syneroBittrex = new Coin("Synero","AMP",HomePage.bittrex,"010");
                HomePage.bittrex.addCoin(syneroBittrex);
                Coin syneroPoloniex = new Coin("Synero","AMP",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(syneroPoloniex);
                HomePage.listOfCurrencies.add("Synero");
                break;

            case R.id.matchpoolButton:
                Coin matchpoolBittrex = new Coin("Matchpool","GUP",HomePage.bittrex,"011");
                HomePage.bittrex.addCoin(matchpoolBittrex);
                break;

            case R.id.radiumButton:
                Coin radiumBittrex = new Coin("Radium","RADS",HomePage.bittrex, "010");
                HomePage.bittrex.addCoin(radiumBittrex);
                Coin radiumPoloniex = new Coin("Radium","RADS",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(radiumPoloniex);
                HomePage.listOfCurrencies.add("Radium");
                break;

            case R.id.vericoinButton:
                Coin vericoinBittrex = new Coin("Vericoin","VRC",HomePage.bittrex, "010");
                HomePage.bittrex.addCoin(vericoinBittrex);
                Coin vericoinPoloniex = new Coin("Vericoin","VRC",HomePage.poloniex,"010");
                HomePage.poloniex.addCoin(vericoinPoloniex);
                HomePage.listOfCurrencies.add("Vericoin");
                break;

            case R.id.propyButton:
                Coin propyHuobi = new Coin("Propy","propy", HomePage.huobi, "011");
                HomePage.huobi.addCoin(propyHuobi);
                break;







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
        int numCurrencies = allCurrenciesButtons.size();
        int counter = 0;
        try {
            FileInputStream fileInputStream = openFileInput("CurrenciesInformation");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //while more lines
            //System.out.println("Size of allExchangesButton is: " + numExchanges);
            StringBuilder message = new StringBuilder();
            int data = 1;
            while(counter < numCurrencies && data != -1){
                data = bufferedReader.read();
                message.append((char) data);
                //if word is true, set exchange value to true
                if(message.length()==4 && message.charAt(0) == 't'){
                    allCurrenciesButtons.get(counter).setChecked(true); //why is above statement necessary/ why doesn't
                    //the text automatically change once it's checked
                    message.delete(0, 4);
                    //System.out.println("Went" + allCurrenciesButtons.get(counter).isChecked() + counter);
                    counter++;

                }
                else if (message.length() == 5 && message.charAt(0) == 'f'){
                    //if word is false, set exchange value to false
                    allCurrenciesButtons.get(counter).setChecked(false);
                    message.delete(0, 5);
                    //System.out.println("went false" + counter);
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
        //hasSubscription wasn't showing the correct value immediately, thus only make this condition after some time has elapsed and hasSubscription working correctly
        if((!hasSubscription && HomePage.isCreatedCryptocurrencies)){
            int numOn = 0;
            for(int i = 2; i < allCurrenciesButtons.size(); i++){
                if(allCurrenciesButtons.get(i).isChecked()){
                    numOn++;
                }
                if(numOn == MAX_NUMBER_ALLOWED){
                    for (int j = MAX_NUMBER_ALLOWED; j < allCurrenciesButtons.size(); j++){
                        allCurrenciesButtons.get(j).setChecked(false);
                    }
                    selectAllCurrenciesButton.setText("Off");
                    break;
                }
            }
        }
        else{
            selectAllCurrenciesButton.setText("On");
            for(ToggleButton button: allCurrenciesButtons){
                if(!button.isChecked()){
                    selectAllCurrenciesButton.setText("Off");
                    break;
                }
            }
        }
        startingValuesForButtons = new boolean[allCurrenciesButtons.size()];
        for(int i = 0; i < allCurrenciesButtons.size(); i++){
            startingValuesForButtons[i] = allCurrenciesButtons.get(i).isChecked();
        }
    }

    private boolean anyNewCoinsAddedOrDeleted(){
        for(int i = 0; i < startingValuesForButtons.length; i++){
            if(allCurrenciesButtons.get(i).isChecked() != startingValuesForButtons[i]){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        //Toast.makeText(this, "Thank You For Purchasing A Subscription", Toast.LENGTH_LONG).show();
        Cryptocurrencies.hasSubscription = true;
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }
    //Either onBillingInitialized or onPurchaseHistoryRestored
    @Override
    public void onBillingInitialized() {
        if(!bp.loadOwnedPurchasesFromGoogle()) {
            Toast.makeText(this,"Please Check Your Internet Connection",Toast.LENGTH_LONG);
            Cryptocurrencies.hasSubscription = false;
        }
//this is getting executed below
        else{
            if(bp.isSubscribed("monthly_sub")){
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
                Cryptocurrencies.hasSubscription = true;
            }
            else{
                Toast.makeText(this,"You Do Not Have A Subscription", Toast.LENGTH_SHORT).show();
                hasSubscription = false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if(resultCode == 0){
            Toast.makeText(this,"Buy A Subscription To See The Top Arbitrage Opportunities", Toast.LENGTH_LONG).show();
        }
        else if(resultCode == -1){
            Cryptocurrencies.hasSubscription = true;
            Toast.makeText(this, "Thank You For Purchasing A Subscription", Toast.LENGTH_LONG).show();
        }
        else if (resultCode == 2){
            Toast.makeText(this, "Network Connection Down", Toast.LENGTH_LONG).show();
        }
        else if(resultCode == 7){
            Toast.makeText(this,"You Are Already Subscribed",Toast.LENGTH_SHORT).show();;
        }
        else {
            Toast.makeText(this,"OnActivityResultMethod Result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop(){

        //want this to run for first time so that everything is restored for user automatically
        if(anyNewCoinsAddedOrDeleted() || !HomePage.isCreatedCryptocurrencies) {
            clearExchanges();
            HomePage.listOfCurrencies.clear();

            for (ToggleButton button : allCurrenciesButtons) {
                if (button.isChecked()) {
                    //adds bitcoin and ethereum if at least one coin is checked
                    if (!hasAddedBitcoinAndEthereum) {
                        addBitcoinAndEthereumToExchanges();
                        hasAddedBitcoinAndEthereum = true;
                    }
                    addCurrencyToExchanges(button);
                }
            }
        }

        HomePage.isCreatedCryptocurrencies = true;


        hasAddedBitcoinAndEthereum = false;

        saveSelectedCoinsInfo(allCurrenciesButtons);


        super.onStop();
    }
    @Override
    protected void onDestroy(){
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();

    }

/*    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        }
        catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }*/

}