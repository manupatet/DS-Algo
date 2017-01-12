/**
 * File Name    : ProgrammingAssigment5.java
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

import java.io.File;
import java.io.IOException;

import com.pat8.algo.pa5.example.TSP;

public class ProgrammingAssigment5 {
    public static void main(final String[] args) throws NumberFormatException, IOException {
        final TravellingSalesMan t = new TravellingSalesMan("PA_DataFiles" + File.separator + "PA5Q1Ans32.txt");
        /*
         * long start = System.currentTimeMillis()/1000; tsp.branchAndBound();
         * System.out.println("done in ("+(System.currentTimeMillis()/1000-start)+" sec)"); Short[][] distanceMatrix =
         * new Short[5][5]; distanceMatrix[0] = new Short[]{0, 10, 8, 9, 7}; distanceMatrix[1] = new Short[]{10, 0, 10,
         * 5, 6}; distanceMatrix[2] = new Short[]{8, 10, 0, 8, 9}; distanceMatrix[3] = new Short[]{9, 5, 8, 0, 6};
         * distanceMatrix[4] = new Short[]{7, 6, 9, 6, 0};
         */

        final TSP tsp = new TSP(t.getDistanceMatrix());
        tsp.generateSolution();
    }
}
