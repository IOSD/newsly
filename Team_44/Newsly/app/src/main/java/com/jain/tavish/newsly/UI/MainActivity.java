package com.jain.tavish.newsly.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jain.tavish.newsly.Adapters.HomeScreenAdapter;
import com.jain.tavish.newsly.ModelClasses.APIResults;
import com.jain.tavish.newsly.ModelClasses.Articles;
import com.jain.tavish.newsly.Networking.ApiInterface;
import com.jain.tavish.newsly.Networking.RetrofitClient;
import com.jain.tavish.newsly.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "b6c867599dd841e7b1dff94143d6df82";

    public @BindView(R.id.progress_bar) ProgressBar progressBar;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions googleSignInOptions;
    public @BindView(R.id.recycler_view_main) RecyclerView recyclerView;
    public @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    public HomeScreenAdapter homeScreenAdapter;
    public Call<APIResults> apiResultsCall;
    public List<Articles> articlesList;
    public APIResults apiResults;

    public String dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        articlesList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        getNationalNews();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                switch(item.getItemId()){
                    case R.id.action_national_news:
                        getNationalNews();
                        break;
                    case R.id.action_international_news:
                        getInternationalNews();
                        break;
                    case R.id.action_search:
                        showAlertDialog();
                        break;

                }
                return true;
            }
        });
    }

    public void getInternationalNews(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiResultsCall = apiInterface.getInternationalNews(API_KEY, "the-washington-post");
        apiResultsCall.enqueue(new Callback<APIResults>() {
            @Override
            public void onResponse(Call<APIResults> call, Response<APIResults> response) {
                apiResults = response.body();

                if(articlesList != null){
                    articlesList.clear();
                }
                articlesList = apiResults.getArticles();

                homeScreenAdapter = new HomeScreenAdapter(MainActivity.this, articlesList);
                recyclerView.setAdapter(homeScreenAdapter);

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                homeScreenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<APIResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNationalNews(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiResultsCall = apiInterface.getTopHeadlines(API_KEY, "in");
        apiResultsCall.enqueue(new Callback<APIResults>() {
            @Override
            public void onResponse(Call<APIResults> call, Response<APIResults> response) {
                apiResults = response.body();

                if(articlesList != null){
                    articlesList.clear();
                }
                articlesList = apiResults.getArticles();

                homeScreenAdapter = new HomeScreenAdapter(MainActivity.this, articlesList);
                recyclerView.setAdapter(homeScreenAdapter);

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                homeScreenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<APIResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSearchedNews(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiResultsCall = apiInterface.getSearchedNews(API_KEY, dialogText);
        apiResultsCall.enqueue(new Callback<APIResults>() {
            @Override
            public void onResponse(Call<APIResults> call, Response<APIResults> response) {
                apiResults = response.body();

                if(articlesList != null){
                    articlesList.clear();
                }
                articlesList = apiResults.getArticles();

                homeScreenAdapter = new HomeScreenAdapter(MainActivity.this, articlesList);
                recyclerView.setAdapter(homeScreenAdapter);

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                homeScreenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<APIResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAlertDialog(){
        articlesList.clear();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Type the news you want to search for ....");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(70, 0, 80, 0);

        final EditText textBox = new EditText(MainActivity.this);
        layout.addView(textBox, params);

        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialogText = textBox.getText().toString();
                getSearchedNews();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

    public void sendToSignInActivity() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu_btn:
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Logged Out Successfully !!!", Toast.LENGTH_SHORT).show();
                                sendToSignInActivity();
                                finish();
                            }
                        });
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}