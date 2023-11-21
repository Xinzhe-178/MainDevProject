package com.example.lib_common.view.smartrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.CusRecyclerView;
import com.example.lib_utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Objects;

/**
 * Created by 王鑫哲 on 2022/2/26 下午 02:17
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SmartRecyclerView extends SmartRefreshLayout {

    private Context mContext;

    private CusRecyclerView mRecyclerView;

    private CusRefreshHeaderView mBaseHeaderView;

    private OnBindingClickParamsCall<RecyclerView> mPresTrainCallBack;

    public void setPresTrainCallBack(OnBindingClickParamsCall<RecyclerView> presTrainCallBack) {
        mPresTrainCallBack = presTrainCallBack;
    }

    public SmartRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initRecyclerViewAll();
        initSmartRefreshLayout();
    }

    private void initSmartRefreshLayout() {
        // 设置下拉刷新时显示的View
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBaseHeaderView = new CusRefreshHeaderView(mContext);
        mBaseHeaderView.setLayoutParams(layoutParams);
        addView(mBaseHeaderView, 0);
    }

    private void initRecyclerViewAll() {
        mRecyclerView = new CusRecyclerView(mContext);
        //移除RecyclerView默认上划/下拉头部/底部蓝色效果
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(layoutParams);
        addView(mRecyclerView);

        /**
         * newState有三个状态
         * SCROLL_STATE_IDLE = 0 停止滚动
         * SCROLL_STATE_DRAGGING = 1 正在被外部拖拽,一般为用户正在用手指滚动
         * SCROLL_STATE_SETTLING = 2 自动滚动开始
         * 手指拖动列表滚动时 先会走一次 newState = 1  拖动过程中走int dx, int dy 松手走newState = 0
         * 预加载设置为手指拖动开始&结束&自动滚动开始 故为onScrollStateChanged
         * onScrolled 在列表处于运动状态 就会一直调用
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    int lastPos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    //获取recycle人View当前的列表总数据
                    int itemCount = Objects.requireNonNull(mRecyclerView.getAdapter()).getItemCount();
//                    LogUtils.PrintE("onScrollStateChanged-->def", "itemCount = " + itemCount + "  lastPos = " + lastPos);
                    if (itemCount > 5 && itemCount - 5 == lastPos && mPresTrainCallBack != null) {
                        mPresTrainCallBack.clickCall(recyclerView);
//                        LogUtils.PrintE("onScrollStateChanged-->start", "itemCount = " + itemCount + "  lastPos = " + lastPos);
                    }
                }
            }
        });
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public CusRecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
