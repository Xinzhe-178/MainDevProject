package com.example.lib_common.view.guide;

import android.view.View;

import com.example.lib_common.R;
import com.example.lib_utils.Res;

/**
 * Created by 王鑫哲 on 2023/8/9 4:39 下午
 * E-mail: User_wang_178@163.com
 * Ps: https://github.com/jimmysuncpt/AndroidGuide
 */
public class GuideViewController {

    public static View getView(GuideHelperType type) {
        switch (type) {
            case HOME_BOTTOM:
                return Res.getView(R.layout.guide_home_bottom_layout);
            case HOM_BOTTOM_ORDER:
                return Res.getView(R.layout.guide_home_bottom_order_layout);
            case CUS_VIEW_FAVORITE:
                return Res.getView(R.layout.guide_cus_favorite_layout);
            case UPDATE_USER_AVATAR:
                return Res.getView(R.layout.guide_user_avatar_layout);
        }
        return null;
    }
}
