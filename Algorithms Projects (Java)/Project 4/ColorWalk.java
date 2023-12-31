/**
 * Description - This class has a function to find the shortest find the shortest walk to each vertex from the source
 * vertex. The class has helper functions for this.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 30, 2023
 */
public class ColorWalk {
    public static class WalkPair {
        char startColor;
        int walkWeight;
    }
    public static WalkPair[] colorWalk(Graph G, int start) {
        MyWalkPath[] pathCollection = new MyWalkPath[G.vertices];
        WalkPair[] result = new WalkPair[G.vertices];
        AdjacencyList startNode;
        int currWeight = 0;

        for (int i = 0; i < G.vertices; i++) {
            pathCollection[i] = new MyWalkPath();
        }

        startNode = G.localAdjRed[start];
        pathCollection[start].redWeight = 0;
        convertGraphToColorPath(G, start, 'R', 'R', pathCollection, startNode, startNode, currWeight);
        G.resetVisitedFlag();
        startNode = G.localAdjGreen[start];
        pathCollection[start].greenWeight = 0;
        convertGraphToColorPath(G, start, 'G', 'G', pathCollection, startNode, startNode, currWeight);
        G.resetVisitedFlag();
        startNode = G.localAdjBlue[start];
        pathCollection[start].blueWeight = 0;
        convertGraphToColorPath(G, start, 'B', 'B', pathCollection, startNode, startNode, currWeight);


        for (int i = 0; i < G.vertices; i++) {
            result[i] = new WalkPair();
            if (pathCollection[i].redWeight <= pathCollection[i].greenWeight  &&
                    pathCollection[i].redWeight <= pathCollection[i].blueWeight ) {
                result[i].startColor = 'R';
                result[i].walkWeight = pathCollection[i].redWeight;
            } else if (pathCollection[i].greenWeight <= pathCollection[i].redWeight  &&
                    pathCollection[i].greenWeight <= pathCollection[i].blueWeight ) {
                result[i].startColor = 'G';
                result[i].walkWeight = pathCollection[i].greenWeight;
            } else if (pathCollection[i].blueWeight <= pathCollection[i].redWeight  &&
                    pathCollection[i].blueWeight <= pathCollection[i].greenWeight ) {
                result[i].startColor = 'B';
                result[i].walkWeight = pathCollection[i].blueWeight;
            }
            if (i == start) {
                result[i].startColor = '-';
                result[i].walkWeight = 0;
            }
            if (result[i].walkWeight == Integer.MAX_VALUE) {
                result[i].startColor = '-';
                result[i].walkWeight = -1;
            }
        }
        return result;
    }

    public static void convertGraphToColorPath(Graph G, int startIdx, char startColor, char currentColor,
                                               MyWalkPath[] pathCollection, AdjacencyList currNode, AdjacencyList orgNode, int currWeight){
        Vertex nextVertex = currNode.head.next;
        AdjacencyList nextNode = new AdjacencyList();
        AdjacencyList prevNode = new AdjacencyList();
        char nextColor = '-';
        int previousWeight;
        AdjacencyList[] pQueue = new AdjacencyList[G.edges + G.vertices];

        while (currNode != null && nextVertex !=null) {
            nextVertex.visited = true;
            currentColor = currNode.nodeColor;

            previousWeight = currWeight;
            currWeight = currWeight + nextVertex.edgeWeight;

            if (nextVertex.data != startIdx) {
                if (startColor == 'R') {
                    pathCollection[nextVertex.data].redWeight =  Math.min(currWeight,  pathCollection[nextVertex.data].redWeight);
                } else if (startColor == 'G') {
                    pathCollection[nextVertex.data].greenWeight = Math.min(currWeight,  pathCollection[nextVertex.data].greenWeight);
                } else if (startColor == 'B') {
                    pathCollection[nextVertex.data].blueWeight = Math.min(currWeight,  pathCollection[nextVertex.data].blueWeight);
                }
            }

            if (currentColor == 'R') {
                nextColor = 'G';
                nextNode = G.localAdjGreen[nextVertex.data];
            }
            else if (currentColor == 'G') {
                nextColor = 'B';
                nextNode = G.localAdjBlue[nextVertex.data];
            }
            else if (currentColor == 'B') {
                nextColor = 'R';
                nextNode = G.localAdjRed[nextVertex.data];
            }

            //insert Node into pQueue for later exploration.
            if (nextNode.visited == false && nextNode.head.next !=null && !nodeNotYetExplored(nextNode, pQueue)) {
                nextNode.nodeWeight = currWeight;
                nextNode.preNodeWeight = currNode.nodeWeight;
                insertPQueue(nextNode, pQueue);
            }
            else {
                //Revisit part of tree if node weight reduced.
                if (nextNode.nodeWeight > currWeight && nextNode.head.next !=null) {
                    nextNode.visited = false;
                    nextNode.nodeWeight = currWeight;
                    nextNode.preNodeWeight = currNode.nodeWeight;
                    insertPQueue(nextNode, pQueue);
                }
            }

            nextVertex = nextVertex.next;
            if (nextVertex != null) {
                currWeight = previousWeight;
            }
            else {
                prevNode = currNode;
                currNode = pollPQueue(pQueue);
                if (currNode != null) {
                    nextVertex = currNode.head.next;
                    previousWeight = 0;
                    currWeight = currNode.nodeWeight;
                    currentColor = currNode.nodeColor;
                }
            }
        }
    }

    public static AdjacencyList pollPQueue(AdjacencyList[] pQueue) {
        AdjacencyList nextNode = null;
        for (int i = 0; i < pQueue.length; i++) {
            if (pQueue[i] != null && pQueue[i].visited == false) {
                nextNode = pQueue[i];
                pQueue[i].visited = true;
                resetVisitedFlagForNodeVertices(nextNode);
                break;
            }
        }
        return nextNode;
    }

    public static boolean insertPQueue(AdjacencyList node, AdjacencyList[] pQueue) {
        boolean pQueueInsSuccess = false;
        AdjacencyList temp = new AdjacencyList();
        temp = node;
        for (int i = 0; i < pQueue.length; i++) {
            if (pQueue[i] == null) {
                pQueue[i] = new AdjacencyList();
                pQueue[i] = temp;
                pQueueInsSuccess = true;
                break;
            }
        }
        return pQueueInsSuccess;
    }

    public static boolean nodeNotYetExplored(AdjacencyList node, AdjacencyList[] pQueue) {
        boolean rtn = false;
        for (int i = 0; i < pQueue.length; i++) {
            if (pQueue[i] != null && pQueue[i].visited == false && pQueue[i] == node) {
                return true;
            }
        }
        return rtn;
    }

    public static void resetVisitedFlagForNodeVertices(AdjacencyList node) {
        Vertex tempVertex;
        tempVertex = node.head;
        while (tempVertex != null) {
            tempVertex.visited = false;
            tempVertex = tempVertex.next;
        }
    }
}

class MyWalkPath {
    int redWeight;
    int greenWeight;
    int blueWeight;

    public MyWalkPath() {
        redWeight = Integer.MAX_VALUE;
        greenWeight = Integer.MAX_VALUE;
        blueWeight = Integer.MAX_VALUE;
    }
}
