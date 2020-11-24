import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    public class TrieNode {
        private TrieNode() {
            children = new HashMap<>();
            isWord = false;
            extraInfo = new ArrayList<>();
        }

        Map<Character, TrieNode> children;
        private boolean isWord;


        public List<Map<String, Object>> extraInfo;
        /*
        private double lat;
        private double lon;
        private long id;*/
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word, long id, double lat, double lon) {
        if (word == null)
            return;
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
        }
        node.isWord = true;

        // if two words go into the same leaf, latter will cover the former id, lat, lon
        /*node.id = id;
        node.lat = lat;
        node.lon = lon;*/

        Map<String, Object> m = new HashMap<>();
        m.put("ID", id);
        m.put("lat", lat);
        m.put("lon", lon);
        node.extraInfo.add(m);
    }

  /*  public boolean startsWith(String prefix) {
        if (prefix == null) {
            return false;
        }
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return true;
    }*/

    public TrieNode startsWith(String prefix) {
        if (prefix == null) {
            return null;
        }
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node;
    }

    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isWord;
    }
}
