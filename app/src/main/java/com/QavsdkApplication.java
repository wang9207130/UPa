package com;

import android.app.Application;
import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.tencent.qcloud.suixinbo.helper.InitBusinessHelper;
import com.tencent.qcloud.suixinbo.utils.SxbLogImpl;


/**
 * 全局Application
 */
public class QavsdkApplication extends Application {

    private static QavsdkApplication app;
    private static Context context;
    //xutils

    public static HttpUtils httpUtils;
    public static BitmapUtils bitmapUtils;


    @Override
    public void onCreate() {
        super.onCreate();

        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(0*1000);
        httpUtils.configDefaultHttpCacheExpiry(0);
        httpUtils.configRequestThreadPoolSize(10);
        httpUtils.configResponseTextCharset("UTF-8");
        bitmapUtils = new BitmapUtils(this);



        app = this;
        context = getApplicationContext();

        SxbLogImpl.init(getApplicationContext());

        //初始化APP
        InitBusinessHelper.initApp(context);


//        LeakCanary.install(this);

        //创建AVSDK 控制器类
    }

    public static Context getContext() {

        return context;
    }

    public static QavsdkApplication getInstance() {
        return app;
    }
}
