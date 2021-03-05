package com.misael_redrejo.memorymisaelredrejo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.fragments.CreditsFragment;
import com.misael_redrejo.memorymisaelredrejo.fragments.PlayFragment;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CreditsFragment creditsFragment = (CreditsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCredits);
    }
}