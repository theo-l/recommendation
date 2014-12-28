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
public class BookUser {

    public BookUser(int bid, int uid, Date date) {
		super();
		this.bid = bid;
		this.uid = uid;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	
	public void setProperty(int bookid,int userid,Date date){
		this.bid=bookid;
		this.uid=userid;
		this.date=date;
	}
	
	 public String toString(){
	    	return "bookid:"+getBid()+"-userid:"+getUid()+"-date:"+getDate();
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BookUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	private int id;
    private int bid;
    private int uid;
    private String url;
    private int count;
    private Date date;
}
