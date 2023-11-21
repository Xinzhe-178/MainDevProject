package com.example.networkpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.bean.HomeBottomShopBean;
import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.lib_utils.VibratorUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentHomeLayoutBinding;
import com.example.networkpro.databinding.ShopCarViewLayoutBinding;
import com.example.networkpro.ui.activity.PlaceOrderActivity;
import com.example.networkpro.ui.activity.ShopsSettingActivity;
import com.example.networkpro.ui.adapter.HomeRecyclerLeftAdapter;
import com.example.networkpro.ui.adapter.HomeRecyclerRightAdapter;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/11/5 上午 11:00
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeViewModel extends BaseViewModel {
    /**
     * 左边adapter默认选中下标
     * 当前右边展示第几个列表的数据
     */
    private int mLeftDefSelectIndex = 0;
    /**
     * 左边列表Adapter
     */
    private HomeRecyclerLeftAdapter mLeftAdapter;
    /**
     * 左边列表List
     */
    private List<HomeRecyclerGroupBean> mLeftListData;
    /**
     * 右边列表Adapter
     */
    private HomeRecyclerRightAdapter mRightAdapter;
    /**
     * 右边列表List
     */
    private List<HomeRecyclerGroupBean.RightGroup> mRightListData;
    /**
     * 当前购物车List
     */
    private List<HomeRecyclerGroupBean.RightGroup> mShopCarListData;

    @SuppressLint("StaticFieldLeak")
    private FragmentActivity mActivity;

    private FragmentHomeLayoutBinding mHomeLayoutBinding;

    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    /**
     * 初始化列表
     *
     * @param binding
     * @param activity
     * @param homeAllData
     */
    public void initRecycler(FragmentHomeLayoutBinding binding, FragmentActivity activity, List<HomeRecyclerGroupBean> homeAllData) {
        mActivity = activity;
        mHomeLayoutBinding = binding;

        mLeftListData = homeAllData == null ? UserManage.getHomeAllData() : homeAllData;
        LogUtils.PrintE("mLeftListData-->", new Gson().toJson(mLeftListData));

        //如果第一次安装 则购物车并无数据  就会导致mShopCarListData为null
        mShopCarListData = UserManage.getShopCarData();

        //初始化购物车view显示数据更新
        mHomeLayoutBinding.bottomView.setModel(mShopCarListData);

        if (mLeftListData == null || mLeftListData.size() == 0) {
            setBindingHintAddViewIsShow(true);
            initRecyclerListener();
            initShopCarView();
            return;
        }
        initRecyclerViewRelevant();
        initShopCarView();
    }

    private void initShopCarView() {
        mHomeLayoutBinding.vShopCar.setBottomView(mHomeLayoutBinding.bottomView);
        mHomeLayoutBinding.vShopCar.setViewModel(this);

        // 这个是初始化购物车数据 必须在向购物车View赋值ViewModel后才可调用，否则为空 取不到
        mHomeLayoutBinding.vShopCar.update(getShopCarListData());
        // 默认购物车不显示
        setShopCarViewIsShow(false);
    }

    /**
     * 设置hint[去添加]显示隐藏
     *
     * @param isShow
     */
    private void setBindingHintAddViewIsShow(boolean isShow) {
        mHomeLayoutBinding.rvLeft.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mHomeLayoutBinding.rvRight.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mHomeLayoutBinding.tvToAdd.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化列表相关
     */
    private void initRecyclerViewRelevant() {
        initLeftAdapter();
        initRightAdapter();
        initRecyclerListener();
    }

    /**
     * 跳转商品详情逻辑
     *
     * @param data
     */
    private void jumpShopDetails(HomeRecyclerGroupBean.RightGroup data) {
        mHomeLayoutBinding.vShopCar.jumpShopDetails(data);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initRecyclerListener() {
        /**
         * 左边适配器点击处理
         */
        if (mLeftAdapter != null) {
            mLeftAdapter.addItemClickListener((data, holder, pos) -> {
                VibratorUtils.start(mActivity);
                if (mLeftListData != null && mLeftListData.size() != 0) {
                    if (pos == mLeftDefSelectIndex) {
                        // TODO: 2021/11/30  代表两次点击的是同样一个条目  这里不进行任何操作  后期需要可以在此处添加逻辑
//                        toast("点击同个item!!不刷新");
                    } else {
                        List<HomeRecyclerGroupBean> dataBean = mLeftAdapter.getDataBean();
                        dataBean.get(mLeftDefSelectIndex).isSelect = false;
                        dataBean.get(pos).isSelect = true;
                        mLeftAdapter.notifyDataSetChanged();
                        // TODO: 2022/2/4 此处可选择只刷新变更的item 但 效果没有刷新全部的好
//                         mLeftAdapter.notifyItemChanged(pos);
//                         mLeftAdapter.notifyItemChanged(mLeftDefSelectIndex);
                        mLeftDefSelectIndex = pos;
                        refreshRightData();
                    }
                }
            });

//            // 左边item长按删除操作
//            mLeftAdapter.addItemLongClickListener((data, holder, pos) -> DialogManage.init(mActivity, DialogType.TYPE_D).setDataD("确定删除该类型?", data.leftTitle, new OnDialogMainClickCall() {
//                @Override
//                public void mainCall() {
//                    mLeftListData.remove(data);
//                    mLeftAdapter.removeItem(pos);
//                    UserManage.setHomeAllData(mLeftListData);
//
//                    // 左边删除有个小不同就是需要将默认选中改下位置 这里改为第一个 并要刷新对应展示逻辑
//                    if (mLeftListData.size() > 0) {
//                        mLeftDefSelectIndex = 0;
//                        refreshRightData();
//                    }
//                    ToastUtils.show("删除成功");
//                }
//            }));
        }

        // 进入详情页
        if (mRightAdapter != null) {
            mRightAdapter.addItemClickListener((data, holder, pos) -> jumpShopDetails(data));

            // 右边Item长按删除操作
            mRightAdapter.addItemLongClickListener((data, holder, pos) -> DialogManage.init(mActivity, DialogType.TYPE_D).setDataD("确定删除该商品?", data.rightTitle, () -> {
                // 取到当前右边的数据集合
                List<HomeRecyclerGroupBean.RightGroup> rightGroup = mLeftListData.get(mLeftDefSelectIndex).RightGroup;
                // 取到当前右边的item
                HomeRecyclerGroupBean.RightGroup rightItemData = rightGroup.get(rightGroup.indexOf(data));
                // 将当前删除的item从右边集合中移除
                rightGroup.remove(rightItemData);
                // 将当前适配器展示的item移除 会有动画  体验更好些(以上操作并不会影响当前展示的效果 只是为了保持数据同步)
                mRightAdapter.removeItem(pos);
                LogUtils.PrintE("mLeftListData", "json->" + JSON.toJSONString(mLeftListData));
                ToastUtils.show("删除成功");
            }));
        }

        /**
         * 点击Tab 列表滚到顶部
         */
        LiveEventBus.get(EventPath.HOME_FRAGMENT_RETURN_TOP).observe(mActivity, o -> {
            if (o instanceof Integer) {
                int pos = Integer.parseInt(o.toString());
                if (pos == 0) {
                    mHomeLayoutBinding.rvLeft.scrollToPosition(0);
                }
            }
        });

        /**
         * 添加商品后更新数据
         * 如果mLeftListData.size==0 则为第一次安装(之前没有数据)
         * 上面逻辑  如果数据为空 return 则适配器并没有进行初始化
         * 需要做判断 已经初初始化了正常刷新就好
         */
        LiveEventBus.get(EventPath.UPDATE_ADD_REFRESH_HOME_DATA).observe(mActivity, o -> {
            List<HomeRecyclerGroupBean> list = (List<HomeRecyclerGroupBean>) o;
            if (mLeftListData.size() == 0) {
                mLeftListData = list;
                setBindingHintAddViewIsShow(false);
                initRecyclerViewRelevant();
            } else {
                setHaveDataShowState(true);
                RefreshData(list);
            }
        });

        /**
         * 清除商品信息
         * 将左右两个列表隐藏 [去添加]显示出来
         * 购物车数据其实已经清空了 只是需要刷新展示即可 以下方式可最快实现效果
         */
        LiveEventBus.get(EventPath.CLEAR_ALL_SHOP_DATA).observe(mActivity, o -> {
            setHaveDataShowState(false);
            mHomeLayoutBinding.bottomView.setModel(new HomeBottomShopBean(0, 0));
            mShopCarListData.clear();
        });

        mHomeLayoutBinding.bottomView.setOnCommitListener(s -> {
            // 购物车隐藏
            setShopCarViewIsShow(false);

            String shopName = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_NAME);
            String shopAddress = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);
            if (TextUtils.isEmpty(shopName) || TextUtils.isEmpty(shopAddress)) {
                Bundle bundle = new Bundle();
                bundle.putString("key", s);
                bundle.putString(Const.ShopsSettingConst.FINISH_IS_JUMP_ORDER_PAGE, "true");
                JumpUtils.jump(ShopsSettingActivity.class, bundle);
            } else {
                // 进入下单页面
                Bundle bundle = new Bundle();
                bundle.putString("key", s);
                JumpUtils.jump(PlaceOrderActivity.class, bundle);
            }
        });
    }

    /**
     * 设置首页View 有无数据显示还是隐藏
     */
    private void setHaveDataShowState(boolean isHaveData) {
        mHomeLayoutBinding.rvLeft.setVisibility(isHaveData ? View.VISIBLE : View.GONE);
        mHomeLayoutBinding.rvRight.setVisibility(isHaveData ? View.VISIBLE : View.GONE);
        mHomeLayoutBinding.tvToAdd.setVisibility(isHaveData ? View.GONE : View.VISIBLE);
    }

    /**
     * 右边适配器
     */
    private void initRightAdapter() {
        mHomeLayoutBinding.rvRight.setLayoutManager(new LinearLayoutManager(mActivity));
        mRightAdapter = new HomeRecyclerRightAdapter(BR.model, this);
        mHomeLayoutBinding.rvRight.setAdapter(mRightAdapter);
        refreshRightData();
    }

    /**
     * 刷新右边适配器数据
     */
    private void refreshRightData() {
        mRightListData = mLeftListData.get(mLeftDefSelectIndex).RightGroup;
        mRightAdapter.update(mRightListData);
    }

    /**
     * 初始化左边适配器
     */
    private void initLeftAdapter() {
        mHomeLayoutBinding.rvLeft.setLayoutManager(new LinearLayoutManager(mActivity));
        mLeftAdapter = new HomeRecyclerLeftAdapter(BR.model);
        mHomeLayoutBinding.rvLeft.setAdapter(mLeftAdapter);

        //初始化默认选中
        int pos = ShareData.getShareIntData(Const.Data.HOME_LEFT_ADAPTER_SELECT);

        if (mLeftListData != null && mLeftListData.size() > 0) {
            mLeftListData.get(pos).isSelect = true;
        }
        mLeftAdapter.update(mLeftListData);
        mLeftDefSelectIndex = pos;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveAdapterSelectIndex();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveAdapterSelectIndex();
        VibratorUtils.stopVibrator();
    }

    /**
     * 关闭页面时 缓存选中的状态
     * 经测
     * 当关闭activity时 onPause-->onDestroy
     * 直接关闭后台时 切换到后台 onDestroy
     */
    private void saveAdapterSelectIndex() {
        ShareData.setShareIntData(Const.Data.HOME_LEFT_ADAPTER_SELECT, mLeftDefSelectIndex);
        UserManage.setShopCarData(mShopCarListData);
        UserManage.setHomeAllData(mLeftListData);
    }

    /**
     * 点击右边适配器的条目[add]点击  将当前点击的商品添加进购物车中
     */
    public OnBindingClickParamsCall<HomeRecyclerGroupBean.RightGroup> onIvAddClick = new OnBindingClickParamsCall<HomeRecyclerGroupBean.RightGroup>() {
        @Override
        public void clickCall(HomeRecyclerGroupBean.RightGroup data) {
            // 给右边的食品设置数量 这个数量要和购物车的同步
            data.count = data.count + 1;

            HomeRecyclerGroupBean.RightGroup group = new HomeRecyclerGroupBean.RightGroup();
            group.rightTitle = data.rightTitle;
            group.imageUrl = data.imageUrl;
            group.desc = data.desc;
            group.stockCount = data.stockCount;
            group.price = data.price;
            group.originalPrice = data.originalPrice;
            group.isSeckill = data.isSeckill;
            group.childImageData = data.childImageData;
            group.count = data.count;
            group.indexOfLeft = mLeftDefSelectIndex;
            // 添加数据时 倒叙，后添加的加在第一位

            if (group.count == 1) {
                // 当前商品数量为1 正常添加
                mShopCarListData.add(0, group);
            } else {
                // 不为1 找到这个商品 数量+1即可
                for (HomeRecyclerGroupBean.RightGroup shopCarListDatum : mShopCarListData) {
                    if (TextUtils.equals(shopCarListDatum.rightTitle, group.rightTitle)) {
                        shopCarListDatum.count += 1;
                        break;
                    }
                }
            }

            mHomeLayoutBinding.bottomView.setModel(mShopCarListData);
            ToastUtils.show("添加成功");
        }
    };

    /**
     * 购物车步进器状态更新
     * 这个方法只刷新首页数据状态
     */
    public void carStepperStateRefresh(int carDataPos, int type, int leftIndex) {
        // 取到当前左边的item
        HomeRecyclerGroupBean right = mLeftListData.get(leftIndex);
        // 取到当前点击的购物车item
        HomeRecyclerGroupBean.RightGroup cardData = getShopCarListData().get(carDataPos);
        // 遍历右边列表所有数据
        for (HomeRecyclerGroupBean.RightGroup rightGroup : right.RightGroup) {
            // 根据名字判断是否是当前商品
            if (TextUtils.equals(rightGroup.rightTitle, cardData.rightTitle)) {
                // 如果是当前商品 则将数量根据类型加或减
                rightGroup.count = type == 1 ? rightGroup.count - 1 : rightGroup.count + 1;
                // 不考虑重复情况 找到后就退出循环
                break;
            }
        }
    }

    /**
     * 刷新数据 该方法只用在编辑数据后进行刷新
     * 只需要刷新左右两个适配器即可
     * 默认选中不需要刷新 因为每次添加的数据都在末尾
     * 购物车不需要刷新
     * 此处直接将修改后的数据传过来即可 不用这边再去读缓存了
     * 注意 需要设置默认选中
     *
     * @param list
     */
    private void RefreshData(List<HomeRecyclerGroupBean> list) {
        mLeftListData = list;
        mLeftListData.get(mLeftDefSelectIndex).isSelect = true;
        mLeftAdapter.update(mLeftListData);
        mRightAdapter.update(mLeftListData.get(mLeftDefSelectIndex).RightGroup);
    }

    /**
     * 设置购物车View显示/隐藏
     *
     * @param isShow
     */
    public void setShopCarViewIsShow(boolean isShow) {
        mHomeLayoutBinding.vShopCar.setVisibility(isShow ? View.VISIBLE : View.GONE);

        // 弹出时设置背景是否为圆角
        if (isShow) {
            mHomeLayoutBinding.bottomView.setBackgroundColor(Res.color(R.color.pink));
        } else {
            mHomeLayoutBinding.bottomView.setBackgroundDrawable(Res.Drawable(R.drawable.home_bottom_shop_view_con_bg));
        }
    }

    public void showShopCarPopupWindow() {
        // 点击底部显示/隐藏购物车 如果当前为显示，再次点击则为隐藏 反之
        if (mHomeLayoutBinding.vShopCar.getVisibility() == View.VISIBLE) {
            setShopCarViewIsShow(false);
            return;
        }

        if (mShopCarListData == null || mShopCarListData.size() == 0) {
            ToastUtils.show("购物车为空哦");
            return;
        }

        setShopCarViewIsShow(true);

        List<HomeRecyclerGroupBean.RightGroup> list = new ArrayList<>(mShopCarListData);
        if (list.size() > 0) {
            ShopCarViewLayoutBinding binding = mHomeLayoutBinding.vShopCar.getBinding();
            if (binding != null) {
                binding.tvDefHint.setVisibility(View.GONE);
            }
        }
        mHomeLayoutBinding.vShopCar.update(list);
    }

    public List<HomeRecyclerGroupBean.RightGroup> getShopCarListData() {
        return mShopCarListData;
    }

    public void setShopCarListData(List<HomeRecyclerGroupBean.RightGroup> list) {
        mShopCarListData = list;
    }
}
