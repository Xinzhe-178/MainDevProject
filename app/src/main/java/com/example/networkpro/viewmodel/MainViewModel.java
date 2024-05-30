package com.example.networkpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.lib_common.mvvm.BaseViewModel;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityMainBinding;
import com.example.networkpro.manage.fragment.FragmentSwitch;
import com.example.networkpro.ui.fragment.HomeFragment;
import com.example.networkpro.ui.fragment.MineFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 03:43
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class MainViewModel extends BaseViewModel {

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void initFragmentAndTab(FragmentManager supportFragmentManager, ActivityMainBinding binding) {
        FragmentSwitch fragmentSwitch = FragmentSwitch.getInstance();
        fragmentSwitch.init(supportFragmentManager, R.id.fl_main, 0, new HomeFragment(), new MineFragment());

        binding.navView
                .setDefTabIcons(R.drawable.icon_home_sel_def, R.drawable.icon_master_sel_def)
                .setSelTabIcons(R.drawable.icon_home_sel_sel, R.drawable.icon_master_sel_sel)
                .build();

        // 底部Tab点击监听 并设置对应Fragment状态
        binding.navView.setOnNavTabClickListener((lastIndex, currentIndex) -> {
            fragmentSwitch.switchPage(currentIndex);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentSwitch.getInstance().onDestroy();
    }
}
