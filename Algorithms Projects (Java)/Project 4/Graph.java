import java.io.IOException;
import java.io.File;
import java.util.Scanner;

/**
 * Description - This class is used to read the corresponding data from the input files and place them in their
 * respective datastructures. It has helper functions for this.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 30, 2023
 */
public class Graph {

    AdjacencyList[] localAdjacencyList = new AdjacencyList[0];

    AdjacencyList[] localAdjRed = new AdjacencyList[0];
    AdjacencyList[] localAdjGreen = new AdjacencyList[0];
    AdjacencyList[] localAdjBlue = new AdjacencyList[0];

    int edges = 0;
    int vertices = 0;

    public static Graph readVertexWeights(String filename) throws IOException {
        Graph newGraph = new Graph();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));

            if (scanner.hasNext()) {
                int number = scanner.nextInt();
                newGraph.localAdjacencyList = new AdjacencyList[number];
                newGraph.vertices = number;
                for (int i = 0; i < number; i++) {
                    newGraph.localAdjacencyList[i] = new AdjacencyList();
                    newGraph.localAdjacencyList[i].insert(i);
                }
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
                String line2 = scanner.nextLine();
                Scanner lineScanner = new Scanner(line2);

                int vertexNo = 0;
                while (lineScanner.hasNextInt()) {
                    int number = lineScanner.nextInt();
                    newGraph.localAdjacencyList[vertexNo].head.weight = number;
                    vertexNo++;
                }

                lineScanner.close();
            }

            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    Scanner lineScanner = new Scanner(line);

                    while (lineScanner.hasNextInt()) {
                        int number = lineScanner.nextInt();
                        newGraph.localAdjacencyList[lineNumber].insert(number);
                        newGraph.localAdjacencyList[number].inDegree++;
                        newGraph.edges++;
                    }

                    lineScanner.close();
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("An error occurred while reading the file.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return newGraph;
    }


    public static Graph readEdgeWeights(String filename) throws IOException {
        Graph newGraph = new Graph();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));

            if (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().split("\\s+");
                String numberStr = "";

                try {
                    numberStr = numbers[0];
                    newGraph.vertices = Integer.parseInt(numberStr);
                    newGraph.localAdjacencyList = new AdjacencyList[newGraph.vertices];
                    for (int i = 0; i < newGraph.vertices; i++) {
                        newGraph.localAdjacencyList[i] = new AdjacencyList();
                        newGraph.localAdjacencyList[i].insert(i);
                    }

                    numberStr = numbers[1];
                    newGraph.edges = Integer.parseInt(numberStr);

                } catch (NumberFormatException e) {
                    System.err.println("Invalid number: " + numberStr + " In File:" + filename + " At Line#:" + 0);
                }
            }

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] numbers = line.split("\\s+");
                    String numberStr = "";

                    try {
                        numberStr = numbers[0];
                        int srcVertex = Integer.parseInt(numberStr);
                        numberStr = numbers[1];
                        int destVertex = Integer.parseInt(numberStr);
                        numberStr = numbers[2];
                        int edgeWeight = Integer.parseInt(numberStr);

                        newGraph.localAdjacencyList[srcVertex].insert(destVertex);
                        newGraph.localAdjacencyList[srcVertex].current.edgeWeight = edgeWeight;

                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number: " + numberStr + " In File:" + filename + " At Line#:" + lineNumber);
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("An error occurred while reading the file.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return newGraph;
    }

    public static Graph readEdgeColors(String filename) throws IOException {
        Graph newGraph = new Graph();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));

            if (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().split("\\s+");
                String numberStr = "";

                try {
                    numberStr = numbers[0];
                    newGraph.vertices = Integer.parseInt(numberStr);
                    newGraph.localAdjacencyList = new AdjacencyList[newGraph.vertices];
                    newGraph.localAdjRed = new AdjacencyList[newGraph.vertices];
                    newGraph.localAdjGreen = new AdjacencyList[newGraph.vertices];
                    newGraph.localAdjBlue = new AdjacencyList[newGraph.vertices];

                    for (int i = 0; i < newGraph.vertices; i++) {
                        newGraph.localAdjacencyList[i] = new AdjacencyList();
                        newGraph.localAdjacencyList[i].insert(i);

                        newGraph.localAdjRed[i] = new AdjacencyList();
                        newGraph.localAdjRed[i].nodeColor = 'R';
                        newGraph.localAdjRed[i].insert(i);
                        newGraph.localAdjGreen[i] = new AdjacencyList();
                        newGraph.localAdjGreen[i].nodeColor = 'G';
                        newGraph.localAdjGreen[i].insert(i);
                        newGraph.localAdjBlue[i] = new AdjacencyList();
                        newGraph.localAdjBlue[i].nodeColor = 'B';
                        newGraph.localAdjBlue[i].insert(i);
                    }

                    numberStr = numbers[1];
                    newGraph.edges = Integer.parseInt(numberStr);

                } catch (NumberFormatException e) {
                    System.err.println("Invalid number: " + numberStr + " In File:" + filename + " At Line#:" + 0);
                }
            }

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] numbers = line.split("\\s+");
                    String numberStr = "";

                    try {
                        int srcVertex = Integer.parseInt(numbers[0]);
                        int destVertex = Integer.parseInt(numbers[1]);
                        int edgeWeight = Integer.parseInt(numbers[2]);
                        char edgeColor = numbers[3].charAt(0);

                        newGraph.localAdjacencyList[srcVertex].insert(destVertex);
                        newGraph.localAdjacencyList[srcVertex].current.edgeWeight = edgeWeight;
                        newGraph.localAdjacencyList[srcVertex].current.edgeColor = edgeColor;

                        Vertex tempVertex;

                        switch (edgeColor) {
                            case 'R':
                                tempVertex = newGraph.localAdjRed[srcVertex].checkIfEdgeExists(destVertex);
                                updateEdgeWeight(tempVertex, newGraph.localAdjRed[srcVertex], destVertex, edgeWeight);
                                break;
                            case 'G':
                                tempVertex = newGraph.localAdjGreen[srcVertex].checkIfEdgeExists(destVertex);
                                updateEdgeWeight(tempVertex, newGraph.localAdjGreen[srcVertex], destVertex, edgeWeight);
                                break;
                            case 'B':
                                tempVertex = newGraph.localAdjBlue[srcVertex].checkIfEdgeExists(destVertex);
                                updateEdgeWeight(tempVertex, newGraph.localAdjBlue[srcVertex], destVertex, edgeWeight);
                                break;
                            default:
                                System.out.println("Incorrect color in file at Line#:" + lineNumber);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number: " + numberStr + " In File:" + filename + " At Line#:" + lineNumber);
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("An error occurred while reading the file." + filename);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        newGraph.sortAdjLists();
        return newGraph;
    }

    private static void updateEdgeWeight(Vertex tempVertex, AdjacencyList adjacencyList, int destVertex, int edgeWeight) {
        if (tempVertex == null) {
            adjacencyList.insert(destVertex);
            adjacencyList.current.edgeWeight = edgeWeight;
        } else {
            tempVertex.edgeWeight = Math.min(tempVertex.edgeWeight, edgeWeight);
        }
    }


    public void sortAdjLists() {
        for (int i=0; i < localAdjRed.length; i++) {
            localAdjRed[i].sortAdjecencyList();
            localAdjGreen[i].sortAdjecencyList();
            localAdjBlue[i].sortAdjecencyList();
        }
    }
    public void resetVisitedFlag() {
        Vertex tempVertex;
        for (int i=0; i < localAdjRed.length; i++) {
            localAdjRed[i].visited = false;
            localAdjRed[i].nodeWeight = -1;
            localAdjRed[i].preNodeWeight = -1;
            tempVertex = localAdjRed[i].head;
                while (tempVertex != null) {
                    tempVertex.visited = false;
                    tempVertex = tempVertex.next;
                }
        }
        for (int i=0; i < localAdjGreen.length; i++) {
            localAdjGreen[i].visited = false;
            localAdjGreen[i].nodeWeight = -1;
            localAdjGreen[i].preNodeWeight = -1;
            tempVertex = localAdjGreen[i].head;
            while (tempVertex != null) {
                tempVertex.visited = false;
                tempVertex = tempVertex.next;
            }
        }
        for (int i=0; i < localAdjBlue.length; i++) {
            localAdjBlue[i].visited = false;
            localAdjBlue[i].nodeWeight = -1;
            localAdjBlue[i].preNodeWeight = -1;
            tempVertex = localAdjBlue[i].head;
            while (tempVertex != null) {
                tempVertex.visited = false;
                tempVertex = tempVertex.next;
            }
        }
    }
}

