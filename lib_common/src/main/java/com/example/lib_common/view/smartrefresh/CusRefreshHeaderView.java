package com.example.lib_common.view.smartrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.lib_common.R;
import com.example.lib_utils.GlideUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * Created by Administrator on 2020/6/11.
 * Email 17600284843@163.com
 * Description: 自定义 SmartRefreshLayout 刷新头
 */
public class CusRefreshHeaderView extends InternalAbstract {

    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
//    public static String REFRESH_HEADER_FINISH = "刷新成功";
//    public static String REFRESH_HEADER_FAILED = "刷新失败";

    private View mRootView;

    public CusRefreshHeaderView(Context context) {
        this(context, null);
    }

    protected CusRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected CusRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_header, this, false);
        addView(mRootView);

        ImageView iv_load = mRootView.findViewById(R.id.iv_load);
//        GlideUtils.setImageUrl(iv_load, R.drawable.ic_def_load);
//        GlideUtils.setImageUrl(iv_load, "http://photocdn.sohu.com/20151221/mp49706572_1450681308314_10.gif");
        GlideUtils.setImageUrl(iv_load, "https://img.zcool.cn/community/01645e5cc92871a801214168ee66f1.gif");
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 1000;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        String state = "";
        switch (newState) {
            case PullDownToRefresh: //下拉过程
//                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_repeat);
//                    mIvRefreshView.startAnimation(animation);
                state = REFRESH_HEADER_PULLING;
                break;
            case ReleaseToRefresh: //松开刷新
                state = REFRESH_HEADER_RELEASE;
                break;
            case Refreshing: //loading中
                state = REFRESH_HEADER_LOADING;
                break;
        }
    }
}
