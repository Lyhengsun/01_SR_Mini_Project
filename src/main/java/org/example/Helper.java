package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Helper {
    static Scanner sc = new Scanner(System.in);

    public enum TYPE {
        PRICE,
        QUANTITY,
        UPDATEID,
        NUM
    }

    public static void errorMessage(String msg) {
        System.out.println(ConsoleColor.ANSI_RED + msg + ConsoleColor.ANSI_RESET);
    }

    public static void inputMessage(String msg) {
        System.out.print(ConsoleColor.ANSI_YELLOW + msg + ConsoleColor.ANSI_RESET);
    }

    public static void successMessage(String msg) {
        System.out.println(ConsoleColor.ANSI_GREEN + msg + ConsoleColor.ANSI_RESET);
    }

    public static void enterInput() {
        System.out.print(ConsoleColor.ANSI_YELLOW + "Enter to continue..." + ConsoleColor.ANSI_RESET);
        sc.nextLine();
    }

    public static boolean continueInput() {
        System.out.print(ConsoleColor.ANSI_YELLOW + "Do you want to continue (Y/N): " + ConsoleColor.ANSI_RESET);
        String input = sc.nextLine();

        while (!Pattern.matches("^[YyNn]$", input)) {
            errorMessage("Invalid input!!! Only Y and N allowed");
            System.out.print(ConsoleColor.ANSI_YELLOW + "Do you want to continue (Y/N): " + ConsoleColor.ANSI_RESET);
            input = sc.nextLine();
        }
        return input.equals("y") || input.equals("Y");
    }

    public static String inputValidation(String temp, TYPE type) {
        switch (type) {
            case UPDATEID -> {
                while (!Pattern.matches("\\d+", temp)) {
                    Helper.errorMessage("Invalid input! Please try again.");
                    Helper.inputMessage("Input ID to update: ");
                    temp = sc.nextLine();
                }
            }
            case PRICE -> {
                while (!Pattern.matches("^([1-9][0-9]{1,5}(\\.\\d{1,2})?)|(0\\.[1-9]{1,2})|(0\\.0[1-9])|([1-9](\\.\\d{1,2})?)$", temp)) {
                    Helper.errorMessage("Invalid!!! Only number, 6 digits and 2 decimals are allowed.");
                    Helper.inputMessage("Enter unit price: ");
                    temp = sc.nextLine();
                }
            }
            case QUANTITY -> {
                while (!Pattern.matches("^\\d{1,8}$", temp)) {
                    Helper.errorMessage("Invalid!!! Only allowed up to 8 digits.");
                    Helper.inputMessage("Enter quantity: ");
                    temp = sc.nextLine();
                }
            }
            case NUM -> {
                while (!Pattern.matches("^[1-5]$", temp)) {
                    Helper.errorMessage("Invalid input!!! Only numbers allowed");
                    Helper.inputMessage("Choose an option to update: ");
                    temp = sc.nextLine();
                }
            }
        }
        return temp;
    }
}

