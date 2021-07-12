package com.example.btl.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SqliteCart extends SQLiteOpenHelper {


    public SqliteCart( Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void insertData(String name, String price, String total, byte[] img){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Bill values(Null, ?, ?,?, ?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindString(3, total);
        statement.bindBlob(4, img);

        statement.executeInsert();
    }
    public  void deleteData(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE From Bill WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete  from Bill");
        db.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
