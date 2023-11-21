package com.example.lib_utils;

import android.text.InputFilter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 王鑫哲 on 2022/1/5 下午 05:34
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class TextUtils {
    public static String getText(EditText editText) {
        return editText == null ? "" : editText.getText().toString().trim();
    }

    public static int getTextLength(EditText editText) {
        return editText == null ? 0 : editText.getText().toString().trim().length();
    }

    public static int getTextLength(TextView textView) {
        return textView == null ? 0 : textView.getText().toString().trim().length();
    }

    public static String getText(TextView textView) {
        return textView == null ? "" : textView.getText().toString();
    }

    public static boolean isEmpty(EditText editText) {
        return editText == null || isEmpty(editText.getText().toString().trim());
    }

    public static boolean isEmpty(TextView textView) {
        return textView == null || isEmpty(textView.toString());
    }

    public static boolean isEmpty(String s) {
        return s == null || (s.equals(""));
    }

    public static void setEditTextMaxLength(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        return android.text.TextUtils.equals(a, b);
    }
}
