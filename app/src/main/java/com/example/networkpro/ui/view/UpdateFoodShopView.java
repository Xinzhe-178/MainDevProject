package com.example.networkpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_easyphotos.models.album.entity.Photo;
import com.example.lib_utils.EditTextUtils;
import com.example.lib_utils.InputMoney;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.UpdateFoodShopViewLayoutBinding;
import com.example.networkpro.ui.adapter.UpdateFoodShopParListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/2/9 下午 02:34
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class UpdateFoodShopView extends BaseFrameLayout<UpdateFoodShopViewLayoutBinding> {
    /**
     * 默认商品封面
     */
    private final String defShopImage = "https://p2.ssl.qhimgs1.com/t01db233b8d80f25bf2.gif";

    private OnBindingClickParamsCall<HomeRecyclerGroupBean.RightGroup> mOnSaveClick;

    private OnBindingClickCall mCoverImageClick;

    private List<Photo> mPhotoList;

    public void setCoverImageClick(OnBindingClickCall coverImageClick) {
        mCoverImageClick = coverImageClick;
    }

    public void setOnSaveClick(OnBindingClickParamsCall<HomeRecyclerGroupBean.RightGroup> onSaveClick) {
        mOnSaveClick = onSaveClick;
    }

    public UpdateFoodShopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UpdateFoodShopView(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.update_food_shop_view_layout;
    }

    @Override
    public void initView() {
        mBinding.setShopView(this);
        initItemView();
    }

    /**
     * 初始化各条条目显示View
     */
    private void initItemView() {
        initTitleView();
        initDescView();
        initStockCountView();
        initPriceView();
        initOriginalPriceView();
    }

    /**
     * 仿 IOS 开关点击
     */
    public OnBindingClickParamsCall<Boolean> onSwitchButtonClick = mBinding::setOriginalPriceIsShow;

    /**
     * 保存点击
     */
    public OnBindingClickCall saveClick = () -> mOnSaveClick.clickCall(getInputBody());

    /**
     * 封面图片点击
     */
    public OnBindingClickCall coverImageClick = () -> mCoverImageClick.clickCall();

    private void initOriginalPriceView() {
        mBinding.setOriginalPriceIsShow(false);
        mBinding.vFive.setLeftHintText("原价");
        EditText ed = mBinding.vFive.getEt_name_center();
        ed.setHint("(最高999)");
        ed.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ed.setFilters(new InputFilter[]{new InputMoney()});
    }

    private void initPriceView() {
        mBinding.vFour.setLeftHintText("现价");
        EditText ed = mBinding.vFour.getEt_name_center();
        ed.setHint("(最高999)");
        ed.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ed.setFilters(new InputFilter[]{new InputMoney()});
    }

    private void initStockCountView() {
        mBinding.vThree.setLeftHintText("库存数量");
        EditText ed = mBinding.vThree.getEt_name_center();
        ed.setHint("(最多2位)");
        TextUtils.setEditTextMaxLength(ed, 2);
        ed.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void initDescView() {
        mBinding.vTwo.setLeftHintText("简介");
        EditText ed = mBinding.vTwo.getEt_name_center();
        ed.setHint("(最多800字)");
        TextUtils.setEditTextMaxLength(ed, 800);
    }

    private void initTitleView() {
        mBinding.vOne.setLeftHintText("名称");
        EditText ed = mBinding.vOne.getEt_name_center();
        ed.setHint("(最多15字)");
        EditTextUtils.setEditTextInputChinese(ed, 10);
    }

    /**
     * 获取输入的商品信息
     * 回调给UpdateFoodActivity
     *
     * @return
     */
    private HomeRecyclerGroupBean.RightGroup getInputBody() {
        HomeRecyclerGroupBean.RightGroup group = new HomeRecyclerGroupBean.RightGroup();
        group.rightTitle = TextUtils.isEmpty(mBinding.vOne.getCenterText()) ? "无名商品" : mBinding.vOne.getCenterText();
        group.desc = TextUtils.isEmpty(mBinding.vTwo.getCenterText()) ? "" : mBinding.vTwo.getCenterText();
        group.stockCount = Integer.parseInt(TextUtils.isEmpty(mBinding.vThree.getCenterText()) ? "0" : mBinding.vThree.getCenterText());
        group.price = Double.parseDouble(TextUtils.isEmpty(mBinding.vFour.getCenterText()) ? "0" : mBinding.vFour.getCenterText());
        group.originalPrice = Double.parseDouble(TextUtils.isEmpty(mBinding.vFive.getCenterText()) ? "0" : mBinding.vFive.getCenterText());
        group.isSeckill = mBinding.btnIsSeckill.isChecked();
        group.imageUrl = mPhotoList != null ? mPhotoList.size() > 0 ? TextUtils.isEmpty(mPhotoList.get(0).path) ? defShopImage : mPhotoList.get(0).path : defShopImage : defShopImage;

        //以下两个判断其实是必走 因为他们没有在其他地方初始化  只能为null
        if (group.childImageData == null) {
            group.childImageData = new HomeRecyclerGroupBean.RightGroup.ChildImageData();
        }

        if (group.childImageData.mImageLists == null) {
            group.childImageData.mImageLists = new ArrayList<>();
        }
        group.childImageData.mImageLists.addAll(mBinding.viewListView.getSelectImageData());
        return group;
    }

    /**
     * 选择照片后设置给展示的View
     * 隐藏[上传封面] View
     *
     * @param photos
     */
    public void setCoverImageUrl(List<Photo> photos) {
        mPhotoList = photos;
        mBinding.setCoverImage(mPhotoList.get(0).uri);
        mBinding.tvCoverImageHint.setVisibility(GONE);
    }

    public void setActivity(Activity activity) {
        mBinding.viewListView.setActivity(activity);
    }

    /**
     * 设置为初始状态
     * 如果添加一个商品成功后 未退出页面 紧接着添加的话  上次输入的内容未清空
     * 所以 在点击保存的时候调用此方法 将View 设置为初始化状态
     */
    public void setInitialSate() {
        mBinding.setCoverImage(null);
        mBinding.tvCoverImageHint.setVisibility(VISIBLE);

        // 清空添加详情页图片View
        UpdateFoodShopParListAdapter adapter = mBinding.viewListView.getAdapter();
        if (adapter != null) {
            ArrayList<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> list = new ArrayList<>();
            list.add(new HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList("", true));
            adapter.update(list);
        }

        mBinding.vOne.setCenterText("");
        mBinding.vTwo.setCenterText("");
        mBinding.vThree.setCenterText("");
        mBinding.vFour.setCenterText("");
        mBinding.vFive.setCenterText("");
        mBinding.btnIsSeckill.setChecked(false);
    }
}
