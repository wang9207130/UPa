package com.jimmy.yuenkeji.upa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**账号与安全
 * Created by Administrator on 2016/5/24.
 */
public class SafeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        ViewUtils.inject(this);
    }
    @OnClick(R.id.rl_bind)
    public void Click(View view){
        switch (view.getId()){
            case R.id.rl_bind:
                Intent intent=new Intent(SafeActivity.this,BindPhoneActivity.class);
                startActivity(intent);
                break;

        }
    }
}
