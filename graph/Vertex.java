/* Vertex.java */

package graph;
import list.*;

/**
 * A Vertex is meant to be the internal representation of a vertex.
 * The vertex object from the application is passed in and set to be "vertKey"
 * A vertex has a DList of vertices that represent it's neighbors
 */

class Vertex {

    protected Object vertexKey;
    protected DList currEdges;

    Vertex(Object v) {
        vertexKey = v;
        currEdges = new DList();
    }

    /*
     * degree() returns the degree of this vertex.
     * @return the degree of this vertex
     */
    int degree(){
        return currEdges.length();
    }

    Object key(){
        return vertexKey;
    }

}