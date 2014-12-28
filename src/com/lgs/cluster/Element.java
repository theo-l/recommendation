package com.lgs.cluster;

//矩阵中向量元素
public class Element {

	private int id;
	private double sim;
	
	public Element(){
		
	}

	public Element(int id){
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSim() {
		return sim;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}
	
}
