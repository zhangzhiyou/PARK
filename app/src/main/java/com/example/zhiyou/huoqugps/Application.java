package com.example.zhiyou.huoqugps;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by zhiyou on 14-9-20.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
