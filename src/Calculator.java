import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {

    public static void main(String[] args) {

        // прочли строку
        Scanner con = new Scanner(System.in);
        String input = con.nextLine();
        String[] numbers = input.split(" ");

        // получили и напечатали результат, обработали при этом ошибку
        try {
            String res = solve(numbers);
            System.out.println(res);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // непосредственно решение
    static String solve(String[] numbers) throws Exception {

        // проверяем длину массива, если меньше или больше трех - бросаем исключение и завершаем работу
        if (numbers.length != 3) {
            throw new Exception("Некорректно введенные данные");
        }

        String first_num;
        String sec_num;

        // проверили формат, привели числа к арабской записи
        boolean rome = false;
        if (match_rome(numbers)) {
            rome = true;
            first_num = to_arab(numbers[0]);
            sec_num = to_arab(numbers[2]);
        } else if (match_arab(numbers)) {
            first_num = numbers[0];
            sec_num = numbers[2];
        } else {
            throw new Exception("Некорректно введенные данные");
        }

        //вычислили результат
        double first = Double.parseDouble(first_num);
        double sec = Double.parseDouble(sec_num);
        double res;
        switch (numbers[1]) {
            case "+" -> res = first + sec;
            case "-" -> res = first - sec;
            case "*" -> res = first * sec;
            case "/" -> res = first / sec;
            default -> throw new Exception("Некорректно введенные данные");
        }

        // выдали окончательный ответ
        String answer;
        if (rome) {
            answer = to_rome((int) res);
        } else {
            answer = Integer.toString((int) res);
        }
        return answer;
    }

    // являются ли оба числа арабскими
    static boolean match_arab(String[] numbers) {
        String first_num = numbers[0];
        String sec_num = numbers[2];
        String arab_pattern = "^(10)|[1-9]$";
        return Pattern.matches(arab_pattern, first_num)
                & Pattern.matches(arab_pattern, sec_num);
    }

    // являются ли оба числа римскими
    static boolean match_rome(String[] numbers) {
        String first_num = numbers[0];
        String sec_num = numbers[2];
        String rome_pattern = "^X|IX|VI{0,3}|IV|I{0,3}$";
        return Pattern.matches(rome_pattern, first_num)
                & Pattern.matches(rome_pattern, sec_num);
    }

    // перевод римских чисел в арабские
    static String to_arab(String num) {
        return switch (num) {
            case "I" -> "1";
            case "II" -> "2";
            case "III" -> "3";
            case "IV" -> "4";
            case "V" -> "5";
            case "VI" -> "6";
            case "VII" -> "7";
            case "VIII" -> "8";
            case "IX" -> "9";
            case "X" -> "10";
            default -> "";
        };
    }

    // переписываем арабское число в римское
    static String to_rome(int num) {
        String res = "";

        if (num < 0) {
            res += "-";
            num = -num;
        }

        if (num == 100) return res + "C";

        // сначала десятки
        switch (num / 10) {
            case 1 -> res += "X";
            case 2 -> res += "XX";
            case 3 -> res += "XXX";
            case 4 -> res += "XL";
            case 5 -> res += "L";
            case 6 -> res += "LX";
            case 7 -> res += "LXX";
            case 8 -> res += "LXXX";
            case 9 -> res += "XC";
        }

        // потом единицы
        switch (num % 10) {
            case 1 -> res += "I";
            case 2 -> res += "II";
            case 3 -> res += "III";
            case 4 -> res += "IV";
            case 5 -> res += "V";
            case 6 -> res += "VI";
            case 7 -> res += "VII";
            case 8 -> res += "VIII";
            case 9 -> res += "IX";
        }

        return res;
    }
}

