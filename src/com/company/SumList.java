package com.company;

import java.util.ArrayList;

/**
 * A SumList stores an array of sums and their respective indexes.
 */
public class SumList {

    public ArrayList<Integer> sumList;
    public ArrayList<Integer> indexList;

    public SumList()
    {
        sumList = new ArrayList<>();
        indexList = new ArrayList<>();
    }

    /**
     * Add a sum and it's respective index in the o
     * @param sum the sum to be added
     * @param index
     */
    public void add(int sum, int index)
    {
        sumList.add(sum);
        indexList.add(index);
    }

}
