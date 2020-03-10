package com.newing.utils;

import android.text.TextUtils;


public class PrisonDifferentName {
    public static final int    UNDEFINED              = -1;
    public static final int    PRISON                 = 0;
    public static final int    DETENTION              = 1;
    public static final int    DETOX                  = 2;

    // 获取类别
    public static String getDormNameByPrisonId(String prisonId) {

        switch (getPrisonTypeByPrisonId(prisonId)) {
            case PRISON:
                return "监室";
            case DETENTION:
                return "拘室";
            case DETOX:
                return "戒室";
            default:
                return "";
        }
    }




    public static String getDormAreaNameByPrisonId(String prisonId) {

        switch (getPrisonTypeByPrisonId(prisonId)) {
            case PRISON:
                return "监区";
            case DETENTION:
                return "拘区";
            case DETOX:
                return "戒区";
            default:
                return "";
        }
    }

    public static String getDormNameByNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        return getDormNameByPrisonId(number.substring(0, 9));
    }

    public static String getPrisonNameByPrisonId(String prisonId) {

        switch (getPrisonTypeByPrisonId(prisonId)) {
            case PRISON:
                return "看守所";
            case DETENTION:
                return "拘留所";
            case DETOX:
                return "戒毒所";
            default:
                return "";
        }
    }

    public static String getLevelToPrisonNameByPrisonId(String prisonId, String level_name) {
        if (TextUtils.isEmpty(level_name)) {
            return "";

        }
        switch (getPrisonTypeByPrisonId(prisonId)) {
            case PRISON://"看守所"

                switch (level_name) {
                    case "一级重大风险":
                        return "一级";
                    case "二级重大风险":
                        return "二级";
                    default:
                        return "普通";
                }
            case DETOX://戒毒所
            case DETENTION://拘留所
                switch (level_name) {
                    case "重大风险":
                        return "一级";
                    case "较大风险":
                        return "二级";
                    default:
                        return "普通";
                }
            default:
                return "普通";
        }
    }


    public static String getShortPrisonNameByPrisonId(String prisonId) {

        switch (getPrisonTypeByPrisonId(prisonId)) {
            case PRISON:
                return "监所";
            case DETENTION:
                return "拘所";
            case DETOX:
                return "戒所";
            default:
                return "";
        }
    }

    public static int getPrisonTypeByPrisonId(String prisonId) {
        if (TextUtils.isEmpty(prisonId)) {
            return UNDEFINED;
        }
        String s = prisonId.substring(prisonId.length() - 2, prisonId.length() - 1);//截取第八位
        switch (s) {
            case "1":
                return PRISON;
            case "2":
                return DETENTION;
            case "3":
                return DETOX;
        }
        return UNDEFINED;
    }
}
