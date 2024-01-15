/* Edge.java */

package graph;
import list.*;

/**
 * An Edge is meant to be the internal representation of an edge
 */

public class Edge implements Comparable {

    protected ListNode tempVertexA;
    protected ListNode tempVertexB;
    protected int weight;
    protected Object vertexU;
    protected Object vertexV;
    public Edge()
    {
        this.vertexU = null;
        this.vertexV = null;
        this.weight = 0;
    }
    public Edge(Object u, Object v, int weight) {
        this.vertexU = u;
        this.vertexV = v;
        this.weight = weight;
    }
    public Edge(ListNode a, ListNode b, int w) {
        tempVertexA = a;
        tempVertexB = b;
        this.weight = w;
    }

    public void setWeight(int w){
        weight = w;
    }

    public int getWeight(){
        return weight;
    }

    public Object getU()
    {
        return vertexU;
    }

    public Object getV()
    {
        return vertexV;
    }
    public void remove(){
        try {
            if(tempVertexA == tempVertexB){
                tempVertexA.remove();
            } else {
                tempVertexA.remove();
                tempVertexB.remove();
            }
        }
        catch(InvalidNodeException e){

        }
    }
    public int compareTo(Object o)
    {
        Edge e = (Edge)o;
        if (((Edge)e).getWeight() < getWeight())
            return 1;
        else if (((Edge)e).getWeight() > getWeight())
            return -1;
        else if (e.getU().equals(getU()) && e.getV().equals(getV()) && e.getWeight() == getWeight())
            return 0;
        return -1;
    }

}