package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import com.davidjdickinson.jdnd.hotelreservation.api.HotelResource;
import com.davidjdickinson.jdnd.hotelreservation.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HotelReservationTests {

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
        Room room = new Room("100", 125.0, RoomType.SINGLE );
        List<IRoom> rooms = new LinkedList<IRoom>();
        rooms.add(room);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);
        HotelResource hotelResource = HotelResource.getInstance();
        IRoom savedRoom = hotelResource.getRoom("100");
        Assertions.assertEquals(savedRoom.getRoomNumber(), "100");
    }

    @Test
    @DisplayName("Book a room")
    public void book_a_room() {
        addOneRoom("200");
        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");
        Customer jill = hotelResource.getCustomer("jill@uphill.com");
        IRoom room = hotelResource.getRoom("200");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 05, 01);
        Date checkInDate = calendar.getTime();
        calendar.set(2022, 05, 10);
        Date checkOutDate = calendar.getTime();
        hotelResource.bookARoom(jill, room, checkInDate, checkOutDate);

        Collection<Reservation> reservations = hotelResource.getCustomerReservations("jill@uphill.com");
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
        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("Jill", "Upthehill", "jill@uphill.com");
        hotelResource.createACustomer("Jack", "Upthehill", "jack@uphill.com");
        hotelResource.createACustomer("Jerry", "Seinfeld", "jerry@aol.com");

        AdminResource ar = AdminResource.getInstance();
        Collection<Customer> customers = ar.getAllCustomers();
        Assertions.assertEquals(3, customers.size());
    }

    @Test
    @DisplayName("Find a room")
    public void find_a_room(){
        addOneRoom("100");
        addOneRoom("200");
        addOneRoom("300");
        HotelResource hr = HotelResource.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 06, 01);
        Date checkInDate = calendar.getTime();
        calendar.set(2022, 06, 10);
        Date checkOutDate = calendar.getTime();
        Collection<IRoom> availableRooms = hr.findARoom(checkInDate, checkOutDate);

        Assertions.assertEquals(3, availableRooms.size());
    }

    @Test
    @DisplayName("Filter out date confilcts")
    public void filter_out_date_conflicts(){
        List<IRoom> rooms = new LinkedList<IRoom>();

        Room room1 = new Room("400", 125.0, RoomType.SINGLE );
        rooms.add(room1);
        Room room2 = new Room("500", 125.0, RoomType.SINGLE );
        rooms.add(room2);
        Room room3 = new Room("600", 125.0, RoomType.DOUBLE );
        rooms.add(room3);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);

        HotelResource hr = HotelResource.getInstance();
        hr.createACustomer("Kareem", "Jabaar", "no33@lakers.com");
        Customer customer = hr.getCustomer("jill@uphill.com");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 07, 01);
        Date checkInDate = calendar.getTime();
        calendar.set(2022, 07, 10);
        Date checkOutDate = calendar.getTime();

        hr.bookARoom(customer,room1,checkInDate, checkOutDate);

        calendar.set(2022, 07, 05);
        Date desiredCheckInDate = calendar.getTime();
        calendar.set(2022, 07, 15);
        Date desiredCheckOutDate = calendar.getTime();

        Collection<IRoom> availableRooms = hr.findARoom(desiredCheckInDate, desiredCheckOutDate);

        Assertions.assertEquals(2, availableRooms.size());
    }

    private void addOneRoom(String roomNumber) {
        Room room = new Room(roomNumber, 125.0, RoomType.SINGLE );
        List<IRoom> rooms = new LinkedList<IRoom>();
        rooms.add(room);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);
    }

}
