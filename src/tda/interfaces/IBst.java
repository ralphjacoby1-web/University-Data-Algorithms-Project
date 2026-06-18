package tda.interfaces;

public interface IBst<T extends Comparable<T>> {
    void insert(T element);
    void delete(T element);
    boolean search(T element);
    void inOrder();
    T max();
    T min();
}
