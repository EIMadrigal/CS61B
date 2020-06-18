package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private final int BLANK = 0;

    private int[][] board;

    public Board(int[][] tiles) {
        int N = tiles.length;
        board = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (!inBound(i, j)) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        return board[i][j];
    }

    private boolean inBound(int i, int j) {
        if (i < 0 || j < 0 || i >= size() || j >= size()) {
            return false;
        }
        return true;
    }


    public int size() {
        return board.length;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int blankPosX = -1;
        int blankPosY = -1;
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (tileAt(i, j) == BLANK) {
                    blankPosX = i;
                    blankPosY = j;
                }
            }
        }

        int[][] copy = new int[size()][size()];
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                copy[i][j] = tileAt(i, j);
            }
        }

        // The blank tile has 4 directions to go
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        for (int i = 0; i < 4; ++i) {
            int nextx = blankPosX + dx[i];
            int nexty = blankPosY + dy[i];
            if (inBound(nextx, nexty)) {
                copy[blankPosX][blankPosY] = copy[nextx][nexty];
                copy[nextx][nexty] = BLANK;
                Board neighbor = new Board(copy);
                neighbors.enqueue(neighbor);
                copy[nextx][nexty] = copy[blankPosX][blankPosY];
                copy[blankPosX][blankPosY] = BLANK;
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hammingDis = 0;
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                int goalNum = i * size() + j + 1;
                if (goalNum < size() * size() && board[i][j] != goalNum) {
                    ++hammingDis;
                }
            }
        }
        return hammingDis;
    }

    public int manhattan() {
        int manhattanDis = 0;
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (board[i][j] == BLANK) {
                    continue;
                }
                int goalPosX = (board[i][j] - 1) / size();
                int goalPosY = (board[i][j] - 1) % size();
                manhattanDis += Math.abs(goalPosX - i) + Math.abs(goalPosY - j);
            }
        }
        return manhattanDis;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    // Returns true if this board's tile values are the same position as y's
    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board yy = (Board) y;
        if (yy.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (this.board[i][j] != yy.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
