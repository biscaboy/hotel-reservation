package com.davidjdickinson.jdnd.hotelreservation.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;

public class ReservationService {

    private static Collection<Reservation> reservations;
    private static ReservationService instance;

    private ReservationService(){
        reservations = new HashSet<Reservation>();
    }

    public static ReservationService getInstance(){
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room){

    }

    public IRoom getARoom(String roomId){
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer){
        return null;
    }

    public void printAllReservations(){

    }
}

