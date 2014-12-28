/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.similar;

import com.lgs.bean.Vector;
import java.util.HashMap;

/**
 *
 * @author acer
 * 利用余弦求相似性
 */
public class CosinSimilar extends VectorSimilar{

    private int type;
    
    @Override
    public double getSimilar(Vector v1, Vector v2) {
        switch(type){
            case Type.Project: return this.CosineProjectSim(v1, v2);
            case Type.Distribution:return this.CosineDistributionSim(v1, v2);
            case Type.Macro:return this.CosineMacroSim(v1, v2);
            case Type.Collaborative:return this.CosineCollaborativeSim(v1, v2);
            default :return 0;
        }
    }
    
    //利用余弦值来求解向量的相似性
    //投影产生的矩阵都是0、1矩阵
    public double CosineProjectSim(Vector v1,Vector v2){
        //分子
        double numerator=this.ProjectNumerator(v1, v2);
        
        //分母
        double denominator=this.ProjectDenominator(v1, v2);
        
        return numerator/denominator;
    }
    
    
    //直接投影属于0、1矩阵
    public double ProjectNumerator(Vector v1,Vector v2){
        double sum=0;
        
        HashMap<Integer,Double> ele1=v1.getElements();
        HashMap<Integer,Double> ele2=v2.getElements();
        
        for(Integer i:ele1.keySet()){
            if(ele2.containsKey(i)){
                sum+=1;
            }
        }
        
        
        return sum;
    }
    
    public double ProjectDenominator(Vector v1,Vector v2){
        
        double sum1=0;
        double sum2=0;
        
        for(Double i:v1.getElements().values()){
            sum1+=1;
        }
        for(Double j:v2.getElements().values()){
            sum2+=1;
        }
       return Math.sqrt(sum1*sum2);
         
     
    }
    
    
    //分布式矩阵的标签具有不同的权重，其分子、分母的计算方法与投影矩阵之间存在差别
    public double CosineDistributionSim(Vector v1,Vector v2){
        
        //分子
        double numerator=this.DistributionNumerator(v1, v2);
        
        //分母
        double denominator=this.DistributionDenominator(v1, v2);
        
        
        return numerator/denominator;
    }
    
    public double DistributionNumerator(Vector v1,Vector v2){
        
        double sum=0;
        for(Integer i:v1.getElements().keySet()){
            if(v2.getElements().containsKey(i)){
                sum+=v1.getElements().get(i)*v2.getElements().get(i);
            }
        }
        
        return sum;
    }
    public double DistributionDenominator(Vector v1,Vector v2){
        double sum1=0;
        double sum2=0;
        
        for(Double i:v1.getElements().values()){
            sum1+=i*i;
        }
        
        for(Double j:v2.getElements().values()){
            sum2+=j*j;
        }
        return Math.sqrt(sum1)*Math.sqrt(sum2);
        
        
    }
    
     
    public double CosineMacroSim(Vector v1,Vector v2){
        
        //得到
        
        
        return 0;
    }
    
    public double MacroNumerator(Vector v1,Vector v2){
        return 0;
    }
    public double MacroDenominator(Vector v1,Vector v2){
        return 0;
    }
    
    public double CosineCollaborativeSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double CollaborativeNumerator(Vector v1,Vector v2){
        return 0;
    }
    
    public double CollaborativeDenominator(Vector v1,Vector v2){
        return 0;
    }

    @Override
    public void setType(int type) {
         this.type=type;
        }

    @Override
    public int getType() {
       return this.type;
    }
    
    
}
