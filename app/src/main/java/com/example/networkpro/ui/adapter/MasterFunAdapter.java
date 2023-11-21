package com.example.networkpro.ui.adapter;

import com.example.lib_bean.bean.MasterFunBean;
import com.example.lib_common.BaseApplication;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.consts.Const;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.dialog.call.OnDialogMainClickCall;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.MasterFunItemLayoutBinding;
import com.example.networkpro.ui.activity.LoginActivity;
import com.example.networkpro.ui.activity.SettingActivity;
import com.example.networkpro.ui.activity.ShopsSettingActivity;
import com.example.networkpro.ui.activity.UpdateFoodActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/7/1 6:15 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class MasterFunAdapter extends BaseEasyAdapter<MasterFunBean, MasterFunItemLayoutBinding> {
    public MasterFunAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.master_fun_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<MasterFunItemLayoutBinding> holder, int position, MasterFunBean data) {
        addItemClickListener((data1, holder1, pos) -> {
            switch (data1.type) {
                case Const.MasterFunType.TYPE_SETTING:
                    JumpUtils.jump(SettingActivity.class);
                    break;
                case Const.MasterFunType.TYPE_QUIT_LOGIN:
                    DialogManage.init(mContext, DialogType.TYPE_A).setDataA("确定退出登录?", null, () -> {
                        UserManage.setLoginBean(null);
                        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
                        instance.getActivityManager().finishAll();
                        JumpUtils.jump(LoginActivity.class);
                    }, () -> {

                    }, null);
                    break;
                case Const.MasterFunType.TYPE_UPDATE_SHOP:
                    JumpUtils.jump(UpdateFoodActivity.class);
                    break;
                case Const.MasterFunType.TYPE_ORDER_DETAIL:
                    ToastUtils.show("订单明细---> 敬请期待");
                    break;
                case Const.MasterFunType.TYPE_CLEAR_ALL_SHOP_DATA:
                    DialogManage.init(mContext, DialogType.TYPE_A).setDataA("清除数据需要重启应用，是否确认清除？", null, new OnDialogMainClickCall() {
                        @Override
                        public void mainCall() {
                            ToastUtils.show("清除商品---> 敬请期待");
//
//                            CacheDataManage.clearAllShop();
//                            BaseApplication instance = (BaseApplication) AppApplication.getInstance();
//                            instance.getActivityManager().finishAll();
//                            JumpUtils.jump(SplashActivity.class);
                        }
                    }, () -> {

                    }, null);
                    break;
                case Const.MasterFunType.TYPE_CONNECT_US:
                    DialogManage.init(mContext, DialogType.TYPE_D).setDataD("确认拨打？", "4008515151", null, () -> {
                        XXPermissions.with(mContext)
                                .permission(Permission.CALL_PHONE)
                                .request(new OnPermissionCallback() {
                                    @Override
                                    public void onGranted(List<String> permissions, boolean all) {
                                        JumpUtils.jumpCallPhone("4008515151");
                                    }

                                    @Override
                                    public void onDenied(List<String> permissions, boolean never) {
                                        ToastUtils.show("权限被拒绝，拨打失败");
                                    }
                                });
                    }, () -> {
                    }, null);
                    break;
                case Const.MasterFunType.TYPE_SHOPS_SETTING:
                    JumpUtils.jump(ShopsSettingActivity.class);
                    break;
            }
        });
    }
}
