package common.tree;

import java.util.*;

/**
 * Trie implementation using a TreeMap
 *
 * @author saul.mtz.v
 */
public class Trie implements Tree {

    private TreeMap<Character, Trie> children = new TreeMap<Character, Trie>();
    private String prefix;

    Trie() {
        this.prefix = "";
    }

    Trie(String prefix) {
        this.prefix = prefix;

    }

    public boolean containsCharacter(char c) {
        return children.containsKey(c);
    }

    public void add(String str) {
        Trie node = this;
        for (int i = 0; i < str.length(); i ++) {
            Character c = str.charAt(i);
            if (!node.containsCharacter(c)) {
                node.add(c);
            }
            node = node.get(c);
        }
    }


    private void add(char c) {
        children.put(c, new Trie(prefix + c));
    }

    public Trie get(char c) {
        return children.containsKey(c) ? children.get(c) : null;
    }

    public boolean contains(String str) {
        Trie node = this;
        for (int i = 0; i < str.length(); i ++) {
            Character c = str.charAt(i);
            if (!node.containsCharacter(c)) {
                return false;
            }
            node = node.get(c);
        }

        return true;
    }

    public boolean isEmpty() {
        return null == children || 0 == children.size();
    }

    public int size() {
        return isEmpty() ? 0 : children.size();
    }

    public List preOrder() {
        return null;
    }

    public List inOrder() {
        List<String> elements = new ArrayList<String>();
        if (isEmpty()) {
            return elements;
        }

        Deque<Trie> deque = new ArrayDeque<Trie>();
        Trie current = this;
        deque.addAll(current.children.values());

        while (!deque.isEmpty()) {
            current = deque.pop();
            if (current.isEmpty()) {
                elements.add(current.prefix);
            } else {;
                for (Character keyChar: current.children.descendingKeySet()) {
                    deque.push(current.children.get(keyChar));
                }
            }
        }
        return elements;
    }

    public List postOrder() {
        return null;
    }

    public String toString() {
        return prefix;
    }
}
