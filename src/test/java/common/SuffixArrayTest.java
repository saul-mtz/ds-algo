package common;

import common.tree.SuffixTreeTest;
import org.junit.Assert;
import org.junit.Test;

public class SuffixArrayTest extends SuffixTreeTest {

    protected SuffixDataStructure getDataStructure(String word) {
        return new SuffixArray(word);
    }

    @Test
    public void testSuffixArray() {
        String s = "ABRACADABRA!";
        SuffixArray suffixArray = (SuffixArray) getDataStructure(s);

        System.out.println(" rank | ind | lcp | indexOf | lastIndexOf | suffix");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffixArray.rank(i);
            int rank = suffixArray.rank(s.substring(index));
            String suffix = suffixArray.getSuffix(i);

            Assert.assertEquals(i, rank);
            Assert.assertEquals(s.substring(index), suffix);
            Assert.assertEquals(index, suffixArray.lastIndexOf(suffix));

            String lcp  = 0 == i ? "-" : String.valueOf(suffixArray.lcp(i));
            System.out.printf(" %4d | %3d | %3s | %7d | %11d | \"%s\"\n", rank, index, lcp, suffixArray.indexOf(suffix), suffixArray.lastIndexOf(suffix), suffix);
        }
    }

}
