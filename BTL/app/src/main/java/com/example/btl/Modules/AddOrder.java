package com.example.btl.Modules;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.btl.Adapter.OrderAdapter;
import com.example.btl.Class.Order;
import com.example.btl.R;

import java.util.ArrayList;

public class AddOrder extends AppCompatActivity {
    TextView add_order_name, add_order_price;
    ImageView img_add_order;
    int i =0;
    ListView listView;
    ArrayList<Order> arrayList;
    OrderAdapter adapter;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        add_order_name = (TextView) findViewById(R.id.add_order_name);
        add_order_price = (TextView) findViewById(R.id.add_order_price);
        img_add_order = (ImageView) findViewById(R.id.img_add_order);

        listView = (ListView) findViewById(R.id.list_add_order);

        String[] name = {"Den Da", "Bac Xiu", "Capuchino", "Cot Dua", "Ca Phe Muoi", "Ca Phe Trung"};
        String[] price = {"30000", "30000", "40000", "35000", "40000", "40000"};
        int[] img = {R.drawable.denda, R.drawable.bacxiu, R.drawable.capuchino, R.drawable.cotdua, R.drawable.muoi, R.drawable.trung};

        arrayList = new ArrayList<>();

       for (int i = 0; i< img.length; i ++){
            Order order = new Order(name[i], price[i], img[i]);
            arrayList.add(order);
        }


        adapter = new OrderAdapter(AddOrder.this, R.layout.order_items, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(AddOrder.this, Cart.class);
                    intent.putExtra("name", name[position] );
                    intent.putExtra("price", price[position] );
                    intent.putExtra("total", price[position]);
                    intent.putExtra("img", img[position]);
                    startActivity(intent);

                
            }
        });
    }
}