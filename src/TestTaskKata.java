import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TestTaskKata {
    static Map<String,Integer> romanArabianDict = new HashMap<String,Integer>()
    {
        {
            put("I", 1);
            put("II", 2);
            put("III", 3);
            put("IV", 4);
            put("V", 5);
            put("VI", 6);
            put("VII", 7);
            put("VIII", 8);
            put("IX", 9);
            put("X", 10);
        }
    };
    static String [] devidersRoman = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
    static int [] devidersArabi = {1, 4, 5, 9, 10, 40, 50, 90, 100};
    static String [] operators = {"+", "-", "*", "/"};


    public static String [] input() {
        Scanner sc = new Scanner(System.in);
        String row = sc.nextLine();
        return row.split(" ");
    }


    public static boolean symbolCheck(String i) {
        try {
            Integer.parseInt(i);
        } catch (NumberFormatException e) {
            if (!romanArabianDict.containsKey(i)) {
                System.out.println("Unexpected symbol");
                return true;}
        }
        if (!romanArabianDict.containsKey(i)) {
            if (Integer.parseInt(i)>10 | Integer.parseInt(i)<1) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("argument not in allowed range");
                    return true;
                }
            }
        }
        return false;
    }


    public static String[] getter() {
        String [] input = input();
        String x = input[0];
        String y = input[2];
        if (symbolCheck(x)) {
            input = getter();
        }
        if (symbolCheck(y)) {
            input = getter();
        }
        if (input.length != 3) {
            System.out.println("Wrong expression length");
            try {
                throw new IOException();
            } catch (IOException e) {
                input = getter();
            }
        }
        if (romanArabianDict.containsKey(x) != romanArabianDict.containsKey(y)) {
            System.out.println("Only one argument is roman digit");
            try {
                throw new IOException();
            } catch (IOException e) {
                input = getter();
            }
        }
        if (!Arrays.asList(operators).contains(input[1])) {
            System.out.println("Wrong operand");
            try {
                throw new IOException();
            } catch (IOException e) {
                input = getter();
            }
        }
        return input;
    }


    public static String romanResult(int remainder, int nextDevider, String romanResult) {
        if (remainder == 0) {
            return romanResult;
        } else {
            int multiplier = remainder / devidersArabi[nextDevider];
            romanResult = romanResult.concat(devidersRoman[nextDevider].repeat(multiplier));
            remainder = remainder - (multiplier * devidersArabi[nextDevider]);
            nextDevider -= 1;
            return romanResult(remainder, nextDevider, romanResult);
        }
    }


    public static int calc(int x, int y, String op) {
        return switch (op) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> x / y;
            default -> throw new IllegalStateException("What?" + op);
        };
    }


    public static String calculate(String firstVal, String op, String secVal) {
        boolean roman = romanArabianDict.containsKey(firstVal);
        int x;
        int y;
        String result = "";
        if (roman) {
            x = romanArabianDict.get(firstVal);
            y = romanArabianDict.get(secVal);
            if ((op.equals("-")) & (x < y)) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("The Roman number system only allows positive numbers as an answer");
                }
            } else {
            result = romanResult(calc(x, y, op), 8, "");
            }
        } else {
           x = Integer.parseInt(firstVal);
           y = Integer.parseInt(secVal);
            result = String.valueOf(calc(x, y, op));
        }
        return result;
    }


    public static void main (String[]args){
       String [] getterOutput = getter();
        String calculatorOutput = calculate(getterOutput[0], getterOutput[1], getterOutput[2]);
        System.out.println(calculatorOutput);
        }
    }