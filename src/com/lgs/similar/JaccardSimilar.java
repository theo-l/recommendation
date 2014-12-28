/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.similar;

import com.lgs.bean.Vector;
import com.lgs.dao.TagDisDao;

/**
 *
 * @author acer
 */
public class JaccardSimilar extends VectorSimilar{

    private int type;
    private TagDisDao tdDao;
    
    
    public JaccardSimilar(){
        tdDao=new TagDisDao();
    }
    
    @Override
    public double getSimilar(Vector v1, Vector v2) {
        switch(this.getType()){
            case Type.Project:return this.JaccardProjectSim(v1, v2); 
            case Type.Distribution:return this.JaccardDistributionSim(v1, v2);
            case Type.Macro:return this.JaccardMacroSim(v1, v2);
            case Type.Collaborative:return this.JaccardCollaborativeSim(v1, v2);
            default:return 0;
        }
    }
    
    public double JaccardProjectSim(Vector v1,Vector v2){
        
        double numerator=this.ProjectNumerator(v1, v2);
        
        double denominator=this.ProjectDenominator(v1, v2);
        
        
        return numerator/denominator;
    }
    
    //jaccard 方法的分子
    public double ProjectNumerator(Vector v1,Vector v2){
        double sum=0;
        for(Integer i:v1.getElements().keySet()){
            if(v2.getElements().containsKey(i)){
                sum+=1;
            }
        }
        
        return sum;
    }
    
    //jaccard方法的分母
    public double ProjectDenominator(Vector v1,Vector v2){
        double sum=0;
        
        for(Integer i:v1.getElements().keySet()){
            sum+=v1.getElements().get(i);
        }
        for(Integer j:v2.getElements().keySet()){
            sum+=v2.getElements().get(j);
        }
        
        
        return sum-this.ProjectNumerator(v1, v2);
    }
    
    public double JaccardDistributionSim(Vector v1,Vector v2){
        double numerator =this.DistributionNumerator(v1, v2);
        
        double denominator=this.DistributionDenominator(v1, v2);
        
        return numerator/denominator;
    }
    
    
    //主要计算标签的全局分布值
    public double DistributionNumerator(Vector v1,Vector v2){
        double sum=0;
        
        for(Integer i:v1.getElements().keySet()){
            if(v2.getElements().containsKey(i)){
                sum+=tdDao.getTagDistributionById(i);
            }
        }
        return sum;
    }
    
    public double DistributionDenominator(Vector v1,Vector v2){
        
        double sum=0;
        
        for(Integer i:v1.getElements().keySet()){
            sum+=tdDao.getTagDistributionById(i);
        }
        for(Integer j:v2.getElements().keySet()){
            sum+=tdDao.getTagDistributionById(j);
        }
        
        
        
        return sum-this.DistributionNumerator(v1, v2);
    }
    
    public double JaccardMacroSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double JaccardCollaborativeSim(Vector v1,Vector v2){
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
