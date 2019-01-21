package com.fanxing.fxweather2;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanxing.fxweather2.SQLite.MySQLiteOpenHelper;
import com.fanxing.fxweather2.adapter.FragmentAdapter;
import com.fanxing.fxweather2.entity.MyWeatherCityFind;
import com.fanxing.fxweather2.fragment.WeatherFragment;
import com.fanxing.fxweather2.getLocation.GPSUtils;
import com.fanxing.fxweather2.getLocation.GetLocation;
import com.fanxing.fxweather2.getPermissions.GP;
import com.fanxing.fxweather2.manager.HttpgetWeather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ib_toolbar_add)
    ImageButton ibToolbarAdd;

    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layou)
    DrawerLayout drawerLayou;
    @BindView(R.id.ll_yuandian)
    LinearLayout llYuandian;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private List<Map<String, String>> paths = new ArrayList<>();

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                title.setText(msg.obj.toString());
                fragmentAdapter.updateList(fragments);
            }
        }
    };
    private SQLiteDatabase db;
    private HashMap<String, String> map;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private boolean b = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //监听
        myListener();
        //初始化viewPager
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        //创建&&打开数据库
        opendb();
        //加载定位城市
        initLocation(1);
        //加载数据
        initData();
        //状态栏沉浸
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        隐藏ActionBar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

    }


    //返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                boolean a = true;
                int i =0;
                for (;i<paths.size();i++) {
                    if (paths.get(i).get("city").equals(data.getStringExtra("city")) && paths.get(i).get("cid").equals(data.getStringExtra("cid"))) {
                        a = false;
                        break;
                    }
                }
                if (a) {
                    title.setText(data.getStringExtra("city"));
                    map = new HashMap<>();
                    map.put("city", data.getStringExtra("city"));
                    map.put("cid", data.getStringExtra("cid"));
                    paths.add(map);
                    fragments.add(WeatherFragment.newInstance(data.getStringExtra("city")));
                    fragmentAdapter.updateList(fragments);
                    viewPager.setCurrentItem(paths.size() - 1);
                    ContentValues values = new ContentValues();
                    values.put("city", data.getStringExtra("city"));
                    values.put("cid", data.getStringExtra("cid"));
                    db.insert("city", null, values);
                    values.clear();
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    viewPager.setCurrentItem(i);
                    Toast.makeText(this, "该城市已存在", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == 2) {
                initLocation(requestCode);
                initData();
                viewPager.setCurrentItem(0);

            }
        } else if (requestCode == 2) {
            if (resultCode == 2) {
                navigation.setCheckedItem(R.id.mn_mian);
                initLocation(requestCode);
                initData();
            }
        }
    }



    //打开创建数据库
    private void opendb() {
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this);
        db = dbHelper.getWritableDatabase();
    }


    //初始化定位数据
    private void initLocation(int requestCode) {
        //定位
//        Location location = getLocation();
        Location location = getLocation();
//        Log.i("LocationLocationLocati",(location==null)+"");
         if (location != null) {
            String s = "" + location.getLongitude() + "," + location.getLatitude();
            HttpgetWeather.city(getApplicationContext(), s, new HttpgetWeather.WeatherCityFindListener() {
                @Override
                public void onWeatherCityFind(MyWeatherCityFind myWeatherCityFind) {
                    if (myWeatherCityFind.getHeWeather6().get(0).getStatus().equals("ok")) {
                        map = new HashMap<String, String>();
                        map.put("city", myWeatherCityFind.getHeWeather6().get(0).getBasic().get(0).getLocation());
                        map.put("cid", myWeatherCityFind.getHeWeather6().get(0).getBasic().get(0).getCid());
                        paths.set(0, map);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = myWeatherCityFind.getHeWeather6().get(0).getBasic().get(0).getLocation();
                        handler.handleMessage(message);
//                        Toast.makeText(MainActivity.this, "获取到定位信息", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
             if (requestCode!=2) {
                 Toast.makeText(this, "定位失败，请手动添加城市", Toast.LENGTH_LONG).show();
             }
        }
    }

    //初始化数据
    private void initData() {
        Location location = getLocation();
        fragments.clear();
        paths.clear();
        imageViews.clear();
        int i = 0;

        if (location != null) {
            b = true;
            fragments.add(WeatherFragment.newInstance("" + location.getLongitude() + "," + location.getLatitude()));
            map = new HashMap<>();
            paths.add(map);

            i += 1;
        }

//        //删除
//        db.execSQL("delete from city where id='1'");

        //从数据库读取数据
        // 通过游标遍历1个名为city的表
        Cursor result = db.rawQuery("SELECT * FROM city", null);
        result.moveToFirst();
        if (i == 0 && !result.isAfterLast()) {
            title.setText(result.getString(1));
        }
        while (!result.isAfterLast()) {
            map = new HashMap<>();
//            Log.i("city", result.getString(1));
            map.put("city", result.getString(1));
            map.put("cid", result.getString(2));
            paths.add(map);
            // do something useful with these
            result.moveToNext();
        }
        result.close();
        if (i==0&&paths.size()==0){
            title.setText("请手动添加城市");
        }
        //给Adapter添加fragment
        for (; i < paths.size(); i++) {
            fragments.add(WeatherFragment.newInstance(paths.get(i).get("cid")));
        }
        fragmentAdapter.updateList(fragments);
    }

    private void bianse(int i) {
        for (int j = 0; j < imageViews.size(); j++) {
            if (j != i) {
                if (j == 0 && b) {
                    imageViews.get(j).setImageResource(R.drawable.ic_dingwei_hei);
                } else {
                    imageViews.get(j).setImageResource(R.drawable.ic_yuandian_hei);
                }
            } else {
                if (j == 0 && b) {
                    imageViews.get(j).setImageResource(R.drawable.ic_dingwei_bai);
                } else {
                    imageViews.get(j).setImageResource(R.drawable.ic_yuandian_bai);
                }
            }
        }
    }


    //监听事件方法
    private void myListener() {


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mn_mian:
                        drawerLayou.closeDrawers();
                        break;
                    case R.id.mn_city:
                        Intent intent = new Intent(MainActivity.this, CityManagerActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        //设置显示home键,并且能够点击

        toolbar.setNavigationIcon(R.drawable.ic_toolbar_navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断抽屉有没有打开，如果没有打开，则打开
                if (!drawerLayou.isDrawerOpen(navigation)) {
                    drawerLayou.openDrawer(navigation);
                }
            }
        });

        ibToolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.i("onPageSelected", "" + position + "    " + paths.size());
                title.setText(paths.get(position).get("city"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //判断使用什么定位
    private String judgeProvider(LocationManager locationManager) {
      List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            //网络定位
            return LocationManager.NETWORK_PROVIDER;
        }else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)){
            //网络定位
            return LocationManager.GPS_PROVIDER;
        }else {
            return null;
        }
    }

    //获取位置
    public Location getLocation() {
        //获得位置服务
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String s = judgeProvider(lm);
        //有位置提供器的情况
        if (s != null) {
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return lm.getLastKnownLocation(s);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}


