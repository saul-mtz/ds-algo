package common.tree;

import common.SuffixArray;
import common.SuffixDataStructure;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Test for the Suffix ds data Structure
 */
public class SuffixTreeTest {

    protected SuffixDataStructure ds;

    protected SuffixDataStructure getDataStructure(String word) {
        return new SuffixTreeGraphviz(word);
    }

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {
        ArrayList<String> testWords = new ArrayList<>();
        testWords.add("abcabxabcd");
        testWords.add("banana");
        testWords.add("ABRACADABRA!");
        testWords.add("dedododeeodo");
        testWords.add("abcdefabxybcdmnabcdex");
        testWords.add("abcadak");
        testWords.add("ooooooooo");
        testWords.add("mississippi");

        for (String testWord : testWords) {
            ds = getDataStructure(testWord);
            testOrder((SuffixTree) ds);
        }
    }

    protected void testOrder(SuffixTree tree) {
        ArrayDeque<SuffixTree.TreeNode> queue = new ArrayDeque<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        queue.push(tree.getRoot());

        int i = 0;
        while (!queue.isEmpty()) {
            SuffixTree.TreeNode node = queue.pop();
            if (node.isLeaf()) {
                int index = node.getBeginIndex();
                Assert.assertTrue(index >= 0);
                Assert.assertFalse(indexes.contains(index));
                //System.out.println(i + ", " + index + ", " + tree.substring(index));

                if (!indexes.isEmpty()) {
                    String str1 = tree.substring(indexes.get(i-1));
                    String str2 = tree.substring(index);
                    if (str1.compareTo(str2) >= 0) {
                        System.err.println(str1);
                        System.err.println(str2);
                    }
                    Assert.assertTrue(str1.compareTo(str2) < 0);
                }
                indexes.add(index);
                i ++;
            } else {
                ArrayList<SuffixTree.TreeNode> children = new ArrayList<>(node.getChildNodes());
                for (int j = children.size()-1; j >= 0; j --) {
                    queue.push(children.get(j));
                }
            }
        }

        Assert.assertEquals(tree.length(), indexes.size());
        Runtime rt = Runtime.getRuntime();
        try {
            System.out.printf("Suffix Tree for '%s' is correct.\n", tree);
            String baseName = ((SuffixTreeGraphviz) tree).getGraphvizFileName();
            String command = "dot -Tsvg " + baseName + ".gv -o " + baseName + ".svg";
            rt.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
