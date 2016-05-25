/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.DTWBandwidth;
import utils.DynamicTimeWarping;
import utils.FileUtil;

/**
 *
 * @author PeDeNRiQue
 */
public class KNearestNeighbors3D {
    private String ARQUIVO = "dtw";
    private String FILENAME = ARQUIVO+".txt";
    private Integer acurate = 0;
    private Integer total = 0;
    
    public static void main(String [] args){
        
        List<List<Double>> train = FileUtil.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\treino3D.txt"," ");
        //System.out.println("Primeiro Arquivo\nSegundoArguivo");
        List<List<Double>> teste = FileUtil.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\teste3D.txt"," ");
        //System.out.println("Leu segundo arquivo");
    
        long start = System.currentTimeMillis();
        
        KNearestNeighbors3D knn = new KNearestNeighbors3D("dtw.txt");
        FileUtil.writeFile("ORIGINAL\tPREDICAO",knn.FILENAME);
        knn.calculateDistances(train, teste);
        
        //Após o término do método
        long finish = System.currentTimeMillis();
        long total = finish - start;
        
        System.out.println(knn.acurate+"/"+knn.total);
        System.out.println(total/1000.0+" segundos\nFIM");
    }   
    
    public KNearestNeighbors3D(){}
    
    public KNearestNeighbors3D(String filename){
        FILENAME = filename;
    }
    
    private List<List<Double>> calculateDistances(List<List<Double>> train,List<List<Double>> testes){
        List<Double> results = new ArrayList<Double>();
        
        for(int i = 0; i < testes.size();i = i + 3){
            results.add(findKNearestNeighbors(train,testes.subList(i, i+3)));
        }
        
        return testes;
    }
        
    private Double findKNearestNeighbors(List<List<Double>> references,List<List<Double>> compareds){
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
        int bandwidth = 30;
        for(int i = 0; i < references.size(); i = i + 3){
            
            //System.out.println("REFERENCIAS: "+r.get(0)+" <_> ");
            //temp = euclideanDistance(r,compared,parPresente);
            if(false){
                temp = DynamicTimeWarping.DTWDistance(references.get(i).subList(1, references.get(i).size()),compareds.get(0).subList(1, compareds.get(0).size()));
                temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+1).subList(1, references.get(i+1).size()),compareds.get(1).subList(1, compareds.get(1).size()));
                temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+2).subList(1, references.get(i+2).size()),compareds.get(2).subList(1, compareds.get(2).size()));
            }else{
                temp = DTWBandwidth.DTWDistance(references.get(i).subList(1, references.get(i).size()),compareds.get(0).subList(1, compareds.get(0).size()),bandwidth);
                temp = temp + DTWBandwidth.DTWDistance(references.get(i+1).subList(1, references.get(i+1).size()),compareds.get(1).subList(1, compareds.get(1).size()),bandwidth);
                temp = temp + DTWBandwidth.DTWDistance(references.get(i+2).subList(1, references.get(i+2).size()),compareds.get(2).subList(1, compareds.get(2).size()),bandwidth);
            }
            //temp = DTWBandwidth.DTWDistance(r.subList(1, r.size()),compared.subList(1, compared.size()),100);//1%;5%;10%;20%;50%;100%
            
            if(temp < neighbor.get(0)){
                neighbor = new ArrayList<Double>();
                neighbor.add(temp);//distance
                neighbor.add(references.get(i).get(0));//target
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
        //System.out.println(compareds.get(0).get(0)+" > "+target);
        if(Objects.equals(compareds.get(0).get(0), target)){            
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
