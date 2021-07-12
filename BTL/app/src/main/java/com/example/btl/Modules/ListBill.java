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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.Adapter.BillAdapter;
import com.example.btl.Class.Bill;
import com.example.btl.Class.Coffee;
import com.example.btl.Database.SqliteCart;
import com.example.btl.R;

import java.util.ArrayList;
import java.util.List;

public class ListBill extends AppCompatActivity {
    Button btn_check, btn_con;
    GridView gridView;
    ArrayList<Bill> list;
    BillAdapter adapter = null;
    TextView total_cart,item_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);
        total_cart = (TextView) findViewById(R.id.total_cart);
        item_count = (TextView) findViewById(R.id.item_count);
        gridView = (GridView) findViewById(R.id.list_bill);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_con = (Button) findViewById(R.id.btn_con);
        list = new ArrayList<>();
        adapter = new BillAdapter(ListBill.this , R.layout.bill_items, list);
        gridView.setAdapter(adapter);

        Cursor cursor = Cart.mydb.getData("Select * from Bill");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String total = cursor.getString(3);
            byte[] img = cursor.getBlob(4);

            list.add(new Bill(id, name, price,total, img));

        }
        adapter.notifyDataSetInvalidated();

        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListBill.this, AddOrder.class));
            }
        });


        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.mydb.deleteAll();
                Toast.makeText(ListBill.this, "Thanh toan thanh cong" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ListBill.this, AddOrder.class));
            }
        });


        total_cart.setText(String.valueOf(calculateTotal(list)));
        item_count.setText(String.valueOf(countItem(list)));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence[] items = {"Delete", "Cancel"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListBill.this);

                dialog.setTitle("Chose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item==0){
                            Cursor c = Cart.mydb.getData("SELECT id FROM Bill");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }
                    }
                });
                dialog.show();
            }
        });

    }
    private void updateBill(){
        Cursor cursor = Cart.mydb.getData("Select * from Bill");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String total = cursor.getString(3);
            byte[] img = cursor.getBlob(4);

            list.add(new Bill(id, name, price,total, img));


        }
        adapter.notifyDataSetInvalidated();
    }
    private void showDialogDelete(final int idC){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListBill.this);
        dialogDelete.setTitle("Delete");
        dialogDelete.setMessage("Ban co muon xoa san pham nay khong");
        dialogDelete.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Cart.mydb.deleteData(idC);
                    Toast.makeText(ListBill.this,"Delete thanh cong", Toast.LENGTH_SHORT).show();

                    updateBill();
                }
                catch (Exception e){
                    Log.e("delete", "delete fail");
                }

            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
    private double calculateTotal(List<Bill> itemList) {
        int result = 0;
        for (int i = 0; i < itemList.size(); i++) {
            result += Integer.parseInt(itemList.get(i).getPrice());
        }
        return result;
    }

    private double countItem(List<Bill> itemList){

        int item = 0;
        for (int i = 0; i < itemList.size(); i++) {

            item += Integer.parseInt(String.valueOf(itemList.get(i).getId()));
        }
        return item;
    }

}