package com.newing.utils.encrypt;

/**
 * @author sqq
 * @version V1.0.0
 * @date 2017/6/21 15:09
 */
public class HexUtil {

    public HexUtil() {
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        int j = 0;

        for(int i = 0; i < bArray.length; ++i) {
            String sTemp = Integer.toHexString(255 & bArray[i]);
            if(sTemp.length() < 2) {
                sb.append(0);
            }

            sb.append(sTemp.toUpperCase());
            ++j;
        }

        return sb.toString();
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        boolean mask = true;

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(num >>> 24 - i * 8);
        }

        return b;
    }

    public static int bytes2int(byte[] b) {
        short mask = 255;
        boolean temp = false;
        int res = 0;

        for(int i = 0; i < 4; ++i) {
            res <<= 8;
            int var5 = b[i] & mask;
            res |= var5;
        }

        return res;
    }

    public static int bytes2short(byte[] b) {
        short mask = 255;
        boolean temp = false;
        int res = 0;

        for(int i = 0; i < 2; ++i) {
            res <<= 8;
            int var5 = b[i] & mask;
            res |= var5;
        }

        return res;
    }

    public static String bcd2str(byte[] bcds) {
        if(bcds == null) {
            return "";
        } else {
            char[] ascii = "0123456789abcdef".toCharArray();
            byte[] temp = new byte[bcds.length * 2];

            for(int res = 0; res < bcds.length; ++res) {
                temp[res * 2] = (byte)(bcds[res] >> 4 & 15);
                temp[res * 2 + 1] = (byte)(bcds[res] & 15);
            }

            StringBuffer var5 = new StringBuffer();

            for(int i = 0; i < temp.length; ++i) {
                var5.append(ascii[temp[i]]);
            }

            return var5.toString().toUpperCase();
        }
    }


    /**
     * 十六进制字符串转换成bytes
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        int l = hexStr.length();
        if (l % 2 != 0) {
            StringBuilder sb = new StringBuilder(hexStr);
            sb.insert(hexStr.length() - 1, '0');
            hexStr = sb.toString();
        }
        byte[] b = new byte[hexStr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexStr.charAt(j++);
            char c1 = hexStr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}
