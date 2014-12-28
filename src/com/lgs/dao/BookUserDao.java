/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.BookUser;
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
public class BookUserDao extends BaseDao{
    
	private Statement stmt;
	
	public BookUserDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public BookUser getBookUserByBid(int bid){
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
    
    public boolean saveBookUser(BookUser bu){
    	String sql="insert into book_user(bookid,userid,date) values("+bu.getBid()+","+bu.getUid()+",'"+bu.getDate()+"')";
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
    
    public Vector getDistributionBookUserByBid(int bid){
        Vector v=new Vector();
        v.setVid(bid);
        String sql="select userid,count(userid) count from book_user where bookid=? group by userid ";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            
            pstmt.setInt(1, bid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("userid");
                Double value=rs.getDouble("count");
                
                v.addElement(key, value);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v;
    }
    
    public Vector getProjectBookUserByBid(int bid){
        Vector v=new Vector();
        v.setVid(bid);
        
        String sql="select distinct userid from book_user where bookid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, bid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("userid");
                Double value=1.0;
                v.addElement(key, value);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
}
