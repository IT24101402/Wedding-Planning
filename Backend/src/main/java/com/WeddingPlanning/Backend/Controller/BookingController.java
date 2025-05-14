package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.Booking;
import com.WeddingPlanning.Backend.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import com.WeddingPlanning.Backend.Config.BookingFileHandler;
import org.springframework.http.ResponseEntity;



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
    public ResponseEntity<Booking> updateBooking(@PathVariable String id, @RequestBody Booking updatedBooking) {
        Booking booking = bookingService.updateBooking(Long.valueOf(id), updatedBooking);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(Long.valueOf(id));
    }
}
