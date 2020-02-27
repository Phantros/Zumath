package com.company;

public class ShortList {

    private NumberNode[] nodeList;
    private int startIndex;

    public ShortList(NumberNode[] nodeList, int startIndex)
    {
        this.nodeList = nodeList;
        this.startIndex = startIndex;
    }

    public NumberNode[] getShortList()
    {
        return nodeList;
    }

    public int getStartIndex()
    {
        return startIndex;
    }
}
