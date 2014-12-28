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
public class BookTag {

    public BookTag(int bid, int tid, Date date) {
		super();
		this.bid = bid;
		this.tid = tid;
		this.date = date;
	}
    public void setProperty(int bookid,int tagid,Date date){
    	this.bid=bookid;
    	this.tid=tagid;
    	this.date=date;
    }
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
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
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
	public BookTag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 public String toString(){
	    	return "Bookid:"+getBid()+"-tagid:"+getTid()+"-date:"+getDate();
	    }
	
	private int id;
    private int bid;
    private int tid;
    private int count;
    private String url;
    private Date date;
}
