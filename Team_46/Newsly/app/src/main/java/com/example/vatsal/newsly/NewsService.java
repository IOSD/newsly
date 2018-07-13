package com.example.vatsal.newsly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shubham on 7/13/2018.
 */

public interface NewsService {
    @GET("top-headlines")
    Call<Result> getLatestNews(@Query("apiKey") String ApiKey,@Query("country") String country);
}
