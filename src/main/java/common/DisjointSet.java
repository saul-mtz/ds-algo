package common;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Disjoint set using the definition from:
 * https://en.wikipedia.org/wiki/Disjoint-set_data_structure
 *
 * Basic implementation, good enough to solve some problems,
 * find and union operations need to be optimized
 */
public class DisjointSet<T> {

    private Map<T, List<T>> sets;
    private int size = 0;

    DisjointSet() {
        sets = new HashMap<>();
    }

    public void makeSet(T value) {
        sets.put(value,  new ArrayList<T>(asList(value)));
        size ++;

    }

    public T find(T value) {
        for (T keySet : sets.keySet()) {
            if (sets.get(keySet).contains(value)) {
                return keySet;
            }
        }
        return null;
    }

    public void union(T rootA, T rootB) {
        // both sets are the same, nothing to do
        if (rootA.equals(rootB)) {
            return;
        }

        List<T> setA = sets.get(rootA);
        List<T> setB = sets.get(rootB);

        if (setA.size() >= setB.size()) {
            setA.addAll(setB);
            sets.remove(rootB);
        } else {
            setB.addAll(setA);
            sets.remove(rootA);
        }
    }

    /**
     * The number of elements in all the sets
     */
    public int size() {
        return size;
    }

    /**
     * Number of disjoint sets
     * @return
     */
    public int setsCount() {
        return sets.size();
    }
}