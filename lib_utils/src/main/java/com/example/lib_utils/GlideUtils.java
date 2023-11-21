package com.example.lib_utils;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

public class GlideUtils {

    private static final int default_round = DensityUtils.dp(4);   //默认矩形圆角角度,单位:dp

    /**
     * 加载矩形圆角图片(用Glide)
     */
    @BindingAdapter(value = {"roundImageUrl", "round_angle"}, requireAll = false)
    public static void setRoundImageUrl(ImageView imageView, Object url, float angle) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transforms(new CenterCrop(), new RoundedCorners(angle > 0 ? DensityUtils.dp(angle) : default_round));
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .transition(withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.PrintE(String.format("加载图片错误：setRoundImageUrl()/%s/%s", url, e.getMessage()));
        }
    }

    /**
     * 加载矩形图片(用Glide)
     */
    @BindingAdapter(value = {"app:image_url"})
    public static void setImageUrl(ImageView imageView, Object url) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view);
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.PrintE(String.format("加载图片错误：setRoundImageUrl()/%s/%s", url, e.getMessage()));
        }
    }

    /**
     * 加载矩形图片(用Glide)
     */
    @BindingAdapter(value = {"app:image_url_local"})
    public static void setImageUrl(ImageView imageView, int url) {
        imageView.setImageResource(url);
    }

    @BindingAdapter(value = {"app:setImageResource"})
    public static void setImageResource(ImageView imageView, int url) {
        imageView.setImageResource(url);
    }

    /**
     * 加载矩形图片(用Glide)
     * 加载本地资源图 Gif
     */
    @BindingAdapter(value = {"app:image_url"})
    public static void setImageUrlNativeGif(ImageView imageView, int url) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view);
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.PrintE(String.format("加载图片错误：setRoundImageUrl()/%s/%s", url, e.getMessage()));
        }
    }

    /**
     * 加载矩形圆角图片(用Glide)
     */
//    @BindingAdapter(value = {"app:image_url_round"})
    public static void setImageDrawable(ImageView imageView, Object drawable) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view);
        try {
            Glide.with(imageView.getContext())
                    .load(drawable)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 没有占位图 没有加载失败图 加载图片
     *
     * @param imageView
     * @param drawable
     */
    @BindingAdapter(value = {"app:setImageNPEDrawable"})
    public static void setImageNPEDrawable(ImageView imageView, Object drawable) {
        Glide.with(imageView.getContext()).load(drawable).into(imageView);
    }

    /**
     * 加载圆形图片(用Glide)
     *
     * @param imageView 图片对象
     * @param imageUrl  图片url
     */
    @BindingAdapter({"app:image_url_round"})
    public static void setCircleImageUrl(ImageView imageView, Object imageUrl) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_view)
                .error(R.drawable.ic_placeholder_view)
                .circleCrop();
        try {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载加载高斯模糊图片
     */
    @BindingAdapter(value = {"setBlurImageUrl"}, requireAll = false)
    public static void setLordBlurImageView(ImageView imageView, Object url) {
        Context context = imageView.getContext();
        RequestOptions options = new RequestOptions()
                .fitCenter()
//                .placeholder(R.drawable.ic_lord_header_bg)
//                .error(R.drawable.ic_lord_header_bg)
                .dontAnimate()
                .transforms(new BlurTransformation(context));
        try {
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.PrintE(String.format("加载图片错误：setRoundImageUrl()/%s/%s", "", e.getMessage()));
        }
    }

    /**
     * 设置image旋转角度
     */
    public static void setAngleImage(ImageView imageView, @DrawableRes int imagePath, int angle) {
        Context context = imageView.getContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imagePath);
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(bitmap1);
    }

    /**
     * 通过Glide获取图片宽高
     *
     * @param context
     * @param imageUrl
     * @return
     */
    public static void getImageWh(Context context, String imageUrl, GetImageWHCallback callback) {
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                        if (callback != null) {
                            callback.call(resource.getWidth(), resource.getHeight());
                        }
                    }
                });
    }

    public interface GetImageWHCallback {
        void call(int width, int height);
    }
}
