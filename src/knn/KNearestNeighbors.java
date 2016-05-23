/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.DynamicTimeWarping;
import utils.FileUtil;

/**
 *
 * @author PeDeNRiQue
 */
public class KNearestNeighbors {
    
    /*
    CONSIDERAR_PAR_PRESENTE quando false significa que vou considerar no cálculo da distância
    mesmo quando um dos valores for ausente, e quando true só será considerado quando os dois registros tiverem 
    um valor.
    */  
    private String ARQUIVO = "dtw";
    private String FILENAME = ARQUIVO+".txt";
    private Integer acurate = 0;
    private Integer total = 0;
    public static void main(String [] args){
        
        List<List<Double>> train = FileUtil.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\treino.txt"," ");
        //System.out.println("Primeiro Arquivo\nSegundoArguivo");
        List<List<Double>> teste = FileUtil.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\teste.txt"," ");
        //System.out.println("Leu segundo arquivo");
    
        long start = System.currentTimeMillis();
        
        KNearestNeighbors knn = new KNearestNeighbors("dtw.txt");
        FileUtil.writeFile("ORIGINAL\tPREDICAO",knn.FILENAME);
        knn.calculateDistances(train, teste);
        
        //Após o término do método
        long finish = System.currentTimeMillis();
        long total = finish - start;
        
        System.out.println(knn.acurate+"/"+knn.total);
        System.out.println(total/1000.0+" segundos\nFIM");
    }   
    
    public KNearestNeighbors(){}
    
    public KNearestNeighbors(String filename){
        FILENAME = filename;
    }
    
    private List<List<Double>> calculateDistances(List<List<Double>> train,List<List<Double>> testes){
    
        for(List<Double> teste : testes){
            teste.add(findKNearestNeighbors(train,teste));
        }
        
        return testes;
    }
        
    private Double findKNearestNeighbors(List<List<Double>> references,List<Double> compared){
        Double distance = Double.POSITIVE_INFINITY;
        Double temp;
        Double target = null;      
        int j;
        List<Double> neighbor;
        
        
        neighbor = new ArrayList<Double>();
        neighbor.add(distance);
       
//        System.out.println("INICIO");
//        for(Double d : compared){
//            System.out.print(d+" ");
//        }
        
        for(List<Double> r : references){
            
            //System.out.println("REFERENCIAS: "+r.get(0)+" <_> ");
            //temp = euclideanDistance(r,compared,parPresente);
            temp = DynamicTimeWarping.DTWDistance(r.subList(1, r.size()),compared.subList(1, compared.size()),30);
           
            if(temp < neighbor.get(0)){
                neighbor = new ArrayList<Double>();
                neighbor.add(temp);//distance
                neighbor.add(r.get(0));//target
            }
        }        
        
        distance = neighbor.get(0);
        target = neighbor.get(1);
        
        //compared.get(0) -> id
        //compared.get(1) -> target
//        FileUtil.writeFile(target+"\t"+compared.get(0)+"\t"+
//                compared.get(1)+"\t"+distance,this.FILENAME);
        //FileUtil.writeFile(compared.get(0)+"\t"+target,this.FILENAME);
        
        total++;
        if(Objects.equals(compared.get(0), target)){            
            acurate++;
        }
        return target;
    }
    
    private void showMatriz(List<List<String>> matriz){
        for(List<String> ss : matriz){
            for(String s : ss){
                System.out.print(s+"<>");
            }
            System.out.println(" ");
        }
    }
}
