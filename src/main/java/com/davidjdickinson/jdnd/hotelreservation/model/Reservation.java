package com.davidjdickinson.jdnd.hotelreservation.model;

public class Reservation {
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
        if ((this.checkInDate.after(checkInDate) && this.checkInDate.before(checkOutDate)) ||
                (this.checkOutDate.after(checkInDate) && this.checkOutDate.before(checkOutDate))) {
            return true;
        }
        return false;
    }
}
