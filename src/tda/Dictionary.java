package tda;

import tda.interfaces.IDictionary;

public class Dictionary<K, V> implements IDictionary<K, V> {

    private static final int CAPACITY = 16;
    private Object[] buckets;
    private int size;

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public Dictionary() {
        buckets = new Object[CAPACITY];
    }

    private int index(K key) {
        return Math.abs(key.hashCode() % CAPACITY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        int i = index(key);
        Entry<K, V> entry = (Entry<K, V>) buckets[i];
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = (Entry<K, V>) buckets[i];
        buckets[i] = newEntry;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int i = index(key);
        Entry<K, V> entry = (Entry<K, V>) buckets[i];
        while (entry != null) {
            if (entry.key.equals(key)) return entry.value;
            entry = entry.next;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void remove(K key) {
        int i = index(key);
        Entry<K, V> entry = (Entry<K, V>) buckets[i];
        Entry<K, V> prev = null;
        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) buckets[i] = entry.next;
                else prev.next = entry.next;
                size--;
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }
}
