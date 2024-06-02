package org.exampledana;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        System.out.println("""
        This calculator is capable of computing a metric distance value from an expression that contains different scales and systems (supported units: mm, cm, dm, m, km)
        EXAMPLE: 10 cm + 1 m - 10 mm = 1090 mm
        Please type the expression that you want to be computed:
        """);
        String expression = scan.nextLine();
        System.out.println("You can also choose the unit of the output from the following supported formats: mm, cm, dm, m, km. Type your option:");
        Unit unit = getUnit(scan);
        calculateDistance(expression, unit);
        //calculateDistance("20m-10cm+2m-4m", Unit.mm);
    }

    static Unit getUnit(Scanner scan) {
        Unit unit=null;
        do {
            try {
                unit = Unit.valueOf(scan.nextLine().toLowerCase().replace(" ", ""));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid unit! Try again:");
            }
        } while (unit==null);
        return unit;
    }

    static double calculateDistance(String expression, Unit unit){
        if (!expression.startsWith("-")){
            expression="+"+expression;
        }
        Pattern pattern = Pattern.compile("(\\+|\\-)(\\d+)(mm|cm|dm|m|km)");
        Matcher matcher=pattern.matcher(expression);
        List<String> listOfComputations= new ArrayList<>();
        while (matcher.find()){
            for (int i=1; i<=3; i++) {
                listOfComputations.add(matcher.group(i));
                //System.out.println(matcher.group(i));
            }
        }
        //System.out.println(listOfComputations);
        double value=0;
        for (int i=0; i<=listOfComputations.size()-3; i++){
            if(listOfComputations.get(i).equals("+")){
                Unit unit1 = Unit.valueOf(listOfComputations.get(i+2));
                value+=Double.parseDouble(listOfComputations.get(i+1))*(unit1.getValue());
            }
            if(listOfComputations.get(i).equals("-")) {
                Unit unit1 = Unit.valueOf(listOfComputations.get(i+2));
                value-=Double.parseDouble(listOfComputations.get(i+1))*(unit1.getValue());
            }
        }
        value/= unit.getValue();
        // System.out.println(value);
        return value;
    }
}

