package com.example.ryan.PathToAnEmpire;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class religionPageActivity extends AppCompatActivity {
    int religionChoiceProgress;
    double relResMult=0.0, relMilMult=0.0, relGoldMult=0.0;
    String religionOrigin, religionStructure, religionValues, religionInvolvement, religionName, religionGods;
    Boolean formedReligion=false;

    public static final String PREFNAME = "Prefs";

    //Pre-formation UI elements
    TextView religionFormingReligion;
    Button religionSubmitButton;
    EditText religionSubmissionET;
    RadioGroup religionChoices;
    RadioButton religionChoiceOne, religionChoiceTwo, religionChoiceThree;

    //Post-formation UI elements
    TextView religionReligionHeader, religionNameTV, religionOriginTV, religionStructureTV, religionInvolvementTV, religionGodsTV, religionValuesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_religion_page);

        religionFormingReligion=(TextView)findViewById(R.id.religionFormingReligion);
        religionSubmitButton=(Button)findViewById(R.id.religionSubmitButton);
        religionReligionHeader=(TextView)findViewById(R.id.religionReligionHeader);
        religionNameTV=(TextView)findViewById(R.id.religionNameTV);
        religionOriginTV=(TextView)findViewById(R.id.religionOriginTV);
        religionStructureTV=(TextView)findViewById(R.id.religionStructureTV);
        religionInvolvementTV =(TextView)findViewById(R.id.religionInvolvementTV);
        religionGodsTV=(TextView)findViewById(R.id.religionGodsTV);
        religionValuesTV=(TextView)findViewById(R.id.religionValuesTV);
        religionSubmissionET=(EditText)findViewById(R.id.religionSubmissionET);
        religionChoices=(RadioGroup)findViewById(R.id.religionChoices);
        religionChoiceOne=(RadioButton)findViewById(R.id.religionChoiceOne);
        religionChoiceTwo=(RadioButton)findViewById(R.id.religionChoiceTwo);
        religionChoiceThree=(RadioButton)findViewById(R.id.religionChoiceThree);

        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        formedReligion = prefs.getBoolean("formedReligion", false);

        if(!formedReligion) {
            religionReligionHeader.setVisibility(GONE);
            religionNameTV.setVisibility(GONE);
            religionOriginTV.setVisibility(GONE);
            religionStructureTV.setVisibility(GONE);
            religionInvolvementTV.setVisibility(GONE);
            religionGodsTV.setVisibility(GONE);
            religionStructureTV.setVisibility(GONE);
            religionSubmissionET.setVisibility(GONE);
            religionChoices.setVisibility(GONE);
            religionValuesTV.setVisibility(GONE);
        }
        else{
            //Set formation-related UI elements to GONE
            LinearLayout relPageLL;
            relPageLL = (LinearLayout) findViewById(R.id.relPageLayout);
            relPageLL.removeView(religionFormingReligion);
            relPageLL.removeView(religionSubmitButton);
            relPageLL.removeView(religionSubmissionET);
            relPageLL.removeView(religionChoices);

            //Set up the post-formation UI with the appropriate values
            setReligionUI();

        }

        religionSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFNAME, MODE_PRIVATE).edit();
                religionChoiceProgress++;

                if (religionChoiceProgress == 1) {
                    religionFormingReligion.setText("How has your religion come into being?");
                    setReligionChoices("Philosophical reasoning led to the creation of a new belief system (Boost to resources)",
                            "I had visions of the one true religion (Boost to military power from troops)",
                            "Prophets and priests from other lands came and preached to us (Get more gold from trading)");
                    religionSubmitButton.setText("Next");
                    religionChoices.setVisibility(VISIBLE);


                } else if (religionChoiceProgress == 2) {
                    if(religionChoiceOne.isChecked()){
                        relResMult+=.02; //2% resource inc boost
                        religionOrigin="Origin: Philosophical Reasoning";

                    }
                    else if(religionChoiceTwo.isChecked()){
                        relMilMult+=.05; //5% military power boost
                        religionOrigin="Origin: Visions of the True Religion";
                    }
                    else if(religionChoiceThree.isChecked()){
                        relGoldMult+=.2; //20% boost to trade-acquired gold
                        religionOrigin="Origin: Conversion by Another Land";
                    }
                    religionFormingReligion.setText("Is there a hierarchy to this religion?");
                    setReligionChoices("The religion is not formally organized.  Stories, ideas and beliefs spread freely by the people through word of mouth and legends (Boost to resources)",
                            "The new religion is state-mandated.  Nonbelievers and heretics are shunned.  (Boost to military power from troops)",
                            "The religion is well-organized, and offerings are frequently taxed by the state. (Get more gold from trading)");

                } else if (religionChoiceProgress == 3) {
                    if(religionChoiceOne.isChecked()){
                        relResMult+=.02; //2% resource inc boost
                        religionStructure="Hierarchy: Unorganized";
                    }
                    else if(religionChoiceTwo.isChecked()){
                        relMilMult +=.05; //5% military power boost
                        religionStructure="Hierarchy: State-Mandated";
                    }
                    else if(religionChoiceThree.isChecked()){
                        relGoldMult+=.2; //20% boost to trade-acquired gold
                        religionStructure="Hierarchy: Well-Organized";
                    }
                    editor.putString("religionOrigin", religionOrigin);
                    religionFormingReligion.setText("What kind of life does your religion emphasize?");
                    setReligionChoices("A wholesome, peaceful life focusing on development of the soul (Boost to resources)",
                            "A life moderated by a strict set of values (Boost to military power from troops)",
                            "Glory through knowledge of the true faith and the peaceful conversion of other lands (Get more gold from trading)");

                } else if (religionChoiceProgress == 4) {
                    if(religionChoiceOne.isChecked()){
                        relResMult+=.02; //2% resource inc boost
                        religionValues="Life Philosophy: Development of the Soul";
                    }
                    else if(religionChoiceTwo.isChecked()){
                        relMilMult+=.05; //5% military power boost
                        religionValues="Life Philosophy: Moderation and Strength";
                    }
                    else if(religionChoiceThree.isChecked()){
                        relGoldMult+=.2; //20% boost to trade-acquired gold
                        religionValues="Life Philosophy: Knowledge of the Faith";
                    }
                    religionFormingReligion.setText("How closely is your religion connected with the state?");
                    setReligionChoices("Religion and state are considered separate entities (Boost to resources)",
                            "State and religion are strictly intertwined.  Officials in the religion often hold important state positions, and vice versa (Boost to military power from troops)",
                            "Religion is loosely associated with the state through the common culture of its followers (Get more gold from trading)");

                } else if(religionChoiceProgress == 5) {
                    if(religionChoiceOne.isChecked()){
                        relResMult+=.02; //2% resource inc boost
                        religionInvolvement="Involvement: Separated from the State";
                    }
                    else if(religionChoiceTwo.isChecked()){
                        relMilMult+=.05; //5% military power boost
                        religionInvolvement="Involvement: Closely Tied to the State";
                    }
                    else if(religionChoiceThree.isChecked()){
                        relGoldMult+=.2; //20% boost to trade-acquired gold
                        religionInvolvement="Involvement: Loosely Involved in State Matters";
                    }
                    religionFormingReligion.setText("Enter the name, or names, of any god or gods your religion idolizes.");
                    religionChoices.setVisibility(GONE);
                    religionSubmissionET.setVisibility(VISIBLE);

                } else if(religionChoiceProgress == 6) {
                    if(!religionSubmissionET.getText().toString().equals("")) religionGods = religionSubmissionET.getText().toString();
                    else religionGods = "None";
                    religionFormingReligion.setText("What is the name of your religion?");
                    religionSubmitButton.setText("Done");

                } else {
                    if(!religionSubmissionET.getText().toString().equals("")) religionName = religionSubmissionET.getText().toString();
                    else religionName = "Defaultism";
                    formedReligion=true;
                    putDouble(editor,"relResMult", relResMult);
                    putDouble(editor,"relMilMult", relMilMult);
                    putDouble(editor,"relGoldMult", relGoldMult);
                    editor.putBoolean("formedReligion", formedReligion);
                    editor.putString("religionName", religionName);
                    editor.putString("religionStructure", religionStructure);
                    editor.putString("religionValues", religionValues);
                    editor.putString("religionInvolvement", religionInvolvement);
                    editor.putString("religionOrigin", religionOrigin);
                    editor.putString("religionGods", religionGods);

                    editor.apply();

                    //Remove unnecessary UI elements
                    LinearLayout relPageLL;
                    relPageLL = (LinearLayout) findViewById(R.id.relPageLayout);
                    relPageLL.removeView(religionFormingReligion);
                    relPageLL.removeView(religionSubmitButton);
                    relPageLL.removeView(religionSubmissionET);
                    relPageLL.removeView(religionChoices);

                    //Set non-formation-related UI elements to visible and apply the appropriate strings
                    setReligionUI();
                }
            }
        });
    }

    public void setReligionChoices(String choiceOne, String choiceTwo, String choiceThree){
        religionChoiceOne.setText(choiceOne);
        religionChoiceTwo.setText(choiceTwo);
        religionChoiceThree.setText(choiceThree);
    }

    @Override
    public void onBackPressed() {
      if(formedReligion==true) super.onBackPressed();
    }

    SharedPreferences.Editor putDouble(SharedPreferences.Editor editor, String key, double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }

    public void setReligionUI(){
        //Set non-formation-related UI elements to visible
        religionReligionHeader.setVisibility(VISIBLE);
        religionNameTV.setVisibility(VISIBLE);
        religionOriginTV.setVisibility(VISIBLE);
        religionStructureTV.setVisibility(VISIBLE);
        religionInvolvementTV.setVisibility(VISIBLE);
        religionGodsTV.setVisibility(VISIBLE);
        religionValuesTV.setVisibility(VISIBLE);

        //Retrieve religion variables from sharedPrefs
        SharedPreferences prefs = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        religionName = prefs.getString("religionName", "Default");
        religionOrigin = prefs.getString("religionOrigin", "Default");
        religionStructure = prefs.getString("religionStructure", "Default");
        religionValues = prefs.getString("religionValues", "Default");
        religionInvolvement = prefs.getString("religionInvolvement", "Default");
        religionGods = prefs.getString("religionGods", "Default");

        //Set TVs to reflect sharedPrefs religion variables
        religionNameTV.setText(religionName);
        religionOriginTV.setText(religionOrigin);
        religionStructureTV.setText(religionStructure);
        religionValuesTV.setText(religionValues);
        religionInvolvementTV.setText(religionInvolvement);
        religionGodsTV.setText("Gods: " + religionGods);
    }
}