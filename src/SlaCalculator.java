import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

enum Numerals {
    ARABIC, SLAVA
}

public class SlaCalculator {
    private static final Map<String, String> numbers = new HashMap<>();

    static {
        numbers.put("1", "I");
        numbers.put("2", "II");
        numbers.put("3", "III");
        numbers.put("4", "IV");
        numbers.put("5", "V");
        numbers.put("6", "VI");
        numbers.put("7", "VII");
        numbers.put("8", "VIII");
        numbers.put("9", "IX");
        numbers.put("10", "X");
    }

    private static String calc(String input) throws Exception {
        String[] expression = input.split(" ");
        return typeNumerals(expression[0], expression[2]).equals(Numerals.ARABIC) ? arabicCalc(expression[0], expression[2], expression[1])
                : romanCalc(expression[0], expression[2], expression[1]);
    }

    private static Numerals typeNumerals(String value1, String value2) throws Exception {
        if (numbers.containsKey(value1) && numbers.containsKey(value2)) {
            return Numerals.ARABIC;
        } else if (numbers.containsValue(value1)
                && numbers.containsValue(value2)) {
            return Numerals.SLAVA;
        } else {
            throw new Exception();
        }
    }

    private static String arabicCalc(String value1, String value2, String sign) throws Exception {
        int i = Integer.parseInt(value1);
        int j = Integer.parseInt(value2);
        return switch (sign) {
            case "+" -> String.valueOf(i + j);
            case "-" -> String.valueOf(i - j);
            case "/" -> String.valueOf(i / j);
            case "*" -> String.valueOf(i * j);
            default -> throw new Exception();
        };
    }

    private static String romanCalc(String value1, String value2, String sign) throws Exception {
        Optional<String> result1 = numbers.entrySet().stream()
                .filter(entry -> value1.equals(entry.getValue())).map(Map.Entry::getKey)
                .findFirst();
        Optional<String> result2 = numbers.entrySet()
                .stream().filter(entry -> value2.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst();
        int num = Integer.parseInt(arabicCalc(result1.get(), result2.get(), sign));
        return intToRoman(num);
    }

    private static String intToRoman(int num) throws Exception {
        if (num < 1) throw new Exception();
        int[] nums = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNums = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            while (num >= nums[i]) {
                num -= nums[i];
                sb.append(romanNums[i]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String expr = sc.nextLine();
            if (expr.equals("stop")) break;
            else {
                System.out.println(calc(expr));
            }
        }
        sc.close();
    }
}