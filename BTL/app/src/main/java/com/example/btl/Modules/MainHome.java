package com.example.btl.Modules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.Adapter.CoffeeListAdapter;
import com.example.btl.Class.Coffee;
import com.example.btl.R;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity {
    Button btn_setting, btn_cart;
    TextView txtName, txtGia;
    ImageView img_item;
    final int RESULT_PRODUCT_ACTIVITY = 1;
    GridView gridViewMain;
    ArrayList<Coffee> listMain;
    CoffeeListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);




        gridViewMain = (GridView) findViewById(R.id.gridViewMain);
        listMain = new ArrayList<>();
        adapter = new CoffeeListAdapter(this, R.layout.coffee_items, listMain);
        gridViewMain.setAdapter(adapter);


        btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_cart = (Button) findViewById(R.id.btn_cart);

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHome.this, AddOrder.class));
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHome.this, AddNew.class));
            }
        });
        Cursor c;
        c = AddNew.db.getData("Select * from Coffee");

        listMain.clear();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String price = c.getString(2);
            byte[] img = c.getBlob(3);

            listMain.add(new Coffee(id, name, price, img));
        }

        adapter.notifyDataSetInvalidated();

        // set item click

        gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence[] items = {"Add to cart", "Cancel"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainHome.this);
                dialog.setTitle("Chose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item==0){

                            showDialogAddCart();
                        }

                        else {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    private void showDialogAddCart(){
        final AlertDialog.Builder dialogCart = new AlertDialog.Builder(MainHome.this);
        dialogCart.setTitle("Go to cart");
        dialogCart.setMessage("Đi tới phần order?");


        dialogCart.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                   Intent intent = new Intent(MainHome.this, AddOrder.class);

                   startActivity(intent);
                }
                catch (Exception e){
                    Log.e("Add to Cart", "Add fail");
                }

            }
        });
        dialogCart.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogCart.show();
    }


}