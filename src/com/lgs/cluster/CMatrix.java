package com.lgs.cluster;

import java.util.ArrayList;
import java.util.List;

//聚类矩阵，在此利用向量的方式进行表示
public class CMatrix {

	private  List<CNode> matrix;
	
	public CMatrix(){
		
		this.matrix=new ArrayList<CNode>();
	}
	
	public void addVector(CNode node){
		this.matrix.add(node);
	}
	
	public List<CNode> getMatrix(){
		return this.matrix;
	}
	
	public CNode getNodeById(int id){
		CNode node=null;
		
		for(CNode n:matrix){
			if(n.getNodeid()==id){
				node=n;
			}
		}
		
		return node;
	}
}
