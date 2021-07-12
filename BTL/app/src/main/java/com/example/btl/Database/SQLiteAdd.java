package com.example.btl.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.btl.Class.Coffee;

public class SQLiteAdd extends SQLiteOpenHelper {
    public SQLiteAdd( Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteAdd( Context context) {
        super(context, "Coffee", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void insertData(String name, String price, byte[] img){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Coffee values(Null, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, img);

        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Coffee getValue(int ID) {
        Coffee coffee = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, price from product where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            coffee = new Coffee(id, name, price, img);
        }
        cursor.close();
        return coffee;
    }
    public void updateData(String name, String price, byte[] img, int id){
        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("name", coffee.getName());
//        values.put("price", coffee.getPrice());
//        values.put("img", coffee.getImg());
//        return db.update("Coffee", values, "id = ?", new String[]{String.valueOf(coffee.getId())});

        String sql = "UPDATE Coffee SET name = ?, price =?, img =? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindDouble(4, (double)(id));
        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, img);

        statement.execute();
        db.close();
    }
    public  void deleteData(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE From Coffee WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
