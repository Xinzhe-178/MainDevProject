package com.example.networkpro.ui.fragment;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.lib_bean.bean.BeautyBean;
import com.example.lib_common.Interfac.PresetsNetCallBack;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.fragment.BaseMvvmFragment;
import com.example.lib_network.callback.Request;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.SaveUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentBeautyLayoutBinding;
import com.example.networkpro.viewmodel.BeautyViewModel;

/**
 * Created by 王鑫哲 on 2022/7/13 4:47 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BeautyFragment extends BaseMvvmFragment<BeautyViewModel, FragmentBeautyLayoutBinding> {

    private BeautyBean mBeautyBean = new BeautyBean();

    @Override
    protected void initView() {
        mBinding.setFragment(this);
        mBinding.setIsLike(false);
        mBinding.setViewMode(mViewModel);
        mViewModel.init(mBinding);
        mBinding.setPbIsShow(false);
    }

    @Override
    protected void initListener() {
        mBinding.ivImage.setOnLongClickListener(v -> {
            DialogManage.init(mContext, DialogType.TYPE_F).setDataF("是否保存至相册？", getImagePath(), "云雾消散之际我爱你人尽皆知", () -> {
                mBinding.setPbIsShow(true);
                SaveUtils.saveImage(String.valueOf(System.currentTimeMillis()), getImagePath(), () -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            ToastUtils.show("保存成功");
                            mBinding.setPbIsShow(false);
                        });
                    }
                });
            });
            return true;
        });
    }

    /**
     * 请求网络随机图片
     */
    private void requestImage() {
        Request.net(Request.getUrlApi(Urls.HAN_XIAO_HAN_URL).getRandomBeautyImage(), new PresetsNetCallBack.DefNetCallTwo<BeautyBean>() {
            @Override
            public void onSuccess(BeautyBean result) {
                super.onSuccess(result);
                if (result != null) {
                    mBeautyBean = result;
                    loadImage();
                    mBinding.setModel(result);
                    mBinding.setIsLike(false);
                    mViewModel.setIvLikeIsClick(true);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            requestImage();
            LogUtils.PrintE("BeautyFragment-->setUserVisibleHint->显示");
        } else {
            LogUtils.PrintE("BeautyFragment-->setUserVisibleHint->隐藏");
            LoadingDialogController.getInstance().dismissDialog();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_beauty_layout;
    }

    public OnBindingClickCall onImageClick = this::requestImage;

    private void loadImage() {
        if (TextUtils.isEmpty(getImagePath())) {
            ToastUtils.show("加载失败");
            return;
        }
        LogUtils.PrintE("mBeautyBean--> " + JSON.toJSONString(mBeautyBean));
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view);
        try {
            Glide.with(mBinding.ivImage.getContext())
                    .load(getImagePath())
                    .thumbnail(0.3f)
                    .apply(options)
                    .error(R.drawable.ic_placeholder_view)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ToastUtils.show("加载失败");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(mBinding.ivImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImagePath() {
        return !TextUtils.isEmpty(mBeautyBean.imgurl) ? mBeautyBean.imgurl : "";
    }

    @Override
    public Class<BeautyViewModel> onBindViewModel() {
        return BeautyViewModel.class;
    }
}
