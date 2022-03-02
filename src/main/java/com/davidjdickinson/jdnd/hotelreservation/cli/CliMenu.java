package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.Scanner;

/**
 * Command Line Interface Menu for the Hotel Reservation Application
 *
 * This menu displays a menus, main prompts and handles user input.
 * @author David Dickinson
 * @version 1.0
 */

public abstract class CliMenu {

    public static int OPTION_MIN = 1;
    public static int OPTION_MAX = 5;
    public static int OPTION_EXIT = OPTION_MAX;

    abstract void doOption(int optionNumber, Scanner scanner);
    abstract void displayMenu();
    void displayMainPrompt(){
        System.out.println(" Please select a number for the menu option");
    }
}
