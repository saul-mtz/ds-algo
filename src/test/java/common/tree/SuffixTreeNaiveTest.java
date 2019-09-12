package common.tree;

import common.SuffixDataStructure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Suffix ds data Structure
 */
public class SuffixTreeNaiveTest {

    protected SuffixDataStructure ds;

    protected SuffixDataStructure getDataStructure(String word) {
        return new SuffixTreeNaive(word);
    }

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {
        ds = getDataStructure("BANANA");
        Assert.assertEquals(ds.length(), 6);

        Assert.assertTrue(ds.find("NA"));
        Assert.assertEquals(ds.indexOf("NA"), 2);
        Assert.assertEquals(ds.lastIndexOf("NA"), 4);
        Assert.assertEquals(ds.substringCount("NA"), 2);

        Assert.assertFalse(ds.find("SA"));
        Assert.assertEquals(ds.indexOf("SA"), -1);
        Assert.assertEquals(ds.lastIndexOf("SA"), -1);
        Assert.assertEquals(ds.substringCount("SA"), 0);

        Assert.assertTrue(ds.find("A"));
        Assert.assertEquals(ds.indexOf("A"), 1);
        Assert.assertEquals(ds.lastIndexOf("A"), 5);
        Assert.assertEquals(ds.substringCount("A"), 3);

        Assert.assertTrue(ds.find("B"));
        Assert.assertEquals(ds.indexOf("B"), 0);
        Assert.assertEquals(ds.lastIndexOf("B"), 0);
        Assert.assertEquals(ds.substringCount("B"), 1);
    }

}
