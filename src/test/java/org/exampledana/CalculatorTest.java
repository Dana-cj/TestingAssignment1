package org.exampledana;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    public void conversionWorks() {
        Assertions.assertEquals((-1000), Calculator.calculateDistance("-1m", Unit.mm));
    }
    @Test
    public void conversionWithUpperCaseAndLowerCaseLettersWorks() {
        Assertions.assertEquals((2), Calculator.calculateDistance("20 cM", Unit.dm));
    }
    @Test
    public void additionWorks() {
        Assertions.assertEquals((10200), Calculator.calculateDistance("10m+20cm", Unit.mm));
    }
    @Test
    public void additionWitWhiteSpacesWorks() {
        Assertions.assertEquals((10200), Calculator.calculateDistance("+ 10 m + 20 cm", Unit.mm));
    }
    @Test
    public void subtractionWorks() {
       // Assertions.assertEquals((0.8), Calculator.calculateDistance("1m-2dm", Unit.m));
        Assertions.assertEquals((18), Calculator.calculateDistance("-20mm+20cm", Unit.cm));
    }
    @Test
    public void additionAndSubtractionWorks() {
        Assertions.assertEquals((9), Calculator.calculateDistance("-1m+10m-200cm-1m+3m", Unit.m));
        Assertions.assertEquals((0.01), Calculator.calculateDistance("1km-990m", Unit.km));
        Assertions.assertEquals((1), Calculator.calculateDistance("2dm-10cm", Unit.dm));
    }
    @Test
    public void additionAndSubtractionWithWhiteSpacesWorks() {
        Assertions.assertEquals((9), Calculator.calculateDistance("-1 m+10 m-200 cm-1 m+3 m", Unit.m));
        Assertions.assertEquals((0.01), Calculator.calculateDistance(" 1 km - 990 m", Unit.km));
        Assertions.assertEquals((1), Calculator.calculateDistance(" 2 dm -  10 cm ", Unit.dm));
    }
    @ParameterizedTest
    @CsvSource({"10m+20cm-10cm, 10100", "2 dm -  10 cm, 100", "1KM-200m, 800000", "1cm, 10", "-2mm+4mm, 2"})
    //@CsvSource(value = {"10m+20cm"= 10200}, delimiter = ':')
    public void parameterizedAdditionWorks(String expression, double result){
        Assertions.assertEquals(result, Calculator.calculateDistance(expression, Unit.mm));
    }

}
