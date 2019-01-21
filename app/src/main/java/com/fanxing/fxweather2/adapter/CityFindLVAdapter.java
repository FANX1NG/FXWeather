package com.fanxing.fxweather2.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanxing.fxweather2.AddCityActivity;
import com.fanxing.fxweather2.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/5.
 */

public class CityFindLVAdapter extends BaseAdapter {
    private ArrayList<Map<String, String>> maps;
    private Context context;

    public CityFindLVAdapter(ArrayList<Map<String, String>> maps, Context applicationContext) {
        this.maps = maps;
        this.context = applicationContext;

    }


    @Override
    public int getCount() {
        return maps.size();
    }

    @Override
    public Object getItem(int i) {
        return maps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.city_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mLocation = (TextView) view.findViewById(R.id.tv_location);
            holder.mParentCity = (TextView) view.findViewById(R.id.tv_parent_city);
            holder.mAdminArea = (TextView) view.findViewById(R.id.tv_admin_area);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mLocation.setText(maps.get(i).get("Location"));
        holder.mParentCity.setText(maps.get(i).get("ParentCity"));
        holder.mAdminArea.setText(maps.get(i).get("AdminArea"));
        holder.mLocation.setTag(maps.get(i).get("cid"));
        return view;
    }

    class ViewHolder {
        TextView mLocation;
        TextView mParentCity;
        TextView mAdminArea;

    }
}
