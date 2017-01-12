/**
 * File Name    : ProgrammingAssigment1.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 17, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa1;

import java.io.IOException;

public class ProgrammingAssigment1 {
    public static void main(final String[] args) throws IOException {
        final JobScheduler difference = new JobScheduler(false, "jobsToSchedule.txt");
        difference.compute();
        final JobScheduler ratio = new JobScheduler(true, "jobsToSchedule.txt");
        ratio.compute();
        final PrimsAlgo p = new PrimsAlgo("graph.txt");
        final long start = System.currentTimeMillis();
        System.out.print("Computing MST cost... " + p.compute() + " (" + (System.currentTimeMillis() - start) + " ms)");
    }
}
