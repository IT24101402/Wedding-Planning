package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.Booking;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class BookingRepository {
    private final String FILE_PATH = "bookings.txt";

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(Booking.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void saveBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(booking.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBooking(String bookingId, Booking updated) {
        List<Booking> all = getAllBookings();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Booking b : all) {
                if (b.getBookingId().equals(bookingId)) {
                    // Ensure updated booking keeps the correct bookingId
                    if (updated.getBookingId() == null || updated.getBookingId().isEmpty()) {
                        updated.setBookingId(b.getBookingId());
                    }
                    writer.write(updated.toString());
                } else {
                    writer.write(b.toString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBooking(String bookingId) {
        List<Booking> all = getAllBookings();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Booking b : all) {
                if (!b.getBookingId().equals(bookingId)) {
                    writer.write(b.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Booking findBookingById(String bookingId) {
        return getAllBookings().stream().filter(b -> b.getBookingId().equals(bookingId)).findFirst().orElse(null);
    }
}
