package com.android.videoplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private MainActivity context;
    private List<Video> videos;

    public VideoAdapter(MainActivity context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoThumbnail;
        TextView videoName;
        TextView videoDate;
        TextView videoDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoName = itemView.findViewById(R.id.videoName);
            videoDate = itemView.findViewById(R.id.videoDate);
            videoDuration = itemView.findViewById(R.id.videoDuration);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Video video = videos.get(position);
        final ArrayList<String> AllVideosPath = getAllVideosPath(videos);
        holder.videoThumbnail.setImageBitmap(video.getVideoThumbnail());
        holder.videoName.setText(video.getName());
        holder.videoDate.setText(translateTime(1, video.getDate()));
        holder.videoDuration.setText(translateTime(2, video.getDuration()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File videoDetail = new File(video.getPath());
                if (videoDetail.exists()) {
                    //转到视频播放的activity 传输点击的视频地址和所有视频地址的列表
                    VideoPlayerActivity.actionStart(context, video.getPath(), AllVideosPath);
                } else {
                    ToastUtil.showToast(context, "文件不存在");
                    videos = context.getVideos(2, null);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public void setFilter(List<Video> filterVideos) {
        videos.clear();
        videos.addAll(filterVideos);
        notifyDataSetChanged();
    }

    //转换视频的时长和修改时间格式
    public String translateTime(int date1_duration2, long date_duration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat durationFormat = new SimpleDateFormat("HH:MM:ss");
        Date date = new Date(date_duration);
        if (date1_duration2 == 1) {
            return dateFormat.format(date);
        } else if (date1_duration2 == 2) {
            return durationFormat.format(date);
        } else {
            return "ERROR";
        }
    }

    //刷新数据
    public void refreshData(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    //获取所有视频文件路径列表
    public ArrayList<String> getAllVideosPath(List<Video> videos) {
        ArrayList<String> AllVideosPath = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            AllVideosPath.add(videos.get(i).getPath());
        }
        return AllVideosPath;
    }


}
