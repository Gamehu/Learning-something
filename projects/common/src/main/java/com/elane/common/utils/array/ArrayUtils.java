package com.elane.common.utils.array;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @description
 *          数组/阵列工具类
 * @author carl
 *
 * @version 创建时间：2016年9月5日 下午12:32:20
 * 
 **/
public class ArrayUtils {
    /**
     * 去除第一个数组存在于第二个数组相同的值
     * @param arr1 需要去除与第二个数组里面相同数据的集合
     * @param arr2 第二个数组
     * @param 返回arr2去除数据后的数组
     * */
    public static String[] arrayContrast(String[] arr1, String[] arr2){
        List<String> list = new LinkedList<String>();
        for (String str : arr1) {                //处理第一个数组,list里面的值为1,2,3,4
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {      //如果第二个数组存在和第一个数组相同的值，就删除
            if(list.contains(str)){
                list.remove(str);
            }
        }
        String[] result = {};   //创建空数组
        return list.toArray(result);    //List to Array
    }
}
