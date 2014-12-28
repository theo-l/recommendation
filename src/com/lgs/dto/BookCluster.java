package com.lgs.dto;

import java.util.ArrayList;
import java.util.List;

import com.lgs.bean.BookSim;
import com.lgs.cluster.ClusterNode;
import com.lgs.dao.BookDiceProSimDao;
import com.lgs.dao.BookTagDao;


//书籍聚类业务处理类

public class BookCluster{
	
	public int bookSize;//所有书籍的数量，用来控制新生成聚类的类别
	public List<ClusterNode>  clusters;//用来存储所有的聚类类别
	public  BookDiceProSimDao bdpsDao;
	public BookTagDao btDao;
	public double[] levels={0.8,0.6,0.5,0.4,0.3};//五个聚类阀值用来约束聚类方法
//	public int[] done;//用来记录聚类过程
	public List<Integer> done;
//	public int index;//用来跟踪done数组
	public int decrease;//用来跟踪初始聚类的长度减少标记
	public boolean debug;
	
	public BookCluster(){
		clusters=new ArrayList<ClusterNode>();
		bdpsDao=new BookDiceProSimDao();
		btDao=new BookTagDao();
	}
	
	public void setDebug(boolean d){
		this.debug=d;
	}
	public boolean getDebug(){
		return this.debug;
	}

	public void config(){
		setBookSize();
		initClusters();
		done=new ArrayList<Integer>();
//		index=0;
		decrease=bookSize;
	}
	//设置bookSize的值
	public void setBookSize(){
		this.bookSize=btDao.getDistictBookid().length;
	}
	
	//聚类类别初始化
	public void initClusters(){
		int[] books=btDao.getDistictBookid();
		for(int i=0;i<this.bookSize;i++){
		  	ClusterNode node=new ClusterNode();
		  	node.setId(i);
		  	node.setBookid(books[i]);
		  	node.addBookId(books[i]);
//		  	node.setChild(null);
		  	clusters.add(node);
		}
	}
	
	//聚类函数，先将相似度较高的节点进行聚类，然后将相似度较低的节点进行聚类，知道所有的初始化节点都已经完成聚类
	//聚类步骤：1.得到满足相似阀值的所有记录，根据记录的书籍id将书籍进行聚类，并从clusters中删除聚类过的节点以及在标记列表中将其id值记录
	public void cluster(int level)	{
		
		if(level>5||done.size()==bookSize){
			return;
		}
		String sql=generateLenSql(level);
		
		if(debug){
			debug(sql);
		}
		
		int len=bdpsDao.getLenBySQL(sql);
		
		debug(len+"");
		//test
//		len=20;
		
		//根据len进行遍历处理所有的书籍id值
		
		int begin=0;
		int offset=1000;
		
		
		//利用while循环遍历所有满足阀值的记录
		while(begin<len){
			sql=generateEntitySql(level,begin,offset);
			if(debug){
				debug(sql);
			}
			List<BookSim> bs=bdpsDao.getSimEntitiesBySQL(sql);
			
			for(BookSim b:bs){
				begin++;
				
				if(begin>len){
					return;
				}
				
				int bookid=b.getBookid();
				int simbookid=b.getSimbookid();
				
				ClusterNode book1=getInitNodeByBookId(bookid);
				ClusterNode book2=getInitNodeByBookId(simbookid);
				
				//从初始聚类中移除已经参与聚类的节点,并将控制初始聚类中节点的数量，保证了聚类中的书籍id值不会出现重复，
				this.cleanInitNodeByBookId(bookid);
				this.cleanInitNodeByBookId(simbookid);
				decrease--;
				decrease--;
			
//				
//				if(debug){
//					debug("bookid: "+bookid);
//					debug("simbookid: "+simbookid);
//				}
				
				if(debug){
					debug(clusters.size()+"");
				}
				
				//创建新的类别
				ClusterNode cluster=new ClusterNode(++bookSize);
				cluster.setBookid(0);//新创建的类别的书籍id都设置为0方便区分
				cluster.setLevel(level);//设置类别的相似度级别
				cluster.addChild(book1);//将初始化的节点添加到类别中
				cluster.addChild(book2);
//				System.out.println("cluster id :"+cluster.getId()+" cluster's bookid :"+cluster.getBookid());
				clusters.add(cluster);//将新建立的类别加入到聚类类别中去
				
				if(debug){
					debug(clusters.size()+"");
				}
				
				
			
				
				
				if(debug){
					debug(clusters.size()+"");
				}
				
				//在聚类跟踪数组中添加聚类过的记录
				if(!isBookIdClustered(bookid)){
				done.add(bookid);
				}
				if(!isBookIdClustered(simbookid)){
					done.add(simbookid);
				}
				if(done.size()>=bookSize){
					return;
				}
//				done[index++]=simbookid;
			}
			
			debug(begin+"");
			this.showClusters();
			
			bs.clear();//每次都进行数据清理以便减少内存占用
		}
		
		
		cluster(++level);//进行其他层次阀值的聚类处理
		
		
		
		
	}
	
