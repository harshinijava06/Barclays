package com.airbaggage.placement.input;

import com.airbaggage.placement.constants.EnumNodes;
import com.barclays.pojos.Departure;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FlightDepartureHandler implements LuggageInputInterface {

    Map<String, Departure> flightIdToDepartureMap;

   
    public Map<String, Departure> getFlightIdToDepartureMap() {
        return flightIdToDepartureMap;
    }

    public void setFlightIdToDepartureMap(Map<String, Departure> flightIdToDepartureMap) {
        this.flightIdToDepartureMap = flightIdToDepartureMap;
    }

    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
            boolean startDepartureSection = false;
            boolean endDepartureSection = false;

            while (scanner.hasNextLine() && !endDepartureSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Departures")) { //skip
                        startDepartureSection = false;
                        continue;
                    } else if (line.endsWith("Departures")) {
                        startDepartureSection = true;
                        flightIdToDepartureMap = new HashMap<String, Departure>();
                        continue;
                    } else if (startDepartureSection && !line.endsWith("Departures")) {
                        endDepartureSection = true;
                    }
                }

                if (startDepartureSection && !endDepartureSection) {
                    //Format: <flight_id> <flight_gate> <destination> <flight_time>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 4) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String flightId = tokens[0];
                    String flightGate = tokens[1];
                    String destination = tokens[2];
                    String departureTime = tokens[3];

                    EnumNodes departureGate = EnumNodes.getGate(flightGate);

                    if (departureGate == null) {
                        throw new IOException("INVALID GATE FOUND...");
                    }

                    flightIdToDepartureMap.put(flightId, new Departure(flightId, departureGate, destination, departureTime));
                }

            }
        }finally {
            scanner.close();
        }
    }

}
