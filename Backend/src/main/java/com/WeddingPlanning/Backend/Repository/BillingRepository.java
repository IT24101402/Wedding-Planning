package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.Bill;
import com.WeddingPlanning.Backend.Model.Booking;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillingRepository {

    private final String FILE_PATH = "bills.txt";

    public void saveBill(Bill bill) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println("Bill:");
            for (Booking booking : bill.getBookings()) {
                writer.println(booking.getServiceType() + "," + booking.getPrice());
            }
            writer.println("Total: " + bill.getTotal());
            writer.println("Finalized: " + bill.isFinalized());
            writer.println("Paid: " + bill.isPaid());
            writer.println("---");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllBills() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}