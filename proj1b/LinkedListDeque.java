public class LinkedListDeque<T> implements Deque<T> {

    private Node sentinel;
    private int size;

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        Node first = new Node(item, sentinel, sentinel.next);
        first.next.prev = first;
        sentinel.next = first;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        Node last = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            if (p.next != sentinel) {
                System.out.print(" ");
            }
            p = p.next;
        }
        System.out.print("\n");
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size -= 1;
        Node ans = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        ans.prev = null;
        ans.next = null;
        return ans.item;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        size -= 1;
        Node ans = sentinel.prev;
        ans.prev.next = sentinel;
        sentinel.prev = ans.prev;
        ans.prev = null;
        ans.next = null;
        return ans.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node p = sentinel.next;
        for (int i = 0; i < index; ++i) {
            p = p.next;
        }
        return p.item;
    }
}
