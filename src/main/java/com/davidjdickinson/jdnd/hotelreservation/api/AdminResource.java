package com.davidjdickinson.jdnd.hotelreservation.api;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static final int EXIT_ADMIN_OPTION = 4;

    private static AdminResource instance;

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (instance == null){
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email){
        return null;
    }

    public void addRoom(List<IRoom> rooms){

    }

    public Collection<IRoom> getAllRooms(){
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return null;
    }

    public void DisplayAllReservations() {

    }
}
