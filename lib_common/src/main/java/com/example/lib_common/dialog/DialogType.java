package com.example.lib_common.dialog;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 王鑫哲 on 2022/4/14 上午 10:45
 * E-mail: User_wang_178@163.com
 * Ps:
 */

@Retention(RetentionPolicy.SOURCE) //指定注解仅存在与源码中,不加入到 class 文件中
//@Target({ElementType.PARAMETER}) //@Target 说明了Annotation所修饰的对象范围 用于描述参数
@StringDef(value = {DialogType.TYPE_A, DialogType.TYPE_B, DialogType.TYPE_C, DialogType.TYPE_D, DialogType.TYPE_E,
        DialogType.TYPE_F, DialogType.TYPE_G,
})
public @interface DialogType {
    String TYPE_A = "type_a";
    String TYPE_B = "type_b";
    String TYPE_C = "type_c";
    String TYPE_D = "type_d";
    String TYPE_E = "type_e";
    String TYPE_F = "type_f";
    String TYPE_G = "type_g";
}
