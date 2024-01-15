/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * @author Rachel, Ryan, Hiro
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

    private HashTableChained vertexTable;
    private HashTableChained edgeTable;
    private int numOfEdges;
    private DList vList;

    /**
     * WUGraph() constructs a graph having no vertices or edges.
     *
     * Running time:  O(1).
     */
    public WUGraph(){
        vertexTable = new HashTableChained();
        edgeTable = new HashTableChained();
        vList = new DList();
    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     *
     * Running time:  O(1).
     */
    public int vertexCount(){
        return vList.length();
    }

    /**
     * edgeCount() returns the number of edges in the graph.
     *
     * Running time:  O(1).
     */
    public int edgeCount(){
        return numOfEdges;
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     *
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     *
     * Running time:  O(|V|).
     */


    public Object[] getVertices(){
        Object[] vertexList = new Object[vertexCount()];

        int counter = 0;
        ListNode current = vList.front();

        try{
            Vertex tempVertex;
            while (current.isValidNode()) {
                tempVertex = (Vertex)current.item();
                vertexList[counter] = tempVertex.key();
                current = current.next();
                counter++;
            }
        } catch(InvalidNodeException e) {
        }

        return vertexList;
    }


    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.  The
     * vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(1).
     */
    public void addVertex(Object vertex){
        if (vertexTable.find(vertex) == null){
            Vertex newVert = new Vertex(vertex);
            vList.insertFront(newVert);
            vertexTable.insert(vertex,vList.front());
        }
    }


    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */


    public void removeVertex(Object vertex){
        Entry entry = vertexTable.find(vertex);
        if(entry != null){
            ListNode node = (ListNode)entry.value();
            try{
                Vertex tempVert = (Vertex)(node.item());
                clearEdges(tempVert);
                vertexTable.remove(vertex);
                node.remove();
            } catch (InvalidNodeException e){

            }
        }
    }


    void clearEdges(Vertex v){
        ListNode edgeNode = v.currEdges.front();
        try{
            Vertex tempVert;
            while(edgeNode.isValidNode()){
                tempVert = (Vertex)edgeNode.item();
                edgeNode = edgeNode.next();
                removeEdge(v.key(),tempVert.key());
            }
        } catch(InvalidNodeException e) {

        }
    }


    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     *
     * Running time:  O(1).
     */
    public boolean isVertex(Object vertex){
        return vertexTable.find(vertex) !=  null;
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     *
     * Running time:  O(1).
     */
    public int degree(Object vertex){
        Entry entry = vertexTable.find(vertex);
        if(entry != null){
            ListNode node = (ListNode)entry.value();
            try {
                Vertex vert = (Vertex)node.item();
                return vert.degree();
            } catch (InvalidNodeException e) {

            }
        }
        return 0;
    }
    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     *
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     *
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */
    //inspiration from friend Yi Wei Wang :)
    public Neighbors getNeighbors(Object vertex){
        int degree = degree(vertex);
        if (degree == 0){
            return null;
        }

        Neighbors neighbors = new Neighbors();
        neighbors.neighborList = new Object[degree];
        neighbors.weightList = new int[degree];

        try {

            Entry entry = vertexTable.find(vertex);
            ListNode node = (ListNode)entry.value();
            Vertex vert = (Vertex)node.item();

            int counter = 0;
            Vertex tempVertex;
            node = vert.currEdges.front();

            while (node.isValidNode()){
                tempVertex = (Vertex)node.item();
                neighbors.weightList[counter] = weight(vertex, tempVertex.key());
                neighbors.neighborList[counter] = tempVertex.key();
                node = node.next();
                counter++;
            }
        }
        catch (InvalidNodeException e) {

        }

        return neighbors;
    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the edge is already
     * contained in the graph, the weight is updated to reflect the new value.
     * Self-edges (where u == v) are allowed.
     *
     * Running time:  O(1).
     */
    //god I hated this one lmao
    public void addEdge(Object u, Object v, int weight){
        if (!isVertex(u) || !isVertex(v)){
            return;
        }
        Entry tempEntry;
        VertexPair tempPair = new VertexPair(u, v);
        if (isEdge(u, v)){
            tempEntry = edgeTable.find(tempPair);
            Edge currEdge = (Edge)tempEntry.value();
            currEdge.setWeight(weight);
        } else {
            tempEntry = vertexTable.find(u);
            ListNode a = (ListNode)tempEntry.value();
            tempEntry = vertexTable.find(v);
            ListNode b = (ListNode)tempEntry.value();
            try{
                List tempEdgeListA = ((Vertex)a.item()).currEdges;
                List tempEdgeListB = ((Vertex)b.item()).currEdges;
                tempEdgeListA.insertFront(b.item());
                if(a != b){
                    tempEdgeListB.insertFront(a.item());
                }
                Edge newEdge = new Edge(tempEdgeListA.front(), tempEdgeListB.front(), weight);
                edgeTable.insert(tempPair, newEdge);
                numOfEdges++;

            }
            catch (InvalidNodeException e) {

            }
        }
    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     *
     * Running time:  O(1).
     */
    public void removeEdge(Object u, Object v){
        VertexPair pair = new VertexPair(u, v);
        //takes care of all the cases that the edge might not exist
        if (!isVertex(v) || !isVertex(u) || !isEdge(u, v)){
            return;
        }
        Entry tempEntry = edgeTable.find(pair);
        Edge currEdge = (Edge)tempEntry.value();
        currEdge.remove();
        edgeTable.remove(pair);
        numOfEdges--;
    }
    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     *
     * Running time:  O(1).
     */
    public boolean isEdge(Object u, Object v){
        if ( !(isVertex(v) && isVertex(u)) ){
            return false;
        }
        VertexPair tempPair = new VertexPair(u, v);
        return edgeTable.find(tempPair) != null;
    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     *
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but
     * also more annoying.)
     *
     * Running time:  O(1).
     */
    public int weight(Object u, Object v){
        if (isEdge(u, v)){
            VertexPair tempPair = new VertexPair(u, v);
            Edge currEdge = (Edge)(edgeTable.find(tempPair).value());
            return currEdge.getWeight();
        }
        return 0;
    }

}
