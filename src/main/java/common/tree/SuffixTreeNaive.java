package common.tree;

import common.SuffixDataStructure;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Implementation based on:
 * @link https://en.wikipedia.org/wiki/Suffix_tree
 */
public class SuffixTreeNaive implements SuffixDataStructure {
    private Trie trie;
    private String src;

    public SuffixTreeNaive(String src) {
        if (null == src || 0 == src.length()) {
            return;
        }

        this.src = src;
        trie = new Trie();
        for (int i = 0; i < src.length(); i ++) {
            trie.add(src.substring(i), i);
        }
    }

    public boolean find(String str) {
        return null != trie.getPositions(str);
    }

    public int indexOf(String word) {
        TreeSet<Integer> positions = trie.getPositions(word);
        return null == positions ? -1 : positions.first();
    }

    public int lastIndexOf(String word) {
        TreeSet<Integer> positions = trie.getPositions(word);
        return null == positions ? -1 : positions.last();
    }

    public int substringCount(String word) {
        TreeSet<Integer> positions = trie.getPositions(word);
        return null == positions ? 0 : positions.size();
    }


    @Override
    public int length() {
        return src.length();
    }

    public String toString() {
        return src;
    }

    private class Trie {

        private TreeMap<Character, Trie> children;
        private TreeSet<Integer> startPositions = new TreeSet<>();

        public void add(String str, int i) {
            Trie node = this;
            for (Character c : str.toCharArray()) {
                if (!node.indexOf(c)) {
                    if (null == node.children) {
                        node.children = new TreeMap<Character, Trie>();
                    }
                    Trie child = new Trie();
                    node.children.put(c, child);
                    node = child;
                } else {
                    node = node.children.get(c);
                }
                node.startPositions.add(i);
            }
        }

        private boolean indexOf(Character c) {
            return null != children && children.containsKey(c);
        }

        private TreeSet<Integer> getPositions(String word) {
            if (null == word || word.isEmpty()) {
                return null;
            }
            Trie node = this;
            for (Character c : word.toCharArray()) {
                if (!node.indexOf(c)) {
                    return null;
                }
                node = node.children.get(c);
            }
            return node.startPositions;
        }
    }
}