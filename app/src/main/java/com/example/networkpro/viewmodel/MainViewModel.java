package com.example.networkpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.lib_bean.bean.CurrentTimeBean;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.dialog.TipView;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.DateUtils;
import com.example.lib_utils.Res;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityMainBinding;
import com.example.networkpro.manage.fragment.FragmentSwitch;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 03:43
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class MainViewModel extends BaseViewModel {

    private ActivityMainBinding mBinding;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    /**
     * 初始化底部Tab
     */
    public void initBottomTab(ActivityMainBinding binding, FragmentSwitch fragmentSwitch) {
        mBinding = binding;

        if (AppStyleManage.isShowSquare()) {
            mBinding.navView.setTabDefTitles("首页", "广场", "我的").setDefTabIcons(R.drawable.icon_home_sel_def, R.drawable.icon_square_sel_def, R.drawable.icon_master_sel_def).setSelTabIcons(R.drawable.icon_home_sel_sel, R.drawable.icon_square_sel_sel, R.drawable.icon_master_sel_sel).setSelIsBold(true).build();
        } else {
            mBinding.navView.setTabDefTitles("首页", "我的").setDefTabIcons(R.drawable.icon_home_sel_def, R.drawable.icon_master_sel_def).setSelTabIcons(R.drawable.icon_home_sel_sel, R.drawable.icon_master_sel_sel).setSelIsBold(true).build();
        }

        mBinding.navView.getGroupView().setBackgroundColor(Res.color(R.color.pink));

        //底部Tab点击监听
        mBinding.navView.setOnNavTabClickListener((lastIndex, currentIndex) -> {
            if (lastIndex == currentIndex) {
                // 当前选中点击
                LiveEventBus.get(EventPath.HOME_FRAGMENT_RETURN_TOP).post(lastIndex);
            } else {
                fragmentSwitch.switchPage(currentIndex);
                // 当前选中的为首页时则将圆角去掉 其余都设置圆角背景
                if (currentIndex == 0) {
                    mBinding.navView.getGroupView().setBackgroundColor(Res.color(R.color.pink));
                } else {
                    mBinding.navView.getGroupView().setBackgroundResource(R.drawable.home_bottom_shop_view_con_bg);
                }
            }
        });
    }

    /**
     * 获取当前日期时间
     */
    public void initIsNormalSse() {
        net(getUrlApi(Urls.SU_NING_BASE_URL).getCurrentTime(), new NetCallBack<CurrentTimeBean>() {
            @Override
            public void onSuccess(CurrentTimeBean result) {
                String todayFlag = DateUtils.getTodayFlag(new Date(result.currentTime));
                String time = DateUtils.getTimeFromLong(result.currentTime);
                TipView.getInstance().start(todayFlag.concat("好"), "现在是 ".concat(time));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentSwitch.getInstance().onDestroy();
    }
}
