package com.jimmy.yuenkeji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jimmy.yuenkeji.adpter.FragmentAdapter;
import com.jimmy.yuenkeji.upa.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class LiveFragment extends Fragment {
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewInject(R.id.rg)
    private RadioGroup mRadioGroup;
    private List<Fragment>mList;
    private FragmentAdapter mAdapter;
    @ViewInject(R.id.rb_focus)
    private RadioButton rbFocus;
    @ViewInject(R.id.rb_hot)
    private RadioButton rbHot;
    @ViewInject(R.id.rb_new)
    private RadioButton rbnew;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livefragment, container, false);
        ViewUtils.inject(this,view);

        mList=new ArrayList<Fragment>();
        mList.add(new FocusFragment());
        mList.add(new HotFragment());
        mList.add(new NewFragment());

        mAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbFocus.setChecked(true);
                        break;
                    case 1:
                        rbHot.setChecked(true);
                        break;
                    case 2:
                        rbnew.setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<group.getChildCount();i++){
                    if (group.getChildAt(i).getId()==checkedId){
                        mViewPager.setCurrentItem(i);
                    }
                }
            }
        });

        return view;
    }
}
