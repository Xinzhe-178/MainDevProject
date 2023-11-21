package com.example.lib_utils;

import android.text.InputFilter;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 王鑫哲 on 2022/9/3 21:42
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class EditTextUtils {

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            if (source.equals(" ")) return "";
            else return null;
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public static void setEditTextInputChinese(EditText editText, int maxLength) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String speChat = "[^\u4E00-\u9FA5]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxLength)});
    }

    public static void setEditTextInputChinese(EditText editText) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String speChat = "[^\u4E00-\u9FA5]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
