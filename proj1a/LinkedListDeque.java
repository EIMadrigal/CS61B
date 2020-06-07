public class LinkedListDeque<T> {

    private Node sentinel;
    private int size;

    public class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * @source Got help with Josh Hug from https://www.youtube.com/watch?v=JNroRiEG7U4
     * */
    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); ++i) {
            addLast(other.get(i));
        }
    }

    public void addFirst(T item) {
        size += 1;
        Node first = new Node(item, sentinel, sentinel.next);
        first.next.prev = first;
        sentinel.next = first;
    }

    public void addLast(T item) {
        size += 1;
        Node last = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }

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

    public T removeFirst() {
        if (sentinel.next == null) {
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

    public T removeLast() {
        if (sentinel.prev == null) {
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

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index + 1 == size) {
            return sentinel.item;
        }
        sentinel = sentinel.next;
        return getRecursive(index + 1);
    }
}
