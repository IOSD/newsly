package com.example.vatsal.newsly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebPage extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("webPage");
        webView.loadUrl(url);
    }
}