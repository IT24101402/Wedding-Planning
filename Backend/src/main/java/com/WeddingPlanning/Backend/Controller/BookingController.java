package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.Booking;
import com.WeddingPlanning.Backend.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import com.WeddingPlanning.Backend.Config.BookingFileHandler;


import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")

public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/user/{username}")
    public List<Booking> getBookingsByUsername(@PathVariable String username) {
        List<Booking> allBookings = BookingFileHandler.readBookingsFromFile();
        return allBookings.stream()
                .filter(b -> b.getUserName().equals(username))
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return BookingFileHandler.readBookingsFromFile();
    }

    @PostMapping
    public String addBooking(@RequestBody Booking booking) {
        bookingService.addBooking(booking);
        return booking.getConfirmationMessage();
    }

    @PutMapping("/{id}")
    public void updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }
}
