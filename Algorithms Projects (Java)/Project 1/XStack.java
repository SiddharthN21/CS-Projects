/**
 * Description - This program is used to execute the stack functions push, pop, and peek functions by calling the
 * functions from MysteryX. An object of MysteryX is declared to do this.
 *
 * @author Siddharth Nadgaundi
 *
 * @version September 21, 2023
 */

public class XStack {
    public MysteryX mystack;
    public XStack() {
        mystack  = new MysteryX();
    }

    public void push(int value)
    {
        this.mystack.insertFront(value);
    }
    public int pop()
    {
        return (this.mystack.deleteFront());
    }
    public int peek()
    {
        return (this.mystack.getFront());
    }
}
