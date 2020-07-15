package hello.test.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    ImageView i1;
    Button b1;
    Button b2;
    Button b3;
    Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("LifeCycle","--onCreate--");

        t1 = (TextView)findViewById(R.id.T1);
        b1 = (Button)findViewById(R.id.B1);
        b2 = (Button)findViewById(R.id.B2);
        b3 = (Button)findViewById(R.id.B3);
        b4 = (Button)findViewById(R.id.B4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("b1111111111111111111111");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("B2222222222");
            }
        });

        setListener();

    }

    public void setListener(){
        OnClick onClick = new OnClick();
        b3.setOnClickListener(onClick);
        b4.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.B3:
                    t1.setText("B3333333");
                    break;
                case R.id.B4:
                    t1.setText("B4444444");
            }
        }
    }

    public void b1_onCLick(View v){
        t1.setText("B11111111");
    }

}
