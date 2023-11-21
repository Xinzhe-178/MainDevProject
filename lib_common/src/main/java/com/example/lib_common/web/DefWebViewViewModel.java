package com.example.lib_common.web;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lib_common.consts.Const;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.view.guide.GuideHelper;
import com.example.lib_common.view.guide.GuideHelperType;
import com.example.lib_common.web.webcusview.WebCusFavoriteView;
import com.example.lib_utils.ShareData;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2022/4/3 上午 11:31
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class DefWebViewViewModel extends BaseViewModel {
    public DefWebViewViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public View getCusView(String cusViewType, Object cusData) {
        switch (cusViewType) {
            case Const.CommonWebViewPageConst.CUS_VIEW_FAVORITE:
                WebCusFavoriteView cusFavoriteView = new WebCusFavoriteView(mContent, cusData);
                // 是否弹出诱导 没有弹出过为false
                boolean isShowGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.CUS_VIEW_FAVORITE);
                if (!isShowGuide) {
                    GuideHelper.getInstance().showGuide(GuideHelperType.CUS_VIEW_FAVORITE, cusFavoriteView.getBinding().ivFavorite);
                }
                return cusFavoriteView;
        }
        return null;
    }
}
