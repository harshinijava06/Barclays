package com.barclays.pojos;

import com.airbaggage.placement.constants.EnumNodes;


public class Node implements Comparable<Node>{

    private EnumNodes nodeId;
    private String nodeName;
    private Double minDistance = Double.POSITIVE_INFINITY;
    private Node previous;

    public Node(EnumNodes nodeId, String nodeName){
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public EnumNodes getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || !(obj instanceof Node)){
            return false;
        }

        Node other = (Node) obj;

        return (this.nodeId.getValue().equals(other.nodeId.getValue()));
    }

    @Override
    public int hashCode() {
        return nodeId.getValue().hashCode();
    }

    public int compareTo(Node other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