class Vertex {
    int data;
    Vertex next;
    int weight;
    int edgeWeight;
    char edgeColor;

    boolean visited;


    public Vertex(int data) {
        this.data = data;
        this.next = null;
        this.weight = -1;
        this.edgeWeight = -1;
        this.edgeColor = '\0';
        this.visited = false;
    }
}
class AdjacencyList {
    Vertex head;
    Vertex current;
    int edgeCount;
    int inDegree;
    int outDegree;
    boolean visited;
    int nodeWeight;
    int preNodeWeight;
    char nodeColor;

    public AdjacencyList() {
        head = null;
        current = null;
        edgeCount = 0;
        inDegree = 0;
        outDegree = 0;
        visited = false;
        nodeWeight = -1;
        preNodeWeight = -1;
        nodeColor = '\0';
    }
    public void insert(int data) {
        Vertex newNode = new Vertex(data);
        if (head == null) {
            head = newNode;
            current = newNode;
            newNode.edgeWeight = -1;
            newNode.edgeColor = '\0';
        } else if (head.next == null) {
            head.next = newNode;
            current = newNode;
            edgeCount++;
            outDegree++;
        } else {
            current.next = newNode;
            current = newNode;
            edgeCount++;
            outDegree++;
        }
    }
    public Vertex checkIfEdgeExists(int destIndex) {
        Vertex tempVertex = head.next;
        Vertex rtnVertex = null;
        while (tempVertex != null) {
            if (tempVertex.data == destIndex) {
                rtnVertex = tempVertex;
                break;
            }
            tempVertex = tempVertex.next;
        }
        return rtnVertex;
    }

    public void sortAdjecencyList() {
        if (head != null && head.next != null && head.next.next != null) {
            Vertex prev = head;
            Vertex curr = head.next;
            Vertex next = curr.next ;

            while (curr != null && next != null) {
                if (curr.edgeWeight > next.edgeWeight) {
                    prev.next = next;
                    curr.next = next.next;
                    next.next = curr;
                    prev = prev.next;
                    next = curr.next;
                }
                else {
                    prev = prev.next;
                    curr = curr.next;
                    next = next.next;
                }
            }
        }
    }
}

