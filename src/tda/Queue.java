package tda;

import tda.interfaces.IQueue;

public class Queue<T> implements IQueue<T> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        T element;
        Node next;

        Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void enqueue(T element) {
        if (element == null) throw new IllegalArgumentException();
        Node node = new Node(element);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException();
        T element = head.element;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return element;
    }

    @Override
    public T front() {
        if (isEmpty()) throw new IllegalStateException();
        return head.element;
    }

    @Override
    public boolean isEmpty() { return head == null; }

    @Override
    public int size() { return size; }
}
