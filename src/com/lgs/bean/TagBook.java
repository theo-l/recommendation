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
public class TagBook {

    private int id;
    private int tid;
    private int bid;
    private int count;
    private String url;
    private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
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
	public TagBook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TagBook(int tid, int bid, Date date) {
		super();
		this.tid = tid;
		this.bid = bid;
		this.date = date;
	}
    
	public void setProperty(int tagid,int bookid,Date date){
		this.tid=tagid;
		this.bid=bookid;
		this.date=date;
	}
	
	 public String toString(){
	    	return "tagid:"+getTid()+"-bookid:"+getBid()+"-date:"+getDate();
	    }
    
}
