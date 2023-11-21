package com.example.networkpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.utils.GlideEngine;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_easyphotos.EasyPhotos;
import com.example.lib_easyphotos.callback.SelectCallback;
import com.example.lib_easyphotos.models.album.entity.Photo;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.databinding.MasterViewZoomLayoutBinding;
import com.example.networkpro.ui.activity.SeekInputActivity;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by 王鑫哲 on 2021/11/12 下午 05:43
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class MasterViewModel extends BaseViewModel {

    private MasterViewZoomLayoutBinding mZoomLayoutBinding;

    public MasterViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    /**
     * 搜索点击
     */
    public OnBindingClickCall onSeekClick = () -> {
        JumpUtils.jump(SeekInputActivity.class);
    };

    /**
     * 头像点击
     */
    public OnBindingClickCall onAvatarClick = () -> {
        EasyPhotos.createAlbum(ContextManager.getTopActivity(), GlideEngine.getInstance().isShowCamera(), false, GlideEngine.getInstance())
                //如果需要拍照 必须设置FileProvider
                .setFileProviderAuthority("Com.NetWorkPro.Camera.Private.FileProvider")
                .setUseWidth(false)
                .setPuzzleMenu(false)
                .setGif(true)
                .setCleanMenu(false)
                .setVideo(false)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        String userAvatar = photos.get(0).uri.toString();
                        UserManage.setUserAvatar(userAvatar);
                        mZoomLayoutBinding.setUserAvatar(userAvatar);
                        setBg();

                        // 更新头像发起通知
                        LiveEventBus.get(EventPath.UPDATE_USER_AVATAR).post(userAvatar);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.show("操作取消");
                    }
                });
    };

    public void initZoomBinging(MasterViewZoomLayoutBinding binding) {
        mZoomLayoutBinding = binding;
        setBg();
    }

    /**
     * 设置高斯模糊背景
     */
    private void setBg() {
        GlideUtils.setLordBlurImageView(mZoomLayoutBinding.ivBg, mZoomLayoutBinding.getUserAvatar());
    }
}
