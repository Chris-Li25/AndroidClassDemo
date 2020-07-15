package com.android.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Video> videos = new ArrayList<>();

    private ContentResolver contentResolver;

    private String TAG = "VideoDetail";

    public final static int MAIN_ACTIVITY = 1;
    public final static int SEARCH_FINISHED = 2;
    public final static int FILE_EXIST = 3;
    public final static int VIDEOS_EMPTY = 4;


    private SearchView searchView;
    private RecyclerView videosList;
    private TextView tips;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoAdapter videoAdapter;

    //用于其他线程传递消息并设置UI变化
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH_FINISHED:
                    videoAdapter.refreshData(videos);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case FILE_EXIST:
                    tips.setVisibility(View.GONE);
                    videosList.setVisibility(View.VISIBLE);
                    break;
                case VIDEOS_EMPTY:
                    ToastUtil.showToast(MainActivity.this, "无视频文件");
                    break;
                default:
                    break;
            }
        }
    };

    //启动app是扫面文件的线程
    Thread startApp = new Thread(new Runnable() {
        @Override
        public void run() {
            videos = getVideos(MAIN_ACTIVITY, null);
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();
        tips = findViewById(R.id.tips);
        videosList = findViewById(R.id.videosList);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.measure(0, 0);

        //获取读取存储空间权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            swipeRefreshLayout.setRefreshing(true);
            startApp.start();
        } else {
            swipeRefreshLayout.setRefreshing(true);
            startApp.start();
        }

        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新MediaStore
                new Thread(new SearchF()).start();
            }
        });


        //配置适配器和布局
        videoAdapter = new VideoAdapter(this, videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        videosList.setLayoutManager(layoutManager);
        videosList.setAdapter(videoAdapter);

    }


    //工具栏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchVideos);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("请输入视频名称");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                swipeRefreshLayout.setRefreshing(true);
                new Thread(new FilterSearch(newText)).start();
                return true;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }


    //通过MediaStore和ContentResolver获取手机中的所有视频信息
    public List<Video> getVideos(int STATES, String filterKey) {

        List<Video> videos = new ArrayList<Video>();

        Cursor cursor = null;

        try {
            // String[] mediaColumns = { "_id", "_data", "_display_name",
            // "_size", "date_modified", "duration", "resolution" };
            cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (cursor.moveToNext()) {

                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));//修改时间
                Bitmap videoThumbnail = getVideoThumbnail(id);
                Video video = new Video(id, path, name, resolution, size, date, duration, videoThumbnail);
                if (filterKey != null) {
                    if (name.contains(filterKey)) {
                        Log.e(TAG, "getVideos: FilterKey "+ "ADD" );
                        videos.add(video);
                    }
                } else {
                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (videos.isEmpty()) {
            Message message = new Message();
            message.what = VIDEOS_EMPTY;
            handler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = FILE_EXIST;
            handler.sendMessage(message);
            //发通知更新UI 有文件 将RecyclerView显现
        }

        if (STATES == 1) {
            Message message = new Message();
            message.what = SEARCH_FINISHED;
            handler.sendMessage(message);
            //发通知 扫描完成
        } else {
        }

        return videos;
    }

    //获取视频缩略图
    public Bitmap getVideoThumbnail(int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, options);
        return bitmap;
    }

    //获取权限反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    ToastUtil.showToast(MainActivity.this, "获取存储权限失败,请进入系统设置允许权限");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    //将List<String>转换为String[]
    public String[] turnListToStringArray(List<String> path) {
        String[] paths = new String[path.size()];
        for (int i = 0; i < path.size(); i++) {
            paths[i] = path.get(i);
        }
        return paths;
    }

    //获取存储中所有文件的路径
    public List<String> searchFile(File file) {
        List<String> paths = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (int j = 0; j < files.length; j++) {
                    if (!files[j].isDirectory()) {
                        Log.e(TAG, "searchFile: " + files[j].getAbsolutePath());
                        paths.add(files[j].getAbsolutePath());
                    } else {
                        Log.e(TAG, "searchFile: " + files[j].getAbsolutePath());
                        paths.addAll(searchFile(files[j]));
                    }
                }
            }
        } else {
            paths.add(file.getAbsolutePath());
        }
        return paths;
    }

    //创建一个类用于搜索文件 定义为一个线程
    public class SearchF implements Runnable {

        @Override
        public void run() {
            //api大于等于29时 用这个方法扫描文件
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                MediaScannerConnection.scanFile(MainActivity.this, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Message message = new Message();
                        message.what = SEARCH_FINISHED;
                        handler.sendMessage(message);
                    }
                });

            } else {
                //api小于29时用这个方法扫描文件
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String[] paths = turnListToStringArray(searchFile(file));
                MediaScannerConnection.scanFile(MainActivity.this, paths, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        mediaScanIntent.setData(uri);
                        MainActivity.this.sendBroadcast(mediaScanIntent);


                        Message message = new Message();
                        message.what = SEARCH_FINISHED;
                        handler.sendMessage(message);
                    }
                });
            }
            videos = getVideos(MAIN_ACTIVITY, null);
        }
    }

    public class FilterSearch implements Runnable{

        public String filterText;

        public FilterSearch(String filterText){
            this.filterText = filterText;
        }

        @Override
        public void run() {
            Log.e(TAG, "run: "+filterText );
            videos = getVideos(MAIN_ACTIVITY,filterText);
            Message message = new Message();
            message.what = SEARCH_FINISHED;
            handler.sendMessage(message);
        }
    }

}

