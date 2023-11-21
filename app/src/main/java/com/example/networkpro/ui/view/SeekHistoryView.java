package com.example.networkpro.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.example.lib_bean.bean.SeekHistoryBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.view.AutoNextLineLinearLayout;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_common.view.anim.AnimationRepeatListener;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.VibratorUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.SeekHistorViewLayoutBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/9/4 15:46
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHistoryView extends BaseFrameLayout<SeekHistorViewLayoutBinding> {

    /**
     * 流式布局集合
     * 注意操作数据时也要保持集合中的数据同步
     */
    private List<SeekHistoryBean> mSeekHistoryBeans;

    /**
     * 当前是否处于编辑模式 true：是 false非编辑模式
     */
    private boolean isEditMode = false;

    /**
     * 监听模式切换
     */
    private OnBindingClickParamsCall<Boolean> mOnEditModeListener;

    public void setOnEditModeListener(OnBindingClickParamsCall<Boolean> onEditModeListener) {
        mOnEditModeListener = onEditModeListener;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public SeekHistoryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.seek_histor_view_layout;
    }

    @Override
    protected void initView() {
        mBinding.setView(this);
        mBinding.setIsEditMode(false);

        AutoNextLineLinearLayout autoNext = mBinding.vAutoNext;

        mSeekHistoryBeans = UserManage.getSeekHistory();

        moduleIsShow(mSeekHistoryBeans);

        for (SeekHistoryBean historyBean : mSeekHistoryBeans) {
            autoNext.addView(getView(historyBean));
        }

        autoNext.setOnItemClickListener(new AutoNextLineLinearLayout.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    // 删除Item点击时 触感震动
                    VibratorUtils.start(mContext);

                    // 编辑模式 删除操作
                    try {
                        mSeekHistoryBeans.remove(autoNext.indexOfChild(view));
                        autoNext.removeView(view);
                    } catch (Throwable throwable) {
                        clearHisItemViewAnim();
                    }
                } else {
                    // 普通模式 执行搜索逻辑
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        LiveEventBus.get(EventPath.SEEK_HIS_ITEM_CLICK).post(textView.getText().toString().trim());
                    }
                }
            }

            @Override
            public void onLongClick(View view) {
                addHisItemViewAnim();
            }
        });

        // 对搜索框进行点击监听 用于如果在编辑状态下 点击输入框进行输入 历史ItemView还在抖动场景
        LiveEventBus.get(EventPath.SEEK_HIS_SEEK_VIEW_CLICK).observe((LifecycleOwner) mContext, o -> saveNewHisItemData());
    }

    /**
     * 清除动画抽取
     * 同时兼任改变模式逻辑
     */
    public void clearHisItemViewAnim() {
        // 调用清除后 模式即为普通模式
        isEditMode = false;
        AutoNextLineLinearLayout autoNext = mBinding.vAutoNext;
        if (autoNext.getChildCount() > 0) {
            for (int i = 0; i < autoNext.getChildCount(); i++) {
                View childAt = autoNext.getChildAt(i);
                childAt.clearAnimation();
                textViewAddCloseImage((TextView) childAt);
            }
        }
        mBinding.setIsEditMode(isEditMode);

        if (mOnEditModeListener != null) {
            mOnEditModeListener.clickCall(isEditMode);
        }
    }

    /**
     * 添加动画抽取
     * 同时兼任改变模式逻辑
     */
    public void addHisItemViewAnim() {
        // 调用添加后 模式即为编辑模式
        isEditMode = true;
        AutoNextLineLinearLayout autoNext = mBinding.vAutoNext;
        if (autoNext.getChildCount() > 0) {
            for (int i = 0; i < autoNext.getChildCount(); i++) {
                View childAt = autoNext.getChildAt(i);
                startShakeByView(childAt, 4, 800);
                textViewAddCloseImage((TextView) childAt);
            }
        }
        mBinding.setIsEditMode(isEditMode);

        if (mOnEditModeListener != null) {
            mOnEditModeListener.clickCall(isEditMode);
        }
    }

    /**
     * 添加编辑动画化
     *
     * @param view
     * @param shakeDegrees 抖动幅度 数值越大，幅度越大
     * @param duration     数值越大 抖动效果越柔和
     */
    private void startShakeByView(View view, float shakeDegrees, long duration) {
        Animation rotateAnim = new RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(duration / 10);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(10);
        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(rotateAnim);
        view.startAnimation(smallAnimationSet);
        smallAnimationSet.setAnimationListener(new AnimationRepeatListener());
    }

    @SuppressLint("SetTextI18n")
    private View getView(SeekHistoryBean bean) {
        TextView textView = new TextView(mContext);
        AutoNextLineLinearLayout.LayoutParams layoutParams = new AutoNextLineLinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DensityUtils.dp(8), DensityUtils.dp(8), 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(DensityUtils.dp(8), DensityUtils.dp(4), DensityUtils.dp(8), DensityUtils.dp(4));
        textView.setText(bean.text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);
        textView.setTextColor(Res.color(R.color.black));
        textView.setBackgroundResource(R.drawable.h_adapter_left_select_bg_on);
        return textView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void textViewAddCloseImage(TextView textView) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_close);
        // setBounds(top, bottom, right, left)  方向是  上，下，右，左；在想要设置的第几个坑里填上对应的图片，其他位置填上null即可，该参数若未设置，本人的测试是无法显示图片的
        drawable.setBounds(0, 0, isEditMode ? DensityUtils.dp(12) : 0, isEditMode ? DensityUtils.dp(12) : 0);
        textView.setCompoundDrawables(null, null, isEditMode ? drawable : null, null);
        textView.setCompoundDrawablePadding(isEditMode ? DensityUtils.dp(2) : 0);
    }

    /**
     * [完成]点击
     */
    public OnBindingClickCall onAccomplishClick = this::saveNewHisItemData;

    /**
     * 保存修改后的历史数据
     * 当清除动画后 就应该调用此方法
     * 也就是说 只要进入了编辑模式修改了数据 当退出编辑模式时 所删除的item也会自动保存 即使不点完成
     */
    public void saveNewHisItemData() {
        // 点击完成 更新缓存数据
        UserManage.setSeekHistory(mSeekHistoryBeans);
        // 最后一条删除时 隐藏搜索历史模块
        moduleIsShow(mSeekHistoryBeans);
        // 去除动画
        clearHisItemViewAnim();
    }

    /**
     * 添加搜索历史
     * 去重
     * 倒序（新加入的和末尾的进行替换）
     *
     * @param bean
     */
    public void addInputText(SeekHistoryBean bean) {
        if (bean == null || mSeekHistoryBeans == null) {
            return;
        }

        // 对搜索历史进行遍历，判断是否为第一次搜索
        int i;
        for (i = 0; i < mSeekHistoryBeans.size(); i++) {
            if (TextUtils.equals(mSeekHistoryBeans.get(i).text, bean.text)) {
                break;
            }
        }

        // 这里分别对View和数据源同时控制，也可以只控制数据源，让View与数据源绑定 然后刷新View 这样写不好
        if (i == mSeekHistoryBeans.size()) { // 第一次搜索
            mSeekHistoryBeans.add(0, bean);
            mBinding.vAutoNext.addView(getView(bean), 0);
        } else { // 非第一次搜索

            // 这里判断，如果i==0 则为刚已经搜索了一次 此条目已经出于第一位，所以不用换位置
            if (i != 0) {
                SeekHistoryBean bean1 = new SeekHistoryBean(bean.text, bean.isShowClose);

                mBinding.vAutoNext.removeView(mBinding.vAutoNext.getChildAt(i));
                mSeekHistoryBeans.remove(i);

                // 这里只能先删除 后添加  否则如果连续搜索两次 记录会有问题
                mSeekHistoryBeans.add(0, bean1);
                mBinding.vAutoNext.addView(getView(bean1), 0);
            }
        }
        UserManage.setSeekHistory(mSeekHistoryBeans);
        moduleIsShow(mSeekHistoryBeans);
    }

    private void moduleIsShow(List<SeekHistoryBean> list) {
        mBinding.llGroup.setVisibility(list == null || list.size() == 0 ? GONE : VISIBLE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        VibratorUtils.stopVibrator();
    }
}