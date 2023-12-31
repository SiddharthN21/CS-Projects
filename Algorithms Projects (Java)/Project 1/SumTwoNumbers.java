/**
 * Description - This program is used to sum two numbers, represented in doubly linked lists, and represent it in a
 * singly linked list. The Node class and constructor are used to declare the key attribute & initialize the previous
 * and next pointers to null. The first two for loops are used to create the lists for num1 and num2 using the
 * length() function to get the length of the numbers passed as strings. Next, a while loop is used to add the two
 * numbers from the tail of the list. Within each iteration of the loop, the curr nodes are set to 0 or their current
 * value depending on whether they are null or not. Lastly, a node with the carry forward is added if it is equal to 1.
 *
 * @author Siddharth Nadgaundi
 *
 * @version September 21, 2023
 */

public class SumTwoNumbers {
    public class Node {
        int key;
        Node previous,next;
        public Node() {
            this.next = null;
            this.previous = null;
        }
    }

    public Node num1_list;
    public Node num2_list;

    public Node add(String num1, String num2) {
        if ((num1 == null) || (num2 == null)) {
            return  null;
        }
        int num1_length = num1.length();
        int num2_length = num2.length();
        Node num1_head = null;
        Node num2_head = null;
        Node num1_tail = null;
        Node num2_tail = null;
        Node temp = null;
        Node curr1, curr2;
        Node prev = null;
        Node sum_list = null;
        Node next = null;

        for(int i = 0; i < num1_length; i++) {
            temp = new Node();
            temp.key = Integer.parseInt(num1.substring(i, i+1));
            temp.next = null;
            if (i == 0) {
                temp.previous = null;
                num1_head = temp;
            }
            else {
                temp.previous = prev;
                prev.next = temp;
            }
            prev = temp;
        }
        num1_list = num1_head;
        num1_tail = temp;

        for(int i = 0; i < num2_length; i++) {
            temp = new Node();
            temp.key = Integer.parseInt(num2.substring(i, i+1));
            temp.next = null;
            if (i == 0) {
                temp.previous = null;
                num2_head = temp;
            }
            else {
                temp.previous = prev;
                prev.next = temp;
            }
            prev = temp;
        }
        num2_list = num2_head;
        num2_tail = temp;

        int carry_forward = 0;
        int digit_sum = 0;
        int num1_key, num2_key;
        curr1 = num1_tail;
        curr2 = num2_tail;

        while (curr1 != null || curr2 != null)
        {
            if (curr1 == null)
            {
                num1_key = 0;
            }
            else
            {
                num1_key = curr1.key;
            }
            if (curr2 == null)
            {
                num2_key = 0;
            }
            else
            {
                num2_key = curr2.key;
            }
            digit_sum = num1_key + num2_key + carry_forward;
            carry_forward = digit_sum/10;
            if (carry_forward == 1) {
                digit_sum = digit_sum - 10;
            }
            temp = new Node();
            temp.key = digit_sum;
            if (sum_list == null)
            {
                temp.previous = null;
                temp.next = null;
                sum_list = temp;
            }
            else {
                temp.next = next;
                temp.previous = null;
                sum_list = temp;
            }
            next = temp;
            if (curr1 != null) {
                curr1 = curr1.previous;
            }
            if (curr2 != null) {
                curr2 = curr2.previous;
            }
        }

        if (carry_forward == 1)
        {
            temp = new Node();
            temp.key = carry_forward;
            temp.next = sum_list;
            temp.previous = null;
            sum_list = temp;
        }
        return sum_list;
    }
}
