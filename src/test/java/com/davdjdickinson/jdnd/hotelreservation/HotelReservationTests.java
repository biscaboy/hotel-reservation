package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import com.davidjdickinson.jdnd.hotelreservation.api.HotelResource;
import com.davidjdickinson.jdnd.hotelreservation.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
        Assertions.assertEquals(1, savedRooms.size());
        Assertions.assertTrue(savedRooms.contains(room));
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
        Assertions.assertEquals(1, savedRooms.size());
        Assertions.assertTrue(savedRooms.contains(room));
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

    private void addOneRoom(String roomNumber) {
        Room room = new Room(roomNumber, 125.0, RoomType.SINGLE );
        List<IRoom> rooms = new LinkedList<IRoom>();
        rooms.add(room);
        AdminResource resource = AdminResource.getInstance();
        resource.addRoom(rooms);
    }

    private void createOneCustomer(String first, String last, String email){

    }
}
