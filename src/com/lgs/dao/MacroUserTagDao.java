/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 * 用来访问每个用户拥有的标签的相应的权值
 */
public class MacroUserTagDao extends BaseDao{
    private Statement stmt;
    
    public MacroUserTagDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveMacroUserTag(int uid,int tid,double value){
        String sql="insert into macro_ut(userid,tagid,value) values("+uid+","+tid+","+value+")";
        try {
            this.stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getValueByUidTid(int uid,int tid){
        double result=0;
        String sql="select value from macro_ut where userid="+uid+"and tagid="+tid;
        try {
            ResultSet rs=stmt.executeQuery(sql);
            
            while(rs.next()){
                result=rs.getDouble("value");
            }
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
