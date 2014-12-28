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
 * 用来访问用户的书籍的局部权值
 */
public class MacroUserBookDao extends BaseDao {
    
    private Statement stmt;
    
    public MacroUserBookDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void saveMacroUserBook(int uid,int bid,double value){
        String sql="insert into macro_ub(userid,bookid,value) values("+uid+","+bid+","+value+")";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public double getValueByUidBid(int uid,int bid){
        double result=0;
        String sql="select value from macro_ub where userid="+uid+" and bookid="+bid;
        try {
            
           
            
            ResultSet rs=stmt.executeQuery(sql);
            
            while(rs.next()){
                result=rs.getDouble("value");
            }
            rs.close();
           
        } catch (SQLException ex) {
            Logger.getLogger(MacroUserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
