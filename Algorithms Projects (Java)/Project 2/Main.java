import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    // FancyBlockChain scoring
    static int fbc_score = 0;
    static int fbc_max = 0;

    // BlockChainSecure scoring
    static int bcs_score = 0;
    static int bcs_max = 0;

    static boolean verbose = false;

    // Blockchain bookkeeping
    public static class BCData {
        // Expected capacity and length of blockchain
        int capacity = 0;
        int length = 0;
        
        // Total operation count
        int totalConstructor = 0;
        int totalAddBlockOps = 0;
        int totalGetEarliestBlockOps = 0;
        int totalGetBlockOps = 0;
        int totalRemoveEarliestBlockOps = 0;
        int totalRemoveBlockOps = 0;
        int totalUpdateEarliestBlockOps = 0;
        int totalUpdateBlockOps = 0;

        // Failed operation count
        int failedConstructor = 0;
        int failedAddBlockOps = 0;
        int failedGetEarliestBlockOps = 0;
        int failedGetBlockOps = 0;
        int failedRemoveEarliestBlockOps = 0;
        int failedRemoveBlockOps = 0;
        int failedUpdateEarliestBlockOps = 0;
        int failedUpdateBlockOps = 0;

        // Failure strings
        ArrayList<String> failStrings;

        static final int fbc_ptsPerConstructor = 10;
        static final int fbc_ptsPerAddBlockOp = 6;
        static final int fbc_ptsPerGetEarliestBlockOp = 4;
        static final int fbc_ptsPerGetBlockOp = 4;
        static final int fbc_ptsPerRemoveEarliestBlockOp = 9;
        static final int fbc_ptsPerRemoveBlockOp = 9;
        static final int fbc_ptsPerUpdateEarliestBlockOp = 4;
        static final int fbc_ptsPerUpdateBlockOp = 4;

        static final int bcs_ptsPerConstructor = 14;
        static final int bcs_ptsPerAddBlockOp = 9;
        static final int bcs_ptsPerGetEarliestBlockOp = 5;
        static final int bcs_ptsPerGetBlockOp = 5;
        static final int bcs_ptsPerRemoveEarliestBlockOp = 11;
        static final int bcs_ptsPerRemoveBlockOp = 11;
        static final int bcs_ptsPerUpdateEarliestBlockOp = 4;
        static final int bcs_ptsPerUpdateBlockOp = 4;

        public BCData() {
            failStrings = new ArrayList<>();
            clear();
        }
        public void clear() {
            capacity = 0;
            length = 0;
            totalConstructor = 0;
            totalAddBlockOps = 0;
            totalGetEarliestBlockOps = 0;
            totalGetBlockOps = 0;
            totalRemoveEarliestBlockOps = 0;
            totalRemoveBlockOps = 0;
            totalUpdateEarliestBlockOps = 0;
            totalUpdateBlockOps = 0;
            failedConstructor = 0;
            failedAddBlockOps = 0;
            failedGetEarliestBlockOps = 0;
            failedGetBlockOps = 0;
            failedRemoveEarliestBlockOps = 0;
            failedRemoveBlockOps = 0;
            failedUpdateEarliestBlockOps = 0;
            failedUpdateBlockOps = 0;
            failStrings.clear();
        }
        public int totalFailedOps() {
            return failedConstructor +
                    failedAddBlockOps +
                    failedGetEarliestBlockOps +
                    failedGetBlockOps +
                    failedRemoveEarliestBlockOps +
                    failedRemoveBlockOps +
                    failedUpdateEarliestBlockOps +
                    failedUpdateBlockOps;
        }
        public int fbc_maxPts() {
            return totalConstructor * fbc_ptsPerConstructor +
                    totalAddBlockOps * fbc_ptsPerAddBlockOp +
                    totalGetEarliestBlockOps * fbc_ptsPerGetEarliestBlockOp +
                    totalGetBlockOps * fbc_ptsPerGetBlockOp +
                    totalRemoveEarliestBlockOps * fbc_ptsPerRemoveEarliestBlockOp +
                    totalRemoveBlockOps * fbc_ptsPerRemoveBlockOp +
                    totalUpdateEarliestBlockOps * fbc_ptsPerUpdateEarliestBlockOp +
                    totalUpdateBlockOps * fbc_ptsPerUpdateBlockOp;
        }
        public int bcs_maxPts() {
            return totalConstructor * bcs_ptsPerConstructor +
                    totalAddBlockOps * bcs_ptsPerAddBlockOp +
                    totalGetEarliestBlockOps * bcs_ptsPerGetEarliestBlockOp +
                    totalGetBlockOps * bcs_ptsPerGetBlockOp +
                    totalRemoveEarliestBlockOps * bcs_ptsPerRemoveEarliestBlockOp +
                    totalRemoveBlockOps * bcs_ptsPerRemoveBlockOp +
                    totalUpdateEarliestBlockOps * bcs_ptsPerUpdateEarliestBlockOp +
                    totalUpdateBlockOps * bcs_ptsPerUpdateBlockOp;
        }
    }
    public static BCData bcData;
    
    public static double eps = 1e-6;

    public static File[] extractNames(String part, String type) {
        File folder;
        File[] listOfFiles;
        folder = new File("Tests/"+part+"/input/"+type);
        listOfFiles = folder.listFiles();
        if (listOfFiles == null)
            listOfFiles = new File[0];
        return listOfFiles;
    }
    public static String nameret(File filename) {
        String[] components = filename.toString().split(Pattern.quote(File.separator));
        return components[components.length - 1];
    }

    // Heap checking
    public static void checkTokens(String[] tokens, int numArgs) throws IOException {
        if (tokens.length != numArgs)
            throw new IOException("Expected " + numArgs + " arguments, got \"" + String.join(" ", tokens) + "\"");
    }
    public static boolean checkValidHeap(Block[] bchain, int length) {
        if (bchain == null)
            return false;
        if (length > bchain.length)
            return false;

        for (int i = 0; i < length / 2; i++) {
            int li = i * 2 + 1;
            int ri = i * 2 + 2;
            Block curr = bchain[i];
            Block left = bchain[li];
            if (curr == null || left == null)
                return false;
            Block right = (ri < length) ? bchain[ri] : null;
            if (curr.timestamp > left.timestamp)
                return false;
            if (right != null && curr.timestamp > right.timestamp)
                return false;
        }
        return true;
    }
    public static boolean checkValidIndices(Block[] bchain, int length) {
        if (bchain == null)
            return false;
        if (length > bchain.length)
            return false;

        for (int i = 0; i < length; i++) {
            Block block = bchain[i];
            if (block == null || block.removed || block.index != i)
                return false;
        }
        return true;
    }
    public static boolean containsBlock(Block[] bchain, String data, int length) {
        if (bchain == null)
            return false;
        if (bchain.length < length)
            length = bchain.length;

        for (int i = 0; i < length; i++) {
            if (bchain[i] != null && bchain[i].data.equals(data))
                return true;
        }
        return false;
    }
    public static boolean failTest(String failString) {
        if (bcData == null)
            return false;
        bcData.failStrings.add(failString);
        return true;
    }

    // FancyBlockChain tests
    public static FancyBlockChain testFBCConstructor(Scanner input) throws IOException {
        boolean failed = false;

        FancyBlockChain fbc;

        // Read capacity and whether blockchain starts empty or with an array
        String[] inputTokens = input.nextLine().strip().split(" ");
        checkTokens(inputTokens, 2);
        String constructArg = inputTokens[1];
        bcData.capacity = Integer.parseInt(inputTokens[0]);
        Block[] inputBlocks = new Block[bcData.capacity];

        // Create new empty blockchain with given capacity
        if (constructArg.equals("empty")) {
            fbc = new FancyBlockChain(bcData.capacity);
            bcData.length = 0;

        // Create new FancyBlockChain with input array of Blocks
        } else if (constructArg.equals("array")) {
            for (int i = 0; i < bcData.capacity; i++) {
                // Read and construct Block
                inputTokens = input.nextLine().strip().split(" ");
                checkTokens(inputTokens, 3);
                Block newBlock = new Block();
                newBlock.data = inputTokens[0];
                newBlock.nonce = Double.parseDouble(inputTokens[1]);
                newBlock.timestamp = Integer.parseInt(inputTokens[2]);
                inputBlocks[i] = newBlock;
            }
            fbc = new FancyBlockChain(inputBlocks.clone());
            bcData.length = bcData.capacity;

        } else
            throw new IOException("Unexpected blockchain construction argument \"" + constructArg + "\"");

        // Correctness checks
        if (bcData.length == fbc.length()) {
            fbc_score += 2;
        } else failed = failTest("Constructor: array length " + fbc.length() + " does not match expected length " + bcData.length);
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 6;
            if (constructArg.equals("empty"))
                fbc_score += 2;
        } else failed = failTest("Constructor: not a valid heap after construction");
        if (constructArg.equals("array")) {
            boolean failedAdds = false;
            for (Block block : inputBlocks) {
                if (!containsBlock(fbc.bchain, block.data, bcData.length)) {
                    failedAdds = failTest("Constructor: block \"" + block.data + "\" not found in heap");
                    break;
                }
            }
            if (!failedAdds) {
                fbc_score += 2;
            } else failed = true;
        }

        // Update operation count
        bcData.totalConstructor++;
        if (failed)
            bcData.failedConstructor++;

        return fbc;
    }
    public static void testFBCAddBlock(String[] inputTokens, String[] expectedTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 4);
        Block block = new Block();
        block.data = inputTokens[1];
        block.nonce = Double.parseDouble(inputTokens[2]);
        block.timestamp = Integer.parseInt(inputTokens[3]);
        checkTokens(expectedTokens, 1);
        boolean added;
        if (expectedTokens[0].equals("false")) added = false;
        else if (expectedTokens[0].equals("true")) added = true;
        else
            throw new IOException("Unexpected addBlock result token: \"" + expectedTokens[0] + "\"");
        if (!added && bcData.length < bcData.capacity)
            throw new IOException("Expected addBlock to return false when length < capacity");

        // Execute operation
        boolean result = fbc.addBlock(block);
        if (bcData.length < bcData.capacity)
            bcData.length++;

        // Correctness checks
        if (result == added) {
            fbc_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": returned " + result + " but expected " + added);
        if (bcData.length == fbc.length()) {
            fbc_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": array length " + fbc.length() + " does not match expected length " + bcData.length);
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after adding block");
        if (containsBlock(fbc.bchain, inputTokens[1], bcData.length)) {
            if (added) {
                fbc_score += 2;
            } else failed = failTest(String.join(" ", inputTokens) + ": contains block \"" + block.data + "\" when it should not have been added");
        } else {
            if (!added) {
                fbc_score += 2;
            } else failed = failTest(String.join(" ", inputTokens) + ": does not contain block \"" + block.data + "\" after adding it");
        }

        // Update operation count
        bcData.totalAddBlockOps++;
        if (failed)
            bcData.failedAddBlockOps++;
    }
    public static void testFBCGetEarliestBlock(String[] inputTokens, String[] expectedTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 1);

        // Execute operation
        Block block = fbc.getEarliestBlock();

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                fbc_score += 4;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" when chain should be empty");
        } else {
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    fbc_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
            }
        }

        // Update operation count
        bcData.totalGetEarliestBlockOps++;
        if (failed)
            bcData.failedGetEarliestBlockOps++;
    }
    public static void testFBCGetBlock(String[] inputTokens, String[] expectedTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        Block block = fbc.getBlock(inputTokens[1]);

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                fbc_score += 4;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" but expected null");
        } else {
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    fbc_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
            }
        }

        // Update operation count
        bcData.totalGetBlockOps++;
        if (failed)
            bcData.failedGetBlockOps++;
    }
    public static void testFBCRemoveEarliestBlock(String[] inputTokens, String[] expectedTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 1);

        // Execute operation
        Block block = fbc.removeEarliestBlock();

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (bcData.length > 0)
                throw new IOException("Expected null block when removing earliest from non-empty blockchain");
            if (block == null) {
                fbc_score += 6;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" when chain should be empty");
        } else {
            if (bcData.length == 0)
                throw new IOException("Expected non-null block when removing from empty blockchain");
            bcData.length--;
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    fbc_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block " + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
            }
            if (!containsBlock(fbc.bchain, expectedTokens[0], bcData.length)) {
                fbc_score += 2;
            } else failed = failTest(String.join(" ", inputTokens) + ": heap contains block \"" + expectedTokens[0] +  "\" after it should have been removed");
        }
        if (bcData.length == fbc.length()) {
            fbc_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": array length " + fbc.length() + " does not match expected length " + bcData.length);
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after removing block");

        // Update operation count
        bcData.totalRemoveEarliestBlockOps++;
        if (failed)
            bcData.failedRemoveEarliestBlockOps++;
    }
    public static void testFBCRemoveBlock(String[] inputTokens, String[] expectedTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        Block block = fbc.removeBlock(inputTokens[1]);

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                fbc_score += 6;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" but expected null");
        } else {
            if (bcData.length == 0)
                throw new IOException("Expected non-null block when removing from empty blockchain");
            bcData.length--;
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    fbc_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    fbc_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
            }
            if (!containsBlock(fbc.bchain, expectedTokens[0], bcData.length)) {
                fbc_score += 2;
            } else failed = failTest(String.join(" ", inputTokens) + ": heap contains block \"" + expectedTokens[0] + "\" after it should have been removed");
        }
        if (bcData.length == fbc.length()) {
            fbc_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": array length " + fbc.length() + " does not match expected length " + bcData.length);
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after removing block");

        // Update operation count
        bcData.totalRemoveBlockOps++;
        if (failed)
            bcData.failedRemoveBlockOps++;
    }
    public static void testFBCUpdateEarliestBlock(String[] inputTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        fbc.updateEarliestBlock(Double.parseDouble(inputTokens[1]));

        // Correctness checks
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 4;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after updating block");

        // Update operation count
        bcData.totalUpdateEarliestBlockOps++;
        if (failed)
            bcData.failedUpdateEarliestBlockOps++;
    }
    public static void testFBCUpdateBlock(String[] inputTokens, FancyBlockChain fbc) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 3);

        // Execute operation
        fbc.updateBlock(inputTokens[1], Double.parseDouble(inputTokens[2]));

        // Correctness checks
        if (checkValidHeap(fbc.bchain, bcData.length)) {
            fbc_score += 4;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after updating block");

        // Update operation count
        bcData.totalUpdateBlockOps++;
        if (failed)
            bcData.failedUpdateBlockOps++;
    }
    public static void testFancyBlockChain(File[] filenames, String type) {
        if (bcData == null)
            bcData = new BCData();
        
        if (filenames == null || filenames.length == 0) {
            System.out.println("FancyBlockChain: couldn't find " + type + " test files");
            return;
        }

        for (File filen : filenames) {
            String fname = nameret(filen);
            try {
                Scanner input = new Scanner(filen);
                Scanner expected = new Scanner(new File("Tests/FancyBlockChain/expected/" + type + "/" + fname));

                bcData.clear();
                FancyBlockChain fbc = testFBCConstructor(input);

                // Read operations until the end of the file
                while (input.hasNextLine()) {
                    String[] tokens = input.nextLine().strip().split(" ");
                    String[] expTokens;

                    switch (tokens[0]) {
                    case "addBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testFBCAddBlock(tokens, expTokens, fbc);
                        break;
                    case "getEarliestBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testFBCGetEarliestBlock(tokens, expTokens, fbc);
                        break;
                    case "getBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testFBCGetBlock(tokens, expTokens, fbc);
                        break;
                    case "removeEarliestBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testFBCRemoveEarliestBlock(tokens, expTokens, fbc);
                        break;
                    case "removeBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testFBCRemoveBlock(tokens, expTokens, fbc);
                        break;
                    case "updateEarliestBlock":
                        testFBCUpdateEarliestBlock(tokens, fbc);
                        break;
                    case "updateBlock":
                        testFBCUpdateBlock(tokens, fbc);
                        break;
                    default:
                        throw new IOException("Unrecognized command in input file: " + tokens[0]);
                    }
                }

                // Print any failed operations
                if (bcData.totalFailedOps() > 0)
                    System.out.println("FancyBlockChain: failed test " + type + "/" + fname);
                if (bcData.failedConstructor > 0)
                    System.out.println("\tFailed FancyBlockChain construction");
                if (bcData.failedAddBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedAddBlockOps + " / " + bcData.totalAddBlockOps + " addBlock operations");
                if (bcData.failedGetEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedGetEarliestBlockOps + " / " + bcData.totalGetEarliestBlockOps + " getEarliestBlock operations");
                if (bcData.failedGetBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedGetBlockOps + " / " + bcData.totalGetBlockOps + " getBlock operations");
                if (bcData.failedRemoveEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedRemoveEarliestBlockOps + " / " + bcData.totalRemoveEarliestBlockOps + " removeEarliestBlock operations");
                if (bcData.failedRemoveBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedRemoveBlockOps + " / " + bcData.totalRemoveBlockOps + " removeBlock operations");
                if (bcData.failedUpdateEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedUpdateEarliestBlockOps + " / " + bcData.totalUpdateEarliestBlockOps + " updateEarliestBlock operations");
                if (bcData.failedUpdateBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedUpdateBlockOps + " / " + bcData.totalUpdateBlockOps + " updateBlock operations");
                if (bcData.totalFailedOps() > 0 && verbose) {
                    for (String str : bcData.failStrings)
                        System.out.println("\t" + str);
                }

                // Update maximum possible points
                fbc_max += bcData.fbc_maxPts();

                input.close();
                expected.close();

            } catch (IOException e) {
                System.err.println("FancyBlockChain: error during test " + type + "/" + fname + ":\n\t" + e.getMessage());
            } catch (NoSuchElementException e) {
                System.err.println("FancyBlockChain: error during test " + type + "/" + fname + ":\n\tUnexpected end of file");
            } catch (NumberFormatException e) {
                System.err.println("FancyBlockChain: error during test " + type + "/" + fname + ":\n\tError parsing number " + e.getMessage());
            }
        }
    }

    // BlockChainSecure tests
    public static BlockChainSecure testBCSConstructor(Scanner input, Scanner expected) throws IOException {
        boolean failed = false;

        BlockChainSecure bcs;

        // Read capacity and whether blockchain starts empty of with an array
        String[] inputTokens = input.nextLine().strip().split(" ");
        checkTokens(inputTokens, 2);
        String constructArg = inputTokens[1];
        bcData.capacity = Integer.parseInt(inputTokens[0]);
        Block[] inputBlocks = new Block[bcData.capacity];

        // Create new empty blockchain with given capacity
        if (constructArg.equals("empty")) {
            bcs = new BlockChainSecure(bcData.capacity);
            bcData.length = 0;

        // Create new BlockChainSecure with input array of Blocks
        } else if (constructArg.equals("array")) {
            for (int i = 0; i < bcData.capacity; i++) {
                // Read and construct Block
                inputTokens = input.nextLine().strip().split(" ");
                checkTokens(inputTokens, 3);
                Block newBlock = new Block();
                newBlock.data = inputTokens[0];
                newBlock.nonce = Double.parseDouble(inputTokens[1]);
                newBlock.timestamp = Integer.parseInt(inputTokens[2]);
                inputBlocks[i] = newBlock;
            }
            bcs = new BlockChainSecure(inputBlocks.clone());
            bcData.length = bcData.capacity;

        } else
            throw new IOException("Unexpected blockchain construction argument \"" + constructArg + "\"");

        // Correctness checks
        String[] expectedTokens = expected.nextLine().strip().split(" ");
        checkTokens(expectedTokens, 1);
        // Check that hash table has correct length
        if (bcs.btable != null) {
            if (bcs.btable.length == Integer.parseInt(expectedTokens[0])) {
                bcs_score += 2;
                if (constructArg.equals("empty"))
                    bcs_score += 8;
            } else failed = failTest("Constructor: hash table length " + bcs.btable.length + " does not match expected length " + expectedTokens[0]);
        } else failed = failTest("Constructor: hash table is null");
        if (constructArg.equals("array")) {
            boolean failedHash = false;
            boolean failedIndex = false;
            for (Block inputBlock : inputBlocks) {
                expectedTokens = expected.nextLine().strip().split(" ");
                checkTokens(expectedTokens, 1);
                int hashIdx = Integer.parseInt(expectedTokens[0]);
                if (bcs.btable != null) {
                    Block block = bcs.btable[hashIdx];
                    // Check that block was added to correct location in hash table
                    if (block == null || block.removed || !block.data.equals(inputBlock.data)) {
                        failedHash = failTest("Constructor: block \"" + inputBlock.data + "\" not found at expected hash index " + hashIdx);
                        failedIndex = true;
                        // Check that block can be located in heap
                    } else if (bcs.fbc == null || bcs.fbc.bchain == null || block.index < 0 || block.index >= bcData.capacity || bcs.fbc.bchain[block.index] != block) {
                        failedIndex = failTest("Constructor: block \"" + inputBlock.data + "\" not found at expected heap index " + block.index);
                    }
                } else {
                    failedHash = true;
                    failedIndex = true;
                }
            }
            if (!failedHash) {
                bcs_score += 5;
            } else failed = true;
            if (!failedIndex) {
                bcs_score += 3;
            } else failed = true;
        }
        if (bcs.fbc != null) {
            if (checkValidHeap(bcs.fbc.bchain, bcData.length)) {
                bcs_score += 2;
            } else failed = failTest("Constructor: not a valid heap after construction");
            if (checkValidIndices(bcs.fbc.bchain, bcData.length)) {
                bcs_score += 2;
            } else failed = failTest("Constructor: block indices do not match locations in heap");
        } else failed = failTest("Constructor: FancyBlockChain object is null");

        // Update operation count
        bcData.totalConstructor++;
        if (failed)
            bcData.failedConstructor++;

        return bcs;
    }
    public static void testBCSAddBlock(String[] inputTokens, String[] expectedTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 4);
        Block newBlock = new Block();
        newBlock.data = inputTokens[1];
        newBlock.nonce = Double.parseDouble(inputTokens[2]);
        newBlock.timestamp = Integer.parseInt(inputTokens[3]);
        boolean added;
        if (expectedTokens[0].equals("false")) added = false;
        else if (expectedTokens[0].equals("true")) {
            added = true;
            checkTokens(expectedTokens, 2);
        } else
            throw new IOException("Unexpected addBlock result token: \"" + expectedTokens[0] + "\"");
        if (!added && bcData.length < bcData.capacity)
            throw new IOException("Expected addBlock to return false when length < capacity");

        // Execute operation
        boolean result = bcs.addBlock(newBlock);
        if (bcData.length < bcData.capacity)
            bcData.length++;

        // Correctness checks
        if (result == added) {
            bcs_score += 1;
            if (!added)
                bcs_score += 5;
        } else failed = failTest(String.join(" ", inputTokens) + ": returned " + result + " but expected " + added);
        if (added) {
            if (bcs.btable != null) {
                int hashIdx = Integer.parseInt(expectedTokens[1]);
                if (hashIdx < bcs.btable.length) {
                    Block block = bcs.btable[hashIdx];
                    // Check that block was added to hash table
                    if (block != null && !block.removed && block.data.equals(inputTokens[1])) {
                        bcs_score += 3;
                        // Check that block can be located in heap
                        if (bcs.fbc != null && bcs.fbc.bchain != null && bcs.fbc.bchain[block.index] == block) {
                            bcs_score += 2;
                        } else failed = failTest(String.join(" ", inputTokens) + ": block \"" + inputTokens[1] + "\" not found at expected heap index " + block.index);
                    } else failed = failTest(String.join(" ", inputTokens) + ": block \"" + inputTokens[1] + "\" not found at expected hash index " + hashIdx);
                } else failed = failTest(String.join(" ", inputTokens) + ": expected hash index " + hashIdx + " out of range of hash table");
            } else failed = failTest(String.join(" ", inputTokens) + ": hash table is null");
        }
        if (bcs.fbc != null) {
            if (checkValidHeap(bcs.fbc.bchain, bcData.length)) {
                bcs_score += 1;
            } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after adding block");
            if (checkValidIndices(bcs.fbc.bchain, bcData.length)) {
                bcs_score += 2;
            } else failed = failTest(String.join(" ", inputTokens) + ": block indices do not match locations in heap");
        } else failed = failTest(String.join(" ", inputTokens) + ": FancyBlockChain object is null");

        // Update operation count
        bcData.totalAddBlockOps++;
        if (failed)
            bcData.failedAddBlockOps++;
    }
    public static void testBCSGetEarliestBlock(String[] inputTokens, String[] expectedTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 1);

        // Execute operation
        Block block = bcs.getEarliestBlock();

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                bcs_score += 5;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" when chain should be empty");
        } else {
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
                if (block.index == 0 && !block.removed) {
                    bcs_score += 1;
                } else {
                    if (block.index != 0)
                        failed = failTest(String.join(" ", inputTokens) + ": returned block index " + block.index + " but expected index 0");
                    if (block.removed)
                        failed = failTest(String.join(" ", inputTokens) + ": returned block with removed flag set to true when it should be false");
                }
            }
        }

        // Update operation count
        bcData.totalGetEarliestBlockOps++;
        if (failed)
            bcData.failedGetEarliestBlockOps++;
    }
    public static void testBCSGetBlock(String[] inputTokens, String[] expectedTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        Block block = bcs.getBlock(inputTokens[1]);

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                bcs_score += 5;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" but expected null");
        } else {
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
                if (!block.removed) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block with removed flag set to true when it should be false");
            }
        }

        // Update operation count
        bcData.totalGetBlockOps++;
        if (failed)
            bcData.failedGetBlockOps++;
    }
    public static void testBCSRemoveEarliestBlock(String[] inputTokens, String[] expectedTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 1);

        // Execute operation
        Block block = bcs.removeEarliestBlock();

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (bcData.length > 0)
                throw new IOException("Expected null block when removing earliest from non-empty blockchain");
            if (block == null) {
                bcs_score += 8;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" when chain should be empty");
        } else {
            if (bcData.length == 0)
                throw new IOException("Expected non-null block when removing from empty blockchain");
            bcData.length--;
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
                if (block.removed) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block with removed flag set to false when it should be true");
                // Check that hash table still contains the block (with removed flag == true)
                if (bcs.btable != null && containsBlock(bcs.btable, block.data, bcs.btable.length)) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": hash table does not contain removed block after removal");
            }
            // Check that heap no longer contains the block
            if (bcs.fbc != null && !containsBlock(bcs.fbc.bchain, expectedTokens[0], bcData.length)) {
                bcs_score += 1;
            } else failed = failTest(String.join(" ", inputTokens) + ": heap contains block \"" + expectedTokens[0] + "\" after it should have been removed");
        }
        if (bcData.length == bcs.length()) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": array length " + bcs.length() + " does not match expected length " + bcData.length);
        if (bcs.fbc != null && checkValidHeap(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after removing block");
        if (bcs.fbc != null && checkValidIndices(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": block indices do not match locations in heap");

        // Update operation count
        bcData.totalRemoveEarliestBlockOps++;
        if (failed)
            bcData.failedRemoveEarliestBlockOps++;
    }
    public static void testBCSRemoveBlock(String[] inputTokens, String[] expectedTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        Block block = bcs.removeBlock(inputTokens[1]);

        // Correctness checks
        if (expectedTokens[0].equals("null")) {
            if (block == null) {
                bcs_score += 8;
            } else failed = failTest(String.join(" ", inputTokens) + ": returned non-null block \"" + block.data + "\" but expected null");
        } else {
            if (bcData.length == 0)
                throw new IOException("Expected non-null block when removing from empty blockchain");
            bcData.length--;
            checkTokens(expectedTokens, 3);
            if (block == null) {
                failed = failTest(String.join(" ", inputTokens) + ": returned null but expected block \"" + expectedTokens[0] + "\"");
            } else {
                if (block.data.equals(expectedTokens[0])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block \"" + block.data + "\" but expected block \"" + expectedTokens[0] + "\"");
                if (Math.abs(block.nonce - Double.parseDouble(expectedTokens[1])) < eps) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned nonce " + block.nonce + " but expected nonce " + expectedTokens[1]);
                if (block.timestamp == Integer.parseInt(expectedTokens[2])) {
                    bcs_score += 1;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned timestamp " + block.timestamp + " but expected timestamp " + expectedTokens[2]);
                if (block.removed) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": returned block with removed flag set to false when it should be true");
                // Check that hash table still contains the block (with removed flag == true)
                if (bcs.btable != null && containsBlock(bcs.btable, block.data, bcs.btable.length)) {
                    bcs_score += 2;
                } else failed = failTest(String.join(" ", inputTokens) + ": hash table does not contain removed block after removal");
            }
            // Check that heap no longer contains the block
            if (bcs.fbc != null && !containsBlock(bcs.fbc.bchain, expectedTokens[0], bcData.length)) {
                bcs_score += 1;
            } else failed = failTest(String.join(" ", inputTokens) + ": heap contains block \"" + expectedTokens[0] + "\" after it should have been removed");
        }
        if (bcData.length == bcs.length()) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": array length " + bcs.length() + " does not match expected length " + bcData.length);
        if (bcs.fbc != null && checkValidHeap(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after removing block");
        if (bcs.fbc != null && checkValidIndices(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 1;
        } else failed = failTest(String.join(" ", inputTokens) + ": block indices do not match locations in heap");

        // Update operation count
        bcData.totalRemoveBlockOps++;
        if (failed)
            bcData.failedRemoveBlockOps++;
    }
    public static void testBCSUpdateEarliestBlock(String[] inputTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 2);

        // Execute operation
        bcs.updateEarliestBlock(Double.parseDouble(inputTokens[1]));

        // Correctness checks
        if (bcs.fbc != null && checkValidHeap(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after updating block");
        if (bcs.fbc != null && checkValidIndices(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": block indices do not match locations in heap");

        // Update operation count
        bcData.totalUpdateEarliestBlockOps++;
        if (failed)
            bcData.failedUpdateEarliestBlockOps++;
    }
    public static void testBCSUpdateBlock(String[] inputTokens, BlockChainSecure bcs) throws IOException {
        boolean failed = false;

        // Read arguments
        checkTokens(inputTokens, 3);

        // Execute operation
        bcs.updateBlock(inputTokens[1], Double.parseDouble(inputTokens[2]));

        // Correctness checks
        if (bcs.fbc != null && checkValidHeap(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": not a valid heap after updating block");
        if (bcs.fbc != null && checkValidIndices(bcs.fbc.bchain, bcData.length)) {
            bcs_score += 2;
        } else failed = failTest(String.join(" ", inputTokens) + ": block indices do not match locations in heap");

        // Update operation count
        bcData.totalUpdateBlockOps++;
        if (failed)
            bcData.failedUpdateBlockOps++;
    }
    public static void testBlockChainSecure(File[] filenames, String type) {
        if (bcData == null)
            bcData = new BCData();

        if (filenames == null || filenames.length == 0) {
            System.out.println("BlockChainSecure: couldn't find " + type + " test files");
            return;
        }

        for (File filen : filenames) {
            String fname = nameret(filen);
            try {
                Scanner input = new Scanner(filen);
                Scanner expected = new Scanner(new File("Tests/BlockChainSecure/expected/" + type + "/" + fname));

                bcData.clear();
                BlockChainSecure bcs = testBCSConstructor(input, expected);

                // Read operations until the end of the file
                while (input.hasNextLine()) {
                    String[] tokens = input.nextLine().strip().split(" ");
                    String[] expTokens;

                    switch (tokens[0]) {
                    case "addBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testBCSAddBlock(tokens, expTokens, bcs);
                        break;
                    case "getEarliestBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testBCSGetEarliestBlock(tokens, expTokens, bcs);
                        break;
                    case "getBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testBCSGetBlock(tokens, expTokens, bcs);
                        break;
                    case "removeEarliestBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testBCSRemoveEarliestBlock(tokens, expTokens, bcs);
                        break;
                    case "removeBlock":
                        expTokens = expected.nextLine().strip().split(" ");
                        testBCSRemoveBlock(tokens, expTokens, bcs);
                        break;
                    case "updateEarliestBlock":
                        testBCSUpdateEarliestBlock(tokens, bcs);
                        break;
                    case "updateBlock":
                        testBCSUpdateBlock(tokens, bcs);
                        break;
                    default:
                        throw new IOException("Unrecognized command in input file: " + tokens[0]);
                    }
                }

                // Print any failed operations
                if (bcData.totalFailedOps() > 0)
                    System.out.println("BlockChainSecure: failed test " + type + "/" + fname);
                if (bcData.failedConstructor > 0)
                    System.out.println("\tFailed BlockChainSecure construction");
                if (bcData.failedAddBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedAddBlockOps + " / " + bcData.totalAddBlockOps + " addBlock operations");
                if (bcData.failedGetEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedGetEarliestBlockOps + " / " + bcData.totalGetEarliestBlockOps + " getEarliestBlock operations");
                if (bcData.failedGetBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedGetBlockOps + " / " + bcData.totalGetBlockOps + " getBlock operations");
                if (bcData.failedRemoveEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedRemoveEarliestBlockOps + " / " + bcData.totalRemoveEarliestBlockOps + " removeEarliestBlock operations");
                if (bcData.failedRemoveBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedRemoveBlockOps + " / " + bcData.totalRemoveBlockOps + " removeBlock operations");
                if (bcData.failedUpdateEarliestBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedUpdateEarliestBlockOps + " / " + bcData.totalUpdateEarliestBlockOps + " updateEarliestBlock operations");
                if (bcData.failedUpdateBlockOps > 0)
                    System.out.println("\tFailed " + bcData.failedUpdateBlockOps + " / " + bcData.totalUpdateBlockOps + " updateBlock operations");
                if (bcData.totalFailedOps() > 0 && verbose) {
                    for (String str : bcData.failStrings)
                        System.out.println("\t" + str);
                }

                // Update maximum possible points
                bcs_max += bcData.bcs_maxPts();

                input.close();
                expected.close();

            } catch (IOException e) {
                System.err.println("BlockChainSecure: error during test " + type + "/" + fname + ":\n\t" + e.getMessage());
            } catch (NoSuchElementException e) {
                System.err.println("BlockChainSecure: error during test " + type + "/" + fname + ":\n\tUnexpected end of file");
            } catch (NumberFormatException e) {
                System.err.println("BlockChainSecure: error during test " + type + "/" + fname + ":\n\tError parsing number " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        for (String a : args) {
            if (a.equals("-v") || a.equals("--verbose"))
                verbose = true;
        }

        File[] listOfFilesPart1Small, listOfFilesPart1Medium, listOfFilesPart1Large;
        File[] listOfFilesPart2Small, listOfFilesPart2Medium, listOfFilesPart2Large;

        listOfFilesPart1Small = extractNames("FancyBlockChain","small");
        listOfFilesPart1Medium = extractNames("FancyBlockChain","medium");
        listOfFilesPart1Large = extractNames("FancyBlockChain","large");
        // Call tests
        System.out.println("Testing FancyBlockChain");
        testFancyBlockChain(listOfFilesPart1Small,"small");
        testFancyBlockChain(listOfFilesPart1Medium,"medium");
        testFancyBlockChain(listOfFilesPart1Large,"large");
        System.out.println();

        listOfFilesPart2Small = extractNames("BlockChainSecure","small");
        listOfFilesPart2Medium = extractNames("BlockChainSecure","medium");
        listOfFilesPart2Large = extractNames("BlockChainSecure","large");
        // Call tests
        System.out.println("Testing BlockChainSecure");
        testBlockChainSecure(listOfFilesPart2Small,"small");
        testBlockChainSecure(listOfFilesPart2Medium,"medium");
        testBlockChainSecure(listOfFilesPart2Large,"large");
        System.out.println();


        // Calculate scores
        int weightFBC = 40;
        int scoreFBC = (fbc_max > 0) ? (int) Math.floor((double)fbc_score / fbc_max * weightFBC) : 0;

        int weightBCS = 60;
        int scoreBCS = (bcs_max > 0) ? (int) Math.floor((double)bcs_score / bcs_max * weightBCS) : 0;

        // Print totals
        System.out.println("Total score for FancyBlockChain:  " + scoreFBC + " / " + weightFBC);
        System.out.println("Total score for BlockChainSecure: " + scoreBCS + " / " + weightBCS);
    }
}
