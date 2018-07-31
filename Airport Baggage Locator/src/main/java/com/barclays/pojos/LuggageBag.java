package com.barclays.pojos;

import com.airbaggage.placement.constants.EnumNodes;


public class LuggageBag {
    private String id;
    private EnumNodes entryPoint;
    private String flightId;

    public LuggageBag(){

    }

    public LuggageBag(String bagId, EnumNodes entryPoint, String flightId){
        this.id = bagId;
        this.entryPoint = entryPoint;
        this.flightId = flightId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumNodes getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EnumNodes entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", entryPoint=" + entryPoint +
                ", flightId='" + flightId + '\'' +
                '}';
    }
}
