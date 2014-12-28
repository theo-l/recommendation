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
 * colla_ut访问器
 */
public class CollaborativeUTDao extends BaseDao {
    
    private Statement stmt;
    
    public CollaborativeUTDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUTDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUTDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveCollaborativeUT(int uid,int tid,double value){
        String sql="insert into colla_ut(userid,tagid,value) values("+uid+","+tid+","+value+")";
        try {
           stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUTDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getValueByUidTid(int uid,int tid){
        double value=0;
        
        String sql="select value from colla_ut where userid=? and tagid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, uid);
            pstmt.setInt(2, tid);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                value=rs.getDouble("value");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborativeUTDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
}
