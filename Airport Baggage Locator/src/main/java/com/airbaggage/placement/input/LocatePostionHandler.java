package com.airbaggage.placement.input;

import com.airbaggage.placement.business.LocatePosition;
import com.airbaggage.placement.constants.EnumNodes;
import com.barclays.pojos.Node;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class LocatePostionHandler implements LuggageInputInterface {

    private LocatePosition conveyorGraph;

    private Map<EnumNodes, Node> gateNodeMap = new HashMap<EnumNodes, Node>();

    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            boolean startGraphSection = false;
            boolean endGraphSection = false;

            while (scanner.hasNextLine() && !endGraphSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Conveyor System")) { //skip
                        startGraphSection = false;
                        continue;
                    } else if (line.endsWith("Conveyor System")) {
                        startGraphSection = true;
                        conveyorGraph = new LocatePosition();
                        continue;
                    } else if (startGraphSection && !line.endsWith("Conveyor System")) {
                        endGraphSection = true;
                    }
                }

                if (startGraphSection && !endGraphSection) {
                    //format :: <Node 1> <Node 2> <travel_time>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 3) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String from = tokens[0];
                    String to = tokens[1];
                    int cost = Integer.parseInt(tokens[2]);

                    EnumNodes fromGate = EnumNodes.getGate(from);
                    EnumNodes toGate = EnumNodes.getGate(to);

                    if (fromGate == null || toGate == null) {
                        throw new IOException("INVALID GATE FOUND...");
                    }

                    //add the bi-directional link in the barclays
                    conveyorGraph.addLink(createNode(fromGate, gateNodeMap), createNode(toGate, gateNodeMap), cost);
                    conveyorGraph.addLink(createNode(toGate, gateNodeMap), createNode(fromGate, gateNodeMap), cost);
                }

            }
        } finally {
            scanner.close();
        }
    }

    public LocatePosition getConveyorGraph() {
        return conveyorGraph;
    }

    public void setConveyorGraph(LocatePosition conveyorGraph) {
        this.conveyorGraph = conveyorGraph;
    }

    private Node createNode(EnumNodes gate, Map<EnumNodes, Node> nodeMap) {
        if (nodeMap.containsKey(gate)) {
            return nodeMap.get(gate);
        }
        Node conveyorNode = new Node(gate, gate.getValue());
        nodeMap.put(gate, conveyorNode);
        return conveyorNode;
    }
}
