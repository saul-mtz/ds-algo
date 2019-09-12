package common;

import common.tree.SuffixTree;

/**
 * Interface for any data structure that handle suffix data
 */
public interface SuffixDataStructure {

    boolean find(String word);
    int indexOf(String word);
    int lastIndexOf(String word);
    int substringCount(String word);
    int length();
}
