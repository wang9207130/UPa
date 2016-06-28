package com.jimmy.yuenkeji.bean;

import android.content.Context;
import android.content.SharedPreferences;

import com.jimmy.yuenkeji.utils.PrefUtils;

/**
 * 自己的数据
 * Created by Administrator on 2016/6/13.
 */
public class UserInfoVo {

    /**
     * code : 1
     * msg : 登陆成功
     * data : {"uid":"10000043","user_phone":"13795497151","username":"","face":"Data/headimg/default.jpg","user_logo":"","user_signature":"","user_gender":"0","user_address":"","user_sig":"eJxNjdtOg0AURf*FZxUGGC4mfaitFCuk0mpsDQlB5kBHWhinQ2kx-ntHQhPP41p77-OjvAaruzTL6qYSiTgzUO4VTbnpMSVQCZpT4BI61i0ybBebro0wGhIpY5QkqUgMTv4VD6RMeiUZMjV5rmwNEk6MckjSXPS7CGOsy8Rgj8APtK6k0DX5Rzf*2lcp6B76SQs7yMGWfv1HC4nDx2jy5G0WZbCdva39GRznu3XoR2wfqyevnkCsxirhD13r7LrGL*1lOKbj9n0avhTl83nTLKfQZp9eaMy77*ADyshscovlGSNf-mLbFKOR8nsBtyRbPw__","user_experience":"0","user_age":"0","emotion_state":"0","user_occupation":"","user_income":"0.00","user_give":"0.00","user_pay":"0.00","freeze":"0.00","account":"0.00","user_income_rmb":"0.00","user_pay_rmb":"0.00","paykey":"","zhibo":"0","certify_state":"0","user_fans":"0","user_focus":"0","nickname":"","login":"1","birthday":"","qq":"","reg_ip":"0","reg_time":"0","last_login_ip":"3232235630","last_login_time":"1465818560","status":"1"}
     */

