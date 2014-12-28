/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.BookTag;
import com.lgs.bean.Vector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class BookTagDao extends BaseDao{
    
	private Statement stmt;
	
	public BookTagDao(){
		try {
			this.stmt=this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public BookTag getBookTagByBid(int bid){
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
    public boolean saveBookTag(BookTag bt){
    	
    	String sql="insert into book_tag(bookid,tagid,count,date) values("+bt.getBid()+","+bt.getTid()+","+1+",'"+bt.getDate()+"')";
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
    
    //按照分布式的形式进行投影得到降维处理的矩阵，其中元素的权值按照用户标注的数量进行计算
    public Vector getDistributionBookTagByBid(int bid){
        Vector v=new Vector();
        v.setVid(bid);
        
        String sql="select tagid,count from book_tag where bookid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, bid);
            
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                Integer key=rs.getInt("tagid");
                Double value=rs.getDouble("count");
                
                v.addElement(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
        return v;
        
    }
    
    //按照直接投影的方式获取书籍—标签向量，其中的元素值为0、1，根据存储方式的设计，只存储值为1的标签
    public Vector getProjectBookTagByBid(int bid){
        Vector v=new Vector();
        v.setVid(bid);
        String sql="select tagid from book_tag where bookid=?";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, bid);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                Integer key=rs.getInt("tagid");
                Double value=1.0;
                v.addElement(key, value);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return v;
        
    }
    
    public int[] getDistictBookid(){
    	int len=0;
    	String sql="select count(distinct bookid) count from book_tag";
    	try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				len=rs.getInt("count");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		//用int数组来存储所有的书籍的id,这样节省内存
		int[]  books=new int[len];
		
		sql="select distinct bookid from book_tag";
		int index=0;
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				books[index++]=rs.getInt("bookid");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
    }
}
