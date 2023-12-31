/**
 * Description - This class is used to find the max weight chain and tree in a graph G. It has helper functions to do
 * this.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 30, 2023
 */

public class MaxWeight {
    public static int maxWeightChain(Graph G) {
        int maxWeight = Integer.MIN_VALUE;
        AdjacencyList node = null;

        // Get leaf node
        for (int i = 0; i < G.localAdjacencyList.length; i++) {
            node = G.localAdjacencyList[i];
            if ((node.inDegree == 1 || node.inDegree == 0) && node.outDegree == 1 && node.edgeCount == 1) {
                break;
            }
            else {
                node = null;
            }
        }

        // Handle edge case scenario. Node without edges.
        if (node == null) {
            if (G.edges == 0 && G.vertices > 0) {
                for (int i = 0; i < G.localAdjacencyList.length; i++) {
                    maxWeight = Math.max(maxWeight, G.localAdjacencyList[i].head.weight);
                }
            }
        }
        else {
            maxWeight = chainRecursion(G, node, -1, -1, -1);
        }

        return maxWeight;
    }

    public static int maxWeightTree(Graph G) {
        int maxWeight;
        AdjacencyList node = null;

        // Get non-leaf node
        for (int i = 0; i < G.localAdjacencyList.length; i++) {
            node = G.localAdjacencyList[i];
            if (node.inDegree > 1 || node.outDegree > 1 || node.edgeCount > 1) {
                break;
            }
            else {
                node = null;
            }
        }

        // Initialize the 4-tuple for the starting node
        int[] startNodeTuple = {0, 0, 0, 0};

        //Handle edge case scenarios. Only nodes without edges. Only 2 node chain and no tree.
        if (node == null) {
            if (G.vertices == 2 && (G.edges == 1 || G.edges == 2)) {
                for (int i = 0; i < G.localAdjacencyList.length; i++) {
                    startNodeTuple[0] = Math.max(startNodeTuple[0], G.localAdjacencyList[i].head.weight);
                }
            }
            else if (G.edges == 0) {
                for (int i = 0; i < G.localAdjacencyList.length; i++) {
                    startNodeTuple[0] = Math.max(startNodeTuple[0], G.localAdjacencyList[i].head.weight);
                }
            }
            else {
                startNodeTuple[0] = 0;
            }
        }

        else {
            // Start the recursive tree exploration from the given node
            recursiveTreeExploration(G, node.head.data, -1, startNodeTuple);
        }

        // The result is the maximum value in the L2 entry of the starting node tuple
        return startNodeTuple[0];
    }

    public static int chainRecursion(Graph tempG, AdjacencyList node, int inNode, int out1Node, int out2Node) {
        int maxOut1Out2;
        int maxWeight = 0;
        AdjacencyList tempNode1, tempNode2;
        node.visited = true;

        //if this is first call set values for leaf node.
        if (inNode == -1 && out1Node == -1 && out2Node == -1) {
            inNode = node.head.weight;
            out1Node = 0;
            out2Node = 0;
        }
        else {
            if (inNode == -1) {
                System.out.println("Error in Graph.java while setting weights for node.\n");
                return -1;
            }
            if (out1Node > out2Node) {
                maxOut1Out2 = out1Node;
            }
            else {
                maxOut1Out2 = out2Node;
            }
            out2Node = out1Node;
            out1Node = inNode;
            inNode = (node.head.weight) + maxOut1Out2;
        }
        if (node.head.next != null) {
            tempNode1 = tempG.localAdjacencyList[node.head.next.data];
            if (tempNode1.visited == false) {
                node = tempNode1;
            }
            else if (node.head.next.next != null) {
                tempNode2 = tempG.localAdjacencyList[node.head.next.next.data];
                if (tempNode2.visited == false) {
                    node = tempNode2;
                }
                else {
                    node = null;
                }
            }
            else {
                node = null;
            }
            if (node != null) {
                maxWeight = chainRecursion(tempG, node, inNode, out1Node, out2Node);
            }
        }
        if (inNode > maxWeight) {
            maxWeight = inNode;
        }
        return maxWeight;
    }

    private static int findMax(int a, int b, int c, int d, int e) {
        int max = -1;
        max = a;
        if (b > max) {
            max = b;
        }
        if (c > max) {
            max = c;
        }
        if (d > max) {
            max = d;
        }
        if (e > max) {
            max = e;
        }
        return max;
    }

    private static void recursiveTreeExploration(Graph graph, int currentNode, int parentNode, int[] tuple) {
        // Mark the current node as visited
        graph.localAdjacencyList[currentNode].visited = true;

        // Base case: If the node is a leaf
        if (graph.localAdjacencyList[currentNode].outDegree == 1 && graph.localAdjacencyList[currentNode].inDegree == 1
                && graph.localAdjacencyList[currentNode].edgeCount == 1) {
            tuple[0] = 0; // L2
            tuple[1] = graph.localAdjacencyList[currentNode].head.weight; // L1.in
            tuple[2] = 0; // L1.out1
            tuple[3] = 0; // L1.out2
            // Unmark the node before returning
            graph.localAdjacencyList[currentNode].visited = false;
            return;
        }

        // Initialize the tuple for the current node
        int[] currentTuple = {0, 0, 0, 0};

        // Iterate through the neighbors of the current node
        Vertex currentVertex = graph.localAdjacencyList[currentNode].head.next;
        while (currentVertex != null) {
            int neighbor = currentVertex.data;
            if (!graph.localAdjacencyList[neighbor].visited) {
                // Recursive call on the neighbor
                int[] neighborTuple = new int[4];
                recursiveTreeExploration(graph, neighbor, currentNode, neighborTuple);
                // Update the current tuple based on the neighbor's tuple
                int a, b, c, d, e;
                a = currentTuple[0];
                b = neighborTuple[0];
                c = currentTuple[1] + Math.max(neighborTuple[2], neighborTuple[3]);
                d = currentTuple[2] + Math.max(neighborTuple[1],neighborTuple[2]);
                e = currentTuple[3] + neighborTuple[1];
                // Update L2(u)
                currentTuple[0] = findMax(a, b, c, d, e);
                a = currentTuple[1];
                b = graph.localAdjacencyList[currentNode].head.weight + Math.max(neighborTuple[2], neighborTuple[3]);
                // Update L1.in(u)
                currentTuple[1] = Math.max(a, b);
                // Update L1.out1(u)
                currentTuple[2] = Math.max(currentTuple[2], neighborTuple[1]);
                // Update L1.out2(u)
                currentTuple[3] = Math.max(currentTuple[3], neighborTuple[2]);
            }
            // Move to the next neighbor
            currentVertex = currentVertex.next;
        }
        // Update the parent's tuple based on the current node's tuple
        System.arraycopy(currentTuple, 0, tuple, 0, 4);
        // Unmark the node before returning
        graph.localAdjacencyList[currentNode].visited = false;
    }
}
