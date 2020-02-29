package com.company;

/**
 * A ShortList contains a list of NumberNodes that are to be considered
 * when looking whether a combo has been found.
 * It also stores the index that it starts at.
 */
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
