package com.newing.utils;


import java.util.List;

public class ListUtil {
    //List非空判断
    public static  <T> boolean isNolList(List<T> list) {
        return (list != null && list.size() > 0);
    }

}
