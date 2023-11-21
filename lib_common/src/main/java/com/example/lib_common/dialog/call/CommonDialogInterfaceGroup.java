package com.example.lib_common.dialog.call;

import com.example.lib_common.databinding.DialogTypeALayoutBinding;
import com.example.lib_common.databinding.DialogTypeBLayoutBinding;
import com.example.lib_common.databinding.DialogTypeCLayoutBinding;
import com.example.lib_common.databinding.DialogTypeDLayoutBinding;
import com.example.lib_common.databinding.DialogTypeELayoutBinding;
import com.example.lib_common.databinding.DialogTypeFLayoutBinding;
import com.example.lib_common.databinding.DialogTypeGLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/4/14 下午 05:56
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface CommonDialogInterfaceGroup {

    void onDialogACall(DialogTypeALayoutBinding binding, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall);

    void onDialogBCall(DialogTypeBLayoutBinding binding, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall);

    void onDialogCCall(DialogTypeCLayoutBinding binding, String title, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall);

    void onDialogDCall(DialogTypeDLayoutBinding binding, String title, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall);

    void onDialogECall(DialogTypeELayoutBinding binding, String title, String link, String content, OnDialogMainClickCall mainClickCall, OnDialogDismissCall dismissCall);

    void onDialogFCall(DialogTypeFLayoutBinding binding, String title, String link, String content, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall);

    void onDialogGCall(DialogTypeGLayoutBinding binding, String title, onDialogEditTextCall editTextCall, OnDialogMainClickCall mainClickCall, OnDialogAuxiliaryClickCall auxiliaryClickCall, OnDialogDismissCall dismissCall);

}
