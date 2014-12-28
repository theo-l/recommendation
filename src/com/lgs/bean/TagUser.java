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
public class TagUser {

    public TagUser(int tid, int uid, Date date) {
		super();
		this.tid = tid;
		this.uid = uid;
		this.date = date;
	}
	public TagUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setProperty(int tagid,int userid,Date date){
		this.tid=tagid;
		this.uid=userid;
		this.date=date;
	}
	
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
	private int id;
    private int tid;
    private int uid;
    private int count;
    private String url;
    private Date date;
    
    public String toString(){
    	return "tagid:"+getTid()+"-userid:"+getUid()+"-date:"+getDate();
    }
}
