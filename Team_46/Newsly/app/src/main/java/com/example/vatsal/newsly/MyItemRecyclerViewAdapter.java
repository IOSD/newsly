package com.example.vatsal.newsly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vatsal.newsly.ItemFragment.OnListFragmentInteractionListener;
import com.example.vatsal.newsly.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    ArrayList<NewsItem> newsItems;
    Context context;
    LayoutInflater inflater;
    public MyItemRecyclerViewAdapter(Context context,ArrayList<NewsItem> newsItems) {
        this.newsItems = newsItems;
        this.context=context;
        this.inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view=inflater.inflate(R.layout.news_row,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.newsTitle.setText(newsItems.get(position).title);
        newsViewHolder.newsDescription.setText(newsItems.get(position).description);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
}
