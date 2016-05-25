/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.List;

/**
 *
 * @author PeDeNRiQue
 */
public class DTWBandwidth {
    public static Double DTWDistance(List<Double> s, List<Double> t,int r) {
        int sSize = s.size();
        int tSize = t.size();
        

        Double[][] matrixDTW = new Double[sSize][tSize];  
        
        r = (sSize*r/100);
        r = Math.max(r, Math.abs(sSize-tSize));
        
        for(int i=0;i<sSize;i++){
            for(int j=0;j<tSize;j++){
                matrixDTW[i][j] = Double.MAX_VALUE;
            }
        }
        matrixDTW[0][0] = 0.;
        Double cost;
        for (int i= 1; i<sSize;i++){
                for (int j= Math.max(1, i-r); j<Math.min(tSize, i+r);j++){
                    cost = distance(s.get(i), t.get(j));
                    matrixDTW[i][j] = cost + Math.min(matrixDTW[i-1][j], Math.min(matrixDTW[i][j-1], matrixDTW[i-1][j-1]));
                }
        }

        return matrixDTW[sSize-1][tSize-1];
    }
    
    private static Double minimum(Double value1,Double value2,Double value3){
        Double smaller = value1;
        
        if(value2 < smaller){
            smaller = value2;
        }
        if(value3 < smaller){
            smaller = value3;
        }
        
        return smaller;
    }
    
    private static Double distance(Double pointOne,Double pointTwo){
        Double distance = 0.;
        distance = Math.pow(pointOne - pointTwo,2);
        return distance;
    }
}
