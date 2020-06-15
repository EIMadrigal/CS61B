package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        marked[s] = true;

        while (!q.isEmpty()) {
            Integer front = q.poll();
            if (front.equals(t)) {
                return;
            }
            for (int adj : maze.adj(front)) {
                if (!marked[adj]) {
                    edgeTo[adj] = front;
                    announce();
                    distTo[adj] = distTo[front] + 1;
                    marked[adj] = true;
                    q.offer(adj);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

