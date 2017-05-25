package com.example.ryan.PathToAnEmpire;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ryan.PathToAnEmpire.NewGameActivity.PREFNAME;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     //-- Player Status UI Elements --
     TextView resourceTV, militaryPowerTV, goldTV, mainSettlementNameTV, gatheringTV, upgradesTV, titleTV;
    //-- Game Booleans --
     Boolean didNG=false, hasGame=false, formedReligion=false, handledRelMults=false;
     //-- Game Buttons --
     Button newGameButton, continueButton;
     //-- Resource Buttons --
     Button lumberMillButton, huntersCampButton, quarryButton, mineButton, farmButton, smeltingFurnaceButton;
     //-- Upgrade Buttons --
     Button armoryButton, blacksmithButton, crannyButton, barracksButton, templeButton, cropRotationButton, marketplaceButton;
     //-- Upgrade Variables --
     Boolean hasArmory=false, hasBlacksmith=false, hasCranny=false, hasBarracks=false, hasTemple=false, hasCropRotation=false, hasMarketplace=false;
     int settlementRank=0;
     //-- Building Counts --
     int numLumberMills, numHuntersCamps, numQuarries, numMines, numFarms, numSmeltingFurnaces;
     // -- Info and Menus
     MenuItem religionOption, settlementUpgradeOption;
     String settlementType, settlementName, heroName;
     //Player currencies
     double resources=0, gold=0, militaryPower=0, soldierMP=0.0, heroMP=0.0;
     //Mults and rates
     double resMult=1.0, goldMult=1.0, milMult=1.0, soldierMilMult=1.0, resourceRate=1.0, baseResourceRate=1.0, relResMult=0.0, relGoldMult=0.0, relMilMult=0.0;
     int eventTicker=0;
     Random eventRandom = new Random();

     Handler incrementHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            resourceRate=(baseResourceRate * resMult);
            resources+=resourceRate;
            eventTicker+=1;

            if(eventTicker==330){ //Every 5.5 minutes
                eventTicker=0;
                if(eventRandom.nextInt(100) >= 60) randomEvent(eventRandom.nextInt(8)); //40% chance every 100 seconds of an event from IDs 0-7
            }

            resourceTV.setText("Resources: " + (double)Math.round(resources*100)/100 + " (" + (double)Math.round(resourceRate*100)/100 + "/s" + ")" + " ");
            militaryPowerTV.setText("Military Power: " + (double)Math.round(militaryPower*100)/100 + " (" + (double)Math.round(milMult*100)/100 + ")");
            goldTV.setText("Gold: " + (double)Math.round(gold*100)/100 + " (" + (double)Math.round(goldMult*100)/100 + ")");

        }
    };

    Runnable runnable = new Runnable() {
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (Exception e) {
                    }
                }
                incrementHandler.sendEmptyMessage(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTV = (TextView)findViewById(R.id.titleTV);
        resourceTV = (TextView)findViewById(R.id.resourceTV);
        militaryPowerTV = (TextView)findViewById(R.id.militaryPowerTV);
        goldTV = (TextView)findViewById(R.id.goldTV);
        mainSettlementNameTV = (TextView)findViewById(R.id.mainSettlementNameTV);
        gatheringTV = (TextView)findViewById(R.id.gatheringTV);
        upgradesTV = (TextView)findViewById(R.id.upgradesTV);

        lumberMillButton = (Button)findViewById(R.id.lumberMillButton);
        newGameButton = (Button)findViewById(R.id.newGameButton);
        continueButton = (Button)findViewById(R.id.continueButton);
        templeButton = (Button)findViewById(R.id.templeButton);
        huntersCampButton = (Button)findViewById(R.id.huntersCampButton);
        quarryButton = (Button)findViewById(R.id.quarryButton);
        mineButton = (Button)findViewById(R.id.mineButton);
        farmButton = (Button)findViewById(R.id.farmButton);
        smeltingFurnaceButton = (Button)findViewById(R.id.smeltingFurnaceButton);
        armoryButton = (Button)findViewById(R.id.armoryButton);
        blacksmithButton = (Button)findViewById(R.id.blacksmithButton);
        crannyButton = (Button)findViewById(R.id.crannyButton);
        barracksButton = (Button)findViewById(R.id.barracksButton);
        templeButton = (Button)findViewById(R.id.templeButton);
        cropRotationButton = (Button)findViewById(R.id.cropRotationButton);
        marketplaceButton = (Button)findViewById(R.id.marketplaceButton);

        religionOption = (MenuItem)findViewById(R.id.religionMenu);

        //Initially, some UI elements should be invisible
        mainSettlementNameTV.setVisibility(GONE);
        resourceTV.setVisibility(GONE);
        militaryPowerTV.setVisibility(GONE);
        goldTV.setVisibility(GONE);
        gatheringTV.setVisibility(GONE);
        lumberMillButton.setVisibility(GONE);
        huntersCampButton.setVisibility(GONE);
        quarryButton.setVisibility(GONE);
        mineButton.setVisibility(GONE);
        farmButton.setVisibility(GONE);
        smeltingFurnaceButton.setVisibility(GONE);
        upgradesTV.setVisibility(GONE);
        armoryButton.setVisibility(GONE);
        blacksmithButton.setVisibility(GONE);
        crannyButton.setVisibility(GONE);
        barracksButton.setVisibility(GONE);
        templeButton.setVisibility(GONE);
        cropRotationButton.setVisibility(GONE);
        marketplaceButton.setVisibility(GONE);

        //If we don't have a saved game, don't show continue button
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        hasGame = prefs.getBoolean("hasGame", false);
        if(!hasGame) continueButton.setVisibility(GONE);

        //On click listeners
        newGameButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);

        lumberMillButton.setOnClickListener(this);
        huntersCampButton.setOnClickListener(this);
        quarryButton.setOnClickListener(this);
        mineButton.setOnClickListener(this);
        farmButton.setOnClickListener(this);
        smeltingFurnaceButton.setOnClickListener(this);

        armoryButton.setOnClickListener(this);
        blacksmithButton.setOnClickListener(this);
        crannyButton.setOnClickListener(this);
        templeButton.setOnClickListener(this);
        barracksButton.setOnClickListener(this);
        cropRotationButton.setOnClickListener(this);
        marketplaceButton.setOnClickListener(this);

    }

     @Override
     public void onClick(View v) {

         LinearLayout mainLL = (LinearLayout)findViewById(R.id.mainLinearLayout);

         switch(v.getId()){
                    // -- Game State --
                    case R.id.newGameButton:
                        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        Intent newGameIntent = new Intent(v.getContext(), NewGameActivity.class);
                        newGameIntent.setFlags(FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(newGameIntent);
                        mainLL.removeView(newGameButton);
                        mainLL.removeView(continueButton);
                        mainLL.removeView(titleTV);
                        break;
                    case R.id.continueButton:
                        loadGame();
                        setMainUI();
                        invalidateOptionsMenu();
                        Thread incrementThread = new Thread(runnable);
                        incrementThread.start();
                        Message msg = incrementHandler.obtainMessage();
                        incrementHandler.sendMessage(msg);
                        mainLL.removeView(newGameButton);
                        mainLL.removeView(continueButton);
                        mainLL.removeView(titleTV);
                        break;

                    // -- Resources --
                    case R.id.lumberMillButton:
                        if(resources>=20){
                            resources-=20;
                            baseResourceRate+=0.1;
                            resourceRate=(baseResourceRate * resMult);
                            numLumberMills++;
                            setMainUI();
                        }
                        break;
                    case R.id.huntersCampButton:
                        if(resources>=200){
                            resources-=200;
                            baseResourceRate+=1.0;
                            resourceRate=(baseResourceRate * resMult);
                            numHuntersCamps++;
                            setMainUI();
                        }
                        break;
                    case R.id.quarryButton:
                        if(resources>=2000){
                            resources-=2000;
                            baseResourceRate+=10;
                            resourceRate=(baseResourceRate * resMult);
                            numQuarries++;
                            setMainUI();
                        }
                        break;
                    case R.id.mineButton:
                        if(resources>=20000){
                            resources-=20000;
                            baseResourceRate+=100;
                            resourceRate=(baseResourceRate * resMult);
                            numMines++;
                            setMainUI();
                        }
                        break;
                    case R.id.farmButton:
                        if(resources>=200000){
                            resources-=200000;
                            baseResourceRate+=1000;
                            resourceRate=(baseResourceRate * resMult);
                            numFarms++;
                            setMainUI();
                        }
                        break;
                    case R.id.smeltingFurnaceButton:
                        if(resources>=2000000){
                            resources-=2000000;
                            baseResourceRate+=10000;
                            resourceRate=(baseResourceRate * resMult);
                            numSmeltingFurnaces++;
                            setMainUI();
                        }
                        break;

                    // -- Upgrades --
                    case R.id.armoryButton:
                        if(resources>=1000){
                            resources-=1000;
                            hasArmory=true;
                            setMainUI();
                        }
                        break;
                    case R.id.blacksmithButton:
                        if(resources>=1000){
                            resources-=1000;
                            hasBlacksmith=true;
                            setMainUI();
                        }
                        break;
                    case R.id.crannyButton:
                        if(resources>=10000){
                            resources-=10000;
                            hasCranny=true;
                            setMainUI();
                        }
                        break;
                    case R.id.templeButton:
                        if(resources>=30000){
                            resources-=30000;
                            hasTemple=true;
                            invalidateOptionsMenu();
                            setMainUI();
                        }
                        break;
                    case R.id.barracksButton:
                        if(resources>=200000){
                            resources-=200000;
                            hasBarracks=true;
                            milMult+=0.15;
                            militaryPower = (soldierMP + heroMP) * milMult;
                            setMainUI();
                        }
                        break;
                    case R.id.cropRotationButton:
                        if(resources>=200000){
                            resources-=200000;
                            hasCropRotation=true;
                            resMult+=0.15;
                            setMainUI();
                        }
                        break;
                    case R.id.marketplaceButton:
                        if(resources>=200000){
                            resources-=200000;
                            hasMarketplace=true;
                            goldMult+=0.15;
                            setMainUI();
                        }
                        break;
                }
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) { //Make the ... options menu appear
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu_main, menu);
         religionOption=menu.findItem(R.id.religionMenu);
         settlementUpgradeOption=menu.findItem(R.id.settlementUpgradeMenu);
         if(hasTemple) religionOption.setVisible(true);
         if(formedReligion)religionOption.setTitle("Religion");
         if(settlementRank>=5) settlementUpgradeOption.setVisible(false);
         return true;
     }

     @Override
     public boolean onPrepareOptionsMenu(Menu menu){ // ... options menu should only appear after NG or continue
         return didNG;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) { //Handle ... options menu click events
         switch(item.getItemId()) {
             case R.id.heroMenu:
                 Intent heroMenuIntent = new Intent(getApplicationContext(), heroPageActivity.class);
                 startActivity(heroMenuIntent);
                 return true;
             case R.id.settlementNameChangeMenu:
                 final AlertDialog.Builder newSettlementNameAlert = new AlertDialog.Builder(this);
                 final EditText newSettlementNameET = new EditText(this);
                 newSettlementNameAlert.setView(newSettlementNameET);
                 newSettlementNameAlert.setTitle("New Settlement Name");
                 newSettlementNameAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                         settlementName = newSettlementNameET.getText().toString().trim();
                         mainSettlementNameTV.setText("The " + settlementType + " of " + settlementName);
                     }
                 });
                 newSettlementNameAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                         dialog.cancel();
                     }
                 });
                 newSettlementNameAlert.show();
                 return true;
             case R.id.settlementUpgradeMenu:

                 final AlertDialog.Builder settlementUpgradeAlert = new AlertDialog.Builder(this); //Prompt the user regarding upgrades
                 final AlertDialog.Builder settlementUpgradeDialogue = new AlertDialog.Builder(this); //Resulting dialogue from upgrades

                 switch(settlementRank) {
                     case 0:
                         settlementUpgradeAlert.setMessage("Upgrade your settlement?  \nAll multipliers will increase by 10%  \n(Cost: 2,000R)");
                         break;
                     case 1:
                         settlementUpgradeAlert.setMessage("Upgrade your settlement?  \nAll multipliers will increase by 10%  \n(Cost: 20,000R)");
                         break;
                     case 2:
                         settlementUpgradeAlert.setMessage("Upgrade your settlement?  \nAll multipliers will increase by 10%  \n(Cost: 200,000R)");
                         break;
                     case 3:
                         settlementUpgradeAlert.setMessage("Upgrade your settlement?  \nAll multipliers will increase by 10%  \n(Cost: 2,000,000R)");
                         break;
                     case 4:
                         settlementUpgradeAlert.setMessage("Upgrade your settlement?  \nAll multipliers will increase by 10%  \n(Cost: 20,000,000R)");
                         break;
                 }
                 settlementUpgradeAlert.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                         String settlementUpgradeText="You require more resources to upgrade your settlement!";

                         if(settlementRank==0 && resources >= 2000){
                             resources-=2000;
                             settlementRank++;
                             settlementType="Village";
                             resMult+=0.1;
                             milMult+=0.1;
                             goldMult+=0.1;
                             settlementUpgradeText="With time, you and your small group of wanderers have turned what little you had into a vibrant community." +
                                     "  Your settlement has become a project of like-minded citizens, with laws and a culture of its own." +
                                     "  This stability has led to a period of prosperity, and your settlement can now be considered a formal village.";
                         }
                         else if(settlementRank==1 && resources >= 20000){
                             resources-=20000;
                             settlementRank++;
                             settlementType="City";
                             resMult+=0.1;
                             milMult+=0.1;
                             goldMult+=0.1;
                             settlementUpgradeText="Your village has become a bustling center of cultural and economic innovation." +
                                     "  As more people have come to settle, so too has its reputation grown.  You have grown in power and name" +
                                     " as its leader, and your people look towards you to define what their home means to them." +
                                     "\n\nYour community has evolved into a city, and productivity has improved.";
                         }
                         else if(settlementRank==2 && resources >= 200000){
                             resources-=200000;
                             settlementRank++;
                             settlementType="County";
                             resMult+=0.1;
                             milMult+=0.1;
                             goldMult+=0.1;
                             settlementUpgradeText="As your power grew, so too did your people.  Your land has become vast and influential, and " +
                                     "you now oversee the administration of many cities and villages." +
                                     "\n\nYour land has evolved into a county, and productivity has improved.";
                         }
                         else if(settlementRank==3 && resources >= 2000000){
                             resources-=2000000;
                             settlementRank++;
                             settlementType="Nation";
                             resMult+=0.1;
                             milMult+=0.1;
                             goldMult+=0.1;
                             settlementUpgradeText="Under your leadership, more counties have begun to form.  They are under the control of vassals, who" +
                                     " in turn receive their orders from you.  Your rule has become widespread, and you have earned the respect, and fear, of " +
                                     "other powerful rulers.  \n\nYour county has become a nation, and productivity has improved.";
                         }
                         else if(settlementRank==4 && resources >= 20000000){
                             resources-=20000000;
                             settlementRank++;
                             settlementType="Empire";
                             resMult+=0.1;
                             milMult+=0.1;
                             goldMult+=0.1;
                             settlementUpgradeText="What began as just a small group of settlers has become a sprawling empire.  Your power" +
                                     " and prestige are known to the world, and are the target of both envy and respect." +
                                     "\n\nYour nation has become an empire, and productivity from settlement size has reached its peak.";
                             invalidateOptionsMenu();
                         }
                         mainSettlementNameTV.setText("The " + settlementType + " of " + settlementName);

                         settlementUpgradeDialogue.setMessage(settlementUpgradeText);
                         settlementUpgradeDialogue.setPositiveButton("Continue", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}});
                         settlementUpgradeDialogue.show();

                     }
                 });
                 settlementUpgradeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                         dialog.cancel();
                     }
                 });
                 settlementUpgradeAlert.show();
                 return true;
             case R.id.helpMenu:
                 final AlertDialog.Builder helpAlert = new AlertDialog.Builder(this);
                 helpAlert.setTitle("Help");
                 helpAlert.setMessage("In this game, you play as the founder of a small settlement " +
                         "that will grow as you harvest resources, construct new buildings and " +
                         "research new technologies, trade for gold, and build an army." +
                         "\n\nResources are used to construct buildings and trade for gold." +
                         "\n\nGold is used to buy weapons and armor for your hero, as well as to hire soldiers " +
                         "to defend your settlement and invade other lands." +
                         "\n\nMilitary Power is the numeric value which represents your army's total strength.  " +
                         "It is the total of your hero's power and soldiers' power, with the appropriate " +
                         "bonuses applied through upgrades and choices made that influence your settlement." +
                         "\n\nBonuses to resource acquisition rate, military power and gold earned through trade " +
                         "are applied by upgrades purchased, forming a religion with the appropriate " +
                         "attributes, and choices made upon the start of a new game." +
                         "\n\nIf you successfully invade another settlement, you will gain resources proportional to its power.  " +
                         "Larger settlements yield larger gains.");
                 helpAlert.setPositiveButton("Ok", null);
                 AlertDialog helpDialog = helpAlert.show();
                 TextView messageText = (TextView)helpDialog.findViewById(android.R.id.message);
                 messageText.setGravity(Gravity.CENTER);
                 return true;
             case R.id.religionMenu:
                 Intent religionMenuIntent = new Intent(getApplicationContext(), religionPageActivity.class);
                 startActivity(religionMenuIntent);
                 return true;
             case R.id.militaryMenu:
                 Intent militaryMenuIntent = new Intent(getApplicationContext(), militaryPageActivity.class);
                 startActivity(militaryMenuIntent);
                 return true;
             case R.id.invasionMenu:
                 Intent invasionMenuIntent = new Intent(getApplicationContext(), invasionPageActivity.class);
                 startActivity(invasionMenuIntent);
                 return true;
             case R.id.tradeMenu:
                 Intent tradeMenuIntent = new Intent(getApplicationContext(), tradePageActivity.class);
                 startActivity(tradeMenuIntent);
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
     }


    @Override
    protected void onRestart(){
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        loadGame();
        setMainUI();
        if(formedReligion && !handledRelMults) { //If we've just formed a religion
                resMult+=relResMult;
                milMult+=relMilMult;
                militaryPower*=milMult;
                goldMult+=relGoldMult;
                handledRelMults=true;
                militaryPower = (soldierMP + heroMP) * milMult;
        }

        if(!didNG){ //If user just finished NG
            didNG=true;
            resMult = getDouble(prefs, "ngResMult", 1.0);
            milMult = getDouble(prefs, "ngMilMult", 1.0);
            goldMult = getDouble(prefs, "ngGoldMult", 1.0);
            heroName = prefs.getString("heroName", "Bob");
            settlementName = prefs.getString("settlementName", "Party Town");
            settlementType = "Encampment";
            resourceRate*=resMult;
            militaryPower*=milMult;
            setMainUI();
            invalidateOptionsMenu();
            Thread incrementThread = new Thread(runnable);
            incrementThread.start();
            Message msg = incrementHandler.obtainMessage();
            incrementHandler.sendMessage(msg);
        }
        super.onRestart();
    }

    @Override
    public void onPause(){
        saveGame();
        super.onPause();
    }


    double getDouble(SharedPreferences preferences, String key, double defaultValue) {
        if(!preferences.contains(key)) return defaultValue;
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

     SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
         return editor.putLong(key, Double.doubleToRawLongBits(value));
     }

     public void saveGame(){
         SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
         editor.putBoolean("didNG", didNG);
         editor.putBoolean("hasGame", hasGame);
         editor.putBoolean("formedReligion", formedReligion);
         editor.putBoolean("handledRelMults", handledRelMults);
         editor.putBoolean("hasBlacksmith", hasBlacksmith);
         editor.putBoolean("hasArmory", hasArmory);
         editor.putBoolean("hasCranny", hasCranny);
         editor.putBoolean("hasBarracks", hasBarracks);
         editor.putBoolean("hasTemple", hasTemple);
         editor.putBoolean("hasCropRotation", hasCropRotation);
         editor.putBoolean("hasMarketplace", hasMarketplace);
         editor.putInt("numLumberMills", numLumberMills);
         editor.putInt("numHuntersCamps", numHuntersCamps);
         editor.putInt("numQuarries", numQuarries);
         editor.putInt("numMines", numMines);
         editor.putInt("numFarms", numFarms);
         editor.putInt("numSmeltingFurnaces", numSmeltingFurnaces);
         editor.putInt("settlementRank", settlementRank);
         editor.putString("settlementType", settlementType);
         editor.putString("settlementName", settlementName);
         editor.putString("heroName", heroName);
         putDouble(editor, "resources", resources);
         putDouble(editor, "gold", gold);
         putDouble(editor, "militaryPower", militaryPower);
         putDouble(editor, "soldierMP", soldierMP);
         putDouble(editor, "heroMP", heroMP);
         putDouble(editor, "resMult", resMult);
         putDouble(editor, "goldMult", goldMult);
         putDouble(editor, "milMult", milMult);
         putDouble(editor, "soldierMilMult", milMult);
         putDouble(editor, "resourceRate", resourceRate);
         putDouble(editor, "baseResourceRate", baseResourceRate);
         putDouble(editor, "relResMult", relResMult);
         putDouble(editor, "relGoldMult", relGoldMult);
         putDouble(editor, "relMilMult", relMilMult);

         editor.apply();
     }

     public void loadGame(){ //Set appropriate variables to their sharedPrefs values, then set appropriate UI elements to reflect them
         SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
         didNG=prefs.getBoolean("didNG", false);
         hasGame=prefs.getBoolean("hasGame", false);
         formedReligion=prefs.getBoolean("formedReligion", false);
         handledRelMults=prefs.getBoolean("handledRelMults", false);
         hasBlacksmith=prefs.getBoolean("hasBlacksmith", false);
         hasArmory=prefs.getBoolean("hasArmory", false);
         hasCranny=prefs.getBoolean("hasCranny", false);
         hasBarracks=prefs.getBoolean("hasBarracks", false);
         hasTemple=prefs.getBoolean("hasTemple", false);
         hasCropRotation=prefs.getBoolean("hasCropRotation", false);
         hasMarketplace=prefs.getBoolean("hasMarketplace", false);
         numLumberMills=prefs.getInt("numLumberMills", 0);
         numHuntersCamps=prefs.getInt("numHuntersCamps", 0);
         numQuarries=prefs.getInt("numQuarries", 0);
         numMines=prefs.getInt("numMines", 0);
         numFarms=prefs.getInt("numFarms", 0);
         numSmeltingFurnaces=prefs.getInt("numSmeltingFurnaces", 0);
         settlementRank=prefs.getInt("settlementRank", 0);
         settlementType=prefs.getString("settlementType", "Smallish Community");
         settlementName=prefs.getString("settlementName", "Defaultington");
         heroName=prefs.getString("heroName", "Bobicus");
         resources=getDouble(prefs, "resources", 0);
         gold=getDouble(prefs, "gold", 0);
         militaryPower=getDouble(prefs, "militaryPower", 0);
         soldierMP=getDouble(prefs, "soldierMP", 0);
         heroMP=getDouble(prefs, "heroMP", 0);
         resMult=getDouble(prefs, "resMult", 1.0);
         goldMult=getDouble(prefs, "goldMult", 1.0);
         milMult=getDouble(prefs, "milMult", 1.0);
         soldierMilMult=getDouble(prefs, "soldierMilMult", 1.0);
         resourceRate=getDouble(prefs, "resourceRate", 1.0);
         baseResourceRate=getDouble(prefs, "baseResourceRate", 1.0);
         relResMult=getDouble(prefs, "relResMult", 0);
         relGoldMult=getDouble(prefs, "relGoldMult", 0);
         relMilMult=getDouble(prefs, "relMilMult", 0);
     }

    public void setMainUI(){
        mainSettlementNameTV.setVisibility(VISIBLE);
        resourceTV.setVisibility(VISIBLE);
        militaryPowerTV.setVisibility(VISIBLE);
        goldTV.setVisibility(VISIBLE);
        gatheringTV.setVisibility(VISIBLE);
        lumberMillButton.setVisibility(VISIBLE);
        huntersCampButton.setVisibility(VISIBLE);
        quarryButton.setVisibility(VISIBLE);
        mineButton.setVisibility(VISIBLE);
        farmButton.setVisibility(VISIBLE);
        smeltingFurnaceButton.setVisibility(VISIBLE);
        upgradesTV.setVisibility(VISIBLE);
        armoryButton.setVisibility(VISIBLE);
        blacksmithButton.setVisibility(VISIBLE);
        crannyButton.setVisibility(VISIBLE);
        barracksButton.setVisibility(VISIBLE);
        templeButton.setVisibility(VISIBLE);
        cropRotationButton.setVisibility(VISIBLE);
        marketplaceButton.setVisibility(VISIBLE);

        mainSettlementNameTV.setText("The " + settlementType + " of " + settlementName);
        lumberMillButton.setText("Lumber Mill (" + numLumberMills + "): +0.1 R/s (Cost: 20R)");
        huntersCampButton.setText("Hunter's Camp (" + numHuntersCamps + "): +1.0 R/s (Cost: 200R)");
        quarryButton.setText("Quarry (" + numQuarries + "): +10 R/s (Cost: 2,000R)");
        mineButton.setText("Mine (" + numMines + "): +100 R/s (Cost: 20,000R)");
        farmButton.setText("Farm (" + numFarms + "): +1,000 R/s (Cost: 200,000 R)");
        smeltingFurnaceButton.setText("Smelting Furnace (" + numSmeltingFurnaces + "): +10,000 R/s (Cost: 2,000,000R)");

        resourceTV.setText("Resources: " + (double)Math.round(resources*100)/100 + " (" + (double)Math.round(resourceRate*100)/100 + "/s" + ")" + " ");
        militaryPowerTV.setText("Military Power: " + (double)Math.round(militaryPower*100)/100 + " (" + (double)Math.round(milMult*100)/100 + ")");
        goldTV.setText("Gold: " + (double)Math.round(gold*100)/100 + " (" + (double)Math.round(goldMult*100)/100 + ")");

        LinearLayout mainLL = (LinearLayout) findViewById(R.id.mainLinearLayout);
        if(hasArmory)mainLL.removeView(armoryButton);
        if(hasBlacksmith)mainLL.removeView(blacksmithButton);
        if(hasCranny)mainLL.removeView(crannyButton);
        if(hasTemple)mainLL.removeView(templeButton);
        if(hasBarracks)mainLL.removeView(barracksButton);
        if(hasCropRotation)mainLL.removeView(cropRotationButton);
        if(hasMarketplace)mainLL.removeView(marketplaceButton);
        if(hasArmory && hasBlacksmith && hasCranny && hasTemple && hasBarracks && hasCropRotation && hasMarketplace) mainLL.removeView(upgradesTV);
        if(formedReligion)religionOption.setTitle("Religion");
    }

    public void randomEvent(int id){
        int secondaryRandom=0;
        double lostResources=0;
        switch(id) {
            case 0: //Attacked by something
                secondaryRandom = eventRandom.nextInt(3);
                if (secondaryRandom == 0) { //Raiders
                    if (hasCranny) lostResources = resources / 20;
                    else lostResources = resources / 10;
                    Toast.makeText(getApplicationContext(), "One of your buildings was looted by raiders!  You've lost some resources", Toast.LENGTH_LONG).show();
                    resources -= lostResources;
                } else if (secondaryRandom == 2) { //Werewolves
                    if (hasCranny) lostResources = resources / 10;
                    else lostResources = resources / 5;
                    Toast.makeText(getApplicationContext(), "Some of your buildings were ransacked by werewolves!  You've lost some resources", Toast.LENGTH_LONG).show();
                    resources -= lostResources;
                } else if (secondaryRandom == 3) { //Another settlementType
                    if (hasCranny) lostResources = resources / 5;
                    else lostResources = resources / 3;
                    Toast.makeText(getApplicationContext(), "Your " + settlementType + "was attacked by the war party of another " + settlementType + "!  You've lost some resources", Toast.LENGTH_LONG).show();
                    resources -= lostResources;
                }
                break;
            case 1: //Trading post built
                Toast.makeText(getApplicationContext(), "A group of traders have opened a trading post in your " + settlementType +".  You've gained some gold.", Toast.LENGTH_LONG).show();
                if(gold>100) gold*=1.1;
                else gold+=10;
                break;
            case 2: //Omens (2-5)
                Toast.makeText(getApplicationContext(), "The villagers claim they saw an omen in the stars...", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "You had visions in your dreams last night...", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(getApplicationContext(), "You spot a large murder of crows flying towards your settlement...", Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "Your feel an unnatural chill in the air...", Toast.LENGTH_LONG).show();
                break;
            case 6: //Cave found
                Toast.makeText(getApplicationContext(), "Your workers found a cave with valuable mineral deposits.  You've gained some resources.  ", Toast.LENGTH_LONG).show();
                if(resources>1000) resources*=1.1;
                else resources+=100;
                break;
            case 7: //Fire
                Toast.makeText(getApplicationContext(), "One of your buildings caught fire, scorching the supplies inside.  You've lost some resources", Toast.LENGTH_LONG).show();
                resources*=0.9;
                break;
        }
    }
 }