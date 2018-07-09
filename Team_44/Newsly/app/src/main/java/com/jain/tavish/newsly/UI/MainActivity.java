package com.jain.tavish.newsly.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public static final String API_KEY = "";

    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions googleSignInOptions;
    public @BindView(R.id.recycler_view_main) RecyclerView recyclerView;
    public HomeScreenAdapter homeScreenAdapter;
    public Call<APIResults> apiResultsCall;
    public List<Articles> articlesList;
    public APIResults apiResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        articlesList = new ArrayList<>();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiResultsCall = apiInterface.getTopHeadlines(API_KEY, "in");
        apiResultsCall.enqueue(new Callback<APIResults>() {
            @Override
            public void onResponse(Call<APIResults> call, Response<APIResults> response) {
                apiResults = response.body();
                articlesList = apiResults.getArticles();

                homeScreenAdapter = new HomeScreenAdapter(MainActivity.this, articlesList);
                recyclerView.setAdapter(homeScreenAdapter);
            }

            @Override
            public void onFailure(Call<APIResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR !!!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                            }
                        });
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendToSignInActivity() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}