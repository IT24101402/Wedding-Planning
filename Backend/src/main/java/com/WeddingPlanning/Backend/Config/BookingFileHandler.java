package com.WeddingPlanning.Backend.Config;

import com.WeddingPlanning.Backend.Model.Booking;
import java.util.stream.Collectors;
import java.io.*;
import java.util.*;

public class BookingFileHandler {

    private static final String FILE_PATH = "bookings.txt";

    public static List<Booking> getBookingsByUsername(String username) {
        List<Booking> allBookings = readBookingsFromFile(); // reads from bookings.txt
        return allBookings.stream()
                .filter(b -> b.getUserName().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    public static List<Booking> readBookingsFromFile() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                bookings.add(Booking.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}

