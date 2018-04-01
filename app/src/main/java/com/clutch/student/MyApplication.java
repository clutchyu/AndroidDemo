package com.clutch.student;
import android.app.Application;

import com.facebook.stetho.Stetho;
/**
 * Created by clutchyu on 2018/3/17.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
    }
    public static MyApplication getInstance(){
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        return instance;
    }
}
