package com.lgs.dto;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.lgs.bean.BookTag;
import com.lgs.bean.BookUser;
import com.lgs.bean.TagBook;
import com.lgs.bean.TagUser;
import com.lgs.bean.UserBook;
import com.lgs.bean.UserTag;
import com.lgs.bean.UserTagBook;
import com.lgs.dao.BookTagDao;
import com.lgs.dao.BookUserDao;
import com.lgs.dao.TagBookDao;
import com.lgs.dao.TagUserDao;
import com.lgs.dao.UserBookDao;
import com.lgs.dao.UserTagBookDao;
import com.lgs.dao.UserTagDao;


/*
 * 分解user_tag_book数据结构
 * 分解为user_tag、user_book、tag_book,tag_user、book_user、book_tag
 */
public class DestractUserTagBook {

	private int allTaggingCount;
	private List<UserTagBook> utb;
	private UserTagBookDao utbDao;
	private UserTagDao utDao;
	private UserBookDao ubDao;
	private TagUserDao tuDao;
	private TagBookDao tbDao;
	private BookUserDao buDao;
	private BookTagDao btDao;
	
	private UserTag ut;
	private UserBook ub;
	private TagUser tu;
	private TagBook tb;
	private BookTag bt;
	private BookUser bu;
	
	public  static void main(String[] args){
		
		DestractUserTagBook dutb=new DestractUserTagBook();
		dutb.destractTaggingData(0);
	}
	
	public DestractUserTagBook(){
		
		this.init();
	}
	
	/*
	 * 初始化数据库访问对象
	 */
	public void init(){
		this.utDao=new UserTagDao();
		this.ubDao=new UserBookDao();
		this.tbDao=new TagBookDao();
		this.tuDao=new TagUserDao();
		this.buDao=new BookUserDao();
		this.btDao=new BookTagDao();
		this.utbDao=new UserTagBookDao();
		this.utb=new ArrayList<UserTagBook>();
		this.setAllTaggingCount(this.utbDao.getAllTaggingCount());
		this.ub=new UserBook();
		this.ut=new UserTag();
		this.tb=new TagBook();
		this.tu=new TagUser();
		this.bu=new BookUser();
		this.bt=new BookTag();
	}
	
	
	
	public int getAllTaggingCount() {
		return allTaggingCount;
	}

	public void setAllTaggingCount(int allTaggingCount) {
		this.allTaggingCount = allTaggingCount;
	}

	public List<UserTagBook> getUtb() {
		return utb;
	}

	public void setUtb(List<UserTagBook> utb) {
		this.utb = utb;
	}

	/*
	 * 获得user_tag_book数据结构
	 */
	public  void destractTaggingData(int begin){
		
		
		int offset=1000;
	
		if(begin<this.getAllTaggingCount()){
		
		/*
		 * 从user_tag_book数据库中取出begin-offset之间的数据进行处理
		 * 
		 */
		this.setUtb(this.utbDao.getTagging(begin, offset));
		
		for(UserTagBook entity:this.getUtb()){
			
			System.out.println("\n\n\n------------"+entity.getId()+"----------------");
			begin++;
			int userid=entity.getUid();
			int tagid=entity.getTid();
			int bookid=entity.getBid();
			Date date=entity.getDate();
			
			//user_tag实例
			ut.setProperty(userid, tagid, date);
			this.utDao.saveUserTag(ut);
			System.out.println(ut);
			//user_book实例
			ub.setProperty(userid, bookid, date);
			this.ubDao.saveUserBook(ub);
			System.out.println(ub);
			
			//tag_book实例
			tb.setProperty(tagid, bookid, date);
			this.tbDao.saveTagBook(tb);
			System.out.println(tb);
			//tag_user实例
			tu.setProperty(tagid, userid, date);
			this.tuDao.saveTagUser(tu);
			System.out.println(tu);
			
			//book_user实例
			bu.setProperty(bookid, userid, date);
			this.buDao.saveBookUser(bu);
			System.out.println(bu);
			//book_tag实例
			bt.setProperty(bookid, tagid, date);
			this.btDao.saveBookTag(bt);
			System.out.println(bt);
			

			
		}
		
		//清除用过的对象
		this.utb.clear();
		this.destractTaggingData(begin);
		}else{
			return ;
		}
		
	}
}
