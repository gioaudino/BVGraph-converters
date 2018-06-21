package com.gioaudino.thesis;

import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.IOException;

public class GetSuccessors {
    public static void main(String[] args) throws IOException {
        if (args.length < 2 || !args[1].matches("[0-9]+")) {
            System.err.println(String.format("USAGE: %s <basename> <node>", GetSuccessors.class.getName()));
            System.exit(-1);
        }

        Logger.log(GetSuccessors.class, "Loading graph");
        ImmutableGraph graph = ImmutableGraph.load(args[0]);
        Logger.log(GetSuccessors.class, "Graph loaded");


        NodeIterator iterator = graph.nodeIterator(Integer.parseInt(args[1]));
        int node = iterator.nextInt();

        Logger.log(GetSuccessors.class, String.format("NODE: %d - %d successors", node, iterator.outdegree()));

        LazyIntIterator successors = iterator.successors();
        int next;

        while ((next = successors.nextInt()) != -1)
            System.out.println(String.format("%d -> %d", Integer.parseInt(args[1]), next));

    }
}
