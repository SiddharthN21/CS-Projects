/**
 * Description - This class is used to represent the fields / attributes of the blockchain.
 *
 * @author Siddharth Nadgaundi
 *
 * @version October 12, 2023
 */

public class Block {
    String data;
    double nonce;
    int timestamp;

    // Used for Part 2
    int index = -1;
    boolean removed = false;

    public Block() {}
}
