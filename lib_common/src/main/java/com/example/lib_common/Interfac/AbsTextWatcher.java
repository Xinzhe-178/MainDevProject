package com.example.lib_common.Interfac;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 王鑫哲 on 2021/11/8 下午 03:34
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class AbsTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
