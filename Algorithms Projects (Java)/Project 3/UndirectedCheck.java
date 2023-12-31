/**
 * Description - This class contains functions for undirected graph properties. treeCheck() is used to determine if
 * the undirected graph G is a tree. countTriangles() is used to count the number of distinct triangles in G.
 * vertexClusterCoeff() and graphClusterCoeff() are used to determine the clustering coefficients of a vertex and
 * graph respectively.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 9, 2023
 */

public class UndirectedCheck {
    public static boolean treeCheck(ListGraph a) {
        int n = a.localAdjacencyList.length;
        int verticesCount = 0;
        int numEdges = 0;
        boolean isTree = false;

        for (int i = 0; i < n; i++) {
            if (a.localAdjacencyList[i].edgeCount == 0 && n > 1) {
                return isTree;
            }
            numEdges = numEdges + a.localAdjacencyList[i].edgeCount;
            verticesCount++;
        }

        // The undirected graph is a tree if it has exactly n-1 edges and is connected.
        if ((verticesCount == n) && ((verticesCount-1) == numEdges/2) ) {
            isTree = true;
        }
        return isTree;
    }
    public static int countTriangles(MatrixGraph a) {
        int n = a.adjacencyMatrix.length;
        int triangleCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (i != j && j != k && k != i) {
                        if (a.adjacencyMatrix[i][j] == 1 && a.adjacencyMatrix[j][k] == 1 && a.adjacencyMatrix[k][i] == 1) {
                            triangleCount++;
                        }
                    }
                }
            }
        }

        // Each triangle is counted 6 times (permutations of x, y, z), so divide by 6 to get the distinct triangles.
        triangleCount = triangleCount / 6;
        return triangleCount;
    }

    public static double vertexClusterCoeff(ListGraph a, int u) {
        AdjacencyList[] adjacencyList = a.localAdjacencyList;
        int degree = adjacencyList[u].edgeCount;
        int edgesWithinNeighbors = 0;

        if (degree < 2) {
            return 0.0;
        }

        Vertex friend = adjacencyList[u].head.next;
        while(friend != null) {
            int friendNode = friend.data;
            Vertex neighbourOfFriend = adjacencyList[friendNode].head.next;

            while (neighbourOfFriend != null) {
                int neighbourOfFriendNode = neighbourOfFriend.data;

                Vertex friend1= friend.next;
                while (friend1 != null) {
                    if (neighbourOfFriend.data == friend1.data) {
                        edgesWithinNeighbors++;
                    }
                    friend1= friend1.next;
                }
                neighbourOfFriend = neighbourOfFriend.next;
            }
            friend = friend.next;
        }
        return (2.0 * edgesWithinNeighbors) / (degree * (degree - 1));
    }

    public static double graphClusterCoeff(ListGraph a) {
        int n = a.localAdjacencyList.length;
        double clustingCoefficientG = 0.0;
        for (int i = 0; i < n; i++) {
            clustingCoefficientG += vertexClusterCoeff(a, i);
        }
        return clustingCoefficientG/n;
    }
}
