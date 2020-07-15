package com.android.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private Button contactSave, contactRead;
    private EditText contact_name, contact_phoneNumber;
    private RecyclerView contactList;
    private ContactAdapter contactAdapter;

    public int contactNumber ;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactSave = (Button) findViewById(R.id.contactSave);
        contactRead = (Button) findViewById(R.id.contactRead);
        contact_phoneNumber = (EditText) findViewById(R.id.contact_phoneNumber);
        contact_name = (EditText) findViewById(R.id.contact_name);
        contactList = findViewById(R.id.contactList);

        sharedPreferences = getSharedPreferences("contact", MODE_PRIVATE);//读取
        spEditor = sharedPreferences.edit();   //写入
        contactNumber = sharedPreferences.getInt("contactNumber",0);

        RecyclerView contactList = (RecyclerView) findViewById(R.id.contactList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        contactList.setLayoutManager(layoutManager);
        contactAdapter = new ContactAdapter(this,sharedPreferences);
        contactList.setAdapter(contactAdapter);

        contactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contact_name.getText().toString().trim().isEmpty() || contact_name.getText().toString() == null
                        || contact_phoneNumber.getText().toString().trim().isEmpty() || contact_phoneNumber.getText().toString() == null) {
                    ToastUtil.showToast(getApplicationContext(),"联系人姓名或电话不能为空");
                } else {
                    contactNumber++;
                    spEditor.putInt("contact_id"+contactNumber,contactNumber);
                    spEditor.putString("contact_Name" + contactNumber,contact_name.getText().toString()); //写入
                    spEditor.putString("contact_phoneNumber"+contactNumber,contact_phoneNumber.getText().toString());
                    spEditor.putInt("contactNumber",contactNumber);
                    spEditor.apply(); //提交
                    contact_name.setText("");
                    contact_phoneNumber.setText("");
                    ToastUtil.showToast(getApplicationContext(),"保存成功");
                }
            }
        });


        contactRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactAdapter.refreshContact(contactNumber);
                ToastUtil.showToast(getApplicationContext(),"已显示联系人");
            }
        });

    }
}
