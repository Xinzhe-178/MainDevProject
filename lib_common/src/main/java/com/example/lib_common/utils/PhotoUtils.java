package com.example.lib_common.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.lib_utils.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by 王鑫哲 on 2024/5/20 9:47
 * E-mail: User_wang_178@163.com
 * Ps: https://blog.csdn.net/qiankun_zyk/article/details/129797081
 */
public class PhotoUtils {
    public static File tempFile;
    /**
     * 这个要和 AndroidManifest provider一致
     * 他的值和 AndroidManifest 一致即可没有标准
     **/
    private static final String file_provider = "sol.client.com.fileProvider";

    /**
     * 拍照相册权限 适配android 13
     *
     * @param activity
     * @param type       1、相机请求 2、相册请求（可自己定义）
     * @param resultCode
     */
    public static void requirePermission(Activity activity, String type, int resultCode) {
        XXPermissions.with(activity)
                // 不适配分区存储应该这样写
                //.permission(Permission.MANAGE_EXTERNAL_STORAGE)android 13 废弃AndroidManifest.xml也要去掉
                // 适配分区存储应该这样写
                .permission(Permission.CAMERA)//android 13使用一下权限
//                .permission(Permission.READ_MEDIA_IMAGES)//读取照片
//                .permission(Permission.READ_MEDIA_VIDEO)//读取视频
//                .permission(Permission.READ_MEDIA_AUDIO)//读取音频（按需申请）
                .permission(Permission.Group.STORAGE)
//                .interceptor(new PermissionInterceptor())
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (allGranted) {
                            if ("1".equals(type)) {//这是自己写的工具类下面给你们贴出源码
                                PhotoUtils.getPicFromCamera(activity, resultCode);
                            } else {
                                PhotoUtils.getPicFromAlbm(activity, resultCode);
                            }
                        }

                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        OnPermissionCallback.super.onDenied(permissions, doNotAskAgain);
                        if (doNotAskAgain) {
                            ToastUtils.show("权限被拒绝！！！");
                        }
                    }
                });
    }

    /**
     * 从相机获取图片
     *
     * @param activity   上下文
     * @param resultCode 请求吗
     */
    public static void getPicFromCamera(Activity activity, int resultCode) {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(activity.getExternalFilesDir(DIRECTORY_DCIM), "/wy_head.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
//            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, file_provider, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 从相册获取图片
     *
     * @param activity   上下文
     * @param resultCode 请求吗
     */
    public static void getPicFromAlbm(Activity activity, int resultCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, resultCode);
    }

    /**
     * 保存切图到本地 DOWNLOADS-WYXY文件夹
     *
     * @param activity
     * @param name
     * @param bmp
     * @return
     */
    public static String saveImage(Activity activity, String name, Bitmap bmp) {
        File appDir = new File(activity.getExternalFilesDir(DIRECTORY_DOWNLOADS), "WYXY");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Uri getUri(Activity activity) {
        return FileProvider.getUriForFile(activity, file_provider, PhotoUtils.tempFile);
    }

    //Uri转化为Bitmap
    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getPathToUri(Activity activity, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //游标跳到首位，防止越界
        cursor.moveToFirst();
        return cursor.getString(actual_image_column_index);
    }

    public File saveFile(Bitmap bm, String fileName) {//将Bitmap类型的图片转化成file类型，便于上传到服务器
        String path = Environment.getExternalStorageDirectory() + "/wy";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myCaptureFile;

    }

    /**
     * 质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     *
     * @param bmp
     * @param file 保存到的位置
     */
    public static void qualityCompress(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int quality = 70;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(outputStream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}