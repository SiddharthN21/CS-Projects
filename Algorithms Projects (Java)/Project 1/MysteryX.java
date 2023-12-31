/**
 * Description - This program is used to insert, delete, node, and get a node from the front or rear. Based on
 * whether the given node is present or not, the new node is added accordingly by manipulating the pointers. The Node
 * class is used to declare and initialize the attributes of each node.
 *
 * @author Siddharth Nadgaundi
 *
 * @version September 21, 2023
 */

class MysteryX {
    private Node head;
    private Node tail;
    private Node temp;
    public MysteryX() {
        head = null;
        tail = null;
    }
    public void insertFront(int value) {
        if (head == null) {
            head = new Node(value);
            head.prev = head;
            head.next = head;
            tail = head;
        }
        else {
            temp = new Node(value);
            tail.next = temp;
            temp.prev = tail;
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
    }

    public void insertRear(int value) {
        if (tail == null) {
            tail = new Node(value);
            tail.prev = tail;
            tail.next = tail;
            head = tail;
        }
        else {
            temp = new Node(value);
            head.prev = temp;
            temp.next = head;
            temp.prev = tail;
            tail.next = temp;
            tail = temp;
        }
    }
    public int deleteFront() {
        int ret = 0;
        if (head == null) {
            return -1;
        }
        else if (head == tail) {
            ret = head.number;
            head = null;
            tail = null;
            return ret;
        }
        else {
            temp = head;
            head.next.prev = tail;
            tail.next = head.next;
            head = head.next;
            ret = temp.number;
            temp = null;
            return ret;
        }
    }

    public int deleteRear() {
        int ret;
        if (tail == null) {
            return -1;
        }
        else if (head == tail) {
            ret = tail.number;
            head = null;
            tail = null;
            return ret;
        }
        else {
            ret = tail.number;
            temp = tail;
            tail.prev.next = head;
            head.prev = tail.prev;
            tail = tail.prev;
            temp = null;
            return ret;
        }
    }

    public int getFront() {
        if (head == null) {
            return -1;
        }
        else {
            return head.number;
        }
    }

    public int getRear() {
        if (tail == null) {
            return -1;
        }
        else {
            return tail.number;
        }
    }
}


class Node {
    int number;
    public Node next;
    public Node  prev;
    public Node(int item) {
        this.number = item;
        next = null;
        prev = null;
    }
}

