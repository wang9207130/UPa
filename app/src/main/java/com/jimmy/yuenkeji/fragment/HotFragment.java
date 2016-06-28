package com.jimmy.yuenkeji.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.QavsdkApplication;
import com.jimmy.yuenkeji.adpter.HotAdapter;
import com.jimmy.yuenkeji.bean.LiveInfoVo;
import com.jimmy.yuenkeji.bean.UserInfoVo;
import com.jimmy.yuenkeji.upa.LiveActivity;
import com.jimmy.yuenkeji.upa.R;
import com.jimmy.yuenkeji.utils.GsonUtils;
import com.jimmy.yuenkeji.utils.UrlUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class HotFragment extends Fragment {

    private ListView mListView;
    private View view;
    private List<LiveInfoVo.LiveInfoBean> mList;
    private HotAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_hot,container,false);

        ViewUtils.inject(this,view);
        getData();

        mListView= (ListView) view.findViewById(R.id.HotFragment_listview);

        mAdapter=new HotAdapter(mList,getActivity());


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("RegisterActivity","点击了");
                LiveInfoVo.LiveInfoBean item=mList.get(position);
                //如果是自己
                if (item.getUid().equals(UserInfoVo.DataBean.getInstance().getUid())) {
                    Toast.makeText(getActivity(), "this room don't exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                UserInfoVo.DataBean.getInstance().setStatus(Constants.MEMBER+"");
                CurLiveInfo.setHostID("86-"+item.getUser_phone());
//                CurLiveInfo.setHostName(item.getHost().getUsername());
                CurLiveInfo.setHostAvator(item.getFace());
                CurLiveInfo.setRoomNum(Integer.parseInt(item.getUid()));
//                CurLiveInfo.setMembers(item.getWatchCount() + 1); // 添加自己
//                CurLiveInfo.setAdmires(item.getAdmireCount());
                CurLiveInfo.setAddress(item.getUser_address());
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){
        RequestParams params =new RequestParams();
        params.addBodyParameter("pagenum",1+"");
        QavsdkApplication.httpUtils. send(HttpRequest.HttpMethod.POST, UrlUtils.GET_LIVELIST, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                Log.i("RegisterActivity","---"+result);
                try {
                    JSONObject jb=new JSONObject(result);
                    if (jb.getString("code").equals("0")){
                        Toast.makeText(getActivity(),"暂无数据",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LiveInfoVo liveinfovo= GsonUtils.changeGsonToBean(result,LiveInfoVo.class);

              /*  if (liveinfovo.getCode().equals(0)){
                    Toast.makeText(getActivity(),"暂无数据",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                mList=liveinfovo.getData();
                mAdapter=new HotAdapter(mList,getActivity());
                 mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }


}
