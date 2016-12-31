import common.tree.AvlTree;
import common.tree.TreeNode;

import java.util.Scanner;

public class Main {

    public static void solve(int queryType, int i, int j) {
        TreeNode node = numbers.root;

        // biggest node that contains the index range
        TreeNode rangeNode = null;
        while (null != node && (node.indexMin <= i && j <= node.indexMax)) {
            rangeNode = node;
            if (i < node.getIndex()) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        if (null != rangeNode) {
            if (2 == queryType) {
                moveToBack(rangeNode, i, j, queryType);
                numbers.balance(numbers.root.findMax());
            } else {
                moveToFront(rangeNode, i, j, queryType);
                numbers.balance(numbers.root.findMin());
            }
        }
    }

    private static void moveToFront(TreeNode node, int i, int j, int queryType) {

        if (node.indexMin >= i && node.indexMax <= j) {
            moveNode(numbers.removeNode(node, true), queryType);
        } else {
            TreeNode left = node.getLeft();
            TreeNode right = node.getRight();

            if (null != right && right.indexMin <= j) {
                moveToFront(right, i, j, queryType);
            }

            if (i <= node.getIndex() && node.getHeight() <= j) {
                moveNode(numbers.removeNode(node, false), queryType);
            }

            if (null != left && left.indexMax >= i) {
                moveToFront(left, i, j, queryType);
            }
        }
    }

    private static void moveToBack(TreeNode node, Integer i, Integer j, int queryType) {
        if (node.indexMin >= i && node.indexMax <= j) {
            moveNode(numbers.removeNode(node, true), queryType);
        } else {
            TreeNode left = node.getLeft();
            TreeNode right = node.getRight();

            if (null != left && left.indexMax >= i) {
                moveToBack(left, i, j, queryType);
            }

            if (i <= node.getIndex() && node.getHeight() <= j) {
                moveNode(numbers.removeNode(node, false), queryType);
            }

            if (null != right && right.indexMin <= j) {
                moveToBack(right, i, j, queryType);
            }
        }
    }

    private static void moveNode(TreeNode removed, int queryType) {
        if (1 == queryType) {
            numbers.root.findMin().setLeft(removed);
        } else {
            numbers.root.findMax().setRight(removed);
        }
    }

    private static AvlTree numbers;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        numbers = new AvlTree();
        while (n-- > 0) {
            numbers.add(in.nextInt());
        }

        while (m-- > 0) {
            numbers.root.reindex(1);
            solve(in.nextInt(), in.nextInt(), in.nextInt());
        }

        /*
        numbers.root.reindex(1);
        solve(in.nextInt(), in.nextInt(), in.nextInt());
        assertEquals("[2, 3, 4, 1, 5, 6, 7, 8]", numbers.toString());
        assertTrue(numbers.isBalanced());

        numbers.root.reindex(1);
        solve(in.nextInt(), in.nextInt(), in.nextInt());
        assertEquals("[2, 3, 6, 7, 8, 4, 1, 5]", numbers.toString());
        assertTrue(numbers.isBalanced());

        numbers.root.reindex(1);
        solve(in.nextInt(), in.nextInt(), in.nextInt());
        assertEquals("[7, 8, 4, 1, 2, 3, 6, 5]", numbers.toString());
        assertTrue(numbers.isBalanced());

        numbers.root.reindex(1);
        solve(in.nextInt(), in.nextInt(), in.nextInt());
        assertEquals("[2, 3, 6, 5, 7, 8, 4, 1]", numbers.toString());
        assertTrue(numbers.isBalanced());

*/
        StringBuilder sb = new StringBuilder();
        for (Integer number : numbers.root.inOrder()) {
            sb.append(number);
            sb.append(' ');
        }
        System.out.println(Math.abs(numbers.root.findMin().getValue()-numbers.root.findMax().getValue()));
        System.out.println(sb);
    }


}