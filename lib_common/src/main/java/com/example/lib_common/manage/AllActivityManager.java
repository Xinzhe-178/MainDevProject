package com.example.lib_common.manage;

import android.app.Activity;

import com.example.lib_common.dialog.TipView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/12/1 下午 01:44
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class AllActivityManager {

    /**
     * 保存创建的所有Activity
     */
    private List<Activity> mAllActivityManager = new ArrayList<>();

    /**
     * 栈顶Activity
     */
    private String mTopPageView = "";

    /**
     * 添加Activity到管理器
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            // 开启新的Activity时 如果TipView正在显示 去除
            TipView tipView = TipView.getInstance();
            tipView.hide();
            mAllActivityManager.add(activity);
        }
    }

    /**
     * 获取栈顶ActivityName
     *
     * @return 栈顶ActivityName
     */
    public String getTopPageView() {
        return mTopPageView;
    }

    /**
     * 设置TopActivity 栈顶Activity
     *
     * @param topPageView 栈顶Activity
     */
    public void setTopPageView(String topPageView) {
        this.mTopPageView = topPageView;
    }

    /**
     * 从管理器移除Activity
     *
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mAllActivityManager.remove(activity);
        }
    }

    /**
     * 关闭所有activity
     */
    public void finishAll() {
        for (Activity activity : mAllActivityManager) {
            activity.finish();
        }
    }

    /**
     * 获取Activity列表
     *
     * @return 所有Activity列表
     */
    public List<Activity> getAllActivity() {
        return mAllActivityManager;
    }

    /**
     * 获取Activity列表
     *
     * @return 所有Activity列表
     */
    public Activity getTopActivity() {
        if (mAllActivityManager.size() > 0) {
            return mAllActivityManager.get(mAllActivityManager.size() - 1);
        }
        return null;
    }
}
