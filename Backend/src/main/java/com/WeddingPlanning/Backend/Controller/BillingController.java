package com.WeddingPlanning.Backend.Controller;


import com.WeddingPlanning.Backend.Model.Bill;
import com.WeddingPlanning.Backend.Model.Reservation;
import com.WeddingPlanning.Backend.Service.BillingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    @PostMapping("/importBookings")
    public void importBookings() {
        service.importBookingsFromFiles("src/main/resources/static/bookings.txt", "src/main/resources/static/vendors.txt");
    }

    @GetMapping("/quotationByUser")
    public Map<String, Object> getQuotationByUser(@RequestParam String username) {
        List<Reservation> allBookings = service.readFromReservationTxt(); // your logic in service
        List<Reservation> userBookings = allBookings.stream()
                .filter(b -> b.getCustomerName().equalsIgnoreCase(username))
                .collect(Collectors.toList());

        double total = userBookings.stream()
                .mapToDouble(Reservation::getPrice)
                .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("bookings", userBookings);
        response.put("total", total);
        return response;
    }


}
