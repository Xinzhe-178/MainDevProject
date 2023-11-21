package com.example.lib_common.web.webcusview;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.lib_bean.BaseObjBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.Interfac.PresetsNetCallBack;
import com.example.lib_common.R;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.databinding.WebCusFavoriteViewLayoutBinding;
import com.example.lib_common.manage.WanAndroidApiManager;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;

/**
 * Created by 王鑫哲 on 2023/5/24 11:20 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
@SuppressLint("ViewConstructor")
public class WebCusFavoriteView extends BaseFrameLayout<WebCusFavoriteViewLayoutBinding> {

    private RecyclerBean mData;

    public WebCusFavoriteView(@NonNull Context context, Object cusData) {
        super(context);
        if (cusData instanceof RecyclerBean) {
            mData = (RecyclerBean) cusData;

            if (!TextUtils.isEmpty(mData.cusViewDataKey)) {
                switch (mData.cusViewDataKey) {
                    case Const.CommonWebViewPageConst.CUS_VIEW_DATA_HOME_LIST:
                        setFavImageState(false);
                        break;
                    case Const.CommonWebViewPageConst.CUS_VIEW_DATA_FAVORITE_LIST:
                        setFavImageState(true);
                        break;
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.web_cus_favorite_view_layout;
    }

    @Override
    protected void initView() {
        mBinding.setView(this);
    }

    public OnBindingClickCall mOnFavClick = this::onFavClick;

    private void onFavClick() {
        if (mData != null && !TextUtils.isEmpty(mData.cusViewDataKey)) {
            switch (mData.cusViewDataKey) {
                case Const.CommonWebViewPageConst.CUS_VIEW_DATA_HOME_LIST:
                    WanAndroidApiManager.getInstance().onAddFavArticleApiCall(mData.id, new PresetsNetCallBack.DefNetCallTwo<BaseObjBean<Object>>() {
                        @Override
                        public void onSuccess(BaseObjBean<Object> result) {
                            super.onSuccess(result);
                            ToastUtils.show("收藏成功");
                            setFavImageState(true);

                            // 调换类型，成功后为相反逻辑，设置ID是因为收藏和移除收藏 调用的ID值一样但字段不一样，这里相互赋值
                            mData.cusViewDataKey = Const.CommonWebViewPageConst.CUS_VIEW_DATA_FAVORITE_LIST;
                            mData.originId = String.valueOf(mData.id);
                        }
                    });
                    break;
                case Const.CommonWebViewPageConst.CUS_VIEW_DATA_FAVORITE_LIST:
                    WanAndroidApiManager.getInstance().onRemoveFavArticleApiCall(Integer.parseInt(mData.originId), new PresetsNetCallBack.DefNetCallTwo<BaseObjBean<Object>>() {
                        @Override
                        public void onSuccess(BaseObjBean<Object> result) {
                            super.onSuccess(result);
                            ToastUtils.show("取消收藏成功");
                            setFavImageState(false);

                            // 调换类型，成功后为相反逻辑，设置ID是因为收藏和移除收藏 调用的ID值一样但字段不一样，这里相互赋值
                            mData.cusViewDataKey = Const.CommonWebViewPageConst.CUS_VIEW_DATA_HOME_LIST;
                            mData.id = Integer.parseInt(mData.originId);
                        }
                    });
                    break;
            }
        }
    }

    private void setFavImageState(boolean isSelect) {
        mBinding.ivFavorite.setImageResource(isSelect ? R.drawable.ic_favorite_on : R.drawable.ic_favorite_no);
    }
}