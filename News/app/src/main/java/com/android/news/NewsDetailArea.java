package com.android.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class NewsDetailArea extends Fragment {

    public Map<String,String> news = new HashMap<>();
    private TextView title;
    private TextView detail;

    public NewsDetailArea(Map<String,String> news){
        this.news = news;
    }
    public NewsDetailArea(){
        this.news.put("title","新闻详细内容页");
        this.news.put("newsDetail","");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsdetail_area,container,false);
        title = (TextView) view.findViewById(R.id.newsDetail_title);
        detail = (TextView) view.findViewById(R.id.newsDetail);
        if(news != null){
            title.setText(news.get("title"));
            detail.setText(news.get("newsDetail"));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
