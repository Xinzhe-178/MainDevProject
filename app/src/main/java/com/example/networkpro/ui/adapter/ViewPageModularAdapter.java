package com.example.networkpro.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lib_bean.bean.local.ViewPageModularBean;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.databinding.ViewPageModularViewLayoutSonBinding;
import com.example.networkpro.ui.view.ViewPageModularView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/12/4 上午 10:25
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewPageModularAdapter extends PagerAdapter {
    private List<ViewPageModularViewLayoutSonBinding> mViewList; //内布局文件
    private List<ViewPageModularBean> mList; //数据源
    private Context mContext;

    public ViewPageModularAdapter() {
    }

    public ViewPageModularAdapter(List<ViewPageModularViewLayoutSonBinding> viewList, List<ViewPageModularBean> list) {
        mViewList = viewList;
        mList = list;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        mContext = container.getContext();
        initRecyclerView(container, position);
        container.addView(mViewList.get(position).getRoot());
        return mViewList.get(position).getRoot();
//
//        ViewPageModularViewLayoutSonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.view_page_modular_view_layout_son, null, false);
//
//        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
//        HomeModularAdapter modularAdapter = new HomeModularAdapter(getAdapterData(position, ViewPageModularView.pageNum));
//        binding.recyclerView.setAdapter(modularAdapter);
//
//        container.addView(binding.getRoot());
//
//        return binding.getRoot();
    }

    /**
     * 初始化adapter
     *
     * @param container
     * @param position
     */
    private void initRecyclerView(ViewGroup container, int position) {
        ViewPageModularViewLayoutSonBinding view = mViewList.get(position);
        //当前页adapter需要处理的数据
//        List<ViewPageModularBean> adapterData = getAdapterData(position, ViewPageModularView.pageNum);
        //禁止recyclerView滑动
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), showGridLayoutCount(adapterData)) {
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 4) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        view.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        HomeModularAdapter modularAdapter = new HomeModularAdapter(getAdapterData(position, ViewPageModularView.pageNum), BR.model);
        view.recyclerView.setAdapter(modularAdapter);
        modularAdapter.update(getAdapterData(position, ViewPageModularView.pageNum));

        LogUtils.PrintE("mViewPageList", getAdapterData(position, ViewPageModularView.pageNum).toString() + "pos--->" + position);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView(mViewList.get(position).getRoot());
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    /**
     * 设置适配器数据
     *
     * @param page        当前显示页
     * @param pageShowNum 每页显示多少数据
     * @return
     */
    private List<ViewPageModularBean> getAdapterData(int page, int pageShowNum) {
        ArrayList<ViewPageModularBean> list = new ArrayList<>();
        for (int i = 0; i <= page; i++) {
            for (int i1 = 0; i1 < mList.size(); i1++) {
                if (mList.get(i1).showPage == i) {
                    if (list.size() >= pageShowNum) {
                        ToastUtils.show("第" + i + "页" + "数据添加有误哦!");
                        return list;
                    }
                    list.add(mList.get(i1));
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 根据实际的数据控制列表显示n列
     * 如有3条数据 则显示3列 UI效果更佳
     * 按页显示
     *
     * @return
     */
    private int showGridLayoutCount(List<ViewPageModularBean> list) {
        if (list.size() <= ViewPageModularView.pageNum) { //一页如果显示的数据超过要显示的页数 则
            return 4;
        } else {
            return 3;
        }
    }

    public int getViewPageCount(List<ViewPageModularBean> list) {
        int pageNum = ViewPageModularView.pageNum;
        boolean b = list.size() % pageNum != 0;
        int count = b ? (list.size() / pageNum) + 1 : list.size() / pageNum;
        return count;
    }
}
