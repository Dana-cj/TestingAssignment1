package org.exampledana;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Calculator {

    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        boolean formatObserved= false;
        while (!formatObserved) {
            try {
                System.out.println("""
                        This calculator is capable of computing a metric distance value from an expression that contains different scales and systems (supported units: mm, cm, dm, m, km)
                        Note: You can use upper case letters, lower case letters and white spaces 
                        EXAMPLE: 10 cm + 1 m - 10 mm = 1090 mm 
                        Please type the expression that you want to be computed:
                        """);
                String expression = scan.nextLine();
                System.out.println("You can also choose the unit of the output from the following supported formats: mm, cm, dm, m, km (upper case or lower case letters). Type your option:");
                Unit unit = getUnit(scan);
                calculateDistance(expression, unit);
                formatObserved = true;

            } catch (PatternSyntaxException exception) {
                System.out.println("""
                        Wrong format! The expression should have at least a group that contains:
                                                                            - a symbol '+' or '-'
                                                                            - a value 
                                                                            - an unit
                        """);
            }
        }
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

    static double calculateDistance(String expression, Unit unit) throws PatternSyntaxException{
        if (!expression.startsWith("-")){
            expression="+"+expression;
        }
        Pattern pattern = Pattern.compile("(\\+|\\-)(\\d+)(mm|cm|dm|m|km)");
        Matcher matcher=pattern.matcher(expression.toLowerCase().replace(" ", ""));
        List<String> listOfComputations= new ArrayList<>();
        while (matcher.find()){
            for (int i=1; i<=3; i++) {
                listOfComputations.add(matcher.group(i));
                //System.out.println(matcher.group(i));
            }
        }
        if(listOfComputations.size()==0) {
            throw new PatternSyntaxException("Wrong format of the expression.Try again!","(\\+|\\-)(\\d+)(mm|cm|dm|m|km)", 0);
        }
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
        System.out.println(value);
        return value;
    }
}

