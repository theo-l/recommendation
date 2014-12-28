/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.similar;

/**
 *
 * @author acer
 * 计算相似性时所以使用的方法
 */
public class Method {

    //使用余弦方法进行计算
    public static final int Cosine=1;
    
    //使用Dice方法进行计算
    public static final int Dice=2;
    
    //使用Jaccard系数方法进行计算
    public static final int Jaccard=3;
    
    //使用共现匹配方法进行计算
    public static final int Match=4;
    
    //使用重叠值方法进行计算overlap
    public static final int Overlap=5;
    
}
