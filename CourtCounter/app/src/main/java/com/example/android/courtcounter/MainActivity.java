package com.example.android.courtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int scoreA = 0;
    int scoreB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void displayScoreA(int score) {
        TextView scoreTextA = (TextView) findViewById(R.id.team_a_score);
        scoreTextA.setText(String.valueOf(score));
        scoreA = score;
    }

    public void displayScoreB(int score) {
        TextView scoreTextB = (TextView) findViewById(R.id.team_b_score);
        scoreTextB.setText(String.valueOf(score));
        scoreB = score;
    }

    public void resetScore() {
        displayScoreA(0);
        displayScoreB(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.A3point:
                displayScoreA(scoreA + 3);
                break;
            case R.id.A2point:
                displayScoreA(scoreA + 2);
                break;
            case R.id.A1point:
                displayScoreA(scoreA + 1);
                break;
            case R.id.B3point:
                displayScoreB(scoreB + 3);
                break;
            case R.id.B2point:
                displayScoreB(scoreB + 2);
                break;
            case R.id.B1point:
                displayScoreB(scoreB + 1);
                break;
            case R.id.resetScore:
                resetScore();
        }
    }
}
