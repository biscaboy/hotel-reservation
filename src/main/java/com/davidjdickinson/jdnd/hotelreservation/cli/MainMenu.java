package com.davidjdickinson.jdnd.hotelreservation.cli;

import com.davidjdickinson.jdnd.hotelreservation.api.HotelResource;
import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Main Program Menu for the Hotel Reservation Application
 *
 * This menu displays a menu and processes user selection.
 * @author David Dickinson
 * @version 1.0
 */

public class MainMenu extends CliMenu {

    /**
     * Thanks to RegExr for the community patters.
     * The pattern below was found in the community date patters at https://regexr.com/31p85.
     */
    private static Pattern datePattern = Pattern.compile(
            "(0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])" +
            "[\\/\\-]\\d{4}( \\d{1,2}[:-]\\d{2}([:-]\\d{2,3})*)?");
    private static final int OPTION_RESERVE_A_ROOM = 1;

    private static final int OPTION_SEE_MY_RESERVATIONS = 2;
    private static final int OPTION_CREATE_AN_ACCOUNT = 3;
    public static final int OPTION_ADMIN = 4;
    public static final int OPTION_EXIT_PROGRAM = 5;

    public static final String PROMPT_ENTER_ROOM_NUMBER = "Please enter the room number from the list to reserve: ";
    public static final String PROMPT_SAVE_RESERVATION = "Make the reservation? (Enter Y or N): ";
    public static final String PROMPT_ENTER_CHECK_IN_DATE = "Please enter the Check-in date (mm/dd/yyyy): ";
    public static final String PROMPT_ENTER_CHECK_OUT_DATE = "Please enter the Check-out date (mm/dd/yyyy): ";

    public static final String ERROR_DATE_FORMAT = "The date needs to entered as mm/dd/yyyy.";
    public static final String ERROR_ROOM_NUMBER_ENTRY = "Please enter the room number as it appears in the list.";
    public static final String ERROR_NO_ROOMS_AVAILABLE = "There are no rooms available for those dates.";

    private static final String menu =
            " =========== MAIN MENU ===========\n" +
            " 1. Find and reserve a room \n" +
            " 2. See my reservations \n" +
            " 3. Create an account \n" +
            " 4. Admin \n" +
            " 5. Exit \n" +
            " =================================";

    private static MainMenu instance;
    private static HotelResource hotelResource;

