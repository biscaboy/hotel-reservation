package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Customer c = new Customer("Name", "Last", "email");
        });
    }

    // TODO:  1. Add tests for the api.  Make calls to the services.
}
