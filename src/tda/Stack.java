package tda;

import tda.interfaces.IStack;

public class Stack<T> implements IStack<T> {

    private class Node{
        T element;

    }


    @Override
    public void push(T element) {

    }

    @Override
    public T pop() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(T element) {
        return false;
    }
}
