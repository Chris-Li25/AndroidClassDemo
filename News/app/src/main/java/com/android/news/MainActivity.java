package com.android.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NewsListArea newsListArea;
    private NewsDetailArea newsDetailArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListArea = new NewsListArea(this);
        newsDetailArea = new NewsDetailArea();

        getSupportFragmentManager().beginTransaction().add(R.id.newsList_area, newsListArea).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.newsDetail_area, newsDetailArea).commitAllowingStateLoss();

    }
}
