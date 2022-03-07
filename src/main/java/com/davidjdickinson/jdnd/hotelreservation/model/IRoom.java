package com.davidjdickinson.jdnd.hotelreservation.model;

public abstract class IRoom implements Comparable{

    public abstract String getRoomNumber();
    public abstract Double getRoomPrice();
    public abstract RoomType getRoomType();
    public abstract boolean isFree();
    public abstract String getRoomPriceAsCurrency();

}
