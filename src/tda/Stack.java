package tda;

import tda.interfaces.IStack;

public class Stack<T> implements IStack<T> {

    private Node top;
    private int amountNodes;

    private class Node{
        T element;
        Node next;

        Node(T element){
            this.element = element;
            next = null;
        }
    }

    @Override
    public void push(T element) {
        if (element == null){throw new IllegalStateException();}

        Node node = new Node(element);

        node.next = top;
        top = node;
        amountNodes++;
    }

    @Override
    public T pop() {
        if (isEmpty()){throw new IllegalStateException();}

        T element = top.element;

        top = top.next;

        amountNodes--;

        return element;
    }

    @Override
    public T peek() {
        return top.element;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return amountNodes;
    }
}
