package com.davidjdickinson.jdnd.hotelreservation.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;

public class ReservationService {

    private static Map<String, Reservation> reservationsMap;
    private static ReservationService instance;
    private static Map<String, IRoom> roomMap;

    private ReservationService(){
        roomMap = new HashMap<>();
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
        }
    }

    public IRoom getARoom(String roomId){
        return roomMap.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation r = new Reservation(customer, room, checkInDate, checkOutDate);
        if (!reservationsMap.containsKey(r.getId())){
            reservationsMap.put(r.getId(), r);
            return r;
        }
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Map<String, IRoom> availableRooms = new HashMap<>(roomMap);

        // loop through reservations and find reserved room numbers for date range
        for (Reservation r : reservationsMap.values()){
            if (((r.getCheckInDate().after(checkInDate)) &&
                    (r.getCheckInDate().before(checkOutDate)))
                ||
                ((r.getCheckOutDate().after(checkInDate)) &&
                        (r.getCheckOutDate().before(checkOutDate)))) {
                availableRooms.remove(r.getRoom().getRoomNumber());
            }
        }
        return availableRooms.values();
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
        int count = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Reservation r : reservationsMap.values()) {
            String line = count + ". Email: " + r.getCustomer().getEmail() +
                    " | Room: " + r.getRoom().getRoomNumber() +
                    " | Check In: " + formatter.format(r.getCheckInDate()) +
                    " | Check Out: " + formatter.format(r.getCheckOutDate());
            System.out.println(line);
            count++;
        }

    }

    public Collection<IRoom> getAllRooms(){
        return roomMap.values();
    }
}

