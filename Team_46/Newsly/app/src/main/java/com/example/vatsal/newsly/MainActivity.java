package com.example.vatsal.newsly;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int RC_GOOGLE_SIGN_IN = 1;
    static GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.d(TAG, "onCreate: already logged in");
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("account", account.getAccount());
                startActivity(intent);
            } catch (ApiException e) {
                Log.d(TAG, "onActivityResult: " + e.getStatusCode() + " " + e.getLocalizedMessage());
                Toast.makeText(MainActivity.this, e.getLocalizedMessage() + e.getStatusCode(), Toast.LENGTH_LONG).show();
            }

        } else {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            } else {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
            }
        }
    }
}
