package com.fanxing.fxweather2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanxing.fxweather2.adapter.CityFindLVAdapter;
import com.fanxing.fxweather2.entity.MyWeatherCityFind;
import com.fanxing.fxweather2.entity.MyWeatherTop;
import com.fanxing.fxweather2.manager.HttpgetWeather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCityActivity extends AppCompatActivity {

    @BindView(R.id.ib_toolbar_out)
    ImageButton ibToolbarOut;
    @BindView(R.id.ib_toolbar_location)
    ImageButton ibToolbarLocation;
    @BindView(R.id.add_city_toolbar)
    Toolbar addCityToolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.lv_city)
    ListView lvCity;
    @BindView(R.id.tv_lvTitle)
    TextView tvLvTitle;
    private ArrayList<Map<String, String>> topCityMaps = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ButterKnife.bind(this);
        addCityToolbar.setTitle("");
        setSupportActionBar(addCityToolbar);

        //设置文字变得监听
        etSearch.addTextChangedListener(new TextChangeWatcher());
        //加载热门城市
        topCity();
        ibToolbarOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置定位监听
        ibToolbarLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(2,intent);
                finish();
            }
        });
        //设置listView监听
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("city", ((TextView)(((RelativeLayout) view).findViewById(R.id.tv_location))).getText().toString());
                intent.putExtra("cid",((TextView)(((RelativeLayout)view).findViewById(R.id.tv_location))).getTag().toString());
                setResult(1,intent);
                finish();
            }
        });
        lvCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //让EditText失去焦点
                etSearch.clearFocus();
                hideKeyboard(getCurrentFocus().getWindowToken());
                return false;
            }
        });


    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //搜索框文字变动监听
    private class TextChangeWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals("")) {
                find(s.toString());
            } else {
                topCity();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    }

    //获取热门城市
    public void topCity() {
        if (topCityMaps == null) {
            HttpgetWeather.top(AddCityActivity.this, new HttpgetWeather.WeatherTopListener() {
                @Override
                public void onWeatherTop(MyWeatherTop myWeatherTop) {

                    topCityMaps = new ArrayList<>();
                    if (myWeatherTop.getHeWeather6().get(0).getStatus().equals("ok")) {
                        for (MyWeatherTop.HeWeather6Bean.BasicBean basic : myWeatherTop.getHeWeather6().get(0).getBasic()
                                ) {
                            Map<String, String> map = new HashMap<>();
                            map.put("Location", basic.getLocation());
                            map.put("ParentCity", basic.getParent_city());
                            map.put("AdminArea", basic.getAdmin_area());
                            map.put("cid",basic.getCid());
                            topCityMaps.add(map);
                        }
                        lvCity.setAdapter(new CityFindLVAdapter(topCityMaps, getApplicationContext()));
                        tvLvTitle.setText("热门城市");
                    }


                }
            });
        } else {
            lvCity.setAdapter(new CityFindLVAdapter(topCityMaps, getApplicationContext()));
            tvLvTitle.setText("热门城市");
        }
    }

    //搜索城市
    public void find(String s) {
        HttpgetWeather.city(AddCityActivity.this, s.toString(), new HttpgetWeather.WeatherCityFindListener() {
            @Override
            public void onWeatherCityFind(MyWeatherCityFind myWeatherCityFind) {
                ArrayList<Map<String, String>> maps = new ArrayList<>();

                if (myWeatherCityFind.getHeWeather6().get(0).getStatus().equals("ok")) {
                    for (MyWeatherCityFind.HeWeather6Bean.BasicBean basic : myWeatherCityFind.getHeWeather6().get(0).getBasic()
                            ) {
                        Map<String, String> map = new HashMap<>();
                        map.put("Location", basic.getLocation());
                        map.put("ParentCity", basic.getParent_city());
                        map.put("AdminArea", basic.getAdmin_area());
                        map.put("cid",basic.getCid());
                        maps.add(map);
                    }
                    lvCity.setAdapter(new CityFindLVAdapter(maps, getApplicationContext()));
                    tvLvTitle.setText("搜索结果");
                } else {
                    lvCity.setAdapter(new CityFindLVAdapter(maps, getApplicationContext()));
                    tvLvTitle.setText("搜索结果");
                }

            }
        });
    }
}
