package com.example.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.example.lib_common.databinding.DialogTypeALayoutBinding;
import com.example.lib_common.databinding.DialogTypeBLayoutBinding;
import com.example.lib_common.databinding.DialogTypeCLayoutBinding;
import com.example.lib_common.databinding.DialogTypeDLayoutBinding;
import com.example.lib_common.databinding.DialogTypeELayoutBinding;
import com.example.lib_common.databinding.DialogTypeFLayoutBinding;
import com.example.lib_common.databinding.DialogTypeGLayoutBinding;
import com.example.lib_common.dialog.call.OnDialogAuxiliaryClickCall;
import com.example.lib_common.dialog.call.OnDialogDismissCall;
import com.example.lib_common.dialog.call.OnDialogInitBindingCall;
import com.example.lib_common.dialog.call.OnDialogMainClickCall;
import com.example.lib_common.dialog.call.onDialogEditTextCall;
import com.example.lib_utils.ToastUtils;


/**
 * Created by 王鑫哲 on 2022/4/14 上午 10:56
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CommonDialog extends EasyDialog {

    private final String type;

    private ViewDataBinding mBinding;

    public final CommonDialogExecutor mExecutor;

    public CommonDialog(@NonNull Context context, @LayoutRes int layoutId, String type) {
        super(context, layoutId);
        this.type = type;
        mExecutor = new CommonDialogExecutor(this);
    }

    @Override
    protected void initView(ViewDataBinding mBinding, Dialog dialog) {
        this.mBinding = mBinding;
    }

    public void setDataA(String content, OnDialogInitBindingCall<DialogTypeALayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_A)) return;
        mExecutor.onDialogACall((DialogTypeALayoutBinding) mBinding, content, mainClickCall, auxiliaryClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataB(String content, OnDialogInitBindingCall<DialogTypeBLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_B)) return;
        mExecutor.onDialogBCall((DialogTypeBLayoutBinding) mBinding, content, mainClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataC(String title, String content, OnDialogInitBindingCall<DialogTypeCLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_C)) return;
        mExecutor.onDialogCCall((DialogTypeCLayoutBinding) mBinding, title, content, mainClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataD(String title, String content, OnDialogInitBindingCall<DialogTypeDLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_D)) return;
        mExecutor.onDialogDCall((DialogTypeDLayoutBinding) mBinding, title, content, mainClickCall, auxiliaryClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataE(String title, String link, String content, OnDialogInitBindingCall<DialogTypeELayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_E)) return;
        mExecutor.onDialogECall((DialogTypeELayoutBinding) mBinding, title, link, content, mainClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataF(String title, String link, String content, OnDialogInitBindingCall<DialogTypeFLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_F)) return;
        mExecutor.onDialogFCall((DialogTypeFLayoutBinding) mBinding, title, link, content, mainClickCall, auxiliaryClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    public void setDataG(String title, onDialogEditTextCall editTextCall, OnDialogInitBindingCall<DialogTypeGLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (typeIsCorrect(DialogType.TYPE_G)) return;
        mExecutor.onDialogGCall((DialogTypeGLayoutBinding) mBinding, title, editTextCall, mainClickCall, auxiliaryClickCall, dismissCall);
        executionInitBinding(onInitCall);
    }

    /**
     * 类型不一致提示 抽取
     * 如果传入的类型不一致 则走到该逻辑时 弹窗已经展示了 需要关闭弹窗
     */
    private boolean typeIsCorrect(String type) {
        if (!TextUtils.equals(this.type, type)) {
            ToastUtils.show("类型有误");
            dismiss();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 执行自定义设置逻辑
     * 为了使封装的弹窗管理有更高的灵活性及可拓展性
     * 后加此方法 该逻辑会在执行者执行之后执行
     * 执行者只负责处理弹窗的点击事件传递&弹窗展示内容设置
     * 也就是说 如果通过该方法 对弹窗的内容进行单独设置 最终则会以该方法的逻辑实现
     */
    private <VDB extends ViewDataBinding> void executionInitBinding(OnDialogInitBindingCall<VDB> onInitCall) {
        if (onInitCall != null) {
            onInitCall.onInit((VDB) mBinding);
        }
    }
}
