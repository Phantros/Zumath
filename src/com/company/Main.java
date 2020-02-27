package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        NumberList numberList = new NumberList();

        Scanner scanner = new Scanner(System.in);

        Random r = new Random();

        while (numberList.getList().size() != 0) {
            System.out.println(numberList);
            int randTargetBoundLow = 0;
            int randTargetBoundHigh = numberList.getBoundHigh();
            int target = randTargetBoundLow + (r.nextInt(randTargetBoundHigh - randTargetBoundLow) + 1);
            System.out.println(String.format("Target is: %d\n", target));

            int randBoundLow = 0;
            int randBoundHigh = target - 1;
            int valueToInsert = randBoundLow + (r.nextInt(randBoundHigh - randBoundLow) + 1);
            System.out.println(String.format("Value to insert is: %d", valueToInsert));

            System.out.print("Insert at what index? ");
            int index = scanner.nextInt();
            numberList.insertAt(index, valueToInsert);

            System.out.println(String.format("Inserting at %d...\n\nNew numberlist:", index));
            System.out.println(numberList);
            System.out.println(String.format("Combo found:\t%b", numberList.checkForComboAt(index, target)));

            StringBuilder comboBuffer = new StringBuilder("Dead nodes:\t");
            for (NumberNode node : numberList.getList())
            {
                if (!node.isAlive())
                {
                    comboBuffer.append(node.getValue()).append("\t");
                }
            }
            System.out.println(String.format("%s\nRemoving dead nodes...\n\nNew Numberlist:", comboBuffer.toString()));
            numberList.removeDeadNodes();
        }

    }
}
