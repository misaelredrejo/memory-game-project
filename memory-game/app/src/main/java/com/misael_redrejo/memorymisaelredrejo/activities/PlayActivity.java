package com.misael_redrejo.memorymisaelredrejo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.adapters.RecyclerPlayAdapter;
import com.misael_redrejo.memorymisaelredrejo.fragments.PlayFragment;
import com.misael_redrejo.memorymisaelredrejo.models.ImageMemory;
import com.misael_redrejo.memorymisaelredrejo.models.Score;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PlayActivity extends AppCompatActivity {

    private String dificulty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dificulty = extras.getString("dificulty").toLowerCase();
        }

        PlayFragment playFragment = (PlayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlay);
        playFragment.renderData(dificulty);

    }

}