package com.fanxing.fxweather2.getPermissions;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.fanxing.fxweather2.MainActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * Created by 繁星 on 2018/12/16.
 */
//获取权限
public class GP {
    public static void pms(final Context context, Activity aClass) {
        if(!XXPermissions.isHasPermission(context, Permission.Group.LOCATION)) {
            Toast.makeText(context, "ssss", Toast.LENGTH_SHORT).show();
            XXPermissions.with(aClass)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll){

                                Toast.makeText(context, "获取权限成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "获取权限成功，部分权限未正常授予", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if(quick) {
                                Toast.makeText(context, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_LONG).show();
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(context);
                            }else {
                                Toast.makeText(context, "获取权限失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }



}
