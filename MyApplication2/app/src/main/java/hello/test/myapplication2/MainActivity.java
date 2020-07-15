package hello.test.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button B1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifeCycle:","----onStart----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LifeCycle:","----onResume----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifeCycle:","----onPause----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifeCycle:","----onStop----");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("LifeCycle:","----onRestart----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifeCycle:","----onDestroy----");
    }
}