package com.jimmy.yuenkeji.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jimmy.yuenkeji.bean.UserInfoVo;
import com.jimmy.yuenkeji.upa.LiveActivity;
import com.jimmy.yuenkeji.upa.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.utils.Constants;

/**直播界面
 * Created by jimmy on 2016/5/20.
 */
public class MyLiveFragment extends Fragment{
    @ViewInject(R.id.edt_title)
    private EditText edtTitle;
    @ViewInject(R.id.tv_add_topic)
    private TextView tvTopic;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mylivefragment, container, false);
        ViewUtils.inject(this,view);
        return view;
    }

    @OnClick(R.id.start_live)
    public void Click(View view){
        switch (view.getId()){
            case R.id.start_live:
                Intent intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra(Constants.ID_STATUS,Constants.HOST);
                UserInfoVo.DataBean.getInstance().setStatus(Constants.HOST+"");

                CurLiveInfo.setTitle(edtTitle.getText().toString());
                CurLiveInfo.setTopic(tvTopic.getText().toString());
                CurLiveInfo.setHostID(UserInfoVo.DataBean.getInstance().getUid());
                CurLiveInfo.setRoomNum(Integer.parseInt(UserInfoVo.DataBean.getInstance().getUid()));
                startActivity(intent);
                break;


        }
    }




}
