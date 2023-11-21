package com.example.networkpro.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_bean.bean.local.ViewPageModularBean;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ViewPageModularViewLayoutBinding;
import com.example.networkpro.databinding.ViewPageModularViewLayoutSonBinding;
import com.example.networkpro.ui.adapter.ViewPageModularAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/12/3 下午 06:18
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewPageModularView extends BaseFrameLayout<ViewPageModularViewLayoutBinding> {
    /**
     * 显示的数据源集合
     */
    private List<ViewPageModularBean> mList;
    /**
     * 第几页 View
     */
    private List<ViewPageModularViewLayoutSonBinding> mViews;
    /**
     * 默认一页显示多少条数据 多了进行分页
     * 默认: 双排 每排4个
     */
    public static int pageNum = 8;

    private ViewPageModularAdapter mViewPageAdapter;

    private ViewPageModularViewLayoutSonBinding mMSonBinding;

    public ViewPageModularView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_page_modular_view_layout;
    }

    @Override
    public void initView() {
        mViews = new ArrayList<>();
        mMSonBinding = inflate(R.layout.view_page_modular_view_layout_son);
    }

    /**
     * 设置ViewPage显示
     */
    private void addViewPageView() {
        if (mList == null || mList.size() == 0) {
            ToastUtils.show("数据源为空");
            return;
        }

        LogUtils.PrintE("mList", mList.toString());

        if (mList.size() <= pageNum) {
            mViews.add(mMSonBinding);
        } else {
            int num = mList.size() / pageNum;
            for (int i = 0; i < num + 1; i++) {
                mViews.add(mMSonBinding);
            }
        }

        mViewPageAdapter = new ViewPageModularAdapter(mViews, mList);
        mBinding.viewPage.setAdapter(mViewPageAdapter);
        mBinding.viewPage.setCurrentItem(0);
    }

    /**
     * 添加数据源
     *
     * @param list
     * @return
     */
    public ViewPageModularView addData(ArrayList<ViewPageModularBean> list) {
        if (list != null) {
            mList = list;
            addViewPageView();
        } else {
            ToastUtils.show("数据为空");
        }
        return this;
    }
}
