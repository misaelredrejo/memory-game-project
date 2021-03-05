package com.misael_redrejo.memorymisaelredrejo.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.activities.PlayActivity;
import com.misael_redrejo.memorymisaelredrejo.adapters.RecyclerPlayAdapter;
import com.misael_redrejo.memorymisaelredrejo.models.ImageMemory;
import com.misael_redrejo.memorymisaelredrejo.models.Score;
import com.misael_redrejo.memorymisaelredrejo.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class PlayFragment extends Fragment {

    private Chronometer chronometer;

    private List<ImageMemory> imageMemoryList;
    private RecyclerView recyclerView;
    private RecyclerPlayAdapter recyclerPlayAdapter;

    private int lastImage = -1;
    private int lastImagePosition = -1;
    private int totalGuessed = 0;
    private String dificulty = "";
    private String totalTime = "";
    private int totalScore = 0;

    private final String URLPOST ="https://ng-memory-game.herokuapp.com/scores";
    RequestQueue queue;

    View view;


    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play, container, false);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPlay);

        queue = Volley.newRequestQueue(view.getContext());


        chronometer = (Chronometer) view.findViewById(R.id.chronometer) ;
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                cArg.setText(t);
            }
        });

        return view;
    }



    public void renderData(String dif){
        this.dificulty = dif.toLowerCase();
        totalGuessed = 0;

        switch (dificulty) {
            case "easy":
                imageMemoryList = Util.getImageListEasy();
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
                break;
            case "hard":
                imageMemoryList = Util.getImageListHard();
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
                break;
        }


        recyclerPlayAdapter = new RecyclerPlayAdapter((ArrayList<ImageMemory>) imageMemoryList, new RecyclerPlayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageMemory imageMemory, int position) {
                if (position == lastImagePosition || imageMemory.isGuessed()) return; // SI SE HACE CLICK EN LA MISMA IMAGEN O YA ESTÁ ACERTADA NO SIGO

                imageMemory.setFlipped(true);
                recyclerPlayAdapter.notifyItemChanged(position);

                checkGuessed(imageMemory, position);

            }
        });

        recyclerView.setAdapter(recyclerPlayAdapter);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void checkGuessed(ImageMemory imageMemory, int position) {

        if (lastImagePosition != -1){ // Si es la segunda imagen que clico verifico si se ha acertado
            if (imageMemory.getImage() == lastImage) {
                imageMemory.setGuessed(true);
                imageMemoryList.get(lastImagePosition).setGuessed(true);
                recyclerPlayAdapter.notifyItemChanged(position);
                recyclerPlayAdapter.notifyItemChanged(lastImagePosition);
                totalGuessed++;
            } else {
                int tmpLastPos = lastImagePosition;
                (new Handler(Looper.getMainLooper()))
                        .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageMemory.setFlipped(false);
                        imageMemoryList.get(tmpLastPos).setFlipped(false);
                        recyclerPlayAdapter.notifyItemChanged(position);
                        recyclerPlayAdapter.notifyItemChanged(tmpLastPos);
                    }
                }, 500);

            }
        }
        /** ACTUALIZO VARIABLES */
        lastImagePosition = (lastImagePosition == -1 ? position : -1);
        lastImage = (lastImage == -1 ? imageMemory.getImage() : -1);

        /** SI HA GANADO GUARDAR PUNTUACIÓN */
        switch (dificulty) {
            case "easy":
                if (totalGuessed == 3) {
                    chronometer.stop();
                    totalTime = getTime();
                    totalScore = calcScore();
                    showAlertToSubmitScore();
                }
                break;
            case "hard":
                if (totalGuessed == 8) {
                    chronometer.stop();
                    totalTime = getTime();
                    totalScore = calcScore();
                    showAlertToSubmitScore();
                }
                break;
        }

    }

    private void showAlertToSubmitScore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.guardar_puntuacion));
        builder.setMessage(getString(R.string.guardar_puntuacion) + " " + totalTime + "(hh:mm:ss), " + getString(R.string.con)+ " " + totalScore + " " +getString(R.string.puntos) + "\n" + getString(R.string.introducir_nombre));


        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.alert_submit_score,null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextName);


        builder.setPositiveButton(getString(R.string.guardar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = input.getText().toString();
                if (name.length() == 0 || name.length() > 15) {
                    showAlertToSubmitScore();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setTitle(getString(R.string.error_nombre));
                    builder1.setMessage(getString(R.string.error_nombre_mensaje));
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("OK",null);
                    builder1.show();
                    return;
                }
                Score score = new Score(name, totalScore, totalTime, dificulty);
                saveScore(score);
                }
        });
        builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String getTime() {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h   = (int)(time /3600000);
        int m = (int)(time - h*3600000)/60000;
        int s= (int)(time - h*3600000- m*60000)/1000;
        return (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
    }

    private int calcScore() {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h   = (int)(time /3600000);
        int m = (int)(time - h*3600000)/60000;
        int s= (int)(time - h*3600000- m*60000)/1000;
        int score = 0;
        switch (dificulty) {
            case "easy":
                score = Math.max(1, 1000 - s*2);
                break;
            case "hard":
                score = Math.max(1, 1000 - s);
                break;
        }
        return score;
    }

    private void saveScore(Score score) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("name", score.getName());
            postData.put("score", score.getScore());
            postData.put("time", score.getTime());
            postData.put("difficulty", score.getDifficulty());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLPOST, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), getString(R.string.puntuacion_de) + " " + score.getName() + " " + getString(R.string.aniadida) , Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.error_adding), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}