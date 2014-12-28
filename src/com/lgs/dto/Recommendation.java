/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dto;

import com.lgs.bean.Resource;
import com.lgs.bean.UserPro;
import com.lgs.bean.Tag;
import java.util.List;

/**
 *
 * @author theo
 */
public class Recommendation {
    private int uid;//中间组件中的用户id值
    private List<Resource> resources;
    private UserPro userpro;
    private List<Tag> tags;
    
    public UserPro getUserProByUid(int uid){
        return null;
    }
    
    public List<Resource> RecommendResourcesByUid(int uid){
        return null;
    }
    
    public List<Tag> RecommendTagsByUid(int uid){
        return null;
    }
}
