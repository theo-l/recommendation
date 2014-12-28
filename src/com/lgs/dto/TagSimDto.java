/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dto;

import com.lgs.bean.Vector;
import com.lgs.dao.TagBookDao;
import com.lgs.dao.TagDao;
import com.lgs.dao.TagSimDao;
import com.lgs.similar.CosinSimilar;
import com.lgs.similar.DiceSimilar;
import com.lgs.similar.JaccardSimilar;
import com.lgs.similar.MatchSimilar;
import com.lgs.similar.Method;
import com.lgs.similar.OverlapSimilar;
import com.lgs.similar.Type;
import com.lgs.similar.VectorSimilar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class TagSimDto {
    
    private int method;//相似性方法的配置
    private int type;//降维策略
    private List<Integer> allTags;//所有参与标注的标签id
    private VectorSimilar similar;//具体的相似性计算方法
    
    private TagDao tDao;//标签数据访问器
    private TagBookDao tbDao;//标签-书籍数据访问器
    
    private TagSimDao tsDao;
    
    
     public static void main(String[] args){
        TagSimDto tsDto=new TagSimDto();
        
        tsDto.setMethod(Method.Cosine);
        tsDto.setType(Type.Project);
        
        tsDto.config();
        tsDto.generateTagSimMatrix();
    }
    
     
    public TagSimDto(){
        tDao=new TagDao();
        
        tbDao=new TagBookDao();
        
        this.setAllTags(this.tDao.getAllTags());
    }
    
    
    public void config(){
        switch(this.method){
            case Method.Cosine:this.setSimilar(new CosinSimilar());break;
            case Method.Dice:this.setSimilar(new DiceSimilar());break;
            case Method.Jaccard:this.setSimilar(new JaccardSimilar());break;
            case Method.Match:this.setSimilar(new MatchSimilar()); break;
            case Method.Overlap:this.setSimilar(new OverlapSimilar());break;
            default:break;
        }
        this.similar.setType(this.getType());
        this.tsDao=new TagSimDao(this.getMethod(),this.getType());
    }
    
    public List<Vector> getTagVector(int size){
        List<Vector> tags=new ArrayList<Vector>();
//        int size=100;
         for(int i=0;i<size;i++){
            Vector v=null;
            switch(this.getType()){
                case Type.Project:v=this.tbDao.getProjectTagBookByTid(this.getAllTags().get(i));break;
                case Type.Distribution:v=this.tbDao.getDistributionTagBookByTid(this.getAllTags().get(i));break;
                    
                default:break;
            }
            System.out.println(v.getVid());
            tags.add(v);
        }
        
        return tags;
    }
    
    public void generateTagSimMatrix(){
        int size=this.getAllTags().size();
//    	 int size=tags.size();

//         size=100;
      
        List<Vector> tags=this.getTagVector(size);
        
        
         
        for(int i=0;i<size-1;i++){
            Vector tag1=tags.get(i);
            for(int j=i+1;j<=size-1;j++){
                Vector tag2=tags.get(j);
                
                double sim=this.similar.getSimilar(tag1, tag2);
               if(sim>0){
                System.out.println(tag1);
                System.out.println(tag2);
                System.out.printf("tag %d and tag %d : similar is %.5f %n",tag1.getVid(),tag2.getVid(),sim);
                this.tsDao.saveTagSim(tag1.getVid(), tag2.getVid(), sim);
               }
            }
        }
        
        
    }

    /**
     * @return the method
     */
    public int getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(int method) {
        this.method = method;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the allTags
     */
    public List<Integer> getAllTags() {
        return allTags;
    }

    /**
     * @param allTags the allTags to set
     */
    public void setAllTags(List<Integer> allTags) {
        this.allTags = allTags;
    }

    /**
     * @return the similar
     */
    public VectorSimilar getSimilar() {
        return similar;
    }

    /**
     * @param similar the similar to set
     */
    public void setSimilar(VectorSimilar similar) {
        this.similar = similar;
    }

    /**
     * @return the tDao
     */
    public TagDao gettDao() {
        return tDao;
    }

    /**
     * @param tDao the tDao to set
     */
    public void settDao(TagDao tDao) {
        this.tDao = tDao;
    }

    /**
     * @return the tbDao
     */
    public TagBookDao getTbDao() {
        return tbDao;
    }

    /**
     * @param tbDao the tbDao to set
     */
    public void setTbDao(TagBookDao tbDao) {
        this.tbDao = tbDao;
    }
    
   
}
