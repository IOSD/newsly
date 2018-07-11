package com.example.vatsal.newsly.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vatsal.newsly.Adapters.RecyclerViewAdapter;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.News;
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
    public static final String TAG = "TAG";
    public final String API_KEY = "fc38d9df77174f81be9e0d9bbc2430ce";
    public final String sources = "abc-news,bbc-sport,bleacher-report,bloomberg,buzzfeed,cnbc,cnn,daily-mail,espn,four-four-two,google-news,mirror,national-geographic,news24,reddit-r-all,techcrunch,the-hindu,the-sport-bible,the-telegraph,the-times-of-india";
    Call<News> call;

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

    Callback<News> callback = new Callback<News>() {
        @Override
        public void onResponse(Call<News> call, Response<News> response) {
            List<Article> list = response.body().getArticles();
            adapter = new RecyclerViewAdapter(list, getContext());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<News> call, Throwable t) {
            Log.d(TAG, "onFailure: failed");
            Log.d(TAG, "onFailure: " + t.getMessage());
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());

        apiService = ApiClient.getClient().create(ApiInterface.class);
        if (position == 0) {
            call = apiService.getTopHeadlines(API_KEY, "en", "popularity", sources, 1, 30);
        } else {
            call = apiService.getHeadlines(API_KEY, "en", sources, 1, 30);
        }
        call.enqueue(callback);
    }
}
