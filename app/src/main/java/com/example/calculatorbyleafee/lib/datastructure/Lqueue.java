package com.example.calculatorbyleafee.lib.datastructure;

public class Lqueue<type> {
    private Lnode<type> head;
    private Lnode<type> tail;

    public Lqueue() {
        this.head = this.tail = null;
    }
    public void push(type item) {
        if (this.head == null) {
            this.head = this.tail = new Lnode<type>(item);
        } else {
            this.tail.next = new Lnode<type>(item);
            this.tail = this.tail.next;
        }
    }
    public type pop() throws EmptyContainerException {
        if (this.head == null)
            throw new EmptyContainerException();

        if (this.head == this.tail) {
            Lnode<type> temp = this.head;
            this.head = this.tail = null;
            return temp.item;
        } else {
            Lnode<type> temp = this.head;
            this.head = this.head.next;
            return temp.item;
        }
    }
    public type front() throws EmptyContainerException {
        try {
            return head.item;
        } catch (NullPointerException e) {
            throw new EmptyContainerException();
        }
    }
    public type back() throws EmptyContainerException {
        try {
            return tail.item;
        } catch (NullPointerException e) {
            throw new EmptyContainerException();
        }
    }
    public boolean isempty() {
        return this.head == null;
    }
    public String toString(String op) {
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (Lnode<type> temp = this.head; temp != null; temp = temp.next) {
            str.append(temp.item);
            if (temp.next != null) {
                str.append(op);
            }
        }
        str.append(']');
        return str.toString();
    }
    public String toString() {
        return this.toString("<-");
    }

    public static void main(String[] args) throws EmptyContainerException {
        Lqueue<String> queue = new Lqueue<String>();

        queue.push("abc");
        System.out.println("push abc");
        System.out.println("queue's front is " + queue.front());
        System.out.println("queue's back is " + queue.back());
        System.out.println(queue);

        queue.push("def");
        System.out.println("push def");
        System.out.println("queue's front is " + queue.front());
        System.out.println("queue's back is " + queue.back());
        System.out.println(queue);

        System.out.println("pop, and get " + queue.pop());
        System.out.println("queue's front is " + queue.front());
        System.out.println("queue's back is " + queue.back());
        System.out.println(queue);

        System.out.println("Now, the queue is" + (queue.isempty() ? " " : " not ") + "empty, let we try to pop");
        System.out.println("pop, and get " + queue.pop());
        System.out.println(queue);

        System.out.println("Now, the queue is" + (queue.isempty() ? " " : " not ") + "empty, let we try to pop");
        System.out.println("As it is empty, it should throw EmptyContainerException");
        System.out.println("pop, and get " + queue.pop());
    }
}