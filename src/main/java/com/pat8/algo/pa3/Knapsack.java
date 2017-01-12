/**
 * File Name    : Knapsack.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pat8.algo.model.IndependentSet;
import com.pat8.algo.model.Item;

public class Knapsack {
    private final List<Item> allItems;
    private final Item[] allItemsArr;
    private final int knapsackSize;
    private final Map<IndependentSet, List<Item>> cache;
    Map<Integer, Map<Integer, Integer>> memoizedCache = new HashMap<>();

    public Knapsack(final String filename) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(filename)));
            String[] firstLine = in.readLine().split(" ");
            int n = Integer.parseInt(firstLine[1]);
            this.allItems = new ArrayList<>();
            this.cache = new HashMap<>();
            this.knapsackSize = Integer.parseInt(firstLine[0]);
            for (; n > 0; n--) {
                firstLine = in.readLine().split(" ");
                this.allItems.add(new Item(Integer.parseInt(firstLine[0]), Integer.parseInt(firstLine[1])));
            }
            this.allItemsArr = this.allItems.toArray(new Item[this.allItems.size()]);
        } finally {
            in.close();
        }
    }

    public int compute() {
        /**
         * remember the 2D array has +1 column to accommodate zero knapsack size and +1 row to accommodate zero elements
         * case
         */
        final int W = this.knapsackSize + 1;
        final Integer[][] A = new Integer[this.allItemsArr.length + 1][W];
        for (int x = 0; x < W; x++) {
            A[0][x] = 0;
        }

        for (int i = 1; i < A.length; i++) {
            // System.out.println("processing "+allItemsArr[i-1]);
            for (int x = 0; x < W; x++) {
                if (x >= this.allItemsArr[i - 1].getWeight()) {
                    A[i][x] = Math.max(A[i - 1][x], A[i - 1][x - this.allItemsArr[i - 1].getWeight()]
                            + this.allItemsArr[i - 1].getValue());
                } else {
                    A[i][x] = A[i - 1][x];
                }
            }
        }
        // System.out.println("EMPTY\t: "+Arrays.asList(A[0]));
        // for (int i = 1; i < A.length; i++)
        // {
        // System.out.println(allItemsArr[i-1]+"\t: "+Arrays.asList(A[i]));
        // }

        return A[A.length - 1][W - 1];
    }

    public int computeInverseLoop() {
        /**
         * remember the 2D array has +1 column to accommodate zero knapsack size and +1 row to accommodate zero elements
         * case
         */
        final int W = this.knapsackSize + 1;
        final Integer[][] A = new Integer[this.allItemsArr.length + 1][W];
        for (int x = 0; x < W; x++) {
            A[0][x] = 0;
        }

        for (int i = A.length - 1; i >= 0; i--) {
            // System.out.println("processing "+allItemsArr[i-1]);
            for (int x = W - 1; x >= 0; x++) {
                if (x >= this.allItemsArr[i - 1].getWeight()) {
                    A[i][x] = Math.max(A[i - 1][x], A[i - 1][x - this.allItemsArr[i - 1].getWeight()]
                            + this.allItemsArr[i - 1].getValue());
                } else {
                    A[i][x] = A[i - 1][x];
                }
            }
        }
        /*
         * System.out.println("EMPTY\t: "+Arrays.asList(A[0])); for (int i = 1; i < A.length; i++) {
         * System.out.println(allItemsArr[i-1]+"\t: "+Arrays.asList(A[i])); }
         */
        return A[A.length - 1][W - 1];
    }

    /**
     * TODO: tried to do recursively ... but no didn't happen!!!
     */
    /*
     * private List<Item> VRecursive(int i, int x){ IndependentSet is = new IndependentSet(i, x);
     * if(cache.containsKey(is)){ System.out.println("Cache hit for "+is); return cache.get(is); }
     *
     * if(x <=0 ){ throw new IllegalArgumentException("Knapsack size "+x+" is invalid"); }
     *
     * if(i == 1){//first case if(x >= allItems.get(0).getWeight()){ List<Item> arr = new ArrayList<>();
     * arr.add(allItems.get(0)); cache.put(is, arr); return arr; } throw new
     * IllegalArgumentException("Knapsack size "+x+" while first element is "+allItems.get(0).getWeight()); }
     *
     * Item vi = allItems.get(i-1);//ArrayList being zero index List<Item> max = null; try{ List<Item> vOther =
     * VRecursive(i-1, x - vi.getWeight()); vOther.add(vi);//vi + V(i-1, x-wi) i.e. case that includes item vi max =
     * maxValueSet( VRecursive(i-1,x), vOther); }catch(IllegalArgumentException ex){ max = VRecursive(i-1, x); }
     * cache.put(is, max); return max; }
     *
     * private List<Item> maxValueSet(List<Item> list1, List<Item> list2){ int v1=0, v2=0; for(Item i: list1){ v1+=
     * i.getValue(); } for(Item i: list2){ v2+= i.getValue(); } return v1 > v2? list1: list2; }
     */
}

