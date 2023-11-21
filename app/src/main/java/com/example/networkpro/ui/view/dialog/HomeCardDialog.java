package com.example.networkpro.ui.view.dialog;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.example.lib_bean.bean.CardListBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_utils.DateUtils;
import com.example.lib_utils.ShareData;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeCardDialogHintLayoutBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/8/27 17:15
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeCardDialog extends CardListTopDialog {

    public HomeCardDialog(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected List<CardListBean> getDataBean() {
        List<CardListBean> list = new ArrayList<>();
        list.add(getHomeCardDialogHint());
        list.add(getHomeCardDialogHint2());
        list.add(getHomeCardDialogHint3());
        return list;
    }

    @Override
    public void showDialog() {
        show();
    }

    private CardListBean getHomeCardDialogHint() {
        CardListBean data = getData(R.layout.home_card_dialog_hint_layout, 0, null);
        ViewDataBinding binding = data.binding;
        if (binding instanceof HomeCardDialogHintLayoutBinding) {
            HomeCardDialogHintLayoutBinding hintLayoutBinding = (HomeCardDialogHintLayoutBinding) binding;
            setToDayTitle(hintLayoutBinding.tvTitleHint);
        }
        return data;
    }

    private CardListBean getHomeCardDialogHint2() {
        return getData(R.layout.home_card_dialog_hint2_layout, R.id.v_close, bean -> {
            if (getCardData().size() == 2) {
                bean.onClosureCall.onDismiss();
            } else {
                bean.onClosureCall.onRemoveItem(getCardDataPos(bean));
            }
        });
    }

    private CardListBean getHomeCardDialogHint3() {
        return getData(R.layout.home_card_dialog_hint3_layout, R.id.v_close, bean -> {
            if (getCardData().size() == 2) {
                bean.onClosureCall.onDismiss();
            } else {
                bean.onClosureCall.onRemoveItem(getCardDataPos(bean));
            }
        });
    }

    /**
     * 根据时间动态设置标题
     *
     * @param textView
     */
    private void setToDayTitle(@NonNull TextView textView) {
        textView.setText(DateUtils.getTodayFlag(new Date()).concat("好"));
    }

    @Override
    public void onDismiss() {
        super.onDismiss();

        // 如果需要弹出诱导的话 在关闭掉cardDialog后弹出 不然效果不好
        boolean isShowBottomGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOME_BOTTOM);
        boolean isShowBottomOrderGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOM_BOTTOM_ORDER);
        if (!isShowBottomGuide || !isShowBottomOrderGuide) {
            LiveEventBus.get(EventPath.HOME_BOTTOM_GUIDE_SHOW).post(true);
        }
    }
}
