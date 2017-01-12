/**
 * File Name    : TravellingSalesMan.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.pat8.algo.model.City;

public class TravellingSalesMan {
    private final Double[][] distanceMatrix;
    private final City[] cities;

    public TravellingSalesMan(final String graphFile) throws NumberFormatException, IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(graphFile)));
            final String[] firstLine = in.readLine().split(" ");
            final int vertexCount = Integer.parseInt(firstLine[0]);
            this.cities = new City[vertexCount];
            System.out.println("File " + graphFile + ": Vertices " + vertexCount);
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                final String[] split = line.split(" ");
                final Double i1 = Double.parseDouble(split[0]);
                final Double i2 = Double.parseDouble(split[1]);
                final City c = new City(i1, i2);
                this.cities[i] = c;
                i++;
            }

            this.distanceMatrix = new Double[this.cities.length][this.cities.length];
            for (i = 0; i < this.distanceMatrix.length; i++) {
                for (int j = 0; j < this.distanceMatrix[i].length; j++) {
                    if (i != j) {
                        this.distanceMatrix[i][j] = this.cities[i].getEucledianDistance(this.cities[j]);
                    } else {
                        this.distanceMatrix[i][j] = 0.0;
                    }
                }
            }
            // distanceMatrix = new Double[5][5];
            // distanceMatrix[0] = new Double[]{0.0, 10.0, 8.0, 9.0, 7.0};
            // distanceMatrix[1] = new Double[]{10.0, 0.0, 10.0, 5.0, 6.0};
            // distanceMatrix[2] = new Double[]{8.0, 10.0, 0.0, 8.0, 9.0};
            // distanceMatrix[3] = new Double[]{9.0, 5.0, 8.0, 0.0, 6.0};
            // distanceMatrix[4] = new Double[]{7.0, 6.0, 9.0, 6.0, 0.0};
        } finally {
            in.close();
        }
    }

    public Double[][] getDistanceMatrix() {
        return this.distanceMatrix;
    }

    public void branchAndBound() {
        // System.out.println(" lower bound = "+matrixMinima(null, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,1}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,3}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,4}}, distanceMatrix));
        //
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,4},{1,0}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,4},{1,2}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,4},{1,3}}, distanceMatrix));
        //
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2},{1,0}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2},{1,3}}, distanceMatrix));
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2},{1,4}}, distanceMatrix));
        //
        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2},{1,3},{2,4}}, distanceMatrix));

        // System.out.println(" lower bound = "+matrixMinima(new Integer[][]{{0,2},{1,4},{2,3}}, distanceMatrix));

        final Set<Node> branchAndBoundTree = new TreeSet<>();

        final boolean hitUpperBound = false;
        Integer count = 1;
        final Double lowerBound = matrixMinima(null, this.distanceMatrix);
        final Node root = new Node(count, lowerBound, null);
        branchAndBoundTree.add(root);

        // build tree
        for (int i = 0; i < this.distanceMatrix.length; i++) {
            Node parent = null;
            for (final Node n : branchAndBoundTree) {
                if (n.isLeaf()) {
                    parent = n;
                    break;
                }
            }
            if (parent != null) {
                i = parent.getLevel();
                for (int j = 0; j < this.distanceMatrix.length; j++) {
                    try {
                        if (i != j) {
                            Integer[][] cells = parent.getCells();
                            final List<Integer[]> newCells = new LinkedList<>();
                            if (cells != null) {
                                newCells.addAll(Arrays.asList(cells));
                            }
                            // Not to follow cycles in the path
                            for (final Integer[] c : newCells) {
                                if (c[1].intValue() == j || c[0].intValue() == i) {
                                    throw new IllegalStateException("Eliminating sub-tour [" + i + "," + j + "]");
                                }
                            }
                            newCells.add(new Integer[] { i, j });
                            cells = newCells.toArray(new Integer[newCells.size()][2]);
                            final Double lb = matrixMinima(cells, this.distanceMatrix);
                            if (lb < lowerBound) {
                                continue;
                            }
                            final Node child = new Node(++count, lb, cells);
                            parent.addChild(child);
                            branchAndBoundTree.add(child);
                        }
                    } catch (final IllegalStateException x) {
                        // System.out.println(x.getMessage());
                    }
                }
                if (i == this.distanceMatrix.length - 1) {
                    System.out.println("Feasible : " + parent);
                    Double distance = 0.0;
                    for (final Integer[] c : parent.getCells()) {
                        System.out.println(Arrays.asList(c));
                        distance += this.cities[c[0]].getEucledianDistance(this.cities[c[1]]);
                    }
                    System.out.println("Total distance " + distance);
                }
            } else {
                break;
            }
        }
    }

    public Double matrixMinima(final Integer[][] omitCells, final Double[][] matrix) {
        final Set<Integer> colsToOmit = new HashSet<>();
        Double lowerBound = 0.0;
        int startRow = -1;
        if (omitCells != null) {
            for (int i = 0; i < omitCells.length; i++) {
                final int row = omitCells[i][0];
                final int col = omitCells[i][1];
                startRow = row;
                colsToOmit.add(col);
                lowerBound += matrix[row][col];
            }
        }

        final Double infinity = 1000000.0;
        for (int i = startRow + 1; i < matrix.length; i++) {
            Double rowMin = infinity;
            for (int j = 0; j < matrix.length; j++) {
                if (colsToOmit.contains(Integer.valueOf(j))) {
                    continue;
                }
                if (i != j) {
                    try {
                        if (omitCells != null) {
                            for (int k = 0; k < omitCells.length; k++) {
                                if (omitCells[k][0] == j && omitCells[k][1] == i) {
                                    throw new IllegalStateException("Eliminating sub-tour [" + i + "," + j + "] value "
                                            + matrix[i][j]);
                                }
                            }
                        }
                        rowMin = Math.min(rowMin, matrix[i][j]);
                    } catch (final IllegalStateException x) {
                        // System.out.println(x.getMessage());
                        continue;
                    }
                }
            }
            lowerBound += rowMin;
        }
        return lowerBound;
    }
}

class Node implements Comparable<Node> {
    private final Double cost;
    private final Integer id;
    private final Integer[][] cells;
    private List<Node> children;
    private Integer level = 0;

    public Node(final Integer id, final Double cost, final Integer[][] cells) {
        this.id = id;
        this.cost = cost;
        this.cells = cells;
        if (cells != null) {
            for (final Integer[] cell : cells) {
                this.level = Math.max(this.level, cell[0] + 1);
            }
        }
    }

    public Integer getLevel() {
        return this.level;
    }

    public void addChild(final Node child) {
        if (this.children == null) {
            this.children = new LinkedList<>();
        }
        this.children.add(child);
    }

    public Integer[][] getCells() {
        // return (Integer[][]) cells.toArray(new Integer[cells.size()][2]);
        return this.cells;
    }

    public Node[] getChildren() {
        return this.children.toArray(new Node[this.children.size()]);
    }

    public boolean isLeaf() {
        return this.children == null || this.children.size() == 0;
    }

    @Override
    public int compareTo(final Node o) {
        final int cmp = this.cost.compareTo(o.cost);
        if (cmp == 0) {
            return this.id.compareTo(o.id);
        }
        return cmp;
    }

    @Override
    public String toString() {
        return this.id + "[" + this.cost + "]";
    }
}