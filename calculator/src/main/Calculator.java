package main;

public class Calculator {
    public int integerDivision(int dividend, int divisor){
        return dividend/divisor;
    }

    public int integerSubtraction(int minuend,int subtrahend){
        return minuend-subtrahend;
    }

    public double squareRoot(double number){
        if (number<0){
            throw new IllegalArgumentException("Can not calculate the square root of negative number ");
        }
        return Math.sqrt(number);
    }
}
