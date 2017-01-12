/**
 * File Name    : TwoSAT.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TwoSAT {
    private Map<Integer, Variable> variableMap;

    public TwoSAT(final String graphFile) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(graphFile)));
            final String[] firstLine = in.readLine().split(" ");
            // int variableCount = Integer.parseInt(firstLine[0]);
            this.variableMap = new LinkedHashMap<>();
            // System.out.println("File "+graphFile+": Variables "+variableCount);
            String line;
            while ((line = in.readLine()) != null) {
                final String[] split = line.split(" ");
                final Integer i1 = Integer.parseInt(split[0]);
                final Integer i2 = Integer.parseInt(split[1]);
                if (!this.variableMap.containsKey(i1)) {
                    this.variableMap.put(i1, new Variable(i1));
                }
                if (!this.variableMap.containsKey(-i1)) {
                    this.variableMap.put(-i1, new Variable(-i1));
                }
                if (!this.variableMap.containsKey(i2)) {
                    this.variableMap.put(i2, new Variable(i2));
                }
                if (!this.variableMap.containsKey(-i2)) {
                    this.variableMap.put(-i2, new Variable(-i2));
                }
                final Variable v1 = this.variableMap.get(i1);
                final Variable v2 = this.variableMap.get(i2);
                final Variable v1Compliment = this.variableMap.get(-i1);
                final Variable v2Compliment = this.variableMap.get(-i2);
                v1Compliment.addImplicationTo(v2);
                v2Compliment.addImplicationTo(v1);

                // 'From implications' are only to help with transpose of the graph
                v1.addImplicationFrom(v2Compliment);
                v2.addImplicationFrom(v1Compliment);
            }
        } finally {
            in.close();
        }
    }

    public boolean compute() {
        final List<Variable> order = new LinkedList<>();

        // Step 1: Run DFS on transpose of the graph (i.e. direction of edges reversed) and order vertices.
        for (final Integer i : this.variableMap.keySet()) {
            final Variable v = this.variableMap.get(i);
            if (!v.isSeen()) {
                dfs(v, order, true);
                order.add(v);
            }
        }

        // Reset variables back to 'unseen'
        for (final Integer i : this.variableMap.keySet()) {
            this.variableMap.get(i).reset();
        }

        // Step 2: Strongly connected components on the original graph
        final Set<List<Variable>> strongComponents = new LinkedHashSet<>();
        Collections.reverse(order);
        for (final Variable v : order) {
            final List<Variable> scc = new LinkedList<>();
            dfs(v, scc, false);
            scc.add(v);
            if (scc.size() > 1) {
                strongComponents.add(scc);
            }
        }

        // If a variable and it negation are both in one component then its unsatisfiable
        for (final List<Variable> vList : strongComponents) {
            final Set<Integer> set = new LinkedHashSet<>();
            for (final Variable v : vList) {
                if (!set.add(Math.abs(v.getId()))) {
                    return false;
                }
            }
        }

        return true;
    }

    private void dfs(final Variable v, final List<Variable> list, final boolean reverseOrder) {
        v.seen();
        for (final Variable to : v.getImplications(reverseOrder)) {
            if (!to.isSeen()) {
                dfs(to, list, reverseOrder);
                list.add(to);
            }
        }
    }
}
