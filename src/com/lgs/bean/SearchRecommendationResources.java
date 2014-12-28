/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

import java.util.List;

/**
 *
 * @author theo
 */
public class SearchRecommendationResources {
    private int uid;//用户id值，用来确定用户的信息，如果没有登录按照公共推荐，否则进行个性化资源推荐
    private List<Resource> resources;//用户检索时推荐的资源集合
}
