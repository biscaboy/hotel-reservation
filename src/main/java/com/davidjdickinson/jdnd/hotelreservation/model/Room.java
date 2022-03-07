package com.davidjdickinson.jdnd.hotelreservation.model;

import java.text.NumberFormat;

public class Room implements IRoom {

    protected String roomNumber;
    protected Double roomPrice;
    protected RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public String getRoomPriceAsCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.roomPrice);
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room: " + roomNumber +
                " | Price: " + this.getRoomPriceAsCurrency() +
                " | Type: " + roomType;
    }

}
