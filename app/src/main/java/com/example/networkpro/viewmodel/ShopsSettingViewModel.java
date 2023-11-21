package com.example.networkpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_utils.KeyboardUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.databinding.ActivityShopsSettingBinding;

/**
 * Created by 王鑫哲 on 2023/8/22 10:29 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ShopsSettingViewModel extends BaseViewModel {

    private ActivityShopsSettingBinding mBinding;

    public ShopsSettingViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("StaticFieldLeak")
    private EditText mEditText;

    /**
     * 店铺名称点击
     */
    public OnBindingClickCall onShopClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            mEditText = null;
            // 获取店铺名称
            String shopName = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_NAME);

            DialogManage.init(ContextManager.getTopActivity(), DialogType.TYPE_G).setDataG("店铺名称会在下单时展示", editText -> {
                mEditText = editText;
                // 设置最大输入字符
                TextUtils.setEditTextMaxLength(mEditText, 30);
                mEditText.setHint("请输入店铺名称");
                mEditText.setText(shopName);
                // 弹窗弹出时，并弹出软键盘
                KeyboardUtils.show(mEditText);
                // 将光标挪动到末尾
                mEditText.setSelection(TextUtils.getTextLength(mEditText));
            }, () -> {
                // 点击确认
                String text = TextUtils.getText(mEditText);
                // 输入为空时不对其进行设置
                if (TextUtils.isEmpty(text)) {
                    ToastUtils.show("输入为空，设置无效");
                } else {
                    mBinding.tvShop.setText(text);
                    // 保存店铺
                    ShareData.setShareStringData(Const.ShopsSettingConst.SHOP_NAME, text);
                    ToastUtils.show("店铺名称设置成功");
                }
            });
        }
    };

    /**
     * 店铺地址点击
     */
    public OnBindingClickCall onShopAddressClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            mEditText = null;
            // 获取店铺地址
            String shopAddress = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);

            DialogManage.init(ContextManager.getTopActivity(), DialogType.TYPE_G).setDataG("店铺地址会在下单时展示", editText -> {
                mEditText = editText;
                // 设置最大输入字符
                TextUtils.setEditTextMaxLength(mEditText, 30);
                mEditText.setHint("请输入店铺地址");
                mEditText.setText(shopAddress);
                // 弹窗弹出时，并弹出软键盘
                KeyboardUtils.show(mEditText);
                // 将光标挪动到末尾
                mEditText.setSelection(TextUtils.getTextLength(mEditText));
            }, () -> {
                // 点击确认
                String text = TextUtils.getText(mEditText);
                // 输入为空时不对其进行设置
                if (TextUtils.isEmpty(text)) {
                    ToastUtils.show("输入为空，设置无效");
                } else {
                    mBinding.tvShopAddress.setText(text);
                    // 保存店铺
                    ShareData.setShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS, text);
                    ToastUtils.show("店铺地址设置成功");
                }
            });
        }
    };

    public void setBinding(ActivityShopsSettingBinding binding) {
        mBinding = binding;
    }
}
