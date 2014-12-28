/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.UserTag;
import com.lgs.bean.Vector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class UserTagDao extends BaseDao{
	
	private Statement stmt;
    
    public UserTagDao(){
        try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public UserTag getUserTagByUid(int uid){
        return null;
    }
    
    public boolean saveUserTag(UserTag ut){
    	String sql="insert into user_tag(userid,tagid,count,date,url) values("+ut.getUid()+","+ut.getTid()+","+1+",'"+ut.getDate()+"',"+"' ')";
    	boolean flag=true;
    	try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			flag=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return flag;
    }
    
    public Vector getDistributionUserProfileByUid(int uid){
        Vector v=new Vector();
        v.setVid(uid);
        
        String sql="select tagid,count from user_tag where userid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, uid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("tagid");
                Double value=rs.getDouble("count");
                v.getElements().put(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
    }
    
    public Vector getProjectUserProfileByUid(int uid){
        Vector v=new Vector();
        v.setVid(uid);
        
        String sql="select tagid from user_tag where userid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, uid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("tagid");
                Double value=1.0;
                v.addElement(key, value);
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
        
    }
}
