package hello.test.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn1,btn2,btn3, btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button)findViewById(R.id.b1);
        btn2 = (Button)findViewById(R.id.b2);
        btn3 = (Button)findViewById(R.id.b3);
        btnQuit = (Button)findViewById(R.id.bQuit);
        tv = (TextView)findViewById(R.id.tv);

        setListeners();
    }

    private  void setListeners(){
        Onclick onclick = new Onclick();
        btn1.setOnClickListener(onclick);
        btn2.setOnClickListener(onclick);
        btn3.setOnClickListener(onclick);
        btnQuit.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.b1:
                    tv.setText("One");
                    Intent intent = new Intent(MainActivity.this, OneLayout.class);
                    intent.putExtra("msg","Main --> One!");
                    startActivity(intent);
                    break;
                case R.id.b2:
                    tv.setText("Two");
                    intent = new Intent(MainActivity.this,TwoLayout.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name","LYJ");
                    bundle.putInt("height",13);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.b3:
                    tv.setText("Three");
                    intent = new Intent(MainActivity.this,ThreeLayout.class);
                    intent.putExtra("msg","Main --> Three!");
                    startActivityForResult(intent,0);
                    break;
                case R.id.bQuit:
                    tv.setText("Quit");
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                tv.setText(data.getStringExtra("msg"));
                break;
        }
    }
}
