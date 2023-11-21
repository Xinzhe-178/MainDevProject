package com.example.networkpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.utils.GlideEngine;
import com.example.lib_easyphotos.EasyPhotos;
import com.example.lib_easyphotos.callback.SelectCallback;
import com.example.lib_easyphotos.models.album.entity.Photo;
import com.example.lib_utils.KeyboardUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityUpdateFoodBinding;
import com.example.networkpro.ui.activity.UpdateFoodActivity;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/2/4 下午 03:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class UpdateFoodViewModel extends BaseViewModel {

    private ActivityUpdateFoodBinding mBinding;

    @SuppressLint("StaticFieldLeak")
    private UpdateFoodActivity mActivity;
    /**
     * 当前左边列表的数据
     */
    private List<HomeRecyclerGroupBean> mShopList;
    /**
     * 商品选择器View
     */
    private OptionsPickerView<String> mShopSelectView;
    /**
     * 商品选择器View暂存List
     */
    private List<String> mShopSelectViewList;
    /**
     * 要添加的类型位置
     */
    private int selectPos = -1;

    public UpdateFoodViewModel(@NonNull Application application) {
        super(application);
    }

    public void initSelect(ActivityUpdateFoodBinding binding, UpdateFoodActivity activity) {
        mBinding = binding;
        mActivity = activity;
        mBinding.setIsSelectLeft(true);
        mBinding.setViewUpdateContentIsShow(false);
        initShopSelect();
        saveUpdateContents();

        //拉起相册上传图片
        mBinding.viewUpdateContent.setCoverImageClick(this::openPhoto);
    }

    /**
     * 初始化商品选择器
     */
    private void initShopSelect() {
        mShopSelectViewList = new ArrayList<>();
        mShopSelectView = new OptionsPickerBuilder(mActivity, (options1, options2, options3, v) -> {
            selectPos = options1;
            mBinding.tvSelectShop.setText("正在添加 : ".concat(mShopSelectViewList.get(options1)));
            mBinding.setViewUpdateContentIsShow(true);
        }).setLayoutRes(R.layout.update_food_select_shop_view_layout, view -> {
                    view.findViewById(R.id.tv_confirm).setOnClickListener(v -> onSexTypeConfirmClick(false));
                    view.findViewById(R.id.tv_end).setOnClickListener(v -> onSexTypeConfirmClick(true));
                }).setOutSideCancelable(true) //点击空白可关闭
                .setContentTextSize(18)
                .setItemVisibleCount(3)
                .setLineSpacingMultiplier(3)
                .setLineSpacingMultiplier(3.5f)
                .build();
    }

    private void onSexTypeConfirmClick(boolean b) {
        if (b) mShopSelectView.returnData();
        mShopSelectView.dismiss();
    }

    /**
     * 初始化类型选择器的数据
     */
    private void initShopSelectViewData() {
        if (mShopList != null) {
            if (mShopSelectViewList != null) {
                mShopSelectViewList.clear();
                for (HomeRecyclerGroupBean bean : mShopList) {
                    mShopSelectViewList.add(bean.leftTitle);
                }
                mShopSelectView.setPicker(mShopSelectViewList);
                mShopSelectView.show();
            }
        }
    }

    /**
     * 选择点击
     * flag 是否为类型
     */
    public OnBindingClickParamsCall<Boolean> onSelectClick = new OnBindingClickParamsCall<Boolean>() {
        @Override
        public void clickCall(Boolean flag) {
            KeyboardUtils.hide(mBinding.edInputType);
            mBinding.setIsSelectLeft(flag);
            if (flag) {
                mBinding.setViewUpdateContentIsShow(false);
                mBinding.tvSelectShop.setText("点击进行选择商品");
                mBinding.viewUpdateContent.setInitialSate();
            }
        }
    };

    /**
     * 选择商品点击 弹出选择View
     */
    public OnBindingClickCall onSelectShopClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            mShopList = UserManage.getHomeAllData();
            if (mShopList.size() == 0) {
                ToastUtils.show("请先添加类型哦~");
                return;
            }
            initShopSelectViewData();
        }
    };

    /**
     * 添加类型 保存 点击
     */
    public OnBindingClickCall onAddTypeClick = new OnBindingClickCall() {
        @Override
        public void clickCall() {
            String text = TextUtils.getText(mBinding.edInputType);
            if (TextUtils.isEmpty(mBinding.edInputType)) {
                ToastUtils.show("不可为空哦");
                return;
            }
            List<HomeRecyclerGroupBean> list = UserManage.getHomeAllData();
            list.add(new HomeRecyclerGroupBean(text, list.size()));

            UserManage.setHomeAllData(list);
            LiveEventBus.get(EventPath.UPDATE_ADD_REFRESH_HOME_DATA).post(list);

            mBinding.edInputType.setText("");
            KeyboardUtils.hide(mBinding.edInputType);

            ToastUtils.show("添加类型成功");
        }
    };

    /**
     * 保存编辑的商品信息
     * integer 只是接口封装  无用
     */
    @SuppressLint("CheckResult")
    private void saveUpdateContents() {
        mBinding.viewUpdateContent.setOnSaveClick(this::saveData);
    }

    private void saveData(HomeRecyclerGroupBean.RightGroup data) {
        data.childId = String.valueOf(System.currentTimeMillis());
        data.relevanceId = selectPos;
        mShopList.get(selectPos).RightGroup.add(data);

        UserManage.setHomeAllData(mShopList);
        mBinding.setViewUpdateContentIsShow(false);
        LiveEventBus.get(EventPath.UPDATE_ADD_REFRESH_HOME_DATA).post(mShopList);

        ToastUtils.show("添加商品成功");
        KeyboardUtils.hide(mBinding.edInputType);
        mBinding.tvSelectShop.setText("点击进行选择商品");
        LogUtils.PrintE("UpdateFoodViewModel", "saveData当前线程 = " + Thread.currentThread());

        mBinding.viewUpdateContent.setInitialSate();
    }

    /**
     * 打开相册选择商品封面
     */
    private void openPhoto() {
        EasyPhotos.createAlbum(mActivity, GlideEngine.getInstance().isShowCamera(), false, GlideEngine.getInstance())
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
                        mBinding.viewUpdateContent.setCoverImageUrl(photos);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.show("操作取消");
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.viewUpdateContent.setActivity(null);
    }
}
