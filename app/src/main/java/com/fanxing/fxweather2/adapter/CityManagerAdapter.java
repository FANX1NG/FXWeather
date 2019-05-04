package com.fanxing.fxweather2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private boolean flage;
    public int editStatus = 1;
    //已被选择城市的id
    public List<String> selectId = new ArrayList<>();
    //已被选择的按钮
    public List<CheckBox> checkboxList = new ArrayList<>();
    public List<Integer> mid = new ArrayList<>();

    public CityManagerAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<Map<String, String>> arrayList, boolean flage) {
        if (arrayList == null) return;
        this.mData.clear();
        this.mData.addAll(arrayList);
        this.flage = flage;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.citymanager_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mCityCid = (TextView) view.findViewById(R.id.tv_city_cid);
            holder.mCityT = (TextView) view.findViewById(R.id.tv_cityt);
            holder.mCBselect = view.findViewById(R.id.checkbox_select);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // 根据flage来设置checkbox的显示状况
        if (flage) {
            holder.mCBselect.setVisibility(View.VISIBLE);
        } else {
            holder.mCBselect.setVisibility(View.INVISIBLE);
        }
        checkboxList.add(holder.mCBselect);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editStatus == 2) {
                    if (holder.mCBselect.isChecked()) {
                        holder.mCBselect.setChecked(false);
                    } else {
                        holder.mCBselect.setChecked(true);
                    }
                }
            }
        });
        //给选择按钮设置点击事件
        holder.mCBselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectId.add(holder.mCityCid.getText().toString());
                    mid.add(i);
                } else {
                    selectId.remove(mid.indexOf(i));
                    mid.remove(mid.indexOf(i));

                }
            }


        });

        holder.mCityT.setText(mData.get(i).get("city"));
        holder.mCityCid.setText(mData.get(i).get("cid"));

        return view;
    }

    class ViewHolder {
        TextView mCityT;
        TextView mCityCid;
        CheckBox mCBselect;
    }
}
