package com.davidjdickinson.jdnd.hotelreservation.cli;

import com.davidjdickinson.jdnd.hotelreservation.api.HotelResource;
import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;
import com.davidjdickinson.jdnd.hotelreservation.model.ReservationDate;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Main Program Menu for the Hotel Reservation Application
 *
 * This menu displays a menu and processes user selection.
 *
 * ===============================================
 * Credits - sources of my learning in this class:
 * ===============================================
 * Excption Handling -
 *      https://docs.oracle.com/javase/7/docs/technotes/guides/language/catch-multiple.html
 * RegEx  - https://regexr.com/31p85
 * Comparing Dates - https://www.javatpoint.com/how-to-compare-dates-in-java
 * Date vs Calendar - https://stackoverflow.com/questions/1404210/java-date-vs-calendar
 *
 * @author David Dickinson
 * @version 1.0
 */

public class MainMenu extends CliMenu {

    /**
     * Thanks to RegExr for the community patters.
     * The pattern below was found in the community date patters at https://regexr.com/31p85.
     */
    private static final Pattern datePattern = Pattern.compile(
            "(0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])" +
            "[\\/\\-]\\d{4}( \\d{1,2}[:-]\\d{2}([:-]\\d{2,3})*)?");

    private static final int OPTION_RESERVE_A_ROOM = 1;
    private static final int OPTION_SEE_MY_RESERVATIONS = 2;
    private static final int OPTION_CREATE_AN_ACCOUNT = 3;
    public static final int OPTION_ADMIN = 4;

    public static final String PROMPT_ENTER_ROOM_NUMBER = "Please enter the room number from the list to reserve: ";
    public static final String PROMPT_SAVE_RESERVATION = "Make the reservation? (Enter Y or N): ";
    public static final String PROMPT_ENTER_CHECK_IN_DATE = "Please enter the Check-in date (mm/dd/yyyy): ";
    public static final String PROMPT_ENTER_CHECK_OUT_DATE = "Please enter the Check-out date (mm/dd/yyyy): ";
    public static final String PROMPT_ENTER_EMAIL = "Please enter your email address: ";
    public static final String PROMPT_ENTER_FIRST_NAME = "Please enter your first name: ";
    public static final String PROMPT_ENTER_LAST_NAME = "Please enter your last name: ";
    public static final String PROMPT_HAVE_ACCOUNT = "Do you have an account (Y or N)? ";
    public static final String PROMPT_TRY_AGAIN = "Would you like to try again? (Enter Y or N): ";

    public static final String MSG_RESERVATION_NOT_FOUND = "Sorry, no reservations were found for %s.%n";
    public static final String MSG_RESERVATION_FOUND = "Reservation%s found for %s.%n";
    public static final String MSG_ACCOUNT_CREATED = "Account Created: %s%n";
    public static final String MSG_ROOM_NOT_AVAILABLE = "Room %s is not available.  Please select a room from the list.%n";
    public static final String MSG_SUCCESSFUL_RESERVATION = "Your reservation was successful.  %nReservation details: %s%n ";

    public static final String ERROR_DATE_FORMAT = "The date needs to entered as mm/dd/yyyy.";
    public static final String ERROR_ROOM_NUMBER_ENTRY = "Please enter the room number as it appears in the list.";
    public static final String ERROR_NO_ROOMS_AVAILABLE = "There are no rooms available for those dates.";
    public static final String ERROR_ACCOUNT_NOT_CREATED = "Your account could not be created.\n Please try again.";
    public static final String ERROR_ACCOUNT_NOT_FOUNT = "Your email address could not be found.";
    public static final String ERROR_PAST_DATE = "Please enter a date in the future.";
    public static final String ERROR_PAST_CHECKOUT_DATE = "The check-out date is before the check-in date. Please try again.";
    public static final String ERROR_UNSUCCESSFUL_RESERVATION = "Your reservation was unsuccessful.  Please try again.";

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
        String email = promptInput(scanner, PROMPT_ENTER_EMAIL);
        Collection<Reservation> reservations = hotelResource.getCustomerReservations(email);

