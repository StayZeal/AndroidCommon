package cn.stayzeal.andr.common.utils;


import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class TextStyleUtil {

    public static SpannableStringBuilder bold(String s, int start, int end) {

        SpannableStringBuilder style = new SpannableStringBuilder(s);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        style.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return style;
    }

    public static SpannableStringBuilder color(String s, int start, int end, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(s);
//        ForegroundColorSpan styleSpan = new ForegroundColorSpan(color);
        ForegroundColorSpan styleSpan = new ForegroundColorSpan(Color.RED);
//        style.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//前后都不包括
        style.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//在这里和上面并没有区别

        return style;
    }


}
