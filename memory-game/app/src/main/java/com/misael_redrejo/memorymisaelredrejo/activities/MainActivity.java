package com.misael_redrejo.memorymisaelredrejo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.fragments.CreditsFragment;
import com.misael_redrejo.memorymisaelredrejo.fragments.MenuFragment;
import com.misael_redrejo.memorymisaelredrejo.fragments.PlayFragment;
import com.misael_redrejo.memorymisaelredrejo.fragments.ScoresFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.DataListener {
    boolean isMultiPanel;
    PlayFragment playFragment;
    ScoresFragment scoresFragment;
    LinearLayout linearPlay;
    LinearLayout linearCredits;
    LinearLayout linearScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMultiPanel();

        if (isMultiPanel) {
            initializeFragmentsAndLayouts();
        }
    }


    @Override
    public void sendData(String dif) {
        if (isMultiPanel){
            linearPlay.setVisibility(View.VISIBLE);
            linearCredits.setVisibility(View.GONE);
            linearScores.setVisibility(View.GONE);
            playFragment.renderData(dif);
        }else {
            Intent intent = new Intent(this,PlayActivity.class);
            intent.putExtra("dificulty",dif);
            startActivity(intent);
        }
    }

    @Override
    public void openCredits() {
        if (isMultiPanel){
            linearCredits.setVisibility(View.VISIBLE);
            linearPlay.setVisibility(View.GONE);
            linearScores.setVisibility(View.GONE);
        }else {
            startActivity(new Intent(this,CreditsActivity.class));
        }
    }

    @Override
    public void openScores() {

        if (isMultiPanel){
            scoresFragment.renderData();
            linearScores.setVisibility(View.VISIBLE);
            linearPlay.setVisibility(View.GONE);
            linearCredits.setVisibility(View.GONE);
        }else {
            startActivity(new Intent(this,ScoresActivity.class));
        }
    }

    private void setMultiPanel(){
        isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.fragmentPlay)!=null);
    }

    private void initializeFragmentsAndLayouts() {
        playFragment = (PlayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlay);
        scoresFragment = (ScoresFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentScores);
        linearPlay = (LinearLayout) findViewById(R.id.linearPlay);
        linearScores = (LinearLayout) findViewById(R.id.linearScores);
        linearCredits = (LinearLayout) findViewById(R.id.linearCredits);
    }
}