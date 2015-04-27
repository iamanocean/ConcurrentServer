package com.company;

/**
 * Created by louis on 4/1/15.
 * A node class for a concurrent list
 *
 */
public class Node<T> {
    /**
     *	A node class for a concurrent list
     */
    T item;
    int key;
    Node next;

    /**
     *	Node object Constructor for sentinel nodes
     *
     *	@param	key		The key for the sentinel node
     */
    public Node(int key) {
        this.key = key;
    }

    /**
     *	Node object Constructor for all other nodes
     *
     *	@param	item	Any object that has a hashCode() method
     */
    public Node(T item) {
        this.key = item.hashCode();
        this.item = item;
    }
}
