package com.fanxing.fxweather2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanxing.fxweather2.CityManagerActivity;
import com.fanxing.fxweather2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 繁星 on 2019/1/6.
 */

public class CityManagerAdapter extends BaseAdapter {
    private List<Map<String, String>> mData = new ArrayList<>();
    private Context context;

    public CityManagerAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<Map<String, String>> arrayList) {
        if (arrayList == null) return;
        this.mData.clear();
        this.mData.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.citymanager_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mCityCid = (TextView) view.findViewById(R.id.tv_city_cid);
            holder.mCityT = (TextView) view.findViewById(R.id.tv_cityt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mCityT.setText(mData.get(i).get("city"));
        holder.mCityCid.setText(mData.get(i).get("cid"));

        return view;
    }

    class ViewHolder {
        TextView mCityT;
        TextView mCityCid;
    }
}
