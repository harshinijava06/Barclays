package com.airbaggage.placement;

import com.airbaggage.placement.business.LocatePosition;
import com.airbaggage.placement.constants.EnumNodes;
import com.airbaggage.placement.input.BagInputInterface;
import com.airbaggage.placement.input.LocatePostionHandler;
import com.airbaggage.placement.input.FlightDepartureHandler;
import com.barclays.pojos.LuggageBag;
import com.barclays.pojos.Node;
import com.barclays.pojos.Departure;

import java.util.List;
import java.util.Map;


public class App {

    public static void main(String[] args) {

        LocatePosition conveyorGraph = null;
        Map<String, Departure> flightIdToDepartureMap = null;
        Map<String, LuggageBag> bagIdToBagMap = null;

        LocatePostionHandler conveyorGraphHandler = new LocatePostionHandler();
        try {
            conveyorGraphHandler.process();
            conveyorGraph = conveyorGraphHandler.getConveyorGraph();
            //System.out.println(conveyorGraph);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FlightDepartureHandler flightDepartureHandler = new FlightDepartureHandler();
        try {
            flightDepartureHandler.process();
            flightIdToDepartureMap = flightDepartureHandler.getFlightIdToDepartureMap();
            //System.out.println(flightIdToDepartureMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        BagInputInterface bagHandler = new BagInputInterface();
        try {
            bagHandler.process();
            bagIdToBagMap = bagHandler.getBagIdToBagMap();
            //System.out.println(bagIdToBagMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        StringBuffer output = new StringBuffer();

        for (Map.Entry<String, LuggageBag> entry : bagIdToBagMap.entrySet()) {
            LuggageBag bag = entry.getValue();
            String bagId = bag.getId();
            String flightId = bag.getFlightId();
            EnumNodes sourceGate = bag.getEntryPoint();

            output.append(bagId + " ");


            EnumNodes departureGate = null;
            if (flightId.equals("ARRIVAL")) {
                departureGate = sourceGate.BAGGAGE_CLAIM;
            } else {
                departureGate = flightIdToDepartureMap.get(flightId).getDepartureGate();
            }

            Node sourceNode = new Node(sourceGate, sourceGate.getValue());
            Node targetNode = new Node(departureGate, departureGate.getValue());
            List<Node> shortestPath = conveyorGraph.findShortestPath(sourceNode, targetNode);

            if (!shortestPath.isEmpty()) {
                output.append(sourceGate.getValue() + " ");
                Node prevNode = shortestPath.get(0);
                output.append(prevNode.getNodeId().getValue() + " ");

                for (int i = 1; i < shortestPath.size(); i++) {
                    Node current = shortestPath.get(i);
                    prevNode = current;
                    output.append(current.getNodeId().getValue() + " ");
                }
                output.append(": " + prevNode.getMinDistance());
                output.append(System.lineSeparator());
            } else { //PATH NOT FOUND
                output.append("PATH_NOT_EXISTS");
                output.append(System.lineSeparator());
            }
        }

        System.out.println(output.toString());
    }
}
