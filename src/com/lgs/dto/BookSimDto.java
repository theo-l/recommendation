/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dto;

import com.lgs.bean.Vector;
import com.lgs.dao.BookDao;
import com.lgs.dao.BookSimDao;
import com.lgs.dao.BookTagDao;
import com.lgs.similar.CosinSimilar;
import com.lgs.similar.DiceSimilar;
import com.lgs.similar.JaccardSimilar;
import com.lgs.similar.MatchSimilar;
import com.lgs.similar.Method;
import com.lgs.similar.OverlapSimilar;
import com.lgs.similar.Type;
import com.lgs.similar.VectorSimilar;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author acer 处理书籍相似性的类
 */
public class BookSimDto {

	private int method; 					// 计算相似性的方法
	private int type; 						// 数据降维策略类型
	private BookDao bDao; 					// 书籍数据库访问对象
	private BookTagDao btDao; 				// 书籍-标签数据库访问对象
	private VectorSimilar similar;			// 相似性方法对象
	private List<Integer> allBooks; 		// 所有的书籍id存储
	private BookSimDao bsDao; 				// 标签相似性数据访问对象
	private String tbName;

	public static void main(String[] args) {
		BookSimDto bsDto = new BookSimDto();

		// 设置计算相似性的方法类型
		bsDto.setMethod(Method.Dice);
		// 设置数据降维类型
		bsDto.setType(Type.Project);

		// 对该业务组件进行配置
		bsDto.config();

		// 根据之前的配置生成书籍相似性矩阵
		bsDto.generateBookSimMatrix();
	}

	public BookSimDto() {
		this.bDao = new BookDao();

		this.btDao = new BookTagDao();

		this.setAllBooks(bDao.getAllBooks());
	}

	/*
	 * 根据相似性方法以及数据降维策略进行配置具体的相似性计算方法
	 */
	public void config() {
		switch (this.method) {
		case Method.Cosine:
			this.setSimilar(new CosinSimilar());
			break;
		case Method.Dice:
			this.setSimilar(new DiceSimilar());
			break;
		case Method.Jaccard:
			this.setSimilar(new JaccardSimilar());
			break;
		case Method.Match:
			this.setSimilar(new MatchSimilar());
			break;
		case Method.Overlap:
			this.setSimilar(new OverlapSimilar());
			break;
		default:
			break;
		}
		this.similar.setType(this.getType());
		
		//设置数据访问对象中的method  和 type 确定该对象访问不同的相似性数据表
		bsDao = new BookSimDao(this.getMethod(), this.getType());
	}

	// 得到每本书籍的向量，分别为投影和分布式进行相似计算时提供数据
	public List<Vector> getBookVectors(int size) {
		
		List<Vector> books = new ArrayList<Vector>();
		for (int i = 0; i < size; i++) {
			Vector v = null;
			switch (this.getType()) {
			case Type.Project:
				v = this.btDao.getProjectBookTagByBid(this.getAllBooks().get(i));
				break;
			case Type.Distribution:
				v = this.btDao.getDistributionBookTagByBid(this.getAllBooks()
						.get(i));
				break;
			default:
				break;
			}

			System.out.println(v.getVid());
			books.add(v);
		}

		return books;
	}
	
	//按照投影降维策略生成相似性矩阵
	public void generateBookProjectSimMatrix() {

		// 获得所有被标注书籍的id值
		int size = this.getAllBooks().size();
//		size = 100;

		List<Vector> books = this.getBookVectors(size);
		// for(int i=0;i<size;i++){
		// Vector
		// v=this.btDao.getProjectBookTagByBid(this.getAllBooks().get(i));
		// System.out.println(v.getVid());
		// books.add(v);
		// }

		for (int i = 0; i < size - 1; i++) {
			Vector book1 = books.get(i);
			for (int j = i + 1; j <= size - 1; j++) {
				Vector book2 = books.get(j);

				// 根据相似性方法以及策略计算两个书籍向量的相似性
				double sim = this.getSimilar().getSimilar(book1, book2);

				 if(sim>0){
				System.out.println(book1);
				System.out.println(book2);
				System.out.printf("tag %d and tag %d : similar is %.5f%n",
						book1.getVid(), book2.getVid(), sim);
				 bsDao.saveBookSim(book1.getVid(), book2.getVid(), sim);
				 }
			}
		}

	}

	//按照分布式降维策略生成相似矩阵
	public void generateBookDistributionSimMatrix() {
		this.generateBookProjectSimMatrix();
	}

	//按照宏观降维策略生成相似矩阵
	public void generateBookMacroSimMatrix() {
		// 首先得到所有被标注书籍的id集合

		// 对集合中的每对标签
	}

	//按照协同过滤降维策略生成相似矩阵
	public void generateBookCollaborativeSimMatrix() {

	}

	// 相似性矩阵生成的控制中心
	public void generateBookSimMatrix() {
		switch (this.getType()) {
		case Type.Project:
			this.generateBookProjectSimMatrix();
			break;
		case Type.Distribution:
			this.generateBookDistributionSimMatrix();
			break;
		case Type.Macro:
			this.generateBookMacroSimMatrix();
			break;
		case Type.Collaborative:
			this.generateBookCollaborativeSimMatrix();
			break;
		default:
			break;
		}

	}

	/**
	 * @return the method
	 */
	public int getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(int method) {
		this.method = method;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the bDao
	 */
	public BookDao getbDao() {
		return bDao;
	}

	/**
	 * @param bDao
	 *            the bDao to set
	 */
	public void setbDao(BookDao bDao) {
		this.bDao = bDao;
	}

	/**
	 * @return the btDao
	 */
	public BookTagDao getBtDao() {
		return btDao;
	}

	/**
	 * @param btDao
	 *            the btDao to set
	 */
	public void setBtDao(BookTagDao btDao) {
		this.btDao = btDao;
	}

	/**
	 * @return the similar
	 */
	public VectorSimilar getSimilar() {
		return similar;
	}

	/**
	 * @param similar
	 *            the similar to set
	 */
	public void setSimilar(VectorSimilar similar) {
		this.similar = similar;
	}

	/**
	 * @return the allBooks
	 */
	public List<Integer> getAllBooks() {
		return allBooks;
	}

	/**
	 * @param allBooks
	 *            the allBooks to set
	 */
	public void setAllBooks(List<Integer> allBooks) {
		this.allBooks = allBooks;
	}

}
