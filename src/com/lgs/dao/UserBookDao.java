/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.UserBook;
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
public class UserBookDao extends BaseDao {
	
	private Statement stmt;

	
	public UserBookDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public UserBook getUserBookByUid(int uid){
        return null;
    }
    
    public void closeStatement(){
    	try {
			this.stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean saveUserBook(UserBook ub){
    	String sql="insert into user_book(userid,bookid,date) values("+ub.getUid()+","+ub.getBid()+",'"+ub.getDate()+"')";
    	boolean flag=true;
    	try {
			stmt.execute(sql);
		} catch (SQLException e) {
			flag=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return flag;
    }
    
    public Vector getDistributionUserBookByUid(int uid){
        Vector v=new Vector();
        v.setVid(uid);
        
        String sql="select bookid , count from user_book where userid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, uid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("bookid");
                Double value=rs.getDouble("count");
               
                v.addElement(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
    }
    
    //按照投影直接从用户-书籍上下文中获取用户的书籍向量，其中的元素的值为1
    public Vector getProjectUserBookByUid(int uid){
        Vector v=new Vector();
        v.setVid(uid);
        
        String sql="select bookid from user_book where userid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, uid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("bookid");
                Double value=1.0;
                v.addElement(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
    }
}
