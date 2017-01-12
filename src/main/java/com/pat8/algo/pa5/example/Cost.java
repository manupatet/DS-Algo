/**
 * File Name    : Cost.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Feb 7, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa5.example;

class Cost<T>
{
    private final Object matrix[][];
    public Cost(final int numRows, final int numCols)
    {
        this.matrix = new Object[numRows][numCols];
    }

    public void assignCost(final T s, final int row, final int col)
    {
        this.matrix[row][col] = s;
    }

    public T cost(final int row, final int col)
    {
        return (T)this.matrix[row][col];
    }
}