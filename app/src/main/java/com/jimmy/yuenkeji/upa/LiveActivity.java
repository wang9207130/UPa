package com.jimmy.yuenkeji.upa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jimmy.yuenkeji.bean.UserInfoVo;
import com.tencent.av.TIMAvManager;
import com.tencent.av.sdk.AVView;
import com.tencent.qcloud.suixinbo.avcontrollers.QavsdkControl;
import com.tencent.qcloud.suixinbo.helper.EnterLiveHelper;
import com.tencent.qcloud.suixinbo.helper.LiveHelper;
import com.tencent.qcloud.suixinbo.model.ChatEntity;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;
import com.tencent.qcloud.suixinbo.utils.Constants;
import com.tencent.qcloud.suixinbo.utils.SxbLog;
import com.tencent.qcloud.suixinbo.viewinface.EnterQuiteRoomView;
import com.tencent.qcloud.suixinbo.viewinface.LiveView;

import java.util.ArrayList;

/**直播界面
 * Created by Administrator on 2016/5/18.
 */
public class LiveActivity extends AppCompatActivity implements EnterQuiteRoomView,LiveView {

    private EnterLiveHelper mEnterRoomHelper;
    private LiveHelper mLiveHelper;
    private String TAG = "LiveActivity";
    private View avView;
    private Dialog mDetailDialog;
    private AlertDialog backDialog;
    private ArrayList<ChatEntity> mTmpChatList = new ArrayList<ChatEntity>();//缓冲队列


