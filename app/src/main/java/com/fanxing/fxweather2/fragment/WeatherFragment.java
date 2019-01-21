package com.fanxing.fxweather2.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanxing.fxweather2.MyApplication;
import com.fanxing.fxweather2.R;
import com.fanxing.fxweather2.entity.MyWeatherForecast;
import com.fanxing.fxweather2.entity.MyWeatherLifeStyle;
import com.fanxing.fxweather2.entity.MyWeatherNow;
import com.fanxing.fxweather2.manager.HttpgetWeather;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private String path;
    private Activity activity;
    private TextView cond;
    private TextView temp;
    private TextView hum;
    private TextView pres;
    private TextView windDir;
    private TextView windSc;
    private ImageView imCond;
    private RefreshLayout mrefreshLayout;
    private List<TextView> tvmax;
    private List<TextView> tvmin;
    private List<TextView> tvdate;
    private List<ImageView> ivcond;
    private List<TextView> tvpop;
    private TextView cwt;
    private TextView drst;
    private TextView flut;

    private TextView sportt;
    private TextView uvt;
    private TextView cwb;
    private TextView drsb;
    private TextView flub;

    private TextView sportb;
    private TextView uvb;
    private TextView airb;
    private TextView airt;
    private boolean a= false;
    private boolean b= false;
    private boolean c = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                a = true;
            }else if (msg.what == 2){
                b = true;
            }else if (msg.what==3){
                c = true;
            }

        }
    };
    //    private String[]date_week = {"周一","周二","周三","周四","周五","周六","周日","周一","周二","周三","周四","周五","周六"};
    //    private boolean isInitView = false;
//    private boolean isVisible = false;
    public WeatherFragment() {

    }

    public static WeatherFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        Log.i(path,"onAttach");
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i(path,"onCreate");
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.path = bundle.getString("path");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.i(path,"onCreateView");
//        if (container==null){
//            return null;
//        }else{
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        //初始化变量
        init(view);
//        isInitView = true;
//        //加载数据
//        isCanLoadData();
        loadWeatherNow();
        loadWeatherForecast();
        loadWeatherLifeStyle();
        refreshFragment();
        return view;

//        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.i(path,"onActivityCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
//        Log.i(path,"onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.i(path,"onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.i(path,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.i(path,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.i(path,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Log.i(path,"onDetach");
    }
    //关于懒加载实现的方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.i(path,"setUserVisibleHint"+isVisibleToUser);
//        if(isVisibleToUser){
//            isVisible = true;
//            isCanLoadData();
//        }else{
//            isVisible = false;
//        }

        super.setUserVisibleHint(isVisibleToUser);
    }
    //关于懒加载实现的方法
