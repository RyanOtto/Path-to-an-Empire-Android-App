package com.example.ryan.thesomewhatincrementalgameoflife;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ryan.thesomewhatincrementalgameoflife.NewGameActivity.PREFNAME;

public class heroPageActivity extends AppCompatActivity implements View.OnClickListener {

    TextView heroNameTV, heroPowerTV, heroGoldTV, headTV, shouldersTV, torsoTV, handsTV, legsTV, feetTV, mhTV, ohTV;
    Button headButton, shouldersButton, torsoButton, handsButton, legsButton, feetButton, mhButton, ohButton;
    Boolean heroHasBlacksmith, heroHasArmory;

    // -- Hero Armor --
    Boolean hasHeadOne=false, hasHeadTwo=false, hasHeadThree=false;
    Boolean hasShouldersOne=false, hasShouldersTwo=false, hasShouldersThree=false;
    Boolean hasTorsoOne=false, hasTorsoTwo=false, hasTorsoThree=false;
    Boolean hasHandsOne=false, hasHandsTwo=false, hasHandsThree=false;
    Boolean hasLegsOne=false, hasLegsTwo=false, hasLegsThree=false;
    Boolean hasFeetOne=false, hasFeetTwo=false, hasFeetThree=false;

    // -- Hero Weapons --
    Boolean hasMHOne=false, hasMHTwo=false, hasMHThree=false;
    Boolean hasOHOne=false, hasOHTwo=false, hasOHThree=false;
    String heroName;
    double heroPower=0.0, heroGold=0.0, newMilitaryPower=0.0, milMult=1.0, soldierMPMult=1.0, soldierMP=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_page);

        heroNameTV=(TextView)findViewById(R.id.heroNameTV);
        heroPowerTV=(TextView)findViewById(R.id.heroPowerTV);
        heroGoldTV=(TextView)findViewById(R.id.heroGoldTV);
        headTV=(TextView)findViewById(R.id.headTV);
        shouldersTV=(TextView)findViewById(R.id.shouldersTV);
        torsoTV=(TextView)findViewById(R.id.torsoTV);
        handsTV=(TextView)findViewById(R.id.handsTV);
        legsTV=(TextView)findViewById(R.id.legsTV);
        feetTV=(TextView)findViewById(R.id.feetTV);
        mhTV =(TextView)findViewById(R.id.mhTV);
        ohTV=(TextView)findViewById(R.id.ohTV);

        headButton=(Button)findViewById(R.id.headButton);
        shouldersButton=(Button)findViewById(R.id.shouldersButton);
        torsoButton=(Button)findViewById(R.id.torsoButton);
        handsButton=(Button)findViewById(R.id.handsButton);
        legsButton=(Button)findViewById(R.id.legsButton);
        feetButton=(Button)findViewById(R.id.feetButton);
        mhButton=(Button)findViewById(R.id.mhButton);
        ohButton=(Button)findViewById(R.id.ohButton);

        loadHero();
        setupHeroUI();

        headButton.setOnClickListener(this);
        shouldersButton.setOnClickListener(this);
        torsoButton.setOnClickListener(this);
        handsButton.setOnClickListener(this);
        legsButton.setOnClickListener(this);
        feetButton.setOnClickListener(this);
        mhButton.setOnClickListener(this);
        ohButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.headButton:
                if(!hasHeadThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasHeadOne) hasHeadOne=true;
                    else if(!hasHeadTwo) hasHeadTwo=true;
                    else hasHeadThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.shouldersButton:
                if(!hasShouldersThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasShouldersOne) hasShouldersOne=true;
                    else if(!hasShouldersTwo) hasShouldersTwo=true;
                    else hasShouldersThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.torsoButton:
                if(!hasTorsoThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasTorsoOne) hasTorsoOne=true;
                    else if(!hasTorsoTwo) hasTorsoTwo=true;
                    else hasTorsoThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.handsButton:
                if(!hasHandsThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasHandsOne) hasHandsOne=true;
                    else if(!hasHandsTwo) hasHandsTwo=true;
                    else hasHandsThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.legsButton:
                if(!hasLegsThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasLegsOne) hasLegsOne=true;
                    else if(!hasLegsTwo) hasLegsTwo=true;
                    else hasLegsThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.feetButton:
                if(!hasFeetThree && heroGold>=10000){
                    heroPower+=10000;
                    heroGold-=10000;
                    if(!hasFeetOne) hasFeetOne=true;
                    else if(!hasFeetTwo) hasFeetTwo=true;
                    else hasFeetThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.mhButton:
                if(!hasMHThree && heroGold>=20000){
                    heroPower+=20000;
                    heroGold-=20000;
                    if(!hasMHOne) hasMHOne=true;
                    else if(!hasMHTwo) hasMHTwo=true;
                    else hasMHThree=true;
                    setupHeroUI();
                }
                break;
            case R.id.ohButton:
                if(!hasOHThree && heroGold>=20000){
                    heroPower+=20000;
                    heroGold-=20000;
                    if(!hasOHOne) hasOHOne=true;
                    else if(!hasOHTwo) hasOHTwo=true;
                    else hasOHThree=true;
                    setupHeroUI();
                }
                break;
        }
    }

    public void setupHeroUI() {
        heroNameTV.setText("Name: " + heroName);
        heroPowerTV.setText("Power Contribution: " + (double)Math.round((heroPower*milMult)*100)/100);
        heroGoldTV.setText("Gold: " + (double)Math.round(heroGold*100)/100);

        if(heroHasArmory) {
            if(!hasHeadThree)headButton.setVisibility(VISIBLE);
            if(!hasShouldersThree)shouldersButton.setVisibility(VISIBLE);
            if(!hasTorsoThree)torsoButton.setVisibility(VISIBLE);
            if(!hasHandsThree)handsButton.setVisibility(VISIBLE);
            if(!hasLegsThree)legsButton.setVisibility(VISIBLE);
            if(!hasFeetThree)feetButton.setVisibility(VISIBLE);
        }
        if(heroHasBlacksmith){
            if(!hasMHThree)mhButton.setVisibility(VISIBLE);
            if(!hasOHThree)ohButton.setVisibility(VISIBLE);
        }

        if(hasHeadThree){
            headTV.setText("Head: Plate Helmet");
            headButton.setVisibility(GONE);
        }
        else if(hasHeadTwo){
            headTV.setText("Head: Wizard's Hat");
            headButton.setText("Buy Plate Helmet: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasHeadOne){
            headTV.setText("Head: Leather Cap");
            headButton.setText("Buy Wizard's Hat: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else headButton.setText("Buy Leather Cap: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");


        if(hasShouldersThree){
            shouldersTV.setText("Shoulders: Plate Pauldrons");
            shouldersButton.setVisibility(GONE);
        }
        else if(hasShouldersTwo){
            shouldersTV.setText("Shoulders: Iron Pauldrons");
            shouldersButton.setText("Buy Plate Pauldrons: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasShouldersOne){
            shouldersTV.setText("Shoulders: Leather Shoulderpads");
            shouldersButton.setText("Buy Iron Pauldrons: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else shouldersButton.setText("Buy Leather Shoulderpads: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");

        if(hasTorsoThree){
            torsoTV.setText("Torso: Plate Cuirass");
            torsoButton.setVisibility(GONE);
        }
        else if(hasTorsoTwo){
            torsoTV.setText("Torso: Chainmail Shirt");
            torsoButton.setText("Buy Plate Cuirass: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasTorsoOne){
            torsoTV.setText("Torso: Leather Cuirass");
            torsoButton.setText("Buy Chainmail Shirt: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else torsoButton.setText("Buy Leather Cuirass: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");

        if(hasLegsThree){
            legsTV.setText("Legs: Plate Greaves");
            legsButton.setVisibility(GONE);
        }
        else if(hasLegsTwo){
            legsTV.setText("Legs: Chainmail Leggings");
            legsButton.setText("Buy Plate Greaves: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasLegsOne){
            legsTV.setText("Legs: Leather Pants");
            legsButton.setText("Buy Chainmail Leggings: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else legsButton.setText("Buy Leather Pants: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");

        if(hasHandsThree) {
            handsTV.setText("Hands: Plate Gauntlets");
            handsButton.setVisibility(GONE);
        }
        else if(hasHandsTwo){
            handsTV.setText("Hands: Chainmail Gauntlets");
            handsButton.setText("Buy Plate Gauntlets: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasHandsOne){
            handsTV.setText("Hands: Leather Gloves");
            handsButton.setText("Buy Chainmail Gloves: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else handsButton.setText("Buy Leather Gloves: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");

        if(hasFeetThree){
            feetTV.setText("Feet: Plate Boots");
            feetButton.setVisibility(GONE);
        }
        else if(hasFeetTwo){
            feetTV.setText("Feet: Chainmail Boots");
            feetButton.setText("Buy Plate Boots: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else if(hasFeetOne){
            feetTV.setText("Feet: Leather Boots");
            feetButton.setText("Buy Chainmail Boots: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");
        }
        else feetButton.setText("Buy Leather Boots: +" + (double)Math.round((10000*milMult)*100)/100 + " Power (Cost: 10,000G)");

        if(hasMHThree){
            mhTV.setText("Main Hand: Colossal Flaming Greataxe");
            mhButton.setVisibility(GONE);
        }
        else if(hasMHTwo){
            mhTV.setText("Main Hand: Longsword");
            mhButton.setText("Buy Colossal Flaming Greataxe: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 20,000G)");
        }
        else if(hasMHOne){
            mhTV.setText("Main Hand: Small Dagger");
            mhButton.setText("Buy Longsword: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 20,000G)");
        }
        else mhButton.setText("Buy Small Dagger: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 20,000G)");

        if(hasOHThree){
            ohTV.setText("Off Hand: Stunningly Massive Bulwark");
            ohButton.setVisibility(GONE);
        }
        else if(hasOHTwo) {
            ohTV.setText("Off Hand: Large Shield");
            ohButton.setText("Buy Stunningly Massive Bulwark: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 200,000G)");
        }
        else if(hasOHOne) {
            ohTV.setText("Off Hand: Small Shield");
            ohButton.setText("Large Shield: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 20,000G)");
        }
        else ohButton.setText("Buy Small Shield: +" + (double)Math.round((20000*milMult)*200)/200 + " Power (Cost: 20,000G)");
    }

    @Override
    protected void onRestart(){
        loadHero();
        setupHeroUI();
        super.onRestart();
    }

    @Override
    public void onPause(){
        saveHero();
        super.onPause();
    }

    public void loadHero(){
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        heroHasArmory=prefs.getBoolean("hasArmory", false);
        heroHasBlacksmith=prefs.getBoolean("hasBlacksmith", false);
        hasHeadOne=prefs.getBoolean("hasHeadOne", false);
        hasHeadTwo=prefs.getBoolean("hasHeadTwo", false);
        hasHeadThree=prefs.getBoolean("hasHeadThree", false);
        hasShouldersOne=prefs.getBoolean("hasShouldersOne", false);
        hasShouldersTwo=prefs.getBoolean("hasShouldersTwo", false);
        hasShouldersThree=prefs.getBoolean("hasShouldersThree", false);
        hasTorsoOne=prefs.getBoolean("hasTorsoOne", false);
        hasTorsoTwo=prefs.getBoolean("hasTorsoTwo", false);
        hasTorsoThree=prefs.getBoolean("hasTorsoThree", false);
        hasHandsOne=prefs.getBoolean("hasHandsOne", false);
        hasHandsTwo=prefs.getBoolean("hasHandsTwo", false);
        hasHandsThree=prefs.getBoolean("hasHandsThree", false);
        hasLegsOne=prefs.getBoolean("hasLegsOne", false);
        hasLegsTwo=prefs.getBoolean("hasLegsTwo", false);
        hasLegsThree=prefs.getBoolean("hasLegsThree", false);
        hasFeetOne=prefs.getBoolean("hasFeetOne", false);
        hasFeetTwo=prefs.getBoolean("hasFeetTwo", false);
        hasFeetThree=prefs.getBoolean("hasFeetThree", false);
        hasMHOne=prefs.getBoolean("hasMHOne", false);
        hasMHTwo=prefs.getBoolean("hasMHTwo", false);
        hasMHThree=prefs.getBoolean("hasMHThree", false);
        hasOHOne=prefs.getBoolean("hasOHOne", false);
        hasOHTwo=prefs.getBoolean("hasOHTwo", false);
        hasOHThree=prefs.getBoolean("hasOHThree", false);
        heroName=prefs.getString("heroName", "Bob");
        heroPower=getDouble(prefs, "heroMP", 0.0);
        heroGold=getDouble(prefs, "gold", 0.0);
        newMilitaryPower=getDouble(prefs, "militaryPower", 0.0);
        milMult=getDouble(prefs, "milMult", 1.0);
        soldierMPMult=getDouble(prefs, "soldierMPMult", 1.0);
        soldierMP=getDouble(prefs, "soldierMP", 0.0);

    }

    public void saveHero(){
        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
        editor.putBoolean("hasArmory", heroHasArmory);
        editor.putBoolean("hasBlacksmith", heroHasBlacksmith);
        editor.putBoolean("hasHeadOne", hasHeadOne);
        editor.putBoolean("hasHeadTwo", hasHeadTwo);
        editor.putBoolean("hasHeadThree", hasHeadThree);
        editor.putBoolean("hasShouldersOne", hasShouldersOne);
        editor.putBoolean("hasShouldersTwo", hasShouldersTwo);
        editor.putBoolean("hasShouldersThree", hasShouldersThree);
        editor.putBoolean("hasTorsoOne", hasTorsoOne);
        editor.putBoolean("hasTorsoTwo", hasTorsoTwo);
        editor.putBoolean("hasTorsoThree", hasTorsoThree);
        editor.putBoolean("hasHandsOne", hasHandsOne);
        editor.putBoolean("hasHandsTwo", hasHandsTwo);
        editor.putBoolean("hasHandsThree", hasHandsThree);
        editor.putBoolean("hasLegsOne", hasLegsOne);
        editor.putBoolean("hasLegsTwo", hasLegsTwo);
        editor.putBoolean("hasLegsThree", hasLegsThree);
        editor.putBoolean("hasFeetOne", hasFeetOne);
        editor.putBoolean("hasFeetTwo", hasFeetTwo);
        editor.putBoolean("hasFeetThree", hasFeetThree);
        editor.putBoolean("hasMHOne", hasMHOne);
        editor.putBoolean("hasMHTwo", hasMHTwo);
        editor.putBoolean("hasMHThree", hasMHThree);
        editor.putBoolean("hasOHOne", hasOHOne);
        editor.putBoolean("hasOHTwo", hasOHTwo);
        editor.putBoolean("hasOHThree", hasOHThree);
        putDouble(editor, "heroMP", heroPower);
        putDouble(editor, "gold", heroGold);
        newMilitaryPower = ((soldierMP * soldierMPMult) + heroPower) * milMult;
        putDouble(editor, "militaryPower", newMilitaryPower);
        editor.apply();
    }

    double getDouble(SharedPreferences preferences, String key, double defaultValue) {
        if(!preferences.contains(key)) return defaultValue;
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }
}