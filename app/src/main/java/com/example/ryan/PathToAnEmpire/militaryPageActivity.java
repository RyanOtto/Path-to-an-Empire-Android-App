package com.example.ryan.PathToAnEmpire;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ryan.PathToAnEmpire.NewGameActivity.PREFNAME;

public class militaryPageActivity extends AppCompatActivity implements View.OnClickListener{
    double milPageGold=0, newMilitaryPower=0, soldierMP=0.0, heroMP=0.0, milMult=1.0, soldierMPMult=1.0;
    TextView heroMPTV, soldierMPTV, soldierMilMultTV, totalMPTV, milGoldTV, genMilMultTV, upgradeArmyTV;
    Button scoutButton, raiderButton, archerButton, mercenaryButton, swordsmanButton, cavalierButton, knightButton, upgradeWeaponsButton, upgradeArmorButton;
    Boolean hasIronWeapons=false, hasIronArmor=false, hasSteelWeapons=false, hasSteelArmor=false, hasArmory=false, hasBlacksmith=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_military_page);

        heroMPTV=(TextView)findViewById(R.id.heroMPTV);
        soldierMPTV=(TextView)findViewById(R.id.soldierMPTV);
        soldierMilMultTV =(TextView)findViewById(R.id.soldierMilMultTV);
        totalMPTV=(TextView)findViewById(R.id.totalMPTV);
        milGoldTV=(TextView)findViewById(R.id.milGoldTV);
        genMilMultTV=(TextView)findViewById(R.id.genMilMultTV);
        upgradeArmyTV=(TextView)findViewById(R.id.upgradeArmyTV);

        scoutButton=(Button)findViewById(R.id.scoutButton);
        raiderButton=(Button)findViewById(R.id.raiderButton);
        archerButton=(Button)findViewById(R.id.archerButton);
        mercenaryButton=(Button)findViewById(R.id.mercenaryButton);
        swordsmanButton=(Button)findViewById(R.id.swordsmanButton);
        cavalierButton=(Button)findViewById(R.id.cavalierButton);
        knightButton=(Button)findViewById(R.id.knightButton);
        upgradeWeaponsButton=(Button)findViewById(R.id.upgradeWeaponsButton);
        upgradeArmorButton=(Button)findViewById(R.id.upgradeArmorButton);

        loadMilitary();
        setupMilUI();

        scoutButton.setOnClickListener(this);
        raiderButton.setOnClickListener(this);
        archerButton.setOnClickListener(this);
        mercenaryButton.setOnClickListener(this);
        swordsmanButton.setOnClickListener(this);
        cavalierButton.setOnClickListener(this);
        knightButton.setOnClickListener(this);
        upgradeWeaponsButton.setOnClickListener(this);
        upgradeArmorButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.scoutButton:
                if(milPageGold>=5){
                    milPageGold-=5;
                    soldierMP+=5;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.raiderButton:
                if(milPageGold>=50){
                    milPageGold-=50;
                    soldierMP+=50;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.archerButton:
                if(milPageGold>=500){
                    milPageGold-=500;
                    soldierMP+=500;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.mercenaryButton:
                if(milPageGold>=5000){
                    milPageGold-=5000;
                    soldierMP+=5000;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.swordsmanButton:
                if(milPageGold>=50000){
                    milPageGold-=50000;
                    soldierMP+=50000;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.cavalierButton:
                if(milPageGold>=500000){
                    milPageGold-=500000;
                    soldierMP+=500000;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.knightButton:
                if(milPageGold>=5000000){
                    milPageGold-=5000000;
                    soldierMP+=5000000;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    setupMilUI();
                }
                break;
            case R.id.upgradeWeaponsButton:
                if(!hasIronWeapons && milPageGold>=5000){
                    milPageGold-=5000;
                    soldierMPMult+=0.15;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    hasIronWeapons=true;
                    setupMilUI();
                }
                else if(hasIronWeapons && milPageGold>=50000){
                    milPageGold-=50000;
                    soldierMPMult+=0.15;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    hasSteelWeapons=true;
                    setupMilUI();
                }
                break;
            case R.id.upgradeArmorButton:
                if(!hasIronArmor && milPageGold>=5000){
                    milPageGold-=5000;
                    soldierMPMult+=0.15;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    hasIronArmor=true;
                    setupMilUI();
                }
                else if(hasIronArmor && milPageGold>=50000){
                    milPageGold-=50000;
                    soldierMPMult+=0.15;
                    newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
                    hasSteelArmor=true;
                    setupMilUI();
                }
                break;
        }

    }

    @Override
    protected void onPause(){
        saveMilitary();
        super.onPause();
    }

    @Override
    protected void onRestart(){
        loadMilitary();
        setupMilUI();
        super.onRestart();
    }

    double getDouble(SharedPreferences preferences, String key, double defaultValue) {
        if(!preferences.contains(key)) return defaultValue;
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }

    public void loadMilitary(){
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        milPageGold =getDouble(prefs, "gold", 0);
        newMilitaryPower=getDouble(prefs, "militaryPower", 0);
        soldierMP=getDouble(prefs, "soldierMP", 0);
        soldierMPMult=getDouble(prefs, "soldierMPMult", 1.0);
        heroMP=getDouble(prefs, "heroMP", 0);
        milMult=getDouble(prefs, "milMult", 1.0);
        hasIronWeapons=prefs.getBoolean("hasIronWeapons", false);
        hasIronArmor=prefs.getBoolean("hasIronArmor", false);
        hasSteelWeapons=prefs.getBoolean("hasSteelWeapons", false);
        hasSteelArmor=prefs.getBoolean("hasSteelArmor", false);
        hasBlacksmith=prefs.getBoolean("hasBlacksmith", false);
        hasArmory=prefs.getBoolean("hasArmory", false);
        newMilitaryPower = ((soldierMP*soldierMPMult) + heroMP) * milMult;
    }

    public void saveMilitary(){
        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
        putDouble(editor, "gold", milPageGold);
        putDouble(editor, "militaryPower", newMilitaryPower);
        putDouble(editor, "soldierMP", soldierMP);
        putDouble(editor, "heroMP", heroMP);
        putDouble(editor, "milMult", milMult);
        putDouble(editor, "soldierMPMult", soldierMPMult);
        editor.putBoolean("hasIronWeapons", hasIronWeapons);
        editor.putBoolean("hasIronArmor", hasIronArmor);
        editor.putBoolean("hasSteelWeapons", hasSteelWeapons);
        editor.putBoolean("hasSteelArmor", hasSteelArmor);
        editor.apply();
    }

    public void setupMilUI(){
        heroMPTV.setText("Hero Military Power: "+ (double)Math.round((heroMP*milMult)*100)/100);
        soldierMPTV.setText("Soldier Military Power: "+ (double)Math.round(((soldierMP*soldierMPMult)*milMult)*100)/100);
        soldierMilMultTV.setText("Soldier Upgrade Multiplier: "+(double)Math.round(soldierMPMult*100)/100);
        genMilMultTV.setText("General Military Multiplier: " + (double)Math.round(milMult*100)/100);
        totalMPTV.setText("Total Military Power: "+ (double)Math.round(newMilitaryPower*100)/100);
        milGoldTV.setText("Gold: " + (double)Math.round(milPageGold *100)/100);

        scoutButton.setText("Hire Scout: +" + (double)Math.round(((5*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 5G)");
        raiderButton.setText("Hire Raider: +" + (double)Math.round(((50*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 50G)");
        archerButton.setText("Hire Archer: +" + (double)Math.round(((500*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 500G)");
        mercenaryButton.setText("Hire Mercenary: +" + (double)Math.round(((5000*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 5000G)");
        swordsmanButton.setText("Hire Swordsman: +" + (double)Math.round(((50000*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 50000G)");
        cavalierButton.setText("Hire Cavalry: +" + (double)Math.round(((500000*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 500000G)");
        knightButton.setText("Hire Knight: +" + (double)Math.round(((5000000*soldierMPMult)*milMult)*100)/100 + " Power (Cost: 5000000G)");

        if(!hasArmory && !hasBlacksmith) upgradeArmyTV.setVisibility(GONE);
        if(!hasArmory) upgradeArmorButton.setVisibility(GONE);
        else if(hasArmory && !hasSteelArmor) upgradeArmorButton.setVisibility(VISIBLE);
        if(!hasBlacksmith) upgradeWeaponsButton.setVisibility(GONE);
        else if(hasBlacksmith && !hasSteelWeapons) upgradeWeaponsButton.setVisibility(VISIBLE);

        if(hasIronWeapons) upgradeWeaponsButton.setText("Upgrade to Steel Weapons: +15% New Soldier Strength (Cost: 50,000G)");
        if(hasIronArmor) upgradeArmorButton.setText("Upgrade to Steel Armor: +15% New Soldier Strength (Cost: 50,000G)");

        LinearLayout militaryLL = (LinearLayout) findViewById(R.id.militaryLL);
        if(hasSteelWeapons) militaryLL.removeView(upgradeWeaponsButton);
        if(hasSteelArmor) militaryLL.removeView(upgradeArmorButton);
        if(hasSteelWeapons && !hasArmory) upgradeArmyTV.setVisibility(GONE);
        if(hasSteelArmor && !hasBlacksmith) upgradeArmyTV.setVisibility(GONE);
        if(hasSteelWeapons && hasSteelArmor) militaryLL.removeView(upgradeArmyTV);
    }
}
