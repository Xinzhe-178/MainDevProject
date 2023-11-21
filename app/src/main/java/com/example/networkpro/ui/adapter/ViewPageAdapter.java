package com.example.networkpro.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by 王鑫哲 on 2021/11/22 下午 07:14
 * E-mail: User_wang_178@163.com
 * Ps:  直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
 */
public class ViewPageAdapter extends PagerAdapter {

    private final List<View> mViewList;

    public ViewPageAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    public void updateData(List<View> viewList) {
        if (viewList != null) {
            mViewList.clear();
            mViewList.addAll(viewList);
            notifyDataSetChanged();
        }
    }

    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        ViewPager parent = (ViewPager) mViewList.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(mViewList.get(position), 0);
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }
}
