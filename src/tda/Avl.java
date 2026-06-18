package tda;

import tda.interfaces.IAvl;

public class Avl<T> implements IAvl<T> {

    private Node root;

    private class Node{
        Node left;
        Node right;
        T element;

        Node(T element){
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }

    @Override
    public void insert(T element) {
        if (element == null) {throw new IllegalArgumentException();}
    }

    private Node insertRecursive(){
        return null;
    }

    @Override
    public void delete(T element) {

    }

    @Override
    public boolean search(T element) {
        return false;
    }

    @Override
    public void inOrder() {

    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public T min() {
        return null;
    }

}
