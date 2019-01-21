package com.fanxing.fxweather2.getLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 繁星 on 2019/1/8.
 */

public class GetLocation {
    private Context context;

    public GetLocation(Context context) {
        this.context = context;
    }

    //获取位置
    public Location getLocation() {
        //获得位置服务
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String s = judgeProvider(lm);
        //有位置提供器的情况
        if (s != null) {
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return lm.getLastKnownLocation(s);
        } else {
            //不存在位置提供器的情况
            Toast.makeText(context.getApplicationContext(), "不存在位置提供器的情况", Toast.LENGTH_LONG).show();
        }
        return null;
    }
    //判断使用什么定位
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;//网络定位
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;//GPS定位
        } else {
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
