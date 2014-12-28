package com.lgs.dto;

import java.util.ArrayList;
import java.util.List;

import com.lgs.bean.Book;
import com.lgs.bean.Tag;

/*
 * 处理与书籍相关的业务实现
 */
public class BookDto {

	public List<Book> getPopularBooks(){
		
		List<Book> popular=new ArrayList<Book>();
		return popular;
	}
	
	/*
	 * 根据书籍的id获得与该书籍相关的书籍集合
	 */
	public List<Book> getRelatedBooksByBid(int bid){
		List<Book> relatedBook=new ArrayList<Book>();
		return relatedBook;
	}
	
	public List<Tag> getRelatedTagByBid(int bid){
		List<Tag> relatedTag=new ArrayList<Tag>();
		return relatedTag;
	}
}
