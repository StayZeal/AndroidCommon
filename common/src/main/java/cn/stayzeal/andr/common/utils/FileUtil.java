package cn.stayzeal.andr.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class FileUtil {

    private static Context mContext;

    public static void init(String appName, Context context) {
        RES_FOLDER_NAME = appName;
        mContext = context;
    }

    private static final String TAG = FileUtil.class.getSimpleName();
    private Context context;
    private static String RES_FOLDER_NAME;
    private final String CACHE_PATH = join(RES_FOLDER_NAME, "cache");
    private final String DOWNLOAD_PATH = join(RES_FOLDER_NAME, "download");
    private final String DATA_PATH = join(RES_FOLDER_NAME, "miniclass.data");
    private final int MIN_SD_SIZE = 5 * 1024 * 1024;
    private final static String VIDEO_PATH = "Movies";

    /**
     * 不同设备可以获取到的sdCard的Download目录
     */
    private final static String DOWNLOAD_PATH_DEFAULT = "Download";

    private final static String AVATAR_PATH = join(RES_FOLDER_NAME, "avatar");

    public FileUtil(Context context) {
        this.context = context;
    }

    public boolean sdcardIsMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public String getSdcardPath() {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        if (Build.VERSION.SDK_INT > 17 && path.startsWith("/storage/emulated")) {
            path = "/storage/emulated/legacy";
        }
        return path;
    }

    public static long getFolderSize(File folder) {
        long size = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile())
                size += file.length();
            else
                size += getFolderSize(file);
        }
        return size;
    }

    @SuppressWarnings("deprecation")
    public long getSdcardAvailableSize() {
        StatFs stat = new StatFs(getSdcardPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return blockSize * availableBlocks;
    }


    @SuppressWarnings("deprecation")
    public long getInternalAvailableSize() {
        StatFs mDataFileStats = new StatFs("/data");
        long freeStorage = (long) mDataFileStats.getAvailableBlocks()
                * mDataFileStats.getBlockSize();
        return freeStorage;
    }

    public static boolean makeDir(String dir, int mod) {
        File destDir = new File(dir);
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                return false;
            }
        }

        Process p;
        String cmd = "chmod -R " + mod + " " + dir;
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sdcardIsEnough() {
        if (sdcardIsMounted() && getSdcardAvailableSize() >= MIN_SD_SIZE) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFileExist(String path) {
        if (path == null) {
            return false;
        }
        try {
            File f = new File(path);
            if (f.exists()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void removeFile(String path) {
        if (path == null) {
            return;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatFileSize(long size) {
        return Formatter.formatFileSize(context, size);
    }

    public static String getAppSizeString(long size) {
        return String.format("%.1fMB", ((float) size) / 1048576);
    }

    public String getAvailableFilePath() {
        String filePath = join(getSdcardPath(), this.CACHE_PATH);
        if (sdcardIsEnough() && makeDir(filePath, 777)) {
            return filePath;
        } else {
            filePath = join(context.getFilesDir().getAbsolutePath(),
                    this.CACHE_PATH);
            makeDir(filePath, 777);
            return filePath;
        }
    }

    public static String getDefaultDownloadPath(String fileName) {
        String path = mContext.getExternalCacheDir().getAbsolutePath();
        MyLog.i(TAG, "path:" + path);
        if (TextUtils.isEmpty(path)) {
            path = Environment.getDownloadCacheDirectory().getAbsolutePath();
        }
        return path + File.separator + fileName;
    }

    public String getDownloadFilePath() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "filePath", Context.MODE_PRIVATE);
        String downloadFilePath = sharedPreferences.getString(
                "downloadFilePath", null);

        if (downloadFilePath == null) {
            downloadFilePath = join(getSdcardPath(), this.DOWNLOAD_PATH);
            if (!sdcardIsEnough() || !makeDir(downloadFilePath, 777)) {
                /*
                 * downloadFilePath = join(
				 * context.getFilesDir().getAbsolutePath(), this.DOWNLOAD_PATH);
				 */

                downloadFilePath = getAvailableSdPath(DOWNLOAD_PATH_DEFAULT);
                makeDir(downloadFilePath, 777);
            }

            Editor editor = sharedPreferences.edit();
            editor.putString("downloadFilePath", downloadFilePath);
            editor.commit();
        }
        return downloadFilePath;

    }

    public String getVideoFolderPath() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "folderPath", Context.MODE_PRIVATE);
        String videoFolderPath = sharedPreferences.getString("videoFolderPath",
                null);

        if (videoFolderPath == null) {
            videoFolderPath = join(getSdcardPath(), this.VIDEO_PATH);
            if (!sdcardIsEnough() || !makeDir(videoFolderPath, 777)) {
                /*
                 * videoFolderPath = join(
				 * context.getFilesDir().getAbsolutePath(), this.VIDEO_PATH);
				 */
                videoFolderPath = getAvailableSdPath(VIDEO_PATH);
                makeDir(videoFolderPath, 777);
            }

            Editor editor = sharedPreferences.edit();
            editor.putString("videoFolderPath", videoFolderPath);
            editor.commit();
        }
        return videoFolderPath;
    }

    public static String getVideoFilePath(Context context, String videoName) {
        return new FileUtil(context).getVideoFolderPath() + File.separator
                + videoName + ".mp4";
    }

    public static boolean isApkFile(String filePath) {
        return filePath.endsWith(".apk");
    }

    public static String getVideoFolderName() {
        return VIDEO_PATH;
    }

    public String getAppCollectionInfoPath() {
        String p1 = join(getSdcardPath(), this.DATA_PATH);
        String p2 = join(context.getFilesDir().getAbsolutePath(),
                this.DATA_PATH);
        String[] pathArary = {p1, p2};
        for (String path : pathArary) {
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }
        return null;
    }

    public String getNewAppcollectionInfoPath() {
        String path = null;

        if (sdcardIsEnough()) {
            path = join(getSdcardPath(), this.DATA_PATH);
        } else {
            path = join(context.getFilesDir().getAbsolutePath(), this.DATA_PATH);
        }
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return path;
    }

    public static String join(String parent, String child) {
        return parent + File.separator + child;
    }

    /**
     * 针对不同机型，不同的sd卡目录做适配。
     *
     * @return
     */
    public String getAvailableSdPath(String filePath) {
        StorageManager sm = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            String[] paths = (String[]) sm.getClass()
                    .getMethod("getVolumePaths", new Class[0]).invoke(sm, new Object[]{});

            for (String s : paths) {
                Log.e(TAG, "path: " + s);
                String fullPath = join(s, filePath);

                boolean b = makeDir(fullPath, 777);
                if (b == true) {
                    // if(getSdcardAvailableSize(s)>5)
                    Log.i(TAG, " avaiable path: " + fullPath);
                    return fullPath;
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCacheDir(String fileName) {
        String path = mContext.getCacheDir() + File.separator + fileName;
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs() == false) {
                return null;
            }
        }
        return path;
    }

    /**
     * 根据uri获取file path
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathByUri(Context context, Uri uri) {
        String fileName = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null,
                null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            fileName = cursor.getString(column_index);
        }
        cursor.close();
        return fileName;
    }

//    public static int getProgressValue(int soFarBytes, int totalBytes) {
//        return (int)( ((long)soFarBytes) * 100 / totalBytes);
//    }
//
//    public static String getFileSizeString(long bytes) {
//        return getAppSizeString(bytes);
//    }

    public static String getAvatarImagePath(Context context) {
        return new FileUtil(context).getAvailableSdPath(AVATAR_PATH) + File.separator;
    }

    public static String saveBitmapToFile(Bitmap bm, String path) {
        File myCaptureFile = new File(path, "croped.png");
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(myCaptureFile);
            bm.compress(Bitmap.CompressFormat.PNG, 95, out);
            out.flush();
            out.close();
            return path + "croped.png";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Nullable
    @SuppressLint("NewApi")
    public static String getPathByUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Nullable
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}