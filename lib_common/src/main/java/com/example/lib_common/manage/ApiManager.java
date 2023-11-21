package com.example.lib_common.manage;

import com.example.lib_bean.bean.LoginBean;
import com.example.lib_utils.TextUtils;

/**
 * Created by 王鑫哲 on 2022/9/13 2:56 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class ApiManager {

    protected final String userName;

    protected final String userPassword;

    public ApiManager() {
        LoginBean loginBean = UserManage.getLoginBean();
        userName = "loginUserName=".concat(TextUtils.isEmpty(loginBean.username) ? "" : loginBean.username);
        userPassword = "loginUserPassword=".concat(TextUtils.isEmpty(loginBean.password) ? "" : loginBean.password);
    }
}
