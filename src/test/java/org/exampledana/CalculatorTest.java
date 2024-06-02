package org.exampledana;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class CalculatorTest {

    @Test
    public void gettingUnitWorks(){
        String input1="mm";
        Assertions.assertEquals(Unit.mm, Calculator.getUnit(new Scanner(input1)));
        String input2="Km";
        Assertions.assertEquals(Unit.km, Calculator.getUnit(new Scanner(input2)));
        String input3="CM";
        Assertions.assertEquals(Unit.cm, Calculator.getUnit(new Scanner(input3)));
    }

    @Test
    public void additionWorks() {
        Assertions.assertEquals((10200), Calculator.calculateDistance("10m+20cm", Unit.mm));
    }
    @Test
    public void additionAndSubtractionWorks() {
        Assertions.assertEquals((9), Calculator.calculateDistance("-1m+10m-200cm-1m+3m", Unit.m));
    }
    @Test
    public void subtractionWorks() {
        Assertions.assertEquals((18), Calculator.calculateDistance("-20mm+20cm", Unit.cm));
    }
//    @ParameterizedTest
//    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
//    @CsvSource(value = {""10m+20cm": 10200", "10m+20m": "30000"}, delimiter = ':')
//    public void parameterizedAdditionWorks(String expression, Unit unit, double result){
//        Assertions.assertEquals(result, Calculator.calculateDistance(expression, Unit.mm));
//    }

}
