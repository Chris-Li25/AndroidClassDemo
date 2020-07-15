package com.android.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactSqliteHelper extends SQLiteOpenHelper {

    public ContactSqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contact(id integer primary key autoincrement, name varchar(20), phoneNumber varchar(20))");
        db.execSQL("insert into contact values(null,'李奕杰','17243859035')");
        db.execSQL("insert into contact values(null,'梁志祥','15348725252')");
        db.execSQL("insert into contact values(null,'刘卓承','19834526958')");
        db.execSQL("insert into contact values(null,'黄智聪','13355672358')");
        db.execSQL("insert into contact values(null,'刘成','15632203258')");
        db.execSQL("insert into contact values(null,'李祥','16373345558')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
