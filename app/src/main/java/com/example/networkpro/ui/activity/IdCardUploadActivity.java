package com.example.networkpro.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_utils.LogUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityIdCardUploadLayoutBinding;
import com.example.networkpro.viewmodel.IdCardUploadViewModel;

/**
 * Created by 王鑫哲 on 2024/5/19 21:26
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class IdCardUploadActivity extends BaseMvvmActivity<ActivityIdCardUploadLayoutBinding, IdCardUploadViewModel> {

    /**
     * 正面
     */
    private final int TYPE_FRONT_CODE = 100;
    /**
     * 反面
     */
    private final int TYPE_OPPOSITE_CODE = 101;

    private Uri mImageFrontUri;
    private Uri mImageOppositeUri;

    @Override
    protected void initView() {
        mTopBar.setTitle("证件上传");
    }

    @Override
    protected void initListener() {
        mBinding.ivFront.setOnClickListener(view -> openAlbum(TYPE_FRONT_CODE));
        mBinding.ivOpposite.setOnClickListener(view -> openAlbum(TYPE_OPPOSITE_CODE));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_id_card_upload_layout;
    }

    @Override
    public Class<IdCardUploadViewModel> onBindViewModel() {
        return IdCardUploadViewModel.class;
    }

    /**
     * 打开相册
     *
     * @param typeCode
     */
    private void openAlbum(int typeCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, typeCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相册返回的数据
        Log.e(this.getClass().getName(), "Result:" + data.toString());
        if (data != null) {
            // 得到图片的全路径
            Uri uri = data.getData();
            setImageUri(requestCode, uri);
            LogUtils.PrintD(getClass().getName() + "onActivityResult-> imageUri: " + uri);
        }
    }

    private void setImageUri(int typeCode, Uri uri) {
        switch (typeCode) {
            case TYPE_FRONT_CODE:
                mImageFrontUri = uri;
                mBinding.ivFront.setImageURI(uri);
                break;
            case TYPE_OPPOSITE_CODE:
                mImageOppositeUri = uri;
                mBinding.ivOpposite.setImageURI(uri);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.PrintE(getClass().getName() + "-> onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.PrintE(getClass().getName() + "-> onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.PrintE(getClass().getName() + "-> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.PrintE(getClass().getName() + "-> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.PrintE(getClass().getName() + "-> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.PrintE(getClass().getName() + "-> onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.PrintE(getClass().getName() + "-> onRestart");
    }
}