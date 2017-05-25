package com.example.ryan.PathToAnEmpire;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class NewGameActivity extends AppCompatActivity{

    int choiceProgress = 0; //Indicates player progress through new game intro dialogues
    Button ngProgressButton;
    TextView newGameTV;
    EditText nameET;
    RadioGroup ngChoices;
    RadioButton ngChoiceOne, ngChoiceTwo, ngChoiceThree;

    double ngResMult = 1.0; //Resource gain multiplier
    double ngMilMult = 1.0; //Military power multiplier
    double ngGoldMult = 1.0; //Gold multiplier when trading

    public static final String PREFNAME = "Prefs";
    String heroName, settlementName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        newGameTV = (TextView) findViewById(R.id.newGameTV);
        nameET = (EditText) findViewById(R.id.nameET);
        ngProgressButton = (Button) findViewById(R.id.ngProgressButton);
        ngChoices = (RadioGroup) findViewById(R.id.ngChoices);
        ngChoiceOne = (RadioButton) findViewById(R.id.ngChoiceOne);
        ngChoiceTwo = (RadioButton) findViewById(R.id.ngChoiceTwo);
        ngChoiceThree = (RadioButton) findViewById(R.id.ngChoiceThree);

        ngChoices.setVisibility(GONE);
        nameET.setVisibility(GONE);

        ngProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choiceProgress++;

                if (choiceProgress == 1) {
                    newGameTV.setText("Why did you leave your home to settle here?");
                    setNGChoices("Wanderlust (Boost to resources)",
                            "I was exiled from my home (Boost to military power)",
                            "The desire for new opportunities in life (Get more gold from trading)");
                    ngProgressButton.setText("Next");
                    ngChoices.setVisibility(VISIBLE);
                    nameET.setVisibility(GONE);

                } else if (choiceProgress == 2) {
                    if(ngChoiceOne.isChecked()) ngResMult+=.02; //2% resource inc boost
                    else if(ngChoiceTwo.isChecked()) ngMilMult+=.05; //5% military power boost
                    else if(ngChoiceThree.isChecked()) ngGoldMult+=.2; //20% boost to trade-acquired gold
                    newGameTV.setText("What kind of place is your new home?");
                    setNGChoices("A bountiful forest (Boost to resources)",
                            "A harsh, brutal landscape (Boost to military power)",
                            "A mountainous region rich in minerals (Get more gold from trading)");

                } else if (choiceProgress == 3) {
                    if(ngChoiceOne.isChecked()) ngResMult+=.02; //2% resource inc boost
                    else if(ngChoiceTwo.isChecked()) ngMilMult+=.05; //5% military power boost
                    else if(ngChoiceThree.isChecked()) ngGoldMult+=.2; //20% boost to trade-acquired gold
                    newGameTV.setText("How will you lead your people to prosperity?");
                    setNGChoices("Through peaceful expansion (Boost to resources)",
                            "Through conquest and the spoils of war (Boost to military power)",
                            "Through trade (Get more gold from trading)");

                } else if (choiceProgress == 4) {
                    if(ngChoiceOne.isChecked()) ngResMult+=.02; //2% resource inc boost
                    else if(ngChoiceTwo.isChecked()) ngMilMult+=.05; //5% military power boost
                    else if(ngChoiceThree.isChecked()) ngGoldMult+=.2; //20% boost to trade-acquired gold
                    newGameTV.setText("What kind of leader do you wish to be?");
                    setNGChoices("An example for my people (Boost to resources)",
                            "A firm, steady ruler (Boost to military power)",
                            "A captain of industry (Get more gold from trading)");

                } else if(choiceProgress == 5) {
                    if(ngChoiceOne.isChecked()) ngResMult+=.02; //2% resource inc boost
                    else if(ngChoiceTwo.isChecked()) ngMilMult+=.05; //5% military power boost
                    else if(ngChoiceThree.isChecked()) ngGoldMult+=.2; //20% boost to trade-acquired gold
                    newGameTV.setText("What is your name?");
                    ngChoices.setVisibility(GONE);
                    nameET.setVisibility(VISIBLE);

                } else if(choiceProgress == 6) {
                    if(!nameET.getText().toString().equals("")) heroName = nameET.getText().toString();
                    else heroName = "Bob";

                    nameET.setText("");
                    newGameTV.setText("What is the name of your new settlement?");
                    ngProgressButton.setText("Begin!");

                } else { //Return to main activity
                    if(!nameET.getText().toString().equals("")) settlementName = nameET.getText().toString();
                    else settlementName = "Party Town";
                    SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
                    putDouble(editor,"ngResMult", ngResMult);
                    putDouble(editor,"ngMilMult", ngMilMult);
                    putDouble(editor,"ngGoldMult", ngGoldMult);
                    editor.putString("heroName", heroName);
                    editor.putString("settlementName", settlementName);
                    editor.putBoolean("hasGame", true);
                    editor.commit();
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {} //When the user hits back in the NG menu, do nothing.

    public void setNGChoices(String choiceOne, String choiceTwo, String choiceThree){
        ngChoiceOne.setText(choiceOne);
        ngChoiceTwo.setText(choiceTwo);
        ngChoiceThree.setText(choiceThree);
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }
}