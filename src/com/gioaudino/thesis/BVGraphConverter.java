package com.gioaudino.thesis;

import java.util.Date;

abstract class BVGraphConverter {
    long printedArcs = 0;
    int printedNodes = 0;
    Date start;
    int nodes;
    long arcs;

    void logProgress() {
        Logger.log(this,
                String.format("%d/%d nodes - %d/%d arcs - %d%% \t %s",
                        this.printedNodes,
                        this.nodes,
                        this.printedArcs,
                        this.arcs,
                        (int) (100 * this.printedArcs / arcs),
                        Logger.getETA(this.printedArcs, this.arcs, this.start))
        );
    }

}
