package cn.stayzeal.andr.common.utils;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.weidoo.baselib.R;

public class SnackbarUtil {

    private static Buider builder = new Buider();
    public static final int NORMAL_TYPE = 0;
    public static final int WRAN_TYPE = 1;
    public static final int ERROR_TYPE = 2;

    public static void setBuilder(Buider builder) {
        SnackbarUtil.builder = builder;
    }

    public static Snackbar show(Activity activity, String text) {
        Snackbar snackbar = getSnackbar(activity, text, NORMAL_TYPE);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar show(Activity activity, String text, String actionText, View.OnClickListener listener) {
        return showType(activity, NORMAL_TYPE, text, actionText, listener);
    }

    public static Snackbar showWarn(Activity activity, String text, String actionText, View.OnClickListener listener) {
        return showType(activity, WRAN_TYPE, text, actionText, listener);
    }

    public static Snackbar showError(Activity activity, String text, String actionText, View.OnClickListener listener) {
        return showType(activity, ERROR_TYPE, text, actionText, listener);
    }

    public static Snackbar show(Activity activity, String text, String actionText, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = showType(activity, NORMAL_TYPE, text, actionText, listener);
        setCallback(snackbar, callback);
        return snackbar;
    }

    public static Snackbar showWarn(Activity activity, String text, String actionText, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = showType(activity, WRAN_TYPE, text, actionText, listener);
        setCallback(snackbar, callback);
        return snackbar;
    }

    public static Snackbar showError(Activity activity, String text, String actionText, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = showType(activity, ERROR_TYPE, text, actionText, listener);
        setCallback(snackbar, callback);
        return snackbar;
    }

    public static void setBackColor(Snackbar snackbar, int color) {
        snackbar.getView().setBackgroundColor(color);
    }

    public static void setActionTextColor(Snackbar snackbar, int color) {
        snackbar.setActionTextColor(color);
    }

    public static void setMessageColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);//获取Snackbar的message控件，修改字体颜色
        }
    }

    public static void setClickListener(Snackbar snackbar, String actionText, View.OnClickListener listener) {
        if (listener == null) {
            return;
        }
        snackbar.setAction(actionText, listener);
    }

    public static void setCallback(Snackbar snackbar, Snackbar.Callback callback) {
        if (callback == null) {
            return;
        }
        snackbar.setCallback(callback);
    }

    public static Snackbar showType(Activity activity, int type, String text, String actionText, View.OnClickListener listener) {
        Snackbar snackbar = getSnackbar(activity, text, type);
        setClickListener(snackbar, actionText, listener);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showType(Activity activity, int type, String text, String actionText, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = getSnackbar(activity, text, type);
        setClickListener(snackbar, actionText, listener);
        snackbar.show();
        setCallback(snackbar, callback);
        return snackbar;
    }

    private static Snackbar getSnackbar(Activity activity, String text, int type) {
        WeakReference<Activity> weak = new WeakReference<Activity>(activity);
        View containter = weak.get().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(containter, text, builder.duration);
        int selectColor[] = getType(type);
        setBackColor(snackbar, selectColor[0]);
        setMessageColor(snackbar, selectColor[1]);
        setActionTextColor(snackbar, selectColor[2]);
        return snackbar;
    }

    private static int[] getType(int type) {
        switch (type) {
            case NORMAL_TYPE:
                return builder.defaultColor;
            case WRAN_TYPE:
                return builder.warnColor;
            case ERROR_TYPE:
                return builder.errorColor;
            default:
                return builder.defaultColor;
        }
    }

    public static class Buider {
        public int duration = Snackbar.LENGTH_SHORT;

        public int defaultColor[] = new int[]{Color.BLUE, Color.WHITE, Color.WHITE};

        public int errorColor[] = new int[]{Color.RED, Color.WHITE, Color.WHITE};

        public int warnColor[] = new int[]{Color.YELLOW, Color.WHITE, Color.WHITE};

        public void build() {
            setBuilder(this);
        }

        public Buider setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Buider setDefaultColor(int backColor, int msgColor, int actionColor) {
            this.defaultColor[0] = backColor;
            this.defaultColor[1] = msgColor;
            this.defaultColor[2] = actionColor;
            return this;
        }

        public Buider setErrorColor(int backColor, int msgColor, int actionColor) {
            this.errorColor[0] = backColor;
            this.errorColor[1] = msgColor;
            this.errorColor[2] = actionColor;
            return this;
        }

        public Buider setWarningColor(int backColor, int msgColor, int actionColor) {
            this.warnColor[0] = backColor;
            this.warnColor[1] = msgColor;
            this.warnColor[2] = actionColor;
            return this;
        }
    }
}
