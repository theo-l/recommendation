/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.UserTagBook;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class UserTagBookDao extends BaseDao{
	
	private Statement stmt;
	
	public UserTagBookDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /*
     * public List<UserTagBook> getTaggingByUid(int uid)
     * 根据用户的Id值获取该用户的所有标注行为
     */
    public List<UserTagBook> getTaggingByUid(int uid){
        List<UserTagBook> taggings=new ArrayList<UserTagBook>();
        String sql="select userid,tagid,bookid from user_tag_book where userid="+uid;
        try {
            
            ResultSet rs=stmt.executeQuery(sql);
            
            while(rs.next()){
                int userid=rs.getInt("userid");
                int tagid=rs.getInt("tagid");
                int bookid=rs.getInt("bookid");
                
                UserTagBook utb=new UserTagBook(userid,tagid,bookid);
                taggings.add(utb);
            }
            
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserTagBookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taggings;
        
    }
    
    public void closeStatement(){
    	try {
			this.stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public int getAllTaggingCount(){
    	int count=0;
    	String sql="select count(*) count from user_tag_book";
    	try {
			Statement	 stmt=this.getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				count=rs.getInt("count");
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return count;
    }
    
    public List<UserTagBook> getTagging(int begin,int offset){
    	List<UserTagBook> utb=new ArrayList<UserTagBook>();
    	
    	String sql="select id,userid,tagid,bookid,date from user_tag_book limit ?,?";
    	
    	try {
			PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
		
			pstmt.setInt(1, begin);
			pstmt.setInt(2, offset);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()){
				UserTagBook entity=new UserTagBook();
				entity.setId(rs.getInt("id"));
				entity.setUid(rs.getInt("userid"));
				entity.setTid(rs.getInt("tagid"));
				entity.setBid(rs.getInt("bookid"));
				entity.setDate(rs.getDate("date"));
				utb.add(entity);
			}
			rs.close();
			pstmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return utb;
    }
}
