package synthesizer;

public class GuitarString {

    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(capacity);
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        while (!buffer.isFull()) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
    }

    /**
     * Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double first = 0.0;
        double toEnqueue = 0.0;
        if (!buffer.isEmpty()) {
            first = buffer.dequeue();
        }
        if (!buffer.isEmpty()) {
            toEnqueue = (first + buffer.peek()) / 2 * DECAY;
        }
        if (!buffer.isFull()) {
            buffer.enqueue(toEnqueue);
        }
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (!buffer.isEmpty()) {
            return buffer.peek();
        }
        return 0.0;
    }
}
