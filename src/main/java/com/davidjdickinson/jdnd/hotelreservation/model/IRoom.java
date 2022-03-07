package com.davidjdickinson.jdnd.hotelreservation.model;

public interface IRoom {

    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
    public String getRoomPriceAsCurrency();

}
