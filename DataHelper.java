package com.example.ain_individual;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "electricity.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE bill(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "month TEXT, unit REAL, rebate REAL, total REAL, final REAL);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS bill");
        onCreate(db);
    }
}