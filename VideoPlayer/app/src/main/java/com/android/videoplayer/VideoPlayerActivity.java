package com.android.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;


public class VideoPlayerActivity extends AppCompatActivity {


    private String videoPath;
    private int videoIndex;

    private ArrayList<String> AllVideosPath;

    private VideoView videoView;


    //传参路由启动页面
    public static void actionStart(Context context, String videoPath, ArrayList<String> AllVideosPath) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("videoPath", videoPath);
        intent.putStringArrayListExtra("AllVideosPath", AllVideosPath);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer_activity);
        videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra("videoPath");
        AllVideosPath = intent.getStringArrayListExtra("AllVideosPath");
        videoIndex = AllVideosPath.indexOf(videoPath);



        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView.setVideoPath(videoPath);
        MediaController mc = new MediaController(this);

        //设置上一个和下一个按钮的监听事件
        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoIndex >= AllVideosPath.size()-1){
                    ToastUtil.showToast(VideoPlayerActivity.this,"已是最后一个视频");
                }else if(!new File(AllVideosPath.get(videoIndex+1)).exists()){
                    ToastUtil.showToast(VideoPlayerActivity.this,"视频不存在,请刷新列表");
                }else{
                    videoView.setVideoPath(AllVideosPath.get(++videoIndex));
                    videoView.start();
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoIndex <= 0){
                    ToastUtil.showToast(VideoPlayerActivity.this,"已是第一个视频");
                }else if(!new File(AllVideosPath.get(videoIndex-1)).exists()){
                    ToastUtil.showToast(VideoPlayerActivity.this,"视频不存在,请刷新列表");
                }else {
                    videoView.setVideoPath(AllVideosPath.get(--videoIndex));
                    videoView.start();
                }
            }
        });
        videoView.setMediaController(mc);
        videoView.start();
    }

    //监听横竖屏变化
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
