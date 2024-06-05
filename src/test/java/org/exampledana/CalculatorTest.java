package org.exampledana;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Scanner;

public class CalculatorTest {

    @Test
    public void gettingUnitFromUserWorks(){
        String input1="mm";
        Assertions.assertEquals(Unit.mm, Calculator.getUnitFromUser(new Scanner(input1)));
        String input3="CM";
        Assertions.assertEquals(Unit.cm, Calculator.getUnitFromUser(new Scanner(input3)));
    }

    @Test
    public void gettingExpressionWorks(){
        Assertions.assertEquals("+2km+1m+1dm", Calculator.getExpression(" 2 km + 1 m + 1 dm "));
        Assertions.assertEquals("-20km+100km", Calculator.getExpression(" - 20 km + 100 km "));
    }
    @Test
    public void gettingExpressionWithoutAllWhiteSpacesWorks(){
        Assertions.assertEquals("+2km+1m+1dm", Calculator.getExpression("2km+1m+ 1 dm"));
        Assertions.assertEquals("-20km+1m", Calculator.getExpression("-20km +1 m "));
    }
    @Test
    public void gettingExpressionWithUpperCaseLettersWorks(){
        Assertions.assertEquals("+2km+1m+1dm", Calculator.getExpression("2KM+1m+ 1DM"));
        Assertions.assertEquals("-20km+1m", Calculator.getExpression(" - 20 Km + 1 m"));
    }

    @Test
    public void conversionWorks() {
        Assertions.assertEquals((-1000), Calculator.calculateDistance("-1m", Unit.mm));
    }
    @Test
    public void conversionWithUpperCaseAndLowerCaseLettersWorks() {
        Assertions.assertEquals((2), Calculator.calculateDistance("20cm", Unit.dm));
    }
    @Test
    public void additionWorks() {
        Assertions.assertEquals((10200), Calculator.calculateDistance("10m+20cm", Unit.mm));
    }
    @Test
    public void additionWitWhiteSpacesWorks() {
        Assertions.assertEquals((10200), Calculator.calculateDistance("+10m+20cm", Unit.mm));
    }
    @Test
    public void subtractionWorks() {
        Assertions.assertEquals((0.8), Calculator.calculateDistance("1m-2dm", Unit.m));
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
        Assertions.assertEquals((9), Calculator.calculateDistance("-1m+10m-200cm-1m+3m", Unit.m));
        Assertions.assertEquals((0.01), Calculator.calculateDistance("1km-990m", Unit.km));
        Assertions.assertEquals((1), Calculator.calculateDistance("2dm-10cm", Unit.dm));
    }
    @ParameterizedTest
    @CsvSource({"10m+20cm-10cm, 10100", "2dm-10cm, 100", "1km-200m, 800000", "1cm, 10", "-2mm+4mm, 2"})
    //@CsvSource(value = {"10m+20cm"= 10200}, delimiter = ':')
    public void parameterizedAdditionWorks(String expression, double result){
        Assertions.assertEquals(result, Calculator.calculateDistance(expression, Unit.mm));
    }

}
