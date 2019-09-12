package common.tree;

import common.SuffixDataStructure;

import java.util.*;

/**
 * Suffix tree using the Ukkonen's algorithm
 *
 * @link https://en.wikipedia.org/wiki/Suffix_tree
 *
 * Based on the awesome explanaition from:
 * @link http://stackoverflow.com/a/9513423/2938519
 */
public class SuffixTree implements SuffixDataStructure {

    protected final char SENTINEL = Character.MIN_VALUE;
    protected final StringBuilder text;

    protected TreeNode root;
    protected int currentIndex;

    protected final ActivePoint activePoint = createActivePoint();
    protected TreeNode lastInserted;
    protected int remainder = 0;

    public SuffixTree(String src) {
        text = new StringBuilder(src.length() + 1);
        init(src);
        build();
    }

    protected void init(String src) {
        text.append(src);
        text.append(SENTINEL);
        root = createNode(null, 0);
        activePoint.set(root, null, 0);
    }

    protected void build() {
        for (currentIndex = 0; currentIndex < text.length(); currentIndex ++) {
            lastInserted = null;
            remainder ++;
            processCharacter();
        }
    }

    protected boolean processCharacter() {
        // active node
        boolean ignored = false;

        do {
            /*
            after many test cases the best approach is try to execute only one fo the actions below, in that order
            - can I ignore the current char? (it is within the active edge)
            - should I split the current edge?
            - insert the current char in the active node
            */

            if (toIgnoreChar()) {
                ignoreChar();
                ignored = true;
                adjustActivePoint(true, false);
            } else if (toSplit()) {
                splitChar();
                adjustActivePoint(false, true);
            } else {
                addNode();
                adjustActivePoint(false, false);
            }

        } while (!ignored && remainder > 0);
        return ignored;
    }

    protected boolean toSplit() {
        return remainder > 1 && null != activePoint.getEdge() && activePoint.length > 0;
    }

    protected boolean toIgnoreChar() {
        TreeNode node = activePoint.getNode();
        Character edge = activePoint.getEdge();

        if (null == edge) {
            // first char of the active node
            return activePoint.getNode().hasChild(currentIndex);
        } else if (null != node.getChild(edge)){
            // the current char is in the active edge
            int index = activePoint.length;
            return node.getChild(edge).charAt(index) == text.charAt(currentIndex);
        } else {
            return false;
        }
    }

    protected Collection<TreeNode> splitChar() {
        TreeNode node = activePoint.getNode();
        Character edge = activePoint.getEdge();

        return node.getChild(edge).split();
    }

    protected void ignoreChar() {
        if (null == activePoint.getEdge()) {
            // the char is in one edge of the active node
            activePoint.set(activePoint.getNode(), currentIndex, 1);
        } else {
            activePoint.incrementLength();
        }
    }

    protected TreeNode addNode() {
        remainder --;
        return activePoint.getNode().add(currentIndex);
    }

    protected void adjustActivePoint(boolean ignored, boolean split) {
        if (0 == remainder) {
            activePoint.set(root, null, 0);
        } else if (root == activePoint.getNode() && activePoint.getLength() > 20) {
            boolean stop = true;
        } else if (split) {
            // After split rules
            // 1: when the active toNode is the root
            if (activePoint.node == root) {
                activePoint.decrementLength();
                activePoint.nextEdge();
            } else {
                // Rule 3: "splitting an edge from an active_node that is not the root toNode"
                if (null != activePoint.node.suffixLink) {
                    // 3.1: "we follow the suffix link going out of that toNode, if there is any, and reset the active_node to the toNode it points to"
                    activePoint.node = activePoint.node.suffixLink;
                    //activePoint.edge = activePoint.node.getEdge(index - activePoint.length);
                } else {
                    // 3.2 " If there is no suffix link, we set the active_node to the root.
                    //       active_edge and active_length remain unchanged."
                    if (activePoint.getNode().getParent().getLength() > 1) {
                        boolean stop = true;
                    }

                    activePoint.node = root;
                }
            }
        } else if (!ignored) {
            // we just add a new node in a node different than the root
            activePoint.set(root, null, 0);
            //activePoint.nextEdge();
        }

        if (null != activePoint.getEdge() && activePoint.getNode().hasChild(activePoint.edge)) {
            TreeNode node = activePoint.getNode().getChild(activePoint.getEdge());
            int l1 = node.getLength();
            int l2 = activePoint.getLength();

            // do we reach the end of the active edge?
            if (l2 >= l1) {
                activePoint.set(node, null, 0);

                if (null != lastInserted && lastInserted != node) {
                    lastInserted.suffixLink = node;
                }
            }
        }
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
        return text.length();
    }

