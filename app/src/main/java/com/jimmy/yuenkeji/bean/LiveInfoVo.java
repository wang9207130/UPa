package com.jimmy.yuenkeji.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class LiveInfoVo {

    /**
     * code : 1
     * msg : 主播信息
     * data : [{"face":"http://192.168.0.137/20160507/Data/headimg/default.jpg","uid":"10000099","nickname":"","user_signature":"","user_address":"","user_phone":"13125253434","numbers":"5","roomnum":"10000099","chatroomnum":"10000099"},{"face":"http://192.168.0.137/20160507/Data/headimg/default.jpg","uid":"10000100","nickname":"","user_signature":"","user_address":"","user_phone":"13125253437","numbers":"0","roomnum":"10000100","chatroomnum":"10000100"},{"face":"http://192.168.0.137/20160507/Data/headimg/201606/1465027788-38817.jpg","uid":"10000029","nickname":"","user_signature":"","user_address":"","user_phone":"13166056382","numbers":"0","roomnum":"10000029","chatroomnum":"0"}]
     */

    private String code;
    private String msg;
    /**
     * face : http://192.168.0.137/20160507/Data/headimg/default.jpg
     * uid : 10000099
     * nickname :
     * user_signature :
     * user_address :
     * user_phone : 13125253434
     * numbers : 5
     * roomnum : 10000099
     * chatroomnum : 10000099
     */

    private List<LiveInfoBean> data;

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

    public List<LiveInfoBean> getData() {
        return data;
    }

    public void setData(List<LiveInfoBean> data) {
        this.data = data;
    }

    public static class LiveInfoBean {
        private String face;
        private String uid;
        private String nickname;
        private String user_signature;
        private String user_address;
        private String user_phone;
        private String numbers;
        private String roomnum;
        private String chatroomnum;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser_signature() {
            return user_signature;
        }

        public void setUser_signature(String user_signature) {
            this.user_signature = user_signature;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getRoomnum() {
            return roomnum;
        }

        public void setRoomnum(String roomnum) {
            this.roomnum = roomnum;
        }

        public String getChatroomnum() {
            return chatroomnum;
        }

        public void setChatroomnum(String chatroomnum) {
            this.chatroomnum = chatroomnum;
        }
    }
}
