package com.misael_redrejo.memorymisaelredrejo.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.adapters.RecyclerScoresAdapter;
import com.misael_redrejo.memorymisaelredrejo.models.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class ScoresFragment extends Fragment {
    private List<Score> scoreList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerScoresAdapter recyclerScoresAdapter;

    private Button btnEasy;
    private Button btnHard;

    private final String URLGET ="https://ng-memory-game.herokuapp.com/scores";
    RequestQueue queue;

    public ScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        btnEasy = (Button) view.findViewById(R.id.btnEasy);
        btnHard = (Button) view.findViewById(R.id.btnHard);

        scoreList = new ArrayList<>();
        queue = Volley.newRequestQueue(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerScores);
        recyclerScoresAdapter = new RecyclerScoresAdapter(scoreList);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerScoresAdapter.getFilter().filter("easy");
        btnEasy.setBackgroundColor(getResources().getColor(R.color.light_gold));
        btnHard.setBackgroundColor(getResources().getColor(R.color.gold));
        recyclerView.setAdapter(recyclerScoresAdapter);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEasy.setBackgroundColor(getResources().getColor(R.color.light_gold));
                btnHard.setBackgroundColor(getResources().getColor(R.color.gold));
                recyclerScoresAdapter.getFilter().filter("easy");
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHard.setBackgroundColor(getResources().getColor(R.color.light_gold));
                btnEasy.setBackgroundColor(getResources().getColor(R.color.gold));
                recyclerScoresAdapter.getFilter().filter("hard");
            }
        });

        return view;
    }

    public void renderData() {
        List<Score> scList = new ArrayList<Score>();
        scoreList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URLGET,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject scoreJson = response.getJSONObject(i);

                                // Get the current student (json object) data
                                Score score = new Score(scoreJson.getString("name"), Integer.parseInt(scoreJson.getString("score")), scoreJson.getString("time"), scoreJson.getString("difficulty"));
                                scList.add(score);
                            }

                            scoreList.addAll(scList);
                            recyclerScoresAdapter.notifyDataSetChanged();
                            recyclerScoresAdapter.getFilter().filter("easy");

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //
                        Toast.makeText(getActivity(), getString(R.string.error_getting), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonArrayRequest);

        btnEasy.setBackgroundColor(getResources().getColor(R.color.light_gold));
        btnHard.setBackgroundColor(getResources().getColor(R.color.gold));
    }


}