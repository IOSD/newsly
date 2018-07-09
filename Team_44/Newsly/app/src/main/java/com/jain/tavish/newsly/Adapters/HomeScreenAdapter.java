package com.jain.tavish.newsly.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jain.tavish.newsly.ModelClasses.Articles;
import com.jain.tavish.newsly.R;
import com.jain.tavish.newsly.UI.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.myViewHolder> {
    private final List<Articles> mResultList;
    private final Context mContext;

    public static class myViewHolder extends RecyclerView.ViewHolder  {

        public @BindView(R.id.tv_headline) TextView tv_headline;
        public @BindView(R.id.iv_image_main) ImageView mImageView;

        public myViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public HomeScreenAdapter(Context context, List<Articles> resultList) {
        mResultList = resultList;
        mContext = context;
    }

    @NonNull
    @Override
    public HomeScreenAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {

            Picasso.get()
                    .load(mResultList.get(position).getUrlToImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.mImageView);
            holder.tv_headline.setText(mResultList.get(position).getTitle());

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);

                    intent.putExtra("author", mResultList.get(position).getAuthor());
                    intent.putExtra("source", mResultList.get(position).getSource().getName());
                    intent.putExtra("title", mResultList.get(position).getTitle());
                    intent.putExtra("description", mResultList.get(position).getDescription());
                    intent.putExtra("url", mResultList.get(position).getUrl());
                    intent.putExtra("urlToImage", mResultList.get(position).getUrlToImage());
                    intent.putExtra("publishedAt", mResultList.get(position).getPublishedAt());

                    mContext.startActivity(intent);
                }
            });


    }
    @Override
    public int getItemCount() {
        return mResultList.size();
    }
}