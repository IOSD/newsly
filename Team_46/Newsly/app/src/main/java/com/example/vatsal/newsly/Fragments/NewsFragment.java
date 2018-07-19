package com.example.vatsal.newsly.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vatsal.newsly.Adapters.RecyclerViewAdapter;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.Main;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.api.ApiClient;
import com.example.vatsal.newsly.api.ApiInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    int position;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    ApiInterface apiService;
    List<Article> list;
    ProgressBar progressBar;
    int index;
    public static final String TAG = "TAG";
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public final String API_KEY = "fc38d9df77174f81be9e0d9bbc2430ce";
    public final String sources = "abc-news,bbc-sport,bleacher-report,bloomberg,buzzfeed,cnbc,cnn,daily-mail,espn,four-four-two,google-news,mirror,national-geographic,news24,reddit-r-all,techcrunch,the-hindu,the-sport-bible,the-telegraph,the-times-of-india";

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    Callback<Main> callback = new Callback<Main>() {
        @Override
        public void onResponse(Call<Main> call, Response<Main> response) {
            list = response.body().getArticles();
            adapter = new RecyclerViewAdapter(list, getContext());
            recyclerView.setAdapter(adapter);
            progressBar.setAlpha(0f);
        }

        @Override
        public void onFailure(Call<Main> call, Throwable t) {
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setAlpha(1f);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Main> call;
        if (position == 0) {
            call = apiService.getTopHeadlines(API_KEY, "en", "popularity", sources, 1, 30);
        } else if (position == 1) {
            call = apiService.getTopHeadlines(API_KEY, "en", "the-hindu,the-times-of-india", 1, 30);
        } else {
            call = apiService.getTopHeadlines(API_KEY, "en", sources, 1, 30);
        }
        call.enqueue(callback);
        index = 1;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1))
                    onScrolledToBottom();
            }
        });
    }

    Callback<Main> callback1 = new Callback<Main>() {
        @Override
        public void onResponse(Call<Main> call, Response<Main> response) {
            List<Article> articles = response.body().getArticles();
            list.addAll(articles);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Main> call, Throwable t) {

        }
    };

    private void onScrolledToBottom() {
        Call<Main> call;
        if (position == 0) {
            call = apiService.getTopHeadlines(API_KEY, "en", "popularity", sources, ++index, 30);
        } else if (position == 1) {
            call = apiService.getTopHeadlines(API_KEY, "en", "the-hindu,the-times-of-india", ++index, 30);
        } else {
            call = apiService.getTopHeadlines(API_KEY, "en", sources, ++index, 30);
        }
        call.enqueue(callback1);
    }
}