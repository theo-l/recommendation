/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.TagUser;
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
public class TagUserDao extends BaseDao{
	
	public Statement stmt;
	
	public TagUserDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public Vector getDistributionTagUserByTid(int tid){
        Vector v=new Vector();
        v.setVid(tid);
        
        String sql="select userid,count(userid) count from tag_user where tagid=? group by userid";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, tid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("userid");
                Double value=rs.getDouble("count");
                
                v.addElement(key, value);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TagUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v;
    }
    
    public Vector getProjectTagUserByTid(int tid){
        Vector v=new Vector();
        v.setVid(tid);
        
        String sql="select distinct userid from tag_user where tagid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, tid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("userid");
                Double value=1.0;
                
                v.addElement(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
    }
    
    public void closeStatement(){
    	try {
			this.stmt.close()	;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean saveTagUser(TagUser tu){
    	String sql="insert into tag_user(tagid,userid,date) values("+tu.getTid()+","+tu.getUid()+",'"+tu.getDate()+"')";
    	boolean flag=true;
    	try {
    		this.stmt.execute(sql);
    	} catch (SQLException e) {
			flag=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return flag;
    }
}
