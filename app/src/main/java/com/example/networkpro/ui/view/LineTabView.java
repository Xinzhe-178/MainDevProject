package com.example.networkpro.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.lib_common.Interfac.OnPageChangeListener;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/7/12 5:26 下午
 * E-mail: User_wang_178@163.com
 * Ps: 自定义TabLayout
 * https://www.it610.com/article/1291989298224111616.htm
 */
public class LineTabView extends LinearLayout {

    private float tab_width;

    private float tab_height;

    private float def_text_size;

    private float sel_text_size;

    private int def_text_color;

    private int sel_text_color;

    private boolean sel_is_bold;

    private boolean isScroll;

    private final Context mContext;

    private final List<TextView> mViewList = new ArrayList<>();

    private OnTabClickListener mOnTabClickListener;

    private ViewPager mViewPage;

    private HorizontalScrollView mScrollView;


    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    public LineTabView(Context context) {
        this(context, null);
    }

    public LineTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LineTabView);
        def_text_size = typedArray.getDimension(R.styleable.LineTabView_ltv_text_def_size, 14f);
        sel_text_size = typedArray.getDimension(R.styleable.LineTabView_ltv_text_sel_size, 18f);
        def_text_color = typedArray.getColor(R.styleable.LineTabView_ltv_def_text_color, mContext.getColor(R.color.gray));
        sel_text_color = typedArray.getColor(R.styleable.LineTabView_ltv_sel_text_color, mContext.getColor(R.color.black));
        sel_is_bold = typedArray.getBoolean(R.styleable.LineTabView_ltv_sel_is_bold, true);
        tab_width = typedArray.getDimension(R.styleable.LineTabView_ltv_tab_width, DensityUtils.dp(60));
        tab_height = typedArray.getDimension(R.styleable.LineTabView_ltv_tab_height, DensityUtils.dp(40));
        isScroll = typedArray.getBoolean(R.styleable.LineTabView_ltv_is_scroll, false);
        typedArray.recycle();

        if (isScroll) {
            mScrollView = new HorizontalScrollView(mContext, null, 0);
            // 取消进度bar 如果该View是new出来的 则需要调用三个参数的构造方法
            mScrollView.setVerticalScrollBarEnabled(false);
            LayoutParams params = new LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
            mScrollView.setLayoutParams(params);
        }
    }

    public void setViewPage(ViewPager viewPage) {
        mViewPage = viewPage != null ? viewPage : new ViewPager(mContext);
        assert viewPage != null;
        viewPage.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setCurrentItem(position);
            }
        });
    }

    public void setCurrentItem(int index) {
        if (index < 0 || index > mViewList.size()) {
            ToastUtils.show("Tab设置有误！");
            return;
        }

        for (TextView view : mViewList) {
            view.setTextSize(def_text_size);
            view.getPaint().setFakeBoldText(false);
            view.setTextColor(def_text_color);
        }

        TextView view = mViewList.get(index);
        view.setTextSize(sel_text_size);
        view.setTextColor(sel_text_color);
        view.getPaint().setFakeBoldText(sel_is_bold);

        if (isScroll) {
            refreshScroll(index);
        }
    }

    public void setAllCurrentItem(int index) {
        setCurrentItem(index);
        mViewPage.setCurrentItem(index);
    }

    private void refreshScroll(int index) {
        //手机屏幕宽度
        int mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        //Tab左侧与父控件边距
        int left = mViewList.get(index).getLeft();
        //Tab宽度
        int width = mViewList.get(index).getMeasuredWidth();
        //计算水平需要滚动距离
        int toX = left + width / 2 - mScreenWidth / 2;
        //滚动
        mScrollView.smoothScrollTo(toX, 0);
    }

    public void setTabs(String... tabTitles) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));

        for (int i = 0; i < tabTitles.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.MATCH_PARENT);
            params.width = (int) tab_width;
            params.height = (int) tab_height;
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(params);
            textView.setText(tabTitles[i]);
            textView.setTextSize(def_text_size);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(def_text_color);

            if (isScroll) {
                linearLayout.addView(textView);
            } else {
                addView(textView);
            }
            mViewList.add(textView);

            int position = i;
            textView.setOnClickListener(view1 -> {
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onClick(position);
                }
                setCurrentItem(position);
                mViewPage.setCurrentItem(position);
            });
        }

        if (isScroll) {
            mScrollView.addView(linearLayout);
            addView(mScrollView);
        }
    }

    public interface OnTabClickListener {
        void onClick(int pos);
    }
}
