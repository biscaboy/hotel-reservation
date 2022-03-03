package com.davidjdickinson.jdnd.hotelreservation.model;

import java.util.regex.Pattern;

public class Customer {

    private static Pattern emailPattern = Pattern.compile("^(.+)@(.+).(.+)");
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("The email is not formatted properly.  Expected: name@domain.suffix");
        }

        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName +
                " | Last Name: " + lastName +
                " | Email: " + email;
    }


}
