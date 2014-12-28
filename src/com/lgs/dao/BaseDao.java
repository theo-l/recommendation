/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author theo
 */
public class BaseDao {

    private Connection conn;
    
    public BaseDao(){
        try {    
            Class.forName("com.mysql.jdbc.Driver");
            this.conn=DriverManager.getConnection("jdbc:mysql://localhost/newbook?useUnicode=true&characterEncoding=utf8","root","mysql");
            if(conn!=null){ 
                System.out.println("success");
            }else{
                System.out.println("failed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection(){
        return this.conn;
    }
    
    public static void main(String[] args){
        BaseDao base=new BaseDao();
    }
}
