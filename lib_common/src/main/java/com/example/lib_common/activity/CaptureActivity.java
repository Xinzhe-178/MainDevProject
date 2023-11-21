package com.example.lib_common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.lib_common.R;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.databinding.ActivityScannerLayoutBinding;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.viewmodel.CaptureViewModel;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ToastUtils;
import com.example.library_barcode.BitmapUtils;
import com.example.library_barcode.CameraImageGraphic;
import com.example.library_barcode.FrameMetadata;
import com.example.library_barcode.GraphicOverlay;
import com.example.library_barcode.MLKit;
import com.example.library_barcode.RealPathFromUriUtils;
import com.example.library_barcode.barcodescanner.WxGraphic;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/3/11 上午 10:04
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CaptureActivity extends BaseMvvmActivity<ActivityScannerLayoutBinding, CaptureViewModel> {
    /**
     * MLKit
     */
    private MLKit mlKit;
    /**
     * 页面进入类型
     */
    private String mType = "";
    /**
     * 如果是原生扫码  扫码类型
     */
    private String mTypeAndroid = "";
    /**
     * 相册选择图片Code
     */
    private final int PHOTO_REQUEST_CODE = 20220311;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scanner_layout;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    public void getBundle(Bundle bundle) {
        mType = bundle.getString(Const.ScanType.TYPE);
        mTypeAndroid = bundle.getString(Const.ScanType.TYPE_ANDROID);
        LogUtils.PrintE("扫码-->跳转类型--> " + "mType = " + mType + " mTypeAndroid = " + mTypeAndroid);
    }

    @Override
    public void initView() {
        boolean granted = XXPermissions.isGranted(this, Permission.CAMERA);
        if (!granted) {
            ToastUtils.show("无权限");
            finish();
        }

        mBinding.setActivity(this);
        mBinding.setIconFlashIsOpen(false);
        initMLKit();
    }

    @Override
    protected boolean setWindowsIsImmerse() {
        return true;
    }

    private void initMLKit() {
        //构造出扫描管理器
        mlKit = new MLKit(this, mBinding.previewView, mBinding.graphicOverlay);
        //初始化默认支持同时识别多个码  因为一进来默认为扫码
        mlKit.setSupportMultiple(true);
        //是否扫描成功后播放提示音和震动
        mlKit.setPlayBeepAndVibrate(true, true);
        //仅识别二维码
        new BarcodeScannerOptions
                .Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_AZTEC)
                .build();

        mlKit.setBarcodeFormats(null);
        mlKit.setOnScanListener(new MLKit.OnScanListener() {
            @Override
            public void onSuccess(List<Barcode> barcodes, @NonNull GraphicOverlay graphicOverlay, InputImage image) {
                showScanResult(barcodes, graphicOverlay, image);
            }

            @Override
            public void onFail(int code, Exception e) {
                ToastUtils.show("识别失败");
            }
        });
    }

    /**
     * 识别二维码关键代码
     *
     * @param barcodes
     * @param graphicOverlay
     * @param image
     */
    private void showScanResult(List<Barcode> barcodes, @NonNull GraphicOverlay graphicOverlay, InputImage image) {
        if (barcodes == null || barcodes.size() == 0) {
            return;
        }
        Bitmap bitmap;
        ByteBuffer byteBuffer = image.getByteBuffer();
        if (byteBuffer != null) {
            FrameMetadata.Builder builder = new FrameMetadata.Builder();
            builder.setWidth(image.getWidth())
                    .setHeight(image.getHeight())
                    .setRotation(image.getRotationDegrees());
            bitmap = BitmapUtils.getBitmap(byteBuffer, builder.build());
        } else {
            bitmap = image.getBitmapInternal();
        }
        if (bitmap != null) {
            graphicOverlay.add(new CameraImageGraphic(graphicOverlay, bitmap));
        }

        //扫描结果只有一个二维码则直接拿到二维码数据执行逻辑
        //多个二维码则设置仿微信操作
        if (barcodes.size() == 1) {
            executionScanSuccess(barcodes.get(0).getRawValue());
        } else {
            //如果不支持一次识别多个码则默认取第一个码
            if (mlKit.isSupportMultiple()) {
                for (int i = 0; i < barcodes.size(); ++i) {
                    Barcode barcode = barcodes.get(i);
                    WxGraphic graphic = new WxGraphic(graphicOverlay, barcode);
                    graphic.setColor(Color.parseColor("#FF4081"));
                    Bitmap bitmapPaint = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_default);
                    graphic.setBitmap(bitmapPaint);
                    graphic.setOnClickListener(barcode1 -> executionScanSuccess(barcode1.getRawValue()));
                    graphicOverlay.add(graphic);
                }
            } else {
                executionScanSuccess(barcodes.get(0).getRawValue());
            }
        }
        if (barcodes.size() > 0) {
            mlKit.stopProcessor();
        }
    }

    @Override
    public Class<CaptureViewModel> onBindViewModel() {
        return CaptureViewModel.class;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE && data != null && data.getData() != null) {
            String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
            if (TextUtils.isEmpty(realPathFromUri)) {
                ToastUtils.show("无法识别二维码");
                return;
            }
            LogUtils.PrintE("onActivityResult-->realPathFromUri = " + realPathFromUri);
            //从相册返回取结果即认为从相册识别 设置为不支持多张同时识别
            mlKit.setSupportMultiple(false);
            mlKit.scanningImage(realPathFromUri);
        }
    }

    /**
     * 只有两种情况 Android&H5
     * 执行扫码成功后逻辑
     */
    private void executionScanSuccess(String resultText) {
        if (TextUtils.isEmpty(resultText)) {
            ToastUtils.show("扫描出错");
            return;
        }
        if (TextUtils.equals(mType, Const.ScanType.TYPE_ANDROID)) {
            mViewModel.executeScanResult(resultText, mTypeAndroid);
            LogUtils.PrintE("扫码-->图片识别Android--> " + "mType = " + mType + " mTypeAndroid = " + mTypeAndroid + " 扫码地址 = " + resultText);
        } else {
            LogUtils.PrintE("扫码-->图片识别H5--> " + "mType = " + mType + " mTypeAndroid = " + mTypeAndroid + " 扫码地址 = " + resultText);
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Const.ScanType.INTENT_EXTRA_KEY_QR_SCAN, resultText);
            bundle.putString(Const.ScanType.TYPE, mType);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    /**
     * 左上角返回点击
     */
    public OnBindingClickCall onFinishCommand = this::finish;

    /**
     * 开/关手电筒点击
     */
    public OnBindingClickCall onIconFlashCommand = () -> {
        mlKit.switchLight();
        mBinding.setIconFlashIsOpen(!mBinding.getIconFlashIsOpen());
    };

    /**
     * 打开相册点击
     */
    public OnBindingClickCall onIconPhotoCommand = () -> {
        startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), PHOTO_REQUEST_CODE);
    };
}
