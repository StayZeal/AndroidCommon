package cn.stayzeal.andr.common.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class SpannableStringUtil {

    //前景色
    public static SpannableString foreGroundColor(String text, int start, int end, int color) {
        SpannableString spanString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
}
