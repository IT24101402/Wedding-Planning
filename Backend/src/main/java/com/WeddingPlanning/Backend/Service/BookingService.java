package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Booking;
import com.WeddingPlanning.Backend.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    public void addBooking(Booking booking) {
        bookingRepository.saveBooking(booking);
    }

    public void updateBooking(String id, Booking booking) {
        bookingRepository.updateBooking(id, booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteBooking(id);
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findBookingById(id);
    }
}
