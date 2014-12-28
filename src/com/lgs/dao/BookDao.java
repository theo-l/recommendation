/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class BookDao extends BaseDao{
    
    public Book getBookByBid(int bid){
        return null;
 
    }
    
    //获得系统中所有书籍的id号
    public List<Integer> getAllBooks(){
       List<Integer> books =new ArrayList<Integer>();
       String sql="select distinct bookid bookid from user_tag_book";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                int bookid=rs.getInt("bookid");
                books.add(bookid);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return books;
    
    }
}
