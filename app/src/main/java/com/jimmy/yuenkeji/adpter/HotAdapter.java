package com.jimmy.yuenkeji.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.QavsdkApplication;
import com.jimmy.yuenkeji.bean.LiveInfoVo;
import com.jimmy.yuenkeji.upa.R;
import com.jimmy.yuenkeji.view.RoundImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class HotAdapter extends MyAppAdapter<LiveInfoVo.LiveInfoBean>{

    @ResInject(id = R.mipmap.img_avator, type = ResType.Drawable)
    private Drawable drawable;
    private Context context;

    public HotAdapter(List<LiveInfoVo.LiveInfoBean> list, Context context) {
        super(list, context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_live_hot, parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tv_nickName.setText(mList.get(position).getNickname());
        viewHolder.tv_location.setText(mList.get(position).getUser_address());
        viewHolder.tv_watch_count.setText(mList.get(position).getNumbers());

        viewHolder.imgAvator.setType(RoundImageView.TYPE_ROUND);
        viewHolder.imgAvator.setBorderRadius(60);

        BitmapDisplayConfig config=new BitmapDisplayConfig();
        config.setLoadFailedDrawable(drawable);
        config.setLoadingDrawable(drawable);

        QavsdkApplication.bitmapUtils.display(viewHolder.imgAvator,mList.get(position).getFace(),config);
        QavsdkApplication.bitmapUtils.display(viewHolder.img_bg,mList.get(position).getFace(),config);


        return convertView;
    }

    public static class ViewHolder{

        @ViewInject(R.id.img_avators)
        private RoundImageView imgAvator;

        @ViewInject(R.id.img_bg)
        private ImageView img_bg;

        @ViewInject(R.id.tv_nickName)
        private TextView tv_nickName;

        @ViewInject(R.id.tv_location)
        private TextView tv_location;

        @ViewInject(R.id.tv_watch_count)
        private TextView tv_watch_count;

        @ViewInject(R.id.live_state)
        private Button live_state;



        public ViewHolder(View view){
            ViewUtils.inject(this,view);
        }

    }

}
