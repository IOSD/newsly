package com.jain.tavish.newsly.UI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jain.tavish.newsly.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity{

    public @BindView(R.id.toolbar_detail) android.support.v7.widget.Toolbar toolbar;
    public @BindView(R.id.collapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;

    public @BindView(R.id.iv_backdrop) ImageView imageView;
    public @BindView(R.id.tv_title) TextView tv_title;
    public @BindView(R.id.tv_source) TextView tv_source;
    public @BindView(R.id.tv_author) TextView tv_author;
    public @BindView(R.id.tv_published_at) TextView tv_published_at;
    public @BindView(R.id.tv_description) TextView tv_description;
    public @BindView(R.id.btn_read_more) Button btn_read_more;

    String author, source, title, description, url, urlToImage, publishedAt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DetailTheme);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FFFFFF"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));

        Intent intent = getIntent();

        author = intent.getExtras().getString("author");
        source = intent.getExtras().getString("source");
        title = intent.getExtras().getString("title");
        description = intent.getExtras().getString("description");
        url = intent.getExtras().getString("url");
        urlToImage = intent.getExtras().getString("urlToImage");
        publishedAt = intent.getExtras().getString("publishedAt");

        Picasso.get()
                .load(urlToImage)
                .into(imageView);

        collapsingToolbarLayout.setTitle(title);
        tv_title.setText(title);

        if(source == null){
            source = "~No Source~";
        }
        tv_source.setText(source);

        if(author == null){
            author = "~No Author~";
        }
        tv_author.setText(author);

        if(publishedAt == null){
            publishedAt = "~No Date Available~";
        }
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date newDate = spf.parse(publishedAt);
            spf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa");
            publishedAt = spf.format(newDate);
        }catch (Exception e){

        }
        tv_published_at.setText(publishedAt);

        if(description == null){
            description = "~No Description~";
        }
        tv_description.setText(description);

        btn_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}