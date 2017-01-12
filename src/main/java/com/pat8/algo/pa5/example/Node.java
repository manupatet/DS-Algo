/**
 * File Name    : Node.java
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

import java.awt.Point;
import java.util.BitSet;

/**
 * The information contained in a tree used for solving TSP using branch and bound.
 */

class Node {
    // Fields
    private int lowerBound;
    private final int numRows, numCols;
    private byte[][] constraint;
    // -1 indicates no edge from row to column allowed,
    // 1 indicates that edge from row to column required,
    // 0 indicates that edge from row to column allowed
    private final double[] nodeCosts; // Used to compute smallest and
    // nexSmallest
    private int edges; // Used by isTour query
    private double tourCost; // Used by isTour query
    private final byte[] trip;
    private String nodeAsString; // Used by isTour query
    private final boolean isLoop = false;
    static BitSet b = new BitSet(); // Used by isCycle and initialized in TSPUI

    // Constructor
    public Node(final int numRows, final int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.nodeCosts = new double[numCols + 1]; // Natural indexing
        this.constraint = new byte[numRows + 1][numCols + 1]; // Natural indexing
        this.trip = new byte[numRows + 1];
    }

    // Commands
    public void assignConstraint(final byte value, final int row, final int col) {
        this.constraint[row][col] = value;
        this.constraint[col][row] = value;
    }

    public int assignPoint(final Point p, int edgeIndex) {
        // Advance edgeIndex until edge that is unconstrained is found
        Point pt = p;
        while (edgeIndex < TSP.newEdge.size()
                && this.constraint[(int) Math.abs(pt.getX())][(int) Math.abs(pt.getY())] != 0) {
            edgeIndex++;
            if (edgeIndex < TSP.newEdge.size()) {
                pt = TSP.newEdge.get(edgeIndex);
            }
        }
        if (edgeIndex < TSP.newEdge.size()) {
            if (pt.getX() < 0) {
                assignConstraint((byte) -1, (int) Math.abs(pt.getX()), (int) Math.abs(pt.getY()));
            } else {
                assignConstraint((byte) 1, (int) pt.getX(), (int) pt.getY());
            }
        }
        return edgeIndex;
    }

    public void setConstraint(final byte[][] constraint) {
        this.constraint = constraint;
    }

    public void addDisallowedEdges() {
        for (int row = 1; row <= this.numRows; row++) {
            // Count the number of paths from row.
            // If the count exceeds one then disallow all other paths
            // from row
            int count = 0;
            for (int col = 1; col <= this.numCols; col++) {
                if (row != col && this.constraint[row][col] == 1) {
                    count++;
                }
            }
            if (count >= 2) {
                for (int col = 1; col <= this.numCols; col++) {
                    if (row != col && this.constraint[row][col] == 0) {
                        this.constraint[row][col] = -1;
                        this.constraint[col][row] = -1;
                    }
                }
            }
        }
        // Check to see whether the presence of a col causes a premature
        // circuit
        for (int row = 1; row <= this.numRows; row++) {
            for (int col = 1; col <= this.numCols; col++) {
                if (row != col && isCycle(row, col) && numCities(b) < this.numRows) {
                    if (this.constraint[row][col] == 0) {
                        this.constraint[row][col] = -1;
                        this.constraint[col][row] = -1;
                    }
                }
            }
        }
    }

    public void addRequiredEdges() {
        for (int row = 1; row <= this.numRows; row++) {
            // Count the number of paths excluded from row
            // If the count equals numCols - 3, include all remaining
            // paths
            int count = 0;
            for (int col = 1; col <= this.numCols; col++) {
                if (row != col && this.constraint[row][col] == -1) {
                    count++;
                }
            }
            if (count >= this.numRows - 3) {
                for (int col = 1; col <= this.numCols; col++) {
                    if (row != col && this.constraint[row][col] == 0) {
                        this.constraint[row][col] = 1;
                        this.constraint[col][row] = 1;
                    }
                }
            }
        }
    }

