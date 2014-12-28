package com.lgs.cluster;

import java.util.ArrayList;
import java.util.List;

public class ClusterNode {

	private int id;
	private int bookid;
	private int level;
	private List<Integer> child;
	
	
	
	public ClusterNode(){
		this.child=new ArrayList<Integer>();
	}
	
	public ClusterNode(int id){
		this.id=id;

		this.child=new ArrayList<Integer>();
	}
	
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int level){
		this.level=level;
	}
	
	
	
	//添加子节点时，仅考虑将节点中的书籍Id值加入到新的类别中
	public void addChild(ClusterNode node){
		
		//遍历添加的节点的字节点
		for(Integer i:node.child){
			this.child.add(i);
		}
		node.child.clear();//清除原有节点中的数据
	}
	
	public void addBookId(int bookid){
		this.child.add(bookid);
	}
	
	public boolean isBookIdIn(int bookid)
	{
		boolean flag=false;
		for(Integer i:this.child){
			if(i==bookid){
				flag=true;
			}
		}
		
		return flag;
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

	public List<Integer> getChild() {
		return child;
	}

	public void setChild(List<Integer> child) {
		this.child = child;
	}
	
	
	public String toString(){
		String result="";
		if(this.bookid!=0){
			result="cluster id :"+ this.getId()+"  book id :"+this.getBookid();
		}else{
			result="cluster id :"+this.getId()+"\n";
			for(Integer i:this.child){
				result+="bookid: "+i+"  ";
			}
			result+="\n";
		}
		
		return result;
	}
	
	public String getIndent(int indent){
		String result="";
		for(int i=0;i<=indent;i++){
			result+=" ";
		}
		indent++;
		return result;
	}

	private static int indent=0;
	
	
}