    /*   private Timer mHearBeatTimer, mVideoTimer;
       private VideoTimerTask mVideoTimerTask;//计时器
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_live);
        initView();
        registerReceiver();


        //进出房间的协助类
        mEnterRoomHelper = new EnterLiveHelper(this, this);
        //房间内的交互协助类
        mLiveHelper = new LiveHelper(this, this);

        mEnterRoomHelper.startEnterRoom();
        mLiveHelper.setCameraPreviewChangeCallback();


    }


    private void initView() {
        avView = findViewById(R.id.av_video_layer_ui);//surfaceView;
//        initBackDialog();
        initDetailDailog();
        Log.i("RegisterActivity","观众进来initView");
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //AvSurfaceView 初始化成功
            if (action.equals(Constants.ACTION_SURFACE_CREATED)) {
                Log.i("RegisterActivity","观众进来ACTION_SURFACE_CREATED");
                //打开摄像头
                if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.HOST + "")) {
                    Log.i("RegisterActivity", "--7--" + UserInfoVo.DataBean.getInstance().getStatus());
                    mLiveHelper.openCameraAndMic();
                }

            }

            if (action.equals(Constants.ACTION_CAMERA_OPEN_IN_LIVE)) {//有人打开摄像头
                Log.i("RegisterActivity","观众进来ACTION_CAMERA_OPEN_IN_LIVE");
                ArrayList<String> ids = intent.getStringArrayListExtra("ids");
                //如果是自己本地直接渲染
                for (String id : ids) {
                    Log.i("RegisterActivity", "--9--" + id + "--00--" + UserInfoVo.DataBean.getInstance().getUser_phone());
                    if (id.equals(UserInfoVo.DataBean.getInstance().getUser_phone())) {

                        showVideoView(true, id);
                        return;
//                        ids.remove(id);
                    }
                }
                //其他人一并获取
                int requestCount = CurLiveInfo.getCurrentRequestCount();
                mLiveHelper.requestViewList(ids);
                requestCount = requestCount + ids.size();
                CurLiveInfo.setCurrentRequestCount(requestCount);
//                }
            }

  /*          if (action.equals(Constants.ACTION_SWITCH_VIDEO)) {//点击成员回调
                backGroundId = intent.getStringExtra(Constants.EXTRA_IDENTIFIER);

                if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.HOST+"")) {//自己是主播
                    if (backGroundId.equals(UserInfoVo.DataBean.getInstance().getUid())) {//背景是自己
                        mHostCtrView.setVisibility(View.VISIBLE);
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                    } else {//背景是其他成员
                        mHostCtrView.setVisibility(View.INVISIBLE);
                        mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                    }
                } else {//自己成员方式
                    if (backGroundId.equals(UserInfoVo.DataBean.getInstance().getUid())) {//背景是自己
                        mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                        mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    } else if (backGroundId.equals(CurLiveInfo.getHostID())) {//主播自己
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    } else {
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    }

                }

            }*/
            if (action.equals(Constants.ACTION_HOST_LEAVE)) {//主播结束
                quiteLivePassively();
            }


        }
    };


    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SURFACE_CREATED);
        intentFilter.addAction(Constants.ACTION_HOST_ENTER);
        intentFilter.addAction(Constants.ACTION_CAMERA_OPEN_IN_LIVE);
        intentFilter.addAction(Constants.ACTION_SWITCH_VIDEO);
        intentFilter.addAction(Constants.ACTION_HOST_LEAVE);
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    @Override
    public void enterRoomComplete(int id_status, boolean succ) {
        Log.i("RegisterActivity","观众进来enterRoomComplete");
        Toast.makeText(LiveActivity.this, "EnterRoom  " + id_status + " isSucc " + succ, Toast.LENGTH_SHORT).show();
        //必须得进入房间之后才能初始化UI
        mEnterRoomHelper.initAvUILayer(avView);

        //设置预览回调，修正摄像头镜像
        mLiveHelper.setCameraPreviewChangeCallback();
        if (succ == true) {
            //IM初始化
            mLiveHelper.initTIMListener("" + CurLiveInfo.getRoomNum());

            if (id_status == Constants.HOST) {//主播方式加入房间成功
                //开启摄像头渲染画面
                SxbLog.i(TAG, "createlive enterRoomComplete isSucc" + succ);
            } else {
                //发消息通知上线
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_EnterLive, "");
            }
        }
    }

    @Override
    public void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo) {
        Log.i("RegisterActivity","观众进来quiteRoomComplete");
        if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.HOST+"") ) {
            if ((getBaseContext() != null) && (null != mDetailDialog) && (mDetailDialog.isShowing() == false)) {
                mDetailTime.setText("00");
                mDetailAdmires.setText("" + CurLiveInfo.getAdmires());
                mDetailWatchCount.setText("" + 00);
                mDetailDialog.show();
            }
        } else {
            finish();
        }

    }

    @Override
    public void memberQuiteLive(String[] list) {
        Log.i("RegisterActivity","观众进来memberQuiteLive");
        if (list == null) return;
        for (String id : list) {
            Log.i("RegisterActivity","观众进来memberQuiteLive"+ id);
            SxbLog.i(TAG, "memberQuiteLive id " + id);
            if (CurLiveInfo.getHostID().equals(id)) {
                if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.MEMBER+""))
                    quiteLivePassively();
            }
        }

    }

    @Override
    public void memberJoinLive(String[] list) {
        Log.i("RegisterActivity","观众进来memberJoinLive");

    }

    @Override
    public void alreadyInLive(String[] list) {
        Log.i("RegisterActivity","观众进来alreadyInLive");
        for (String id : list) {
            if (id.equals(UserInfoVo.DataBean.getInstance().getUid())) {
                QavsdkControl.getInstance().setSelfId(UserInfoVo.DataBean.getInstance().getUid());
                QavsdkControl.getInstance().setLocalHasVideo(true, UserInfoVo.DataBean.getInstance().getUid());
            } else {
                QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
            }
        }

    }

    /**
     * 加载视频数据
     * 是否是本地数据
     *
     * @param
     * @param id 身份
     */
    @Override
    public void showVideoView(boolean isLocal, String id) {
        Log.i("RegisterActivity","观众进来showVideoView");
        //渲染本地Camera
        if (isLocal == true) {
            SxbLog.i(TAG, "showVideoView host :" + UserInfoVo.DataBean.getInstance().getUser_phone());
            QavsdkControl.getInstance().setSelfId(UserInfoVo.DataBean.getInstance().getUser_phone());
            QavsdkControl.getInstance().setLocalHasVideo(true, UserInfoVo.DataBean.getInstance().getUser_phone());
            //主播通知用户服务器
            if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.HOST + "")) {
                mEnterRoomHelper.notifyServerCreateRoom();

              /*  //主播心跳
                mHearBeatTimer = new Timer(true);
                mHeartBeatTask = new HeartBeatTask();
                mHearBeatTimer.schedule(mHeartBeatTask, 1000, 3 * 1000);

                //直播时间
                mVideoTimer = new Timer(true);
                mVideoTimerTask = new VideoTimerTask();
                mVideoTimer.schedule(mVideoTimerTask, 1000, 1000);*/
            }
        } else {
//            QavsdkControl.getInstance().addRemoteVideoMembers(id);
            QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
        }

    }

    @Override
    public void showInviteDialog() {
        Log.i("RegisterActivity","观众进来showInviteDialog");

    }

    @Override
    public void refreshText(String text, String name) {
        Log.i("RegisterActivity","观众进来refreshText");
      if (text != null) {
            refreshTextListView(name, text, Constants.TEXT_TYPE);
        }
    }

    @Override
    public void refreshThumbUp() {
        Log.i("RegisterActivity","观众进来refreshThumbUp");

    }

    @Override
    public void refreshUI(String id) {
        Log.i("RegisterActivity","观众进来refreshUI");

    }

    @Override
    public boolean showInviteView(String id) {
        return false;
    }

    @Override
    public void cancelInviteView(String id) {

    }

    @Override
    public void cancelMemberView(String id) {
        Log.i("RegisterActivity","观众进来cancelMemberView");
        if (UserInfoVo.DataBean.getInstance().getUid().equals(Constants.HOST+"") ) {
        } else {
            //TODO 主动下麦 下麦；
            mLiveHelper.changeAuthandRole(false, Constants.NORMAL_MEMBER_AUTH, Constants.NORMAL_MEMBER_ROLE);
//            mLiveHelper.closeCameraAndMic();//是自己成员关闭
        }
        mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, id);
        QavsdkControl.getInstance().closeMemberView(id);
