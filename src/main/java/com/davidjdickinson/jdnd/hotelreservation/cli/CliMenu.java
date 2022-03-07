package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Command Line Interface Menu for the Hotel Reservation Application
 *
 * This menu displays a menus, main prompts and handles user input.
 * @author David Dickinson
 * @version 1.0
 */

public abstract class CliMenu {

    public static final int OPTION_INITIALIZE_MENU = -1;
    public static final int OPTION_MIN = 1;
    public static final int OPTION_MAX = 5;
    public static final int OPTION_EXIT = OPTION_MAX;
    public static final int OPTION_MENU = 0;

    static final String ERROR_ENTER_Y_OR_NO = "Please enter Y or N.";
    static final String ERROR_ENTRY_FORMAT = "Your entry could not be read, please try again.";

    static final String MSG_PROMPT_USER = " Please select a number for the menu option -> ";

    abstract void doOption(int optionNumber, Scanner scanner);
    abstract void displayMenu();
    void displayMainPrompt(){
        System.out.print(MSG_PROMPT_USER);
    }

    protected boolean prompForYesOrNo(Scanner scanner, String prompt) {
        boolean response = false;
        while (true) {
            try {
                System.out.print(prompt);
                String yesNoResponse = scanner.nextLine().trim().toUpperCase();
                // did they enter a Y or N?
                if (yesNoResponse.length() != 1 ||
                        (!(yesNoResponse.startsWith("Y") || yesNoResponse.startsWith("N")))) {
                    System.out.println(ERROR_ENTER_Y_OR_NO);
                    continue;
                }
                // Did they say YES?
                if (yesNoResponse.startsWith("Y")) {
                    response = true;
                }
                break;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_ENTER_Y_OR_NO);
            }
        }
        return response;
    }

    protected String promptInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return input;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_ENTRY_FORMAT);
            }

        }
    }
}
