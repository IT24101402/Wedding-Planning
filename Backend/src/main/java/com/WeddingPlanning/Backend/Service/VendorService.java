package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.Repository.VendorRepository;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class VendorService {
    private LinkedList<Vendor> vendors = new LinkedList<>();
    private final VendorRepository repository;

    public VendorService(VendorRepository repository) {
        this.repository = repository;
        this.vendors = repository.loadVendors();
    }

    public void addVendor(Vendor vendor) {
        System.out.println("➡️ Attempting to add vendor: " + vendor.getName() + " (ID: " + vendor.getId() + ")");

        // Load latest vendor list from file
        LinkedList<Vendor> currentVendors = repository.loadVendors();

        // Check for duplicate ID or email
        for (Vendor v : currentVendors) {
            if (v.getId().equals(vendor.getId())) {
                System.out.println("⚠️ Vendor with ID " + vendor.getId() + " already exists!");
                return; // Skip adding
            }
            if (v.getEmail().equalsIgnoreCase(vendor.getEmail())) {
                System.out.println("⚠️ Vendor with Email " + vendor.getEmail() + " already exists!");
                return; // Skip adding
            }
        }

        // Add new vendor
        currentVendors.add(vendor);
        this.vendors = currentVendors; // Update in-memory list

        System.out.println("✅ Vendor added. Total vendors now: " + currentVendors.size());

        // Save updated list
        repository.saveVendors(currentVendors);
        System.out.println("💾 Vendor list saved to file.");
    }


    public List<Vendor> getAllVendors() {
        return vendors;
    }

    public Vendor getVendorById(Long id) {
        for (Vendor v : vendors) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }
    public Vendor getVendorByEmail(String email) {
        for (Vendor v : vendors) {
            if (v.getEmail().equals(email)) return v;
        }
        return null;
    }
    public Vendor vendorLogin(String email, String password){
        for (Vendor v : vendors) {
            if (v.getEmail().equals(email)&& v.getPassword().equals(password)) return v;
        }
        return null;
    }

    public void updateVendor(Long id, Vendor updated) {
        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getId().equals(id)) {
                vendors.set(i, updated);
                break;
            }
        }
        repository.saveVendors(vendors);
    }

    public void deleteVendor(Long id) {
        vendors.removeIf(v -> v.getId().equals(id));
        repository.saveVendors(vendors);
    }

    public void sortVendorsByPrice(boolean ascending) {
        for (int i = 0; i < vendors.size() - 1; i++) {
            for (int j = 0; j < vendors.size() - i - 1; j++) {
                boolean condition = ascending ? vendors.get(j).getPrice() > vendors.get(j + 1).getPrice() :
                        vendors.get(j).getPrice() < vendors.get(j + 1).getPrice();
                if (condition) {
                    Vendor temp = vendors.get(j);
                    vendors.set(j, vendors.get(j + 1));
                    vendors.set(j + 1, temp);
                }
            }
        }
    }
}