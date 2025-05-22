package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.Repository.VendorRepository;
import com.WeddingPlanning.Backend.DataStructure.VendorLinkedList;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VendorService {
    private VendorLinkedList vendors;
    private final VendorRepository repository;

    public VendorService(VendorRepository repository) {
        this.repository = repository;
        this.vendors = repository.loadVendors();
    }

    public void addVendor(Vendor vendor) {
        VendorLinkedList currentVendors = repository.loadVendors();

        if (currentVendors.findById(vendor.getId()) != null ||
                currentVendors.findByEmail(vendor.getEmail()) != null) {
            System.out.println("⚠️ Vendor already exists by ID or Email");
            return;
        }

        currentVendors.add(vendor);
        this.vendors = currentVendors;

        repository.saveVendors(currentVendors);
        System.out.println("✅ Vendor added and saved");
    }

    public List<Vendor> getAllVendors() {
        return vendors.toList();
    }

    public Vendor getVendorById(Long id) {
        return vendors.findById(id);
    }

    public Vendor getVendorByEmail(String email) {
        return vendors.findByEmail(email);
    }

    public Vendor vendorLogin(String email, String password) {
        Vendor v = vendors.findByEmail(email);
        return (v != null && v.getPassword().equals(password)) ? v : null;
    }

    public void updateVendor(Long id, Vendor updated) {
        vendors.update(id, updated);
        repository.saveVendors(vendors);
    }

    public void deleteVendor(Long id) {
        vendors.removeById(id);
        repository.saveVendors(vendors);
    }

    public void sortVendorsByPrice(boolean ascending) {
        vendors.sortByPrice(ascending);
    }
}