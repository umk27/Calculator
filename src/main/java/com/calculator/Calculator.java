package com.calculator;

import java.util.*;

public class Calculator {

    private static final List<Character> operations = new ArrayList<>(List.of('+', '-', '*', '/'));
    private static final Map<Character, Integer> romanDigits = new HashMap<>(Map.of('I', 1, 'V', 5, 'X', 10));

    static class Operand {
        int value;
        String type;

        public Operand() {
        }

        public Operand(int value, String type) {
            this.value = value;
            this.type = type;
        }

    }

    public static void calculate() throws Exception {

        System.out.println("Введите математическую операцию над числами в одну строку" +
                " в виде a(+, -, /, *)b например 1+1");

        Scanner scanner = new Scanner(System.in);
        String scan = "";
        if (scanner.hasNextLine()) {
            scan = scanner.nextLine().replaceAll(" ", "");
        } else {
            throw new Exception("формат математической операции не удовлетворяет заданию" +
                    " - два операнда и один оператор (+, -, /, *)");
        }


        String oper = "";
        String[] operandsStr = new String[2];
        String operand = "";


        for (char c : scan.toCharArray()) {
            if (operations.contains(c)) {
                if (!oper.equals("") || operand.equals("")) {
                    throw new Exception("формат математической операции не удовлетворяет заданию" +
                            " - два операнда и один оператор (+, -, /, *)");
                }
                oper = oper + c;
                operandsStr[0] = operand;
                operand = "";
            } else {
                operand = operand + c;
            }
        }
        if (operand.equals("") || operandsStr[0] == null) {
            throw new Exception("формат математической операции не удовлетворяет заданию" +
                    " - два операнда и один оператор (+, -, /, *)");
        }
        operandsStr[1] = operand;

        Operand operand1 = buildOperand(operandsStr[0]);
        Operand operand2 = buildOperand(operandsStr[1]);

        if (operand1.value <= 0 || operand1.value > 10 || operand2.value <= 0 || operand2.value > 10) {
            throw new Exception("калькулятор принимает числа только от 1 до 10");
        }

        if (!operand1.type.equals(operand2.type)) {
            throw new Exception("используются одновременно разные системы счисления");
        }

        int result = 0;
        String romanResult = "";
        switch (operations.indexOf(oper.toCharArray()[0])) {
            case 0:
                result = operand1.value + operand2.value;
                if (operand1.type.equals("Arab")) {
                    System.out.println("--------------");
                    System.out.println("Результат сложения: " + result);
                } else {
                    romanResult = buildRomanResult(result);
                    System.out.println("--------------");
                    System.out.println("Результат сложения: " + romanResult);
                }
                break;
            case 1:
                result = operand1.value - operand2.value;
                if (operand1.type.equals("Arab")) {
                    System.out.println("--------------");
                    System.out.println("Результат вычитания: " + result);
                } else {
                    if (result <= 0) {
                        throw new Exception("римские числа не принимают" +
                                " отрицательные значения и 0");
                    }
                    romanResult = buildRomanResult(result);
                    System.out.println("--------------");
                    System.out.println("Результат вычитания: " + romanResult);
                }
                break;
            case 2:
                result = operand1.value * operand2.value;
                if (operand1.type.equals("Arab")) {
                    System.out.println("--------------");
                    System.out.println("Результат умножения: " + result);
                } else {
                    romanResult = buildRomanResult(result);
                    System.out.println("--------------");
                    System.out.println("Результат умножения: " + romanResult);
                }
                break;
            case 3:
                result = operand1.value / operand2.value;
                if (operand1.type.equals("Arab")) {
                    System.out.println("--------------");
                    System.out.println("Результат деления: " + result);
                } else {
                    romanResult = buildRomanResult(result);
                    System.out.println("--------------");
                    System.out.println("Результат деления: " + romanResult);
                }
                break;
        }


    }

    private static Operand buildOperand(String operandsStr) throws Exception {
        char[] operandCh = operandsStr.toCharArray();
        int romanInt = 0;
        if (Character.isDigit(operandCh[0])) {
            if (operandCh.length > 1 && !Character.isDigit(operandCh[1])) {
                throw new Exception("недопустимое значение операнда");
            }
            return new Operand(Integer.parseInt(operandsStr), "Arab");
        } else {
            for (int i = 0; i <= operandCh.length - 2; i++) {
                if (romanDigits.containsKey(operandCh[i])) {
                    int romanDigit1 = romanDigits.get(operandCh[i]);
                    int romanDigit2 = romanDigits.get(operandCh[i + 1]);
                    if (romanDigit2 <= romanDigit1) {
                        romanInt = romanInt + romanDigit1;
                    } else {
                        romanInt = romanInt - romanDigit1;
                    }

                } else {
                    throw new Exception("недопустимое значение операнда");
                }

            }
            romanInt = romanInt + romanDigits.get(operandCh[operandCh.length - 1]);
        }
        return new Operand(romanInt, "Roman");
    }

    private static String buildRomanResult(int result) {
        String romanResult = "";
        if (result == 100) {
            romanResult = "C";
            result = 0;
        }
        if (result >= 90) {
            romanResult = romanResult + "XC";
            result = result - 90;
        }
        if (result >= 50) {
            romanResult = romanResult + "L";
            result = result - 50;
        }
        if (result >= 40) {
            romanResult = romanResult + "XL";
            result = result - 40;
        }
        while (result >= 10) {
            romanResult = romanResult + "X";
            result = result - 10;
        }
        while (result >= 5) {
            if (result == 9) {
                romanResult = romanResult + "IX";
                result = 0;
            } else {
                romanResult = romanResult + "V";
                result = result - 5;
            }
        }
        while (result >= 1) {
            if (result == 4) {
                romanResult = romanResult + "IV";
                result = 0;
            } else {
                romanResult = romanResult + "I";
                result = result - 1;
            }
        }

        return romanResult;
    }
}
