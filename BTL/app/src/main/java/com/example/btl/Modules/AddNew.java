package com.example.btl.Modules;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btl.Database.SQLiteAdd;
import com.example.btl.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddNew extends AppCompatActivity {

    EditText inputName, inputPrice;
    ImageView img;
    Button btn_chose, btn_add, btn_delete, btn_update , btn_main, btn_home;
    int folder = 456;
    public static SQLiteAdd db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        img = (ImageView) findViewById(R.id.img);
        btn_chose = (Button) findViewById(R.id.btn_chose);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_home = (Button) findViewById(R.id.btn_home);
        btn_main = (Button) findViewById(R.id.btn_main);

        db = new SQLiteAdd(this, "CoffeeDB.sqlite", null, 1);
        db.queryData("Create table if not exists Coffee(id integer primary key autoincrement , name varchar, price varchar, img blob)");

        btn_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, folder);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertData(
                        inputName.getText().toString().trim(),
                        inputPrice.getText().toString().trim(),
                        imageViewToByte(img)
                );
                Toast.makeText(AddNew.this, "them thanh cong" , Toast.LENGTH_LONG).show();
                inputName.setText("");
                inputPrice.setText("");
                img.setImageResource(R.drawable.background1);
            }
        });

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddNew.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNew.this, MainHome.class));
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == folder && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhAnh = byteArrayOutputStream.toByteArray();
        return hinhAnh;
    }

}