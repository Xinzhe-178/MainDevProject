package com.example.networkpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.example.lib_bean.bean.HomeCardShowBean;
import com.example.lib_common.BaseApplication;
import com.example.lib_common.consts.Const;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.view.SpinnerView;
import com.example.lib_common.view.SwitchButton;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.ShareData;
import com.example.networkpro.R;

import java.util.ArrayList;

/**
 * Created by 王鑫哲 on 2023/4/19 4:25 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeControlViewModel extends BaseViewModel {
    /**
     * 是否归位开关
     * 当点击开关时，首先设置为相反值，点击确定后再确定其状态，此时，任何方式关闭dialog打断选择，则归为初始状态
     * 此标志存在原因： 封装dialog监听关闭时，点击确认按钮 也会走关闭回调 导致状态错乱
     */
    private boolean isCanClick = false;

    public HomeControlViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initHomeDialogShow(SpinnerView spinnerView) {
        final ArrayList<Object> list = new ArrayList<>();
        list.add(new HomeCardShowBean("打开app", false, Const.HomeCardDialogShowConst.TYPE_EVERY_OPEN));
        list.add(new HomeCardShowBean("一天一次", false, Const.HomeCardDialogShowConst.TYPE_EVERY_DAY));
        list.add(new HomeCardShowBean("不再弹出", false, Const.HomeCardDialogShowConst.TYPE_NO_SHOW));

        ViewDataBinding topView = inflate(R.layout.home_con_home_card_spinner_item_layout);
        TextView title = topView.getRoot().findViewById(R.id.tv_title);
        String type = ShareData.getShareStringData(Const.HomeCardDialogShowConst.TYPE);
        for (Object o : list) {
            if (o instanceof HomeCardShowBean) {
                HomeCardShowBean bean1 = (HomeCardShowBean) o;
                if (type.equals(bean1.type)) {
                    title.setText(bean1.title);
                    bean1.iconIsShow = true;
                }
            }
        }

        spinnerView
                .setData(list)
                .setTopView(topView.getRoot())
                .setWidthIdEqualTopView(true)
                .setOpenItemView(R.layout.home_con_home_card_spinner_item_layout)
                .setItemInitCall((view, o) -> {
                    TextView tv_title = view.findViewById(R.id.tv_title);
                    if (o instanceof HomeCardShowBean) {
                        HomeCardShowBean bean1 = (HomeCardShowBean) o;
                        tv_title.setText(bean1.title);

                        ImageView imageView = view.findViewById(R.id.iv_down_up);
                        // 由于TopView和ItemView使用的是同一个View 而item的图标有点大不好看 这里调小
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.width = DensityUtils.dp(10);
                        layoutParams.height = DensityUtils.dp(10);
                        imageView.setImageResource(R.drawable.ic_circle);
                        // 设置指示标是否显示
                        imageView.setVisibility(bean1.iconIsShow ? View.VISIBLE : View.GONE);
                        // 直接重置为未选中
                        bean1.iconIsShow = false;
                    }
                })
                .setItemClickCall((view, o) -> {
                    TextView tv_title = view.findViewById(R.id.tv_title);
                    if (o instanceof HomeCardShowBean) {
                        HomeCardShowBean bean1 = (HomeCardShowBean) o;
                        tv_title.setText(bean1.title);
                        // 缓存选中的状态
                        UserManage.setHomeCardDialogShowType(bean1.type);
                        // 将当前点击的设置为显示
                        bean1.iconIsShow = true;
                    }
                    SpinnerView.SpinnerAdapter adapter = spinnerView.getSpinnerAdapter();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                })
                .setOnOpenCall(view -> {
                    // 展开时设置图标指示为收起
                    ImageView imageView = topView.getRoot().findViewById(R.id.iv_down_up);
                    imageView.setImageResource(R.drawable.ic_arrow_up);
                })
                .setOnDismissCall(view -> {
                    // 关闭时设置图标指示为展开
                    ImageView imageView = topView.getRoot().findViewById(R.id.iv_down_up);
                    imageView.setImageResource(R.drawable.ic_down);
                })
                .build();
    }

    public void initIsShowSquareTab(SwitchButton switchButton) {
        switchButton.setChecked(AppStyleManage.isShowSquare());

        switchButton.setmOnCheckedChangeListener(isChecked -> {
            BaseApplication instance1 = (BaseApplication) BaseApplication.getInstance();
            Activity activity = instance1.getActivityManager().getTopActivity();
            DialogManage.init(activity, DialogType.TYPE_A).setDataA("此操作需要重启应用，是否确认？", null, () -> {
                AppStyleManage.setIsShowSquare(!AppStyleManage.isShowSquare());
                ContextManager.restartApp();
                isCanClick = true;
            }, () -> {

            }, () -> {
                if (!isCanClick) {
                    switchButton.refreshChecked(AppStyleManage.isShowSquare(), false);
                }
            });
        });
    }

    public void initWebIsJumpExternal(SwitchButton switchButton) {
        switchButton.setChecked(AppStyleManage.webIsJumpExternal());
        switchButton.setmOnCheckedChangeListener(AppStyleManage::setWebIsJumpExternal);
    }
}
