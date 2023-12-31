/**
 * Description - This program is used to remove every kth node in the linked list. The ElmNode class and constructor
 * are used to create the number and next pointer attributes of each node in the list while the EliminateK
 * constructor is used to set the nodes initially to null. Within the function a for loop is used to create the nodes
 * and list. A while loop is used to remove the nodes by using a count variable to check if the current node is
 * equivalent to the kth node.
 *
 * @author Siddharth Nadgaundi
 *
 * @version September 21, 2023
 */

public class EliminateK {
    private ElmNode head;
    private ElmNode current;
    private ElmNode previous;
    private ElmNode remove;

    public EliminateK() {
        head = null;
        current = null;
        previous = null;
        remove = null;
    }


    public int computeSurvivor(int n,int k)
    {
        if (( n <= 0) || (k <= 0)) {
            return  -1;
        }
        int count = 1;
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                current = new ElmNode(i);
                current.next = current;
                head = current;
            }
            else {
                current = new ElmNode(i);
                previous.next = current;
                current.next = head;
            }
            previous = current;
        }

        current = head;
        while (current.next != current) {
            if (count != k) {
                previous = current;
                current = current.next;
                count++;
            }
            else if (count == k) {
                if (current == head) {
                    head = current.next;
                }
                count = 1;
                previous.next = current.next;
                remove = current;
                current = current.next;
                remove = null;
            }
        }
        ret = head.number;
        return ret;
    }

}

class ElmNode {
    public int number;
    public ElmNode next;
    public ElmNode(int num) {
        this.number = num;
        this.next = null;
    }
}