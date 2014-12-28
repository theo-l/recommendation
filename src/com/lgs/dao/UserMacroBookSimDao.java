/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class UserMacroBookSimDao extends BaseDao{
    
    
    //将用户的书籍局部相似性进行存储
    public void saveUserMacroBookSim(int userid,int bookid,int simbookid,BigDecimal value){
        String sql="insert into user_macro_booksim(userid,bookid,simbookid,value) values(?,?,?,?)";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, userid);
            pstmt.setInt(2, bookid);
            pstmt.setInt(3, simbookid);
            pstmt.setBigDecimal(4, value);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserMacroBookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
