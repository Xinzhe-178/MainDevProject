package com.example.networkpro.ui.activity;

import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.databinding.ViewDataBinding;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.SpinnerView;
import com.example.lib_common.view.SwitchButton;
import com.example.networkpro.ui.view.itemselect.ItemSelectBean;
import com.example.networkpro.ui.view.itemselect.ItemSelectType;
import com.example.networkpro.ui.view.itemselect.ItemSelectView;
import com.example.networkpro.viewmodel.HomeControlViewModel;

import java.util.ArrayList;

/**
 * Created by 王鑫哲 on 2022/8/26 22:12
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeControlActivity extends BaseMvvmActivity<ViewDataBinding, HomeControlViewModel> {

    private ItemSelectView mItemSelectView;

    @Override
    protected void initView() {
        mTopBar.setTitle("首页功能");

        mItemSelectView = new ItemSelectView(mActivity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
        mItemSelectView.setLayoutParams(layoutParams);
        getBindingBase().viewBaseActivity.addView(mItemSelectView);

        initSelectView();
    }

    private void initSelectView() {
        ArrayList<ItemSelectBean> itemSelectBeans = new ArrayList<>();

        ItemSelectBean homeDialogShowBean = new ItemSelectBean(ItemSelectType.TYPE_SPINNER, "首页弹窗展示时机");
        ItemSelectBean isShowSquareTabBean = new ItemSelectBean(ItemSelectType.TYPE_SWITCH_BUTTON, "是否展示【广场】Tab");
        ItemSelectBean webIsJumpExternalBean = new ItemSelectBean(ItemSelectType.TYPE_SWITCH_BUTTON, "网页是否可以跳转外部应用");

        itemSelectBeans.add(homeDialogShowBean);
        itemSelectBeans.add(isShowSquareTabBean);
        itemSelectBeans.add(webIsJumpExternalBean);

        initSpinnerView(homeDialogShowBean, spinnerView -> mViewModel.initHomeDialogShow(spinnerView));
        initSwitchButton(isShowSquareTabBean, switchButton -> mViewModel.initIsShowSquareTab(switchButton));
        initSwitchButton(webIsJumpExternalBean, switchButton -> mViewModel.initWebIsJumpExternal(switchButton));

        mItemSelectView.create(itemSelectBeans);
    }

    private void initSwitchButton(ItemSelectBean bean, OnBindingClickParamsCall<SwitchButton> call) {
        bean.init((endView, selectType) -> {
            if (endView instanceof SwitchButton) {
                SwitchButton switchButton = (SwitchButton) endView;
                if (call != null) {
                    call.clickCall(switchButton);
                }
            }
        });
    }

    private void initSpinnerView(ItemSelectBean bean, OnBindingClickParamsCall<SpinnerView> call) {
        bean.init((endView, selectType) -> {
            if (endView instanceof SpinnerView) {
                SpinnerView spinnerView = (SpinnerView) endView;
                if (call != null) {
                    call.clickCall(spinnerView);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Class<HomeControlViewModel> onBindViewModel() {
        return HomeControlViewModel.class;
    }
}
