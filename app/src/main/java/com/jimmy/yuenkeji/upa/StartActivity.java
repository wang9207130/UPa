package com.jimmy.yuenkeji.upa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jimmy.yuenkeji.fragment.LiveFragment;
import com.jimmy.yuenkeji.fragment.MyFragment;
import com.jimmy.yuenkeji.fragment.MyLiveFragment;


/**
 * 应用主界面
 */
public class StartActivity extends FragmentActivity {
    private RadioGroup mRadioGroup;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private LiveFragment LiveFrag;
    private MyLiveFragment MyLiveFrag;
    private MyFragment MyFrag;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            savedInstanceState=null;
        }
        setContentView(R.layout.activity_start);
        initView();

    }

    public void initView(){
        mRadioGroup= (RadioGroup) findViewById(R.id.rg_groups);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        LiveFrag = new LiveFragment();
        MyLiveFrag = new MyLiveFragment();
        MyFrag = new MyFragment();
        FrameLayout  frameLayout= (FrameLayout) findViewById(R.id.frameLayouts);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayouts,LiveFrag,"liveFragment")
                .add(R.id.frameLayouts,MyLiveFrag,"myLiveFragment").hide(MyLiveFrag)
                .add(R.id.frameLayouts,MyFrag,"myFragment").hide(MyFrag)
                .show(LiveFrag)
                .commit();
        currentFragment = LiveFrag;


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (checkedId == rb.getId()) {
                        rb.setChecked(true);
                    } else {
                        rb.setChecked(false);
                    }
                    transaction = manager.beginTransaction();
                    switch (checkedId){
                        case R.id.rb_live:
                            if (currentFragment != LiveFrag) {
                                switchContent(currentFragment, LiveFrag);
                            }
                            break;
                        case R.id.rb_mylive:
                            switchContent(currentFragment, MyLiveFrag);
                            break;
                        case R.id.rb_my:
                            switchContent(currentFragment, MyFrag);
                            break;
                        default:
                            break;

                    }

                }


            }
        });
    }

    public void switchContent(Fragment from, Fragment to) {

        currentFragment = to;
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.frameLayouts, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

}
