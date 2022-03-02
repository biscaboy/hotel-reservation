package com.davidjdickinson.jdnd.hotelreservation.service;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;

import java.util.*;

/**
 * Thanks to https://www.geeksforgeeks.org/singleton-class-java/
 * for the guide on singleton classes
 */
public class CustomerService {

    private static CustomerService instance;
    private Collection<Customer> customers;
    private Map<String, Customer> customerMap;

    private CustomerService(){
        customers = new LinkedList<>();
        // a copy of the customer list indexed for quick lookups.
        customerMap = new HashMap<>();
    }

    public static CustomerService getInstance(){
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName){
        if (!customerMap.keySet().contains(email)) {
            Customer c = new Customer(firstName, lastName, email);
            customers.add(c);
            customerMap.put(email, c);
        }
    }

    public Customer getCustomer(String customerEmail){
        return customerMap.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return null;
    }

}
