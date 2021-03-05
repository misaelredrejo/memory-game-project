package com.misael_redrejo.memorymisaelredrejo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.activities.CreditsActivity;
import com.misael_redrejo.memorymisaelredrejo.activities.PlayActivity;
import com.misael_redrejo.memorymisaelredrejo.activities.ScoresActivity;

public class MenuFragment extends Fragment {
    private Button buttonCredits;
    private Button buttonPlay;
    private Button buttonScores;
    private Spinner spinner;

    private DataListener callback;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callback = (DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        buttonCredits = (Button) view.findViewById(R.id.buttonCredits);
        buttonPlay = (Button) view.findViewById(R.id.buttonPlay);
        buttonScores = (Button) view.findViewById(R.id.buttonScores);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getBaseContext(), CreditsActivity.class));
                String dif = "";
                switch (spinner.getSelectedItem().toString().toLowerCase()) {
                    case "facil":
                        dif = "easy";
                        break;
                    case "dificil":
                        dif = "hard";
                        break;
                    default:
                        dif = spinner.getSelectedItem().toString();
                }
                callback.sendData(dif);
            }
        });
        buttonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.openCredits();
            }
        });

        buttonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.openScores();
            }
        });


        return view;
    }

    public interface DataListener {
        public void sendData(String data);

        public void openCredits();
        public void openScores();
    }
}