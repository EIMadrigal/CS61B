package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.Map;

public class Solver {

    private Stack<WorldState> seq;
    private MinPQ<Node> minPQ;
    private Map<WorldState, Integer> estimateDisCach;

    private class Node implements Comparable<Node> {
        private WorldState worldState;
        private Node pre;

        // the number of moves made to reach this world state from the initial state.
        private int disToCur;
        private int estimateDis;
        private int priority;

        @Override
        public int compareTo(Node n) {
            return this.priority - n.priority;
        }

        private Node(WorldState worldState, int disToCur, Node pre) {
            this.worldState = worldState;
            this.disToCur = disToCur;
            this.pre = pre;
            if (estimateDisCach.containsKey(this.worldState)) {
                this.estimateDis = estimateDisCach.get(this.worldState);
            } else {
                this.estimateDis = this.worldState.estimatedDistanceToGoal();
                estimateDisCach.put(this.worldState, this.estimateDis);
            }
            this.priority = this.estimateDis + disToCur;
        }
    }

    public Solver(WorldState initial) {
        minPQ = new MinPQ<>();
        seq = new Stack<>();
        estimateDisCach = new HashMap<>();

        Node curNode = new Node(initial, 0, null);
        while (!curNode.worldState.isGoal()) {
            for (WorldState worldState : curNode.worldState.neighbors()) {
                if (curNode.pre == null || !worldState.equals(curNode.pre.worldState)) {
                    Node node = new Node(worldState, curNode.disToCur + 1, curNode);
                    minPQ.insert(node);
                }
            }
            curNode = minPQ.delMin();
        }

        for (Node node = curNode; node != null; node = node.pre) {
            seq.push(node.worldState);
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return seq.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return seq;
    }
}
