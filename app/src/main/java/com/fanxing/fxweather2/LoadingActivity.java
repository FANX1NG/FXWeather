package com.fanxing.fxweather2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanxing.fxweather2.getLocation.GetLocation;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {
    @BindView(R.id.btn_tiao)
    Button btnTiao;
    private int recLen = 3;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);


        //将创建的动画应用到控件
        ivLoading.setBackgroundResource(R.drawable.loading_amin);
        //获得动画对象
        AnimationDrawable drawable = (AnimationDrawable)
                ivLoading.getBackground();
        //开启动画
        drawable.start();

        btnTiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                recLen = 0;
                finish();
            }
        });

        //获取权限
        if (!XXPermissions.isHasPermission(this, Permission.Group.LOCATION)||!XXPermissions.isHasPermission(this,Permission.Group.STORAGE)) {
            XXPermissions.with(this)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                handler.postDelayed(runnable, 1000);
                                Toast.makeText(LoadingActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoadingActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                Toast.makeText(LoadingActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_LONG).show();
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(LoadingActivity.this);
                            } else {
                                Toast.makeText(LoadingActivity.this, "获取权限失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            handler.postDelayed(runnable, 1000);
        }


    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            btnTiao.setText("跳过 " + recLen);
            if (recLen == 0) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else if (recLen>0){
                handler.postDelayed(this, 1000);
            }
        }
    };
}
