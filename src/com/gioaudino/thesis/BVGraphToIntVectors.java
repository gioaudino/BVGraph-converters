package com.gioaudino.thesis;

import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class BVGraphToIntVectors extends BVGraphConverter {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(String.format("USAGE: java %s <source> [<target>]", BVGraphToIntVectors.class.getName()));
            System.exit(1);
        }

        try {
            BVGraphToIntVectors converter = new BVGraphToIntVectors();
            converter.run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void run(String... args) throws IOException {

        Logger.log(this, "Loading graph");

        ImmutableGraph graph = ImmutableGraph.load(args[0]);

        Logger.log(this, "Graph loaded");

        String xOutput, yOutput;
        try {
            xOutput = args[1] + ".x";
            yOutput = args[1] + ".y";
        } catch (IndexOutOfBoundsException e) {
            xOutput = args[0] + ".x";
            yOutput = args[0] + ".y";
        }


        NativeDataOutputStream xOutputStream = new NativeDataOutputStream(new BufferedOutputStream(new FileOutputStream(xOutput)));
        NativeDataOutputStream yOutputStream = new NativeDataOutputStream(new BufferedOutputStream(new FileOutputStream(yOutput)));
        nodes = graph.numNodes();
        arcs = graph.numArcs();

        start = new Date();

        Logger logger = new Logger(this);
        logger.start();

        NodeIterator iterator = graph.nodeIterator();

        while (iterator.hasNext()) {
            int node = iterator.nextInt();
            this.printedNodes++;
            LazyIntIterator successors = iterator.successors();
            int next;
            while ((next = successors.nextInt()) != -1) {
                xOutputStream.writeInt(node);
                yOutputStream.writeInt(next);
                this.printedArcs++;
            }
            xOutputStream.flush();
            yOutputStream.flush();
        }
        logger.stopMe();
        logProgress();
        Logger.log(this, "Conversion completed");
        xOutputStream.flush();
        xOutputStream.close();
        yOutputStream.flush();
        yOutputStream.close();

    }

}
