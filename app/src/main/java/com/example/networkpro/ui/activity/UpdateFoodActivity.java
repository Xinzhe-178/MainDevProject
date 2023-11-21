package com.example.networkpro.ui.activity;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_utils.GestureListener;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityUpdateFoodBinding;
import com.example.networkpro.viewmodel.UpdateFoodViewModel;

/**
 * Created by 王鑫哲 on 2022/2/4 下午 03:13
 * E-mail: User_wang_178@163.com
 * Ps: http://www.taodudu.cc/news/show-4847296.html?action=onClick
 */
public class UpdateFoodActivity extends BaseMvvmActivity<ActivityUpdateFoodBinding, UpdateFoodViewModel> {
    //定义滑动的最小距离
    private static final int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;

    @Override
    protected void initView() {
        mTopBar.setTitle("编辑商品").setTitleColor(R.color.white);
        mViewModel.initSelect(mBinding, this);
        mBinding.setViewModel(mViewModel);
        mBinding.viewUpdateContent.setActivity(this);

        initSlideListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_food;
    }

    @Override
    public Class<UpdateFoodViewModel> onBindViewModel() {
        return UpdateFoodViewModel.class;
    }

    /**
     * 重写onTouchEvent返回一个gestureDetector的屏幕触摸事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 初始化滑动监听，目前只处理左滑和右滑
     */
    private void initSlideListener() {
        gestureDetector = new GestureDetector(this, new GestureListener(MIN_DISTANCE, new GestureListener.GestureListenerCall() {
            @Override
            public void onLeftSlide() {
                selectClick();
            }

            @Override
            public void onRightSlide() {
                selectClick();
            }
        }));
    }

    /**
     * 左右滑动处理 选中交换
     */
    private void selectClick() {
        Boolean selectLeft = mBinding.getIsSelectLeft();
        mViewModel.onSelectClick.clickCall(!selectLeft);
    }
}
