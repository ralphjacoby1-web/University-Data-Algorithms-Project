package tda;

import tda.interfaces.IGenericTree;

public class GenericTree<T> implements IGenericTree<T> {

    private Node root;

    private class Node {
        T value;
        Node firstChild;
        Node nextSibling;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void addRoot(T element) {
        if (root == null) root = new Node(element);
    }

    @Override
    public void addChild(T parent, T child) {
        Node parentNode = findNode(root, parent);
        if (parentNode == null) return;
        Node childNode = new Node(child);
        if (parentNode.firstChild == null) {
            parentNode.firstChild = childNode;
        } else {
            Node sibling = parentNode.firstChild;
            while (sibling.nextSibling != null) sibling = sibling.nextSibling;
            sibling.nextSibling = childNode;
        }
    }

    private Node findNode(Node node, T value) {
        if (node == null) return null;
        if (node.value.equals(value)) return node;
        Node found = findNode(node.firstChild, value);
        if (found != null) return found;
        return findNode(node.nextSibling, value);
    }

    @Override
    public void dfs() {
        dfsRec(root, 0);
    }

    private void dfsRec(Node node, int depth) {
        if (node == null) return;
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println(node.value);
        Node child = node.firstChild;
        while (child != null) {
            dfsRec(child, depth + 1);
            child = child.nextSibling;
        }
    }

    @Override
    public void bfs() {
        if (root == null) return;
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            System.out.print(current.value + "  ");
            Node child = current.firstChild;
            while (child != null) {
                queue.enqueue(child);
                child = child.nextSibling;
            }
        }
        System.out.println();
    }
}
