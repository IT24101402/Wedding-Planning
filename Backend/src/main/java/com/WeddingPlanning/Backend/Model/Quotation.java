package com.WeddingPlanning.Backend.Model;

import java.util.ArrayList;
import java.util.List;

public class Quotation {
    private List<Booking> bookings = new ArrayList<>();
    private double total;

    public void addBooking(Booking booking) {
        bookings.add(booking);
        calculateTotal();
    }

    public void removeBooking(int index) {
        bookings.remove(index);
        calculateTotal();
    }

    public void calculateTotal() {
        total = 0;
        for (Booking b : bookings) {
            total += b.getPrice();
        }
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public double getTotal() {
        return total;
    }
}