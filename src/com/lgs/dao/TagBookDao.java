/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.TagBook;
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
public class TagBookDao extends BaseDao{
	
	public Statement stmt;
	
	public TagBookDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    //根据标签的id获取用书籍表示的标签向量
    public TagBook getTagBookByTid(int tid){
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
    public boolean saveTagBook(TagBook tb){

    	String sql="insert into tag_book(tagid,bookid,date) values("+tb.getTid()+","+tb.getBid()+",'"+tb.getDate()+"')";
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
    
    public Vector getDistributionTagBookByTid(int tid){
        Vector v=new Vector();
        v.setVid(tid);
        
        String sql="select bookid,count(bookid) count from tag_book where tagid=? group by bookid";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, tid);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                Integer key=rs.getInt("bookid");
                Double value=rs.getDouble("count");
                v.addElement(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v;
    }
    
    public Vector getProjectTagBookByTid(int tid){
        Vector v=new Vector();
        v.setVid(tid);
        
        String sql="select bookid from tag_book where tagid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, tid);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                Integer key=rs.getInt("bookid");
                Double value=1.0;
                
                v.addElement(key, value);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TagBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
        
        return v;
    }
}
