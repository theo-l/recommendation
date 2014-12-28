/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

import java.util.List;

/**
 *
 * @author theo
 * 用户的邻居用户，用在协同过滤算法中表示用户的邻居用户（配置相应的个数）
 */
public class UserNeighbor {

    private int count;
    private int uid;
    private List<SimNode> neighbors;
}
