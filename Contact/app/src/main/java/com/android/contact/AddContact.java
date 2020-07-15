package com.android.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    private EditText addContact_name;
    private EditText addContact_phoneNumber;
    private Button addContact_save;
    private Button addContact_cancel;
    private ContactSqliteHelper contactSqliteHelper;
    private SQLiteDatabase contactDb;

    private String contact_id;
    private String contact_name;
    private String contact_phoneNumber;

    public static void actionStart(Context context, String data1, String data2, String data3) {
        Intent intent = new Intent(context, AddContact.class);
        intent.putExtra("contact_id", data1);
        intent.putExtra("name", data2);
        intent.putExtra("phoneNumber", data3);
        context.startActivity(intent);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddContact.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        addContact_name = (EditText) findViewById(R.id.addContact_name);
        addContact_phoneNumber = (EditText) findViewById(R.id.addContact_phoneNumber);
        addContact_save = findViewById(R.id.addContact_save);
        addContact_cancel = findViewById(R.id.addContact_cancel);

        addContact_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addContact_name.getText().toString().trim().isEmpty() || addContact_name.getText().toString() == null ||
                        addContact_phoneNumber.getText().toString().trim().isEmpty() || addContact_phoneNumber.getText().toString() == null) {
                    ToastUtil.showToast(getApplicationContext(), "姓名或电话号码不能为空");
                } else {
                    contactSqliteHelper = new ContactSqliteHelper(AddContact.this, "contact.db", null, 1);
                    contactDb = contactSqliteHelper.getWritableDatabase();
                    contactDb.execSQL("insert into contact(name, phoneNumber) values(?, ?)", new Object[]{addContact_name.getText(), addContact_phoneNumber.getText()});
                    contactDb.close();
                    ToastUtil.showToast(getApplicationContext(), "添加联系人成功");
                    finish();
                }
            }
        });

        addContact_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getApplicationContext(), "取消操作");
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ToastUtil.showToast(getApplicationContext(), "取消操作");
    }
}
