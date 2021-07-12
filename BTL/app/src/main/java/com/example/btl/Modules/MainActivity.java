package com.example.btl.Modules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btl.Adapter.CoffeeListAdapter;
import com.example.btl.Class.Coffee;
import com.example.btl.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_imgUpdate , btn_update;
    ImageView imgUpdate;
    private int id =0;
    int arrID;
    GridView gridView;
    ArrayList<Coffee> list;
    CoffeeListAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_imgUpdate = (Button) findViewById(R.id.btn_imgUpdate);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new CoffeeListAdapter(this, R.layout.coffee_items, list);
        gridView.setAdapter(adapter);

        //get Data
        Cursor cursor = AddNew.db.getData("Select * from Coffee");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] img = cursor.getBlob(3);

            list.add(new Coffee(id, name, price, img));
        }

        adapter.notifyDataSetInvalidated();
// set items
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                show dialog update
                CharSequence[] items = {"update", "delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Chose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Cursor c = AddNew.db.getData("SELECT id FROM Coffee");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(MainActivity.this, arrID.get(position));

                        }  else if (item == 1){
                            Cursor c = AddNew.db.getData("SELECT id FROM Coffee");
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
    private void updateCoffee(){
        Cursor cursor = AddNew.db.getData("Select * from Coffee");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] img = cursor.getBlob(3);

            list.add(new Coffee(id, name, price, img));
        }
        adapter.notifyDataSetInvalidated();
    }


    //tao phan hien thi update
    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update);
        dialog.setTitle("Update");

        btn_imgUpdate = (Button) dialog.findViewById(R.id.btn_imgUpdate);
        imgUpdate = (ImageView) dialog.findViewById(R.id.img_update);
        final EditText nameUpdate = (EditText) dialog.findViewById(R.id.nameUpdate);
        final EditText  priceUpdate = (EditText) dialog.findViewById(R.id.priceUpdate);
        btn_update = (Button) dialog.findViewById(R.id.btn_update);

        // set chieu ngang cho phan hien thi
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        //set chieu cao cho phan hien thi
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        if(btn_imgUpdate != null){
            btn_imgUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 456);

                }
            });
        }else{
            Log.e("DIalog", "btn null");
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AddNew.db.updateData(
                            nameUpdate.getText().toString().trim(),
                            priceUpdate.getText().toString().trim(),
                            AddNew.imageViewToByte(imgUpdate),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this,"Update thanh cong", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    Log.e("update", "update fail");
                }
                updateCoffee();
            }
        });

    }
    //hien thi dialog xoa san pham
    private void showDialogDelete(final int idC){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this);
        dialogDelete.setTitle("Delete");
        dialogDelete.setMessage("Ban co muon xoa san pham nay khong");
        dialogDelete.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    AddNew.db.deleteData(idC);
                    Toast.makeText(MainActivity.this,"Delete thanh cong", Toast.LENGTH_SHORT).show();

                    updateCoffee();
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

    //load lai danh sach san pham

    //chuyen doi kieu anh de lay du lieu
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUpdate.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

