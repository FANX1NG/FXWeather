package com.fanxing.fxweather2.manager;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fanxing.fxweather2.MainActivity;
import com.fanxing.fxweather2.constant.IURL;
import com.fanxing.fxweather2.entity.MyWeatherCityFind;
import com.fanxing.fxweather2.entity.MyWeatherForecast;
import com.fanxing.fxweather2.entity.MyWeatherLifeStyle;
import com.fanxing.fxweather2.entity.MyWeatherNow;
import com.fanxing.fxweather2.entity.MyWeatherTop;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by 繁星 on 2018/12/16.
 */

public class HttpgetWeather {
    static RequestQueue queue = null;

    public static void now(final Context context, String location, final WeatherLoadListener weatherLoadListener) {
        String url =IURL.NOW+"location="+location+"&key="+IURL.APPKEY;
        if (queue==null) {
            queue= Volley.newRequestQueue(context);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                MyWeatherNow myWeatherNow = gson.fromJson(jsonObject.toString(), MyWeatherNow.class);
                weatherLoadListener.onWeatherLoadEnd(myWeatherNow);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
    //加载forecast
    public static void forecast(final Context context, String location, final WeatherForecastListener weatherForecastListener){

        String url = IURL.FORECAST+"location="+location+"&key="+IURL.APPKEY;
//        Log.i("forecast",url);
        if (queue==null) {
            queue= Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                MyWeatherForecast myWeatherForecast = gson.fromJson(jsonObject.toString(), MyWeatherForecast.class);
                weatherForecastListener.onWeatherForecast(myWeatherForecast);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    //加载lifestyle
    public static void lifestyle(final Context context, String location, final WeatherLifestyleListener weatherLifestyleListener){
        String url = IURL.LIFESTYLE+"location="+location+"&key="+IURL.APPKEY;
//        Log.i("forecast",url);
        if (queue==null) {
            queue= Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                MyWeatherLifeStyle myWeatherLifeStyle = gson.fromJson(jsonObject.toString(), MyWeatherLifeStyle.class);
                weatherLifestyleListener.onWeatherLifestyle(myWeatherLifeStyle);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    //城市搜索
    public static void city(final Context context, String location, final WeatherCityFindListener weatherCityFindListener){
        String url = IURL.FIND+"location="+location+"&key="+IURL.APPKEY+"&number=20"+"&group=cn";
        Log.i("forecast",url);
        if (queue==null) {
            queue= Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                MyWeatherCityFind myWeatherCityFind = gson.fromJson(jsonObject.toString(), MyWeatherCityFind.class);
                weatherCityFindListener.onWeatherCityFind(myWeatherCityFind);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    //热门城市
    public static void top(final Context context, final WeatherTopListener weatherTopListener){
        String url = IURL.TOP+"&group=cn"+"&key="+IURL.APPKEY+"&number=15";
        Log.i("forecast",url);
        if (queue==null) {
            queue= Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                MyWeatherTop myWeatherTop = gson.fromJson(jsonObject.toString(), MyWeatherTop.class);
                weatherTopListener.onWeatherTop(myWeatherTop);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public interface WeatherLoadListener {
        void onWeatherLoadEnd(MyWeatherNow myWeatherNow);

    }
    public interface WeatherForecastListener {

        void  onWeatherForecast(MyWeatherForecast myWeatherForecast);
    }
    public interface WeatherLifestyleListener {

        void  onWeatherLifestyle(MyWeatherLifeStyle myWeatherLifeStyle);
    }
    public interface WeatherCityFindListener {

        void  onWeatherCityFind(MyWeatherCityFind myWeatherCityFind);
    }
    public interface WeatherTopListener {

        void  onWeatherTop(MyWeatherTop myWeatherTop);
    }
}
