package com.aroraharsh010.newsly;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class SearchResultsActivity extends AppCompatActivity {
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        query= handleIntent(getIntent());
        Toolbar mToolbar=findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Search for "+query);



    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private String handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
             return intent.getStringExtra(SearchManager.QUERY);
           //send this query to get news from api
        }
        else
            return "no news";
    }

}
