package com.lgs.cluster;

import java.util.ArrayList;
import java.util.List;

public class CNode {


	private int nodeid;
	private List<Element> elements;
	private int type;//说明聚类的类型，依照阀值来考虑
	
	
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	
	public void addElement(int id,double value){
		Element element=new Element();
		element.setId(id);
		element.setSim(value);
		this.elements.add(element);
	}
	
	public void addElement(Element element){
		this.elements.add(element);
	}
	public CNode(){
		elements=new ArrayList<Element>();
	}
	
	public CNode(int id){
		this.nodeid=id;
		elements=new ArrayList<Element>();
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	

	
	
	    
	
	
}
