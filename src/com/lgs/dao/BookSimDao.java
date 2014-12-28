/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dao;

import com.lgs.bean.BookSim;
import com.lgs.similar.Method;
import com.lgs.similar.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 * 用来对相似书籍进行操作的类
 */
public class BookSimDao extends BaseDao{
	
    
    private int method;//相似性计算方法类型
    private int type;//降维策略
    private String tbName;//数据表的名称
    private Statement stmt;
    
    public static void main(String[] args){
        BookSimDao bsDao=new BookSimDao(Method.Cosine,Type.Project);
        
    }
    
    
    public BookSimDao(int method,int type){
        this.setMethod(method);
        this.setType(type);
        this.setTbName();
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public BookSimDao(){
        try {
            this.stmt=this.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try {
            this.stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //将书籍相似性进行存储
    public void saveBookSim(int bookid,int simbookid,double value){
        String sql="insert into "+this.getTbName()+"(bookid,simbookid,value) values("+bookid+","+simbookid+","+value+")";
        try {
            this.stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //按照书籍的id获得两者之间的相似性值
    public double getValueBB(int bookid,int simbookid){
        double value=0;
        String sql="select value from "+this.getTbName()+" where bookid="+bookid +" and simbookid="+simbookid;
        try {
           ResultSet rs=this.stmt.executeQuery(sql);
           
           while(rs.next()){
               value=rs.getDouble("value");
           }
           rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookSimDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public BookSim getBookSimByBid(int bid){
        return null;
    }
    
    public boolean saveBookSim(BookSim bs){
        return false;
    }

    /**
     * @return the method
     */
    public int getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(int method) {
        this.method = method;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the tbName
     */
    public String getTbName() {
        return tbName;
    }

    /**
     * @param tbName the tbName to set
     */
    public void setTbName() {
        switch(this.getMethod()){
            case Method.Match:{
                switch(this.getType()){
                    case Type.Project:this.tbName="bookmatch_prosim";break;
                    case Type.Distribution:this.tbName="bookmatch_dissim";break;
                    case Type.Macro:this.tbName="bookmatch_macrosim";break;
                    case Type.Collaborative:this.tbName="bookmatch_colsim";break;
                    default:break;
                }
            };break;
            case Method.Overlap:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="bookoverlap_prosim";break;
                    case Type.Distribution:this.tbName="bookoverlap_dissim";break;
                    case Type.Macro:this.tbName="bookoverlap_macrosim";break;
                    case Type.Collaborative:this.tbName="bookoverlap_colsim";break;
                    default:break;
                }
            };break;
            case Method.Jaccard:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="bookjac_prosim";break;
                    case Type.Distribution:this.tbName="bookjac_dissim";break;
                    case Type.Macro:this.tbName="bookjac_macrosim";break;
                    case Type.Collaborative:this.tbName="bookjac_colsim";break;
                    default:break;
                }
            };break;
            case Method.Dice:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="bookdice_prosim";break;
                    case Type.Distribution:this.tbName="bookdice_dissim";break;
                    case Type.Macro:this.tbName="bookdice_macrosim";break;
                    case Type.Collaborative:this.tbName="bookdice_colsim";break;
                    default:break;
                }
            };break;
            case Method.Cosine:{
                 switch(this.getType()){
                    case Type.Project:this.tbName="bookcosine_prosim";break;
                    case Type.Distribution:this.tbName="bookcosine_dissim";break;
                    case Type.Macro:this.tbName="bookcosine_macrosim";break;
                    case Type.Collaborative:this.tbName="bookcosine_colsim";break;
                    default:break;
                }
            };break;
            default:break;
        }
    }
}
