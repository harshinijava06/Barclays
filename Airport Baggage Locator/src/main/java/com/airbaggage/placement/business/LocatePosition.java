package com.airbaggage.placement.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.barclays.pojos.ConveyorLink;
import com.barclays.pojos.Node;


public class LocatePosition implements Position<Node, ConveyorLink> {

	private Map<Node, List<ConveyorLink>> neighbors = new HashMap<Node, List<ConveyorLink>>();

	private Set<Node> nodes = new HashSet<Node>();

	private Set<ConveyorLink> links = new HashSet<ConveyorLink>();


	public void addNode(Node node) {
		if (!neighbors.containsKey(node)) {
			neighbors.put(node, new ArrayList<ConveyorLink>());
			nodes.add(node);
		}
	}


	public boolean isLink(Node from, Node to) {
		List<ConveyorLink> links = neighbors.get(from);
		if (links != null && !links.isEmpty()) {
			for (ConveyorLink conveyorLink : links) {
				if (conveyorLink.getTo().equals(to)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Add a link to the Graph.
	 *
	 * @param from
	 * @param to
	 * @param cost
	 */
	 public void addLink(Node from, Node to, double cost) {
		 addNode(from);
		 addNode(to);

		 if (!isLink(from, to)) {
			 ConveyorLink link = new ConveyorLink(from, to, cost);
			 neighbors.get(from).add(link);
			 links.add(link);
		 }
	 }

	 public ConveyorLink getLink(Node source, Node target) {
		 List<ConveyorLink> links = neighbors.get(source);
		 for (ConveyorLink link : links) {
			 if (link.getTo().equals(target)) {
				 return link;
			 }
		 }
		 return null;
	 }


	 public Iterable<Node> getNodes() {
		 return nodes;
	 }

	 public int getOrder() {
		 return neighbors.size();
	 }

	 public Iterable<ConveyorLink> getLinks() {
		 return links;
	 }

	 public int getSize() {
		 return links.size();
	 }

	 public boolean containsNode(Node conveyorNode) {
		 return neighbors.containsKey(conveyorNode);
	 }

	 public boolean containsLink(ConveyorLink conveyorLink) {
		 return links.contains(conveyorLink);
	 }

	 public List<Node> findShortestPath(Node source, Node target) {
		 List<Node> shortestPath = new ArrayList<Node>();

		 source.setMinDistance(0D);

		 PriorityQueue<Node> vertexQueue = new PriorityQueue<Node>();

		 for (Node vertex : nodes) {
			 if (!vertex.equals(source)) {
				 vertex.setMinDistance(Double.POSITIVE_INFINITY);
				 vertex.setPrevious(null);
			 } else {
				 vertex = source;
			 }
			 vertexQueue.add(vertex);
		 }

		 while (!vertexQueue.isEmpty()) {
			 Node u = vertexQueue.poll();

			 if (u.equals(target)) {
				 while (u.getPrevious() != null) {
					 shortestPath.add(u);
					 u = u.getPrevious();
				 }
				 break;
			 }

			 vertexQueue.remove(u);

			 List<ConveyorLink> edges = neighbors.get(u);

			 for (ConveyorLink edge : edges) {
				 Node v = edge.getTo();

				 double weight = edge.getCost();
				 double distanceThroughU = u.getMinDistance() + weight;

				 if (distanceThroughU < v.getMinDistance()) {
					 v.setMinDistance(distanceThroughU);
					 v.setPrevious(u);
					 vertexQueue.remove(v);
					 vertexQueue.add(v);
				 }
			 }
		 }

		 Collections.reverse(shortestPath);

		 return shortestPath;
	 }

	 @Override
	 public String toString() {
		 StringBuffer sb = new StringBuffer();
		 for (Node node : neighbors.keySet()) {
			 sb.append("\n    " + node.getNodeId().getValue() + " -> " + neighbors.get(node));
		 }
		 return sb.toString();
	 }

	 public Map<Node, List<ConveyorLink>> getNeighbors() {
		 return neighbors;
	 }

}
