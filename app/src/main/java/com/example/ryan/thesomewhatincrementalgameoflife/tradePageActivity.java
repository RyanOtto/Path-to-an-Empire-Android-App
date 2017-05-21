package com.example.ryan.thesomewhatincrementalgameoflife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.ryan.thesomewhatincrementalgameoflife.NewGameActivity.PREFNAME;

public class tradePageActivity extends AppCompatActivity implements View.OnClickListener {

    double tradeGold=0, tradeResources=0, tradeMult=1.0;
    Boolean hasFirstTradeUpgrade, hasSecondTradeUpgrade;
    TextView tradeResourcesTV, tradeGoldTV, tradeTradeMultTV, tradeUpgradeTV;
    Button tradeCaravanButton, localGuildsButton, merchantShipButton, tradingPostButton, mintGoldButton, charterFairButton, tradeUpgradeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_page);
        tradeCaravanButton =(Button)findViewById(R.id.tradeCaravanButton);
        localGuildsButton=(Button)findViewById(R.id.localGuildsButton);
        merchantShipButton=(Button)findViewById(R.id.merchantShipButton);
        tradingPostButton=(Button)findViewById(R.id.tradingPostButton);
        mintGoldButton=(Button)findViewById(R.id.mintGoldButton);
        charterFairButton=(Button)findViewById(R.id.charterFairButton);
        tradeUpgradeButton=(Button)findViewById(R.id.tradeUpgradeButton);
        tradeResourcesTV =(TextView)findViewById(R.id.tradeResourcesTV);
        tradeGoldTV=(TextView)findViewById(R.id.tradeGoldTV);
        tradeTradeMultTV=(TextView)findViewById(R.id.tradeTradeMultTV);
        tradeUpgradeTV=(TextView)findViewById(R.id.tradeUpgradeTV);

        loadTrading();
        setupTradeUI();

        tradeCaravanButton.setOnClickListener(this);
        localGuildsButton.setOnClickListener(this);
        merchantShipButton.setOnClickListener(this);
        tradingPostButton.setOnClickListener(this);
        mintGoldButton.setOnClickListener(this);
        charterFairButton.setOnClickListener(this);
        tradeUpgradeButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tradeCaravanButton:
                if(tradeResources>=100){
                    tradeResources-=100;
                    tradeGold+=5*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.localGuildsButton:
                if(tradeResources>=1000){
                    tradeResources-=1000;
                    tradeGold+=50*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.merchantShipButton:
                if(tradeResources>=10000){
                    tradeResources-=10000;
                    tradeGold+=500*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.tradingPostButton:
                if(tradeResources>=100000){
                    tradeResources-=100000;
                    tradeGold+=5000*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.mintGoldButton:
                if(tradeResources>=1000000){
                    tradeResources-=1000000;
                    tradeGold+=50000*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.charterFairButton:
                if(tradeResources>=10000000){
                    tradeResources-=10000000;
                    tradeGold+=500000*tradeMult;
                    saveTrading();
                    setupTradeUI();
                }
                break;
            case R.id.tradeUpgradeButton:
                if(!hasFirstTradeUpgrade && tradeGold>=5000){
                    tradeGold-=5000;
                    tradeMult+=0.15;
                    hasFirstTradeUpgrade=true;
                    saveTrading();
                    setupTradeUI();
                }
                else if(hasFirstTradeUpgrade && tradeGold>=50000){
                    tradeGold-=50000;
                    tradeMult+=0.15;
                    hasSecondTradeUpgrade=true;
                    saveTrading();
                    setupTradeUI();
                }
                break;
        }
    }

    @Override
    protected void onPause(){
        saveTrading();
        super.onPause();
    }

    @Override
    protected void onRestart(){
        loadTrading();
        setupTradeUI();
        super.onRestart();
    }

    double getDouble(SharedPreferences preferences, String key, double defaultValue) {
        if(!preferences.contains(key)) return defaultValue;
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }

    public void saveTrading(){
        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
        putDouble(editor, "gold", tradeGold);
        putDouble(editor, "resources", tradeResources);
        putDouble(editor, "goldMult", tradeMult);
        editor.putBoolean("hasFirstTradeUpgrade", hasFirstTradeUpgrade);
        editor.putBoolean("hasSecondTradeUpgrade", hasSecondTradeUpgrade);
        editor.apply();
    }

    public void loadTrading(){
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        tradeGold=getDouble(prefs, "gold", 1.0);
        tradeResources=getDouble(prefs, "resources", 1.0);
        tradeMult=getDouble(prefs, "goldMult", 1.0);
        hasFirstTradeUpgrade=prefs.getBoolean("hasFirstTradeUpgrade", false);
        hasSecondTradeUpgrade=prefs.getBoolean("hasSecondTradeUpgrade", false);
    }

    public void setupTradeUI(){
        tradeResourcesTV.setText("Resources: " + (double)Math.round(tradeResources*100)/100);
        tradeGoldTV.setText("Gold: " + (double)Math.round(tradeGold*100)/100);
        tradeTradeMultTV.setText("Trade Multiplier: " + (double)Math.round(tradeMult*100)/100);

        tradeCaravanButton.setText("Organize Trade Caravan: " + (double)Math.round((5*tradeMult)*100)/100 + "G (Cost: 100R)");
        localGuildsButton.setText("Sell to Local Guilds: " + (double)Math.round((50*tradeMult)*100)/100 + "G (Cost: 1,000)");
        merchantShipButton.setText("Send out Merchant Ship: " + (double)Math.round((500*tradeMult)*100)/100 + "G (Cost: 10,000R)");
        tradingPostButton.setText("Establish Trading Post: " + (double)Math.round((5000*tradeMult)*100)/100 + "G (Cost: 100,000R)");
        mintGoldButton.setText("Mint Gold Currency: " + (double)Math.round((50000*tradeMult)*100)/100 + "G (Cost: 1,000,000R)");
        charterFairButton.setText("Hold a Charter Fair: " + (double)Math.round((500000*tradeMult)*100)/100 + "G (Cost: 10,000,000R)");

        LinearLayout tradeLL = (LinearLayout) findViewById(R.id.tradeLL);
        if(hasFirstTradeUpgrade && !hasSecondTradeUpgrade) tradeUpgradeButton.setText("Construct New Trade Routes: +15% Trade Multiplier (Cost: 50,000G)");
        else if(hasSecondTradeUpgrade){
            tradeLL.removeView(tradeUpgradeButton);
            tradeLL.removeView(tradeUpgradeTV);
        }

    }

}