//        backToNormalCtrlView();
    }

    @Override
    public void memberJoin(String id, String name) {
       /* Log.i("RegisterActivity","9有观众进来了");
        watchCount++;
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "join live", Constants.MEMBER_ENTER);

        CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);
        tvMembers.setText("" + CurLiveInfo.getMembers());*/
    }

    @Override
    public void memberQuit(String id, String name) {
        Log.i("RegisterActivity","观众进来memberQuit");
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "quite live", Constants.MEMBER_EXIT);

        if (CurLiveInfo.getMembers() > 1) {
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1);
//            tvMembers.setText("" + CurLiveInfo.getMembers());
        }
    }
    /**
     * 被动退出直播
     */
    private void quiteLivePassively() {
        Log.i("RegisterActivity","观众进来quiteLivePassively");
        Toast.makeText(this, "Host leave Live ", Toast.LENGTH_SHORT);
        mLiveHelper.perpareQuitRoom(false);
//        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void readyToQuit() {
        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void hideInviteDialog() {

    }

    @Override
    public void pushStreamSucc(TIMAvManager.StreamRes streamRes) {

    }

    @Override
    public void stopStreamSucc() {

    }

    /**
     * 按会推荐退出直播
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        quiteLiveByPurpose();
        return super.onKeyDown(keyCode, event);
    }



    /**
     * 主动退出直播
     */
    private void quiteLiveByPurpose() {
        Log.i("RegisterActivity","观众进来quiteLiveByPurpose");
        if (UserInfoVo.DataBean.getInstance().getStatus().equals(Constants.HOST + "")) {
//            if (backDialog.isShowing() == false)
                initBackDialog();


        } else {
            mLiveHelper.perpareQuitRoom(true);
//            mEnterRoomHelper.quiteLive();
        }
    }
    private TextView mDetailTime, mDetailAdmires, mDetailWatchCount;
    private void initDetailDailog() {
        Log.i("RegisterActivity","观众进来initDetailDailog");
        mDetailDialog = new Dialog(this, R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_live_detail);
        mDetailTime = (TextView) mDetailDialog.findViewById(R.id.tv_time);
        mDetailAdmires = (TextView) mDetailDialog.findViewById(R.id.tv_admires);
        mDetailWatchCount = (TextView) mDetailDialog.findViewById(R.id.tv_members);

        mDetailDialog.setCancelable(false);

        TextView tvCancel = (TextView) mDetailDialog.findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
                finish();
            }
        });
//        mDetailDialog.show();
    }

    public void initBackDialog() {
        Log.i("RegisterActivity","观众进来initBackDialog");
        backDialog = new AlertDialog.Builder(this).create();
        backDialog.show();
        Window window = backDialog.getWindow();
        window.setContentView(R.layout.chose_exit_dialog);
        // 为确认按钮添加事件,执行退出应用操作
        Button ok = (Button) window.findViewById(R.id.out_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //如果是直播，发消息
                if (null != mLiveHelper) {
                    mLiveHelper.perpareQuitRoom(true);
                   /* if (isPushed) {
                        mLiveHelper.stopPushAction();
                    }*/
                }
                backDialog.dismiss();
            }
        });

        // 关闭alert对话框架
        Button cancel = (Button) window.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backDialog.dismiss();
            }
        });


    }

    /**
     * 消息刷新显示
     *
     * @param name    发送者
     * @param context 内容
     * @param type    类型 （上线线消息和 聊天消息）
     */
    public void refreshTextListView(String name, String context, int type) {
        Log.i("RegisterActivity","观众进来refreshTextListView");
        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        entity.setType(type);
        //mArrayListChatEntity.add(entity);
        notifyRefreshListView(entity);
        //mChatMsgListAdapter.notifyDataSetChanged();

      /*  mListViewMsgItems.setVisibility(View.VISIBLE);
        SxbLog.d(TAG, "refreshTextListView height " + mListViewMsgItems.getHeight());

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }*/
    }

    /**
     * 通知刷新消息ListView
     */
    private void notifyRefreshListView(ChatEntity entity) {
        Log.i("RegisterActivity","观众进来notifyRefreshListView");
//        mBoolNeedRefresh = true;
        mTmpChatList.add(entity);
      /*  if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }*/
    }
}