	public boolean isBookIdClustered(int bookid){
		boolean  flag=false;
		
		for(Integer i:done){
			if(i==bookid){
				flag=true;
			}
		}
		
		return flag;
	}
	
	public void debug(String d){
		System.out.println(d);
	}
	
	public void showClusters(){
		for(ClusterNode node:clusters){
			if (node.getBookid()==0){
				System.out.println(clusters.size());
				System.out.println(node);
			}
		}
		
//		System.out.println("not zero "+countNoZero+"");

		
	}
	
	public void cleanInitNodeByBookId(int bid){
		int index=0;
		for(int i=0;i<clusters.size();i++){
			
			int bookid=clusters.get(i).getBookid();
			if(bookid!=0&&bookid==bid){
				index=i;
			}else if(bookid==0&&clusters.get(i).isBookIdIn(bid)){
			   index=i;
			}
				}
		clusters.remove(index);
	}
	
	public ClusterNode getInitNodeByBookId(int bid){
		int index=0;
		
		for(int i=0;i<clusters.size();i++){
			
			int bookid=clusters.get(i).getBookid();
			
			if(bookid!=0&&bookid==bid){
				index=i;
			}else if(bookid==0&&clusters.get(i).isBookIdIn(bid)){
			   index=i;
			}
				}
		return clusters.get(index);
	}
	
	
	public String generateEntitySql(int level,int begin,int offset){
		switch(level){
		case 0: return "select id,bookid,simbookid,value from bookdice_prosim where value <0.9 and value >=  "+levels[0]+" limit "+begin+","+offset;

		case 1: return "select id,bbookid,simbookid,value from bookdice_prosim where value <  "+levels[0]+" and value >="+levels[1]+" limit "+begin+","+offset;
		case 2: return "select id,bookid,simbookid,value from bookdice_prosim where value <  "+levels[1]+" and value >="+levels[2]+" limit "+begin+","+offset;
		
		case 3: return "select id,bookid,simbookid,value from bookdice_prosim where value <  "+levels[2]+" and value >="+levels[3]+" limit "+begin+","+offset;
		case 4: return "select id,bookid,simbookid,value from bookdice_prosim where value <  "+levels[3]+" and value >="+levels[4]+" limit "+begin+","+offset;
		default :return "select id,bookid,simbookid,value from bookdice_prosim where value <  "+levels[4]+" limit "+begin+","+offset;
		}
	}
	
	//生成获取相似阀值记录长度的sql
	public String generateLenSql(int level){
		switch(level){
		case 0: return "select count(*) count from bookdice_prosim where value<0.9 and value >=  "+levels[0];

		case 1: return "select count(*) count from bookdice_prosim where value <  "+levels[0]+" and value >="+levels[1];
		case 2: return "select count(*) count from bookdice_prosim where value <  "+levels[1]+" and value >="+levels[2];
		
		case 3: return "select count(*) count from bookdice_prosim where value <  "+levels[2]+" and value >="+levels[3];
		case 4: return "select count(*) count from bookdice_prosim where value <  "+levels[3]+" and value >="+levels[4];
		default :return "select count(*) count from bookdice_prosim where value <  "+levels[4];
		}
	}
	
	public static void main(String[] args){
		BookCluster bc=new BookCluster();
		bc.config();
//		bc.setDebug(true);
		bc.cluster(0); 
	}
	
	
}
