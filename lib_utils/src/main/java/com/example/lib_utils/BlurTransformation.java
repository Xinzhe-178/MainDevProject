package com.example.lib_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.RSRuntimeException;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

/**
 * Created by GabrielHeng on 2021/1/5
 * Emails:17310551233@163.com
 * Describe:
 */
public class BlurTransformation extends BitmapTransformation {

    private static String STRING_CHARSET_NAME = "UTF-8";
    private static final String ID = "com.kevin.glidetest.BlurTransformation";
    private static Charset CHARSET = Charset.forName(STRING_CHARSET_NAME);
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

//    private static int MAX_RADIUS = 80;
//    private static int DEFAULT_DOWN_SAMPLING = 2;

    private static int MAX_RADIUS = 40;
    private static int DEFAULT_DOWN_SAMPLING = 2;

    private Context mContext;
    private BitmapPool mBitmapPool;

    private int mRadius;
    private int mSampling;

    public BlurTransformation(Context context) {
        this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool) {
        this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool, int radius) {
        this(context, pool, radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int radius) {
        this(context, Glide.get(context).getBitmapPool(), radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int radius, int sampling) {
        this(context, Glide.get(context).getBitmapPool(), radius, sampling);
    }

    public BlurTransformation(Context context, BitmapPool pool, int radius, int sampling) {
        mContext = context.getApplicationContext();
        mBitmapPool = pool;
        mRadius = radius;
        mSampling = sampling;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTrstansform, int outWidth, int outHeight) {
        int width = toTrstansform.getWidth();
        int height = toTrstansform.getHeight();
        int scaledWidth = width / mSampling;
        int scaledHeight = height / mSampling;

        Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTrstansform, 0, 0, paint);

        try {
            bitmap = RSBlur.blur(mContext, bitmap, mRadius);
        } catch (RSRuntimeException e) {
            bitmap = FastBlur.blur(bitmap, mRadius, true);
        }
        //return BitmapResource.obtain(bitmap, mBitmapPool);
        return bitmap;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlurTransformation;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

}
