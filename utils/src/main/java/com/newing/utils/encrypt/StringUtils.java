package com.newing.utils.encrypt;

import android.os.Build;
import android.text.TextUtils;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {

    public static int cmpVersion(String param1, String param2)
            throws Exception {
        if (param1 == null) {
            try {
                throw new NullPointerException();
            } catch (Exception ex) {
                throw new NullPointerException();
            }
        }
        if (param2 == null) {
            throw new NullPointerException();
        }
        String[] param1s = param1.split("\\.");
        String[] param2s = param2.split("\\.");
        if ((param1s.length != 3) || (param2s.length != 3)) {
            throw new Exception("版本号格式非法");
        }
        int i = formatVersion(param1s).compareTo(formatVersion(param2s));
        return i;
    }

    public static boolean containCn(String paramString) {
        return Pattern.compile("[\\u4e00-\\u9fa5]").matcher(paramString).find();
    }

    public static String dislodgeLastLetter(String paramString) {
        if ((isEmpty(paramString)) || (paramString.length() % 2 == 0)) {
            return paramString;
        }
        return paramString.substring(0, paramString.length() - 1);
    }

    public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
        return TextUtils.equals(paramCharSequence1, paramCharSequence2);
    }

    public static String fillBackChar(String param, char paramChar) {
        if ((isEmpty(param)) || (param.length() % 16 == 0)) {
            return param;
        }
        StringBuffer paramString = new StringBuffer(param);
        for (; ; ) {
            if (paramString.length() % 16 == 0) {
                return paramString.toString();
            }
            paramString.append(paramChar);
        }
    }

    public static String formatCardno(String paramString) {
        if ((isEmpty(paramString)) || (paramString.length() < 12)) {
            return paramString;
        }
        if (paramString.length() - 10 < 31) {
        }
        for (String str1 = "*******************************".substring(0, paramString.length() - 10); ; str1 = "*******************************") {
            String str2 = paramString.substring(0, 6);
            paramString = paramString.substring(paramString.length() - 4, paramString.length());
            return str2 + str1 + paramString;
        }
    }

    public static String formatMobile(String paramString) {
        if ((isEmpty(paramString)) || (paramString.length() < 11)) {
            return paramString;
        }
        String str1 = "*******************************".substring(0, paramString.length() - 4);
        String str2 = paramString.substring(0, 3);
        paramString = paramString.substring(paramString.length() - 4, paramString.length());
        return str2 + str1 + paramString;
    }

    public static <T> String formatMount(T paramT) {
        DecimalFormat localDecimalFormat = new DecimalFormat("##0.00");
        if ((paramT instanceof String)) {
            return localDecimalFormat.format(Double.valueOf(paramT.toString()).doubleValue()).replace(".", "");
        }
        if (((paramT instanceof Integer)) || ((paramT instanceof Double)) || ((paramT instanceof Short)) || ((paramT instanceof Float)) || ((paramT instanceof Long))) {
            return localDecimalFormat.format(paramT).replace(".", "");
        }
        return paramT.toString();
    }

    private static String formatVersion(String[] paramArrayOfString) {
        String str1 = "";
        int i = 0;
        for (; ; ) {
            if (i >= paramArrayOfString.length) {
                return str1;
            }
            String str2 = "000" + paramArrayOfString[i].trim();
            str1 = str1 + str2.substring(str2.length() - 3, str2.length());
            i += 1;
        }
    }

    public static String htmlEncode(String paramString) {
        return TextUtils.htmlEncode(paramString);
    }

    public static boolean isDigitsOnly(CharSequence paramCharSequence) {
        return TextUtils.isDigitsOnly(paramCharSequence);
    }

    public static boolean isEmpty(String paramString) {
        return (TextUtils.isEmpty(paramString)) || (paramString.equals("null")) || (paramString.equals(""));
    }

    public static boolean isL() {
        return Build.VERSION.SDK_INT >= 20;
    }

    public static boolean isMobile(String paramString) {
        if (paramString != null) {
            return paramString.matches("^[1][3-9][0-9]{9}$");
        }
        return false;
    }

    public static boolean isPinYin(String paramString) {
        return Pattern.compile("[a-zA-Z]*").matcher(paramString).matches();
    }

    public static boolean isPwdValid(String paramString) {
        return Pattern.compile("[a-zA-Z0-9]{0,20}").matcher(paramString).matches();
    }

    public static boolean isTermSN(String paramString) {
        if (isEmpty(paramString)) {
            return false;
        }
        return paramString.matches("/[^a-zA-Z0-9]/g");
    }

    public static String join(Collection<String> paramCollection, String paramString) {
        if (paramCollection.size() == 0) {
            return "";
        }
        StringBuilder localStringBuilder = new StringBuilder();
        Iterator<String> iterator = paramCollection.iterator();
        for (; ; ) {
            if (!iterator.hasNext()) {
                if (localStringBuilder.length() > 0) {
                    localStringBuilder.delete(localStringBuilder.length() - 1, localStringBuilder.length());
                }
                return localStringBuilder.toString();
            }
            localStringBuilder.append((String) iterator.next()).append(paramString);
        }
    }

    public static String leftAddZeroForNum(String paramString, int paramInt) {
        for (int i = paramString.length(); ; i = paramString.length()) {
            if (i >= paramInt) {
                return paramString;
            }
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("0").append(paramString);
            paramString = localStringBuffer.toString();
        }
    }

    public static String rightAddZeroForNum(String paramString, int paramInt) {
        for (int i = paramString.length(); ; i = paramString.length()) {
            if (i >= paramInt) {
                return paramString;
            }
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append(paramString).append("0");
            paramString = localStringBuffer.toString();
        }
    }

    public static String unformatMount(String paramString) {
        if (isEmpty(paramString)) {
            return "0.00";
        }
        double d = Double.parseDouble(paramString) * 0.01D;
        if (d > 0.0D) {
            return new DecimalFormat("##0.00").format(d);
        }
        return "0.00";
    }

    public static String trimToEmpty(String str) {
        return str == null?"":str.trim();
    }

    public static String byte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if(hex.length() == 1) {
                sb.append(0);
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] hexStr2Byte(String hexStr) {
        byte[] result = new byte[hexStr.length() / 2];

        for(int i = 0; i < hexStr.length() / 2; ++i) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }

        return result;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

