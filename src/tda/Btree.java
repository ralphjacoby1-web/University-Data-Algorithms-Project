package tda;

import tda.interfaces.IBtree;

public class Btree implements IBtree {

    // minimum degree: each node holds T-1 to 2T-1 keys
    private static final int T = 2;

    private BNode root;

    private class BNode {
        int[] keys;
        BNode[] children;
        int keyCount;
        boolean isLeaf;

        BNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new int[2 * T - 1];
            this.children = new BNode[2 * T];
            this.keyCount = 0;
        }
    }

    public Btree() {
        root = new BNode(true);
    }

    @Override
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(BNode node, int key) {
        int i = 0;
        while (i < node.keyCount && key > node.keys[i]) i++;
        if (i < node.keyCount && key == node.keys[i]) return true;
        if (node.isLeaf) return false;
        return searchRec(node.children[i], key);
    }

    @Override
    public void insert(int key) {
        BNode r = root;
        if (r.keyCount == 2 * T - 1) {
            BNode s = new BNode(false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BNode node, int key) {
        int i = node.keyCount - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.keyCount++;
        } else {
            while (i >= 0 && key < node.keys[i]) i--;
            i++;
            if (node.children[i].keyCount == 2 * T - 1) {
                splitChild(node, i, node.children[i]);
                if (key > node.keys[i]) i++;
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(BNode parent, int i, BNode child) {
        BNode newNode = new BNode(child.isLeaf);
        newNode.keyCount = T - 1;
        for (int j = 0; j < T - 1; j++)
            newNode.keys[j] = child.keys[j + T];
        if (!child.isLeaf) {
            for (int j = 0; j < T; j++)
                newNode.children[j] = child.children[j + T];
        }
        child.keyCount = T - 1;
        for (int j = parent.keyCount; j >= i + 1; j--)
            parent.children[j + 1] = parent.children[j];
        parent.children[i + 1] = newNode;
        for (int j = parent.keyCount - 1; j >= i; j--)
            parent.keys[j + 1] = parent.keys[j];
        parent.keys[i] = child.keys[T - 1];
        parent.keyCount++;
    }

    @Override
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(BNode node) {
        if (node == null) return;
        int i;
        for (i = 0; i < node.keyCount; i++) {
            if (!node.isLeaf) inOrderRec(node.children[i]);
            System.out.print(node.keys[i] + "  ");
        }
        if (!node.isLeaf) inOrderRec(node.children[i]);
    }
}
