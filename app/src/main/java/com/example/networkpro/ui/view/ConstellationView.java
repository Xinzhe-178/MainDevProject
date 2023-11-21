package com.example.networkpro.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.bean.ConstellationBean;
import com.example.lib_common.Interfac.PresetsNetCallBack;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_network.callback.Request;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.BR;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ConstellationViewLayoutBinding;
import com.example.networkpro.ui.adapter.ConstellationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2023/7/6 10:54 上午
 * E-mail: User_wang_178@163.com
 * Ps: https://blog.csdn.net/qq_35605213/article/details/106476783
 */
public class ConstellationView extends BaseFrameLayout<ConstellationViewLayoutBinding> {

    /**
     * RecyclerView款度的一半 ,也就是控件中间位置到左部的距离
     */
    private int centerToLiftDistance;

    private final int CHILDVIEWSIZE = 100;

    /**
     * 当前RecyclerView一半最多可以存在几个Item
     */
    private int childViewHalfCount = 1;

    /**
     * adapter
     */
    private ConstellationAdapter mAdapter;

    private boolean isTouch = false;

    private final List<ConstellationBean.ConstellationItem> centerViewItems = new ArrayList<>();

    private final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();

    /**
     * 上一次选中的条目
     * 如果这一次选中的和上一次一样，则不刷新
     */
    private String lastItem = "";

    public ConstellationView(@NonNull Context context) {
        super(context);
    }

    public ConstellationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.constellation_view_layout;
    }

    @Override
    protected void initView() {
        mAdapter = new ConstellationAdapter(BR.model, this);
        mBinding.rvConstellation.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mBinding.rvConstellation.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                // 设置item边距
                if (itemPosition == 0) {
                    // 第一个item
                    outRect.left = DensityUtils.dp(40);
                    outRect.right = DensityUtils.dp(20);
                } else if (itemPosition == mAdapter.getItemCount() - 1) {
                    // 最后一个item
                    outRect.left = DensityUtils.dp(20);
                    outRect.right = DensityUtils.dp(40);
                } else {
                    // 中间的item
                    outRect.left = DensityUtils.dp(20);
                    outRect.right = DensityUtils.dp(20);
                }
            }
        });
        mBinding.rvConstellation.setAdapter(mAdapter);

        mBinding.rvConstellation.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mBinding.rvConstellation.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                centerToLiftDistance = mBinding.rvConstellation.getWidth() / 2;
                int childViewHeight = DensityUtils.dp(CHILDVIEWSIZE); //43是当前已知的 Item的高度
                childViewHalfCount = (mBinding.rvConstellation.getWidth() / childViewHeight + 1) / 2;

                List<ConstellationBean.ConstellationItem> data = ConstellationBean.ConstellationItem.getData();
                // 头部的空布局
                for (int j = 0; j < childViewHalfCount; j++) {
                    data.add(0, new ConstellationBean.ConstellationItem("", ""));
                }
                // 尾部的空布局
                for (int k = 0; k < childViewHalfCount; k++) {
                    data.add(new ConstellationBean.ConstellationItem("", ""));
                }
                mAdapter.update(data);
                findView();
            }
        });

        mBinding.rvConstellation.postDelayed(() -> scrollToCenter(childViewHalfCount), 300L);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void findView() {
        mBinding.rvConstellation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int fi = linearLayoutManager.findFirstVisibleItemPosition();
                    int la = linearLayoutManager.findLastVisibleItemPosition();
//                    int fi = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                    int la = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    Log.i("ccb", "onScrollStateChanged:首个item: " + fi + "  末尾item:" + la);
                    if (isTouch) {
                        isTouch = false;
                        //获取最中间的Item View
                        int centerPositionDiffer = (la - fi) / 2;
                        // 获取当前所有条目中中间的一个条目索引
                        int centerChildViewPosition = fi + centerPositionDiffer;
                        centerViewItems.clear();
                        //遍历循环，获取到和中线相差最小的条目索引(精准查找最居中的条目)
                        if (centerChildViewPosition != 0) {
                            for (int i = centerChildViewPosition - 1; i < centerChildViewPosition + 2; i++) {
                                View cView = mBinding.rvConstellation.getLayoutManager().findViewByPosition(i);
                                int viewLeft = cView.getLeft() + (cView.getWidth() / 2);
                                centerViewItems.add(new ConstellationBean.ConstellationItem(i, Math.abs(centerToLiftDistance - viewLeft)));
                            }
                            ConstellationBean.ConstellationItem centerViewItem = getMinDifferItem(centerViewItems);
                            centerChildViewPosition = centerViewItem.position;
                        }
                        scrollToCenter(centerChildViewPosition);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    recyclerView.getChildAt(i).invalidate();
                }
            }
        });

        mBinding.rvConstellation.setOnTouchListener((view, motionEvent) -> {
            isTouch = true;
            return false;
        });
    }

    public void scrollToCenter(int position) {
        position = Math.max(position, childViewHalfCount);
        position = Math.min(position, mAdapter.getItemCount() - childViewHalfCount - 1);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mBinding.rvConstellation.getLayoutManager();
        View childView = linearLayoutManager.findViewByPosition(position);
        Log.i("ccb", "滑动后中间View的索引: " + position);
        //把当前View移动到居中位置
        if (childView == null) return;
        int childVhalf = childView.getWidth() / 2;
        int childViewLeft = childView.getLeft();
        int viewCTop = centerToLiftDistance;
        int smoothDistance = childViewLeft - viewCTop + childVhalf;
        LogUtils.PrintD(getClass().getName() + "\n居中位置距离左部距离: " + viewCTop
                + "\n当前居中控件距离左部距离: " + childViewLeft
                + "\n当前居中控件的一半高度: " + childVhalf
                + "\n滑动后再次移动距离: " + smoothDistance);

        mBinding.rvConstellation.smoothScrollBy(smoothDistance, 0, decelerateInterpolator);
        mAdapter.setSelectPosition(position);
        // 请求并更新数据
        requestData(mAdapter.getDataBean().get(position).type);
    }

    /**
     * 请求并更新数据
     */
    private void requestData(String type) {
        if (TextUtils.equals(type, lastItem)) {
            return;
        }
        lastItem = type;

        Request.net(Request.getUrlApi(Urls.HAN_XIAO_HAN_URL).getConstellationData(type, "today"), new PresetsNetCallBack.DefNetCallTwo<ConstellationBean>() {
            @Override
            public void onSuccess(ConstellationBean result) {
                super.onSuccess(result);
                if (result != null && result.data != null) {
                    mBinding.setModel(result.data);
                }
            }
        });
    }

    /**
     * 计算距离中间最近的一个ItemView
     *
     * @param itemHeights
     * @return
     */
    private ConstellationBean.ConstellationItem getMinDifferItem(List<ConstellationBean.ConstellationItem> itemHeights) {
        ConstellationBean.ConstellationItem minItem = itemHeights.get(0); //默认第一个是最小差值
        for (int i = 0; i < itemHeights.size(); i++) {
            //遍历获取最小差值
            if (itemHeights.get(i).differ <= minItem.differ) {
                minItem = itemHeights.get(i);
            }
        }
        return minItem;
    }
}
