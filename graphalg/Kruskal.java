/* Kruskal.java */

package graphalg;

import graph.*;
import list.*;
import dict.*;
import set.*;
import sorting.*;



/**
 * @author Rachel, Ryan, Hiro
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */


public class Kruskal {

    /**
     * minSpanTree() returns a WUGraph that represents the minimum spanning tree
     * of the WUGraph g.  The original WUGraph g is NOT changed.
     *
     * @param g The weighted, undirected graph whose MST we want to compute.
     * @return A newly constructed WUGraph representing the MST of g.
     */
    public static WUGraph minSpanTree(WUGraph g) {
        Object[] verts= g.getVertices();
        WUGraph minSpanTree = new WUGraph();
        for (Object vertex : verts) {
            minSpanTree.addVertex(vertex);
        }
        //gets edges and adds to LinkedQueue
        LinkedQueue edgesQ = new LinkedQueue();
        Object[] currNeighbors;
        int[] currWeights;
        for (int counter = 0; counter < verts.length; counter++) {
            Neighbors current = g.getNeighbors(verts[counter]);  //similar to getNeighbors in WUGraph
            currNeighbors = current.neighborList;
            currWeights = current.weightList;
            for (int i = 0; i < currWeights.length; i++)
            {
                Edge x = new Edge(verts[counter], currNeighbors[i], currWeights[i]);
                edgesQ.enqueue(x);
            }
        }
        //edges are put in order using compareTo
        ListSorts.quickSort(edgesQ);
        //map into ints
        DisjointSets set = new DisjointSets(verts.length);
        HashTableChained verticesToInt = new HashTableChained(verts.length);
        for (int k = 0; k < verts.length; k++)
        {
            verticesToInt.insert(verts[k], k);
        }
        //iterate through
        while (!edgesQ.isEmpty())
        {
            Edge e = new Edge();
            try {
                e = (Edge)edgesQ.dequeue();
            } catch (QueueEmptyException exception) {
                System.err.println("Error");
            }
            Object uObj = e.getU();
            Object vObj = e.getV();
            int uNum = (int)verticesToInt.find(uObj).value();
            int vNum = (int)verticesToInt.find(vObj).value();
            //are u and v connected
            if (set.find(uNum) != set.find(vNum))
            {
                set.union(set.find(uNum), set.find(vNum));
                minSpanTree.addEdge(uObj, vObj, e.getWeight());
            }
        }
        return minSpanTree;
    }
}