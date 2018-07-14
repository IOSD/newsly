package com.aroraharsh010.newsly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mAuth=FirebaseAuth.getInstance();
    }

    public void signOut(View view){
        mAuth.signOut();
        Toast.makeText(this,"Signed out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,LoginPage.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            startActivity(new Intent(this,LoginPage.class));
        }

    }
}
