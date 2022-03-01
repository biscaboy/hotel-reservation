package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.Scanner;

public class MainMenu extends CliMenu {

    private static final int RESERVE_A_ROOM = 1;
    // TODO: abstract the menu for ease of change?
    // private static final String RESERVE_A_ROOM_MSG = "Find and reserve a room";
    // private static final String RESERVE_A_ROOM_METHOD = "";

    private static final int SEE_MY_RESERVATIONS = 2;
    private static final int CREATE_AN_ACCOUNT = 3;
    private static final int ADMIN = 4;
    private static final int EXIT = 5;

    private static final String menu =
            " =========== MAIN MENU ===========\n" +
            " 1. Find and reserve a room \n" +
            " 2. See my reservations \n" +
            " 3. Create an account \n" +
            " 4. Admin \n" +
            " 5. Exit \n" +
            " =================================";

    private static MainMenu instance;

    private MainMenu() {
        // TODO: This is an idea - menu items as objects.
        //MenuItem item = new MenuItem(RESERVE_A_ROOM,...)
    }

    public static MainMenu getInstance(){
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    public void displayMenu(){
        System.out.println(menu);
    }

    public void doOption(int item, Scanner scanner){

        switch (item) {
            case (RESERVE_A_ROOM) :

                break;
            case (SEE_MY_RESERVATIONS) :
                break;
            case (CREATE_AN_ACCOUNT) :
                break;
        }
    }

}
