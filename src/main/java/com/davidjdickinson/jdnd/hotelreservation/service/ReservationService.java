package com.davidjdickinson.jdnd.hotelreservation.service;

import java.util.*;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;

public class ReservationService {

    private static Collection<Reservation> reservations;
    private static Map<String, Reservation> reservationsMap;
    private static Collection<IRoom> rooms;
    private static ReservationService instance;
    private static Map<String, IRoom> roomMap;

    private ReservationService(){
        roomMap = new HashMap<>();
        rooms = new HashSet<>();
        reservations = new HashSet<>();
        reservationsMap = new HashMap<>();
    }

    public static ReservationService getInstance(){
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room){
        if (!roomMap.containsKey(room.getRoomNumber())) {
            roomMap.put(room.getRoomNumber(), room);
            rooms.add(room);
        }
    }

    public IRoom getARoom(String roomId){
        return roomMap.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation r = new Reservation(customer, room, checkInDate, checkOutDate);
        if (!reservationsMap.containsKey(r.getId())){
            reservationsMap.put(r.getId(), r);
            reservations.add(r);
            return r;
        }
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer){
        Collection<Reservation> customerReservations = new LinkedList<>();
        for (Reservation r : reservationsMap.values()) {
            if (r.getCustomer().getEmail().equals(customer.getEmail())){
                customerReservations.add(r);
            }
        }
        return customerReservations;
    }

    public void printAllReservations(){

    }

    public Collection<IRoom> getAllRooms(){
        return new HashSet<IRoom>(rooms);
    }
}

