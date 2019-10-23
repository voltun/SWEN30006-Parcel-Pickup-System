package mycontroller;

import java.util.List;

/**
 * @author Nicholas Wong
 *
 */
public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }
    
    //TODO allow graph to add a new node into list
    public void addNode(Vertex node) {
    	
    	
    }
    
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