        if (reservations.isEmpty()) {
            System.out.printf(MSG_RESERVATION_NOT_FOUND, email);
        } else {
            String suffix = reservations.size() > 1 ? "s" : "";
            for (Reservation r : reservations) {
                System.out.println(r.toString());
            }
        }
    }

    private String doCreateAccount(Scanner scanner) {
        String email = promptInput(scanner, PROMPT_ENTER_EMAIL);
        String firstName = promptInput(scanner, PROMPT_ENTER_FIRST_NAME);
        String lastName = promptInput(scanner, PROMPT_ENTER_LAST_NAME);
        hotelResource.createACustomer(firstName,lastName,email);
        Customer c = hotelResource.getCustomer(email);
        if (c == null) {
            System.out.println(ERROR_ACCOUNT_NOT_CREATED);
        }
        System.out.printf(MSG_ACCOUNT_CREATED, c.toString());
        return email;
    }

    private void doReserveARoom(Scanner scanner) {
        // prompt the user for dates
        ReservationDate checkInDate = promptForDate(scanner, PROMPT_ENTER_CHECK_IN_DATE);
        ReservationDate checkOutDate = null;
        while(true) {
            checkOutDate = promptForDate(scanner, PROMPT_ENTER_CHECK_OUT_DATE);

            if (checkOutDate.before(checkInDate)) {
                System.out.println(ERROR_PAST_CHECKOUT_DATE);
                continue;
            }
            break;
        }

        // check for available rooms
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        IRoom selectedRoom = promptSelectRoom(scanner, availableRooms);
        if (selectedRoom == null) {
            System.out.println(ERROR_UNSUCCESSFUL_RESERVATION);
            return;
        }

        String email = promptForAccount(scanner);
        Customer customer = promptForCustomer(scanner, email);
        if (customer == null) {
            System.out.println(ERROR_UNSUCCESSFUL_RESERVATION);
            return;
        }

        boolean saveReservation = prompForYesOrNo(scanner, PROMPT_SAVE_RESERVATION);
        if (saveReservation) {
            Reservation reservation = hotelResource.bookARoom(customer, selectedRoom, checkInDate, checkOutDate );
            if (reservation == null) {
                System.out.println(ERROR_UNSUCCESSFUL_RESERVATION);
            } else {
                System.out.printf(MSG_SUCCESSFUL_RESERVATION, reservation.toString());
            }
        }
    }

    private IRoom promptSelectRoom(Scanner scanner, Collection<IRoom> availableRooms) {
        IRoom selectedRoom = null;

        if (availableRooms.isEmpty())  {
            System.out.println(ERROR_NO_ROOMS_AVAILABLE);
        } else {
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
        }
        return selectedRoom;
    }

    private String promptForAccount(Scanner scanner) {
        String email = null;
        // do you have an account with us?
        boolean hasAccount = prompForYesOrNo(scanner, PROMPT_HAVE_ACCOUNT);

        if (!hasAccount) {
            // create an account
            email = doCreateAccount(scanner);
        } else {
            email = promptInput(scanner, PROMPT_ENTER_EMAIL);
        }
        return email;
    }

    private Customer promptForCustomer(Scanner scanner, String email) {
        Customer customer = null;
        while (customer == null) {
            customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println(ERROR_ACCOUNT_NOT_FOUNT);
                boolean tryAgain = prompForYesOrNo(scanner, PROMPT_TRY_AGAIN);
                if (tryAgain) {
                    email = promptInput(scanner, PROMPT_ENTER_EMAIL);
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
                System.out.printf(MSG_ROOM_NOT_AVAILABLE, roomNumber);

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(ERROR_ROOM_NUMBER_ENTRY);
            }

        }
    }

    private ReservationDate promptForDate(Scanner scanner, String prompt) {
        ReservationDate result = null;
        while (result == null) {
            try {
                System.out.print(prompt);
                String inputDate = scanner.nextLine();
                // did they enter a well formatted date?
                if (!datePattern.matcher(inputDate).matches()) {
                    System.out.println(ERROR_DATE_FORMAT);
                    continue;
                }
                ReservationDate selectedDate = new ReservationDate(inputDate);
                ReservationDate today = new ReservationDate();

                // don't save a date in the past, so compare the dates (is selectedDate before today?)
                if (selectedDate.before(today)) {
                    System.out.println(ERROR_PAST_DATE);
                    continue;
                }
                result = selectedDate;
            } catch (InputMismatchException | NullPointerException | ParseException exception) {
                System.out.println(ERROR_DATE_FORMAT);
            }
        }
        return result;
    }
}