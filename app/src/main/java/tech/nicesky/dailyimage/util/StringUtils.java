package tech.nicesky.dailyimage.util;

import android.text.TextUtils;

/**
* @class tech.nicesky.dailyimage.util.StringUtils
* @date on 2018/10/10-上午8:53
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description: N/A
*
*/
public class StringUtils {


    /**
     * 字符串空判断
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str) || isNull(str);
    }

    public static boolean isEmptyNoSpace(CharSequence... strs) {
        if (strs == null) {
            return true;
        }
        if (strs.length < 1) {
            return true;
        }
        boolean hasEmp = false;
        for (CharSequence c :
                strs) {
            if (isEmptyNoSpace(c)) {
                hasEmp = true;
                break;
            }
        }
        return hasEmp;
    }


    /**
     * 字符串空判断 清除了空格字符
     *
     * @param str
     * @return
     */
    public static boolean isEmptyNoSpace(CharSequence str) {
        if (isEmpty(str)) {
            return true;
        }
        str = str.toString().trim();//去除首尾半角
        if (isEmpty(str)) {
            return true;
        }
        str = str.toString().replaceAll(" ", "");//去除中间半角空格
        str = str.toString().replaceAll("　", "");//去除全角空格 :)
        return TextUtils.isEmpty(str) || isNull(str);
    }

    /**
     * 字符串空判断
     *
     * @param str
     * @return
     */
    public static boolean isNull(CharSequence str) {
        if (TextUtils.isEmpty(str)) {

            return true;
        }
        if (!TextUtils.isEmpty(str) && "null".equals(str.toString().toLowerCase())) {
            return true;
        } else
            return false;
    }
}
