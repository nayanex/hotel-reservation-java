package utils;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static <T> T getInput(String prompt, Class<T> type) {
        T input = null;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println(prompt);
            String userInput = scanner.nextLine();

            try {
                if (type == Integer.class) {
                    input = type.cast(Integer.parseInt(userInput));
                } else if (type == Double.class) {
                    input = type.cast(Double.parseDouble(userInput));
                } else if (type == String.class) {
                    input = type.cast(userInput);
                } else if (type.isEnum()) {
                    input = type.cast(Enum.valueOf((Class<? extends Enum>) type, userInput.toUpperCase()));
                }

                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid " + type.getSimpleName() + ".");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid value for " + type.getSimpleName() + ".");
            }
        }

        return input;
    }

    public static String getEmailInput(String prompt) {
        String input = null;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println(prompt);
            String userInput = scanner.nextLine();

            if (isValidEmail(userInput)) {
                input = userInput;
                isValidInput = true;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }

        return input;
    }

    private static boolean isValidEmail(String email) {
        // Regular expression pattern for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }

    public static Date getDateInput(String prompt, String dateFormat) {
        Date input = null;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println(prompt);
            String userInput = scanner.nextLine();

            try {
                input = parseDate(userInput, dateFormat);
                isValidInput = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format: " + dateFormat);
            }
        }

        return input;
    }

    private static Date parseDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // Ensure strict date parsing

        return sdf.parse(dateString);
    }
}
