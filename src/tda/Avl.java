package tda;

import tda.interfaces.IAvl;

public class Avl<T extends Comparable<T>> implements IAvl<T> {

    private Node root;

    private class Node {
        T element;
        Node left, right;
        int height;

        Node(T element) {
            this.element = element;
            this.height = 1;
        }
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t = x.right;
        x.right = y;
        y.left = t;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node t = y.left;
        y.left = x;
        x.right = t;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node rebalance(Node node) {
        updateHeight(node);
        int bf = balanceFactor(node);
        if (bf > 1) {
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (bf < -1) {
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
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
        else return node;
        return rebalance(node);
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
        return rebalance(node);
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

    public int getHeight() {
        return height(root);
    }

    public int getBalanceFactor(T element) {
        Node node = findNode(root, element);
        return node == null ? 0 : balanceFactor(node);
    }

    private Node findNode(Node node, T element) {
        if (node == null) return null;
        int cmp = element.compareTo(node.element);
        if (cmp == 0) return node;
        return cmp < 0 ? findNode(node.left, element) : findNode(node.right, element);
    }
}
