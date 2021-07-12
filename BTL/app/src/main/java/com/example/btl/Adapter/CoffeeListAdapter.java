package com.example.btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.Class.Coffee;
import com.example.btl.R;

import java.util.ArrayList;

public class CoffeeListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Coffee> list;

    public CoffeeListAdapter(Context context, int layout, ArrayList<Coffee> list){
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
        ImageView img_item;
        TextView txtName, txtPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            holder.img_item = (ImageView) row.findViewById(R.id.img_item);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Coffee coffee = list.get(position);
        holder.txtName.setText(coffee.getName());
        holder.txtPrice.setText(coffee.getPrice());

        byte[] coffeeImage = coffee.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(coffeeImage, 0, coffeeImage.length);

        holder.img_item.setImageBitmap(bitmap);

        return row;
    }
}
