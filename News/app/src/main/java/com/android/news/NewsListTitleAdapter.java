package com.android.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class NewsListTitleAdapter extends RecyclerView.Adapter<NewsListTitleAdapter.ViewHolder> {
    @NonNull

    private List<Map<String,String>> newsList;
    private NewsDetailArea newsDetailArea;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = (TextView)itemView.findViewById(R.id.newsTitle);
        }
    }


    public NewsListTitleAdapter(Context context,List<Map<String,String>>itemList){
        this.context = context;
        this.newsList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newstitle_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListTitleAdapter.ViewHolder holder,final int position) {
        Map<String,String> news = newsList.get(position);
        holder.newsTitle.setText(news.get("title"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> news = newsList.get(position);
                newsDetailArea = new NewsDetailArea(news);
                replaceFragment(newsDetailArea,context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    //替换Fragment
    private void replaceFragment(Fragment fragment, Context context){
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.newsDetail_area,fragment);
        transaction.commit();
    }


}
