package com.jimmy.yuenkeji.upa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.QavsdkApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**设置界面
 * Created by jimmy on 2016/5/21.
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        ViewUtils.inject(this);

    }

    public void initView(){
    }
    @OnClick({R.id.btn_exit,R.id.rl_conunt_safe})
    public void Click(View view){
        Intent intent;
        switch (view.getId()){
            case  R.id.btn_exit:
                break;
            case  R.id.rl_conunt_safe:
                intent=new Intent(SettingActivity.this,SafeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
