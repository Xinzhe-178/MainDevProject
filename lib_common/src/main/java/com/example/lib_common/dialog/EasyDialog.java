package com.example.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.lib_common.R;

/**
 * Created by 王鑫哲 on 2022/4/13 下午 06:12
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class EasyDialog<VDB extends ViewDataBinding> extends Dialog {

    private final int mLayoutId;

    protected VDB mBinding;

    protected Context mContent;

    private Window mWindow;

    private int mGravity = Gravity.CENTER;

    private boolean clickWindowIsDismiss = true;

    private int width = GridLayout.LayoutParams.MATCH_PARENT;

    private int height = GridLayout.LayoutParams.WRAP_CONTENT;

    public EasyDialog setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    public EasyDialog setClickWindowIsDismiss(boolean clickWindowIsDismiss) {
        this.clickWindowIsDismiss = clickWindowIsDismiss;
        return this;
    }

    public EasyDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public EasyDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public VDB getBinding() {
        return mBinding;
    }

    public EasyDialog(@NonNull Context context, @LayoutRes int layoutId) {
        super(context, R.style.dialog);
        mContent = context;
        mLayoutId = layoutId;
    }

    public EasyDialog build() {
        //这行代码必须加  不加则会导致Dialog上方默认加个黑边 或者说是会有默认Title的位置 且必须要在设置setContentView 和设置具体宽高前设置 否则会报错
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding = inflate(mLayoutId);
        setContentView(mBinding.getRoot());
        setCanceledOnTouchOutside(clickWindowIsDismiss);
        mWindow = getWindow();
        WindowManager.LayoutParams windowAttributes = mWindow.getAttributes();
        windowAttributes.width = width;
        windowAttributes.height = height;
//        setCancelable(true);
        mWindow.setAttributes(windowAttributes);
        mWindow.setGravity(mGravity);
        initView(mBinding, this);
        initListener(mBinding, this);
        return this;
    }

    protected abstract void initView(VDB mBinding, Dialog dialog);

    protected void initListener(VDB mBinding, Dialog dialog) {

    }

    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContent), layoutId, null, false);
    }
}
