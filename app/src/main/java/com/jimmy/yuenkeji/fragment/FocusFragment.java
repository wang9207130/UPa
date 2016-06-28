package com.jimmy.yuenkeji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimmy.yuenkeji.upa.R;

/**
 * Created by Administrator on 2016/6/23.
 */
public class FocusFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_focus,container,false);
        return view;
    }
}
