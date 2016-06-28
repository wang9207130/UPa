package com.jimmy.yuenkeji.adpter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyAppAdapter<T> extends BaseAdapter {
	protected List<T> mList;
	protected Context mContext;
    public MyAppAdapter(List<T>list,Context context){
		// TODO Auto-generated constructor stub
    	mList=list;
    	mContext=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
}
