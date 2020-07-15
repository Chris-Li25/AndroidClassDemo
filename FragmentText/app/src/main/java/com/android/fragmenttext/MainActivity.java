package com.android.fragmenttext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyFragment_1 myFragment_1;
    private MyFragment_2 myFragment_2;
    private Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFragment_1 = new MyFragment_1();
        myFragment_2 = new MyFragment_2();

        btnChange = (Button)findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flTitle,myFragment_2).commitAllowingStateLoss();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.flTitle,myFragment_1).commitAllowingStateLoss();
    }
}
