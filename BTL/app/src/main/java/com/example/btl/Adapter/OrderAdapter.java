package com.example.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.Class.Order;
import com.example.btl.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Order> listOrder;

    public OrderAdapter(Context context, int layout, List<Order> listOrder) {
        this.context = context;
        this.layout = layout;
        this.listOrder = listOrder;
    }

    @Override
    public int getCount() {
        return listOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(layout, null);

        Order order = listOrder.get(position);

        TextView add_order_name = row.findViewById(R.id.add_order_name);
        TextView add_order_price = row.findViewById(R.id.add_order_price);
        ImageView img_add_order = row.findViewById(R.id.img_add_order);


        add_order_name.setText(order.getName());
        add_order_price.setText(order.getPrice());
        img_add_order.setImageResource(order.getImg());
        return row;
    }
}
