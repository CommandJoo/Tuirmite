package de.curses.util;

public class Pair<T, V> {

    private T value1;
    private V value2;

    public Pair(T value1, V value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T value1() {
        return value1;
    }

    public V value2() {
        return value2;
    }

    public void setValue1(T value1) {
        this.value1 = value1;
    }

    public void setValue2(V value2) {
        this.value2 = value2;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pair<?,?>) && (((Pair<?, ?>) obj).value1.equals(value1) && ((Pair<?, ?>) obj).value2.equals(value2));
    }
}
