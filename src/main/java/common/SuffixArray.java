package common;

/**
 * Suffix Array Data Structure, concepts taken from:
 * https://en.wikipedia.org/wiki/Suffix_array
 *
 * First version taken from the awesome:
 * http://algs4.cs.princeton.edu/63suffix/SuffixArrayX.java.html
 *
 */
public class SuffixArray implements SuffixDataStructure {
    private static final int CUTOFF =  5;   // cutoff to insertion sort (any value between 0 and 12)
    private final char[] textChars;
    private final int[] index;   // index[i] = j means textChars.substring(j) is ith largest suffix
    private final int[] lcp;   // index[i] = j means textChars.substring(j) is ith largest suffix
    private final int n;         // number of characters in textChars
    private static final char SENTINEL = '\0'; // it is assumed that this character will not appear in the textChars

    /**
     * Initializes a suffix array for the given {@code textChars} string.
     * @param text the input string
     */
    public SuffixArray(String text) {
        n = text.length();
        text += SENTINEL;
        this.textChars = text.toCharArray();
        index = new int[n];
        lcp = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        sort(0, n-1, 0);
    }

    @Override
    public boolean find(String word) {
        return -1 != search(word);
    }

    @Override
    public int indexOf(String word) {
        int rank = rank(word);
        if (-1 != rank) {
            int m = word.length();
            while (rank < n-1 && lcp(rank+1) >= m) {
                rank ++;
            }
            return index[rank];
        } else {
            return -1;
        }
    }

    @Override
    public int lastIndexOf(String word) {
        int rank = rank(word);
        return -1 != rank ? index[rank] : -1;
    }

    @Override
    public int substringCount(String word) {
        int count = 0;
        int rank = rank(word);
        if (-1 != rank) {
            int m = word.length();
            count ++;
            while (rank < n-1 && lcp(rank+1) >= m) {
                rank ++;
                count ++;
            }
        }
        return count;
    }

    /**
     * Returns the <em>i-</em>th smallest suffix as a string.
     *
     * @param i the index
     * @return the <em>i</em> smallest suffix as a string
     * @throws java.lang.IndexOutOfBoundsException unless {@code 0 <= i < n}
     */
    public String getSuffix(int i) {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException();
        return new String(textChars, index[i], n - index[i]);
    }

    /**
     * Returns the index into the original string of the <em>i</em>th smallest suffix.
     * That is, {@code textChars.substring(sa.index(i))} is the <em>i</em> smallest suffix.
     * @param i an integer between 0 and <em>n</em>-1
     * @return the index into the original string of the <em>i</em>th smallest suffix
     * @throws java.lang.IndexOutOfBoundsException unless {@code 0 <=i < n}
     */
    public int rank(int i) {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException();
        return index[i];
    }

    private int search(String query) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, index[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            }  else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * Returns the length of the longest common prefix of the <em>i</em>th
     * smallest suffix and the <em>i</em>-1st smallest suffix.
     * @param i an integer between 1 and <em>n</em>-1
     * @return the length of the longest common prefix of the <em>i</em>th
     * smallest suffix and the <em>i</em>-1st smallest suffix.
     * @throws java.lang.IndexOutOfBoundsException unless {@code 1 <= i < n}
     */
    public int lcp(int i) {
        if (i < 1 || i >= n) throw new IndexOutOfBoundsException();
        if (0 == lcp[i]) {
            lcp[i] = lcp(index[i], index[i - 1]);
        }
        return lcp[i];
    }

    public int rank(String query) {
        int rank = search(query);
        int m = query.length();
        if (-1 != rank) {
            while (rank > 0 && lcp(rank) >= m) {
                rank --;
            }
            return rank;
        } else {
            return -1;
        }
    }

    /**
     * Returns the length of the input string.
     * @return the length of the input string
     */
    public int length() {
        return n;
    }

    public String toString() {
        return new String(textChars, 0, n);
    }

    // 3-way string quicksort lo..hi starting at dth character
    private void sort(int lo, int hi, int d) { 

        // cutoff to insertion sort for small sub arrays
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        char v = textChars[index[lo] + d];
        int i = lo + 1;
        while (i <= gt) {
            char t = textChars[index[i] + d];
            if      (t < v) swap(lt++, i++);
            else if (t > v) swap(i, gt--);
            else            i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(lo, lt-1, d);
        if (v > 0) sort(lt, gt, d+1);
        sort(gt+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(index[j], index[j - 1], d); j--) {
                swap(j, j - 1);
            }
        }
    }

    // is textChars[i+d..n) < textChars[j+d..n) ?
    private boolean less(int i, int j, int d) {
        if (i == j) return false;
        i = i + d;
        j = j + d;
        while (i < n && j < n) {
            if (textChars[i] < textChars[j]) return true;
            if (textChars[i] > textChars[j]) return false;
            i++;
            j++;
        }
        return i > j;
    }

    /**
     * Swap two elements
     * @param i index of the first value
     * @param j index of the second value
     */
    private void swap(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    // longest common prefix of textChars[i..n) and textChars[j..n)
    private int lcp(int i, int j) {
        int length = 0;
        while (i < n && j < n) {
            if (textChars[i] != textChars[j]) {
                return length;
            }
            i++;
            j++;
            length++;
        }
        return length;
    }

    // is query < textChars[i..n) ?
    private int compare(String query, int i) {
        int copyI = i;
        int m = query.length();
        int j = 0;
        while (i < n && j < m) {
            if (query.charAt(j) != textChars[i]) {
                return query.charAt(j) - textChars[i];
            }
            i++;
            j++;

        }

        if (j < m) {
            return 1;
        } else {
            return 0;
        }
    }

}
