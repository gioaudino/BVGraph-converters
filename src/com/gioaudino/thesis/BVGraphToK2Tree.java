package com.gioaudino.thesis;

import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class BVGraphToK2Tree extends BVGraphConverter{

    long printedArcs = 0;
    int printedNodes = 0;
    Date start;
    int nodes;
    long arcs;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(String.format("USAGE: java %s <source> [<target>]", BVGraphToK2Tree.class.getName()));
            System.exit(1);
        }

        try {
            BVGraphToK2Tree converter = new BVGraphToK2Tree();
            converter.run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void run(String... args) throws IOException {

        Logger.log(this, "Loading graph");

        ImmutableGraph graph = ImmutableGraph.load(args[0]);

        Logger.log(this, "Graph loaded");

        String outputFilename;
        try {
            outputFilename = args[1] + ".k2tree";
        } catch (IndexOutOfBoundsException e) {
            outputFilename = args[0] + ".k2tree";
        }


        NativeDataOutputStream outputStream = new NativeDataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFilename)));
        nodes = graph.numNodes();
        arcs = graph.numArcs();

        outputStream.writeInt(nodes);
        outputStream.writeLong(arcs);

        start = new Date();

        Logger logger = new Logger(this);
        logger.start();

        NodeIterator iterator = graph.nodeIterator();

        while (iterator.hasNext()) {
            int node = iterator.nextInt();
            outputStream.writeInt(-node - 1);
            this.printedNodes++;
            LazyIntIterator successors = iterator.successors();
            int next;
            while ((next = successors.nextInt()) != -1) {
                outputStream.writeInt(next + 1);
                this.printedArcs++;
            }
        }
        logger.stopMe();
        logProgress();
        Logger.log(this, "Conversion completed");
        outputStream.flush();
        outputStream.close();

    }


}
