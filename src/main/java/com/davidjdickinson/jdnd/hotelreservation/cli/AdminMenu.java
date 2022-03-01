package com.davidjdickinson.jdnd.hotelreservation.cli;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import java.util.Scanner;

public class AdminMenu extends CliMenu {


    public static final int SEE_ALL_CUSTOMERS_OPTION = 1;
    public static final int SEE_ALL_ROOMS_OPTION = 2;
    public static final int SEE_ALL_RESERVATIONS_OPTION = 3;
    public static final int ADD_A_ROOM_OPTION = 4;
    public static final int EXIT_ADMIN_OPTION = 5;
    private static final String menu =
            " 1. See all customers \n" +
            " 2. See all rooms \n" +
            " 3. See all reservations \n" +
            " 4. Add a room \n" +
            " 5. Back to main menu";

    private static AdminMenu instance;

    private AdminResource adminResource;

    private AdminMenu() {
        adminResource = AdminResource.getInstance();
    }

    public static AdminMenu getInstance(){
        if (instance == null) {
            instance = new AdminMenu();
        }
        return instance;
    }

    public void displayMenu(){
        System.out.println(menu);
    }

    public void doOption(int item, Scanner scanner){

        // TODO: Add these actions.
        switch (item) {
            case (SEE_ALL_CUSTOMERS_OPTION) :
                break;
            case (SEE_ALL_ROOMS_OPTION) :
                break;
            case (SEE_ALL_RESERVATIONS_OPTION) :
                break;
            case (ADD_A_ROOM_OPTION) :
                // get user input (room, rate, type)

                break;
        }
    }
}