package com.davidjdickinson.jdnd.hotelreservation.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    Date checkInDate;
    Date checkOutDate;
    private String id;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.id = customer + room.getRoomNumber() + checkInDate.hashCode() + checkOutDate.hashCode();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public String getId() {
        return this.id;

    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "Email: " + this.getCustomer().getEmail() +
                    " | Room: " + this.getRoom().getRoomNumber() +
                    " | Check In: " + formatter.format(this.getCheckInDate()) +
                    " | Check Out: " + formatter.format(this.getCheckOutDate());
        }
}
