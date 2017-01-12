/**
 * File Name    : ProgrammingAssignment4.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 19, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa4;

import java.io.File;
import java.io.IOException;

public class ProgrammingAssignment4 {
    public static void main(final String[] args) throws IOException {
        // BellmanFord bf = new BellmanFord("PA_DataFiles"+File.separator+"PA4Q1Ans-6.txt");
        // long start = System.currentTimeMillis()/1000;
        // boolean b = bf.compute(3);
        final BellmanFord bf = new BellmanFord("PA_DataFiles" + File.separator + "PA4Q1Graph3.txt");
        /**
         * Although the algo produced correct answer -19, but strangely for PA4Q1Ans-6.txt it produces wrong result
         * (possibly the cycle is not negative but exactly 0!). Need to see that case else the algo is not correct.
         * Also, this is a brute force method. A more elegant method is Johnson's algo which uses bellmen-ford and
         * Dijkstra's to compute all-pairs shortest path in O(m n2 log n) which is better than current O(n3)
         */
        final long start = System.currentTimeMillis() / 1000;
        final boolean b = bf.compute(7);
        System.out.println("This graph has " + (b ? "a min weight of " + bf.getMinValue() : "a negative cycle") + " ("
                + (System.currentTimeMillis() / 1000 - start) + " sec)");
    }
}