    private String code;
    private String msg;
    /**
     * uid : 10000043
     * user_phone : 13795497151
     * username :
     * face : Data/headimg/default.jpg
     * user_logo :
     * user_signature :
     * user_gender : 0
     * user_address :
     * user_sig : eJxNjdtOg0AURf*FZxUGGC4mfaitFCuk0mpsDQlB5kBHWhinQ2kx-ntHQhPP41p77-OjvAaruzTL6qYSiTgzUO4VTbnpMSVQCZpT4BI61i0ybBebro0wGhIpY5QkqUgMTv4VD6RMeiUZMjV5rmwNEk6MckjSXPS7CGOsy8Rgj8APtK6k0DX5Rzf*2lcp6B76SQs7yMGWfv1HC4nDx2jy5G0WZbCdva39GRznu3XoR2wfqyevnkCsxirhD13r7LrGL*1lOKbj9n0avhTl83nTLKfQZp9eaMy77*ADyshscovlGSNf-mLbFKOR8nsBtyRbPw__
     * user_experience : 0
     * user_age : 0
     * emotion_state : 0
     * user_occupation :
     * user_income : 0.00
     * user_give : 0.00
     * user_pay : 0.00
     * freeze : 0.00
     * account : 0.00
     * user_income_rmb : 0.00
     * user_pay_rmb : 0.00
     * paykey :
     * zhibo : 0
     * certify_state : 0
     * user_fans : 0
     * user_focus : 0
     * nickname :
     * login : 1
     * birthday :
     * qq :
     * reg_ip : 0
     * reg_time : 0
     * last_login_ip : 3232235630
     * last_login_time : 1465818560
     * status : 1
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String uid;
        private String user_phone;
        private String username;
        private String face;//头像url
        private String user_logo;
        private String user_signature;
        private String user_gender;
        private String user_address;
        private String user_sig;
        private String user_experience;//用户经验
        private String user_age;
        private String emotion_state;
        private String user_occupation;
        private String user_income;//以收入
        private String user_give;
        private String user_pay;//已充值
        private String freeze;//冻结资金
        private String account;//账号余额
        private String user_income_rmb;
        private String user_pay_rmb;
        private String paykey;
        private String zhibo;//直播状态，0在房间外，1在房间内
        private String certify_state;//认证状态，0未认证，1认证
        private String user_fans;
        private String user_focus;
        private String nickname;
        private String login;//登录次数
        private String birthday;
        private String qq;
        private String reg_ip;//注册ip
        private String reg_time;//注册时间
        private String last_login_ip;//最后登录ip
        private String last_login_time;//最后登录时间
        private String status;//会员状态

        private static DataBean ourInstance = new DataBean();

        public static DataBean getInstance() {

            return ourInstance;
        }


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getUser_logo() {
            return user_logo;
        }

        public void setUser_logo(String user_logo) {
            this.user_logo = user_logo;
        }

        public String getUser_signature() {
            return user_signature;
        }

        public void setUser_signature(String user_signature) {
            this.user_signature = user_signature;
        }

        public String getUser_gender() {
            return user_gender;
        }

        public void setUser_gender(String user_gender) {
            this.user_gender = user_gender;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public String getUser_sig() {
            return user_sig;
        }

        public void setUser_sig(String user_sig) {
            this.user_sig = user_sig;
        }

        public String getUser_experience() {
            return user_experience;
        }

        public void setUser_experience(String user_experience) {
            this.user_experience = user_experience;
        }

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getEmotion_state() {
            return emotion_state;
        }

        public void setEmotion_state(String emotion_state) {
            this.emotion_state = emotion_state;
        }

        public String getUser_occupation() {
            return user_occupation;
        }

        public void setUser_occupation(String user_occupation) {
            this.user_occupation = user_occupation;
        }

        public String getUser_income() {
            return user_income;
        }

        public void setUser_income(String user_income) {
            this.user_income = user_income;
        }

        public String getUser_give() {
            return user_give;
        }

        public void setUser_give(String user_give) {
            this.user_give = user_give;
        }

        public String getUser_pay() {
            return user_pay;
        }

        public void setUser_pay(String user_pay) {
            this.user_pay = user_pay;
        }

        public String getFreeze() {
            return freeze;
        }

        public void setFreeze(String freeze) {
            this.freeze = freeze;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getUser_income_rmb() {
            return user_income_rmb;
        }

        public void setUser_income_rmb(String user_income_rmb) {
            this.user_income_rmb = user_income_rmb;
        }

        public String getUser_pay_rmb() {
            return user_pay_rmb;
        }

        public void setUser_pay_rmb(String user_pay_rmb) {
            this.user_pay_rmb = user_pay_rmb;
        }

        public String getPaykey() {
            return paykey;
        }

        public void setPaykey(String paykey) {
            this.paykey = paykey;
        }

        public String getZhibo() {
            return zhibo;
        }

        public void setZhibo(String zhibo) {
            this.zhibo = zhibo;
        }

        public String getCertify_state() {
            return certify_state;
        }

        public void setCertify_state(String certify_state) {
            this.certify_state = certify_state;
        }

        public String getUser_fans() {
            return user_fans;
        }

        public void setUser_fans(String user_fans) {
            this.user_fans = user_fans;
        }

        public String getUser_focus() {
            return user_focus;
        }

        public void setUser_focus(String user_focus) {
            this.user_focus = user_focus;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void getCache(Context context) {

            SharedPreferences sharedata = context.getSharedPreferences(PrefUtils.PREF_NAME, Context.MODE_PRIVATE);
            user_sig = sharedata.getString("usersing", "");
            user_phone = sharedata.getString("identifier", "");
            face = sharedata.getString("avator", "");
            uid = sharedata.getString("upaId", "");
            user_gender = sharedata.getString("gender", "");
            user_signature = sharedata.getString("signature", "");
            emotion_state = sharedata.getString("emotion", "");
            user_age = sharedata.getString("age", "");
            nickname = sharedata.getString("nickname", "");

        }

        public void setCache(Context context, UserInfoVo userInfoVo) {

            SharedPreferences sp = context.getSharedPreferences(PrefUtils.PREF_NAME, Context.MODE_PRIVATE);
            sp.edit()
                    .putString("usersing", userInfoVo.getData().getUser_sig())
                    .putString("identifier", userInfoVo.getData().getUser_phone())
                    .putString("avator", userInfoVo.getData().getFace())
                    .putString("upaId", userInfoVo.getData().getUid())
                    .putString("gender", userInfoVo.getData().getUser_gender())
                    .putString("signature", userInfoVo.getData().getUser_signature())
                    .putString("emotion", userInfoVo.getData().getEmotion_state())
                    .putString("age", userInfoVo.getData().getUser_age())
                    .putString("nickname", userInfoVo.getData().getNickname())
                    .commit();

        }
    }
}
