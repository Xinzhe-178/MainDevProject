package com.example.networkpro.manage;

import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.example.lib_common.consts.CardDialogConst;
import com.example.networkpro.ui.view.dialog.CardListTopDialog;
import com.example.networkpro.ui.view.dialog.HomeCardDialog;

/**
 * Created by 王鑫哲 on 2022/8/27 15:11
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CardDialogManage {

    private static String mType;

    private static FragmentActivity mActivity;

    private CardListTopDialog mDialog;

    private CardDialogManage() {
        switchInit();
    }

    public CardListTopDialog getDialog() {
        return mDialog;
    }

    public static CardDialogManage init(FragmentActivity activity, String type) {
        mActivity = activity;
        mType = TextUtils.isEmpty(type) ? CardDialogConst.HOME_TYPE : type;
        return new CardDialogManage();
    }

    private void switchInit() {
        switch (mType) {
            case CardDialogConst.HOME_TYPE:
                mDialog = new HomeCardDialog(mActivity);
                break;
        }
    }
}
