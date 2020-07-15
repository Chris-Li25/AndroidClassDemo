package hello.test.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TwoLayout extends AppCompatActivity {

    private TextView tv;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_layout);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoLayout.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv = (TextView)findViewById(R.id.tv);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        Integer height = bundle.getInt("height");

        tv.setText(name+","+height);
    }
}
