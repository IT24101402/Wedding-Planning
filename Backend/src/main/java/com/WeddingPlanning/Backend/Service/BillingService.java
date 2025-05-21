package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Bill;
import com.WeddingPlanning.Backend.Model.BookingModel;
import com.WeddingPlanning.Backend.Repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final BillingRepository repository;
    private Bill currentBill = new Bill();

    public BillingService(BillingRepository repository) {
        this.repository = repository;
    }

    public void addBooking(BookingModel booking) {
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
}