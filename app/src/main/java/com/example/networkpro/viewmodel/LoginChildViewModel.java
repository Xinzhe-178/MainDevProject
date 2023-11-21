package com.example.networkpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.lib_bean.BaseObjBean;
import com.example.lib_bean.bean.LoginBean;
import com.example.lib_bean.bean.RegisterBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.manage.WanAndroidApiManager;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_utils.DeviceUtil;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.databinding.ActivityLoginBinding;
import com.example.networkpro.ui.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by 王鑫哲 on 2022/4/8 上午 10:40
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class LoginChildViewModel extends LoginViewModel {

    public LoginChildViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    @Override
    public void init(ActivityLoginBinding binding) {
        super.init(binding);

        mPageViewOne.setViewModel(this);
        mPageViewTwo.setViewModel(this);
    }

    /**
     * 登录点击
     */
    public OnBindingClickCall onImmediatelyLoginClick = () -> {
        if (isSatisfyLoginCondition()) {
            HashMap<String, String> map = new HashMap<>();
            String userName = TextUtils.getText(mPageViewOne.edUser);
            String password = TextUtils.getText(mPageViewOne.edPwd);
            map.put("username", userName);
            map.put("password", password);

            WanAndroidApiManager.getInstance().onLoginApiCall(map, new NetCallBack<BaseObjBean<LoginBean>>() {
                @Override
                public void onStart() {
                    LoadingDialogController.getInstance().showDialog();
                }

                @Override
                public void onSuccess(BaseObjBean<LoginBean> result) {
                    ToastUtils.show("登录成功");
                    LoadingDialogController.getInstance().dismissDialog();

                    LoginBean data = result.data;
                    data.password = password;
                    data.phoneModel = DeviceUtil.getManufacturer();
                    data.registerData = String.valueOf(System.currentTimeMillis());

                    // 缓存登录者的信息
                    UserManage.setLoginBean(data);
                    // 登录成功 进入首页
                    JumpUtils.jump(MainActivity.class);
                    // 关闭登录页面
                    finish.setValue(null);
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                    LoadingDialogController.getInstance().dismissDialog();
                }
            });
        }
    };

    /**
     * 注册点击
     * 将注册页输入的内容传到登录页
     * 将注册页输入的数据清空
     */
    public OnBindingClickCall onImmediatelyRegisterClick = () -> {
        if (isSatisfyRegisterCondition()) {
            String userName = TextUtils.getText(mPageViewTwo.edUser);
            String password = TextUtils.getText(mPageViewTwo.edPwd);

            HashMap<String, String> map = new HashMap<>();
            map.put("username", userName);
            map.put("password", password);
            map.put("repassword", password);

            WanAndroidApiManager.getInstance().onRegisterApiCall(map, new NetCallBack<BaseObjBean<RegisterBean>>() {
                @Override
                public void onStart() {
                    LoadingDialogController.getInstance().showDialog();
                }

                @Override
                public void onSuccess(BaseObjBean<RegisterBean> result) {
                    ToastUtils.show("注册成功");
                    LoadingDialogController.getInstance().dismissDialog();

                    // 切换到登录
                    selectPage(0);
                    mPageViewOne.edUser.setText(userName);
                    mPageViewOne.edPwd.setText(password);
                    mPageViewTwo.edUser.setText("");
                    mPageViewTwo.edPwd.setText("");
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                    LoadingDialogController.getInstance().dismissDialog();
                }
            });
        }
    };
}
