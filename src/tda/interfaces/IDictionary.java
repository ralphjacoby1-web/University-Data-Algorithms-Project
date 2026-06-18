package tda.interfaces;

public interface IDictionary<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    boolean containsKey(K key);
    boolean isEmpty();
    int size();
}
