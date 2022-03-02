package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.api.AdminResource;
import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Room;
import com.davidjdickinson.jdnd.hotelreservation.model.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HotelReservationTests {

    @Test
    @DisplayName("Create a customer")
    public void create_a_customer(){
        Customer customer = new Customer("Jill", "Upthehill", "jill@uphill.com");
        assertEquals("First Name: Jill, Last Name: Upthehill, Email: jill@uphill.com", customer.toString());
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

    // TODO:  1. Add tests for the api.  Make calls to the services.

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

}
