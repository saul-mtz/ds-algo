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
    private TreeMap<Character, Trie> children = new TreeMap<Character, Trie>();

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
        for (int i = 0; i < str.length(); i ++) {
            Character c = str.charAt(i);
            if (!node.children.containsKey(c)) {
                node.children.put(c, new Trie(node.prefix + c));
                node = node.children.get(c);
            } else {
                node = node.children.get(c);
                node.size ++;
            }
        }

        this.size ++;
        node.isWord = true;
    }


    /**
     * Validate if the current node or its descendants contains the word given
     *
     * @param str
     * @return null
     */
    public boolean contains(String str) {
        Trie node = this;
        for (int i = 0; i < str.length(); i ++) {
            Character c = str.charAt(i);
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }

        return true;
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
        List<String> elements = new ArrayList<String>();
        if (isEmpty()) {
            return elements;
        }

        Deque<Trie> deque = new ArrayDeque<Trie>();
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
