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
public class OverlapSimilar extends VectorSimilar{

    @Override
    public double getSimilar(Vector v1, Vector v2) {
        switch(this.getType()){
            case Type.Project:return this.OverlapProjectSim(v1, v2); 
            case Type.Distribution:return this.OverlapDistributionSim(v1, v2);
            case Type.Macro:return this.OverlapMacroSim(v1, v2);
            case Type.Collaborative:return this.OverlapCollaborativeSim(v1, v2);
            default:return 0;
        }
    }
    
    public double OverlapProjectSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double OverlapDistributionSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double OverlapMacroSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double OverlapCollaborativeSim(Vector v1,Vector v2){
        return 0;
    }

    @Override
    public void setType(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
