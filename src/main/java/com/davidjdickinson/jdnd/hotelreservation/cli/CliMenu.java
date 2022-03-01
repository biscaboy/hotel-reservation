package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.Scanner;

public abstract class CliMenu {

    public static int MIN_OPTION;
    public static int MAX_OPTION;
    public static int EXIT_OPTION = MAX_OPTION;

    abstract void doOption(int optionNumber, Scanner scanner);
    abstract void displayMenu();

    void displayPrompt(){
        System.out.println(" Please select a number for the menu option");
    }

}
