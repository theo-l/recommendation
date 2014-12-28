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
 * 访问用户的局部标签相似性的访问器
 */
public class UserMacroTagSimDao extends BaseDao {
    
    public void saveUserMacroTagSim(int userid,int tagid,int simtagid,BigDecimal value){
        String sql="insert into user_macro_tagsim(userid,tagid,simtagid,value) values(?,?,?,?)";
        try {
            PreparedStatement pstmt=this.getConnection().prepareStatement(sql);
            pstmt.setInt(1, userid);
            pstmt.setInt(2, tagid);
            pstmt.setInt(3, simtagid);
            pstmt.setBigDecimal(4, value);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserMacroTagSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
