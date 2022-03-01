package com.davidjdickinson.jdnd.hotelreservation.cli;

public abstract class CliMenu {

    public static int MIN_OPTION;
    public static int MAX_OPTION;
    public static int EXIT_OPTION = MAX_OPTION;

    abstract void doOption(int optionNumber);
    abstract void displayMenu();

    void displayPrompt(){
        System.out.println(" Please select a number for the menu option");
    }

}
