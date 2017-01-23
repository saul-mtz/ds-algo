package common.tree;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test for the Suffix tree data Structure
 */
public class SuffixTreeTest {

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {

        SuffixTree tree = new SuffixTree("BANANA");
        System.out.println("Prefixes");

        Assert.assertTrue(tree.find("NA"));
        Assert.assertEquals(tree.indexOf("NA"), 2);
        Assert.assertEquals(tree.lastIndexOf("NA"), 4);
        Assert.assertEquals(tree.substringCount("NA"), 2);

        Assert.assertFalse(tree.find("SA"));
        Assert.assertEquals(tree.indexOf("SA"), -1);
        Assert.assertEquals(tree.lastIndexOf("SA"), -1);
        Assert.assertEquals(tree.substringCount("SA"), 0);

        Assert.assertTrue(tree.find("A"));
        Assert.assertEquals(tree.indexOf("A"), 1);
        Assert.assertEquals(tree.lastIndexOf("A"), 5);
        Assert.assertEquals(tree.substringCount("A"), 3);

        Assert.assertTrue(tree.find("B"));
        Assert.assertEquals(tree.indexOf("B"), 0);
        Assert.assertEquals(tree.lastIndexOf("B"), 0);
        Assert.assertEquals(tree.substringCount("B"), 1);
    }

}
