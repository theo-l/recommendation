/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.similar;

import com.lgs.bean.Vector;
import com.lgs.dao.TagDisDao;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author acer
 */
public class DiceSimilar extends VectorSimilar{
    
    //矩阵降维的类型
    private int type;

    //标签分布式降维的数据库访问器 tag_distribution
    private TagDisDao tdd;
    
    public DiceSimilar(){
        this.tdd=new TagDisDao();
    }
    @Override
    public double getSimilar(Vector v1, Vector v2) {
        switch(type){
            case Type.Project:return this.DiceProjectSim(v1, v2) ;
            case Type.Distribution:return this.DiceDistributionSim(v1, v2);
            case Type.Macro:return this.DiceMacroSim(v1, v2);
            case Type.Collaborative:return this.DiceCollaborativeSim(v1, v2);
            default:return 0;
        }
    }

    @Override
    public void setType(int type) {
        this.type=type;
    }

    @Override
    public int getType() {
        return this.type;
    }
    /*
     * 利用Dice算法计算投影矩阵的相似性，可以计算Project和Distribution矩阵的相似性
     * 向量中所有元素的权重为全局权重，因此直接计算
     */
    public double DiceProjectSim(Vector v1,Vector v2){
        //2|X1nX2|/(|X1|+|X2|)
        //the value of each element in the vector is 1 or 0
        //numerator :2|X1nX2|
        //denominator:(|X1|+|X2|)
 
        
        //分子
        double numerator=this.ProjectNumerator(v1, v2);
        
        //分母
        double denominator=this.ProjectDenominator(v1, v2);
        
        return (2*numerator)/(denominator);
 
    }
    
    public double ProjectDenominator(Vector v1,Vector v2){
        HashMap<Integer,Double> element1=v1.getElements();
        HashMap<Integer,Double> element2=v2.getElements();
        
        double s1=0;
        double s2=0;
        
        for(Double v:element1.values()){
            s1+=v*v;
        }
//        double sv1=Math.sqrt(s1);
        for(Double v:element2.values()){
            s2+=v*v;
        }
//        double sv2=Math.sqrt(s2);
        return s1+s2;
    }
    
    public double ProjectNumerator(Vector v1,Vector v2){
        HashMap<Integer,Double> element1=v1.getElements();
        HashMap<Integer,Double> element2=v2.getElements();
        Double numerator=0.0;
        for(Integer i:element1.keySet()){
            if(element2.containsKey(i)){
                Double w1=element1.get(i);
                Double w2=element2.get(i);
                numerator+=w1*w2;
            }
        }
        return numerator;
    }
    
    
    
    public double DiceDistributionSim(Vector v1,Vector v2){
        //分子
        double numerator=DistributionNumerator(v1,v2);
        System.out.println("numerator is: "+numerator);
        //分母
        double denominator=this.DistributionDenominator(v1, v2);
        System.out.println("denominator is: "+denominator);
    
        return (2*numerator)/denominator;
        
    }
    
    public double DistributionNumerator(Vector v1,Vector v2){
       
        double sum=0;
        Set<Integer> keys1=v1.getElements().keySet();
        
        for(Integer i:keys1){
            if(v2.getElements().keySet().contains(i)){
                sum+=v1.getElements().get(i)*v2.getElements().get(i);
            }
        }
        
        
        return 2*Math.sqrt(sum);
    }
    
    
    //按照标签分布的权重来获取相应的相似计算的分母值
    public double DistributionDenominator(Vector v1,Vector v2){
//        double sum=0;
        double sum1=0;
        double sum2=0;
        
        for(Integer i:v1.getElements().keySet()){
            sum1+=v1.getElements().get(i)*v1.getElements().get(i);
        }
        
        for(Integer j:v2.getElements().keySet()){
            sum2+=v2.getElements().get(j)*v2.getElements().get(j);
        }
        return Math.sqrt(sum1)+Math.sqrt(sum2);
//        return sum;
    }
    
    public double DiceMacroSim(Vector v1,Vector v2){
        
        //分子
        double numerator=this.MacroNumerator(v1, v2);
        
        //分母
        double denominator=this.MacroDenominator(v1, v2);
        
        return (2*numerator)/(denominator);
    }
    
    public double MacroNumerator(Vector v1,Vector v2){
        return 0;
    }
    
    public double MacroDenominator(Vector v1,Vector v2){
        return 0;
    }
    
    public double DiceCollaborativeSim(Vector v1,Vector v2){
        
        //分子
        double numerator =this.CollaborativeNumerator(v1, v2);
        
        //分母
        double denominator=this.CollaborativeDenominator(v1, v2);
        
        return (2*numerator)/denominator;
        
        
    }
    
    
    public double CollaborativeNumerator(Vector v1,Vector v2){
        return 0;
    }
    
    public double CollaborativeDenominator(Vector v1,Vector v2){
        return 0;
    }
    
}
