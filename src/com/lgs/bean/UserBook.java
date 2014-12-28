/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

import java.util.Date;

/**
 *
 * @author theo
 */
public class UserBook {
    private int id;
    private int bid;
    private int uid;
    private int count;
    private String url;
    private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UserBook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBook(int bid, int uid, Date date) {
		super();
		this.bid = bid;
		this.uid = uid;
		this.date = date;
	}
	public void setProperty(int userid,int bookid,Date date){
		this.bid=bookid;
		this.uid=bookid;
		this.date=date;
	}
    
	 public String toString(){
	    	return "userid:"+getUid()+"-bookid:"+getBid()+"-date:"+getDate();
	    }
    
}
