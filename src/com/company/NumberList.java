package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NumberList {

    /** EXCLUSIVE */
    private static final int BOUND_LOW = 0;
    /** EXCLUSIVE */
    private static final int BOUND_HIGH = 20;
    private static final int INITIAL_LIST_CAPACITY = 40;
    private ArrayList<NumberNode> numberList = new ArrayList<>(INITIAL_LIST_CAPACITY);

    public static int getBoundHigh() {
        return BOUND_HIGH;
    }

    public NumberList()
    {
        Random r = new Random();
        for (int i = 0; i < INITIAL_LIST_CAPACITY; i++) {
            int randValue = BOUND_LOW + (r.nextInt(BOUND_HIGH - BOUND_LOW) + 1);
            numberList.add(i, new NumberNode(randValue));
        }
    }

    public boolean checkForComboAt(int startIndex, int target)
    {
        SumList sumListLeft = new SumList(), sumListRight = new SumList();
        sumListLeft.add(0, -1);
        sumListRight.add(0, -1);
        ShortList sl = findShortList(startIndex, target);
        NumberNode[] shortList = sl.getShortList();

        int leftStartIndex = sl.getStartIndex();
        int rightStartIndex = sl.getStartIndex() + 1;

        int sum = 0;
        for (int i = leftStartIndex; i > -1; i--) {
            sum += shortList[i].getValue();
            sumListLeft.add(sum, i);
        }
        sum = 0;
        for (int i = rightStartIndex; i < shortList.length; i++) {
            sum += shortList[i].getValue();
            sumListRight.add(sum, i);
        }

        for (int i = 0; i < sumListLeft.sumList.size(); i++) {
            for (int j = 0; j < sumListRight.sumList.size(); j++) {
                int current_sum = sumListLeft.sumList.get(i) + sumListRight.sumList.get(j);
                if (current_sum == target)
                {
                    for (int k = 0; k < i + 1; k++)
                    {
                        int index = sumListLeft.indexList.get(k);
                        if (index > -1)
                        {
                            shortList[index].kill();
                        }
                    }
                    for (int k = 0; k < j + 1; k++)
                    {
                        int index = sumListRight.indexList.get(k);
                        if (index > -1)
                        {
                            shortList[index].kill();
                        }
                    }
                    return true;
                }
                else if (current_sum > target)
                {
                    return false;
                }
            }

        }
        return false;
    }

    public void removeDeadNodes()
    {
        ArrayList<Integer> indexesToRemove = new ArrayList<>();
        int index = 0;
        for (NumberNode node : numberList)
        {
            if (!node.isAlive())
            {
                indexesToRemove.add(index);
            }
            index++;
        }

        Collections.reverse(indexesToRemove);

        for (int i : indexesToRemove)
        {
            numberList.remove(i);
        }
    }

    /**
     * Returns an array of the numbers that could potentially form a combo.
     * This eliminates all numbers that can not make up the combo from the original array,
     * to reduce the amount of numbers we have to consider next.
     * @param startIndex the index to look from
     * @param target the target number we're looking for
     * @return an array of the numbers that could potentially form a combo
     */
    private ShortList findShortList(int startIndex, int target)
    {
        // Get values from the list of number nodes
        int[] values = getValues();

        // Create the shortlist of numbers
        ArrayList<NumberNode> tempList = new ArrayList<>();
        // Include all consecutive values to the left of startIndex until they exceed target
        int sum = 0;
        for (int i = startIndex; i > -1; i--) {
            // Add the current value to the sum
            NumberNode node = numberList.get(i);
            sum += node.getValue();
            // If the sum exceeds the target, this number can't extend the combo and we can stop looking.
            if (sum > target)
            {
                break;
            }
            // Else we add it to the shortlist
            tempList.add(node);
        }

        int newStartIndex = tempList.size() - 1;
        // Reverse that list so it's in order of original array
        Collections.reverse(tempList);

        // Now add the numbers right of startIndex
        sum = 0;
        for (int i = startIndex; i < numberList.size(); i++) {
            // Add the current value to the sum
            NumberNode node = numberList.get(i);
            sum += node.getValue();
            // If the sum exceeds the target, this number can't extend the combo and we can stop looking.
            if (sum > target)
            {
                break;
            }
            // Else we add it to the shortlist
            // (if it's not the start index, since that's already included)
            else if (i != startIndex)
            {
                tempList.add(node);
            }
        }

        // Now convert the ArrayList into an array of nodes
        NumberNode[] nodes = new NumberNode[tempList.size()];
        int index = 0;
        for (NumberNode node : tempList)
        {
            nodes[index++] = node;
        }
        return new ShortList(nodes, newStartIndex);
    }

    public int[] getValues()
    {
        int[] values = new int[numberList.size()];
        int index = 0;
        for (NumberNode node : numberList)
        {
            values[index++] = node.getValue();
        }
        return values;
    }

    public ArrayList<NumberNode> getList()
    {
        return numberList;
    }

    /** Insert a new node into the list **/
    public void insertAt(int index, NumberNode node)
    {
        numberList.add(index, node);
    }

    /** Insert a new node into the list **/
    public void insertAt(int index, int value)
    {
        numberList.add(index, new NumberNode(value));
    }

    @Override
    public String toString()
    {
        StringBuilder indexBuffer = new StringBuilder("Indexes:\t");
        StringBuilder valueBuffer = new StringBuilder("Values:\t\t");
        int index = 0;
        char tab = '\t';
        for (NumberNode node : numberList)
        {
            indexBuffer.append(index++).append(tab);
            valueBuffer.append(node.getValue()).append(tab);
        }
        return String.format("%s\n%s", indexBuffer.toString(), valueBuffer.toString());
    }

}
