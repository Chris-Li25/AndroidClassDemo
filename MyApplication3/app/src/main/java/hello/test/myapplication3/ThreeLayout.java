package hello.test.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreeLayout extends AppCompatActivity {

    private TextView tv;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_layout);

        setTitle("ThreeThreeThree");

        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //(ThreeLayout.this,MainActivity.class);
                intent.putExtra("msg","I am back!!!");
                setResult(Activity.RESULT_OK,intent);
//                startActivity(intent);
                finish();
            }
        });

        tv = (TextView)findViewById(R.id.tv);
        String msg = getIntent().getStringExtra("msg");
        tv.setText(msg);

    }
}
