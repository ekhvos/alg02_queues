package com.ekhvos;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        final int amount = Integer.parseInt(args[0]);

        if (amount == 0) {
            return;
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            final String s = StdIn.readString();
            queue.enqueue(s);
        }


        for (int i = 0; i < amount; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
