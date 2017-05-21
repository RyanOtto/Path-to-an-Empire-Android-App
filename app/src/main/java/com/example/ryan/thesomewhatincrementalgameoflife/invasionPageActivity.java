package com.example.ryan.thesomewhatincrementalgameoflife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.ryan.thesomewhatincrementalgameoflife.NewGameActivity.PREFNAME;

public class invasionPageActivity extends AppCompatActivity implements View.OnClickListener {

    TextView invasionMPTV, invasionResourcesTV, heroMPTV, soldierMPTV;
    Button svButton, mvButton, lvButton, scButton, mcButton, lcButton, skButton, mkButton, lkButton, seButton, meButton, leButton;
    double invasionResources, invasionMP, invasionSoldierMP, invasionHeroMP, invasionMilMult, invasionSoldierMult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invasion_page);

        invasionMPTV=(TextView)findViewById(R.id.invasionMPTV);
        invasionResourcesTV=(TextView)findViewById(R.id.invasionResourcesTV);
        heroMPTV=(TextView)findViewById(R.id.heroMPTV);
        soldierMPTV=(TextView)findViewById(R.id.soldierMPTV);
        invasionResourcesTV=(TextView)findViewById(R.id.invasionResourcesTV);
        svButton=(Button)findViewById(R.id.svButton);
        mvButton=(Button)findViewById(R.id.mvButton);
        lvButton=(Button)findViewById(R.id.lvButton);
        scButton=(Button)findViewById(R.id.scButton);
        mcButton=(Button)findViewById(R.id.mcButton);
        lcButton=(Button)findViewById(R.id.lcButton);
        skButton=(Button)findViewById(R.id.skButton);
        mkButton=(Button)findViewById(R.id.mkButton);
        lkButton=(Button)findViewById(R.id.lkButton);
        seButton=(Button)findViewById(R.id.seButton);
        meButton=(Button)findViewById(R.id.meButton);
        leButton=(Button)findViewById(R.id.leButton);

        invasionLoad();
        setupInvasionUI();

        svButton.setOnClickListener(this);
        mvButton.setOnClickListener(this);
        lvButton.setOnClickListener(this);
        scButton.setOnClickListener(this);
        mcButton.setOnClickListener(this);
        lcButton.setOnClickListener(this);
        skButton.setOnClickListener(this);
        mkButton.setOnClickListener(this);
        lkButton.setOnClickListener(this);
        seButton.setOnClickListener(this);
        meButton.setOnClickListener(this);
        leButton.setOnClickListener(this);
    }

    public void invasion(double playerMP, double enemyMP){
        Random random=new Random();
        double powerDifference=1.0; //By default, we assume both forces are equal in power
        if(playerMP!=enemyMP) powerDifference=Math.max(playerMP, enemyMP)/Math.min(playerMP, enemyMP); //How many times stronger the stronger combatant is than the weaker one

        if(invasionSoldierMP<10) Toast.makeText(getApplicationContext(), "You need more troops to accompany you to battle (minimum soldier power of 10)!", Toast.LENGTH_SHORT).show();
        else if(enemyMP>playerMP && powerDifference>=5) {
            invasionSoldierMP*=.10; //Reduce player troop power 90%
            Toast.makeText(getApplicationContext(), "Your troops were slaughtered by the enemy's massive defensive force.", Toast.LENGTH_SHORT).show();
            setupInvasionUI();
        }

        else if(enemyMP>playerMP && powerDifference>=4 && powerDifference<5){
            invasionSoldierMP*=.3; //Reduce player troop power 70%
            Toast.makeText(getApplicationContext(),  "Your forces have been shattered by a much larger opposition.", Toast.LENGTH_SHORT).show();
            setupInvasionUI();
        }

        else if(enemyMP>playerMP && powerDifference>=3 && powerDifference<4){
            if(random.nextInt(100)>=80){//20% chance of victory
                invasionSoldierMP*=.65; //Reduce player troop power 35%
                invasionResources+=(enemyMP*1.2)*20;
                Toast.makeText(getApplicationContext(), "Your leadership overcame a much larger foe", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
            else{
                invasionSoldierMP*=.50; //Reduce player troop power 50%
                Toast.makeText(getApplicationContext(), "Your forces were outmatched by a sizably larger army", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }

        }

        else if(enemyMP>playerMP && powerDifference>=2 && powerDifference<3){
            if(random.nextInt(100)>=60){//40% chance of victory
                invasionSoldierMP*=.75; //Reduce player troop power 25%
                invasionResources+=(enemyMP*1.2)*20;
                Toast.makeText(getApplicationContext(), "You have bested a larger enemy", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
            else{
                invasionSoldierMP*=.65; //Reduce player troop power 35%
                Toast.makeText(getApplicationContext(), "You have lost to a larger force" , Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
        }


        else if(enemyMP>=playerMP && powerDifference>=1 && powerDifference<2){
            if(random.nextInt(100)>=40){//60% chance of victory
                invasionSoldierMP*=.85; //Reduce player troop power between 15%
                invasionResources+=(enemyMP*1.2)*20;
                Toast.makeText(getApplicationContext(), "You've defeated the enemy in a relatively even battle", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
            else{
                invasionSoldierMP*=.70; //Reduce player troop power 30%
                Toast.makeText(getApplicationContext(), "You've been bested by the enemy in a fair fight", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
        }

        else if(playerMP>enemyMP && powerDifference>=2 && powerDifference<=3){
            if(random.nextInt(100)>=20){//80% chance of victory
                invasionSoldierMP*=.85; //Reduce player troop power between 15%
                invasionResources+=(enemyMP*1.2)*20;
                Toast.makeText(getApplicationContext(), "You've swiftly defeated a smaller force", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
            else{
                invasionSoldierMP*=.75; //Reduce player troop power 25%
                Toast.makeText(getApplicationContext(), "You've been defeated by a smaller force", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
        }

        else if(playerMP>enemyMP && powerDifference>=3){
            if(random.nextInt(100)>=10) {//90% chance of victory
                invasionSoldierMP *= .90; //Reduce player troop power 10%
                invasionResources+=(enemyMP*1.2)*20;
                Toast.makeText(getApplicationContext(), "You've easily defeated a much smaller force", Toast.LENGTH_SHORT).show();
            }
            else{
                invasionSoldierMP*=.85; //Reduce player troop power 15%
                Toast.makeText(getApplicationContext(), "You've been defeated by a much smaller force", Toast.LENGTH_SHORT).show();
                setupInvasionUI();
            }
            setupInvasionUI();
        }
    }

    @Override
    public void onClick(View v) {
        double enemyMP=0.0;
        Random enemyMPGen=new Random();
        switch(v.getId()){
            case R.id.svButton:
                enemyMP=25+enemyMPGen.nextInt(50-25+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.mvButton:
                enemyMP=75+enemyMPGen.nextInt(150-75+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.lvButton:
                enemyMP=250+enemyMPGen.nextInt(500-250+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.scButton:
                enemyMP=500+enemyMPGen.nextInt(1500-500+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.mcButton:
                enemyMP=2500+enemyMPGen.nextInt(5000-2500+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.lcButton:
                enemyMP=5000+enemyMPGen.nextInt(15000-5000+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.skButton:
                enemyMP=25000+enemyMPGen.nextInt(50000-25000+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.mkButton:
                enemyMP=75000+enemyMPGen.nextInt(150000-75000+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.lkButton:
                enemyMP=170000+enemyMPGen.nextInt(300000-170000+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.seButton:
                enemyMP=200000+enemyMPGen.nextInt(500000-200000);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.meButton:
                enemyMP=750000+enemyMPGen.nextInt(1000000-750000+1);
                invasion(invasionMP, enemyMP);
                break;
            case R.id.leButton:
                enemyMP=1500000+enemyMPGen.nextInt(1500000-1250000+1);
                invasion(invasionMP, enemyMP);
                break;
        }

    }

    public void invasionLoad(){
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        invasionResources=getDouble(prefs, "resources", 0);
        invasionMP=getDouble(prefs, "militaryPower", 0);
        invasionSoldierMP=getDouble(prefs, "soldierMP", 0);
        invasionSoldierMult=getDouble(prefs, "soldierMilMult", 0);
        invasionMilMult=getDouble(prefs, "milMult", 0);
        invasionHeroMP=getDouble(prefs, "heroMP", 0);

    }

    public void invasionSave(){
        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
        putDouble(editor, "militaryPower", invasionMP);
        putDouble(editor, "soldierMP", invasionSoldierMP);
        putDouble(editor, "resources", invasionResources);
        editor.apply();
    }

    public void setupInvasionUI(){
        invasionMP = ( ( (invasionSoldierMP*invasionSoldierMult) + invasionHeroMP) ) * invasionMilMult;

        invasionMPTV.setText("Total Military Power: " + (double)Math.round(invasionMP*100)/100);
        heroMPTV.setText("Hero Military Power: " + (double)Math.round(invasionHeroMP*100)/100);
        soldierMPTV.setText("Soldier Military Power: " + (double)Math.round(invasionSoldierMP*100)/100);
        invasionResourcesTV.setText("Resources: " + (double)Math.round(invasionResources*100)/100);
    }

    @Override
    protected void onPause(){
        invasionSave();
        super.onPause();
    }

    @Override
    protected void onRestart(){
        invasionLoad();
        setupInvasionUI();
        super.onRestart();
    }

    double getDouble(SharedPreferences preferences, String key, double defaultValue) {
        if(!preferences.contains(key)) return defaultValue;
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }
}