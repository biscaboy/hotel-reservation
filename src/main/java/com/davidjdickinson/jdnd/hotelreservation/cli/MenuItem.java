package com.davidjdickinson.jdnd.hotelreservation.cli;

import java.util.Objects;

public class MenuItem {

    private int number;
    private String description;
    private String action;

    public MenuItem(int number, String description, String action) {
        this.number = number;
        this.description = description;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return number == menuItem.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
