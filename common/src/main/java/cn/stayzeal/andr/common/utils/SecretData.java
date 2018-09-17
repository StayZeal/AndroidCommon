package cn.stayzeal.andr.common.utils;


import android.content.Context;

public class SecretData {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }


    public static String getImeiFilePath() {
        // /mnt/sdcard/.android/.system/.deviceid
        String filePathInSd = new String(new char[]{46, 97, 110, 100, 114,
                111, 105, 100, 47, 46, 115, 121, 115, 116, 101, 109, 47, 46,
                100, 101, 118, 105, 99, 101, 105, 100});
        return FileUtil.join(new FileUtil(mContext).getSdcardPath(),
                filePathInSd);
    }

    public static String getPassWord() {
        return new String(new char[]{20, 14, 8, 28, 20, 14, 8, 28});
    }

}
