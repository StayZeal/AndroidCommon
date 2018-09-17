package cn.stayzeal.andr.common.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtil {


    private static final String TAG = "ImageUtil";
    public static final String HISTORY_IMAGE_PATH = "history_image";
    public static final String COMMON_IMAGE_PATH = "common_image";

    public static String getImageFullPathName(String path, String name, String suffix) {
        return path + File.separator + name + suffix;
    }

    public static boolean saveImage(String fullPathName, Bitmap bitmap) {
        File image = new File(fullPathName);
        if (!image.exists()) {
            if (!image.mkdirs()) {
                return false;
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(image);
            bitmap.compress(CompressFormat.PNG, 100, fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static Bitmap getImg(String filePath, String fileName) {
        String fullPathName = FileUtil.getCacheDir(filePath) + File.separator + fileName + ".png";
        Bitmap bitmap = BitmapFactory.decodeFile(fullPathName);
        return bitmap;

    }

    public static Bitmap getImg(String fullPathName) {
        Bitmap bitmap = BitmapFactory.decodeFile(fullPathName);
        return bitmap;

    }


    public static Bitmap getCompressImg(String fullPathName) {
        Bitmap bitmap = getImg(fullPathName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        return bitmap;
    }

    /**
     * 按照指定的width和height获取bitmap图片以供显示
     *
     * @param fullPathName
     * @param width
     * @param height
     * @return
     */
    public static Bitmap showImage(String fullPathName, int width, int height) {

        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(fullPathName, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(fullPathName, options);

        Log.v(TAG,"show image size:"+bitmap.getByteCount()) ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.v(TAG,"show image allocation size:"+bitmap.getAllocationByteCount()) ;
            ;
        }
        return bitmap;
    }

    public static int calculateInSampleSize(Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 保存图片到history_image文件夹
     *
     * @param imageUrl
     * @param videoId
     */
 /*   public static void saveHistoryImgFromUrl(String imageUrl, String videoId) {
        saveImgFromUrl(HISTORY_IMAGE_PATH, imageUrl, videoId, false);
    }*/

    public static void saveHistoryImgFromIV(ImageView imageView, String videoId) {
        saveImageFromIV(HISTORY_IMAGE_PATH, imageView, videoId);
    }

    public static String getHistoryImgPath(String videoId) {
        return getImagePath("history_image", videoId);
    }

    public static String getImagePath(String filePath, String fileName) {
        String path = FileUtil.getCacheDir(filePath) + File.separator + fileName + ".png";
//        MyLog.i(TAG, "path:" + path);
        return path;
    }

  /*  *//**
     * @param filePath
     * @param imageUrl
     * @param videoId
     * @param overWrite 文件已存在是否覆盖
     *//*
    public static void saveImgFromUrl(String filePath, String imageUrl, String videoId, boolean overWrite) {
        String path = FileUtil.getCacheDir(filePath) + File.separator + videoId + ".png";
        File file = new File(path);
        if (file.exists() && !overWrite) {//避免重复保存
            return;
        }
        DownApkTask.newInstance(file).execute(imageUrl);

    }*/


    public static void saveImageFromIV(String filePath, ImageView imageView, String videoId) {
        String path = FileUtil.getCacheDir(filePath) + File.separator + videoId + ".png";
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            if (bitmap == null) {
                return;
            }
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
