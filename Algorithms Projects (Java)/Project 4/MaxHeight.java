/**
 * Description - This class is used to find the maxHeight() of a vehicle that can travel from any city to any city in
 * a given network. It has helper functions to do this.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 30, 2023
 */

public class MaxHeight {
    public static int maxHeight(Graph G) {
        AdjacencyList[] adjacencyList = G.localAdjacencyList;
        int numVertices = adjacencyList.length;
        boolean[] visited = new boolean[numVertices];
        MaxHeap maxHeap = new MaxHeap(G.edges);

        visited[0] = true;
        addEdgesToHeap(adjacencyList, 0, visited, maxHeap);

        int maxHeight = Integer.MAX_VALUE;

        while (!maxHeap.isEmpty()) {
            Edge edge = maxHeap.poll();
            int currentVertex = edge.dest;

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                maxHeight = Math.min(maxHeight, edge.weight);
                addEdgesToHeap(adjacencyList, currentVertex, visited, maxHeap);
            }
        }
        return maxHeight;
    }

    private static void addEdgesToHeap(AdjacencyList[] adjacencyList, int vertex, boolean[] visited, MaxHeap maxHeap) {
        Vertex current = adjacencyList[vertex].head.next;

        while (current != null) {
            if (!visited[current.data]) {
                maxHeap.offer(new Edge(vertex, current.data, current.edgeWeight));
            }
            current = current.next;
        }
    }
}

class Edge {
    int src, dest, weight;
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class MaxHeap {
    private Edge[] heap;
    private int size;

    public MaxHeap(int capacity) {
        heap = new Edge[capacity];
        size = 0;
    }

    public void offer(Edge edge) {
        heap[size] = edge;
        heapifyUp(size);
        size++;
    }

    public Edge poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        Edge root = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);

        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && heap[index].weight > heap[parent].weight) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int largest = index;

        if (leftChild < size && heap[leftChild].weight > heap[largest].weight) {
            largest = leftChild;
        }

        if (rightChild < size && heap[rightChild].weight > heap[largest].weight) {
            largest = rightChild;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {
        Edge temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
