package com.example.lib_common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.example.lib_common.R;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.databinding.StepperViewLayoutBinding;
import com.example.lib_utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2021/11/8 下午 02:26
 * E-mail: User_wang_178@163.com
 * Ps: 步进器
 */
public class StepperView extends BaseFrameLayout<StepperViewLayoutBinding> {
    /**
     * 默认显示数
     */
    private int defSelectCount;
    /**
     * 最大显示数
     */
    private int maxSelectCount;
    /**
     * 最小显示数
     */
    private int minSelectCount;
    /**
     * 已到达最大时点击提示文案
     */
    private String arriveMaxHintText = "";
    /**
     * 已到达最小时点击提示文案
     */
    private String arriveMinHintText = "";
    /**
     * 已到达最小大时[+] 是否置灰
     */
    private boolean arriveMaxBtnIsGrey;
    /**
     * 已到达最小小时[+] 是否置灰
     */
    private boolean arriveMinBtnIsGrey;
    /**
     * 当前选择的数
     */
    private int selectCount;
    /**
     * 点击监听
     * 1为减 2为加
     */
    private OnBindingClickParamsCall<Integer> onClickListener;

    public void setOnClickListener(OnBindingClickParamsCall<Integer> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public StepperView(@NonNull @NotNull Context context) {
        super(context);
    }

    public StepperView(@NonNull @NotNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StepperView(@NonNull @NotNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 获取当前选择的数
     *
     * @return
     */
    public int getSelectCount() {
        return selectCount;
    }

    @Override
    public int getLayoutId() {
        return R.layout.stepper_view_layout;
    }

    @Override
    public void initView() {

    }

    private void isInputShow() {
        mBinding.tvCenter.setText(String.valueOf(defSelectCount));

        if (defSelectCount < minSelectCount || defSelectCount > maxSelectCount) {
            ToastUtils.show("<步进器>条件有误!!!点击无效");

            mBinding.tvLeft.setEnabled(false);
            mBinding.tvRight.setEnabled(false);
            return;
        }
        selectCount = defSelectCount;

        refreshBtnState();
    }

    @Override
    public void initListener() {
        mBinding.tvLeft.setOnClickListener(v -> refreshStepperState(1));
        mBinding.tvRight.setOnClickListener(v -> refreshStepperState(2));
    }

    /**
     * 刷新步进器状态
     *
     * @param flag 1: -    2: +
     */
    private void refreshStepperState(int flag) {
        if (flag == 1) {
            if (selectCount > minSelectCount) {
                selectCount -= 1;
                refreshBtnState();
            } else {
                ToastUtils.show(arriveMinHintText);
            }
        }

        if (flag == 2) {
            if (selectCount < maxSelectCount) {
                selectCount += 1;
                refreshBtnState();
            } else {
                ToastUtils.show(arriveMaxHintText);
            }
        }

        if (onClickListener != null) {
            onClickListener.clickCall(flag);
        }
    }

    public void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.StepperView);
        defSelectCount = typedArray.getInt(R.styleable.StepperView_sv_defSelectCount, 0);
        maxSelectCount = typedArray.getInt(R.styleable.StepperView_sv_maxSelectCount, 0);
        minSelectCount = typedArray.getInt(R.styleable.StepperView_sv_minSelectCount, 0);
        arriveMaxBtnIsGrey = typedArray.getBoolean(R.styleable.StepperView_sv_arriveMaxBtnIsGrey, true);
        arriveMinBtnIsGrey = typedArray.getBoolean(R.styleable.StepperView_sv_arriveMinBtnIsGrey, true);
        arriveMaxHintText = typedArray.getString(R.styleable.StepperView_sv_arriveMaxHintText);
        arriveMinHintText = typedArray.getString(R.styleable.StepperView_sv_arriveMinHintText);

        typedArray.recycle();

        isInputShow();
    }

    /**
     * 刷新加减按钮颜色状态
     */
    private void refreshBtnState() {
        mBinding.tvCenter.setText(String.valueOf(selectCount));
        selectCount = Integer.parseInt(mBinding.tvCenter.getText().toString());
        if (arriveMinBtnIsGrey) {
            mBinding.tvLeft.setTextColor(getResources().getColor(selectCount == minSelectCount ? R.color.gray : R.color.black));
        }
        if (arriveMaxBtnIsGrey) {
            mBinding.tvRight.setTextColor(getResources().getColor(selectCount == maxSelectCount ? R.color.gray : R.color.black));
        }
    }

    public void setSelectCount(int count) {
        defSelectCount = count;
        selectCount = count;
        mBinding.tvCenter.setText(String.valueOf(defSelectCount));
    }
}
