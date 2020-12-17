package com.ekhvos;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of queue which returns content in random order.
 * @param <Item> the type of data
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        validateAdd(item);

        // process initial value
        if (size == 0) {
            first = new Node<>(item);
        } else {
            Node<Item> oldFirst = first;

            // set new first
            this.first = new Node<>(item, oldFirst);
        }

        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        validateRemove();

        Node<Item> oldFirst = first;
        if (size == 1) {
            first = null;
        } else {
            int position = StdRandom.uniform(size);
            if (position == 0) {
                first = oldFirst.next;
            } else {
                Node<Item> node = getNode(position - 1);
                Node<Item> toRemove = node.next;
                node.next = toRemove.next;
                oldFirst = toRemove;
            }
        }

        size--;
        return oldFirst.item;
    }

    private Node<Item> getNode(int position) {
        Node<Item> node = first;
        for (int i = 0; i < position; i++) {
            node = node.next;
        }

        return node;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateRemove();

        int position = StdRandom.uniform(size);
        Node<Item> node = getNode(position);
        return node.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(first, size);
    }

    private void validateAdd(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Input should not be null");
        }
    }

    private void validateRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty dequeue");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(0);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }

        public Node(Item item) {
            this.item = item;
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] items;
        private int pointer = 0;

        public RandomizedQueueIterator(Node<Item> first, int size) {
            this.items = (Item[]) new Object[size];

            Node<Item> node = first;
            for (int i = 0; i < size; i++) {
                items[i] = node.item;
                node = node.next;
            }

            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return pointer < items.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[pointer++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
