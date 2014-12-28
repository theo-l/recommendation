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
public class MatchSimilar extends VectorSimilar{

    private int type;
    
    @Override
    public double getSimilar(Vector v1, Vector v2) {
      switch(this.getType()){
          case Type.Project:return this.MatchProjectSim(v1, v2);
          case Type.Distribution:return this.MatchDistributionSim(v1, v2);
          case Type.Macro:return this.MatchMacroSim(v1, v2);
          case Type.Collaborative:return this.MatchCollaborativeSim(v1, v2);
          default :return 0;
      }
    }

    public double MatchProjectSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double MatchDistributionSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double MatchMacroSim(Vector v1,Vector v2){
        return 0;
    }
    
    public double MatchCollaborativeSim(Vector v1,Vector v2){
        return 0;
    }
    
    @Override
    public void setType(int type) {
       this.type=type;
    }

    @Override
    public int getType() {
        return this.type;
    }
    
}
