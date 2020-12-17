package com.ekhvos;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of dequeue.
 * @param <Item> the type
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int size = 0;

    // construct an empty deque
    public Deque() {
        this.first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateAdd(item);

        // process initial value
        if (size == 0) {
            first = new Node<>(item);
        } else {
            Node<Item> oldFirst = first;
            // if only 1 item exist - return itself
            Node<Item> last = oldFirst.previous == null ? oldFirst : oldFirst.previous;

            Node<Item> newFirst = new Node<>(item, oldFirst, last);

            // update old first with link to new first as previous
            oldFirst.previous = newFirst;
            // set new first
            this.first = newFirst;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateAdd(item);

        // process initial value
        if (size == 0) {
            first = new Node<>(item);
        } else {
            // if only 1 item exist - return itself
            Node<Item> oldLast = first.previous == null ? first : first.previous;
            Node<Item> newLast = new Node<>(item, null, oldLast);

            // update link to new last as next
            oldLast.next = newLast;
            // update last item for first as previous
            first.previous = newLast;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemove();

        Node<Item> oldFirst = first;
        if (size == 1) {
            first = null;
        } else if (size == 2) {
            Node<Item> newFirst = oldFirst.next;
            first = new Node<>(newFirst.item);
        } else {
            Node<Item> newFirst = oldFirst.next;
            // repoint new first previous to current last
            newFirst.previous = oldFirst.previous;

            first = newFirst;
        }

        size--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateRemove();

        Node<Item> oldLast = first.previous == null ? first : first.previous;
        if (size == 1) {
            first = null;
        } else if (size == 2) {
            Node<Item> newLast = oldLast.previous;
            first = new Node<>(newLast.item);
        } else {
            Node<Item> newLast = oldLast.previous;
            // repoint new last to null
            newLast.next = null;

            first.previous = newLast;
        }

        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator(first);
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
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        public Node(Item item, Node<Item> next, Node<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeueIterator(Node<Item> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
