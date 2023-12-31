/**
 * Description - This class consists of several functions utilized by the secure blockchain. The functions serve the
 * same purpose as fancy blockchain.
 *
 * @author Siddharth Nadgaundi
 *
 * @version October 12, 2023
 */

public class BlockChainSecure {
    public FancyBlockChain fbc;
    public Block[] btable;
    int bLength = 0;
    int hashTableSize = 0;
    Hasher hash = new Hasher();

    public BlockChainSecure(int capacity) {
        fbc = new FancyBlockChain(capacity);
        hashTableSize = getNextPrime(capacity);
        btable = new Block[hashTableSize];
        bLength = 0;
    }
    public BlockChainSecure(Block[] initialBlocks) {
        int hashValue = -1;
        int capacity = initialBlocks.length;
        hashTableSize = getNextPrime(capacity);
        btable = new Block[hashTableSize];
        bLength = capacity;
        for (int i = 0; i < capacity; i++) {
            hashValue = getHash(initialBlocks[i].data, hashTableSize);
            initialBlocks[i].index = i;
            btable[hashValue] = initialBlocks[i];
        }
        fbc = new FancyBlockChain(initialBlocks);
    }

    public int length() {
        return bLength;
    }

    public boolean addBlock(Block newBlock) {
        boolean rtn = fbc.addBlock(newBlock);
        if (rtn) {
            int hashValue;
            hashValue = getHash(newBlock.data, hashTableSize);
            btable[hashValue] = newBlock;
        }
        bLength = fbc.bLength;
        return rtn;
    }
    public Block getEarliestBlock() {
        return fbc.getEarliestBlock();
    }

    public Block getBlock(String data) {
        int hashValue = getHashForRetrieval(data, hashTableSize);
        if (hashValue != -1) {
            return btable[hashValue];
        }
        return null;
    }

    public Block removeEarliestBlock() {
        Block removedBlock = fbc.removeEarliestBlock();
        if (removedBlock != null) {
            removedBlock.index = -1;
            removedBlock.removed = true;
        }
        bLength = fbc.bLength;
        return removedBlock;
    }

    public Block removeBlock(String data) {
        Block removedBlock = null;
        int hashValue = getHashForRetrieval(data, hashTableSize);
        if (hashValue != -1) {
            removedBlock = btable[hashValue];
            int i = removedBlock.index;
            fbc.bchain[i] = fbc.bchain[fbc.bLength - 1];
            fbc.bchain[i].index = i;
            removedBlock.index = -1;
            removedBlock.removed = true;
            // Remove the reference to the last block and decrement the size
            fbc.bchain[fbc.bLength - 1] = null;
            fbc.bLength--;
            // swimUP or sinkDown by comparing replaced block with its new parent.
            if (i < fbc.bLength) {
                if (fbc.compareByTimestamp(fbc.bchain[i], fbc.bchain[(i - 1) / 2]) < 0) {
                    fbc.swimUp(i);
                } else {
                    fbc.sinkDown(i);
                }
            }
        }
        bLength = fbc.bLength;
        return removedBlock;
    }

    public void updateEarliestBlock(double nonce) {
        fbc.updateEarliestBlock(nonce);
    }

    public void updateBlock(String data, double nonce) {
        int hashValue = getHashForRetrieval(data, hashTableSize);
        if (hashValue != -1) {
            Block block = btable[hashValue];
            block.nonce = nonce;
            fbc.maxTimeStamp = fbc.maxTimeStamp + 1;
            block.timestamp = fbc.maxTimeStamp;
            int i = block.index;
            // swimUP or sinkDown by comparing updated block with its new parent.
            if (i < fbc.bLength) {
                if (fbc.compareByTimestamp(fbc.bchain[i], fbc.bchain[(i - 1) / 2]) < 0) {
                    fbc.swimUp(i);
                } else {
                    fbc.sinkDown(i);
                }
            }
        }
    }

    public int getNextPrime(int capacity) {
        int next = capacity + 1;
        while (true) {
            if (isPrimeSixKSqrt(next)) {
                return next; // Return the next prime number
            }
            next++;
        }
    }
    public boolean isPrimeSixKSqrt(int n)
    {
        if (n <= 1)
        {
            return false;
        }
        if (n <= 3)
        {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0)
        {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6)
        {
            if (n % i == 0 || n % (i + 2) == 0)
            {
                return false;
            }
        }
        return true;
    }

    public int getHash(String str, int size) {
        int hashValue = (hash.hash1(str, size)) % size;
        int k = 1;

        while (btable[hashValue] != null && k < size && (!btable[hashValue].removed)) {
            hashValue = (hash.hash1(str, size) + (k * (hash.hash2(str, size)))) % size;
            k++;
        }
        k = 0;
        while (btable[hashValue] != null && k < size && (!btable[hashValue].removed)) {
            hashValue = (hash.hash1(str, size) + k ) % size;
            k++;
        }
        if (btable[hashValue] != null && (!btable[hashValue].removed)) {
            hashValue = -1;
        }
        return hashValue;
    }

    public int getHashForRetrieval(String str, int size) {
        int k = 0;
        while (k < size) {
            int hashValue = (hash.hash1(str, size) + (k * (hash.hash2(str, size)))) % size;
            if ((btable[hashValue] != null) && (btable[hashValue].removed == false) && (btable[hashValue].data.equals(str))) {
                return hashValue;
            }
            k++;
        }
        k = 0;
        while (k < size) {
            int hashValue = (hash.hash1(str, size) + k ) % size;
            if ((btable[hashValue] != null) && (btable[hashValue].removed == false) && (btable[hashValue].data.equals(str))) {
                return hashValue;
            }
            k++;
        }
        return -1;
    }
}
