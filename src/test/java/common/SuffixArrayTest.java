package common;

import common.tree.SuffixTreeNaiveTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SuffixArrayTest extends SuffixTreeNaiveTest {

    private ArrayList columns;
    protected SuffixDataStructure getDataStructure(String word) {
        return new SuffixArray(word);
    }

    @Test
    public void testSuffixArray() {
        ArrayList<String> testWords = new ArrayList<>();
        testWords.add("abcabxabcd");
        testWords.add("dedododeeodo");
        testWords.add("banana");
        testWords.add("ABRACADABRA!");
        testWords.add("abcdefabxybcdmnabcdex");
        testWords.add("abcadak");
        testWords.add("ooooooooo");
        testWords.add("mississippi");

        for (String testWord : testWords) {
            SuffixArray suffixArray = (SuffixArray) getDataStructure(testWord);

            // number of elements in the suffix array
            testBasicProperties(testWord, suffixArray);
            System.out.println(getHeader(suffixArray));

            for (int i = 0; i < testWord.length(); i++) {
                int rank = suffixArray.rank(i);
                //int rank = suffixArray.rank(suffixArray.substring(index));
                String suffix = suffixArray.getSuffix(i);

                //Assert.assertEquals(i, rank);
                //Assert.assertEquals(testWord.substring(rank), suffix);
                //Assert.assertEquals(index, suffixArray.lastIndexOf(suffix));

                String lcp = 0 == i ? "-" : String.valueOf(suffixArray.lcp(i));
                System.out.printf(getFormat(suffixArray), rank, i, lcp, suffixArray.indexOf(suffix), suffixArray.lastIndexOf(suffix), suffix);
            }
            System.out.println("");
        }
    }

    private void testBasicProperties(String s, SuffixArray sa) {
        Assert.assertEquals(s.length(), sa.length());

        ArrayList<Integer> temp = new ArrayList<>();
        // are all the suffixes in the suffix array?
        for (int i = 0; i < s.length(); i ++) {
            try {
                int index = sa.get(i);
                Assert.assertFalse(temp.contains(index));
                Assert.assertTrue(s.substring(index).equals(sa.getSuffix(i)));

                if (i > 0) {
                    Assert.assertTrue(sa.getSuffix(i).compareTo(sa.getSuffix(i-1)) > 0);
                }
                temp.add(index);
            } catch (Exception e) {
                Assert.assertTrue(i < 0 || i > s.length());
            }
        }
    }


    private String getHeader(SuffixArray suffixArray) {
        if (null == columns) {
            columns = new ArrayList();
            columns.add("rank");
        }

        return " rank | ind | lcp | indexOf | lastIndexOf | suffix" +
                "\n----------------------------------------------------------";
    }

    private String getFormat(SuffixArray suffixArray) {
        return " %4d | %3d | %3s | %7d | %11d | \"%s\"\n";
    }

}
