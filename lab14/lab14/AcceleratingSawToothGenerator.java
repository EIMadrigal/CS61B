package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double periodChangeFac;

    public AcceleratingSawToothGenerator(int period, double periodChangeFac) {
        state = 0;
        this.period = period;
        this.periodChangeFac = periodChangeFac;
    }

    @Override
    public double next() {
        state = (state + 1) % this.period;
        if (state == 0) {
            this.period = (int) (this.periodChangeFac * this.period);
        }
        return normalize(state);
    }

    private double normalize(int state) {
        return 2.0 * state / (this.period - 1) - 1.0;
    }
}
