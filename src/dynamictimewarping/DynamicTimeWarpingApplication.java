/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dynamictimewarping;

import java.util.Arrays;
import java.util.List;
import utils.DynamicTimeWarping;
import utils.FileUtil;

/**
 *
 * @author PeDeNRiQue
 */
public class DynamicTimeWarpingApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<List<Double>> treinamento = FileUtil.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\treino.txt"," ");

        //System.out.println(treinamento.get(0).get(0));
        //System.out.println(treinamento.size()+" "+treinamento.get(0).size());
        
        List<Double> m1 = Arrays.asList(1., 1., 2., 3., 2., 0.);
        List<Double> m2 = Arrays.asList(0., 1., 1., 2., 3., 2., 1.);
        
        System.out.println(DynamicTimeWarping.DTWDistance(m1,m2));
        
    }
    
}
