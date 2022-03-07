package com.davidjdickinson.jdnd.hotelreservation.cli;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import com.davidjdickinson.jdnd.hotelreservation.model.FreeRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Room;
import com.davidjdickinson.jdnd.hotelreservation.model.RoomType;

import java.util.*;

/**
 * Administrative Menu for the Hotel Reservation Application
 *
 * This menu displays a menu and processes user selection.
 * @author David Dickinson
 * @version 1.0
 */
public class AdminMenu extends CliMenu {

    private static final int OPTION_ALL_CUSTOMERS = 1;
    private static final int OPTION_SEE_ALL_ROOMS = 2;
    private static final int OPTION_SEE_ALL_RESERVATIONS = 3;
    private static final int OPTION_ADD_A_ROOM = 4;
    private static final int OPTION_EXIT_ADMIN = 5;
    private static final String menu =
            " =========== ADMIN MENU ==========\n" +
            " 1. See all customers \n" +
            " 2. See all rooms \n" +
            " 3. See all reservations \n" +
            " 4. Add a room \n" +
            " 5. Back to main menu \n" +
            "=================================";

    private static final String PROMPT_ENTER_A_ROOM_NUMBER = "Enter a room number: ";
    private static final String PROMPT_ENTER_A_ROOM_RATE = "Enter a room rate (e.g. 200.00): ";
    private static final String PROMPT_ENTER_A_ROOM_TYPE =
            "Enter a room type: ( Enter 1 for SINGLE, 2 for DOUBLE ): ";
    private static final String PROMPT_ADD_ANOTHER_ROOM = "Add another room? (Y or N): ";

    private static final String MSG_NO_RESULTS = "There were no results to display.";
    private static final String MSG_ROOM_EXISTS = "Room number %s already exists.%n";
    private static final String MSG_ROOMS_ADDED_TITLE = "Room(s) you added:";

    private static final String ERROR_ENTRY_NOT_AN_OPTION = "That's not a menu option.  Please try again.";
    private static final String ERROR_ROOM_NUMBER_NOT_IN_RANGE = "Please enter a number between 100 and 999. ";
    private static final String ERROR_ENTRY_IS_NOT_A_NUMBER = "Your entry is not a number. Please try again.";
    private static final String ERROR_TYPE_IS_NOT_VALID = "That's not an option. Please enter 1 for Single or 2 for Double.";

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

        switch (item) {
            case (OPTION_ALL_CUSTOMERS) :
                doShowAll(adminResource.getAllCustomers());
                break;
            case (OPTION_SEE_ALL_ROOMS) :
                doShowAll(adminResource.getAllRooms());
                break;
            case (OPTION_SEE_ALL_RESERVATIONS) :
                doShowAll(adminResource.getAllReservations());
                break;
            case (OPTION_ADD_A_ROOM) :
                adminResource.addRoom(promptForRooms(scanner));
                break;
            case (OPTION_EXIT_ADMIN) :
                break;
            default :
                System.out.println(ERROR_ENTRY_NOT_AN_OPTION);
        }
    }

    private void doShowAll(Collection collection) {
        if (collection.isEmpty()) {
            System.out.println(MSG_NO_RESULTS);
        } else {
            for (Object o : collection) {
                System.out.println(o.toString());
            }
        }
    }

    private List<IRoom> promptForRooms(Scanner scanner) {
        List<IRoom> roomsToAdd = new LinkedList<>();
        while (true) {
            String roomNumber = getRoomNumber(scanner);
            // Does this number already exist or has been entered earlier?
            // check the resource to see if it has been saved
            IRoom room = adminResource.findRoomByNumber(roomNumber);
            if (isRoomNumberUnique(scanner, roomNumber, roomsToAdd)) {
                System.out.printf(MSG_ROOM_EXISTS, roomNumber);
                // do you want to continue?
                if (prompForYesOrNo(scanner, PROMPT_ADD_ANOTHER_ROOM)) {
                    continue;
                } else {
                    break;
                }
            }
            // We have a unique room number
            Double roomRate = getRoomRate(scanner);
            RoomType roomType = getRoomType(scanner);
            if (roomRate > 0.0) {
                room = new Room(roomNumber, roomRate, roomType);
            } else {
                room = new FreeRoom(roomNumber, 0.0, roomType);
            }
            roomsToAdd.add(room);
            if (prompForYesOrNo(scanner, PROMPT_ADD_ANOTHER_ROOM)) {
                continue;
            }
            break;
        }
        // list out the entered rooms
        if (!roomsToAdd.isEmpty()){
            System.out.println(MSG_ROOMS_ADDED_TITLE);
            for (IRoom r : roomsToAdd) {
                System.out.println(r.toString());
            }
        }
        return roomsToAdd;
    }

    private boolean isRoomNumberUnique(Scanner scanner, String roomNumber, Collection<IRoom> rooms) {
        // Does this number already exist or has been entered earlier?
        // check the resource to see if it has been saved
        IRoom room = adminResource.findRoomByNumber(roomNumber);
        boolean roomAlreadySavedOrEntered = (room != null);
        // check the local collection to see if it was already entered.
        if (!roomAlreadySavedOrEntered) {
            for (IRoom enteredRoom : rooms) {
                if (enteredRoom.getRoomNumber().equals(roomNumber)) {
                    roomAlreadySavedOrEntered = true;
                }
            }
        }
        return roomAlreadySavedOrEntered;
    }

    private RoomType getRoomType(Scanner scanner) {
        while (true) {
            System.out.println(PROMPT_ENTER_A_ROOM_TYPE);
            try {
                int type = Integer.parseInt(scanner.nextLine().trim());
                if (type != 1 && type !=2){
                    System.out.println(ERROR_TYPE_IS_NOT_VALID);
                    continue;
                }
                if (type == 1) {
                    return RoomType.SINGLE;
                } else {
                    return RoomType.DOUBLE;
                }
            } catch (InputMismatchException | NumberFormatException exception) {
                System.out.println(ERROR_ENTRY_IS_NOT_A_NUMBER);
            }
        }
    }

    private Double getRoomRate(Scanner scanner) {
        double roomRate = 0.0;
        while (true) {
            System.out.print(PROMPT_ENTER_A_ROOM_RATE);
            try {
                roomRate = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (InputMismatchException | NumberFormatException exception) {
                System.out.println(ERROR_ENTRY_IS_NOT_A_NUMBER);
            }
        }
        return Double.valueOf(roomRate);
    }

    private String getRoomNumber(Scanner scanner) {
        // get the room number
        int roomNumber = 0;
        while (true) {
            System.out.print(PROMPT_ENTER_A_ROOM_NUMBER);
            try {
                roomNumber = Integer.parseInt(scanner.nextLine().trim());
                if (!(roomNumber >= 100 && roomNumber < 1000)) {
                    System.out.println(ERROR_ROOM_NUMBER_NOT_IN_RANGE);
                    continue;
                }
                break;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_ROOM_NUMBER_NOT_IN_RANGE);
            } catch (NumberFormatException numberFormatException) {
                System.out.println(ERROR_ENTRY_IS_NOT_A_NUMBER);
            }
        }
        return String.valueOf(roomNumber);
    }
}