    private MainMenu() {
        hotelResource = HotelResource.getInstance();
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
            case (OPTION_RESERVE_A_ROOM) :
                doReserveARoom(scanner);
                break;
            case (OPTION_SEE_MY_RESERVATIONS) :
                doShowReservations(scanner);
                break;
            case (OPTION_CREATE_AN_ACCOUNT) :
                doCreateAccount(scanner);
                break;
        }
    }

    private void doShowReservations(Scanner scanner) {
        String email = promptInput(scanner, "Please enter your email address: ");
        Collection<Reservation> reservations = hotelResource.getCustomerReservations(email);

        if (reservations.isEmpty()) {
            System.out.println("Sorry, no reservations were found for " + email + ".");
        } else {
            String suffix = reservations.size() > 1 ? "s" : "";
            System.out.println("Reservation" + suffix + " found for " + email + ":");
            for (Reservation r : reservations) {
                r.toString();
            }
        }
    }

    private String doCreateAccount(Scanner scanner) {
        String email = promptInput(scanner, "Please enter your email address: ");
        String firstName = promptInput(scanner, "Please enter your first name: ");
        String lastName = promptInput(scanner, "Please enter your last name: ");
        hotelResource.createACustomer(firstName,lastName,email);
        Customer c = hotelResource.getCustomer(email);
        if (c == null) {
            System.out.println("Your account could not be created.\n Please try again.");
        }
        System.out.println("Account Created: " + c.toString());
        return email;
    }

    private void doReserveARoom(Scanner scanner) {
        // prompt the user for dates
        Date checkInDate = promptForDate(scanner, PROMPT_ENTER_CHECK_IN_DATE);
        Date checkOutDate = promptForDate(scanner, PROMPT_ENTER_CHECK_OUT_DATE);

        // check for available rooms
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        IRoom selectedRoom = promptSelectRoom(scanner, availableRooms);
        if (selectedRoom == null) {
            System.out.println("Your reservation was unsuccessful.  Please try again.");
            return;
        }

        String email = promptForAccount(scanner);
        Customer customer = promptForCustomer(scanner, email);
        if (customer == null) {
            System.out.println("Your reservation was unsuccessful.  Please try again.");
            return;
        }

        Reservation reservation = hotelResource.bookARoom(customer, selectedRoom, checkInDate, checkOutDate );
        if (reservation == null) {
            System.out.println("Your reservation was unsuccessful.  Please try again.");
        } else {
            System.out.println("Your reservation was successful.  \nDetails: " + reservation.toString());
        }

    }

    private IRoom promptSelectRoom(Scanner scanner, Collection<IRoom> availableRooms) {
        IRoom selectedRoom = null;

        if (availableRooms.isEmpty())  {
            System.out.println(ERROR_NO_ROOMS_AVAILABLE);
        } else {
            System.out.println("Available rooms: ");
            for (IRoom r : availableRooms) {
                System.out.println(r.toString());
            }

            // select a room unless there is only one
            if (availableRooms.size() > 1) {
                selectedRoom = promptForRoom(scanner, availableRooms);
            } else {
                for (IRoom r : availableRooms) {
                    selectedRoom = r;
                }
            }
            System.out.println("You've selected:  " + selectedRoom.toString());
        }
        return selectedRoom;
    }

    private String promptForAccount(Scanner scanner) {
        String email = null;
        // do you have an account with us?
        boolean hasAccount = prompForYesOrNo(scanner, "Do you have an account (Y or N)? ");

        if (!hasAccount) {
            // create an account
            email = doCreateAccount(scanner);
        } else {
            email = promptInput(scanner, "Please enter your email address: ");
        }
        return email;
    }

    private Customer promptForCustomer(Scanner scanner, String email) {
        Customer customer = null;
        while (customer == null) {
            customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Your email address could not be found.");
                boolean tryAgain = prompForYesOrNo(scanner, "Would you like to try again? (Enter Y or N): ");
                if (tryAgain) {
                    email = promptInput(scanner, "Please enter your email address: ");
                    continue;
                }
            }
        }
        return customer;
    }

    private IRoom promptForRoom(Scanner scanner, Collection<IRoom> availableRooms) {
        while (true) {
            try {
                System.out.print(PROMPT_ENTER_ROOM_NUMBER);
                String roomNumber = scanner.nextLine();
                // did they enter a valid room number?
                for (IRoom room : availableRooms){
                    if (room.getRoomNumber().equals(roomNumber)) {
                        return room;
                    }
                }
                System.out.println("Room " + roomNumber + " is not available.  Please select a room from the list.");

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_ROOM_NUMBER_ENTRY);
            }

        }
    }
    // TODO: only dates in the future.
    private Date promptForDate(Scanner scanner, String prompt) {
        Date result = null;
        while (result == null) {
            try {
                System.out.print(prompt);
                String date = scanner.nextLine();
                // did they enter a well formatted date?
                if (!datePattern.matcher(date).matches()) {
                    System.out.println(ERROR_DATE_FORMAT);
                    continue;
                }
                String [] split = date.split("/");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(split[2]),
                             Integer.parseInt(split[1]),
                             Integer.parseInt(split[0]));
                Date today = new Date();
                Date selected = calendar.getTime();
                if (selected.before(today)) {
                    System.out.println("Please enter a date in the future.");
                    continue;
                }
                result = selected;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_DATE_FORMAT);
            }
        }
        return result;
    }
}
/02/