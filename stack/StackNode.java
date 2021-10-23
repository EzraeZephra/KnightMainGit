package stack;

public class StackNode<T> {

    T data;
    private StackNode next;

    public StackNode(T data) {
        this.data = data;
    }

    public StackNode(T data, StackNode next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public StackNode getNext() {
        return next;
    }

    public void setNext(StackNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "StackNode{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}