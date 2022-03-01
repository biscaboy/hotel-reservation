package com.davidjdickinson.jdnd.hotelreservation.service;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;

import java.util.Collection;

/**
 * Thanks to https://www.geeksforgeeks.org/singleton-class-java/
 * for the guide on singleton classes
 */
public class CustomerService {

    private static CustomerService instance;

    private CustomerService(){}

    public static CustomerService getInstance(){
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName){

    }

    public Customer getCustomer(String customerEmail){
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return null;
    }

}
