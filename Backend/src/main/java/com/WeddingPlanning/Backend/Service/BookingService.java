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

    public Booking updateBooking(Long id, Booking booking) {
        String bookingId = String.valueOf(id); // convert Long to String
        bookingRepository.updateBooking(bookingId, booking);
        return booking;
    }

    public void deleteBooking(Long id) {
        String bookingId = String.valueOf(id); // convert Long to String
        bookingRepository.deleteBooking(bookingId);
    }

    public Booking getBookingById(Long id) {
        String bookingId = String.valueOf(id); // convert Long to String
        return bookingRepository.findBookingById(bookingId);
    }
}
