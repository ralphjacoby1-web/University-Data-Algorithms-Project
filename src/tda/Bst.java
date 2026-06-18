package tda;

import tda.interfaces.IBst;

public class Bst<T extends Comparable<T>> implements IBst<T> {

    private Node root;

    private class Node {
        T element;
        Node left, right;

        Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void insert(T element) {
        if (element == null) throw new IllegalArgumentException();
        root = insertRec(root, element);
    }

    private Node insertRec(Node node, T element) {
        if (node == null) return new Node(element);
        int cmp = element.compareTo(node.element);
        if (cmp < 0) node.left = insertRec(node.left, element);
        else if (cmp > 0) node.right = insertRec(node.right, element);
        return node;
    }

    @Override
    public void delete(T element) {
        root = deleteRec(root, element);
    }

    private Node deleteRec(Node node, T element) {
        if (node == null) return null;
        int cmp = element.compareTo(node.element);
        if (cmp < 0) node.left = deleteRec(node.left, element);
        else if (cmp > 0) node.right = deleteRec(node.right, element);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = minNode(node.right);
            node.element = successor.element;
            node.right = deleteRec(node.right, successor.element);
        }
        return node;
    }

    @Override
    public boolean search(T element) {
        return searchRec(root, element);
    }

    private boolean searchRec(Node node, T element) {
        if (node == null) return false;
        int cmp = element.compareTo(node.element);
        if (cmp == 0) return true;
        return cmp < 0 ? searchRec(node.left, element) : searchRec(node.right, element);
    }

    @Override
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.print(node.element + "  ");
        inOrderRec(node.right);
    }

    @Override
    public T max() {
        if (root == null) return null;
        Node n = root;
        while (n.right != null) n = n.right;
        return n.element;
    }

    @Override
    public T min() {
        if (root == null) return null;
        return minNode(root).element;
    }

    private Node minNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
