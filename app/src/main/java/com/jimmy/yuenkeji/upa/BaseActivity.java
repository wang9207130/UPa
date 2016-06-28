package com.jimmy.yuenkeji.upa;

import android.app.Activity;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

/**基类
 * Created by Administrator on 2016/5/17.
 */
public abstract class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        setView();
        initData();;
        initView();
    }

    /**
     * 初始化布局
     */
    public abstract  void setView();

    /**
     * 初始化控件
     */
    public abstract  void initView();

    /**
     * 初始化数据
     */
    protected   void  initData(){

    };
}
