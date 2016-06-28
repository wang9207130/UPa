package com.jimmy.yuenkeji.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimmy.yuenkeji.upa.ContributeActivity;
import com.jimmy.yuenkeji.upa.EditorActivity;
import com.jimmy.yuenkeji.upa.IncomeActivity;
import com.jimmy.yuenkeji.upa.LevelActivity;
import com.jimmy.yuenkeji.upa.PayActivity;
import com.jimmy.yuenkeji.upa.R;
import com.jimmy.yuenkeji.upa.SettingActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 个人中心界面
 * Created by jimmy on 2016/5/20.
 */
public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment, container, false);
        ViewUtils.inject(this, view);

        return view;
    }

    @OnClick({R.id.img_editor, R.id.rl_settings, R.id.rl_contribution, R.id.rl_incomes, R.id.rl_levels,R.id.rl_money})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.img_editor:
                Intent intent1 = new Intent(getActivity(), EditorActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_settings:
                Intent intent2 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_contribution:
                Intent intent3 = new Intent(getActivity(), ContributeActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_incomes:
                Intent intent4 = new Intent(getActivity(), IncomeActivity.class);
                startActivity(intent4);
                break;
            case R.id.rl_levels:
                Intent intent5 = new Intent(getActivity(), LevelActivity.class);
                startActivity(intent5);
                break;
            case R.id.rl_money:
                Intent intent6 = new Intent(getActivity(), PayActivity.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }


}
