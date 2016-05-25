/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author PeDeNRiQue
 */
public class OneNearestNeighbors {
    public static void main(String[] args){
//        KNearestNeighbors knn = new KNearestNeighbors();
        KNearestNeighbors3D knn = new KNearestNeighbors3D();
        knn.SakoeChiba = false;
        knn.bandwidth = 100;
        
        knn.execute();
    }
    
    public static class KNearestNeighbors{
        private Integer acuracy = 0;
        private Integer total = 0;
        private Boolean SakoeChiba = false;
        private Integer bandwidth = 100;
        
        public void execute(){
            OneNearestNeighbors oneNearestNeighbors = new OneNearestNeighbors();
            List<List<Double>> train = oneNearestNeighbors.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\treino.txt"," ");
            //System.out.println("Primeiro Arquivo\nSegundoArguivo");
            List<List<Double>> teste = oneNearestNeighbors.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\teste.txt"," ");
            //System.out.println("Leu segundo arquivo");

            long start = System.currentTimeMillis();

            
            calculateDistances(train, teste);

            //Após o término do método
            long finish = System.currentTimeMillis();
            long time = finish - start;

            System.out.println(acuracy+"/"+total);
            System.out.println(time/1000.0+" segundos\nFIM");
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


            for(List<Double> r : references){
                if(SakoeChiba){
                    temp = DynamicTimeWarping.DTWDistance(r.subList(1, r.size()),compared.subList(1, compared.size()),bandwidth);
                }else{
                    temp = DynamicTimeWarping.DTWDistance(r.subList(1, r.size()),compared.subList(1, compared.size()));//0%;1%;5%;10%;20%;50%;100%
                }
                if(temp < neighbor.get(0)){
                    neighbor = new ArrayList<Double>();
                    neighbor.add(temp);//distance
                    neighbor.add(r.get(0));//target
                }
            }        

            distance = neighbor.get(0);
            target = neighbor.get(1);

            total++;
            if(Objects.equals(compared.get(0), target)){            
                acuracy++;
            }
            return target;
        }
        
    }
    
    public static class KNearestNeighbors3D{
        private Integer acuracy = 0;
        private Integer total = 0;
        private Boolean SakoeChiba = false;
        private Integer bandwidth = 100;
        
        public void execute(){
            OneNearestNeighbors oneNearestNeighbors = new OneNearestNeighbors();
            List<List<Double>> train = oneNearestNeighbors.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\treino3D.txt"," ");
            //System.out.println("Primeiro Arquivo\nSegundoArguivo");
            List<List<Double>> teste = oneNearestNeighbors.readFile("C:\\Users\\PeDeNRiQue\\Copy\\USP\\Disciplinas\\ProjetoDeAlgoritmo\\programas\\dtw\\teste3D.txt"," ");
            //System.out.println("Leu segundo arquivo");

            long start = System.currentTimeMillis();

            calculateDistances(train, teste);

            //Após o término do método
            long finish = System.currentTimeMillis();
            long time = finish - start;

            System.out.println(acuracy+"/"+total);
            System.out.println(time/1000.0+" segundos\nFIM");
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
            
            for(int i = 0; i < references.size(); i = i + 3){
                if(SakoeChiba){
                    temp = DynamicTimeWarping.DTWDistance(references.get(i).subList(1, references.get(i).size()),compareds.get(0).subList(1, compareds.get(0).size()),bandwidth);
                    temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+1).subList(1, references.get(i+1).size()),compareds.get(1).subList(1, compareds.get(1).size()),bandwidth);
                    temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+2).subList(1, references.get(i+2).size()),compareds.get(2).subList(1, compareds.get(2).size()),bandwidth);
                }else{
                    temp = DynamicTimeWarping.DTWDistance(references.get(i).subList(1, references.get(i).size()),compareds.get(0).subList(1, compareds.get(0).size()));
                    temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+1).subList(1, references.get(i+1).size()),compareds.get(1).subList(1, compareds.get(1).size()));
                    temp = temp + DynamicTimeWarping.DTWDistance(references.get(i+2).subList(1, references.get(i+2).size()),compareds.get(2).subList(1, compareds.get(2).size()));
                }                    

                if(temp < neighbor.get(0)){
                    neighbor = new ArrayList<Double>();
                    neighbor.add(temp);//distance
                    neighbor.add(references.get(i).get(0));//target
                }
            }        

            distance = neighbor.get(0);
            target = neighbor.get(1);


            total++;
            if(Objects.equals(compareds.get(0).get(0), target)){            
                acuracy++;
            }
            return target;
        }
    }
    
    public static class DynamicTimeWarping{
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

            Double cost;
            int ii,jj;
            for(int i = 0; i < sSize; i++){
                for(int j = 0; j < tSize; j++){
                    ii = i + 1;
                    jj = j + 1;
                    cost = distance(s.get(i),t.get(j));
                    matrixDTW[ii][jj] = cost + minimum(matrixDTW[ii-1][jj],matrixDTW[ii][jj-1],matrixDTW[ii-1][jj-1]);
                }
            }

            return matrixDTW[sSize][tSize];
        }
        public static Double DTWDistance(List<Double> s, List<Double> t,int r) {
            int sSize = s.size();
            int tSize = t.size();


            Double[][] matrixDTW = new Double[sSize][tSize];  

            r = (sSize*r/100);
            //r = Math.max(r, Math.abs(sSize-tSize));

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
    
    public  List<List<Double>> readFile(String fileName,String separator){
        BufferedReader br = null;
        List<List<Double>> matriz = new ArrayList<List<Double>>();
        try {

            String sCurrentLine;

            br = new BufferedReader(
                    new FileReader(fileName));

            while ((sCurrentLine = br.readLine()) != null) {

                String[] temp = sCurrentLine.split(separator);
                List<Double> linha = new ArrayList<Double>();
                for(String s : temp){
                    linha.add(Double.parseDouble(s));
                }
                matriz.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                    if (br != null)br.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
        
        return matriz;
    }
}
