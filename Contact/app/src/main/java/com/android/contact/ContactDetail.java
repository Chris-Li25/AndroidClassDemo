package com.android.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class ContactDetail extends AppCompatActivity {

    private LinearLayout editArea;
    private EditText contactDetail_name;
    private EditText contactDetail_phoneNumber;
    private Button contactDetail_save;
    private Button contactDetail_cancel;
    private ContactSqliteHelper contactSqliteHelper;
    private SQLiteDatabase contactDb;

    private String contact_id;
    private String contact_name;
    private String contact_phoneNumber;


    public static void actionStart(Context context, String data1, String data2, String data3) {
        Intent intent = new Intent(context, ContactDetail.class);
        intent.putExtra("contact_id", data1);
        intent.putExtra("name", data2);
        intent.putExtra("phoneNumber", data3);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        editArea = (LinearLayout) findViewById(R.id.editArea);
        contactDetail_name = (EditText) findViewById(R.id.contactDetail_name);
        contactDetail_phoneNumber = (EditText) findViewById(R.id.contactDetail_phoneNumber);
        contactDetail_save = findViewById(R.id.contactDetail_save);
        contactDetail_cancel = findViewById(R.id.contactDetail_cancel);

        Intent intent = getIntent();
        contact_id = intent.getStringExtra("contact_id");
        contact_name = intent.getStringExtra("name");
        contact_phoneNumber = intent.getStringExtra("phoneNumber");

        contactDetail_name.setText(contact_name);
        contactDetail_phoneNumber.setText(contact_phoneNumber);

        contactDetail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactDetail_name.getText().toString().trim().isEmpty() || contactDetail_name.getText().toString() == null ||
                        contactDetail_phoneNumber.getText().toString().trim().isEmpty() || contactDetail_phoneNumber.getText().toString() == null) {
                    ToastUtil.showToast(getApplicationContext(), "姓名或电话号码不能为空");
                } else {
                    contactSqliteHelper = new ContactSqliteHelper(ContactDetail.this, "contact.db", null, 1);
                    contactDb = contactSqliteHelper.getWritableDatabase();
                    contactDb.execSQL("update contact set name = ?,phoneNumber = ? where id = ?", new Object[]{contactDetail_name.getText(), contactDetail_phoneNumber.getText(), contact_id});
                    contactDb.close();
                    ToastUtil.showToast(getApplicationContext(), "修改成功");
                    finish();
                }
            }
        });

        contactDetail_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getApplicationContext(), "取消操作");
                contactDetail_name.setText(contact_name);
                contactDetail_phoneNumber.setText(contact_phoneNumber);
                editArea.setVisibility(View.INVISIBLE);
                contactDetail_name.setEnabled(false);
                contactDetail_phoneNumber.setEnabled(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editContact:
                editArea.setVisibility(View.VISIBLE);
                contactDetail_name.setEnabled(true);
                contactDetail_phoneNumber.setEnabled(true);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(editArea.getVisibility()==0){
            ToastUtil.showToast(getApplicationContext(), "取消操作");
        }
    }
}
