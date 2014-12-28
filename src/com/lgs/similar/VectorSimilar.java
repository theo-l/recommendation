/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.similar;

import com.lgs.bean.Vector;

/**
 *
 * @author acer
 */
public abstract class VectorSimilar {
    
    public abstract double getSimilar(Vector v1,Vector v2);
    public abstract void setType(int type);
    public abstract int getType();
}
