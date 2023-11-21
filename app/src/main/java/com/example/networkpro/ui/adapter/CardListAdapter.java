package com.example.networkpro.ui.adapter;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.lib_bean.bean.CardListBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_utils.DensityUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.CardListDialogItemLayoutBinding;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/8/27 15:13
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CardListAdapter extends BaseEasyAdapter<CardListBean, CardListDialogItemLayoutBinding> {

    public CardListAdapter(List<CardListBean> dataBean, int BR_id) {
        super(dataBean, BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.card_list_dialog_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<CardListDialogItemLayoutBinding> holder, int position, CardListBean data) {
        FrameLayout flGroup = holder.getBinding().flGroup;

        // 实现一个页面显示出第二个item 故将宽度设置为屏幕宽度-50 只有一个item默认铺满屏幕
        if (getItemCount() > 1) {
            ViewGroup.LayoutParams layoutParams = flGroup.getLayoutParams();
            layoutParams.width = DensityUtils.getWidth() - DensityUtils.dp(60);
            flGroup.setLayoutParams(layoutParams);
        }

        if (data.binding != null) {
            /**
             * IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
             * 这个错误一般出现在addView()
             * 大致意思是子view已经拥有一个父布局，我们需要先让该子view的父布局调用一下 removeView()方法。也就是说一个子view只能拥有一个父view。
             * 这种情况往往会出现在动态添加view上，我们添加子view的时候，并不知道子view是不是已经拥有一个父view，如果说已经存在一个父view那么就会报以上错误。
             * 解决方法为下面if逻辑 添加View前先清除View的父容器
             */

            synchronized (this) {
                ViewGroup viewParent = (ViewGroup) data.binding.getRoot().getParent();
                if (viewParent != null) {
                    viewParent.removeAllViews();
                }

                flGroup.addView(data.binding.getRoot());
            }
        }
    }
}
