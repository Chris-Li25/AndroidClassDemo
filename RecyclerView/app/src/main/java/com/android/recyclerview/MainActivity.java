package com.android.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> itemList = new ArrayList<>();
        for(int i=0;i<26;i++){
            itemList.add("Item Lyj "+i);
        }

        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        MyAdapter adapter = new MyAdapter(this,itemList);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }
}
