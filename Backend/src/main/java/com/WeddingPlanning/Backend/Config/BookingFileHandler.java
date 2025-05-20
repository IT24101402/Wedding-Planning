package com.WeddingPlanning.Backend.Config;

import com.WeddingPlanning.Backend.Model.Booking;

import java.io.*;
import java.util.*;

public class BookingFileHandler {

    private static final String FILE_PATH = "src/main/resources/static/bookings.txt";

    // v4 edit
    //
    //
    
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

