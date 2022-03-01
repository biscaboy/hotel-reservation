package com.davidjdickinson.jdnd.hotelreservation.api;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource instance;

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (instance == null){
            instance = new HotelResource();
        }
        return instance;
    }

    public Customer getCustomer(String email){
        return null;
    }

    public void createACustomer(String firstName, String lastName, String email){

    }

    public IRoom getRoom(String roomNumber){
        return null;
    }

    public Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservations(){
        return null;
    }

    public IRoom findARoom(Date checkInDate, Date checkOutDate){
        return null;
    }
}
