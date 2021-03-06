package com.davidjdickinson.jdnd.hotelreservation.api;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import com.davidjdickinson.jdnd.hotelreservation.model.IRoom;
import com.davidjdickinson.jdnd.hotelreservation.model.Reservation;
import com.davidjdickinson.jdnd.hotelreservation.model.ReservationDate;
import com.davidjdickinson.jdnd.hotelreservation.service.CustomerService;
import com.davidjdickinson.jdnd.hotelreservation.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource instance;
    private CustomerService customerService;
    private ReservationService reservationService;

    private HotelResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static HotelResource getInstance() {
        if (instance == null){
            instance = new HotelResource();
        }
        return instance;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email){
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return bookARoom(customer, room, new ReservationDate(checkInDate), new ReservationDate(checkOutDate));
    }

    public Reservation bookARoom(Customer customer, IRoom room, ReservationDate checkInDate, ReservationDate checkOutDate){
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer c = customerService.getCustomer(customerEmail);
        return reservationService.getCustomerReservations(c);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return findARoom(new ReservationDate(checkInDate),new ReservationDate(checkOutDate));
    }

    public Collection<IRoom> findARoom(ReservationDate checkInDate, ReservationDate checkOutDate){
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

}
