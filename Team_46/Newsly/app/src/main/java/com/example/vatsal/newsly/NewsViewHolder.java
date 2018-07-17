package com.example.vatsal.newsly;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shubham on 7/13/2018.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {
    TextView newsTitle,newsDescription;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.newsTitle=itemView.findViewById(R.id.textView);
        this.newsDescription=itemView.findViewById(R.id.textView2);

    }
}
