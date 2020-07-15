package com.android.videoplayer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

//用于存储视频信息的类
public class Video {
    private int id = 0; //id
    private String path = null; //路径
    private String name = null; //视频名
    private String resolution = null;// 分辨率
    private long size = 0;  //视频大小
    private long date = 0;  //视频修改日期
    private long duration = 0;  //视频时长
    private Bitmap videoThumbnail = null;  //视频缩略图

    public Video(int id, String path, String name, String resolution, long size, long date, long duration, Bitmap videoThumbnail) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.resolution = resolution;
        this.size = size;
        this.date = date;
        this.duration = duration;
        this.videoThumbnail = videoThumbnail;
    }

    public Video() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setVideoThumbnail(Bitmap videoThumbnail) { this.videoThumbnail = videoThumbnail; }

    public Bitmap getVideoThumbnail() { return videoThumbnail; }

    @Override
    public String toString() {
        return "Video [id=" + id + ", path=" + path + ", name=" + name + ", resolution=" + resolution + ", size=" + size + ", date=" + date
                + ", duration=" + duration + "]";
    }

}
