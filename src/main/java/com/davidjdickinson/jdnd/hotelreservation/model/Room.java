package com.davidjdickinson.jdnd.hotelreservation.model;

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
                " | Price: " + roomPrice +
                " | Type: " + roomType + ")";
    }

}
