package stack;

public class StackList<T> {
    private StackNode<T> head;
    private StackNode<T> tail;
    public int counter = 0;

    public void push(T data) {
        if(head == null && tail == null) {
            startList(data);
        }
        else {
            addFront(data);
        }
        counter++;
    }

    private void startList(T data) {
        StackNode<T> newNode = new StackNode(data, null);
        head = newNode;
        tail = newNode;
    }

    private void addFront(T data) {
        StackNode newNode = new StackNode(data, head);
        head = newNode;
    }

    public T pop() {
        T returnData = head.getData();
        StackNode tempNode = head;
        head = head.getNext();
        tempNode.setNext(null);
        counter--;
        return returnData;
    }

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    public int size() {
        return counter;
    }

    public boolean isEmpty() {
        return head == null;
    }
}