package com.barclays.pojos;

import com.airbaggage.placement.constants.EnumNodes;

public class Departure {
    private String flightId;
    private EnumNodes departureGate;
    private String destination;
    private String departureTime;

    public Departure(){
    }

    public Departure(String flightId, EnumNodes departureGate, String destination, String departureTime){
        this.flightId = flightId;
        this.departureGate = departureGate;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public EnumNodes getDepartureGate() {
        return departureGate;
    }

    public void setDepartureGate(EnumNodes departureGate) {
        this.departureGate = departureGate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "FlightDeparture{" +
                "flightId='" + flightId + '\'' +
                ", departureGate=" + departureGate +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
