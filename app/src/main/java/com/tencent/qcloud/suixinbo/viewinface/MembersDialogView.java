package com.tencent.qcloud.suixinbo.viewinface;

import com.tencent.qcloud.suixinbo.model.MemberInfo;

import java.util.ArrayList;


/**
 * 成员列表回调
 */
public interface MembersDialogView extends MvpView {

    void showMembersList(ArrayList<MemberInfo> data);

}
