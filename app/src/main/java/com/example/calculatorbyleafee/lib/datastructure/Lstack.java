package com.example.calculatorbyleafee.lib.datastructure;

public class Lstack<type> {
    private Lnode<type> _top;

    public Lstack() {
        this._top = null;
    }
    public void push(type item) {
        if (_top == null) {
            _top = new Lnode<type>(item);
        } else {
            _top = new Lnode<type>(item, _top);
        }
    }
    public type pop() throws EmptyContainerException {
        try {
            Lnode<type> temp = _top;
            this._top = this._top.next;
            return temp.item;
        } catch (NullPointerException nu) {
            throw new EmptyContainerException();
        }
    }
    public type top() throws EmptyContainerException {
        try {
            return _top.item;
        } catch (NullPointerException nu) {
            throw new EmptyContainerException();
        }
    }
    public boolean isempty() {
        return _top == null;
    }
    public String toString(String op) {
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (Lnode<type> temp = this._top; temp != null; temp = temp.next) {
            str.append(temp.item);
            if (temp.next != null)
                str.append(op);
        }
        str.append(']');
        return str.toString();
    }
    public String toString() {
        return this.toString("/");
    }

    public static void main(String[] args) throws EmptyContainerException {
        Lstack<String> st = new Lstack<String>();

        st.push("abc");
        System.out.println("push 'abc'");
        System.out.println("stack's top is " + st.top());
        System.out.println(st);


        st.push("def");
        System.out.println("push 'def'");
        System.out.println("stack's top is " + st.top());
        System.out.println(st);

        System.out.println("pop, and get " + st.pop());
        System.out.println("stack's top is " + st.top());
        System.out.println(st);

        System.out.println("Now, the stack is" + (st.isempty() ? " " : " not ") + "empty, let we try to pop");
        System.out.println("pop, and get " + st.pop());
        System.out.println(st);

        System.out.println("Now, the stack is" + (st.isempty() ? " " : " not ") + "empty, let we try to pop");
        System.out.println("As it is empty, it will throw and EmptyContainer Exception");
        System.out.println("pop, and get " + st.pop());
        System.out.println("stack's top is " + st.top());
        System.out.println(st);
    }
}
