package org.exampledana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static boolean formatObserved = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (!formatObserved) {
            printDescription();
            String expression = scan.nextLine();
            printUnitMenu();
            Unit unit = getUnitFromUser(scan);
            calculateDistance(expression, unit);
        }
    }

    static void printDescription() {
        System.out.println("""
                This calculator is capable of computing a metric distance value from an expression that contains different scales and systems (supported units: mm, cm, dm, m, km)
                Note: You can use upper case letters, lower case letters and white spaces 
                EXAMPLE: 10 cm + 1 m - 10 mm = 1090 mm 
                Please type the expression that you want to be computed:
                """);
    }

    private static void printUnitMenu() {
        System.out.println("You can also choose the unit of the output from the following supported formats: mm, cm, dm, m, km (upper case or lower case letters). Type your option:");
    }

    static Unit getUnitFromUser(Scanner scan) {
        Unit unit = null;
        try {
            unit = Unit.valueOf(scan.nextLine().toLowerCase().replace(" ", ""));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid unit! The result will be converted using the lowest unit found in the expression.");
        }
        return unit;
    }

    static double calculateDistance(String expression, Unit unit) {
        expression = getExpression(expression);

        int numberOfCharacters = expression.length();
        int registeredCharacters = 0;
        List<String> listOfComputations = new ArrayList<>();

        //mach pattern
        Pattern pattern = Pattern.compile("(\\+|\\-)(\\d+)(mm|cm|dm|m|km)");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            for (int i = 1; i <= 3; i++) {
                listOfComputations.add(matcher.group(i));
            }
        }
        for (String str : listOfComputations) {
            registeredCharacters += str.length();
        }

        //check if all characters in the expression have the right format
        if (registeredCharacters != numberOfCharacters) {
            System.out.println("""
                    Wrong format! The expression should have at least a group that contains:
                                                                        - a symbol '+' or '-'
                                                                        - a value
                                                                        - an unit
                    Try again!
                    """);
            listOfComputations.clear();
        } else {
            formatObserved = true;
        }

        //get final unit
        if (unit == null) {
            if (!listOfComputations.isEmpty()) {
                List<Unit> listOfUnits = new ArrayList<>();
                for (int i = 2; i < listOfComputations.size(); i += 3) {
                    listOfUnits.add(Unit.valueOf(listOfComputations.get(i)));
                }
                Collections.sort(listOfUnits);
                unit = listOfUnits.get(0);
            }
        }

        // calculate distance
        double value = 0;
        for (int i = 0; i < listOfComputations.size(); i++) {
            if (listOfComputations.get(i).equals("+")) {
                Unit unit1 = Unit.valueOf(listOfComputations.get(i + 2));
                value += Double.parseDouble(listOfComputations.get(i + 1)) * (unit1.getValue());
            }
            if (listOfComputations.get(i).equals("-")) {
                Unit unit1 = Unit.valueOf(listOfComputations.get(i + 2));
                value -= Double.parseDouble(listOfComputations.get(i + 1)) * (unit1.getValue());
            }
        }
        value /= unit.getValue();
        System.out.println("Result= " + value + " " + unit);
        return value;
    }

    static String getExpression(String expression) {
        expression = expression.toLowerCase().replace(" ", "");
        if (!expression.startsWith("-") && !expression.startsWith("+")) {
            expression = "+" + expression;
        }
        return expression;
    }
}