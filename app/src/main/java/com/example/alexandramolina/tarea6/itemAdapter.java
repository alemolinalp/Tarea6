package com.example.alexandramolina.tarea6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexandramolina on 24/5/18.
 */

public class itemAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<Item> arrayList;

    public itemAdapter(Context context, int layout, ArrayList<Item> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txt_name, txt_price, txt_description;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);

            viewHolder.txt_name = view.findViewById(R.id.list_name);
            viewHolder.txt_price = view.findViewById(R.id.list_price);
            viewHolder.txt_description = view.findViewById(R.id.list_des);
            //viewHolder.imageView = view.findViewById(R.id.imageView2);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final Item item = arrayList.get(i);
        viewHolder.txt_name.setText(item.getItemName());
        viewHolder.txt_price.setText(item.getItemPrice());
        viewHolder.txt_description.setText(item.getItemDes());
        //viewHolder.imageView.setImageBitmap(item.getItemImage());

        return view;
    }
}
