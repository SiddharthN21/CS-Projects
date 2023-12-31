/**
 * Description - This class has properties of directed graphs. The computeInDegrees() function is used to determine
 * the indegrees of all vertices while the dagCheck() function is used to determine if graph G is a directed acyclic
 * graph.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 9, 2023
 */

public class DirectedCheck {
    public static int[] computeInDegrees(ListGraph a) {
        int n = a.localAdjacencyList.length;
        int[] inDeg = new int[n];

        for (int i = 0; i < n; i++) {
            Vertex tempVertex = a.localAdjacencyList[i].head.next;
            while (tempVertex != null) {
                inDeg[tempVertex.data] = inDeg[tempVertex.data] + 1;
                tempVertex = tempVertex.next;
            }
        }
        return inDeg;
    }

    public static int[] dagCheck(ListGraph a) {
        AdjacencyList[] tempAdjList = a.localAdjacencyList;
        int n = a.localAdjacencyList.length;
        int[] numSourcesSinks = new int[2];
        int totalSources = 0;
        int totalSinks = 0;

        AdjacencyList nodesZeroInDegree = new AdjacencyList();

        for (int i = 0; i < n; i++) {
            if (tempAdjList[i].inDegree == 0 ) {
                nodesZeroInDegree.insert(tempAdjList[i].head.data);
            }
            if (tempAdjList[i].inDegree == 0 && tempAdjList[i].outDegree > 0) {
                totalSources++;
            }
            else if (tempAdjList[i].inDegree > 0 && tempAdjList[i].outDegree == 0) {
                totalSinks++;
            }
            else if (tempAdjList[i].inDegree == 0 && tempAdjList[i].outDegree == 0) {
                totalSources++;
                totalSinks++;
            }
        }

        int visited = 0;

        while (nodesZeroInDegree.head != null) {
            Vertex tempNode = tempAdjList[nodesZeroInDegree.head.data].head.next;
            while ( tempNode != null) {
                tempAdjList[tempNode.data].inDegree--;
                if ( tempAdjList[tempNode.data].inDegree == 0) {
                    nodesZeroInDegree.insert(tempAdjList[tempNode.data].head.data);
                }
                tempNode = tempNode.next;
            }
            visited++;
            nodesZeroInDegree.head = nodesZeroInDegree.head.next;
        }
        if (visited == n) {
            numSourcesSinks[0] = totalSources;
            numSourcesSinks[1] = totalSinks;
        }
        else {
            numSourcesSinks[0] = -1;
            numSourcesSinks[1] = -1;
        }
        return numSourcesSinks;
    }
}
