package com.misael_redrejo.memorymisaelredrejo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.adapters.RecyclerPlayAdapter;
import com.misael_redrejo.memorymisaelredrejo.adapters.RecyclerScoresAdapter;
import com.misael_redrejo.memorymisaelredrejo.fragments.PlayFragment;
import com.misael_redrejo.memorymisaelredrejo.fragments.ScoresFragment;
import com.misael_redrejo.memorymisaelredrejo.models.ImageMemory;
import com.misael_redrejo.memorymisaelredrejo.models.Score;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ScoresFragment scoresFragment = (ScoresFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentScores);
        scoresFragment.renderData();
    }
}