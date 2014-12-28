/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.Tag;
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
public class TagDao extends BaseDao{
    
	//根据标签的id号来获得标签
    public Tag getTagByTid(int tid){
    	
    	String sql="select * from tags where id=?";
    	Tag tag=new Tag();
    	
    	try {
			PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
			
			pstmt.setInt(1, tid);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()){
				int 		id=rs.getInt("id");
				String 		name=rs.getString("name");
				String 		url=rs.getString("url");
				int 		count=rs.getInt("count");
				
				tag.setId(id);
				tag.setName(name);
				tag.setUrl(url);
				tag.setCount(count);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return tag;
    }
    
    
    //获取所有参与标注的标签集合
    public List<Integer> getAllTags(){
        List<Integer> tags=new ArrayList<Integer>();
        String sql="select distinct tagid tagid from tag_book";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()){
                int bookid=rs.getInt("tagid");
                tags.add(bookid);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tags;
    }
    
}
