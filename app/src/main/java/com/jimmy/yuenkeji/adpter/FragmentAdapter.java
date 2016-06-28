package com.jimmy.yuenkeji.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment>mlist;

    public FragmentAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.mlist=list;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
