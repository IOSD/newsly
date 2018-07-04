package com.example.vatsal.newsly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SecondActivity extends AppCompatActivity {
//    boolean isGoogleAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
//        String s = intent.getExtras().getString("account").getClass().getSimpleName();
//        if (s.equals("GoogleSignInAccount"))
//            isGoogleAccount = true;
//        else
//            isGoogleAccount = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
//                if (isGoogleAccount) {
                MainActivity.mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
//                } else {
//
//                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
