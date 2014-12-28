package com.lgs.dto;

import java.util.ArrayList;
import java.util.List;

import com.lgs.bean.Tag;

/*
 * 处理与标签相关的业务实现
 */
public class TagDto {

	
	public List<Tag> getPopularTags(){
		
		List<Tag> popular=new ArrayList<Tag>();
		
		return popular;
	}
	
	public List<Tag> getRelatedTagByTid(int tid){
		List<Tag> relatedTag=new ArrayList<Tag>();
		return relatedTag;
	}
}
