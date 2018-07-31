package com.barclays.pojos;

import java.util.Objects;


public final class ConveyorLink {

    private Node from;
    private Node to;
    private double cost;

    public ConveyorLink(Node from, Node to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof Node)) {
            return false;
        }

        ConveyorLink other = (ConveyorLink) obj;

        return (this.from.equals(other.from) && this.to.equals(other.to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Link [" + from.getNodeId().getValue() + "->" + to.getNodeId().getValue() + " : " + cost + "]";
    }
}
