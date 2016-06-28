package com.jimmy.yuenkeji.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.QavsdkApplication;
import com.jimmy.yuenkeji.bean.IdeaInfo;
import com.jimmy.yuenkeji.upa.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class IdeaAdaptr extends MyAppAdapter<IdeaInfo> {
	
	@ResInject(id= R.mipmap.pictures_no,type=ResType.Drawable)
	private Drawable drawable;

	public IdeaAdaptr(List<IdeaInfo> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
 		 convertView=LayoutInflater.from(mContext).inflate(R.layout.item_idea, parent,false);
 		 viewHolder=new ViewHolder(convertView);
 		 convertView.setTag(viewHolder);
 		 
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}

		
		BitmapDisplayConfig config2=new BitmapDisplayConfig();
		config2.setLoadFailedDrawable(drawable);
		config2.setLoadingDrawable(drawable);
//		ScreenUtils.setRelativeView(viewHolder.imageView, (float) 0.75, mContext);
		QavsdkApplication.bitmapUtils.display(viewHolder.imageView, mList.get(position).getHeadimgs(), config2);
		
		return convertView;
	}
	
	
	public static class ViewHolder{
		
		@ViewInject(R.id.img_bg)
		private ImageView imageView;
		

		public ViewHolder(View view){
			ViewUtils.inject(this,view);
		}

	}

}
