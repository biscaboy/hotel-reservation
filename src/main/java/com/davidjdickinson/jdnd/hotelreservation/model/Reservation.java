package com.davidjdickinson.jdnd.hotelreservation.model;

import java.util.Comparator;

public class Reservation implements Comparable {
    private Customer customer;
    private IRoom room;
    ReservationDate checkInDate;
    ReservationDate checkOutDate;
    private String id;

    public Reservation(Customer customer, IRoom room, ReservationDate checkInDate, ReservationDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.id = customer + room.getRoomNumber() + this.checkInDate.getId() + this.checkOutDate.getId();
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

    public ReservationDate getCheckInDate() {
        return checkInDate;
    }

    public ReservationDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getId() {
        return this.id;

    }

    @Override
    public String toString() {
        return "Email: " + this.getCustomer().getEmail() +
                    " | Room: " + this.getRoom().getRoomNumber() +
                    " | Check In: " + this.getCheckInDate().toString() +
                    " | Check Out: " + this.getCheckOutDate().toString();
        }

    public boolean hasConflict(ReservationDate checkInDate, ReservationDate checkOutDate) {
        // if either date matches a date on this reservation there is a conflict.
        if (this.checkInDate.equals(checkInDate) ||
                this.checkInDate.equals(checkOutDate) ||
                this.checkOutDate.equals(checkInDate) ||
                this.checkOutDate.equals(checkOutDate)) {
            return true;
        };

        // if either date is in between the dates of this reservation there is a conflict.
        if ((checkInDate.after(this.checkInDate) && checkInDate.before(this.checkOutDate)) ||
                (checkOutDate.after(this.checkInDate) && checkOutDate.before(this.checkOutDate))) {
            return true;
        }

        // if checkout dates span existing reservation
        if ((checkInDate.before(this.checkInDate)) && (checkOutDate.after(this.checkOutDate))) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Reservation r = (Reservation)o;
        return Integer.compare(this.checkInDate.getId(), ((Reservation) o).getCheckInDate().getId());
    }
}
