package com.android.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private Button start_btn;
    private Button stop_btn;
    private Chronometer chronometer;
    private long recordTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = (Button)findViewById(R.id.start_btn);
        stop_btn = (Button)findViewById(R.id.stop_btn) ;
        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }


    public void Start(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime() - recordTime);
        recordTime = 0;
        chronometer.start();
        start_btn.setEnabled(false);
        stop_btn.setEnabled(true);
    }

    public void Stop(View view) {
        chronometer.stop();
        recordTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        start_btn.setEnabled(true);
        stop_btn.setEnabled(false);
    }

    public void Reset(View view) {
        recordTime = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        start_btn.setEnabled(true);
        stop_btn.setEnabled(false);
    }
}
