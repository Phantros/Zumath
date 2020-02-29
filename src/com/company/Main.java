package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // This object contains the array of number nodes and the methods required for handling the array.
    private static NumberList numberList;

    public static void main(String[] args) {
        // write your code here
        numberList = new NumberList();

        Scanner scanner = new Scanner(System.in);
        Random r = new Random();

        // While the array still contains numbers...
        while (numberList.getList().size() != 0) {

            // Generate the next random number to 'shoot' into the array
            int randBoundLow = 0;
            int randBoundHigh = 20;
            int valueToInsert = randBoundLow + (r.nextInt(randBoundHigh - randBoundLow) + 1);
            /** Generate the next target sum to reach
             * This is done by first selecting a random amount of numbers to add together (up to a range of 5),
             * then selecting a random place in the array and adding the consecutive numbers together.
             * This values is then returned, after which we add the next value to 'shoot' to it.
             * This ensures that the user can always make a correct move, but can also make an incorrect move,
             * place the new value into the array on a different index, and not break the level.
             */
            int target = getTarget() + valueToInsert;

            // Show the current list of numbers, the value to insert and the target to reach.
            System.out.println(numberList);
            System.out.println(String.format("Value to insert is: %d", valueToInsert));
            System.out.println(String.format("Target is: %d\n", target));

            // Ask the user to 'shoot' the value to a specific index.
            // Note: Currently only accepts integers, this will done with a cannon in the real game.
            System.out.print("Insert at what index? ");
            int index = scanner.nextInt();

            // Insert the number into the list at the given index.
            numberList.insertAt(index, valueToInsert);

            // Display the list after insertion
            System.out.println(String.format("Inserting at %d...\n\nNew numberlist:", index));
            System.out.println(numberList);

            // Display whether a combo has been found
            System.out.println(String.format("\nCombo found:\t%b", numberList.checkForComboAt(index, target)));

            // Check whether any dodes are dead, and add their values to the string buffer.
            StringBuilder comboBuffer = new StringBuilder("Dead nodes:\t");
            for (NumberNode node : numberList.getList())
            {
                if (!node.isAlive())
                {
                    comboBuffer.append(node.getValue()).append("\t");
                }
            }
            // Display the dead nodes and then remove them from the list.
            System.out.println(String.format("%s\nRemoving dead nodes...\n\nNew Numberlist:", comboBuffer.toString()));
            numberList.removeDeadNodes();
        }
        System.out.println("You cleared the list!");
    }

    /**
     * Generates a randomly generated possible sum of consecutive numbers in the array
     * @return a randomly generated possible sum of consecutive numbers in the array
     */
    public static int getTarget()
    {
        // Get the values from the numberlist object
        int[] values = numberList.getValues();

        Random r = new Random();
        int firstIndex = r.nextInt(values.length);
        int times = 1 + r.nextInt(1);

        int sum = 0;
        // Let the user that the combo starts at this index
        System.out.println("Calculating a random combo that contains index:  " + firstIndex);
        for (int i = 0; i < times; i++)
        {
            int value = 0;
            if (firstIndex + i < values.length)
            {
                value = values[firstIndex + i];
            }
            sum += value;
        }
        return sum;
    }
}
