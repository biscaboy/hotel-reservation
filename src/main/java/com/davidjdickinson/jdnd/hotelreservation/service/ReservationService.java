package com.davidjdickinson.jdnd.hotelreservation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;
import com.davidjdickinson.jdnd.hotelreservation.model.ReservationDate;

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

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return reserveARoom(customer, room, new ReservationDate(checkInDate), new ReservationDate(checkOutDate));
    }
    public Reservation reserveARoom(Customer customer, IRoom room, ReservationDate checkInDate, ReservationDate checkOutDate){
        Reservation r = new Reservation(customer, room, checkInDate, checkOutDate);
        if (!reservationsMap.containsKey(r.getId())){
            reservationsMap.put(r.getId(), r);
            return r;
        }
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) throws ParseException {
        return findRooms(new ReservationDate(checkInDate), new ReservationDate(checkOutDate));
    }

    public Collection<IRoom> findRooms(ReservationDate checkInDate, ReservationDate checkOutDate){
        Map<String, IRoom> availableRooms = new HashMap<>(roomMap);

        // loop through reservations and find reserved room numbers for date range
        for (Reservation r : reservationsMap.values()){
            if (r.hasConflict(checkInDate, checkOutDate)) {
                availableRooms.remove(r.getRoom().getRoomNumber());
            }
        }
        List<IRoom> result = new LinkedList<>(availableRooms.values());
        Collections.sort(result);
        return result;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer){
        List<Reservation> customerReservations = new LinkedList<>();
        for (Reservation r : reservationsMap.values()) {
            if (r.getCustomer().getEmail().equals(customer.getEmail())){
                customerReservations.add(r);
            }
        }
        Collections.sort(customerReservations);
        return customerReservations;
    }

    public void printAllReservations(){
        int count = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Reservation r : getAllReservations()) {
            String line = count + r.toString();
            System.out.println(line);
            count++;
        }

    }

    public Collection<Reservation> getAllReservations(){
        List<Reservation> result = new LinkedList<>(reservationsMap.values());
        Collections.sort(result);
        return result;
    }

    public Collection<IRoom> getAllRooms(){
        List<IRoom> result = new LinkedList<>(roomMap.values());
        Collections.sort(result);
        return result;
    }
}

