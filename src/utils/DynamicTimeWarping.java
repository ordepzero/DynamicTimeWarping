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
public class DynamicTimeWarping {
    
    
    public static Double DTWDistance(List<Double> s, List<Double> t) {
        int sSize = s.size();
        int tSize = t.size();
        
        Double[][] matrixDTW = new Double[sSize+1][tSize+1];

        for(int i = 0; i <= sSize;i++){
            matrixDTW[i][0] = Double.MAX_VALUE;
        }
        for(int i = 0; i <= tSize;i++){
            matrixDTW[0][i] = Double.MAX_VALUE;
        }
        matrixDTW[0][0] = 0.0;
            
//        for(int i = 0; i < sSize; i++){
//            for(int j = 0; j < tSize; j++){
//                System.out.print(matrixDTW[i][j]+" ");
//            }
//            System.out.println("");
//        }
//        System.out.println("");
        
        Double cost;
        int ii,jj;
        for(int i = 0; i < sSize; i++){
            for(int j = 0; j < tSize; j++){
                ii = i + 1;
                jj = j + 1;
                cost = euclidianDistance(s.get(i),t.get(j));
                matrixDTW[ii][jj] = cost + minimum(matrixDTW[ii-1][jj],matrixDTW[ii][jj-1],matrixDTW[ii-1][jj-1]);
                //System.out.print(matrixDTW[ii][jj]+" ");
                //System.out.println(cost+" "+matrixDTW[ii-1][jj]+" "+matrixDTW[ii][jj-1]+" "+matrixDTW[ii-1][jj-1]);
            }
            //System.out.println("");
        }

        return matrixDTW[sSize][tSize];
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
    
    private static Double euclidianDistance(Double pointOne,Double pointTwo){
        Double distance = 0.;
        
        distance = Math.sqrt(Math.pow(pointOne - pointTwo,2));
        
        return distance;
    }
}
