package common.tree;

import java.util.List;

/**
 * General interface for all the kinds of tree
 *
 * œauthor saul.martinez
 */
public interface Tree {

    // size and capacity
    boolean isEmpty();
    int size();

    // content
    boolean contains(String str);

    // traversal
    // root -> left -> right
    List preOrder();

    // left -> root -> right
    List inOrder();

    // left -> right -> root
    List postOrder();

}
