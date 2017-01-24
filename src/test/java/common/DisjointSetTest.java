package common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Suffix tree data Structure
 */
public class DisjointSetTest {

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {
        DisjointSet<Integer> disjointSet = new DisjointSet<>();

        Assert.assertNull(disjointSet.find(1));
        Assert.assertEquals(disjointSet.size(), 0);
        Assert.assertEquals(disjointSet.setsCount(), 0);

        disjointSet.makeSet(1);
        Assert.assertEquals(disjointSet.size(), 1);
        Assert.assertEquals(disjointSet.setsCount(), 1);
        Assert.assertNotNull(disjointSet.find(1));
        Assert.assertEquals(Integer.valueOf(1), disjointSet.find(1));

        disjointSet.makeSet(2);
        Assert.assertEquals(disjointSet.size(), 2);
        Assert.assertEquals(disjointSet.setsCount(), 2);
        Assert.assertNotNull(disjointSet.find(2));
        Assert.assertEquals(Integer.valueOf(2), disjointSet.find(2));

        disjointSet.union(1, 2);
        Assert.assertEquals(disjointSet.size(), 2);
        Assert.assertEquals(disjointSet.setsCount(), 1);
        Assert.assertNotNull(disjointSet.find(1));
        Assert.assertNotNull(disjointSet.find(2));
        Assert.assertEquals(Integer.valueOf(1), disjointSet.find(1));
        Assert.assertEquals(Integer.valueOf(1), disjointSet.find(2));
    }

}
