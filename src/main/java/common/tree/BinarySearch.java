package common.tree;


import java.util.ArrayList;
import java.util.List;

/**
 * @link https://en.wikipedia.org/wiki/Binary_search_tree
 */
public class BinarySearch {

    protected Node root;

    public  Node add(Integer label) {
        if (null == root) {
            root = insert(null, label);
            return root;
        } else {
            return insert(root, label);
        }
    }

    public int getHeight() {
        if (null == root) {
            return 0;
        }
        return root.getHeight();
    }

    public List inOrder(Node root) {
        if (null == root) {
            return null;
        }

        List<Integer> values = new ArrayList<>();
        List leftNodes  = inOrder(root.left);
        List rightNodes = inOrder(root.right);

        if (null != leftNodes) {
            values.addAll(leftNodes);
        }
        values.add(root.data);
        if (null != rightNodes) {
            values.addAll(rightNodes);
        }

        return values;
    }

    public Node lca(Integer a, Integer b) {
        ArrayList<Node> ancestorsA = new ArrayList<>();
        ArrayList<Node> ancestorsB = new ArrayList<>();

        findAncestors(root, a, ancestorsA);
        findAncestors(root, b, ancestorsB);

        //Collections.reverse(ancestorsA);
        for (Node ancestor : ancestorsA) {
            if (null != ancestor && isCommon(ancestor, ancestorsB)) {
                return ancestor;
            }
        }

        return null;
    }

    public String toString() {
        return inOrder(root).toString();
    }

    private  Node insert(Node node, Integer label) {
        if (null == node) {
            return new Node(label);
        } else if (label < node.data) {
            node.left = insert(node.left, label);
            node.setHeight(null); // height needs to be re-calculated
        } else {
            node.right = insert(node.right, label);
            node.setHeight(null); // height needs to be re-calculated
        }
        return node;
    }

    protected boolean findAncestors(Node n, Integer value, List<Node> ancestors) {
        if (null == n.left && null == n.right && value != n.data) {
            return false;
        }

        boolean found = true;
        if (n.data != value) {
            found = findAncestors(value > n.data ? n.right : n.left, value, ancestors);
        }

        if (found) {
            ancestors.add(n);
        }

        return found;
    }

    private boolean isCommon(Node n, List<Node> ancestors) {
        for (Node ancestor : ancestors) {
            if (n.data == ancestor.data) {
                return true;
            }
        }
        return false;
    }

}

class Node {
    Integer data;
    Node left;
    Node right;

    private Integer height;

    Node(Integer data) {
        this.data = data;
        this.height = 0;
    }

    public int getHeight() {
        if (null == height) {
            int heightLeft  = null == left  ? -1 : left.getHeight();
            int heightRight = null == right ? -1 : right.getHeight();

            height = Math.max(heightLeft, heightRight) + 1;
        }
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String toString() {
        return data.toString();
    }
}