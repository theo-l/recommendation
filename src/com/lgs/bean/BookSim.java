/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

import java.util.List;

/**
 *
 * @author theo
 */
public class BookSim {
    private int id;
    private int bookid;
    private int simbookid;
    private double value;
    
    public BookSim(){
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public int getSimbookid() {
		return simbookid;
	}

	public void setSimbookid(int simbookid) {
		this.simbookid = simbookid;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public BookSim(int id, int bookid, int simbookid, double value) {
		super();
		this.id = id;
		this.bookid = bookid;
		this.simbookid = simbookid;
		this.value = value;
	}
}
