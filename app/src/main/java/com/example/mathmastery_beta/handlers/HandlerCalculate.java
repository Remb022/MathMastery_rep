package com.example.mathmastery_beta.handlers;

public class HandlerCalculate {
    public double calculateResult(int num1, int num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return (double) num1 / num2;
            case "%":
                return num1 % num2;
            case "^":
                return Math.pow(num1, num2);
            default:
                throw new IllegalArgumentException("Not Operation Found " + operation);
        }
    }
}
