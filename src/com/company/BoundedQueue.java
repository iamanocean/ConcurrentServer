package com.company;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * Created by louis on 4/11/15.
 * Book's implementation of a bounded queue with some changes, the book
 * has two major mistakes.
 */
public class BoundedQueue<T> {
    private ReentrantLock enqueueLock, dequeLock;
    private Condition notEmptyCondition, notFullCondition;
    AtomicInteger size;
    volatile QueueNode<T> head;
    volatile QueueNode<T> tail;
    int capacity;

    /**
     * Constructor for the bounded Queue
     *
     * @param capacity The total capacity for the queue, must be non-negative.
     *                 other wise capacity is set to 10.
     * */
    public BoundedQueue(int capacity) {
        if (capacity < 0) {
            this.capacity = 10;
        }
        this.capacity = capacity;
        head = new QueueNode<>(null);
        tail = head;
        size = new AtomicInteger(0);
        enqueueLock = new ReentrantLock();
        dequeLock = new ReentrantLock();
        notFullCondition = enqueueLock.newCondition();
        notEmptyCondition = dequeLock.newCondition();
    }

    /**
     * Enqueue method for the queue interface. Uses signalAll() to avoid lost
     * wakeups
     *
     * @param x The item to enqueue.
     * */
    public void enqueue(T x) {
        boolean mustWakeDequeuers = false;
        enqueueLock.lock();
        try {
            while (size.get() == capacity) {

                notFullCondition.await();
            }
            QueueNode<T> e = new QueueNode<>(x);
            tail.next = e;
            tail = e;
            //System.out.println("Successfully enqueued " + x);
            if(size.getAndIncrement() == 0) {
                mustWakeDequeuers = true;
            }
        } catch (InterruptedException error) {
            error.printStackTrace();
        } finally {
            enqueueLock.unlock();
        }
        if (mustWakeDequeuers) {
            dequeLock.lock();
            try {
                notEmptyCondition.signalAll();
            } finally {
                dequeLock.unlock();
            }
        }
    }

    /**
     * Dequeue method for the queue interface, returns a generic object. Uses
     * signalAll() to avoid the lost wakeup problem.
     *
     * @return A generic object.
     * */
    public T dequeue() {
        T result = null;
        boolean mustWakeEnqueuers = false;
        dequeLock.lock();
        try {
            while (size.get() == 0) {
                notEmptyCondition.await();
            }
            result = head.next.value;
            head = head.next;
            //System.out.println("successfully dequeued");
            if (size.getAndDecrement() == capacity) {
                mustWakeEnqueuers = true;
            }
        } catch (InterruptedException error) {
            error.printStackTrace();
        } finally {
            dequeLock.unlock();
        }
        if (mustWakeEnqueuers) {
            enqueueLock.lock();
            try {
                notFullCondition.signalAll();
            } finally {
                enqueueLock.unlock();
            }
        }
        return result;
    }

}
