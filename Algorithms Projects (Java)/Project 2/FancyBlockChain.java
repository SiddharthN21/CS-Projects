/**
 * Description - This class contains several functions that the blockchain utilizes. It has functions to construct
 * blockchains, return their length, adding a new block, getters, removing blocks, and updating blocks.
 *
 * @author Siddharth Nadgaundi
 *
 * @version October 12, 2023
 */

public class FancyBlockChain {
    public Block[] bchain;
    int bLength = 0;
    int maxTimeStamp = 0;

    Block oldRootBlock;

    boolean rootBlockReplacedDuringAdd = false;


    public FancyBlockChain(int capacity) {
        bchain = new Block[capacity];
        bLength = 0; //Initially no blocks
    }

    public FancyBlockChain(Block[] initialBlocks) {
        if (initialBlocks == null) {
            return;
        }
        // Calculate the capacity of the bchain array
        int capacity = initialBlocks.length;
        bLength = capacity;

        // Initialize the bchain array with the calculated capacity
        bchain = new Block[capacity];

        // Copy elements from initialBlocks to bchain
        System.arraycopy(initialBlocks, 0, bchain, 0, capacity);

        // Perform bottom-up heap construction based on timestamp
        for (int i = (capacity - 1) / 2; i >= 0; i--) {
            sinkDown(i);
        }

        //Set initial max timestamp
        for (int i = 0; i < bLength; i++) {
            if (maxTimeStamp < bchain[i].timestamp) {
                maxTimeStamp = bchain[i].timestamp;
            }
        }
    }

    public int length() {
        return bLength;
    }

    public boolean addBlock(Block newBlock) {
        // If the array is not full, simply add the newBlock at the end
        if  (bLength < bchain.length) {
            bchain[bLength] = newBlock;
            bchain[bLength].index = bLength;
            swimUp (bLength ); // Heapify upward
            bLength++;
            if (maxTimeStamp < newBlock.timestamp) {
                maxTimeStamp = newBlock.timestamp;
            }
            return true; // Block added successfully
        } else {
            // Check if the newBlock has a timestamp later than the earliest block
            if (newBlock.timestamp > bchain[0].timestamp) {
                // Replace the earliest block with the newBlock
                rootBlockReplacedDuringAdd = true;
                oldRootBlock = bchain[0];
                bchain[0] = newBlock;
                bchain[0].index = 0;

                oldRootBlock.index = -1;
                oldRootBlock.removed = true;

                sinkDown(0); // Heapify downward
                if (maxTimeStamp < newBlock.timestamp) {
                    maxTimeStamp = newBlock.timestamp;
                }
                return true; // Block added successfully
            } else {
                return false; // Block cannot be added
            }
        }
    }

    public Block getEarliestBlock() {
        if (bchain == null) {
            return null;
        }
        else {
            return bchain[0];
        }
    }

    public Block getBlock(String data) {
        for (int i = 0; i < bLength; i++) {
            if (bchain[i].data.equals(data)) {
                return bchain[i]; // Found the block with the specified data
            }
        }
        return null; // Block with the specified data not found
    }

    public Block removeEarliestBlock() {
        if (bLength == 0) {
            return null; // No blocks in the blockchain
        }
        // Store the earliest block (root)
        Block earliestBlock = bchain[0];

        if (bLength > 1) {
            // Replace the root with last block and then sink it down.
            bchain[0] = bchain[bLength - 1];
            bchain[0].index = 0;
            // Remove the reference to the last block and decrement the size
            bchain[bLength - 1] = null;
        }
        bLength--;
        sinkDown(0);

        return earliestBlock; // Return the earliest block that was removed
    }

    public Block removeBlock(String data) {
        Block removedBlock = null;
        for (int i = 0; i < bLength; i++) {
            if (bchain[i].data.equals(data)) { //Found Block
                removedBlock = bchain[i];
                // Replace the block with last Block
                bchain[i] = bchain[bLength - 1];
                // Remove the reference to the last block and decrement the size
                bchain[bLength - 1] = null;
                bLength--;
                // swimUP or sinkDown by comparing replaced block with its new parent.
                if (i < bLength) {
                    if (compareByTimestamp(bchain[i], bchain[(i - 1) / 2]) < 0) {
                        swimUp(i);
                    } else {
                        sinkDown(i);
                    }
                }
                break;
            }
        }
        return removedBlock;
    }

    public void updateEarliestBlock(double nonce) {
        if (bLength > 0) {
            bchain[0].nonce = nonce;
            maxTimeStamp = maxTimeStamp + 1;
            bchain[0].timestamp = maxTimeStamp;
            sinkDown(0);
        }
    }

    public void updateBlock(String data, double nonce) {
        for (int i = 0; i < bLength; i++) {
            if (bchain[i].data.equals(data)) { //Found Block
                bchain[i].nonce = nonce;
                maxTimeStamp = maxTimeStamp + 1;
                bchain[i].timestamp = maxTimeStamp;
                // swimUP or sinkDown by comparing updated block with its new parent.
                if (i < bLength) {
                    if (compareByTimestamp(bchain[i], bchain[(i - 1) / 2]) < 0) {
                        swimUp(i);
                    } else {
                        sinkDown(i);
                    }
                }
                break;
            }
        }
    }

    // Custom comparator for comparing Blocks based on timestamp
    public int compareByTimestamp(Block block1, Block block2) {
        return Integer.compare(block1.timestamp, block2.timestamp);
    }

    public void swimUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && compareByTimestamp(bchain[index], bchain[parentIndex]) < 0) {
            // Swap the current element with its parent
            Block temp = bchain[index];
            bchain[index] = bchain[parentIndex];
            bchain[parentIndex] = temp;
            bchain[index].index = index;
            bchain[parentIndex].index = parentIndex;

            // Move up to the parent level
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    public void sinkDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallest = index;

        if (leftChildIndex < bLength && compareByTimestamp(bchain[leftChildIndex], bchain[smallest]) < 0) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < bLength && compareByTimestamp(bchain[rightChildIndex], bchain[smallest]) < 0) {
            smallest = rightChildIndex;
        }

        if (smallest != index) {
            // Swap the current element with the smallest child
            Block temp = bchain[index];
            bchain[index] = bchain[smallest];
            bchain[index].index = index;
            bchain[smallest] = temp;
            bchain[smallest].index = smallest;

            // Recursively heapify the affected subtree
            sinkDown(smallest);
        }
    }
}
