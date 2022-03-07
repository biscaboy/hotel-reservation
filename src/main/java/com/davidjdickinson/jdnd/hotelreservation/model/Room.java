package com.davidjdickinson.jdnd.hotelreservation.model;

import java.text.NumberFormat;

public class Room extends IRoom implements Comparable {

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

    @Override
    public int compareTo(Object o) {
        int a = Integer.parseInt(this.roomNumber);
        int b = Integer.parseInt(((IRoom)o).getRoomNumber());
        return Integer.compare(a, b);
    }
}
