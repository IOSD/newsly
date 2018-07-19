package com.example.vatsal.newsly;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_GOOGLE_SIGN_IN = 1;
    public static final String API_KEY="61f35f2df49b4e559e21b35973523e85";
    static GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    public static final String TAG = "TAG";
    CallbackManager callbackManager;
    static LoginButton loginButton;
    private static final String EMAIL = "email";
    public static final String isGoogleAccount = "isGoogleAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Google Sign Up


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            Log.d(TAG, "onCreate: already logged in");
//            Intent intent = new Intent(SignInActivity.this, LoggedInActivity.class);
//            intent.putExtra("account", account);
//            startActivity(intent);
//        }
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                intent.putExtra(isGoogleAccount, true);
                startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
            }
        });


        // Facebook Sign In

        if (isLoggedIn())
            sendToSecondActivity(false);


        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sendToSecondActivity(false);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Error while logging in", Toast.LENGTH_LONG).show();
            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            sendToSecondActivity(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                sendToSecondActivity(true);
                return;
            } catch (ApiException e) {
                Log.d(TAG, "onActivityResult: " + e.getStatusCode() + " " + e.getLocalizedMessage());
                Toast.makeText(SignInActivity.this, e.getLocalizedMessage() + e.getStatusCode(), Toast.LENGTH_LONG).show();
            }

        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            sendToSecondActivity(true);
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void sendToSecondActivity(boolean isGoogle) {
        Intent i = new Intent(SignInActivity.this, LoggedInActivity.class);
        i.putExtra(isGoogleAccount, isGoogle);
        startActivity(i);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
