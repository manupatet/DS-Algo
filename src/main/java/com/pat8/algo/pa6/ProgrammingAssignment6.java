/**
 * File Name    : ProgrammingAssignment6.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Feb 2, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa6;

import java.io.File;
import java.io.IOException;

public class ProgrammingAssignment6 {
    public static void main(final String[] args) throws IOException {
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsUnatisfiable1.txt");
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsUnatisfiable5.txt");
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsUnatisfiable3.txt");
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsSatisfiable2.txt");
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsSatisfiable2.txt");
        // compute("PA_DataFiles"+File.separator+"PA6Q1AnsSatisfiable2.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph1.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph2.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph3.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph4.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph5.txt");
        compute("PA_DataFiles" + File.separator + "PA6Q1Graph6.txt");
    }

    public static void compute(final String fileName) throws IOException {
        final long start = System.currentTimeMillis() / 1000;
        final TwoSAT twosat = new TwoSAT(fileName);
        final long temp = System.currentTimeMillis() / 1000;
        System.out.print("loaded " + fileName + " [" + (temp - start) + " sec] ");
        final boolean b = twosat.compute();
        System.out.println(" is " + (b ? "satisfiable" : "unsatisfiable") + " ["
                + (System.currentTimeMillis() / 1000 - temp) + " sec]");
    }
}
