package com.example.lib_common.dialog;

import android.content.Context;

import androidx.annotation.LayoutRes;

import com.example.lib_common.R;
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

/**
 * Created by 王鑫哲 on 2022/4/14 上午 10:47
 * E-mail: User_wang_178@163.com
 * Ps: 封装全局Dialog管理器
 */
public class DialogManage {

    private final Context mContext;

    @DialogType
    private final String mType;

    private CommonDialog mDialog;

    private DialogManage(Context context, @DialogType String type) {
        mContext = context;
        mType = type;
        initSwitch();
    }

    public static DialogManage init(Context context, @DialogType String type) {
        return new DialogManage(context, type);
    }

    /**
     * 根据所传不同的type展示不同的弹窗
     */
    private void initSwitch() {
        switch (mType) {
            case DialogType.TYPE_A:
                extractDialogShowType(R.layout.dialog_type_a_layout);
                break;
            case DialogType.TYPE_B:
                extractDialogShowType(R.layout.dialog_type_b_layout);
                break;
            case DialogType.TYPE_C:
                extractDialogShowType(R.layout.dialog_type_c_layout);
                break;
            case DialogType.TYPE_D:
                extractDialogShowType(R.layout.dialog_type_d_layout);
                break;
            case DialogType.TYPE_E:
                extractDialogShowType(R.layout.dialog_type_e_layout);
                break;
            case DialogType.TYPE_F:
                extractDialogShowType(R.layout.dialog_type_f_layout);
                break;
            case DialogType.TYPE_G:
                extractDialogShowType(R.layout.dialog_type_g_layout);
                break;
        }
    }

    /**
     * 创建不同的弹窗逻辑抽取
     */
    private void extractDialogShowType(@LayoutRes int layoutId) {
        if (mDialog == null) {
            mDialog = new CommonDialog(mContext, layoutId, mType);
        }
        mDialog.build().show();
    }

    public void setDataA(String content, OnDialogMainClickCall mainClickCall) {
        setDataA(content, null, mainClickCall, () -> mDialog.dismiss(), null);
    }

    public void setDataA(String content, OnDialogInitBindingCall<DialogTypeALayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataA(content, onInitCall, mainClickCall, auxiliaryClickCall, dismissCall);
    }

    public void setDataB(String content, OnDialogMainClickCall mainClickCall) {
        setDataB(content, null, mainClickCall, null);
    }

    public void setDataB(String content, OnDialogInitBindingCall<DialogTypeBLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataB(content, onInitCall, mainClickCall, dismissCall);
    }

    public void setDataC(String title, String content, OnDialogMainClickCall mainClickCall) {
        setDataC(title, content, null, mainClickCall, null);
    }

    public void setDataC(String title, String content, OnDialogInitBindingCall<DialogTypeCLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataC(title, content, onInitCall, mainClickCall, dismissCall);
    }

    public void setDataD(String title, String content, OnDialogMainClickCall mainClickCall) {
        setDataD(title, content, null, mainClickCall, () -> mDialog.dismiss(), null);
    }

    public void setDataD(String title, String content, OnDialogInitBindingCall<DialogTypeDLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataD(title, content, onInitCall, mainClickCall, auxiliaryClickCall, dismissCall);
    }

    public void setDataE(String title, String link, String content, OnDialogMainClickCall mainClickCall) {
        setDataE(title, link, content, null, mainClickCall, null);
    }

    public void setDataE(String title, String link, String content, OnDialogInitBindingCall<DialogTypeELayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataE(title, link, content, onInitCall, mainClickCall, dismissCall);
    }

    public void setDataF(String title, String link, String content, OnDialogMainClickCall mainClickCall) {
        setDataF(title, link, content, null, mainClickCall, () -> mDialog.dismiss(), null);
    }

    public void setDataF(String title, String link, String content, OnDialogInitBindingCall<DialogTypeFLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataF(title, link, content, onInitCall, mainClickCall, auxiliaryClickCall, dismissCall);
    }

    public void setDataG(String title, onDialogEditTextCall editTextCall, OnDialogMainClickCall mainClickCall) {
        setDataG(title, editTextCall, null, mainClickCall, () -> mDialog.dismiss(), null);
    }

    public void setDataG(String title, onDialogEditTextCall editTextCall, OnDialogInitBindingCall<DialogTypeGLayoutBinding> onInitCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall) {
        if (mDialog == null) return;
        mDialog.setDataG(title, editTextCall, onInitCall, mainClickCall, auxiliaryClickCall, dismissCall);
    }
}
