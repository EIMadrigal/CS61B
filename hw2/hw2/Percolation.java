package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int numOfOpen;
    private int virtualTop;  // for the sake of O(1)
    private int virtualBottom;

    private boolean[][] gridState;
    private int N;

    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF antiBackwash;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N)   {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0.");
        }

        this.N = N;
        gridState = new boolean[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                gridState[i][j] = false;
            }
        }
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        numOfOpen = 0;

        UF = new WeightedQuickUnionUF(N * N + 2);
        antiBackwash = new WeightedQuickUnionUF(N * N + 1);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("out of bound.");
        }

        if (!gridState[row][col]) {
            gridState[row][col] = true;
            ++numOfOpen;
        }

        if (row == 0) {
            UF.union(virtualTop, xyto1D(row, col));
            antiBackwash.union(virtualTop, xyto1D(row, col));
        }
        if (row == N - 1) {
            UF.union(virtualBottom, xyto1D(row, col));
        }

        int[] drow = {0, 1, 0, -1};
        int[] dcol = {-1, 0, 1, 0};
        for (int i = 0; i < 4; ++i) {
            int nextrow = row + drow[i];
            int nextcol = col + dcol[i];
            if (validate(nextrow, nextcol) && isOpen(nextrow, nextcol)) {
                UF.union(xyto1D(nextrow, nextcol), xyto1D(row, col));
                antiBackwash.union(xyto1D(nextrow, nextcol), xyto1D(row, col));
            }
        }
    }

    private boolean validate(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    private int xyto1D(int row, int col) {
        return row * N + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("out of bound.");
        }
        return gridState[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("out of bound.");
        }

        return isOpen(row, col) && antiBackwash.connected(virtualTop, xyto1D(row, col));

        /* wrong avoid backwash
        if (col - 1 >= 0 && index - 1 >= 0 && UF.connected(index - 1, virtualTop)) {
            return true;
        }
        if (col + 1 < N && index + 1 < N * N && UF.connected(index + 1, virtualTop)) {
            return true;
        }
        if (row - 1 >= 0 && index - N >= 0 && UF.connected(index - N, virtualTop)) {
            return true;
        }
        if (row + 1 < N && index + N < N * N && UF.connected(index + N, virtualTop)) {
            return true;
        } */
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(virtualTop, virtualBottom);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
