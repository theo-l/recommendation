/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.TagSim;
import com.lgs.similar.Method;
import com.lgs.similar.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class TagSimDao extends BaseDao{
    
    
    private Statement stmt;    
    private String tbName;    
    private int method;
    private int type;
    
    
    
    
    public static void main(String[] args){
//        TagSimDao tsDao=new TagSimDao(Method.Cosine,Type.Project);
//        tsDao.saveTagSim(1, 2, 3);
        
        TagSimDao tsDao=new TagSimDao();
        tsDao.setMethod(Method.Cosine);
        tsDao.setType(Type.Project);
        tsDao.setTbName();
        tsDao.saveTagSim(1, 2, 3);
    }
    
    
    
    //根据标签的id号来得到标签的相似标签列表
    public TagSim getTagSimByTid(int tid){
        return null;
    }
    
    //保存标签的相似用户列表
    public boolean saveTagSim(TagSim ts){
        return false;
    }
    
    public TagSimDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(TagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public TagSimDao(int method,int type){
        
        this.setMethod(method);
        this.setType(type);
        this.setTbName();
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(TagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setTbName();
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * @return the tbName
     */
    public String getTbName() {
        return tbName;
    }

    /**
     * @param tbName the tbName to set
     * 根据所选择的相似性计算方法以及相应的降维策略类型确定数据表的名称
     */
    public void setTbName() {
        switch(this.getMethod()){
            case Method.Match:{
                switch(this.getType()){
                    case Type.Project:this.tbName="tagmatch_prosim";break;
                    case Type.Distribution:this.tbName="tagmatch_dissim";break;
                    case Type.Macro:this.tbName="tagmatch_macrosim";break;
                    case Type.Collaborative:this.tbName="tagmatch_colsim";break;
                    default:break;
                }
            };break;
            case Method.Overlap:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="tagoverlap_prosim";break;
                    case Type.Distribution:this.tbName="tagoverlap_dissim";break;
                    case Type.Macro:this.tbName="tagoverlap_macrosim";break;
                    case Type.Collaborative:this.tbName="tagoverlap_colsim";break;
                    default:break;
                }
            };break;
            case Method.Jaccard:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="tagjac_prosim";break;
                    case Type.Distribution:this.tbName="tagjac_dissim";break;
                    case Type.Macro:this.tbName="tagjac_macrosim";break;
                    case Type.Collaborative:this.tbName="tagjac_colsim";break;
                    default:break;
                }
            };break;
            case Method.Dice:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="tagdice_prosim";break;
                    case Type.Distribution:this.tbName="tagdice_dissim";break;
                    case Type.Macro:this.tbName="tagdice_macrosim";break;
                    case Type.Collaborative:this.tbName="tagdice_colsim";break;
                    default:break;
                }
            };break;
            case Method.Cosine:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="tagcosine_prosim";break;
                    case Type.Distribution:this.tbName="tagcosine_dissim";break;
                    case Type.Macro:this.tbName="tagcosine_macrosim";break;
                    case Type.Collaborative:this.tbName="tagcosine_colsim";break;
                    default:break;
                }
            };break;
            default:break;
        }
    }
    
    public void saveTagSim(int tid,int simtid,double value){
        String sql="insert into "+ this.getTbName()+"(tagid,simtagid,value) values("+tid+","+simtid+","+value+")";
        try {
            this.stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getValueTT(int tagid,int simtagid){
        double value=0;
        String sql="select value from "+this.getTbName()+" where tagid ="+tagid+" and simtagid="+simtagid;
        try {
            ResultSet rs=this.stmt.executeQuery(sql);
            while(rs.next()){
                value=rs.getDouble("value");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
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
}
