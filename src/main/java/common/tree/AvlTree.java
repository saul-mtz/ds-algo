package common.tree;

import java.util.ArrayList;

/**
 * AVL Tree
 *
 * @link https://en.wikipedia.org/wiki/AVL_tree
 * @author saul.martinez
 */
public class AvlTree extends BinarySearch {

    public Node add(int val) {

        Node newNode = super.add(val);
        ArrayList<Node> ancestors = new ArrayList<>();
        findAncestors(root, val, ancestors);

        for (Node ancestor : ancestors) {
            // check the balance factor and do rotations if needed
            int balanceFactor = balanceFactor(ancestor);
            if (-2 == balanceFactor || 2 == balanceFactor) {
                balance(ancestor, balanceFactor);
            }

            // re-calculate the height for the current node
            // newNode.getHeight();
        }

        return newNode;
    }

    public boolean isBalanced() {
        int balanceFactor = balanceFactor(root);
        return balanceFactor > -2 && balanceFactor < 2;
    }

    private int balanceFactor(Node n) {
        return (null == n.left ? -1 : n.left.getHeight()) - (null == n.right ? -1 : n.right.getHeight());
    }

    private void balance(Node n, int balanceFactor) {
        if (-2 == balanceFactor) {
            if (-1 == balanceFactor(n.right)) {
                // right right case
                rotateLeft(n);
            } else {
                // right left case
                rotateRight(n.right);
                rotateLeft(n);
            }
        } else {
            if (-1 == balanceFactor(n.left)) {
                // left right case
                rotateLeft(n.left);
                rotateRight(n);
            } else {
                // left left case
                rotateRight(n);
            }
        }
    }

    private void rotateLeft(Node n) {
        Node newNode = new Node(n.data);
        newNode.left = n.left;
        newNode.right = n.right.left;

        n.data = n.right.data;
        n.right = n.right.right;
        n.left = newNode;

        n.left.setHeight(null);
    }

    private void rotateRight(Node n) {
        Node newNode = new Node(n.data);
        newNode.right = n.right;
        newNode.left = n.left.right;

        n.data = n.left.data;
        n.left = n.left.left;
        n.right= newNode;

        n.right.setHeight(null);
    }
}