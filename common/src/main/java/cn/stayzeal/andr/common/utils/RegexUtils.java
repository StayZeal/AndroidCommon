package cn.stayzeal.andr.common.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式验证工具
 *
 */
public final class RegexUtils {
    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>
     *               移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *               </p>
     *               <p>
     *               联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *               </p>
     *               <p>
     *               电信的号段：133、153、180（未启用）、189
     *               </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 以字母开头，长度在6~15之间，只能包含字符、数字和下划线
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd6_15(String pwd) {
        String regex = "^[a-zA-Z0-9]\\w{5,14}$";
        return Pattern.matches(regex, pwd);
    }
}