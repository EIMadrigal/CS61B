import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.introcs.In;

public class Boggle {

    // File path of dictionary file
    static String dictPath = "words.txt";
    private static char[][] board;
    private static int[] dx = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    private static int[] dy = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    private static Trie trie;
    private static TreeSet<String> ans;

    private static class CompareByLen implements Comparator<String> {
        @Override
        // s1 is to be called, s2 is the obj in the set
        public int compare(String s1, String s2) {
            int num = s2.length() - s1.length();
            return num == 0 ? s1.compareTo(s2) : num;
        }
    }

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0 || dictPath == null || !isBoardRect(boardFilePath)) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        In in = new In(dictPath);
        trie = new Trie();
        while (in.hasNextLine()) {
            trie.insert(in.readLine());
        }

        ans = new TreeSet<>(new CompareByLen());
        boolean[][] vis = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                String cur = "";
                dfs(i, j, vis, cur);
            }
        }

        List<String> res = new ArrayList<>();
        while (res.size() < k) {
            if (ans.isEmpty()) {
                break;
            }
            res.add(ans.pollFirst());
        }

        return res;
    }

    private static void dfs(int x, int y, boolean[][] vis, String cur) {
        if (!inBound(x, y) || vis[x][y] || !trie.startsWith(cur)) {
            return;
        }
        vis[x][y] = true;
        cur += String.valueOf(board[x][y]);

        if (cur.length() >= 3 && trie.search(cur)) {
            ans.add(cur);
        }
        for (int m = 0; m < 8; ++m) {
            int nextx = x + dx[m], nexty = y + dy[m];
            dfs(nextx, nexty, vis, cur);
        }
        vis[x][y] = false;
    }

    private static boolean inBound(int x, int y) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length;
    }

    private static boolean isBoardRect(String boardFilePath) {
        if (boardFilePath == null) {
            return false;
        }

        In in = new In(boardFilePath);
        String[] input = in.readAllLines();

        int row = input.length, col = input[0].length();
        board = new char[row][col];
        for (int i = 0; i < row; ++i) {
            if (input[i].length() != col) {
                return false;
            }
            for (int j = 0; j < col; ++j) {
                board[i][j] = input[i].charAt(j);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        for (String s : solve(7, "smallBoard.txt")) {
            System.out.println(s);
        }
    }
}
