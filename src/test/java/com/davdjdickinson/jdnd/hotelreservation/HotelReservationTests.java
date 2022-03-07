package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import com.davidjdickinson.jdnd.hotelreservation.api.HotelResource;
import com.davidjdickinson.jdnd.hotelreservation.model.*;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HotelReservationTests {

    @BeforeAll
    public static void beforeAll() {
        List<IRoom> rooms = new LinkedList<IRoom>();

        // create 6 rooms (101, 202, 303, ... 606) for increasing prices with even room number as single, odd as double.
        for (int i = 1; i <= 6; i++ ) {
            String roomNum = Integer.valueOf(i * 101).toString();
            Room room = new Room(roomNum, 50.0 * i, i % 2 == 0 ? RoomType.SINGLE : RoomType.DOUBLE);
            rooms.add(room);
        }
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);

        // Three test customers.
        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");
        hotelResource.createACustomer("Jack", "Upthehill", "jack@uphill.com");
        hotelResource.createACustomer("Jerry", "Seinfeld", "jerry@aol.com");
    }
    @Test
    @DisplayName("Create a customer")
    public void create_a_customer(){
        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");
        Customer customer = hotelResource.getCustomer("jill@uphill.com");
        assertEquals("Jill", customer.getFirstName());
        assertEquals("Upthehill", customer.getLastName());
        assertEquals("jill@uphill.com", customer.getEmail());
    }

    @Test
    @DisplayName("Add duplicate customer")
    public void add_duplicate_customer(){
        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");

        AdminResource ar = AdminResource.getInstance();
        Collection<Customer> customers = ar.getAllCustomers();
        int count = 0;
        for (Customer c : customers) {
            if (c.getFirstName().equals("Jill")){
                count++;
            }
        }
        Assertions.assertEquals(1, count);
    }

    /**
     *  Thanks to https://howtodoinjava.com/junit5/expected-exception-example/ for an
     *  example of how to use the assertThrows method.
     */
    @Test
    @DisplayName("Validate email format")
    public void validate_email_format(){
        assertThrows(IllegalArgumentException.class, () -> {
            Customer c = new Customer("Name", "Last", "email");
        });
    }

    @Test
    @DisplayName("Add a room")
    public void add_a_room() {
        Room room = new Room("100", 125.0, RoomType.SINGLE );

        List<IRoom> rooms = new LinkedList<IRoom>();
        rooms.add(room);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);

        Collection<IRoom> savedRooms = resource.getAllRooms();
        IRoom found = null;
        for (IRoom r : savedRooms) {
            if (r.getRoomNumber().equals(room.getRoomNumber())) {
                found = r;
            }
        }
        Assertions.assertNotNull(found);
    }

    @Test
    @DisplayName("Add duplicate room")
    public void add_duplicate_room(){
        // the system should not allow duplicate rooms to be added.
        Room room = new Room("100", 125.0, RoomType.SINGLE );
        Room roomDuplicate = new Room("100", 125.0, RoomType.SINGLE );
        List<IRoom> rooms = new LinkedList<IRoom>();
        rooms.add(room);
        rooms.add(roomDuplicate);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);

        Collection<IRoom> savedRooms = resource.getAllRooms();
        int numberOfRoomsReturned = 0;
        for (IRoom r : savedRooms){
            if (r.getRoomNumber().equals(room.getRoomNumber())){
                numberOfRoomsReturned++;
            }
        }
        Assertions.assertEquals(1, numberOfRoomsReturned);
    }

    @Test
    @DisplayName("Get a room")
    public void get_a_room(){
        HotelResource hotelResource = HotelResource.getInstance();
        IRoom savedRoom = hotelResource.getRoom("101");
        Assertions.assertEquals(savedRoom.getRoomNumber(), "101");
    }

    @Test
    @DisplayName("Book a room")
    public void book_a_room() throws ParseException {
        HotelResource hotelResource = HotelResource.getInstance();
        Customer jill = hotelResource.getCustomer("jack@uphill.com");
        IRoom room = hotelResource.getRoom("202");
        ReservationDate checkInDate = new ReservationDate("05/01/2022");
        ReservationDate checkOutDate = new ReservationDate("05/10/2022");
        hotelResource.bookARoom(jill, room, checkInDate, checkOutDate);

        Collection<Reservation> reservations = hotelResource.getCustomerReservations("jack@uphill.com");
        Assertions.assertEquals(reservations.size(), 1);
        Reservation r = reservations.iterator().next();
        Assertions.assertEquals(r.getCustomer(), jill);
        Assertions.assertEquals(r.getRoom(), room);
        Assertions.assertEquals(r.getCheckInDate(), checkInDate);
        Assertions.assertEquals(r.getCheckOutDate(), checkOutDate);
    }

    @Test
    @DisplayName("Get all customers")
    public void get_all_customers() {
        AdminResource ar = AdminResource.getInstance();
        Collection<Customer> customers = ar.getAllCustomers();
        Assertions.assertEquals(3, customers.size());
    }

    @Test
    @DisplayName("Find a room")
    public void find_a_room() throws ParseException {
        HotelResource hr = HotelResource.getInstance();
        Calendar calendar = Calendar.getInstance();
        ReservationDate checkInDate = new ReservationDate("06/01/2022");
        ReservationDate checkOutDate = new ReservationDate("06/10/2022");
        Collection<IRoom> availableRooms = hr.findARoom(checkInDate, checkOutDate);

        Assertions.assertEquals(6, availableRooms.size());
    }

    @Test
    @DisplayName("Filter out date confilcts")
    public void filter_out_date_conflicts() throws ParseException {
        HotelResource hr = HotelResource.getInstance();
        IRoom room1 = hr.getRoom("404");
        Customer customer = hr.getCustomer("jill@uphill.com");
        Calendar calendar = Calendar.getInstance();
        ReservationDate checkInDate = new ReservationDate("07/01/2022");
        ReservationDate checkOutDate = new ReservationDate("07/10/2022");

        hr.bookARoom(customer,room1,checkInDate,checkOutDate);
        ReservationDate desiredCheckInDate = new ReservationDate("07/05/2022");
        ReservationDate desiredCheckOutDate = new ReservationDate("07/15/2022");

        // room 400 has conflicting dates with Jill's reservation so it shouldn't be in the returned list.
        Collection<IRoom> availableRooms = hr.findARoom(desiredCheckInDate, desiredCheckOutDate);
        boolean found = false;
        for (IRoom r : availableRooms) {
            if (r.getRoomNumber().equals("404")) {
                found = true;
            }
        }
        Assertions.assertFalse(found);
    }

    @Test
    @DisplayName("Get a customer (Admin Resource)")
    public void get_a_admin_customer(){
        AdminResource ar = AdminResource.getInstance();
        Customer c = ar.getCustomer("jack@uphill.com");
        Assertions.assertNotNull(c);
        Assertions.assertEquals("Jack", c.getFirstName());
    }

    @Test
    @DisplayName("Get a customer (Hotel Resource)")
    public void get_a_hotel_customer(){
        HotelResource hr = HotelResource.getInstance();
        Customer c = hr.getCustomer("jack@uphill.com");
        Assertions.assertNotNull(c);
        Assertions.assertEquals("Jack", c.getFirstName());
    }
}
