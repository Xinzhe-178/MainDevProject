package com.example.lib_utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 * Created by 王鑫哲 on 2022/11/29 21:16
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class KeyboardUtils {

    public static void show(@NonNull EditText editText) {
        editText.postDelayed(() -> {
            editText.requestFocus();
            getManager().showSoftInput(editText, 0);
        }, 200);
    }

    public static void hide(@NonNull EditText editText) {
        getManager().hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private static InputMethodManager getManager() {
        Context context = UtilApplication.getInstance().getApplicationContext();
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
