/**
 * File Name    : TravellingSalesMan.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 26, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.pat8.algo.model.City;

public class TravellingSalesManOld
{
    private City[] cities;
    public TravellingSalesManOld(final String graphFile) throws NumberFormatException, IOException
    {
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(new File(graphFile)));
            final String[] firstLine = in.readLine().split(" ");
            final int vertexCount = Integer.parseInt(firstLine[0]);
            this.cities = new City[vertexCount];
            System.out.println("File "+graphFile+": Vertices "+vertexCount);
            String line;
            int i=0;
            while((line = in.readLine()) != null){
                final String [] split = line.split(" ");
                final Double i1 = Double.parseDouble(split[0]);
                final Double i2 = Double.parseDouble(split[1]);
                final City c = new City(i1, i2);
                //				for(int j=0; j<i;j++){
                //					City c2 = cities[j];
                //					Double dist = c.getEucledianDistance(c2);
                //					Edge<City> edge = new Edge<City>(c2, dist.intValue());
                //					c.addEdge(edge);
                //					c2.addEdge(edge);
                //				}
                this.cities[i] = c;
                i++;
            }
        }finally{
            in.close();
        }
    }
    public int compute(){
        final Integer infinity = 1000000;

        final Integer[][] arr = new Integer[this.cities.length][this.cities.length];
        for(int i=0; i<arr.length; i++){
            arr[i][0]=0;
            for(int j=1; j<arr[i].length ; j++){
                arr[i][j]=infinity;
            }
        }

        //		for(int m=1; m < cities.length; m++){//m is subproblem size
        //			for(int i=0; i<m; i++){//Allowed set S of vertices
        //				Integer min = infinity;
        //				for(int j=1; j<i; j++){//j is an element of S
        //					City c = cities[j-1];
        //					min = Math.min(min, cities[i].getEucledianDistance(c).intValue());
        //				}
        //				System.out.println("min "+min);
        //			}
        //		}
        //		branchAndBound();
        return 0;
    }

    public void branchAndBound(){
        final Double infinity = 1000000.0;
        Double lowerBound = 0.0;

        final Double[][] arr = new Double[this.cities.length][this.cities.length];
        for(int i=0; i<arr.length; i++){
            Double rowMin = infinity;
            for(int j=0; j<arr[i].length ; j++){
                if(i!=j){
                    arr[i][j] = this.cities[i].getEucledianDistance(this.cities[j]);
                    rowMin = Math.min(rowMin, arr[i][j]);
                }else{
                    arr[i][j] = 0.0;
                }
            }
            lowerBound+=rowMin;
        }

        System.out.println("lower bound is "+lowerBound.intValue());
        System.out.println(" lower bound from function = "+matrixMinima(null, arr));

    }

    public Double matrixMinima(final Integer[][] omitCells, final Double[][] matrix){
        final Set<Integer> rowsToOmit = new HashSet<>();
        final Set<Integer> colsToOmit = new HashSet<>();
        if(omitCells != null){
            for(int i=0; i<omitCells.length; i++){
                rowsToOmit.add(omitCells[i][1]);
                colsToOmit.add(omitCells[i][0]);
            }
        }

        final Double lowerBound = 0.0;
        final Double infinity = 1000000.0;
        for(int i=0; i< matrix.length; i++){
            if(colsToOmit.contains(Integer.valueOf(i))){
                continue;
            }
            Double rowMin = infinity;
            for(int j=0; j<matrix.length; j++){
                if(rowsToOmit.contains(Integer.valueOf(j))){
                    continue;
                }
                if(i!=j){
                    rowMin = Math.min(rowMin, matrix[i][j]);
                }
            }
        }
        return 0.0;
    }
}

class SubSolution implements Comparable<SubSolution>{
    private final City from, to;
    private final Double cost;
    public SubSolution(final City from, final City to, final Double cost)
    {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
    @Override
    public int compareTo(final SubSolution o)
    {
        return this.cost.compareTo(o.cost);
    }
}