    public TreeNode getRoot() {
        return root;
    }

    public String substring(int start) {
        return text.substring(start, text.length()-1);
    }

    protected class ActivePoint {
        protected TreeNode node;
        protected Integer edge;
        protected int length;

        public void set(TreeNode node, Integer edge, int length) {
            this.node = node;
            this.edge = edge;
            this.length = length;
        }

        public TreeNode getNode() {
            return node;
        }

        public Character getEdge() {
            return null == edge ? null : text.charAt(edge);
        }

        public int getLength() {
            return length;
        }

        public void incrementLength() {
            length ++;
        }

        public void decrementLength() {
            length --;
        }

        public void nextEdge() {
            edge ++;
        }
    }

    public class TreeNode {

        protected TreeMap<Character, TreeNode> children;
        protected TreeNode suffixLink;
        protected TreeNode parent;

        protected int from;
        protected int to;
        protected int length;
        protected int lengthPath;

        TreeNode(TreeNode parent, int from) {
            this.parent = parent;
            this.from = from;
            this.to = -1;

            length = text.length() - from;
            lengthPath = isRoot() ? length : (parent.lengthPath + length);
        }

        public TreeNode add(int index) {
            if (null == children) {
                children = new TreeMap<>();
            }

            Character key = text.charAt(index);
            TreeNode node = createNode(this, index);
            children.put(key, node);

            return node;
        }

        protected TreeNode add(TreeNode node) {
            Character key = text.charAt(node.from);
            children.put(key, node);
            node.setParent(this);
            return node;
        }

        public TreeNode getChild(Character c) {
            return children.containsKey(c) ? children.get(c) : null;
        }

        public boolean hasChild(int index) {
            Character c = text.charAt(index);
            return null != children && children.containsKey(c);
        }

        public int getLength() {
            return -1 == to ? (currentIndex - from + 1): length;
        }

        public boolean isLeaf() {
            return null == children || 0 == children.size();
        }


        public Integer getBeginIndex() {
            return from - parent.getLengthPath();
        }

        public Collection<TreeNode> getChildNodes() {
            return null == children ? new ArrayList<>() : children.values();
        }

        public boolean isRoot() {
            return null == parent;
        }

        public Character charAt(int index) {
            return text.charAt(from + index);
        }

        public Collection<TreeNode> split() {
            int l = getLength();
            int cutEdgeAt = from + activePoint.length - 1;
            TreeNode node = null;

            ArrayList<TreeNode> nodes = new ArrayList<>();
            if (-1 == to) {
                /*
                Basic case: The node to split point to a leaf node
                Example: create the "abx" path from the "abcd" edge
                                x
                               /
                abcd    ->    ab
                               \
                               cd
                 */
                setTo(cutEdgeAt);
                node = this;

                nodes.add(this.add(to + 1));
                nodes.add(this.add(currentIndex));
                nodes.add(node);
            } else if (cutEdgeAt < length()) {
                /*
                 For example: The 6th iteration of banana, we need to split the edge so that
                 "a$" is a new prefix
                   $             $   $
                  /             /   /
                 ana     ->    a - na
                  \                 \
                  na$               na$
                 */
                TreeNode p = parent;

                TreeNode removed = p.remove(from);

                nodes.add(node = p.add(from));
                node.setTo(cutEdgeAt);
                nodes.add(node.add(currentIndex));
                removed.setFrom(removed.from + activePoint.length);
                nodes.add(node.add(removed));
            }

            // set the suffix link
            if (null != lastInserted && lastInserted != node) {
                lastInserted.suffixLink = node;
            }

            remainder--;
            lastInserted = node;

            return nodes;
        }

        private TreeNode remove(int index) {
            if (hasChild(index)) {
                Character key = text.charAt(index);
                TreeNode removed = children.remove(key);
                removed.parent = null;
                return removed;
            }

            return null;
        }

        public int getLengthPath() {
            if (null == parent) {
                return 0;
            } else if (-1 != to) {
                return lengthPath;
            } else {
                return parent.getLengthPath() + length;
            }
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setTo(int to) {
            this.to = to;
            length = to - from + 1;
            lengthPath = isRoot() ? 0 : (parent.getLengthPath() + length);
        }

        public void setFrom(int from) {
            this.from = from;
            length = to - from + 1;
            lengthPath = isRoot() ? 0 : (parent.getLengthPath() + length);
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
            lengthPath = isRoot() ? 0 : (parent.getLengthPath() + length);
        }
    }

    protected TreeNode createNode(TreeNode parent, int from) {
        return new TreeNode(parent, from);
    }

    protected ActivePoint createActivePoint() {
        return new ActivePoint();
    }

}