package com.example.vatsal.newsly.Adapters;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
=======
import android.support.annotation.NonNull;
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.Button;
=======
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
<<<<<<< HEAD
import com.example.vatsal.newsly.LoggedInActivity;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.WebPage;
=======
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.R;
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Article> dataset;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article item = dataset.get(position);
=======
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article item = dataset.get(position);
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87
        holder.textView.setText(item.getTitle());
        Glide.with(context)
                .load(item.getUrlToImage())
                .into(holder.imageView);
<<<<<<< HEAD
        showFront(holder);
        holder.description.setText(item.getDescription());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebPage.class);
                intent.putExtra("webPage", item.getUrl());
                context.startActivity(intent);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.isFront) {
                    holder.cardView.animate().rotationY(360f).setDuration(500);
                    showRear(holder);
                } else {
                    holder.cardView.animate().rotationY(360f).setDuration(500);
                    showFront(holder);
                }
            }
        });
=======
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
<<<<<<< HEAD
        CardView cardView;
        TextView description;
        Button button;
        View view;
        boolean isFront;
=======
        View view;
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
<<<<<<< HEAD
            cardView = view.findViewById(R.id.card_view);
            description = view.findViewById(R.id.description);
            button = view.findViewById(R.id.button);
            isFront = true;
=======
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87
        }
    }

    public RecyclerViewAdapter(List<Article> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }
<<<<<<< HEAD

    public void showRear(ViewHolder holder) {
        holder.isFront = false;
        holder.textView.setAlpha(0f);
        holder.imageView.setAlpha(0f);
        holder.description.setAlpha(1f);
        holder.button.setAlpha(1f);
        holder.button.setClickable(true);
    }

    public void showFront(ViewHolder holder) {
        holder.isFront = true;
        holder.textView.setAlpha(1f);
        holder.imageView.setAlpha(1f);
        holder.description.setAlpha(0f);
        holder.button.setAlpha(0f);
        holder.button.setClickable(false);
    }
=======
>>>>>>> 773f95ad8d41933f40ecdd95cc61aa7d14927e87
}
