package chris.li.myapplication7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    EditText editUser,editPassword;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = (TextView)findViewById(R.id.tvDisplay);
        editUser = (EditText)findViewById(R.id.editUser);
        editPassword = (EditText)findViewById(R.id.editPassword);
        loginbtn = (Button)findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUser = editUser.getText().toString();
                String strPassword = editPassword.getText().toString();
                tvDisplay.setText("Login:"+strUser+"/"+strPassword);
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strInput = s.toString();
                if(strInput.length()>=8){
                    loginbtn.setTextColor(Color.parseColor("#FF0000"));
                }else{
                    loginbtn.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
