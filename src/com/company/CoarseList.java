package com.company;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by louis on 4/1/15.
 *
 */
public class CoarseList<T> {

    public Node head;
    private ReentrantLock lock = new ReentrantLock();
    private boolean blocking;
    public CoarseList(boolean blocking) {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
        this.blocking = blocking;
    }

    public void print() {
        Node pred;
        Node curr;
        pred = head;
        curr = pred.next;
        while (curr.item != null) {
            pred = curr;
            curr = pred.next;
            //System.out.println("Reporting:" + pred.item.toString());
        }
    }
    public boolean add(T item) {
        Node pred;
        Node curr;
        int key = item.hashCode();

        if(blocking) {
            lock.lock();
        }
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }

            if (key == curr.key) {
                return false;
            } else {
                Node node = new Node<>(item);
                node.next = curr;
                pred.next = node;
                return true;
            }
        } finally {
            if (blocking) {
                lock.unlock();
            }
        }
    }
    public boolean remove(T item) {
        Node pred;
        Node curr;
        int key = item.hashCode();
        if (blocking) {
            lock.lock();
        }
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else {
                return false;
            }
        } finally {
            if (blocking) {
                lock.unlock();
            }
        }
    }


}
