package com.jimmy.yuenkeji.upa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.tencent.qcloud.suixinbo.viewinface.LoginView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册
 * Created by Administrator on 2016/5/17.
 */
public class RegisterActivity extends AppCompatActivity implements LoginView {

    @ViewInject(R.id.edt_name)
    private EditText edtName;
    @ViewInject(R.id.edt_psw)
    private EditText edtCode;
    private static final String TAG = "RegisterActivity";
    @ViewInject(R.id.btn_code)
    private Button btnCode;
    @ViewInject(R.id.btn_registers)
    private Button btnRegister;
    private Timer timer;
    private int yanzhenmakeyongmiao = 60;
    LoginHelper mLoginHeloper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        timer = new Timer();
        mLoginHeloper = new LoginHelper(this, this);
    }

    @OnClick({R.id.rl_back_register, R.id.btn_code, R.id.btn_registers,R.id.iv_back})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.rl_back_register:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_code:
                getYZM();
                break;
            case R.id.btn_registers:
                register();
                break;
            default:
                break;

        }
    }

    /**
     * 注册并登录
     */
    private void register() {

        String identifier = edtName.getText().toString();
        String verify = edtCode.getText().toString();
        if (TextUtils.isEmpty(identifier)) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(verify)) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("identifier", "86-" + identifier);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("type", 2 + "");
        Log.i(TAG, "86-" + identifier + "--lolo--" + "verify" + verify + "--lplp-" + UrlUtils.REGISTER_URL);
        QavsdkApplication.httpUtils.send(HttpRequest.HttpMethod.POST, UrlUtils.REGISTER_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String resultInfo = responseInfo.result;
                Log.i(TAG, " 注册成功===" + resultInfo);
                UserInfoVo userInfoVo = GsonUtils.changeGsonToBean(responseInfo.result, UserInfoVo.class);
                if (userInfoVo.getCode().equals("22")) {
                    Toast.makeText(RegisterActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userInfoVo.getCode().equals("23")) {
                    Toast.makeText(RegisterActivity.this, "手机号不存在,请先注册 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userInfoVo.getCode().equals("24")) {
                    Toast.makeText(RegisterActivity.this, "手机号已注册,请直接登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userInfoVo.getCode().equals("1")) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    String userSig = userInfoVo.getData().getUser_sig();
                    String identifier = "86-"+ userInfoVo.getData().getUser_phone();
                    Log.i(TAG,"--用户签名--"+userSig+"-用户身份—"+identifier);

                    UserInfoVo.DataBean.getInstance().setUser_phone(identifier);
                    UserInfoVo.DataBean.getInstance().setUser_sig(userSig);
                    UserInfoVo.DataBean.getInstance().setUid(userInfoVo.getData().getUid());


                    PrefUtils.setString(getApplication(),"usersing", userInfoVo.getData().getUser_sig());
                    PrefUtils.setString(getApplication(),"identifier",userInfoVo.getData().getUser_phone());
                    PrefUtils.setString(getApplication(),"avator", userInfoVo.getData().getFace());
                    PrefUtils.setString(getApplication(),"upaId", userInfoVo.getData().getUid());
                    PrefUtils.setString(getApplication(),"gender", userInfoVo.getData().getUser_gender());
                    PrefUtils.setString(getApplication(),"signature", userInfoVo.getData().getUser_signature());
                    PrefUtils.setString(getApplication(),"emotion", userInfoVo.getData().getEmotion_state());
                    PrefUtils.setString(getApplication(),"age", userInfoVo.getData().getUser_age());
                    PrefUtils.setString(getApplication(),"nickname", userInfoVo.getData().getNickname());
                    mLoginHeloper.imLogin(identifier, userSig);


                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "==TAG==注册失败");
            }
        });


    }

    /**
     * 获取注册验证码
     */
    private void getYZM() {
        String user_phone = edtName.getText().toString();
        if (TextUtils.isEmpty(user_phone)) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtil.isMobileNO(user_phone)) {
            Toast.makeText(RegisterActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
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
        params.addBodyParameter("type", 2 + "");
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


    @Override
    protected void onDestroy() {
        if (null != timer) {
            timer.cancel();
        }
        mLoginHeloper.onDestory();
        super.onDestroy();
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
        Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }


}
