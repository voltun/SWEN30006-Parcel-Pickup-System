package mycontroller;

import java.util.ArrayList;
import java.util.List;
import utilities.Coordinate;

/**
 * @author Nicholas Wong
 *
 */
public class Graph {
	private static final int ROAD_WEIGHT = 1;
	
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public Graph(Vertex node) {
        this.vertexes = new ArrayList<Vertex>();
        vertexes.add(node);
        this.edges = new ArrayList<Edge>();
    }
    
    
    /**
     * Adds a new vertex into the list of vertexes of this graph,
     * also connects the vertexes through edges if they are next to 
     * each other
     * @param node 
     */
    public void addNode(Vertex node) {
    	vertexes.add(node);
    	connectAdjacent(node);
    }
    
    //Creates edges between 2 vertexes if they are in next to each other
    private void connectAdjacent(Vertex node) {
    	Coordinate nodeID = node.getId();
    	Vertex left = new Vertex(new Coordinate(nodeID.x-1, nodeID.y));
    	Vertex right = new Vertex(new Coordinate(nodeID.x+1, nodeID.y));
    	Vertex up = new Vertex(new Coordinate(nodeID.x, nodeID.y+1));
    	Vertex down = new Vertex(new Coordinate(nodeID.x, nodeID.y-1));
    	
    	checkAndAddAdjacent(node, left);
    	checkAndAddAdjacent(node, right);
    	checkAndAddAdjacent(node, up);
    	checkAndAddAdjacent(node, down);
    }
    
    //Adds 2 edges if its a new node to create undirected graph
    private void checkAndAddAdjacent(Vertex newNode, Vertex adjNode) {
    	if(hasVertex(adjNode)) {
    		edges.add(new Edge(adjNode, newNode, ROAD_WEIGHT));
    		edges.add(new Edge(newNode, adjNode, ROAD_WEIGHT));
    	}
    }
    
    /**
     * Checks if a node is already inside the graph's vertexes list
     * @param vertex - the node to be checked
     * @return
     */
    public boolean hasVertex(Vertex vertex) {
    	return vertexes.contains(vertex);
    }
    
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
