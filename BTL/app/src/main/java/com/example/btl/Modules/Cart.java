package com.example.btl.Modules;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.Database.SQLiteAdd;
import com.example.btl.Database.SqliteCart;
import com.example.btl.R;

import java.io.ByteArrayOutputStream;

public class Cart extends AppCompatActivity {
    TextView name_cart, price_cart, total_cart;
    ImageView img_cart;
    Button btn_cart, btn_view_cart;
    public static SqliteCart mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mydb = new SqliteCart(this, "CartDB.sqlite",null, 1);
        mydb.queryData("Create table if not exists Bill(id integer primary key autoincrement , name varchar, price varchar, total varchar, img blob)");

        name_cart = (TextView) findViewById(R.id.name_cart);
        price_cart = (TextView)  findViewById(R.id.price_cart);
        total_cart = (TextView) findViewById(R.id.total);
        img_cart = (ImageView) findViewById(R.id.img_cart);
        btn_cart = (Button) findViewById(R.id.btn_cart);
        btn_view_cart = (Button) findViewById(R.id.btn_view_cart);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String name = bundle.getString("name");
            String price = bundle.getString("price");
            String total = bundle.getString("total");
            int img = bundle.getInt("img");

            name_cart.setText(name);
            price_cart.setText(price);
            total_cart.setText(total);
            img_cart.setImageResource(img);
        }


        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mydb.insertData(name_cart.getText().toString(),
                            price_cart.getText().toString(),
                            total_cart.getText().toString(),
                            imageViewToByte(img_cart));
                    Toast.makeText(Cart.this, "them thanh cong" , Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("Add bill", "them that bai");
                }
            }
        });

        btn_view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cart.this, ListBill.class));
            }
        });
    }
    public static byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhAnh = byteArrayOutputStream.toByteArray();
        return hinhAnh;
    }
}