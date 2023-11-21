package com.example.networkpro.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.bean.MasterFunBean;
import com.example.lib_common.consts.Const;
import com.example.networkpro.R;
import com.example.networkpro.ui.adapter.MasterFunAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/7/1 6:12 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class MasterFunView extends RecyclerView {

    private final Context mContext;

    private MasterFunAdapter mFunAdapter;

    public MasterFunView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(mContext, 4));
        mFunAdapter = new MasterFunAdapter(BR.model);
        setAdapter(mFunAdapter);
        mFunAdapter.update(getData());
    }

    private List<MasterFunBean> getData() {
        ArrayList<MasterFunBean> list = new ArrayList<>();
        list.add(new MasterFunBean(R.drawable.ic_setting, Const.MasterFunType.TYPE_SETTING, "设置"));
        list.add(new MasterFunBean(R.drawable.ic_reduce, Const.MasterFunType.TYPE_QUIT_LOGIN, "退出登录"));
        list.add(new MasterFunBean(R.drawable.ic_update, Const.MasterFunType.TYPE_UPDATE_SHOP, "编辑商品"));
        list.add(new MasterFunBean(R.drawable.ic_order, Const.MasterFunType.TYPE_ORDER_DETAIL, "订单明细"));
        list.add(new MasterFunBean(R.drawable.ic_clear, Const.MasterFunType.TYPE_CLEAR_ALL_SHOP_DATA, "清除商品"));
        list.add(new MasterFunBean(R.drawable.ic_tele_phone, Const.MasterFunType.TYPE_CONNECT_US, "联系我们"));
        list.add(new MasterFunBean(R.drawable.ic_shop_address, Const.MasterFunType.TYPE_SHOPS_SETTING, "店铺设置"));
        return list;
    }
}
