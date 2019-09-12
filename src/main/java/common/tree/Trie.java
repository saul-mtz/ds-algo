package common.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Trie implementation using a TreeMap
 *
 * @author saul.mtz.v
 */
public class Trie {

    // store the children nodes,
    // uses a TreeMap because the order is important
    private TreeMap<Character, Trie> children;

    // prefix for the current toNode
    private final String prefix;

    // size of the subtree, it has the value of all the sub nodes from this one, included
    private int size = 1;

    // true when the current toNode represents a whole word
    private boolean isWord = false;

    /**
     * Default constructor, used for the root toNode, there is not prefix to keep
     */
    Trie() {
        this.prefix = "";
        size = 0;
    }

    /**
     * Creates a new toNode when all the descendants share the same prefix
     * @param prefix
     */
    private Trie(String prefix) {
        this.prefix = prefix;
    }

    /**
     * True when the current toNode is a leaf
     * @return
     */
    public boolean isEmpty() {
        return null == children || 0 == children.size();
    }

    /**
     * Size of all the words with the current prefix
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add a word to the current toNode
     *
     * @param str
     */
    public void add(String str) {
        Trie node = this;
        for (Character c : str.toCharArray()) {
            if (!node.contains(c)) {
                node = node.add(c);
            } else {
                node = node.children.get(c);
                node.size ++;
            }
        }

        this.size ++;
        node.isWord = true;
    }

    /**
     * Add a child to the current toNode
     *
     * @param c
     * @return
     */
    private Trie add(Character c) {
        if (null == children) {
            children = new TreeMap<>();
        }
        Trie child = new Trie(prefix + c);
        children.put(c, child);
        return child;
    }


    /**
     * Validate if the current toNode or its descendants indexOf the word given
     *
     * @param word
     * @return null
     */
    public boolean contains(String word) {
        if (null == word || word.isEmpty()) {
            return false;
        }

        Trie node = this;
        for (Character c : word.toCharArray()) {
            if (!node.contains(c)) {
                return false;
            }
            node = node.children.get(c);
        }

        return true;
    }

    /**
     * Checks if the current toNode has a child with the key 'c'
     * @param c
     * @return
     */
    private boolean contains(Character c) {
        return null != children && children.containsKey(c);
    }

    /**
     * Validate if the current toNode or its descendants indexOf the word given
     *
     * @param prefix
     * @return Trie
     */
    public Trie search(String prefix) {
        if (null == prefix || prefix.isEmpty()) {
            return null;
        }

        Trie node = this;
        for (Character c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }

        return node;
    }

    /**
     * Get the whole words of the current toNode
     *
     * @return
     */
    public List values() {

        if (isEmpty()) {
            return null;
        }

        ArrayList<String> elements = new ArrayList<>();
        ArrayDeque<Trie> deque = new ArrayDeque<>();
        Trie current;
        deque.push(this);

        while (!deque.isEmpty()) {
            current = deque.pop();
            if (current.isWord) {
                elements.add(current.prefix);
            }

            if (!current.isEmpty()) {
                for (Character keyChar: current.children.descendingKeySet()) {
                    deque.push(current.children.get(keyChar));
                }
            }
        }
        return elements;
    }

    /**
     * Get all the elements that starts with the prefix given
     *
     * @param prefix
     * @return
     */
    public List startsWith(String prefix) {
        Trie prefixNode = search(prefix);
        return null == prefixNode ? null : prefixNode.values();
    }

    /**
     * The prefix is used as the string value
     * @return
     */
    public String toString() {
        return prefix;
    }

}
