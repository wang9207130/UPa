package com.jimmy.yuenkeji.utils;

/**地址链接
 * Created by Administrator on 2016/6/13.
 */
public class UrlUtils {
    /**
     *1 服务器地址
     */

    public static final String SERVER_URL="http://192.168.0.112";
    /**
     *2 注册获取验证码
     */
    public static final String REGISTER_URL_CODE=SERVER_URL+"/20160507/Home/Public/SMScode";
    /**
     * 3：注册
     */
    public static final String REGISTER_URL=SERVER_URL+"/20160507/Home/User/register";
    /**
     *  登录
     */
    public static final String LOGIN_URL=SERVER_URL+"/20160507/Home/User/login";
    /**
     * 4: 观众开直播
     */
    public static final String NEW_ROOM_INFO=SERVER_URL+"/20160507/Home/member/roominfo";
    /**
     * 5：主播关闭直播
     */
    public static final String STOP_ROOM=SERVER_URL+"/20160507/Home/member/closeroom";
    /**
     * 6:获取直播列表
     */
    public static final String GET_LIVELIST=SERVER_URL+"/20160507/Home/Member/zhibolists";




}
