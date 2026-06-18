package tda.interfaces;

public interface IAvl<T> {
    void insert(T element);
    void delete(T element);
    boolean search(T element);
    void inOrder();
    T max();
    T min();
}
