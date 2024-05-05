import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    static String calc(String input) throws Exception {
        String[] tokens = input.split("(?<=\\d)(?=\\D)|(?=\\D)(?<=\\d)|(?<=[*/+-])|(?=[*/+-])");
        if (tokens.length != 3)
            throw new IllegalArgumentException("Неправильный формат выражения");

        int num1, num2;
        boolean isRoman = isRoman(tokens[0]) && isRoman(tokens[2]);
        char operator = tokens[1].charAt(0);

        try {
            if (!isRoman) {
                num1 = Integer.parseInt(tokens[0]);
                num2 = Integer.parseInt(tokens[2]);
            } else {
                num1 = romanToDecimal(tokens[0]);
                num2 = romanToDecimal(tokens[2]);
            }
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
                throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неправильные числа");
        }

        int result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неправильная операция");
        }

        return isRoman ? decimalToRoman(result) : String.valueOf(result);
    }

    static boolean isRoman(String str) {
        return str.matches("[IVXLC]+");
    }

    static int romanToDecimal(String roman) {
        int decimal = 0;
        int lastNumber = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char ch = roman.charAt(i);
            switch (ch) {
                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;
                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;
                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;
                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;
                default:
                    throw new IllegalArgumentException("Неправильные римские цифры");
            }
        }
        return decimal;
    }

    static int processDecimal(int current, int lastNumber, int decimal) {
        if (lastNumber > current) {
            return decimal - current;
        } else {
            return decimal + current;
        }
    }

    static String decimalToRoman(int num) {
        if (num < 1 || num > 100)
            throw new IllegalArgumentException("Недопустимое число для преобразования в римские цифры");

        String[] romanSymbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(romanSymbols[i]);
            }
        }

        return result.toString();
    }
}