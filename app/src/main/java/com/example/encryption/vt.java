package com.example.encryption;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class vt extends SQLiteOpenHelper {
    private static final String DB_NAME="KULLANICI";

    public vt(Context c){

        super(c,DB_NAME,null,8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("vt","Create");
        db.execSQL("CREATE TABLE IF NOT EXISTS Bilgiler(id INTEGER PRIMARY KEY AUTOINCREMENT,isim TEXT NOT NULL," +
                "mail TEXT NOT NULL,sifre TEXT NOT NULL,encrypion TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("vt","Upgrade");
        db.execSQL("DROP TABLE IF EXISTS Bilgiler");
        onCreate(db);
    }

}
