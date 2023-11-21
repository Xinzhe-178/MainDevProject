package com.example.lib_common.dialog;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.databinding.DialogTypeALayoutBinding;
import com.example.lib_common.databinding.DialogTypeBLayoutBinding;
import com.example.lib_common.databinding.DialogTypeCLayoutBinding;
import com.example.lib_common.databinding.DialogTypeDLayoutBinding;
import com.example.lib_common.databinding.DialogTypeELayoutBinding;
import com.example.lib_common.databinding.DialogTypeFLayoutBinding;
import com.example.lib_common.databinding.DialogTypeGLayoutBinding;
import com.example.lib_common.dialog.call.CommonDialogInterfaceGroup;
import com.example.lib_common.dialog.call.OnDialogAuxiliaryClickCall;
import com.example.lib_common.dialog.call.OnDialogDismissCall;
import com.example.lib_common.dialog.call.OnDialogMainClickCall;
import com.example.lib_common.dialog.call.onDialogEditTextCall;
import com.example.lib_utils.GlideUtils;

/**
 * Created by 王鑫哲 on 2022/4/14 下午 05:53
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CommonDialogExecutor implements CommonDialogInterfaceGroup {

    /**
     * 默认设置的内容只是针对于内容区域
     * 如有需求需要自定义按钮内容 则使用 从上到下 从左到右 依次使用 SEPARATION_FLAG 进行分离
     */
    private static final String SEPARATION_FLAG = "&&";

    private final CommonDialog mDialog;

    private OnDialogMainClickCall mMainClickCall;

    private OnDialogAuxiliaryClickCall mAuxiliaryClickCall;

    public CommonDialogExecutor(CommonDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public void onDialogACall(DialogTypeALayoutBinding binding, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, auxiliaryClickCall));
        setShowTextCorrect(content, binding.tvContent, binding.tvLeft, binding.tvRight);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogBCall(DialogTypeBLayoutBinding binding, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, null));
        setShowTextCorrect(content, binding.tvContent, binding.tvCenter);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogCCall(DialogTypeCLayoutBinding binding, String title, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, null));
        setShowTextCorrect(title.concat(SEPARATION_FLAG).concat(content), binding.tvTitle, binding.tvContent, binding.tvCenter);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogDCall(DialogTypeDLayoutBinding binding, String title, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, auxiliaryClickCall));
        setShowTextCorrect(title.concat(SEPARATION_FLAG).concat(content), binding.tvTitle, binding.tvContent, binding.tvLeft, binding.tvRight);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogECall(DialogTypeELayoutBinding binding, String title, String link, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, null));
        setShowTextCorrect(title.concat(SEPARATION_FLAG).concat(content), binding.tvTitle, binding.tvContent, binding.tvCenter);
        GlideUtils.setImageUrl(binding.ivDesc, link);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogFCall(DialogTypeFLayoutBinding binding, String title, String link, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, auxiliaryClickCall));
        setShowTextCorrect(title.concat(SEPARATION_FLAG).concat(content), binding.tvTitle, binding.tvContent, binding.tvLeft, binding.tvRight);
        GlideUtils.setImageUrl(binding.ivDesc, link);
        dialogDismissCorrect(dismissCall);
    }

    @Override
    public void onDialogGCall(DialogTypeGLayoutBinding binding, String title, onDialogEditTextCall editTextCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        binding.setDialogExecutor(setCallBack(mainClickCall, auxiliaryClickCall));
        setShowTextCorrect(title, binding.tvTitle, binding.tvLeft, binding.tvRight);
        dialogDismissCorrect(dismissCall);
        if (editTextCall != null) editTextCall.onInitEditText(binding.edContent);
    }

    /**
     * 弹窗关闭监听抽取
     */
    private void dialogDismissCorrect(OnDialogDismissCall dismissCall) {
        if (dismissCall != null) {
            mDialog.setOnDismissListener(dialogInterface -> dismissCall.onDismiss());
        }
    }

    private CommonDialogExecutor setCallBack(OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall) {
        this.mAuxiliaryClickCall = auxiliaryClickCall;
        this.mMainClickCall = mainClickCall;
        return this;
    }

    /**
     * 设置content逻辑抽取
     */
    private void setShowTextCorrect(String content, TextView... textViews) {
        if (TextUtils.isEmpty(content) || textViews == null) return;
        if (textViews.length == 0) {
            textViews[0].setText(content);
        } else {
            if (content.contains(SEPARATION_FLAG)) {
                String[] split = content.split(SEPARATION_FLAG);
                for (int i = 0; i < textViews.length; i++) {
                    for (int i1 = 0; i1 < split.length; i1++) {
                        if (i == i1) {
                            textViews[i].setText(split[i1]);
                        }
                    }
                }
            } else {
                textViews[0].setText(content);
            }
        }
    }

    /**
     * 主操作点击
     */
    public OnBindingClickCall MainClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            if (mMainClickCall != null) {
                mMainClickCall.mainCall();
                mDialog.dismiss();
            }
        }
    };

    /**
     * 辅助操作点击
     */
    public OnBindingClickCall AuxiliaryClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            if (mAuxiliaryClickCall != null) {
                mAuxiliaryClickCall.auxiliaryCall();
                mDialog.dismiss();
            }
        }
    };
}
