package com.fanxing.fxweather2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.fanxing.fxweather2.SQLite.MySQLiteOpenHelper;
import com.fanxing.fxweather2.adapter.CityManagerAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityManagerActivity extends AppCompatActivity {
    @BindView(R.id.ib_toolbar_out)
    ImageButton ibToolbarOut;
    @BindView(R.id.lv_city_manager)
    ListView lvCityManager;
    private List<Map<String, String>> paths = new ArrayList<>();
    private SQLiteDatabase db;
    private HashMap<String, String> map;
    private CityManagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        ButterKnife.bind(this);
        opendb();
        adapter = new CityManagerAdapter(this);
        lvCityManager.setAdapter(adapter);

        //初始化数据
        intiData();
        //删除数据监听
        lvCityManager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,final View view,final int i, long l) {
                new AlertDialog.Builder(CityManagerActivity.this).setTitle("城市删除提示").setMessage("确认删除"+((TextView)view.findViewById(R.id.tv_cityt)).getText().toString()+"?")

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete("city","cid=?", new String[]{((TextView)view.findViewById(R.id.tv_city_cid)).getText().toString()});
                                paths.remove(i);
                                adapter.updateList(paths);
//                Toast.makeText(CityManagerActivity.this, ""+i+"     "+view.toString(), Toast.LENGTH_SHORT).show();


                            }})
                        .setNegativeButton("取消",null)
                        .show();
                return false;
            }
        });
//        lvCityManager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                db.delete("city","cid=?", new String[]{((TextView)view.findViewById(R.id.tv_city_cid)).getText().toString()});
//                paths.remove(i);
//                adapter.updateList(paths);
////                Toast.makeText(CityManagerActivity.this, ""+i+"     "+view.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
        delData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    //回退键监听
    @Override
    public void onBackPressed() {
        setResult(2);
        finish();
        super.onBackPressed();

    }
    private void delData() {


        ibToolbarOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(2);
                finish();
            }
        });
    }

    private void intiData() {
        // 通过游标遍历1个名为city的表
        Cursor result = db.rawQuery("SELECT * FROM city", null);
        result.moveToFirst();

        while (!result.isAfterLast()) {
            map = new HashMap<>();
//            Log.i("city", result.getString(1));
//            map.put("id", result.getInt(0) + "");
            map.put("city", result.getString(1));

            map.put("cid", result.getString(2));
            paths.add(map);
            // do something useful with these
            result.moveToNext();
        }
        result.close();
        adapter.updateList(paths);

    }


    //打开创建数据库
    private void opendb() {
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this);
        db = dbHelper.getWritableDatabase();
    }
}
