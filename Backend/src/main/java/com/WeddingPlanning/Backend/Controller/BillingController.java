package com.WeddingPlanning.Backend.Controller;


import com.WeddingPlanning.Backend.Model.Bill;
import com.WeddingPlanning.Backend.Model.Reservation;
import com.WeddingPlanning.Backend.Service.BillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@CrossOrigin
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @PostMapping("/addBooking")
    public void addBooking(@RequestBody Reservation booking) {
        service.addBooking(booking);
    }

    @DeleteMapping("/removeBooking/{index}")
    public void removeBooking(@PathVariable int index) {
        service.removeBooking(index);
    }

    @GetMapping("/quotation")
    public Bill getQuotation() {
        return service.getCurrentBill();
    }

    @PostMapping("/finalize")
    public void finalizeBill() {
        service.finalizeBill();
    }

    @PostMapping("/pay")
    public void payBill() {
        service.payBill();
    }

    @GetMapping("/all")
    public List<String> getAllBills() {
        return service.getAllBills();
    }
}