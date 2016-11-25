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
    void add(String str);
    boolean contains(String str);
    List values();
}
