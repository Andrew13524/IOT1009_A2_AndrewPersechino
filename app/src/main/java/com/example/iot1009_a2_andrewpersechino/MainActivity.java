// ANDREW PERSECHINO
// A00228632

package com.example.iot1009_a2_andrewpersechino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    int rightTeamScore;
    int leftTeamScore;
    int adjustAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        rightTeamScore = 0;
        leftTeamScore  = 0;
        adjustAmount   = 1;
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
}