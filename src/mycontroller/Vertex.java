package mycontroller;

import utilities.Coordinate;

/**
 * Class for representing a vertex in a graph
 * @author Nicholas Wong
 *
 */
public class Vertex {
    private final Coordinate id;

    /**
     * Constructor for Vertex
     * @param id
     */
    public Vertex(Coordinate id) {
        this.id = id;
    }
    
    public Coordinate getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}