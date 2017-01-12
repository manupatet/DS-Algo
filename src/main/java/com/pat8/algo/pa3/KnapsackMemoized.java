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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnapsackMemoized {
    private final List<Item> allItems;
    private final Item[] allItemsArr;
    private final int knapsackSize;
    private int minWeight = Integer.MAX_VALUE;
    Map<Integer, Map<Integer, Integer>> memoizedCache = new HashMap<>();

    public KnapsackMemoized(final String filename) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(filename)));
            String[] firstLine = in.readLine().split(" ");
            int n = Integer.parseInt(firstLine[1]);
            this.allItems = new ArrayList<>();
            this.knapsackSize = Integer.parseInt(firstLine[0]);
            for (; n > 0; n--) {
                firstLine = in.readLine().split(" ");
                final int weight = Integer.parseInt(firstLine[1]);
                this.minWeight = Math.min(this.minWeight, weight);
                this.allItems.add(new Item(Integer.parseInt(firstLine[0]), weight));
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
            for (int x = 0; x < W; x++) {
                if (x >= this.allItemsArr[i - 1].getWeight()) {
                    A[i][x] = Math.max(A[i - 1][x], A[i - 1][x - this.allItemsArr[i - 1].getWeight()]
                            + this.allItemsArr[i - 1].getValue());
                } else {
                    A[i][x] = A[i - 1][x];
                }
            }
        }
        return A[A.length - 1][W - 1];
    }

    public int computeMemoized() {
        final int itemCount = this.allItemsArr.length + 1;
        final int W = this.knapsackSize + 1;
        final Integer[][] table = new Integer[2][W];
        for (int x = 0; x < W; x++) {
            table[0][x] = 0;
        }
        // System.out.println("EMPTY\t: "+Arrays.asList(table[0]));

        for (int i = 1; i < itemCount; i++) {
            final Integer[] refRow = table[(i + 1) % 2];
            final Integer[] editRow = table[i % 2];
            for (int x = 0; x < W; x++) {
                if (x >= this.allItemsArr[i - 1].getWeight()) {
                    editRow[x] = Math.max(refRow[x], refRow[x - this.allItemsArr[i - 1].getWeight()]
                            + this.allItemsArr[i - 1].getValue());
                } else {
                    editRow[x] = refRow[x];
                }
            }
            // System.out.println(allItemsArr[i-1]+"\t: "+Arrays.asList(editRow));
        }

        return table[(itemCount - 1) % 2][W - 1];
    }

    public int computeMemoizedOld() {
        final int itemCount = this.allItemsArr.length + 1;
        final int W = this.knapsackSize + 1;
        final Integer[][] table = new Integer[2][W - this.minWeight];
        for (int x = this.minWeight; x < W; x++) {
            table[0][x - this.minWeight] = 0;
        }
        System.out.println("EMPTY\t: " + Arrays.asList(table[0]));

        for (int i = 1; i < itemCount; i++) {
            final Integer[] refRow = table[(i + 1) % 2];
            final Integer[] editRow = table[i % 2];
            for (int x = this.minWeight; x < W; x++) {
                final int rowIndexOffset = x - this.minWeight;
                if (x >= this.allItemsArr[i - 1].getWeight()) {
                    editRow[rowIndexOffset] = Math.max(refRow[rowIndexOffset], refRow[rowIndexOffset
                                                                                      - this.allItemsArr[i - 1].getWeight()]
                                                                                              + this.allItemsArr[i - 1].getValue());
                } else {
                    editRow[rowIndexOffset] = refRow[rowIndexOffset];
                }
            }
            System.out.println(this.allItemsArr[i - 1] + "\t: " + Arrays.asList(editRow));
        }

        return table[itemCount % 2][W - this.minWeight - 1];
    }

    private void store(final int x, final int y, final int value) {
        if (!this.memoizedCache.containsKey(x)) {
            this.memoizedCache.put(x, new HashMap<Integer, Integer>());
        }
        final Map<Integer, Integer> row = this.memoizedCache.get(x);
        row.put(y, value);
    }

    private int lookup(final int x, final int y) {
        if (x == 0 || y == 0) {
            return 0;
        }

        if (!this.memoizedCache.containsKey(x)) {
            this.memoizedCache.put(x, new HashMap<Integer, Integer>());
        }

        final Map<Integer, Integer> row = this.memoizedCache.get(x);
        if (!row.containsKey(y)) {
            row.put(y, null);
        }

        if (row.get(y) == null)
            return 0;
        else
            return row.get(y);
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

class Cell {
    private final int x, y;
    private int hash = 0;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = 31 * 31 + this.x;
            this.hash = this.hash * 31 + this.y;
        }
        return this.hash;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Cell other = (Cell) obj;
            return other.x == this.x && other.y == this.y;
        } catch (final Exception x) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }
}

class IndependentSet {
    private final int index, weight;
    private int hash;

    public IndependentSet(final int i, final int w) {
        this.index = i;
        this.weight = w;
        this.hash = 0;
    }

    @Override
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = 31 * 31 + this.index;
            this.hash = this.hash * 31 + this.weight;
        }
        return this.hash;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final IndependentSet other = (IndependentSet) obj;
            return other.index == this.index && other.weight == this.weight;
        } catch (final Exception x) {
            return false;
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return "V(" + this.index + "," + this.weight + ")";
    }
}

class Item {
    private final int value, weight;

    public Item(final int value, final int weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return this.value;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Item other = (Item) obj;
            return other.value == this.value && other.weight == this.weight;
        } catch (final Exception x) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + this.value + "," + this.weight + "]";
    }
}