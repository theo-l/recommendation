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
 * colla_ub访问器
 */
public class CollaborativeUBDao extends BaseDao{
    
    private Statement stmt;
    
    public CollaborativeUBDao(){
        try {
            stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUBDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUBDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void saveCollaborativeUB(int uid,int bid,double value){
        
       
            String sql="insert into colla_ub(userid,bookid,value) values("+uid+","+bid+","+value+")";
            try {
                stmt.executeUpdate(sql);
               
              
            } catch (SQLException ex) {
                Logger.getLogger(CollaborativeUBDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    public boolean isUBExists(int userid,int bookid){
        boolean flag=false;
        String sql="select id from colla_ub where userid=? and bookid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, userid);
            pstmt.setInt(2, bookid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                flag=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUBDao.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
        return flag;
    }
    

    
    public double getValueByUidBid(int userid,int bookid){
        double result=0;
        String sql="select value from colla_ub where userid=? and bookid=? ";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, userid);
            pstmt.setInt(2, bookid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                result=rs.getDouble("value");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUBDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
