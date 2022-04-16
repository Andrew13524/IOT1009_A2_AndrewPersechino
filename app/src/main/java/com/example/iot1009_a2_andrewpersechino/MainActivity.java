// ANDREW PERSECHINO
// A00228632

package com.example.iot1009_a2_andrewpersechino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
enum Team {LEFT, RIGHT};

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    TextView rightScoreTextView;
    TextView leftScoreTextView;
    Button increaseButton;
    Button decreaseButton;
    RadioGroup pointsRadioGroup;
    RadioButton onePointRadioButton;
    RadioButton twoPointRadioButton;
    RadioButton threePointRadioButton;
    SwitchCompat teamSwitchCompat;
    Team team;
    SharedPreferences prefs;
    int rightTeamScore;
    int leftTeamScore;
    int adjustAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Setting view components based on ID
        rightScoreTextView    = findViewById(R.id.rightScoreTextView);
        leftScoreTextView     = findViewById(R.id.leftScoreTextView);
        increaseButton        = findViewById(R.id.increaseButton);
        decreaseButton        = findViewById(R.id.decreaseButton);
        pointsRadioGroup      = findViewById(R.id.pointsRadioGroup);
        onePointRadioButton   = findViewById(R.id.onePointRadioButton);
        twoPointRadioButton   = findViewById(R.id.twoPointRadioButton);
        threePointRadioButton = findViewById(R.id.threePointRadioButton);
        teamSwitchCompat      = findViewById(R.id.teamSwitchCompat);

        // Using current instance as listeners
        increaseButton       .setOnClickListener(this);
        decreaseButton       .setOnClickListener(this);
        onePointRadioButton  .setOnCheckedChangeListener(this);
        twoPointRadioButton  .setOnCheckedChangeListener(this);
        threePointRadioButton.setOnCheckedChangeListener(this);
        teamSwitchCompat     .setOnCheckedChangeListener(this);

        // Setting default values
        team           = Team.LEFT;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        long id = item.getItemId();

        // If about page was pressed
        if(id == R.id.about){
            // Create toast
            Toast.makeText(this, "Created by: Andrew Persechino, A00228632, IOT1009", Toast.LENGTH_LONG)
                    .show();
            return true;
        }
        // If about page was pressed
        if(id == R.id.settings){
            // Start settings activity
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // When either increase/decrease buttons are clicked
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.increaseButton:
                UpdateTeamScore((float)adjustAmount);
                break;
            case R.id.decreaseButton:
                UpdateTeamScore((float)adjustAmount * -1);
                break;
        }
    }

    // When team switch is changed, set selected team to the opposite one
    // or when radio buttons are selected, set according adjust amount
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()){
            case R.id.teamSwitchCompat:
                if(compoundButton.isChecked()) team = Team.RIGHT;
                else                           team = Team.LEFT;
                break;
            case R.id.onePointRadioButton:
                if(compoundButton.isChecked()) adjustAmount = 1;
                break;
            case R.id.twoPointRadioButton:
                if(compoundButton.isChecked()) adjustAmount = 2;
                break;
            case R.id.threePointRadioButton:
                if(compoundButton.isChecked()) adjustAmount = 3;
                break;
        }
    }

    // Update the score/textview based on parameter
    public void UpdateTeamScore(float adjustAmount){
        switch (team){
            case LEFT:
                leftTeamScore += adjustAmount;
                if(leftTeamScore < 0) leftTeamScore = 0;
                leftScoreTextView.setText(Integer.toString(leftTeamScore));
                break;
            case RIGHT:
                rightTeamScore += adjustAmount;
                if(rightTeamScore < 0) rightTeamScore = 0;
                rightScoreTextView.setText(Integer.toString(rightTeamScore));
                break;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Loading values
        leftScoreTextView.setText(prefs.getString("leftTeamScore", "0"));
        rightScoreTextView.setText(prefs.getString("rightTeamScore", "0"));
        adjustAmount = prefs.getInt("adjustAmount", 1);
        if(adjustAmount == 1){
            pointsRadioGroup.check(R.id.onePointRadioButton);
        }
        else if(adjustAmount == 2){
            pointsRadioGroup.check(R.id.twoPointRadioButton);
        }
        else if(adjustAmount == 3){
            pointsRadioGroup.check(R.id.threePointRadioButton);
        }
    }

    @Override
    protected void onStop(){
        SharedPreferences.Editor editor = prefs.edit();
        boolean saveValues = prefs.getBoolean("save_values_pref", false);

        // Saving values
        if(saveValues){
            editor.putString("leftTeamScore", leftScoreTextView.getText().toString());
            editor.putString("rightTeamScore", rightScoreTextView.getText().toString());
            editor.putInt("adjustAmount", adjustAmount);
        }
        else{
            editor.clear();
            editor.putBoolean("save_values_pref", false);
        }
        super.onStop();
    }
}