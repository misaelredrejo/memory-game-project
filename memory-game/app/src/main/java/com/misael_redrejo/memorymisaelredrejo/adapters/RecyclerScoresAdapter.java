package com.misael_redrejo.memorymisaelredrejo.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.models.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerScoresAdapter extends RecyclerView.Adapter<RecyclerScoresAdapter.RecyclerDataHolder> implements Filterable {
    private List<Score> scoreList;
    private List<Score> scoreListFull;

    public RecyclerScoresAdapter(List<Score> listData) {
        this.scoreList = listData;
        scoreListFull = new ArrayList<>(listData);
    }


    @NonNull
    @Override
    public RecyclerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
        return new RecyclerDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerScoresAdapter.RecyclerDataHolder holder, int position) {
        holder.assignData(scoreList.get(position));
    }


    @Override
    public int getItemCount() {
        return scoreList.size();
    }


    public class RecyclerDataHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewTime;
        TextView textViewScore;

        public RecyclerDataHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            textViewScore = (TextView) itemView.findViewById(R.id.textViewScore);
        }

        public void assignData (final Score score) {
            textViewName.setText(score.getName());
            textViewTime.setText(score.getTime());
            textViewScore.setText(String.valueOf(score.getScore()));

        }
    }

    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Score> filteredList = new ArrayList<>();
            String filterPattern = constraint.toString().toLowerCase();
            if (scoreList.size() > scoreListFull.size()) {
                scoreListFull.clear();
                scoreListFull.addAll(scoreList);
            }
            for (Score score : scoreListFull) {
                if (score.getDifficulty().toLowerCase().equals(constraint)) {
                    filteredList.add(score);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            scoreList.clear();
            scoreList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
