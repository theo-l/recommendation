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
public class UserTag {
    private int id;
    private int uid;
    private int tid;
    private int count;
    private String url;
    private Date date;
    
    public UserTag(){
    	
    }
    
    public String toString(){
    	return "userid:"+getUid()+"-tagid:"+getTid()+"-date:"+getDate();
    }
    
    public UserTag(int uid,int tid,Date date){
    	this.uid=uid;
    	this.tid=tid;
    	this.date=date;
    }

    public void setProperty(int uid,int tid,Date date){
    	this.uid=uid;
    	this.tid=tid;
    	this.date=date;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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
    
}
