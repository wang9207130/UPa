package com.jimmy.yuenkeji.upa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**我的收益界面
 * Created by jimmy on 2016/5/24.
 */
public class IncomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        ViewUtils.inject(this);
    }
    @OnClick({R.id.btn_get,R.id.rl_record})
    public void Click(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_get:
                 intent=new Intent(IncomeActivity.this,GetMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_record:
                 intent=new Intent(IncomeActivity.this,RecordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
