package com.davidjdickinson.jdnd.hotelreservation.cli;

import com.sun.tools.javac.Main;

import java.util.Scanner;

public class HotelApplication {

    public static void main(String [] args){

        CliMenu menu = MainMenu.getInstance();

        Scanner scanner = new Scanner(System.in);
        int userInput = 0;

        while (userInput != CliMenu.EXIT_OPTION) {

            try {
                menu.displayMenu();
                menu.displayPrompt();

                userInput = scanner.nextInt();

                if (userInput < CliMenu.MIN_OPTION || userInput > CliMenu.MAX_OPTION) {
                    System.out.println(userInput + " is not an option.  Please try again.");
                    continue;
                }

                if (menu instanceof MainMenu && userInput == MainMenu.EXIT_OPTION) {
                    menu = AdminMenu.getInstance();
                    userInput = 0;
                    continue;
                }

                if (menu instanceof AdminMenu && userInput == AdminMenu.EXIT_ADMIN_OPTION) {
                    menu = MainMenu.getInstance();
                    userInput = 0;
                    continue;
                }

                menu.doOption(userInput);

            } catch (Exception ex) {
                System.out.println("Unable to read input.  Please try again.");
                continue;
            }
        }
        scanner.close();
    }

}
