package com.example.networkpro.manage.fragment;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.mvvm.IBaseViewModel;
import com.example.lib_utils.LogUtils;

/**
 * Created by 王鑫哲 on 2023/4/15 14:05
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class FragmentSwitch implements IFragmentSwitch, IBaseViewModel {

    private FragmentManager mManager;

    private Fragment[] mFragments;

    private int mCurIndex;

    private FragmentSwitch() {
    }

    private static class Holder {
        private static final FragmentSwitch H = new FragmentSwitch();
    }

    public static FragmentSwitch getInstance() {
        return FragmentSwitch.Holder.H;
    }

    public void setFragmentInitCall(OnBindingClickParamsCall<Fragment> fragmentInitCall) {
        if (mFragments == null || mFragments.length == 0) {
            return;
        }
        for (Fragment fragment : mFragments) {
            if (fragmentInitCall != null) {
                fragmentInitCall.clickCall(fragment);
            }
        }
    }

    @Override
    public void init(FragmentManager manager, @IdRes int fragmentLayout, int defIndex, Fragment... fragment) {
        if (fragment == null || fragment.length == 0) {
            return;
        }
        mManager = manager;
        mFragments = fragment;
        FragmentTransaction beginTransaction = mManager.beginTransaction();

        for (int i = 0; i < fragment.length; i++) {
            Fragment fragment1 = fragment[i];
            beginTransaction.add(fragmentLayout, fragment1);
        }
        beginTransaction.commit();
        fragmentTabSwitch(defIndex);
    }

    @Override
    public void switchPage(int index) {
        mCurIndex = index;
        fragmentTabSwitch(mCurIndex);
    }

    @Override
    public Fragment getCurSelFragment() {
        if (mFragments == null || mFragments.length == 0 || mCurIndex >= mFragments.length) {
            return new Fragment();
        }
        return mFragments[mCurIndex];
    }

    @Override
    public Fragment[] getAllFragment() {
        return mFragments == null ? new Fragment[]{} : mFragments;
    }

    private void fragmentTabSwitch(int currentIndex) {
        if (mFragments == null || mFragments.length == 0 || currentIndex >= mFragments.length) {
            return;
        }
        FragmentTransaction beginTransaction = mManager.beginTransaction();

        for (int i = 0; i < mFragments.length; i++) {
            beginTransaction.hide(mFragments[i]);
        }
        beginTransaction.show(mFragments[currentIndex]).commit();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        LogUtils.PrintE("onDestroy---> ");
        if (mFragments == null || mFragments.length == 0 || mManager == null) {
            return;
        }
        for (Fragment fragment : mFragments) {
            mManager.beginTransaction().remove(fragment);
        }
        mManager = null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
