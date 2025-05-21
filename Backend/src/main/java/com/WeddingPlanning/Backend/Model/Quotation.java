package com.WeddingPlanning.Backend.Model;

import java.util.ArrayList;
import java.util.List;

public class Quotation {
    private List<BookingModel> bookings = new ArrayList<>();
    private double total;

    public void addBooking(BookingModel booking) {
        bookings.add(booking);
        calculateTotal();
    }

    public void removeBooking(int index) {
        bookings.remove(index);
        calculateTotal();
    }

    public void calculateTotal() {
        total = 0;
        for (BookingModel b : bookings) {
            total += b.getPrice();
        }
    }

    public List<BookingModel> getBookings() {
        return bookings;
    }

    public double getTotal() {
        return total;
    }
}