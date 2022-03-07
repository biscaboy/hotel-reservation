package com.davidjdickinson.jdnd.hotelreservation.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ReservationDate implements Comparable {
    public static final String RESERVATION_DATE_FORMAT = "MM/dd/yyyy";
    private static final String RESERVATION_DATE_FORMAT_ID = "yyyyMMdd";

    private Date date;
    private String formattedDate;
    private int id;

    SimpleDateFormat formatter = new SimpleDateFormat();

    public ReservationDate() {
        construct(new Date());
    }

    public ReservationDate(String inputDate) throws ParseException {
        formatter.applyPattern(RESERVATION_DATE_FORMAT);
        Date date = formatter.parse(inputDate);
        construct(date);
    }

    private void construct(Date date) {
        this.date = date;
        formatter = new SimpleDateFormat(RESERVATION_DATE_FORMAT);
        this.formattedDate = formatter.format(date);

        formatter.applyPattern(RESERVATION_DATE_FORMAT_ID);
        this.id = Integer.parseInt(formatter.format(date));
    }

    @Override
    public String toString() {
        return this.formattedDate;
    }

    public int getId() {
        return this.id;
    }

    public boolean before(ReservationDate d){
        return this.compareTo(d) < 0;
    }

    public boolean after(ReservationDate d){
        return this.compareTo(d) > 0;
    }

    @Override
    public int compareTo(Object o) {
        ReservationDate d = (ReservationDate)o;
        return Integer.compare(this.id, d.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDate)) return false;
        ReservationDate that = (ReservationDate) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        construct(calendar.getTime());
    }
}
