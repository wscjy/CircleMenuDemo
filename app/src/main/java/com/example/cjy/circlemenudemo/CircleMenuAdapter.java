package com.example.cjy.circlemenudemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CJY on 2017/12/6.
 */

public class CircleMenuAdapter extends BaseAdapter {
    private List<MenuItem> mItems;
    private LayoutInflater mLayoutInflater;

    public CircleMenuAdapter(Context context, List<MenuItem> items) {
        mItems = items;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            view = mLayoutInflater.inflate(R.layout.circle_menu_item, null);
            viewHolder.logo = view.findViewById(R.id.id_circle_menu_item_image);
            viewHolder.content = view.findViewById(R.id.id_circle_menu_item_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.logo.setImageResource(mItems.get(i).getImageId());


        return null;
    }

    public class ViewHolder {
        public ImageView logo;
        public TextView content;

    }
}