//    public static boolean isEmpty(String value) {
//        int strLen;
//        if(value != null && (strLen = value.length()) != 0) {
//            for(int i = 0; i < strLen; ++i) {
//                if(!Character.isWhitespace(value.charAt(i))) {
//                    return false;
//                }
//            }
//
//            return true;
//        } else {
//            return true;
//        }
//    }

    public static boolean isNumeric(Object obj) {
        if(obj == null) {
            return false;
        } else {
            char[] chars = obj.toString().toCharArray();
            int length = chars.length;
            if(length < 1) {
                return false;
            } else {
                int i = 0;
                if(length > 1 && chars[0] == 45) {
                    i = 1;
                }

                while(i < length) {
                    if(!Character.isDigit(chars[i])) {
                        return false;
                    }

                    ++i;
                }

                return true;
            }
        }
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if(values != null && values.length != 0) {
            String[] var2 = values;
            int var3 = values.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }

        return result;
    }

    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if(!isEmpty(unicode)) {
            for(int i = 0; i < unicode.length(); ++i) {
                out.append(unicode.charAt(i));
            }
        }

        return out.toString();
    }

    public static String stripNonValidXMLCharacters(String input) {
        if(input != null && !"".equals(input)) {
            StringBuilder out = new StringBuilder();

            for(int i = 0; i < input.length(); ++i) {
                char current = input.charAt(i);
                if(current == 9 || current == 10 || current == 13 || current >= 32 && current <= '\ud7ff' || current >= '\ue000' && current <= '�' || current >= 65536 && current <= 1114111) {
                    out.append(current);
                }
            }

            return out.toString();
        } else {
            return "";
        }
    }

    public static String newStringUtf8(byte[] bytes) {
        return newString(bytes, Charset.forName("UTF-8"));
    }

    private static String newString(byte[] bytes, Charset charset) {
        return bytes == null?null:new String(bytes, charset);
    }

    public static byte[] getBytesUtf8(String string) {
        return getBytes(string, Charset.forName("UTF-8"));
    }

    private static byte[] getBytes(String string, Charset charset) {
        return string == null?null:string.getBytes(charset);
    }


    public static Map getmap(String s)
    {
        String[] bb = s.split("&");//将所有&符号截取出来变成一个数组
        String cc = null;//获取每个&之内的内容
        String[] dd = null;//获取每个=号的内容
        String key = null;
        String value = null;
        Map map = new HashMap<String, String>();
        for (int i = 0; i < bb.length; i++) {
            cc = bb[i];//获取每个&的内容
            dd = cc.split("=");//拆分=号
            key = dd[0];//=号前面的值
            value = dd[1];//=号后面的值
            map.put(key, value);//将值放入map中
        }
        return map;
    }

}
