package common.tree;

import java.util.*;

/**
 * Trie implementation using a TreeMap
 *
 * @author saul.mtz.v
 */
public class Trie implements Tree {

    // store the children nodes,
    // uses a TreeMap because the order is important
    private TreeMap<Character, Trie> children;

    // prefix for the current node
    private String prefix;

    // size of the subtree, it has the value of all the subnodes from this one, included
    private int size = 1;

    // true when the current node represents a whole word
    private boolean isWord = false;

    /**
     * Default constructor, used for the root node, there is not prefix to keep
     */
    Trie() {
        this.prefix = "";
        size = 0;
    }

    /**
     * Creates a new node when all the descendants share the same prefix
     * @param prefix
     */
    private Trie(String prefix) {
        this.prefix = prefix;
    }

    /**
     * True when the current node is a leaf
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
     * Add a word to the current node
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
     * Add a child to the current node
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
     * Validate if the current node or its descendants contains the word given
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
     * Checks if the current node has a child with the key 'c'
     * @param c
     * @return
     */
    private boolean contains(Character c) {
        return null != children && children.containsKey(c);
    }

    /**
     * Validate if the current node or its descendants contains the word given
     *
     * @param str
     * @return Trie
     */
    public Trie search(String str) {
        Trie node = this;
        for (int i = 0; i < str.length(); i ++) {
            if (node.prefix.equals(str)) {
                return node;
            }

            Character c = str.charAt(i);
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }

        return null == node || !node.prefix.equals(str) ? null : node;
    }

    /**
     * Get the whole words of the current node
     *
     * @return
     */
    public List values() {
        List<String> elements = new ArrayList<>();
        if (isEmpty()) {
            return elements;
        }

        Deque<Trie> deque = new ArrayDeque<>();
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
        return null == prefixNode ? new ArrayList<String>() : prefixNode.values();
    }

    /**
     * The prefix is used as the string value
     * @return
     */
    public String toString() {
        return prefix;
    }

}
