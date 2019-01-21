package com.fanxing.fxweather2;

import android.app.Application;
import android.content.Context;

/**
 * Created by 繁星 on 2018/12/30.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    /**
     * 获取context
     * @return
     */
    public static Context getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

}
