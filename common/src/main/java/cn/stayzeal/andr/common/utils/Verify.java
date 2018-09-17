package cn.stayzeal.andr.common.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class Verify {

    static int pwdLimitMin = 6, pwdLimitMax = 15;
    static int nameLinitMin = 2, nameLimitMax = 20;

    public static String verifyMobile(String mobile) {
        String showString = "";
        if (TextUtils.isEmpty(mobile)) {
            showString = "请输入手机号";
            return showString;
        }
        if (mobile.length() != 11) {
            showString = "手机号错误";
            return showString;
        }
        String reg = "^1[3-5,6,7,8]\\d{9}$";
        // String reg = "^1[34578]\\d{8}$";
        if (!Pattern.matches(reg, mobile)) {
            showString = "手机号错误";
            return showString;
        }
        return "";
    }

    public static String verifyCode(String code) {
        String showString = "";
        if (TextUtils.isEmpty(code)) {
            showString = "请输入验证码";
            return showString;
        }
        if (code.length() != 4) {
            showString = "请输入正确的验证码";
            return showString;
        }
        return "";
    }

    public static String verifyConfirmPassword(String pwd, String confirPwd) {
        String showString = "";
        if (!pwd.equals(confirPwd)) {
            showString = "两次密码输入不同,请重新输入";
            return showString;
        }
        if (TextUtils.isEmpty(confirPwd)) {
            showString = "请输入密码";
            return showString;
        }
        if (confirPwd.length() < pwdLimitMin ||
                confirPwd.length() > pwdLimitMax) {
//            showString = "密码不得小于" + pwdLimitMin + "位";
            showString = "密码长度6-15个字符";
            return showString;
        }

        return "";
    }

    public static String verifyPasssword(String pwd) {
        String showString = "";
        if (TextUtils.isEmpty(pwd)) {
            showString = "请输入账号和密码";
            return showString;
        }
//        if (pwd.length() < pwdLimitMin) {
//            showString = "密码不得小于" + pwdLimitMin + "位";
//            return showString;
//        }
        if (pwd.length() > pwdLimitMax) {
            showString = "密码不得大于" + pwdLimitMax + "位";
            return showString;
        }
        return "";
    }

    public static String verifyName(String name) {
        String showString = "";
        if (TextUtils.isEmpty(name)) {
            showString = "昵称不得为空";
            return showString;
        }
        if (name.length() < nameLinitMin) {
            showString = "昵称不得小于" + nameLinitMin + "位";
            return showString;
        }
        if (name.length() > nameLimitMax) {
            showString = "昵称不得大于" + nameLimitMax + "位";
            return showString;
        }
        return "";
    }
}
