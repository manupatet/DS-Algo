/**
 * File Name    : ProgrammingAssignment3.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 5, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa3;

import java.io.IOException;

public class ProgrammingAssignment3
{
    public static void main(final String[] args) throws IOException
    {
        final Knapsack k1 = new Knapsack("PA3Q1.txt");
        long start = System.currentTimeMillis();
        System.out.println("Max value Q1: "+k1.compute()+" ("+(System.currentTimeMillis()-start)+" ms)");
        System.out.println();

        final KnapsackMemoized k2 = new KnapsackMemoized("PA3Q2.txt");
        start = System.currentTimeMillis();
        System.out.println("Max value Q2: "+k2.computeMemoized()+" ("+(System.currentTimeMillis()-start)+" ms)");
        //Ans: 2595819
    }
}
