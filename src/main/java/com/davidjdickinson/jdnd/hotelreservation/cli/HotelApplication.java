package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.Scanner;

/**
 * Hotel Reservation Application
 * Udacity Java Programming Nanodegree Project 1
 *
 * This is a command line interface to the application.
 *
 * The program displays a main menu and an admim menu for
 * users to manage a hotel with rooms, customers and reservations.
 *
 * Special thanks to Rohit Jain for answering a question on Scanner.nextInt() and new lines.
 *  See: https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
 *
 * Special thanks to baeldung.com for information on building a jar file with Maven.
 *  See: https://www.baeldung.com/executable-jar-with-maven
 *  
 * @author David Dickinson
 * @version 1.0
 */
public class HotelApplication {

    public static void main(String [] args){

        CliMenu menu = MainMenu.getInstance();

        Scanner scanner = new Scanner(System.in);
        int selectedMenuOption = 0;

        while (selectedMenuOption != CliMenu.OPTION_EXIT) {

            try {
                menu.displayMenu();
                menu.displayMainPrompt();

                selectedMenuOption = Integer.parseInt(scanner.nextLine());

                if (selectedMenuOption < CliMenu.OPTION_MIN || selectedMenuOption > CliMenu.OPTION_MAX) {
                    System.out.println(selectedMenuOption + " is not an option.  Please try again.");
                    continue;
                }

                // if the user selected the admin menu, switch to the admin menu and continue.
                if (menu instanceof MainMenu && selectedMenuOption == MainMenu.OPTION_ADMIN) {
                    menu = AdminMenu.getInstance();
                    selectedMenuOption = 0;
                    continue;
                }

                // if the user is returning from the admin menu, switch back to the main menu and continue
                if (menu instanceof AdminMenu && selectedMenuOption == AdminMenu.OPTION_EXIT) {
                    menu = MainMenu.getInstance();
                    selectedMenuOption = 0;
                    continue;
                }

                // execute the selected option from the menu
                menu.doOption(selectedMenuOption, scanner);

            } catch (Exception ex) {
                System.out.println("Unable to read input.  Please try again.");
                continue;
            }
        }
        scanner.close();
    }

}
