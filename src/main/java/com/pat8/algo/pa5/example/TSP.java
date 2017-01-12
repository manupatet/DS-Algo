/**
 * File Name    : TSP.java
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
import java.util.ArrayList;
import java.util.Random;

public class TSP
{
    // Fields
    private final int numRows;
    private final int numCols;
    private final TimeInterval t = new TimeInterval();
    private double bestTour = Double.MAX_VALUE / 4;
    private Node bestNode;
    public static Cost<Double> c;
    public static ArrayList<Point> newEdge = new ArrayList<>();
    // Contains objects of type Point
    private int newNodeCount = 0;
    private int numberPrunedNodes = 0;
    private final Random rnd = new Random();
    public TSP(final Double[][] costMatrix, final int size, final int bestTour)
    {
        this.bestTour = bestTour;
        this.numRows = this.numCols = size;
        c = new Cost<Double>(this.numRows, this.numCols);
        for (int row = 1; row <= size; row++)
        {
            for (int col = 1; col <= size; col++)
            {
                c.assignCost(costMatrix[row][col], row, col);
            }
        }
    }
    public TSP(final Double[][] costMatrix)
    {
        this.numRows = this.numCols = costMatrix.length;
        c = new Cost<Double>(this.numRows+2, this.numCols+2);
        for (int row = 1; row <= this.numRows; row++)
        {
            for (int col = 1; col <= this.numCols; col++)
            {
                c.assignCost(costMatrix[row-1][col-1], row, col);
            }
        }
    }
    public void generateSolution()
    {
        Point pt;
        // Load newEdge Vector of edge points
        for (int row = 1; row <= this.numRows; row++)
        {
            for (int col = row + 1; col <= this.numCols; col++)
            {
                pt = new Point(row, col);
                newEdge.add(pt);
                pt = new Point(-row, -col);
                newEdge.add(pt);
            }
        }
        // Create root node
        final Node root = new Node(this.numRows, this.numCols);
        this.newNodeCount++;
        root.computeLowerBound();
        System.out.println("Twice the lower bound for root node (no constraints): " + root.lowerBound());
        // Apply the branch and bound algorithm
        this.t.startTiming();
        branchAndBound(root, -1);
        this.t.endTiming();
        if (this.bestNode != null)
        {
            System.out.println("\n\nCost of optimum tour: " + this.bestTour + "\nOptimum tour: " + this.bestNode.tour()
                               + "\nTotal of nodes generated: " + this.newNodeCount + "\nTotal number of nodes pruned: "
                               + this.numberPrunedNodes);
        }
        else
        {
            System.out.println("Tour obtained heuristically is the best tour.");
        }
        System.out.println("Elapsed time for entire algorithm: " + this.t.getElapsedTime() + " seconds.");
        System.out.println();
    }
    // Queries
    public int nodesCreated()
    {
        return this.newNodeCount;
    }
    public int nodesPruned()
    {
        return this.numberPrunedNodes;
    }
    public String tour()
    {
        if (this.bestNode != null)
        {
            return this.bestNode.tour();
        }
        else
        {
            return "";
        }
    }
    public double tourCost()
    {
        return this.bestTour;
    }
    public byte[] trip()
    {
        if (this.bestNode != null)
        {
            return this.bestNode.trip();
        }
        else
        {
            return null;
        }
    }
    private void branchAndBound(final Node node, int edgeIndex)
    {
        if (node != null && edgeIndex < newEdge.size())
        {
            Node leftChild, rightChild;
            int leftEdgeIndex = 0, rightEdgeIndex = 0;
            if (node.isTour())
            {
                node.setTour();
                if (node.tourCost() < this.bestTour)
                {
                    this.bestTour = node.tourCost();
                    this.bestNode = node;
                    System.out.println("\n\nBest tour cost so far: " + this.bestTour + "\nBest tour so far: "
                            + this.bestNode.tour() + "\nNumber of nodes generated so far: " + this.newNodeCount
                            + "\nTotal number of nodes pruned so far: " + this.numberPrunedNodes
                            + "\nElapsed time to date for branch and bound: " + this.t.getElapsedTime() + " seconds.\n");
                }
            }
            else
            {
                if (node.lowerBound() < 2 * this.bestTour)
                {
                    // Create left child node
                    leftChild = new Node(this.numRows, this.numCols);
                    this.newNodeCount++;
                    if (this.newNodeCount % 1000 == 0)
                    {
                        final Point p = newEdge.get(edgeIndex);
                        this.t.endTiming();
                        System.out.println("\nTotal number of nodes created so far: " + this.newNodeCount
                                           + "\nTotal number of nodes pruned so far: " + this.numberPrunedNodes
                                           + "\nElapsed time to date for branch and bound: " + this.t.getElapsedTime() + " seconds.");
                    }
                    else if (this.newNodeCount % 25 == 0)
                    {
                        System.out.print(".");
                    }
                    if (this.newNodeCount % 10000 == 0 && this.bestNode != null)
                    {
                        System.out.println("\n\nBest tour cost so far: " + this.bestTour + "\nBest tour so far: "
                                + this.bestNode.tour());
                    }
                    leftChild.setConstraint(copy(node.constraint()));
                    if (edgeIndex != -1 && newEdge.get(edgeIndex).getX() > 0)
                    {
                        edgeIndex += 2;
                    }
                    else
                    {
                        edgeIndex++;
                    }
                    if (edgeIndex >= newEdge.size())
                    {
                        return;
                    }
                    Point p = newEdge.get(edgeIndex);
                    leftEdgeIndex = leftChild.assignPoint(p, edgeIndex);
                    leftChild.addDisallowedEdges();
                    leftChild.addRequiredEdges();
                    leftChild.addDisallowedEdges();
                    leftChild.addRequiredEdges();
                    leftChild.computeLowerBound();
                    if (leftChild.lowerBound() >= 2 * this.bestTour)
                    {
                        leftChild = null;
                        this.numberPrunedNodes++;
                    }
                    // Create right child node
                    rightChild = new Node(this.numRows, this.numCols);
                    this.newNodeCount++;
                    if (this.newNodeCount % 1000 == 0)
                    {
                        System.out.println("\nTotal number of nodes created so far: " + this.newNodeCount
                                           + "\nTotal number of nodes pruned so far: " + this.numberPrunedNodes);
                    }
                    else if (this.newNodeCount % 25 == 0)
                    {
                        System.out.print(".");
                    }
                    rightChild.setConstraint(copy(node.constraint()));
                    if (leftEdgeIndex >= newEdge.size())
                    {
                        return;
                    }
                    p = newEdge.get(leftEdgeIndex + 1);
                    rightEdgeIndex = rightChild.assignPoint(p, leftEdgeIndex + 1);
                    rightChild.addDisallowedEdges();
                    rightChild.addRequiredEdges();
                    rightChild.addDisallowedEdges();
                    rightChild.addRequiredEdges();
                    rightChild.computeLowerBound();
                    if (rightChild.lowerBound() > 2 * this.bestTour)
                    {
                        rightChild = null;
                        this.numberPrunedNodes++;
                    }
                    if (leftChild != null && rightChild == null)
                    {
                        branchAndBound(leftChild, leftEdgeIndex);
                    }
                    else if (leftChild == null && rightChild != null)
                    {
                        branchAndBound(rightChild, rightEdgeIndex);
                    }
                    else if (leftChild != null && rightChild != null
                            && leftChild.lowerBound() <= rightChild.lowerBound())
                    {
                        if (leftChild.lowerBound() < 2 * this.bestTour)
                        {
                            branchAndBound(leftChild, leftEdgeIndex);
                        }
                        else
                        {
                            leftChild = null;
                            this.numberPrunedNodes++;
                        }
                        if (rightChild.lowerBound() < 2 * this.bestTour)
                        {
                            branchAndBound(rightChild, rightEdgeIndex);
                        }
                        else
                        {
                            rightChild = null;
                            this.numberPrunedNodes++;
                        }
                    }
                    else if (rightChild != null)
                    {
                        if (rightChild.lowerBound() < 2 * this.bestTour)
                        {
                            branchAndBound(rightChild, rightEdgeIndex);
                        }
                        else
                        {
                            rightChild = null;
                            this.numberPrunedNodes++;
                        }
                        if (leftChild.lowerBound() < 2 * this.bestTour)
                        {
                            branchAndBound(leftChild, leftEdgeIndex);
                        }
                        else
                        {
                            leftChild = null;
                            this.numberPrunedNodes++;
                        }
                    }
                }
            }
        }
    }
    private byte[][] copy(final byte[][] constraint)
    {
        final byte[][] toReturn = new byte[this.numRows + 1][this.numCols + 1];
        for (int row = 1; row <= this.numRows; row++)
        {
            for (int col = 1; col <= this.numCols; col++)
            {
                toReturn[row][col] = constraint[row][col];
            }
        }
        return toReturn;
    }
}