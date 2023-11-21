package com.example.lib_common.view.anim;

import android.view.animation.Animation;

/**
 * Created by 王鑫哲 on 2023/3/27 21:47
 * E-mail: User_wang_178@163.com
 * Ps: Anim重复播放
 */
public class AnimationRepeatListener implements Animation.AnimationListener {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animation.reset();
        animation.setAnimationListener(this);
        animation.start();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
