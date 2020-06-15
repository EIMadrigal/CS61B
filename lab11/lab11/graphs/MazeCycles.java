package lab11.graphs;

import edu.princeton.cs.introcs.StdRandom;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    Maze maze;
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private boolean hasCycle = false;
    private int[] parent;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        parent = new int[maze.V()];
        int srcX = StdRandom.uniform(1, maze.N() + 1);
        int srcY = StdRandom.uniform(1, maze.N() + 1);
        int s = maze.xyTo1D(srcX, srcY);
        parent[s] = s;
        dfs(s);
        announce();
    }

    private void dfs(int v) {
        marked[v] = true;

        for (int w : maze.adj(v)) {
            if (hasCycle) {
                return;
            }
            if (!marked[w]) {
                parent[w] = v;
                dfs(w);
            } else {
                // w has been visited but is not the parent of v
                if (w != parent[v]) {
                    parent[w] = v;

                    int cur = v;
                    while (cur != w) {
                        edgeTo[cur] = parent[cur];
                        cur = parent[cur];
                    }
                    edgeTo[cur] = parent[cur];

                    hasCycle = true;
                    return;
                }
            }
        }
    }
}
