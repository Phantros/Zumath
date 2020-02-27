package com.company;

import java.util.ArrayList;

public class SumList {
    public ArrayList<Integer> sumList;
    public ArrayList<Integer> indexList;

    public SumList()
    {
        sumList = new ArrayList<>();
        indexList = new ArrayList<>();
    }

    public void add(int sum, int index)
    {
        sumList.add(sum);
        indexList.add(index);
    }

}
