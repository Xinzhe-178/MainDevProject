package com.example.networkpro.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.utils.GlideEngine;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_easyphotos.EasyPhotos;
import com.example.lib_easyphotos.callback.SelectCallback;
import com.example.lib_easyphotos.models.album.entity.Photo;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.UpdateFoodShopParAdapterItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/5/14 下午 10:57
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class UpdateFoodShopParListAdapter extends BaseAdapter<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList, UpdateFoodShopParAdapterItemLayoutBinding> {

    /**
     * 最多选择的图片数量
     * 末尾+展示 和 选择图片需要设置 且两个值必须一样
     */
    private final int MAX_COUNT = 10;

    private UpdateFoodShopParAdapterItemLayoutBinding mBinding;

    /**
     * 当前展示的activity 使用必须判空
     */
    private Activity mActivity;

    /**
     * 此集合仅用于选择图片后展示中转使用
     */
    private List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> mDataLists = new ArrayList<>();

    public UpdateFoodShopParListAdapter(int BR_id) {
        super(BR_id);
    }

    public List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> getDataLists() {
        return mDataLists;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.update_food_shop_par_adapter_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<UpdateFoodShopParAdapterItemLayoutBinding> holder, int position, HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList data) {
        mBinding = holder.getBinding();

        GlideUtils.setImageUrl(mBinding.ivImage, data.isAdd ? R.drawable.icon_add : data.imgPath);

        addItemClickListener((data1, holder1, pos) -> {
            if (data1.isAdd) {
                selectImage();
            } else {
                removeItem(pos);
            }
        });
    }

    /**
     * 打开相册选择图片
     */
    private void selectImage() {
        if (mActivity == null) return;
        EasyPhotos.createAlbum(mActivity, GlideEngine.getInstance().isShowCamera(), false, GlideEngine.getInstance())
                //如果需要拍照 必须设置FileProvider
                .setFileProviderAuthority("Com.NetWorkPro.Camera.Private.FileProvider")
                .setCount(MAX_COUNT)
                .setUseWidth(false)
                .setPuzzleMenu(false)
                .setGif(true)
                .setCleanMenu(false)
                .setVideo(false)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        mDataLists.clear();
                        for (Photo photo : photos) {
                            mDataLists.add(new HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList(photo.path, false));
                        }
                        update(mDataLists);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.show("操作取消");
                    }
                });
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update(List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> dataBean) {
        if (getDataBean() != null) {
            getDataBean().clear();
            getDataBean().addAll(dataBean);
            refreshAddState();
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItem(int position) {
        if (getDataBean() != null && getDataBean().size() > 0) {
            getDataBean().remove(position);
            refreshAddState();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshAddState() {
        if (getDataBean().size() > 0 && getDataBean().size() < MAX_COUNT && !getDataBean().get(getDataBean().size() - 1).isAdd) {
            getDataBean().add(new HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList("", true));
        }
    }
}
