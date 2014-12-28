/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

/**
 *
 * @author theo
 */ 
public class Tag {
    private int id;
    private String name;
    private int count;
    private String url;
    private double tfidf;
    
    public Tag(){
    	
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getTfidf() {
		return tfidf;
	}
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}
    
    
}
