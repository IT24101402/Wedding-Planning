package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Bill;
import com.WeddingPlanning.Backend.Model.Reservation;
import com.WeddingPlanning.Backend.Repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class BillingService {

    private final BillingRepository repository;
    private Bill currentBill = new Bill();

    public BillingService(BillingRepository repository) {
        this.repository = repository;
    }

    public void addBooking(Reservation booking) {
        currentBill.addBooking(booking);
    }

    public void removeBooking(int index) {
        currentBill.removeBooking(index);
    }

    public Bill getCurrentBill() {
        return currentBill;
    }

    public void finalizeBill() {
        currentBill.finalizeBill();
        repository.saveBill(currentBill);
    }

    public void payBill() {
        currentBill.payBill();
        repository.saveBill(currentBill);
    }

    public List<String> getAllBills() {
        return repository.getAllBills();
    }

    public void importBookingsFromFiles(String bookingsFile, String vendorsFile) {
        Map<String, Double> vendorPriceMap = new HashMap<>();

        // Step 1: Load vendor prices
        try (BufferedReader reader = new BufferedReader(new FileReader(vendorsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String vendorName = parts[1].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    vendorPriceMap.put(vendorName, price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Process bookings
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String customerName = parts[1].trim();
                    String vendorName = parts[2].trim();
                    String serviceName = parts[3].trim();

                    Double price = vendorPriceMap.get(vendorName);
                    if (price != null) {
                        Reservation reservation = new Reservation(customerName, vendorName, serviceName, price);
                        currentBill.addBooking(reservation);
                        repository.saveReservation(reservation);
                    } else {
                        System.err.println("No price found for vendor: " + vendorName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        repository.saveBill(currentBill);
    }

    public List<Reservation> readFromReservationTxt() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/reservation.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // update this based on your actual format
                if (parts.length >= 4) {
                    String customerName = parts[0].trim();
                    String vendorName = parts[1].trim();
                    String serviceType = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());

                    Reservation r = new Reservation(customerName, vendorName, serviceType, price);
                    r.setCustomerName(customerName);
                    r.setVendorName(vendorName);
                    r.setServiceType(serviceType);
                    r.setPrice(price);
                    reservations.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }


}