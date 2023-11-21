package com.example.networkpro.viewmodel;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_utils.ToastUtils;
import com.example.lib_utils.WallpaperUtils;
import com.example.networkpro.databinding.FragmentBeautyLayoutBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2022/7/30 1:03 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BeautyViewModel extends BaseViewModel {

    private boolean ivLikeIsClick = true;

    public void setIvLikeIsClick(boolean ivLikeIsClick) {
        this.ivLikeIsClick = ivLikeIsClick;
    }

    private FragmentBeautyLayoutBinding mBinding;

    public BeautyViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(FragmentBeautyLayoutBinding binding) {
        mBinding = binding;
    }

    public OnBindingClickCall onLikeClick = () -> {
        if (ivLikeIsClick) {
            ivLikeIsClick = false;
            mBinding.setIsLike(!mBinding.getIsLike());
            mBinding.setPbIsShow(true);

            if (mBinding.getIsLike()) {
                WallpaperUtils.setDesktopWallpaper(mBinding.getModel().imgurl, new WallpaperUtils.OnSetStateCall() {
                    @Override
                    public void setSuccess() {
                        Looper.prepare();
                        ToastUtils.show("设置壁纸成功");
                        Looper.loop();
                        ivLikeIsClick = true;
                        mBinding.setPbIsShow(false);
                    }

                    @Override
                    public void setError() {
                        Looper.prepare();
                        ToastUtils.show("设置壁纸失败");
                        Looper.loop();
                        ivLikeIsClick = true;
                        mBinding.setPbIsShow(false);
                    }
                });
            }
        }
    };
}
