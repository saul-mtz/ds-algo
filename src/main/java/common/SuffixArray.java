package common;

import common.tree.SuffixTree;
import org.junit.Assert;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Suffix Array Data Structure, concepts taken from:
 * https://en.wikipedia.org/wiki/Suffix_array
 *
 * First version taken from the awesome:
 * http://algs4.cs.princeton.edu/63suffix/SuffixArrayX.java.html
 *
 */
public class SuffixArray implements SuffixDataStructure {

    private final SuffixTree suffixTree;
    private int[] lcp;
    private ArrayList<Integer> suffixArray;
    private int[] rank;

    public SuffixArray(String text) {
        suffixTree = new SuffixTree(text);
        rank = new int[suffixTree.length()-1];

        ArrayDeque<SuffixTree.TreeNode> queue = new ArrayDeque<>();
        suffixArray = new ArrayList<>(text.length()-1);
        queue.push(suffixTree.getRoot());
/*
        while (!queue.isEmpty()) {
            SuffixTree.TreeNode node = queue.pop();
            if (node.isLeaf()) {
                int index = node.getBeginIndex();
                if (index < text.length()) {
                    suffixArray.add(index);
                }
            } else {
                for (SuffixTree.TreeEdge edge : node.getChildNodes()) {
                    queue.push(edge.toNode);
                }
            }
        }
        */
    }

    @Override
    public boolean find(String word) {
        return false;
    }

    @Override
    public int indexOf(String word) {
        return 0;
    }

    @Override
    public int lastIndexOf(String word) {
        return 0;
    }

    @Override
    public int substringCount(String word) {
        return 0;
    }

    @Override
    public int length() {
        return suffixArray.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < suffixArray.size(); i ++) {
            sb.append(i + " => " + suffixArray.get(i) + ": " + suffixTree.substring(suffixArray.get(i)) + "\n");
        }
        return sb.toString();
    }

    public int rank(int i) {
        return rank[i];
    }

    public String getSuffix(int i) {
        return suffixTree.substring(suffixArray.get(i));
    }

    public char[] lcp(int i) {
        return new char[0];
    }

    public int get(int i) {
        return suffixArray.get(i);
    }
}
