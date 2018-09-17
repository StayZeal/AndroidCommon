package cn.stayzeal.andr.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


public class ImageShowUtil {

    public static void show(Context context, ImageView imageView, String imgurl, int loadingResID, int errorResID) {
        Glide
                .with(context)
                .load(imgurl)
                .placeholder(loadingResID)
                .error(errorResID)
                .crossFade()
                .dontAnimate()
                .into(imageView);
    }

    public static void showwithoutAni(Context context, ImageView imageView, String imgurl, int loadingResID, int errorResID) {
        Glide
                .with(context)
                .load(imgurl)
                .placeholder(loadingResID)
                .error(errorResID)
                .dontAnimate()
                .into(imageView);
    }

    public static void show(Context context, ImageView imageView, int ResId, int loadingResID, int errorResID) {
        Glide
                .with(context)
                .load(ResId)
                .asBitmap()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(imageView);
    }

    public static void showRoundImage(final Context context, final ImageView imageView, String url, int loadingResID, int errorResID) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void showRoundImage(final Context context, final ImageView imageView, int resID, int loadingResID, int errorResID) {
        Glide.with(context)
                .load(resID)
                .asBitmap()
                .centerCrop()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    public static void showGifImage(Context context, ImageView imageView, String imgurl, int loadingResID, int errorResID) {
        Glide
                .with(context)
                .load(imgurl)
                .asGif()
                .placeholder(loadingResID)
                .error(errorResID)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void showGifImage(Context context, ImageView imageView, int ResID, int loadingResID, int errorResID) {
        Glide
                .with(context)
                .load(ResID)
                .asGif()
                .error(errorResID)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
