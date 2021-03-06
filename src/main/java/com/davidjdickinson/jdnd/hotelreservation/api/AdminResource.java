package com.davidjdickinson.jdnd.hotelreservation.api;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;
import com.davidjdickinson.jdnd.hotelreservation.service.CustomerService;
import com.davidjdickinson.jdnd.hotelreservation.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static final int EXIT_ADMIN_OPTION = 4;

    private static AdminResource instance;
    private static ReservationService reservationService;
    private static CustomerService customerService;

    private static Collection<IRoom> rooms;

    private AdminResource() {
        reservationService = ReservationService.getInstance();
        customerService = CustomerService.getInstance();
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> newRooms) {
        for (IRoom room : newRooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }

    public Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public IRoom findRoomByNumber(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }
}