//    private void isCanLoadData(){
//        //所以条件是view初始化完成并且对用户可见
//        if(isInitView && isVisible ){
//            loadWeatherNow();
//            //防止重复加载数据
//            isInitView = false;
//            isVisible = false;
//        }
//    }
    //初始化变量
    private void init(View view) {
        cond = (TextView) view.findViewById(R.id.tv_cond);
        temp = (TextView) view.findViewById(R.id.tv_temp);
        hum = (TextView) view.findViewById(R.id.tv_hum);
        pres = (TextView) view.findViewById(R.id.tv_pres);
        windDir = (TextView) view.findViewById(R.id.tv_wind_dir);
        windSc = (TextView) view.findViewById(R.id.tv_wind_sc);
        imCond = (ImageView) view.findViewById(R.id.im_cond);
        mrefreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        tvmax = new ArrayList<>();
        tvmax.add((TextView)view.findViewById(R.id.tv_max1));
        tvmax.add((TextView)view.findViewById(R.id.tv_max2));
        tvmax.add((TextView)view.findViewById(R.id.tv_max3));
        tvmax.add((TextView)view.findViewById(R.id.tv_max4));
        tvmax.add((TextView)view.findViewById(R.id.tv_max5));
        tvmax.add((TextView)view.findViewById(R.id.tv_max6));
        tvmin = new ArrayList<>();
        tvmin.add((TextView)view.findViewById(R.id.tv_min1));
        tvmin.add((TextView)view.findViewById(R.id.tv_min2));
        tvmin.add((TextView)view.findViewById(R.id.tv_min3));
        tvmin.add((TextView)view.findViewById(R.id.tv_min4));
        tvmin.add((TextView)view.findViewById(R.id.tv_min5));
        tvmin.add((TextView)view.findViewById(R.id.tv_min6));
        tvdate = new ArrayList<>();
        tvdate.add((TextView)view.findViewById(R.id.tv_date1));
        tvdate.add((TextView)view.findViewById(R.id.tv_date2));
        tvdate.add((TextView)view.findViewById(R.id.tv_date3));
        tvdate.add((TextView)view.findViewById(R.id.tv_date4));
        tvdate.add((TextView)view.findViewById(R.id.tv_date5));
        tvdate.add((TextView)view.findViewById(R.id.tv_date6));
        ivcond = new ArrayList<>();
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond1));
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond2));
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond3));
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond4));
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond5));
        ivcond.add((ImageView) view.findViewById(R.id.iv_cond6));
        tvpop = new ArrayList<>();
        tvpop.add((TextView)view.findViewById(R.id.tv_pop1));
        tvpop.add((TextView)view.findViewById(R.id.tv_pop2));
        tvpop.add((TextView)view.findViewById(R.id.tv_pop3));
        tvpop.add((TextView)view.findViewById(R.id.tv_pop4));
        tvpop.add((TextView)view.findViewById(R.id.tv_pop5));
        tvpop.add((TextView)view.findViewById(R.id.tv_pop6));
        cwt = (TextView)view.findViewById(R.id.tv_cw_t);
        drst = (TextView)view.findViewById(R.id.tv_drsg_t);
        flut = (TextView)view.findViewById(R.id.tv_flu_t);
        airt = (TextView)view.findViewById(R.id.tv_air_t);
        sportt = (TextView)view.findViewById(R.id.tv_sport_t);
        uvt = (TextView)view.findViewById(R.id.tv_uv_t);
        cwb = (TextView)view.findViewById(R.id.tv_cw_b);
        drsb = (TextView)view.findViewById(R.id.tv_drsg_b);
        flub = (TextView)view.findViewById(R.id.tv_flu_b);
        airb = (TextView)view.findViewById(R.id.tv_air_b);
        sportb = (TextView)view.findViewById(R.id.tv_sport_b);
        uvb = (TextView)view.findViewById(R.id.tv_uv_b);

    }

    //加载实况天气
    public void loadWeatherNow(){
        HttpgetWeather.now(activity.getApplicationContext(), path, new HttpgetWeather.WeatherLoadListener() {
            private int titleid;

            @Override
            public void onWeatherLoadEnd(MyWeatherNow myWeatherNow) {

                if (myWeatherNow.getHeWeather6().get(0).getStatus().equals("ok")){
                //天气情况
                cond.setText(myWeatherNow.getHeWeather6().get(0).getNow().getCond_txt());
                //温度
                temp.setText(myWeatherNow.getHeWeather6().get(0).getNow().getTmp() + "℃");
                //湿度
                hum.setText(myWeatherNow.getHeWeather6().get(0).getNow().getHum() + "%");
                //气压
                pres.setText(myWeatherNow.getHeWeather6().get(0).getNow().getPres());
                //风力
                    String wind_sc = myWeatherNow.getHeWeather6().get(0).getNow().getWind_sc();
                    windSc.setText(wind_sc + "级");
                    //风向
                    if (wind_sc.equals("0")){
                        windDir.setText("无风");
                    }else{
                        windDir.setText(myWeatherNow.getHeWeather6().get(0).getNow().getWind_dir());
                    }
                //天气代码
                String cond_code = myWeatherNow.getHeWeather6().get(0).getNow().getCond_code();
//                Log.i("tag",cond_code);
                //获取系统时间
                long l = System.currentTimeMillis();
                Date date = new Date(l);
                int hours = date.getHours();
//                Log.i("time",""+hours);
                //根据时间判断特定图标使用晚间样式
                Resources res = activity.getResources();
                if (((hours >= 18 && hours <= 24) || (hours <= 6 && hours >= 0)) && (cond_code.equals("100") || cond_code.equals("103") || cond_code.equals("104") || cond_code.equals("301") || cond_code.equals("300") || cond_code.equals("406") || cond_code.equals("407"))) {
                    titleid = res.getIdentifier("icon_" + cond_code + "n",//需要转换的资源名称
                            "drawable",        //资源类型
                            "com.fanxing.fxweather2");//R类所在的包名
                } else {
                    titleid = res.getIdentifier("icon_" + cond_code,//需要转换的资源名称
                            "drawable",        //资源类型
                            "com.fanxing.fxweather2");//R类所在的包名
                }
                //设置天气图标
                imCond.setImageResource(titleid);
                    Message message = new Message();
                    message.what = 1;
                    handler.handleMessage(message);

            }

            }
        });

    }

    //加载未来七天的天气
    public void loadWeatherForecast(){
        HttpgetWeather.forecast(activity.getApplicationContext(), path, new HttpgetWeather.WeatherForecastListener() {

            private int titleid;

            @Override
            public void onWeatherForecast(MyWeatherForecast myWeatherForecast) {
                if (myWeatherForecast.getHeWeather6().get(0).getStatus().equals("ok")){
                long l = System.currentTimeMillis();
                Date date = new Date(l);
                int hours = date.getHours();
                Resources res = activity.getResources();
                for (int i = 0;i<6;i++) {
                    tvmax.get(i).setText(myWeatherForecast.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_max());
                    tvmin.get(i).setText(myWeatherForecast.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_min());
                    if (i==0){
                        tvdate.get(i).setText("今天");
                        tvdate.get(i).setTextSize(14);
                    }else{
                        tvdate.get(i).setText(myWeatherForecast.getHeWeather6().get(0).getDaily_forecast().get(i).getDate().substring(5));
                    }
                    //天气代码
                    String cond_code_d = myWeatherForecast.getHeWeather6().get(0).getDaily_forecast().get(i).getCond_code_d();
                    //根据时间判断特定图标使用晚间样式
                    if (((hours >= 18 && hours <= 24) || (hours <= 6 && hours >= 0)) && (cond_code_d.equals("100") || cond_code_d.equals("103") || cond_code_d.equals("104") || cond_code_d.equals("301") || cond_code_d.equals("300") || cond_code_d.equals("406") || cond_code_d.equals("407"))) {
                        titleid = res.getIdentifier("icon_" + cond_code_d + "n",//需要转换的资源名称
                                "drawable",        //资源类型
                                "com.fanxing.fxweather2");//R类所在的包名
                    } else {
                        titleid = res.getIdentifier("icon_" + cond_code_d,//需要转换的资源名称
                                "drawable",        //资源类型
                                "com.fanxing.fxweather2");//R类所在的包名
                    }
                    ivcond.get(i).setImageResource(titleid);
                    tvpop.get(i).setText(myWeatherForecast.getHeWeather6().get(0).getDaily_forecast().get(i).getPop()+"%");
                }
                    Message message = new Message();
                    message.what= 2;
                    handler.handleMessage(message);
            }}
        });

    }
    //加载生活指数
    public boolean loadWeatherLifeStyle(){
        HttpgetWeather.lifestyle(activity.getApplicationContext(), path, new HttpgetWeather.WeatherLifestyleListener() {
            @Override
            public void onWeatherLifestyle(MyWeatherLifeStyle myWeatherLifeStyle) {
                if (myWeatherLifeStyle.getHeWeather6().get(0).getStatus().equals("ok")){
                    for (int i = 0;i<myWeatherLifeStyle.getHeWeather6().get(0).getLifestyle().size();i++){
                        MyWeatherLifeStyle.HeWeather6Bean.LifestyleBean lifestyleBean = myWeatherLifeStyle.getHeWeather6().get(0).getLifestyle().get(i);
                        switch (lifestyleBean.getType()){
                            case "air":
                                airt.setText("空气污染扩散条件指数  "+lifestyleBean.getBrf());
                                airb.setText(lifestyleBean.getTxt());
                                break;
                            case "flu":
                                flut.setText("感冒指数  "+lifestyleBean.getBrf());
                                flub.setText(lifestyleBean.getTxt());
                                break;
                            case "cw":
                                cwt.setText("洗车指数  "+lifestyleBean.getBrf());
                                cwb.setText(lifestyleBean.getTxt());
                                break;
                            case "drsg":
                                drst.setText("穿衣指数  "+lifestyleBean.getBrf());
                                drsb.setText(lifestyleBean.getTxt());
                                break;
                            case "uv":
                                uvt.setText("紫外线指数  "+lifestyleBean.getBrf());
                                uvb.setText(lifestyleBean.getTxt());
                                break;
                            case "sport":
                                sportt.setText("运动指数  "+lifestyleBean.getBrf());
                                sportb.setText(lifestyleBean.getTxt());
                                break;

                        }


                    }
                    Message message = new Message();
                    message.what = 3;
                    handler.handleMessage(message);
                }

            }
        });
        return true;
    }

//刷新
    public void refreshFragment(){
        mrefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadWeatherNow();
                loadWeatherForecast();
                loadWeatherLifeStyle();
//                if (a&&b&&c) {
//                    a = false;
//                    b = false;
//                    c = false;
//                    Toast.makeText(activity, "刷新完成", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(true);
//                }

            }
        });

    }

    //获得Conteex
    public Context getContext() {
        if (activity == null) {
            return MyApplication.getInstance();
        }
        return activity;
    }
}