    public void computeLowerBound() {
        int lowB = 0;
        for (int r = 1; r <= this.numRows; r++) {
            for (int col = 1; col <= this.numCols; col++) {
                this.nodeCosts[col] = TSP.c.cost(r, col);
            }
            this.nodeCosts[r] = Short.MAX_VALUE;
            // Eliminate edges that are not allowed
            for (int c = 1; c <= this.numCols; c++) {
                if (this.constraint[r][c] == -1) {
                    this.nodeCosts[c] = Short.MAX_VALUE; // Taken out of
                    // contention
                }
            }
            final double[] required = new double[this.numCols - 1]; // Natural indexing
            int numRequired = 0;
            // Determine whether an edge is required
            for (int c = 1; c <= this.numCols; c++) {
                if (this.constraint[r][c] == 1) {
                    numRequired++;
                    required[numRequired] = this.nodeCosts[c];
                    this.nodeCosts[c] = Short.MAX_VALUE; // Taken out of
                    // contention
                }
            }
            double smallest = 0, nextSmallest = 0;
            if (numRequired == 0) {
                smallest = smallest();
                nextSmallest = nextSmallest();
            } else if (numRequired == 1) {
                smallest = required[1];
                nextSmallest = smallest();
            } else if (numRequired == 2) {
                smallest = required[1];
                nextSmallest = required[2];
            }
            if (smallest == Short.MAX_VALUE) {
                smallest = 0;
            }
            if (nextSmallest == Short.MAX_VALUE) {
                nextSmallest = 0;
            }
            lowB += smallest + nextSmallest;
        }
        this.lowerBound = lowB; // This is twice the actual lower bound
    }

    public void setTour() {
        byte path = 0;
        for (int col = 2; col <= this.numCols; col++) {
            if (this.constraint[1][col] == 1) {
                path = (byte) col;
                break;
            }
        }
        this.tourCost = TSP.c.cost(1, path);
        this.trip[1] = path;
        final int row = 1;
        final int col = path;
        int from = row;
        byte pos = path;
        this.nodeAsString = "" + row + " " + col;
        while (pos != row) {
            for (byte column = 1; column <= this.numCols; column++) {
                if (column != from && this.constraint[pos][column] == 1) {
                    from = pos;
                    pos = column;
                    this.nodeAsString += " " + pos;
                    this.tourCost += TSP.c.cost(from, pos);
                    this.trip[from] = pos;
                    break;
                }
            }
        }
    } // Queries

    public double tourCost() {
        return this.tourCost;
    }

    public byte[] trip() {
        return this.trip;
    }

    public byte constraint(final int row, final int col) {
        return this.constraint[row][col];
    }

    public byte[][] constraint() {
        return this.constraint;
    }

    public int lowerBound() {
        return this.lowerBound;
    }

    public boolean isTour() {
        // Determine path from 1
        int path = 0;
        for (int col = 2; col <= this.numCols; col++) {
            if (this.constraint[1][col] == 1) {
                path = col;
                break;
            }
        }
        if (path > 0) {
            final boolean cycle = isCycle(1, path);
            return cycle && numCities(b) == this.numRows;
        } else {
            return false;
        }
    }

    public boolean isCycle(final int row, final int col) {
        // b = new BitSet(numRows + 1); BRANCH AND BOUND IMPLEMENTATIONS FOR THE
        // TRAV
        for (int i = 0; i < this.numRows + 1; i++) {
            b.clear(i);
        }
        b.set(row);
        b.set(col);
        int from = row;
        int pos = col;
        int edges = 1;
        boolean quit = false;
        while (pos != row && edges <= this.numCols && !quit) {
            quit = true;
            for (int column = 1; column <= this.numCols; column++) {
                if (column != from && this.constraint[pos][column] == 1) {
                    edges++;
                    from = pos;
                    pos = column;
                    b.set(pos);
                    quit = false;
                    break;
                }
            }
        }
        return pos == row || edges >= this.numCols;
    }

    public String tour() {
        return this.nodeAsString;
    }

    @Override
    public String toString() {
        // String representation of constraint matrix
        String returnString = "";
        for (int row = 1; row <= this.numRows; row++) {
            for (int col = row + 1; col <= this.numCols; col++) {
                if (this.constraint[row][col] == 1) {
                    returnString += "" + row + col + "  ";
                } else if (this.constraint[row][col] == -1) {
                    returnString += "*" + row + col + "  ";
                }
            }
        }
        return returnString;
    }

    // Internal methods
    private double smallest() {
        double s = this.nodeCosts[1];
        int index = 1;
        for (int i = 2; i <= this.numCols; i++) {
            if (this.nodeCosts[i] < s) {
                s = this.nodeCosts[i];
                index = i;
            }
        }
        final double temp = this.nodeCosts[1];
        this.nodeCosts[1] = this.nodeCosts[index];
        this.nodeCosts[index] = temp;
        return this.nodeCosts[1];
    }

    private double nextSmallest() {
        double ns = this.nodeCosts[2];
        int index = 2;
        for (int i = 2; i <= this.numCols; i++) {
            if (this.nodeCosts[i] < ns) {
                ns = this.nodeCosts[i];
                index = i;
            }
        }
        final double temp = this.nodeCosts[2];
        this.nodeCosts[2] = this.nodeCosts[index];
        this.nodeCosts[index] = temp;
        return this.nodeCosts[2];
    }

    private int numCities(final BitSet b) {
        int num = 0;
        for (int i = 1; i <= this.numRows; i++) {
            if (b.get(i)) {
                num++;
            }
        }
        return num;
    }
}