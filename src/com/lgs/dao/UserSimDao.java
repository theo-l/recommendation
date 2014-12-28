/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.UserSim;
import com.lgs.similar.Method;
import com.lgs.similar.Type;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class UserSimDao extends BaseDao{
    
    private int method;
    private int type;
    private String tbName;
    private Statement stmt;
    
    public static void main(String[] args){
        UserSimDao usDao=new UserSimDao(Method.Cosine,Type.Project);
        
    }
    
    public UserSimDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public UserSimDao(int method,int type){
        this.setMethod(method);
        this.setType(type);
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTbName();
        
    }
    
    public void saveUserSim(int userid,int simuserid,double value){
        String sql="insert into "+this.getTbName()+"(userid,simuserid,value) values("+userid+","+simuserid+","+value+")";
    }
    
    public UserSim getUserSimByUid(int uid){
        return null;
    }
    public boolean saveUserSim(UserSim us){
        return false;
    }

    public void setTbName(){
        switch(this.getMethod()){
            case Method.Match:{
                switch(this.getType()){
                    case Type.Project:this.tbName="usermatch_prosim";break;
                    case Type.Distribution:this.tbName="usermatch_dissim";break;
                    case Type.Macro:this.tbName="usermatch_macrosim";break;
                    case Type.Collaborative:this.tbName="usermatch_colsim";break;
                    default:break;
                }
            };break;
            case Method.Overlap:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="useroverlap_prosim";break;
                    case Type.Distribution:this.tbName="useroverlap_dissim";break;
                    case Type.Macro:this.tbName="useroverlap_macrosim";break;
                    case Type.Collaborative:this.tbName="useroverlap_colsim";break;
                    default:break;
                }
            };break;
            case Method.Jaccard:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="userjac_prosim";break;
                    case Type.Distribution:this.tbName="userjac_dissim";break;
                    case Type.Macro:this.tbName="userjac_macrosim";break;
                    case Type.Collaborative:this.tbName="userjac_colsim";break;
                    default:break;
                }
            };break;
            case Method.Dice:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="userdice_prosim";break;
                    case Type.Distribution:this.tbName="userdice_dissim";break;
                    case Type.Macro:this.tbName="userdice_macrosim";break;
                    case Type.Collaborative:this.tbName="userdice_colsim";break;
                    default:break;
                }
            };break;
            case Method.Cosine:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="usercosine_prosim";break;
                    case Type.Distribution:this.tbName="usercosine_dissim";break;
                    case Type.Macro:this.tbName="usercosine_macrosim";break;
                    case Type.Collaborative:this.tbName="usercosine_colsim";break;
                    default:break;
                }
            };break;
            default:break;
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
     * @return the tbName
     */
    public String getTbName() {
        return tbName;
    }

    /**
     * @param tbName the tbName to set
     */
    public void setTbName(String tbName) {
        this.tbName = tbName;
    }
}
