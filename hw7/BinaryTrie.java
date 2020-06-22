import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {

    private static class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        // compare based on freq
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    private Node root;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        BitSequence bs = new BitSequence();
        Node cur = root;
        for (int i = 0; i < querySequence.length(); ++i) {
            if (cur.isLeaf()) {
                break;
            }
            if (querySequence.bitAt(i) == 1) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
            bs = bs.appended(querySequence.bitAt(i));
        }
        return new Match(bs, cur.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        BitSequence bs = new BitSequence();
        buildHelper(root, bs, table);
        return table;
    }

    private void buildHelper(Node node, BitSequence cur, Map<Character, BitSequence> table) {
        if (node.isLeaf()) {
            table.put(node.ch, cur);
            return;
        }
        buildHelper(node.left, cur.appended(0), table);
        buildHelper(node.right, cur.appended(1), table);
    }
}
