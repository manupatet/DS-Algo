/**
 * File Name    : ProgrammingAssignment2.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 19, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa2;

import java.io.IOException;

public class ProgrammingAssignment2 {
    public static void main(final String[] args) throws IOException {
        final long start = System.currentTimeMillis();
        final int kValue = 4;
        final KCluster cluster = new KCluster("PA2Q1.txt", kValue);
        System.out.println("Kruskals MST cost : " + cluster.compute() + " (" + (System.currentTimeMillis() - start)
                           + " ms)");
        System.out.println(kValue + " spacing is " + cluster.getSpacing());

        final Question2 ques = new Question2("PA2Q2.txt", 3);
        System.out.println("Kruskals MST cost: " + ques.compute() + " (" + (System.currentTimeMillis() - start)
                           + " ms)");
        System.out.println("kValue " + ques.getKValue());
    }
}
