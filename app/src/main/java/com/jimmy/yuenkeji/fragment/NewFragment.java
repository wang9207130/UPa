package com.jimmy.yuenkeji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.QavsdkApplication;
import com.jimmy.yuenkeji.adpter.IdeaAdaptr;
import com.jimmy.yuenkeji.bean.IdeaBean;
import com.jimmy.yuenkeji.bean.IdeaInfo;
import com.jimmy.yuenkeji.upa.R;
import com.jimmy.yuenkeji.utils.GsonUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23.
 */
public class NewFragment extends Fragment {
    private ListView mListView;

    private View view;
    private ArrayList<IdeaInfo> mList;
    private IdeaAdaptr mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_new,container,false);
        mListView= (ListView) view.findViewById(R.id.lv);
        mList=new ArrayList<IdeaInfo>();
        ShowIdeaData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"去你大爷",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    /**
     * 进入首页初始化数据
     */
    public void ShowIdeaData(){
        String url = "http://139.196.148.69/index.php?s=/home/file/watch_creative_all/page/1/cystyle/0/cytype/0";
        Log.i("====ideaFragment=", url);
        QavsdkApplication.httpUtils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                            Log.i("==创意主页信息获取失败==",
                                    "message:" + arg0.getMessage());
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                            Log.i("==创意主页息获取成功==", "message:" + arg0.result);

                        String resultInfos = arg0.result;
                        if (TextUtils.isEmpty(resultInfos)) {
                            Toast.makeText(getActivity(), "获取信息失败",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
						 IdeaBean ideaBean= GsonUtils.changeGsonToBean(resultInfos, IdeaBean.class);
						mList=ideaBean.getInfo();
						mAdapter=new IdeaAdaptr(mList, getActivity());
						mListView.setAdapter(mAdapter);
                    }

                });




    }
}
