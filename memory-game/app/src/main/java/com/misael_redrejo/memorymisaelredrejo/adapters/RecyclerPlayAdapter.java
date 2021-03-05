package com.misael_redrejo.memorymisaelredrejo.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.models.ImageMemory;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class RecyclerPlayAdapter extends RecyclerView.Adapter<RecyclerPlayAdapter.RecyclerDataHolder> implements View.OnClickListener {
    private ArrayList<ImageMemory> listData;
    private View.OnClickListener listener;
    private OnItemClickListener itemListener;

    public RecyclerPlayAdapter(ArrayList<ImageMemory> listData, OnItemClickListener listener) {
        this.listData = listData;
        this.itemListener = listener;
    }


    @NonNull
    @Override
    public RecyclerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list, parent, false);
        view.setOnClickListener(this);
        /** TESTTTTTTTTTTTTTTTT
        int height = parent.getMeasuredHeight() / 4;
        int width = parent.getMeasuredWidth()/ 4;

        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));

         TESTTTTTTTTTTTTTTTT */
        return new RecyclerDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPlayAdapter.RecyclerDataHolder holder, int position) {
        holder.assignData(listData.get(position), itemListener);
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class RecyclerDataHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public RecyclerDataHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void assignData (final ImageMemory imageMemory, final OnItemClickListener onItemClickListener) {

            if (imageMemory.isFlipped()) {
                imageView.setImageResource(imageMemory.getImage());
            }else {
                imageView.setImageResource(R.drawable.blackimage);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(imageMemory,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(ImageMemory imageMemory, int position);
    }
}
