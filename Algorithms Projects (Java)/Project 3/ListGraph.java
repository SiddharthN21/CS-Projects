import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Description - This class has a read function to read the data from the input files and store it in an adjacency
 * list. It utilized additional classes to store this data.
 *
 * @author Siddharth Nadgaundi
 *
 * @version November 9, 2023
 */
public class ListGraph {
    AdjacencyList[] localAdjacencyList = new AdjacencyList[0];


    public static ListGraph read(String filepath) throws IOException {
        ListGraph newGraph = new ListGraph();
        InputStream istr = null;
        BufferedReader br = null;
        int lineNumber = 0;
        try {
            istr = new FileInputStream(filepath);
            br = new BufferedReader(new InputStreamReader(istr));
            String line;

            // Read the first line outside the loop to declare adjacency list array.
            line = br.readLine();
            if (line != null) {
                String[] numbers = line.split("\\s+");
                for (String numberStr : numbers) {
                    try {
                        int number = Integer.parseInt(numberStr);
                        newGraph.localAdjacencyList = new AdjacencyList[number];
                        for (int i = 0; i < number; i++) {
                            newGraph.localAdjacencyList[i] = new AdjacencyList();
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number: " + numberStr + " In File:" + filepath + " At Line#:"  + lineNumber);
                    }
                }
            }

            while ((line = br.readLine()) != null) {
                newGraph.localAdjacencyList[lineNumber].insert(lineNumber);
                if (!line.trim().isEmpty()) {
                    String[] numbers = line.split("\\s+");
                    for (String numberStr : numbers) {
                        try {
                            int number = Integer.parseInt(numberStr);
                            newGraph.localAdjacencyList[lineNumber].insert(number);
                            newGraph.localAdjacencyList[number].inDegree++;
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number: " + numberStr + " In File:" + filepath + " At Line#:"  + lineNumber);
                        }
                    }
                }
                lineNumber++;
            }
        }
        catch (IOException e) {
                e.printStackTrace();
                throw new IOException("An error occurred while reading the file.");
            }
        finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (istr != null) {
                        istr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        br.close();
        istr.close();
        return newGraph;
    }
}

class Vertex {
    int data;
    Vertex next;

    public Vertex(int data) {
        this.data = data;
        this.next = null;
    }
}

    class AdjacencyList {
        Vertex head;
        Vertex current;
        int edgeCount;
        int inDegree;
        int outDegree;

        public AdjacencyList() {
            head = null;
            current = null;
            edgeCount = 0;
            inDegree = 0;
            outDegree = 0;
        }
        public void insert(int data) {
            Vertex newNode = new Vertex(data);
            if (head == null) {
                head = newNode;
                current = newNode;
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
    }
