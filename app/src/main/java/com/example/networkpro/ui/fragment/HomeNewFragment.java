package com.example.networkpro.ui.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.fragment.BaseFragment;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentHomeNewLayoutBinding;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/7/12 5:07 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeNewFragment extends BaseFragment<FragmentHomeNewLayoutBinding> {

    private ArrayList<Fragment> mFragments;

    public ArrayList<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    protected void initView() {
        mBinding.setFragment(this);

        // 设置顶部高度，由于顶部设置了沉浸式，整体View会顶上去，所以自己写一个状态栏
        ViewGroup.LayoutParams layoutParams = mBinding.vStatusBar.getLayoutParams();
        layoutParams.height = DensityUtils.getStatusHeight();
        mBinding.vStatusBar.setLayoutParams(layoutParams);
        mBinding.vStatusBar.setBackground(Res.Drawable(R.color.default_status_bar_color));

        initViewPage();
        initTab();
    }

    private void initTab() {
        mBinding.tab.setTabs("美食", "美女", "星座");
        mBinding.tab.setCurrentItem(0);
        mBinding.tab.setViewPage(mBinding.viewpage);
    }

    private void initViewPage() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new BeautyFragment());
        mFragments.add(new ConstellationFragment());

        mBinding.viewpage.setCurrentItem(0);
        mBinding.viewpage.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_new_layout;
    }

    public OnBindingClickCall onScanClick = () -> {
        XXPermissions.with(getActivity())
                .permission(Permission.CAMERA)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            JumpUtils.jumpAndroidScan(Const.ScanType.TYPE_ANDROID_SCAN_ALL);
                        } else {
                            ToastUtils.show("部分权限被拒绝");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            ToastUtils.show("权限被永久拒绝，请手动开启权限！");
                            if (getActivity() != null) {
                                XXPermissions.startPermissionActivity(getActivity(), Permission.CAMERA);
                            }
                        } else {
                            ToastUtils.show("获取权限失败");
                        }
                    }
                });
    };
}
