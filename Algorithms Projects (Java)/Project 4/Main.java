import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static String dataPath = "Tests";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Test> tests = Arrays.asList(
            new Test(1, "maxWeightChain", 10),
            new Test(1, "maxWeightTree",  20),
            new Test(2, "maxHeight",      30),
            new Test(3, "colorWalk",      40)
        );

        // Run only a single test
        if (args.length > 0) {
            int testNum = Integer.parseInt(args[0]);
            runTest(tests.get(testNum));

        // Run all tests and print total score
        } else {
            int[] partScores = new int[3];
            int[] partTotals = new int[3];

            for (Test test : tests) {
                runTest(test);
                partScores[test.part - 1] += test.score();
                partTotals[test.part - 1] += test.value;
            }

            // Print scores
            System.out.println();
            for (int part = 0; part < 3; part++) {
                System.out.println("Total score for part " + (part + 1) + ": " + partScores[part] + " / " + partTotals[part]);
            }
        }

        System.exit(0);
    }

    private static void runTest(Test test) throws ExecutionException, InterruptedException {
        System.out.println("Running part " + test.part + ": " + test.methodName + "...");

        try {
            test.run();
        } catch (Throwable e) {
            System.out.println("\tTest case failed! Refer to the stacktrace below:");
            e.printStackTrace(System.err);
        }

        System.out.println("\tScore: " + test.score() + " / " + test.value);
    }

    private static class Test {
        int part;
        String methodName;
        int value;

        int totalGraphs = 0;
        int passedGraphs = 0;

        Test(int part, String methodName, int value) {
            this.part = part;
            this.methodName = methodName;
            this.value = value;
        }

        void run() throws IOException {
            Path expectedPath = Paths.get(Utils.ROOT + dataPath, "expected", methodName + ".txt");
            List<String> lines = Files.readAllLines(expectedPath);

            totalGraphs = lines.size();
            passedGraphs = 0;

            for (String line : lines) {
                String[] tokens = line.strip().split(" ");
                String graphName = tokens[0];

                String inputFile = "part_" + part + "/" + graphName + ".txt";
                String inputPath = Utils.ROOT + dataPath + "/input/" + inputFile;

                try {
                    switch (methodName) {
                        case "maxWeightChain" -> {
                            Graph G = Graph.readVertexWeights(inputPath);
                            Utils.checkEquals(Integer.parseInt(tokens[1]), MaxWeight.maxWeightChain(G),
                                    inputFile + " max weight chain incorrect");
                        }
                        case "maxWeightTree" -> {
                            Graph G = Graph.readVertexWeights(inputPath);
                            Utils.checkEquals(Integer.parseInt(tokens[1]), MaxWeight.maxWeightTree(G),
                                    inputFile + " max weight tree incorrect");
                        }
                        case "maxHeight" -> {
                            Graph G = Graph.readEdgeWeights(inputPath);
                            Utils.checkEquals(Integer.parseInt(tokens[1]), MaxHeight.maxHeight(G),
                                    inputFile + " max height incorrect");
                        }
                        case "colorWalk" -> {
                            Graph G = Graph.readEdgeColors(inputPath);
                            int start = Integer.parseInt(tokens[1]);
                            Utils.checkColorWalkEquals(tokens, ColorWalk.colorWalk(G, start),
                                    inputFile + " color walk incorrect");
                        }
                    }

                    passedGraphs++;
                } catch (AssertionError e) {
                    System.out.println("\t" + e.getMessage());
                    //e.printStackTrace(System.out);
                } catch (Throwable e) {
                    e.printStackTrace(System.out);
                }
            }
        }

        private static int readGraphSize(String filepath) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            int result = Integer.parseInt(reader.readLine());
            reader.close();
            return result;
        }

        public int score() {
            return (totalGraphs > 0) ? (int) Math.floor((double)passedGraphs / totalGraphs * value) : 0;
        }
    }
}
