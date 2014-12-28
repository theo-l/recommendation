/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.User;
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
public class UserDao extends BaseDao{
    
    public User getUserByUid(int uid){
        return null;
    }
    
    public List<Integer> getAllUsersCount(){
        ArrayList<Integer> users=new ArrayList<Integer>();
        String sql="select distinct userid userid  from user_tag_book";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
               int userid=rs.getInt("userid");
               users.add(userid);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
}
