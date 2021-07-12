package com.example.btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.Class.Bill;
import com.example.btl.Class.Coffee;
import com.example.btl.R;

import java.util.ArrayList;

public class BillAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Bill> list;

    public BillAdapter(Context context, int layout, ArrayList<Bill> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView name_bill, price_bill, total_bill;
        ImageView img_bill;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.name_bill = (TextView) row.findViewById(R.id.name_bill);
            holder.price_bill = (TextView) row.findViewById(R.id.price_bill);
            holder.total_bill = (TextView) row.findViewById(R.id.total_bill);
            holder.img_bill = (ImageView)  row.findViewById(R.id.img_bill);

            row.setTag(holder);
        }
        else {
            holder = (BillAdapter.ViewHolder) row.getTag();
        }

        Bill bill = list.get(position);
        holder.name_bill.setText(bill.getName());
        holder.price_bill.setText(bill.getPrice());
        holder.total_bill.setText(bill.getTotal());

        byte[] billImage = bill.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(billImage, 0, billImage.length);
        holder.img_bill.setImageBitmap(bitmap);

        return row;
    }
}
