package com.example.android.mathquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Global Variables
    int score = 0;
    RadioButton answer1RadioButtonPlus;
    CheckBox answer2CheckBox1, answer2CheckBox2, answer2CheckBox3;
    EditText answer3EditText, answer4EditText, answer5EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up components
        answer1RadioButtonPlus = (RadioButton)findViewById(R.id.answer1RadioButtonPlus);
        answer2CheckBox1 = (CheckBox)findViewById(R.id.answer2CheckBox1);
        answer2CheckBox2 = (CheckBox)findViewById(R.id.answer2CheckBox2);
        answer2CheckBox3 = (CheckBox)findViewById(R.id.answer2CheckBox3);
        answer3EditText = (EditText)findViewById(R.id.answer3EditText);
        answer4EditText = (EditText)findViewById(R.id.answer4EditText);
        answer5EditText = (EditText)findViewById(R.id.answer5EditText);
    }

    @Override
    public void onClick(View v) {
        ///set score to zero if not already
        score = 0;

        // Check if answer 1 is correct
        if (answer1RadioButtonPlus.isChecked()){
            score = score + 20;
            System.out.println(score);
        }

        // Check if answer 1 is correct
        if (answer2CheckBox1.isChecked() && answer2CheckBox2.isChecked() && !answer2CheckBox3.isChecked()){
            score = score + 20;
            System.out.println(score);
        }

        // Check if answer 1 is correct
        if ( answer3EditText.getText().toString().equals("6")){
            score = score + 20;
            System.out.println(score);
        }

        // Check if answer 1 is correct
        if (answer4EditText.getText().toString().equals("9")){
            score = score + 20;
        }

        // Check if answer 1 is correct
        if (answer5EditText.getText().toString().equals("5")){
            score = score + 20;
        }

        // Show score in a toast message
        Toast.makeText(this, "Your Score is %" + score, Toast.LENGTH_LONG).show();

    }
}
