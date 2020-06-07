public class ArrayDeque<T> {

    private T[] items;
    private int front;  // first element's index
    private int rear;   // last element's index + 1
    private static final int INIT_CAPACITY = 8;
    private double useRatio;

    public ArrayDeque() {
        // cannot create an array of generic objects T[] items = new T[0]
        items = (T [])new Object[INIT_CAPACITY];
    }

    public ArrayDeque(ArrayDeque<T> other) {
        for (int i = 0; i < other.size(); ++i) {
            addLast(other.get(i));
        }
    }

    public void addFirst(T item) {
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
        if (rear == front) {
            resize();
        }
        useRatio = (rear - front + items.length) % items.length;
    }

    public void addLast(T item) {
        items[rear] = item;
        rear = (rear + 1) % items.length;
        if (rear == front) {
            resize();
        }
        useRatio = (rear - front + items.length) % items.length;
    }

    // double the size
    private void resize() {
        int curCapacity = items.length;
        int newCapacity = curCapacity << 1;
        T[] tmp = (T [])new Object[newCapacity];
        // copy elements from head to last
        System.arraycopy(items, front, tmp, 0, curCapacity - front);
        // copy elements from 0 to head
        System.arraycopy(items, 0, tmp, curCapacity - front, front);
        items = tmp;
        front = 0;
        rear = curCapacity;

        useRatio = 1.0 * curCapacity / newCapacity;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public int size() {
        return (rear - front + items.length) % items.length;
    }

    public void printDeque() {
        int size = size();
        int cnt = 0;
        for (int i = front; i != rear; i = (i + 1) % items.length) {
            System.out.print(items[i]);
            if (cnt < size) {
                System.out.print(" ");
            }
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        T x = items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        useRatio = (rear - front + items.length) % items.length;

        if (items.length >= 16 && useRatio < 0.25) {
            halfSize();
        }
        return x;
    }

    public T removeLast() {
        int index = (rear - 1 + items.length) % items.length;
        T x = items[index];
        items[index] = null;
        rear = index;

        useRatio = (rear - front + items.length) % items.length;
        if (items.length >= 16 && useRatio < 0.25) {
            halfSize();
        }
        return x;
    }

    private void halfSize() {
        int curCapacity = items.length;
        int newCapacity = curCapacity / 2;
        int curSize = (rear - front + curCapacity) % curCapacity;
        
        T[] tmp = (T [])new Object[newCapacity];
        System.arraycopy(items, front, tmp, 0, curSize);
        items = tmp;
        front = 0;
        rear = curSize;

        useRatio = 1.0 * curSize / newCapacity;
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }

        int trueIndex = (front + index) % items.length;
        return items[trueIndex];
    }
}
