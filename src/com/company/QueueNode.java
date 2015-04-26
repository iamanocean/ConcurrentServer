package com.company;

/**
 * Created by louis on 4/18/15.
 *
 */
public class QueueNode<T> {
    volatile QueueNode<T> next;
    T value;
    public QueueNode(T x) {
        this.value = x;
        next = null;
    }
}
