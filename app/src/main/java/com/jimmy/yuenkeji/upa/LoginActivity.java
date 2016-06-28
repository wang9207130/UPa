package com.jimmy.yuenkeji.upa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.QavsdkApplication;
import com.jimmy.yuenkeji.bean.UserInfoVo;
import com.jimmy.yuenkeji.utils.GsonUtils;
import com.jimmy.yuenkeji.utils.PrefUtils;
import com.jimmy.yuenkeji.utils.StringUtil;
import com.jimmy.yuenkeji.utils.UrlUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.qcloud.suixinbo.helper.LoginHelper;
import com.tencent.qcloud.suixinbo.utils.SxbLog;
import com.tencent.qcloud.suixinbo.viewinface.LoginView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 登录界面
 */
public class LoginActivity extends Activity implements LoginView{
    private static final String TAG = "LoginActivity";
    LoginHelper mLoginHeloper;
    @ViewInject(R.id.login_account)
    private EditText edtName;
    @ViewInject(R.id.login_smssig)
    private EditText edtCode;
    @ViewInject(R.id.smslogin)
    private Button btnCode;
    @ViewInject(R.id.btn_login)
    private Button btnRegister;
    private Timer timer;
    private int yanzhenmakeyongmiao = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserInfoVo.DataBean.getInstance().getCache(getApplicationContext());

        timer = new Timer();

        ViewUtils.inject(this);
        mLoginHeloper = new LoginHelper(this, this);
        if (needLogin() == true) {//本地没有账户需要登录

        } else {
            //有账户登录直接IM登录
            SxbLog.i(TAG, "LoginActivity onCreate");
            mLoginHeloper.imLogin("86-"+UserInfoVo.DataBean.getInstance().getUser_phone(), UserInfoVo.DataBean.getInstance().getUser_sig());
        }
    }

    @Override
    protected void onDestroy() {
        if (null != timer) {
            timer.cancel();
        }
        mLoginHeloper.onDestory();
        super.onDestroy();
    }

    @OnClick({R.id.btn_reg,R.id.smslogin,R.id.btn_login})
    public void Click(View view){
        switch (view.getId()){
            case R.id.btn_reg:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.smslogin:
                GetYZM();
                break;
            case R.id.btn_login:
                login();
                break;
            default:
                break;

        }
    }

    /**
     * 获取登录验证码
     */
    private void GetYZM() {
        String user_phone = edtName.getText().toString();
        if (TextUtils.isEmpty(user_phone)) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtil.isMobileNO(user_phone)) {
            Toast.makeText(LoginActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        btnCode.setEnabled(false);
        btnRegister.setEnabled(true);
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new FulshTime(), 0, 1000);
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_phone", user_phone);
        params.addBodyParameter("type", 1 + "");
        Log.i(TAG, "===url===" + UrlUtils.REGISTER_URL_CODE + "user_phone" + user_phone);

        QavsdkApplication.httpUtils.send(HttpRequest.HttpMethod.POST, UrlUtils.REGISTER_URL_CODE, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String resultInfo = responseInfo.result;
                Log.i(TAG, "===" + resultInfo);
           /*     try {
                    JSONObject json = new JSONObject(resultInfo);
                    String code = json.getString("code");
                    if (code.equals("1")) {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "==TAG==获取验证码失败");
            }
        });


    }

    /**
     * 用户登录
     */
    private void login() {

        {

            String identifier = edtName.getText().toString();
            String verify = edtCode.getText().toString();
            if (TextUtils.isEmpty(identifier)) {
                Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(verify)) {
                Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestParams params = new RequestParams();
            params.addBodyParameter("identifier", "86-" + identifier);
            params.addBodyParameter("verify", verify);
            params.addBodyParameter("type", 1 + "");
            Log.i(TAG, "86-" + identifier + "--lolo--" + "verify" + verify + "--lplp-" + UrlUtils.LOGIN_URL);
            QavsdkApplication.httpUtils.send(HttpRequest.HttpMethod.POST, UrlUtils.LOGIN_URL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String resultInfo = responseInfo.result;
                    Log.i(TAG, " 登陆成功===" + resultInfo);
                    UserInfoVo userInfoVo = GsonUtils.changeGsonToBean(responseInfo.result, UserInfoVo.class);

                    if (userInfoVo.getCode().equals("1")) {
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

//                        UserInfoVo.DataBean.getInstance().setUser_phone(identifier);
//                        UserInfoVo.DataBean.getInstance().setUser_sig(userSig);
//                        UserInfoVo.DataBean.getInstance().setUid(userInfoVo.getData().getUid());


                        PrefUtils.setString(getApplication(),"usersing", userInfoVo.getData().getUser_sig());
                        PrefUtils.setString(getApplication(),"identifier",userInfoVo.getData().getUser_phone());
                        PrefUtils.setString(getApplication(),"avator", userInfoVo.getData().getFace());
                        PrefUtils.setString(getApplication(),"upaId", userInfoVo.getData().getUid());
                        PrefUtils.setString(getApplication(),"gender", userInfoVo.getData().getUser_gender());
                        PrefUtils.setString(getApplication(),"signature", userInfoVo.getData().getUser_signature());
                        PrefUtils.setString(getApplication(),"sig", userInfoVo.getData().getUser_sig());
                        PrefUtils.setString(getApplication(),"emotion", userInfoVo.getData().getEmotion_state());
                        PrefUtils.setString(getApplication(),"age", userInfoVo.getData().getUser_age());
                        PrefUtils.setString(getApplication(),"nickname", userInfoVo.getData().getNickname());

                        String userSig = userInfoVo.getData().getUser_sig();
                        String identifier = "86-"+ userInfoVo.getData().getUser_phone();
                        Log.i(TAG,"--用户签名--"+userSig+"-用户身份—"+identifier);
                        mLoginHeloper.imLogin(identifier, userSig);


                    }


                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(TAG, "==TAG==登陆失败");
                }
            });


        }

    }

    @Override
    public void loginSucc() {

        jumpIntoStartActivity();
    }

    @Override
    public void loginFail() {

    }

    /**
     * 直接跳转主界面
     */
    private void jumpIntoStartActivity() {
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 刷新60秒发送的展示的执行
     */
    private Handler handler = new Handler() {


        public void handleMessage(android.os.Message msg) {
            yanzhenmakeyongmiao--;
            if (yanzhenmakeyongmiao <= 0) {
                btnCode.setText("获取验证码");
                btnCode.setEnabled(true);
                timer.cancel();
                timer = null;
                yanzhenmakeyongmiao = 60;
                return;
            }
            btnCode.setText(yanzhenmakeyongmiao + "s后重新发送");
        }

        ;
    };

    private final int what_flushTime = 3;
    /**
     * 刷新60秒发送的展示的定时
     */
    private class FulshTime extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(what_flushTime);
        }

    }
    /**
     * 判断是否需要登录
     *
     * @return true 代表需要重新登录
     */
    public boolean needLogin() {
        if (UserInfoVo.DataBean.getInstance().getUid() != null) {
            return false;//有账号不需要登录
        } else {
            return true;//需要登录
        }

    }
}
