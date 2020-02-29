package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A NumberList stores the array of NumberNodes and contains methods to
 * perform on this array.
 */
public class NumberList {

    /** EXCLUSIVE BOUND */
    private static final int BOUND_LOW = 0;
    /** INCLUSIVE BOUND */
    private static final int BOUND_HIGH = 10;

    /** Initial capacity of the list */
    private static final int INITIAL_LIST_CAPACITY = 40;
    /** The list of NumberNodes */
    private ArrayList<NumberNode> numberList = new ArrayList<>(INITIAL_LIST_CAPACITY);

    /**
     * Returns the upper bound of the randomly generated numbers in this numberList.
     * This bound is inclusive.
     * @return the inclusive upper bound of the randomly generated numbers in the numberList.
     */
    public static int getBoundHigh() {
        return BOUND_HIGH;
    }

    /**
     * Constructor function for this Class.
     * Initializes an instance of this Class.
     */
    public NumberList()
    {
        Random r = new Random();
        for (int i = 0; i < INITIAL_LIST_CAPACITY; i++) {
            int randValue = BOUND_LOW + (r.nextInt(BOUND_HIGH - BOUND_LOW) + 1);
            numberList.add(i, new NumberNode(randValue));
        }
    }

    /**
     * Checks if a combo exists in this numberList.
     * @param startIndex the index to check from
     * @param target the target to reach
     * @return true if combo exists, false otherwise.
     */
    public boolean checkForComboAt(int startIndex, int target)
    {
        // SumList objects store a sum and the index they're found on.
        SumList sumListLeft = new SumList(), sumListRight = new SumList();
        sumListLeft.add(0, -1);
        sumListRight.add(0, -1);

        /**
         * Disregard all the numbers that cannot be part of the combo and return
         * the shorter array with numbers that can.
         */
        ShortList sl = findShortList(startIndex, target);
        NumberNode[] shortList = sl.getShortList();

        // Set the index to start counting from for counting towards
        // left and counting towards right.
        int leftStartIndex = sl.getStartIndex();
        int rightStartIndex = sl.getStartIndex() + 1;

        // Create a temporary sum.
        int sum = 0;
        // Go over all numbers in the short list left of (and including) the start index
        for (int i = leftStartIndex; i > -1; i--) {
            // Increase the sum by the current value
            sum += shortList[i].getValue();
            // Store the sum and at what index in the shortList the sum occured
            sumListLeft.add(sum, i);
        }
        // Reset the temporary sum.
        sum = 0;
        // Go over all numbers in the short list right of (and excluding) the start index
        for (int i = rightStartIndex; i < shortList.length; i++) {
            // Increase the sum by the current value
            sum += shortList[i].getValue();
            // Store the sum and at what index in the shortList the sum occured
            sumListRight.add(sum, i);
        }

        // For both sumlists...
        for (int sumListLeftIndex = 0; sumListLeftIndex < sumListLeft.sumList.size(); sumListLeftIndex++) {
            for (int sumListRightIndex = 0; sumListRightIndex < sumListRight.sumList.size(); sumListRightIndex++) {
                // Get the value of the currently selected sums added together
                int current_sum = sumListLeft.sumList.get(sumListLeftIndex) + sumListRight.sumList.get(sumListRightIndex);

                if (current_sum == target)
                {
                    // Found a combo!
                    // Kill all nodes that make up this combination.
                    for (int k = 0; k < sumListLeftIndex + 1; k++)
                    {
                        int index = sumListLeft.indexList.get(k);
                        if (index > -1)
                        {
                            shortList[index].kill();
                        }
                    }
                    for (int k = 0; k < sumListRightIndex + 1; k++)
                    {
                        int index = sumListRight.indexList.get(k);
                        if (index > -1)
                        {
                            shortList[index].kill();
                        }
                    }
                    return true;
                }
                // If the combined sum is above it's target, no combo can be found, so
                // stop looking any further.
                else if (current_sum > target)
                {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Removes dead nodes from the numberList.
     */
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
        ArrayList<NumberNode> shortList = new ArrayList<>();
        // Include all consecutive values to the left of startIndex until they exceed target
        int sum = 0;
        for (int i = startIndex; i > -1; i--) {
            // Add the current value to the sum
            NumberNode node = numberList.get(i);
            sum += node.getValue();
            // If the sum exceeds the target, this number can't be part of the combo and we can stop looking.
            if (sum > target)
            {
                break;
            }
            // Else we add it to the shortlist
            shortList.add(node);
        }

        int newStartIndex = shortList.size() - 1;
        // Reverse that list so it's in order of original array
        Collections.reverse(shortList);

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
                shortList.add(node);
            }
        }

        // Now convert the ArrayList into an array of nodes
        NumberNode[] shortArray = new NumberNode[shortList.size()];
        int index = 0;
        for (NumberNode node : shortList)
        {
            shortArray[index++] = node;
        }
        return new ShortList(shortArray, newStartIndex);
    }

    /**
     * Convert the NumberNode ArrayList to an int array.
     * @return an int array containing the values of the numberList
     */
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

    /**
     * Return the numberList.
     * @return the numberList of this NumberList object.
     */
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

    /**
     * Represents the NumberList object as a string.
     * @return the NumberList object represented as a string
     */
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
