package learn.ui;

import learn.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO {

    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in MM/dd/yyyy format.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println() {
        System.out.println();
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public DateTimeFormatter getFormater() {
        return formatter;
    }

    public LocalDate readUnrequiredStartLocalDate(String message, Reservation toBeUpdated) {
        String input = readString(message);
        if(input.isBlank() || input.isEmpty()){
            return toBeUpdated.getStartDate();
        }
        while (true){
            while (true) {
                try {
                    return LocalDate.parse(input, formatter);
                } catch (DateTimeParseException ex) {
                    println(INVALID_DATE);
                }
            }
        }
    }

    public LocalDate readUnrequiredEndLocalDate(String message, Reservation toBeUpdated) {
        String input = readString(message);
        if(input.isBlank() || input.isEmpty()){
            return toBeUpdated.getEndDate();
        }
        while (true){
            while (true) {
                try {
                    return LocalDate.parse(input, formatter);
                } catch (DateTimeParseException ex) {
                    println(INVALID_DATE);
                }
            }
        }
    }
}
