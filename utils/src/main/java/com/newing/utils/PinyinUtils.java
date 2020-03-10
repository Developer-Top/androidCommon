package com.newing.utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 创建日期：2016-10-31 on 17:33
 * 描述:
 * 作者:linlingrong
 */

public class PinyinUtils {
    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * @param inputString 含中文字符串
     * @return 拼音字符串
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (char curchar : input) {
                if (Character.toString(curchar).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                            curchar, format);
                    output += temp[0];
                } else {
                    output += Character.toString(curchar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变
     * 花花大神->hhds
     * @param chinese 汉字
     * @return 拼音
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char curchar : arr) {
            if (curchar > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, defaultFormat);
                    if (temp != null && temp.length > 0) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(curchar);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 判断字符串中是否仅包含字母和汉字
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     * @param text 字符串
     * @return 是否符合
     */
    public static boolean isLetterOrChinese(String text) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        return text.matches(regex);
    }

    /**
     * 判断字符串是否为数字或中文或字母
     * * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * * 数字：[0x30,0x39]（或十进制[48, 57]）
     * *小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     * @param str
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含字母
     * @param text 字符串
     * @return 是否符合
     */
    public static boolean isLetter(String text) {
        String regex = "^[a-zA-Z]+$";
        return text.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含汉字
     * @param text 字符串
     * @return 是否符合
     */
    public static boolean isNumber(String text) {
        String regex = "^[0-9]+$";
        return text.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含汉字
     * @param text 字符串
     * @return 是否符合
     */
    public static boolean isChinese(String text) {
        String regex = "^[\u4e00-\u9fa5]+$";
        return text.matches(regex);
    }

    public static String[] split(String text) {
        String[] arr = text.split("\\s+");
        return arr;
    }

    public static String ChineseAndEnglishAndNumber(String text) {
        if (isChinese(text)) {
            return text;
        } else if (isLetter(text)) {
            return text;
        } else if (isNumber(text)) {
            if (text.length() == 4) {
                return text;
            } else {
                return "为四位数，请重新输入";
            }
        }
        return text;
    }
}
