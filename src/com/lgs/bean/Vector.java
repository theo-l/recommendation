/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author acer
 */
public class Vector {
    private int vid;
    private HashMap<Integer,Double> elements;
    
    public Vector(){
        this.setElements();
    }
    public void setElements(){
        this.elements=new HashMap<Integer,Double>();
    }
    
    public void setVid(int id){
        this.vid=id;
    }
    public int getVid(){
        return this.vid;
    }
    
    public void addElement(Integer key,Double value){
        this.elements.put(key, value);
    }
    public HashMap<Integer,Double> getElements(){
        return this.elements;
    }
    
    public String toString(){
        String result="vid: "+this.getVid()+"---\n[";
        Integer[] keys=new Integer[this.elements.keySet().size()];
        this.elements.keySet().toArray(keys);
        Arrays.sort(keys);
        for(int i=0;i<keys.length;i++){
            result+=(keys[i]+"-"+this.elements.get(keys[i])+" ");
        }
        result+="]\n";
        
        return result;
    }
}
