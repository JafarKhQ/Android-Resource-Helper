package com.jafarkhq.reshelper.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jafar
 * Date: 8/15/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ListUtils {
    public static boolean isEmpty(List<?> list){
         return ((null==list) || (list.size()<1));
    }
}
