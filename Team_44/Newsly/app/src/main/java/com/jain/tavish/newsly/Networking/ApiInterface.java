package com.jain.tavish.newsly.Networking;

import com.jain.tavish.newsly.ModelClasses.APIResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines/")
    Call<APIResults> getTopHeadlines(@Query("apiKey") String apiKey, @Query("country")String country);

}